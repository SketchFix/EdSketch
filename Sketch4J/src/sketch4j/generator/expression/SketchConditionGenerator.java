/**
 * @author Lisa Nov 10, 2016 InterStmtPruning.java 
 */
package sketch4j.generator.expression;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

//import gov.nasa.jpf.jvm.Verify;
import sketch4j.executor.SketchExecutor;
import sketch4j.frontend.ast.SJValueCandidate;
import sketch4j.generator.SketchGenerator;

/**
 * I define it as Iterator to support next version without JPF. Right now since
 * we use JPF for backtrack, hasNext return true and next only return one
 * element.
 *
 */

public class SketchConditionGenerator extends SketchGenerator {

	private int constant = -1;
	private int valSign = -1;

	private HashSet<int[]> trueSet = new HashSet<int[]>();
	private HashSet<int[]> falseSet = new HashSet<int[]>();
	private SJValueCandidate[] candidates = null;
	private SJValueCandidate[] prevCand = null;
	protected SketchExecutor exec;
	private Stack<SketchConditionState> stack = new Stack<SketchConditionState>();

	public SketchConditionGenerator(SketchExecutor exec) {
		this.exec = exec;
	}

	public boolean next(SJValueCandidate[] vals) {
		prevCand = candidates;
		candidates = vals;
		if (constant == -1) {
			constant = exec.choose(2);
			// constant = Verify.getInt(0, 2);
			switch (constant) {
			case 0:
				break;
			case 1:
				return true;
			case 2:
				return false;
			}
		}
		if (constant == 1)
			return true;
		else if (constant == 2)
			return false;
		return construct(vals);
	}

	private boolean construct(SJValueCandidate[] vals) {
		switch (valSign) {
		case -1:
			// for each same pairs
			int slen = vals.length;
			for (int i = 0; i < slen; i++) {
				if (vals[i].isNPE()) {
					continue;
				}
				for (int j = i + 1; j < slen; j++) {
					if (vals[j].isNPE())
						continue;
					if (vals[i].valueMatch(vals[j])) {
						// System.out.println(i+" "+j+" "+0);
						trueSet.add(new int[] { i, j, 0 });
						falseSet.add(new int[] { i, j, 1 });
					} else {
						// System.out.println(i+" "+j+" "+1);
						trueSet.add(new int[] { i, j, 1 });
						falseSet.add(new int[] { i, j, 0 });
					}
				}
			}
			break;
		case 0:
			if (trueSet.size() == 0) {
				exec.backtrack();
			}
			// falseSet = new HashSet<int[]>();
			Iterator<int[]> itr = trueSet.iterator();
			while (itr.hasNext()) {
				int[] next = itr.next();
				if (vals[next[0]].isNPE() || vals[next[1]].isNPE()) {
					itr.remove();
				} else {
					if (vals[next[0]].valueMatch(vals[next[1]])) {
						itr.remove();
						falseSet.add(next);
						trueSet.remove(next);
					}
				}
			}
			break;
		case 1:
			if (falseSet.size() == 0) {
				exec.backtrack();
			}
			// trueSet = new HashSet<int[]>();
			itr = falseSet.iterator();
			while (itr.hasNext()) {
				int[] next = itr.next();
				if (vals[next[0]].isNPE() || vals[next[1]].isNPE()) {
					itr.remove();
				} else {
					if (vals[next[0]].valueMatch(vals[next[1]])) {
						itr.remove();
						trueSet.add(next);
						falseSet.remove(next);
					}
				}
			}
		}

		if (trueSet.size() == 0 && falseSet.size() == 0) {
			exec.backtrack();
		}
		if (falseSet.size() == 0)
			valSign = 0;
		else if (trueSet.size() == 0)
			valSign = 1;
		else
			valSign = exec.choose(1);
		stack.push(new SketchConditionState(trueSet, falseSet));
		// printSet();
		return valSign == 0 ? true : false;
	}

	private void printSet() {
		System.out.println("****TRUE set");
		for (int[] p : trueSet)
			System.out.println(candidates[p[0]].getName() + (p[2] == 0 ? " == " : " != ") + candidates[p[1]].getName());
		System.out.println("****FALSE set");
		for (int[] p : falseSet) {
			if ((p[2] == 0 && prevCand[p[0]].valueMatch(prevCand[p[1]]))
					|| (p[2] == 1 && !prevCand[p[0]].valueMatch(prevCand[p[1]])))
				System.out.println(candidates[p[0]] + (p[2] == 0 ? " == " : " != ") + candidates[p[1]]);
		}
	}

	public String toString() {
		try {
			// if (candidates == null)
			// return lhs + " " + relation + " " + rhs + " ";
			if (valSign == 0) {
				HashMap<String, String> cands = new HashMap<String, String>();
				for (int[] p : falseSet) {
					String key = candidates[p[0]].getName() + "_" + candidates[p[1]].getName();
					if (cands.containsKey(key))
						cands.remove(key);
					else
						cands.put(key, candidates[p[0]].getName() + (p[2] == 0 ? " == " : " != ")
								+ candidates[p[1]].getName());
				}
				for (String s : cands.values())
					System.out.println(s);
				System.out.println("------------");
			} else {
				// HashMap<String, String> cands = new HashMap<String,
				// String>();
				// for (int[] p : trueSet) {
				// String key = candidates[p[0]].getName() + "_" +
				// candidates[p[1]].getName();
				//
				// cands.put(key, candidates[p[0]].getName() + (p[2] == 0 ? " ==
				// " : " != ")
				// + candidates[p[1]].getName());
				// }
				printSet();
				// for (String s : cands.values())
				// System.out.println(s);
			}
		} catch (Exception e) {
		}
		return "  CONDITION " + (valSign == 0 ? true : false) + " \n";
	}

	private HashSet<String> printTrueSet() {
		for (int[] p : falseSet) {
			if (!candidates[p[0]].valueMatch(candidates[p[1]])) {
				System.out.println(
						"*" + candidates[p[0]].getName() + (p[2] == 0 ? " == " : " != ") + candidates[p[1]].getName());
			}
		}
		return null;
	}

	public void reset() {
		// System.out.println("reset");
		valSign = -1;
		constant = -1;
		trueSet = new HashSet<int[]>();
		falseSet = new HashSet<int[]>();
		// restoreState();
	}

	private void restoreState() {
		if (stack.size() == 0)
			return;
		SketchConditionState state = stack.pop();
		trueSet = state.getTrueSet();
		falseSet = state.getFalseSet();
	}

	private void serialize() {
		try {
			PrintWriter writer = new PrintWriter("log");
			writer.println(valSign);
			for (int[] arr : trueSet) {
				writer.println(arr[0] + " " + arr[1] + " " + arr[2]);
			}
			writer.println();
			for (int[] arr : falseSet) {
				writer.println(arr[0] + " " + arr[1] + " " + arr[2]);
			}
			writer.close();
		} catch (FileNotFoundException e) {
		}
	}

	private void deserialize() {
		reset();
		try {
			Scanner scan = new Scanner(new File("log"));
			if (scan.hasNextInt()) {
				valSign = scan.nextInt();
			}
			scan.nextLine();
			// trueset
			while (scan.hasNextLine()) {
				String[] token = scan.nextLine().split(" ");
				if (token.length < 3)
					break;
				int[] arr = new int[3];
				arr[0] = Integer.parseInt(token[0]);
				arr[1] = Integer.parseInt(token[1]);
				arr[2] = Integer.parseInt(token[2]);
				trueSet.add(arr);
			}
			// falseset
			while (scan.hasNextLine()) {
				String[] token = scan.nextLine().split(" ");
				if (token.length < 3)
					break;
				int[] arr = new int[3];
				arr[0] = Integer.parseInt(token[0]);
				arr[1] = Integer.parseInt(token[1]);
				arr[2] = Integer.parseInt(token[2]);
				falseSet.add(arr);
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}