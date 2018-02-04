/**
 * @author Lisa Apr 17, 2016 LinkedList_7.java 
 */
package sketch4j.generator;

import java.util.ArrayList;
import java.util.List;

import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.frontend.ast.SJExpressionGenerator;
import sketch4j.frontend.ast.SJValueCandidate;
import sketch4j.generator.expression.SketchConditionGenerator;
import sketch4j.generator.expression.SketchConditionGenerator_Semmetry;
import sketch4j.generator.statement.AssignmentCandidate;
import sketch4j.generator.statement.StmtType;
import sketch4j.validator.SketchCandidateValidator;

@SuppressWarnings("rawtypes")
public class SketchRequest {
	private static SketchExecutor exec = new SketchExecutor(ExecutorType.JPF);
	private static boolean isJUZI;
	private static boolean[] conditionInit = new boolean[10];
	private static boolean[] assignInit = new boolean[10];
	private static List<SketchBlockGenerator> blockGenerator = new ArrayList<SketchBlockGenerator>();
	private static List<List<SymbolicCandidate>> cand = new ArrayList<List<SymbolicCandidate>>();
	private static List<SketchConditionGenerator> condGenerator = new ArrayList<SketchConditionGenerator>();
	private static SketchCandidateValidator validator = new SketchCandidateValidator();
	private static List<SJExpressionGenerator> exprGenerator = new ArrayList<SJExpressionGenerator>();
	private static List<SJValueCandidate[]> candidates = new ArrayList<SJValueCandidate[]>();

	private static void initCandidates(Class candClass, String[] names, Object[] vals, int fieldDeref, boolean hasNull,
			int id) {
		while (candidates.size() <= id) {
			exprGenerator.add(new SJExpressionGenerator());
			exprGenerator.get(id).addTypeVals(candClass, names, vals, fieldDeref);
			candidates.add(exprGenerator.get(id).getSJCandidates(candClass, hasNull));
			// validator.setCandidates(candidates.get(id));
		}
	}

	public static List<SymbolicCandidate> queryBlock(Class candClass, String[] names, Object[] vals, int count,
			int fieldDeref, int id) {
		if (assignInit[id])
			return cand.get(cand.size() - 1);
		if (!assignInit[id])
			initCandidates(candClass, names, vals, fieldDeref, false, id);
		assignInit[id] = true;
		while (id >= blockGenerator.size()) {
			blockGenerator.add(new SketchBlockGenerator());
		}
		List<StmtType> stmts = new ArrayList<StmtType>();
		for (int i = 0; i < count; i++)
			stmts.add(StmtType.ASSIGNMENT);
		SketchBlockGenerator bGen = blockGenerator.get(id);
		bGen.setSketch(stmts, candidates == null ? 10 : candidates.get(id).length);
		bGen.setInitVals(candidates.get(id));
		cand.add(((SketchCandidate) bGen.next()).getList());
		return cand.get(cand.size() - 1);
	}

	public static double queryPrimOperator(String[] names, double[] vals, int id) {

		return 0;
	}

	public static boolean queryCondition(Class candClass, String[] names, Object[] vals, int count, int fieldDeref,
			boolean hasNull, int id) {
		if (conditionInit[id]) {
			candidates.set(id, exprGenerator.get(id).updateSJCandidates(candClass, vals));
			return condGenerator.get(id).next(candidates.get(id));
		}
		conditionInit[id] = true;
		initCandidates(candClass, names, vals, fieldDeref, hasNull, id);
		while (id >= condGenerator.size()) {
			condGenerator.add(new SketchConditionGenerator(SketchRequest.exec));
			// condGenerator.add(new
			// SketchConditionGenerator_Semmetry(SketchRequest.exec));
		}
		List<StmtType> conds = new ArrayList<StmtType>();
		for (int i = 0; i < count; i++)
			conds.add(StmtType.CONDITION);
		return condGenerator.get(id).next(candidates.get(id));
		// not fully support multi expr condition
		// bGen.setSketch(stmts, candidates == null ? 10 : candidates.length);
		// bGen.setInitVals(candidates);
		// cand = ((SketchCandidate) bGen.next()).getList();

	}

	public static boolean notValid(int[] vids) {
		return validator.notValid(vids);
	}

	@SuppressWarnings("incomplete-switch")
	public static String toString(SymbolicCandidate cand) {
		if (cand == null)
			return "";
		switch (cand.getType()) {
		case ASSIGNMENT:
			AssignmentCandidate ac = (AssignmentCandidate) cand;
			return candidates.get(candidates.size() - 1)[ac.getJPFLHS()].getName() + " = "
					+ candidates.get(candidates.size() - 1)[ac.getJPFRHS()].getName();
		case CONDITION:
			return "";
		}
		return "";
	}

	public static void setExecutor(ExecutorType type) {
		exec = new SketchExecutor(type);
		if (type == ExecutorType.JUZI)
			isJUZI = true;
	}

	public static void backtrack() {
		if (exec == null)
			exec = new SketchExecutor(ExecutorType.JUZI);
		exec.backtrack();
	}

	public static void countbacktrack() {
		if (exec == null)
			exec = new SketchExecutor(ExecutorType.JUZI);
		exec.backtrack();
	}

	public static SJValueCandidate getCandidate(int rid) {
		// TODO Auto-generated method stub
		return candidates.get(candidates.size() - 1)[rid];
	}

	public static int incrementPointer() {
		// exec.incrementCounter();
		return exec.getCounterPointer();
	}

	public static int getCounter() {
		// TODO Auto-generated method stub
		return exec.getCounterPointer();
	}

	public static int choose(int j) {
		if (exec == null) {
			exec = new SketchExecutor(ExecutorType.JUZI);
		}
		int res = exec.choose(j);
		// if (isJUZI)
		// exec.incrementCounter();
		return res;
	}

	public static void initialize() {
		for (List<SymbolicCandidate> sketch : cand) {
			for (SymbolicCandidate sc : sketch) {
				sc.reset();
			}
		}
		for (SketchConditionGenerator cg : condGenerator)
			cg.reset();
		// candidates = new ArrayList<SJValueCandidate[]>();
		// conditionInit = new boolean[10];
	}

	public static boolean isJUZI() {
		return isJUZI;
	}

	public static SketchExecutor getExecutor() {
		return exec;
	}

	public static String getString() {
		String str = "";
		for (SketchConditionGenerator cg : condGenerator) {
			str += "Sketch4J.COND(Entry.class)\n";
			str += cg.toString();
		}

		for (SketchBlockGenerator bg : blockGenerator) {
			str += "Sketch4J.BLOCK(Entry.class)\n";
			str += bg.toString();
		}
		return str;
	}
}
