package test.sketch4j.example.linkedList.deprecated;

import java.util.ArrayList;
import java.util.List;

//import gov.nasa.jpf.jvm.Verify;
//import sketch4j.config.Sketch4JCLIParser;
import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.frontend.ast.SJExpressionGenerator;
import sketch4j.frontend.ast.SJValueCandidate;
import sketch4j.generator.SketchBlockGenerator;
import sketch4j.generator.SketchCandidate;
import sketch4j.generator.SymbolicCandidate;
import sketch4j.generator.expression.SketchConditionGenerator;
import sketch4j.generator.statement.AssignmentCandidate;
import sketch4j.generator.statement.StmtType;
import sketch4j.validator.SketchCandidateValidator;
import test.sketch4j.example.linkedList.Entry;
import test.sketch4j.example.linkedList.LinkedList;
@SuppressWarnings("rawtypes")
public class LL_Reverse_Condition {

	// expected result of sketching. Assume no loop in LL
	private static LinkedList expected_reverse(LinkedList l) {
		if (l.head == null)
			return l;
		Entry ln1 = l.head;
		Entry ln2 = l.head.next;
		Entry ln3 = null;
		while (ln2 != null) {
			ln1.next = ln3;
			ln3 = ln1;
			ln1 = ln2;
			ln2 = ln2.next;
		}
		ln1.next = ln3;
		l.head = ln1;
		return l;
	}

	private static int iter_count = 0;
	private final static int ITER_BOUND = 11;

	// translated program to sketch
	// operator hole is replaced with method call
	private static LinkedList sketchme_reverse(LinkedList l) {
		if (l.head == null)
			return l;
		Entry ln1 = l.head;
		Entry ln2 = l.head.next;
		Entry ln3 = null;
		{
			_ln1_ = ln1;
			_ln2_ = ln2;
			_ln3_ = ln3;
			_ll_ = l;

			exprGenerator.addTypeVals(_ln1_.getClass(), new String[] { "ln1", "ln2", "ln3" },
					new Object[] { _ln1_, _ln2_, _ln3_ });
			candidates = exprGenerator.getSJCandidates(_ln1_.getClass(),true);
			 initBlock(0, ln1.getClass(), StmtType.ASSIGNMENT, 1);
			init(0, ln1.getClass());
			// we can have more value insert
		}
		 while (_ln2_ != null) {
//		while (_EXP_(0)) {
			// while (_ln2_ != null) {
			if (iter_count++ > ITER_BOUND) {
//				Verify.ignoreIf(true);
				exec.backtrack();
			}
			_ln1_.next = _ln3_;
			_ln3_ = _ln1_;
			_ln1_ = _ln2_;
//			_ln2_ = _ln2_.next;
			
			 _BLOCK_();
			exprGenerator.updateSJCandidates(_ln1_.getClass(), new Object[] { _ln1_, _ln2_, _ln3_ });
		}

		_ln1_.next = _ln3_;
		_ll_.head = _ln1_;
		// System.out.println("end while");
		return _ll_;
	}

	static LinkedList _ll_;
	static Entry _ln1_, _ln2_, _ln3_;

//	private static boolean _EXP_(int c) {
//		return condGenerator.next(candidates);
//	}
	private static SketchExecutor exec = new SketchExecutor(ExecutorType.JPF);
	private static SketchBlockGenerator blockGenerator = new SketchBlockGenerator ();
	private static List<SymbolicCandidate> cand;
	public static SketchConditionGenerator condGenerator = new SketchConditionGenerator(exec);
	private static SketchCandidateValidator validator = new SketchCandidateValidator();
	private static SJValueCandidate[] candidates;
	private static SJExpressionGenerator exprGenerator = new SJExpressionGenerator();

	private static void init(int id, Class output) {
		condGenerator = new SketchConditionGenerator(exec);
		validator = new SketchCandidateValidator();
	}

	private static void initBlock(int id,  Class output, StmtType type, int count) {
		blockGenerator = new SketchBlockGenerator();
		List<StmtType> stmts = new ArrayList<StmtType>();
		for (int i = 0; i < count; i++)
			stmts.add(type);
		blockGenerator.setSketch(stmts,candidates.length);
		blockGenerator.setInitVals(candidates);
		cand = ((SketchCandidate) blockGenerator.next()).getList();
		condGenerator = new SketchConditionGenerator(exec);
		validator = new SketchCandidateValidator();
	}

	private static void _BLOCK_() {

		for (int i = 0; i < cand.size(); i++) {
			switch (cand.get(i).getType()) {
			case ASSIGNMENT:
				assignment((AssignmentCandidate) cand.get(i), (i == 0) ? null : (AssignmentCandidate) cand.get(i - 1));
				break;
			default:
				break;
			}
		}
	}

	private static void assignment(AssignmentCandidate assign, AssignmentCandidate prev) {
		int lid = assign.getJPFLHS();
		int rid = assign.getJPFRHS();
		Entry[] vals = new Entry[] { _ln1_, _ln2_, _ln3_ };
		int len = vals.length;
		int[] vids = null;
		if (prev == null)
			vids = new int[] { len, lid, rid };
		else
			vids = new int[] { len, lid, rid, prev.getLHS(), prev.getRHS() };
		if (validator.notValid(vids)) {
			System.out.println(blockGenerator);
//			Verify.ignoreIf(true);
			exec.backtrack();
		}
		SJValueCandidate c = candidates[rid];
		if (c.isNPE()) {
//			Verify.ignoreIf(true);
			exec.backtrack();
		}
		Entry rhs = (Entry) c.getValue();
		switch (lid) {
		case 0:
			_ln1_ = rhs;
			break;
		case 1:
			_ln2_ = rhs;
			break;
		case 2:
			_ln3_ = rhs;
			break;
		case 3:
			_ln1_.next = rhs;
			break;
		case 4:
			_ln2_.next = rhs;
			break;
		case 5:
			_ln3_.next = rhs;
			break;
		}

	}

	// test harness

	public static void main(String[] a) {
//		Sketch4JCLIParser.init(a);
//		Verify.resetCounter(0);
//		Verify.resetCounter(1);
//		Verify.resetCounter(2);
//		Verify.resetCounter(3);
//		Verify.resetCounter(4);
//		Verify.resetCounter(5);
		try {
			runTest(createList(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }),
					createList(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }), 5);
			// Test #1
			runTest(createList(new int[] {}), createList(new int[] {}), 0);

			// Test #2
			runTest(createList(new int[] { 1 }), createList(new int[] { 1 }), 1);

			// Test #3
			runTest(createList(new int[] { 1, 2 }), createList(new int[] { 1, 2 }), 2);

			// Test #4
			runTest(createList(new int[] { 1, 2, 3 }), createList(new int[] { 1, 2, 3 }), 3);

			// Test #5
			runTest(createList(new int[] { 1, 2, 3, 4, 5, 6 }), createList(new int[] { 1, 2, 3, 4, 5, 6 }), 4);
			// Test #6
//		} catch (Exception npe) {
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			// print();
			// System.out.println("while ( "+ condGenerator.toString()+" )");
			System.out.println("BACKTRACKING: null pointer exception");
			// System.out.println(blockGenerator);
//			Verify.ignoreIf(true);
			exec.backtrack();
		}
		System.out.println(blockGenerator);
		// if (condGenerator != null)
		System.out.println("while ( " + condGenerator.toString() + " )");
		
		System.out.println("****ALL TESTS PASSED!");
		// print();
		 if (blockGenerator != null)
				 System.out.println(blockGenerator.toString());
		throw new RuntimeException("****FOUND FIRST SOLUTION");
	}

	private static LinkedList createList(int[] arr) {
		LinkedList l = new LinkedList();
		if (arr.length == 0)
			return l;
		l.head = new Entry(arr[0]);
		Entry p = l.head;
		for (int i = 1; i < arr.length; i++) {
			p.next = new Entry(arr[i]);
			p = p.next;
		}
		return l;
	}

	private static void runTest(LinkedList l, LinkedList l_copy, int counter) {
		iter_count = 0;
		LinkedList expected = expected_reverse(l);
		LinkedList actual = sketchme_reverse(l_copy);
		// System.out.println("end while");
		// System.out.println(expected + "--" + actual);
		boolean outcome = checkEq(expected, actual);
//		Verify.incrementCounter(counter);
		exec.incrementCounter();
		System.out.println("counter#" + counter + ": " + exec.getCounterPointer());
//		System.out.println("counter#" + counter + ": " + Verify.getCounter(counter));
		if (!outcome) {
			// print();
			System.out.println("BACKTRACKING: test failure");
//			System.out.println(blockGenerator);
//			Verify.ignoreIf(true);
			exec.backtrack();
		}
	}

	private static boolean checkEq(LinkedList x, LinkedList y) {

		if (x == null && y == null)
			return true;
		Entry xh = x.head, yh = y.head;
		for (; xh != null && yh != null; xh = xh.next, yh = yh.next) {

			if (xh.val != yh.val) {
				return false;
			}
		}
		return xh == null && yh == null;
	}

}