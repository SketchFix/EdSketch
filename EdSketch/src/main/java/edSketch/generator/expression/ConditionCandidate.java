/**
 * @author Lisa Nov 10, 2016 InterStmtPruning.java 
 */
package edSketch.generator.expression;

import java.util.HashSet;
import java.util.Iterator;

import edSketch.executor.SketchExecutor;
import edSketch.frontend.exprGenerator.SJValueCandidate;
import edSketch.generator.statement.StmtType;
import edSketch.generator.statement.SymbolicCandidate;

/**
 * A single condition candidate, that can be combined to generate condition,
 * e.g., a==b && b==c.
 * 
 * @author lisahua
 *
 */
public class ConditionCandidate extends SymbolicCandidate {
	private int constant = -1;
	private int valSign = -1;
	private String[] ops = { " == ", " != ", ">", "<", "<=", ">=" };
	private HashSet<int[]> trueSet = new HashSet<int[]>();
	private HashSet<int[]> falseSet = new HashSet<int[]>();
	private SJValueCandidate[] candidates = null;
	private HashSet<int[]> hasFoundSet = null;
	private int isPrime = -1;

	public ConditionCandidate() {
		type = StmtType.CONDITION;
	}

	public Boolean next(SJValueCandidate[] vals) {
		candidates = vals;
		/**
		 * FIXME: This is a trick of executing multiple tests. Once a test case
		 * pass, we save the selected set. Instead of using expensive value
		 * grouping for each further test cases, we use concrete execution by
		 * selecting a satisfactory candidate. However,I haven't fully tested
		 * the cases when first test case cannot lead to correct candidate.
		 */
		if (hasFoundSet != null && hasFoundSet.size() != 0) {
			int[] next = hasFoundSet.iterator().next();
			return fetchBoolVal(next);
		}
		if (candidates == null || candidates.length == 0)
			return false;
		if (isPrime == -1)
			isPrime = isPrime(candidates[0].getCandClass()) ? 0 : 1;
		if (constant == -1) {
			constant = SketchExecutor.choose(2);
			switch (constant) {
			case 1:
				return false;
			case 2:
				return true;
			case 0:
				break;
			}
		}
		if (constant == 1)
			return false;
		else if (constant == 2)
			return true;
		return construct();
	}

	private boolean construct() {
		switch (valSign) {
		case -1:
			if (isPrime == 0)
				primeConstruct();
			else
				nonPrimeConstruct();
			break;
		case 0:
			if (trueSet.size() == 0) {
				SketchExecutor.backtrack();
			}
			 falseSet = new HashSet<int[]>();
			update(trueSet.iterator());
			break;
		case 1:
			if (falseSet.size() == 0) {
				SketchExecutor.backtrack();
			}
			 trueSet = new HashSet<int[]>();
			update(falseSet.iterator());
			break;
		}
	if (trueSet.size() == 0 && falseSet.size() == 0) {
			SketchExecutor.backtrack();
		}
		if (falseSet.size() == 0)
			valSign = 0;
		else if (trueSet.size() == 0)
			valSign = 1;
		else {
			valSign = SketchExecutor.choose(1);
		}
		return valSign == 0 ? true : false;
	}


	private boolean fetchBoolVal(int[] p) {
		if (isPrime == 0) {
			double lhs_v = 0, rhs_v = 0;
			if (candidates[p[0]].getCandClass().equals(Integer.class) ||candidates[p[0]].getCandClass().equals(int.class))
				lhs_v = ((Integer) candidates[p[0]].getValue()).doubleValue();
			else
				lhs_v = (double) candidates[p[0]].getValue();
			if (candidates[p[1]].getCandClass().equals(Integer.class)||candidates[p[1]].getCandClass().equals(int.class))
				rhs_v = ((Integer) candidates[p[1]].getValue()).doubleValue();
			else
				rhs_v = (double) candidates[p[1]].getValue();
			switch (p[2]) {
			case 0:
				return lhs_v == rhs_v;
			case 1:
				return lhs_v != rhs_v;
			case 2:
				return lhs_v > rhs_v;
			case 3:
				return lhs_v < rhs_v;
			case 4:
				return lhs_v <= rhs_v;
			case 5:
				return lhs_v >= rhs_v;
			}
		} else {
			return candidates[p[0]].valueMatch(candidates[p[1]]) ^ p[2] == 1;
		}
		return false;
	}

	@SuppressWarnings("unused")
	private void printSet() {
		System.out.println("****TRUE set");
		for (int[] p : trueSet)
			System.out.println(candidates[p[0]].getName() + ops[p[2]] + candidates[p[1]].getName());
		System.out.println("****FALSE set");
		for (int[] p : falseSet)
			System.out.println(candidates[p[0]].getName() + ops[p[2]] + candidates[p[1]].getName());
	}

	private boolean isPrime(Class<?> type) {
		return type.equals(int.class) || type.equals(double.class) || type.equals(Integer.class)
				|| type.equals(Double.class);
	}

	// " == ", " != ", ">", "<", "<=", ">="
	private void primeConstruct() {
		for (int i = 0; i < candidates.length; i++) {
			if (candidates[i].isNPE()) {
				continue;
			}
			for (int j = i + 1; j < candidates.length; j++) {
				if (candidates[j].isNPE())
					continue;
				double lhs_v = 0, rhs_v = 0;
				try {
					if (Double.class.isInstance(candidates[i].getValue())) lhs_v = (double) candidates[i].getValue();
					else lhs_v = (int) candidates[i].getValue();
					if (Double.class.isInstance(candidates[j].getValue())) rhs_v = (double) candidates[j].getValue();
					else rhs_v = (int) candidates[j].getValue();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (lhs_v == rhs_v) {
					trueSet.add(new int[] { i, j, 0 });
					trueSet.add(new int[] { i, j, 4 });
					trueSet.add(new int[] { i, j, 5 });
					falseSet.add(new int[] { i, j, 1 });
					falseSet.add(new int[] { i, j, 2 });
					falseSet.add(new int[] { i, j, 3 });
				} else if ((lhs_v > rhs_v)) {
					trueSet.add(new int[] { i, j, 1 });
					trueSet.add(new int[] { i, j, 2 });
					trueSet.add(new int[] { i, j, 5 });
					falseSet.add(new int[] { i, j, 0 });
					falseSet.add(new int[] { i, j, 3 });
					falseSet.add(new int[] { i, j, 4 });

				} else {
					trueSet.add(new int[] { i, j, 1 });
					trueSet.add(new int[] { i, j, 3 });
					trueSet.add(new int[] { i, j, 4 });
					falseSet.add(new int[] { i, j, 0 });
					falseSet.add(new int[] { i, j, 2 });
					falseSet.add(new int[] { i, j, 5 });
				}
			}
		}
//		System.out.println("after prime construct" + trueSet.size());
	}

	private void nonPrimeConstruct() {
		for (int i = 0; i < candidates.length; i++) {
			if (candidates[i].isNPE()) {
				continue;
			}
			for (int j = i + 1; j < candidates.length; j++) {
				if (candidates[j].isNPE())
					continue;
				if (candidates[i].valueMatch(candidates[j])) {
					trueSet.add(new int[] { i, j, 0 });
					falseSet.add(new int[] { i, j, 1 });
				} else {
					trueSet.add(new int[] { i, j, 1 });
					falseSet.add(new int[] { i, j, 0 });
				}
			}
		}
	}

	private void update(Iterator<int[]> itr) {
		while (itr.hasNext()) {
			int[] next = itr.next();
			if (candidates[next[0]].isNPE() || candidates[next[1]].isNPE()) {
				itr.remove();
			} else {
				if (candidates[next[0]].valueMatch(candidates[next[1]])) {
					itr.remove();
					falseSet.add(next);
				}
			}
		}
	}

	public String toString() {
		if (valSign == 0) {
			System.out.println("****TRUE set");
			for (int[] p : trueSet)
				System.out.println(candidates[p[0]].getName() + ops[p[2]] + candidates[p[1]].getName());
		} else {
			System.out.println("****FALSE set");
			for (int[] p : falseSet)
				System.out.println(candidates[p[0]].getName() + ops[p[2]] + candidates[p[1]].getName());
		}
		return "  CONDITION " + (valSign == 0 ? true : false) + " \n";
	}

	public void reset() {
		valSign = -1;
		constant = -1;
		trueSet = new HashSet<int[]>();
		falseSet = new HashSet<int[]>();
	}

	public void serialize() {
		if (valSign == 0)
			hasFoundSet = trueSet;
		else if (valSign == 1)
			hasFoundSet = falseSet;
		reset();
	}

	public void deserialize() {
		hasFoundSet = null;
	}
}