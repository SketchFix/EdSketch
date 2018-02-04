package test.sketch4j.example.linkedList.deprecated;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import gov.nasa.jpf.jvm.Verify;
import test.sketch4j.example.linkedList.Entry;
import test.sketch4j.example.linkedList.LinkedList;

public class Sketch_LL_Reverse {

	// expected result of sketching. Assume no loop in LL
	static LinkedList expected_reverse(LinkedList l) {
		if (l.head == null)
			return l;
		Entry ln1 = l.head;
		Entry ln2 = l.head.next;
		Entry ln3 = null;
		Entry ln4 = null;
		while (ln2 != null) {
			ln4 = ln2.next;
			ln1.next = ln3;
			ln3 = ln1;
			ln1 = ln2;
			ln2 = ln4;
		}
		l.head = ln1;
		ln1.next = ln3;
		return l;
	}

	static int iter_count = 0;
	final static int ITER_BOUND = 11;

	// translated program to sketch
	// operator hole is replaced with method call
	static LinkedList sketchme_reverse(LinkedList l) {
		if (l.head == null)
			return l;
		Entry ln1 = l.head;
		Entry ln2 = l.head.next;
		Entry ln3 = null;
		Entry ln4 = null;
		{
			_ln1_ = ln1;
			_ln2_ = ln2;
			_ln3_ = ln3;
			_ln4_ = ln4;
			_ll_ = l;
		}
		while (_ln2_ != null) {
			if (iter_count++ > ITER_BOUND)
				Verify.ignoreIf(true);
			// _ln4_ = _ln2_.next;
			// _ln1_.next = _ln3_;
			// _ln3_ = _ln1_;
			// _ln1_ = _ln2_;
			_LHS_(0);
			_LHS_(1);
			_LHS_(2);
			_LHS_(3);
			_LHS_(4);
		}
		_ll_.head = _ln1_;
		_ln1_.next = _ln3_;
		return _ll_;
	}

	static int N_S = 5;
	static int[] _exps_ = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
	static String[] _vars_ = { "ln1", "ln2", "ln3", "ln4", "ln1.next", "ln2.next", "ln3.next", "ln4.next", "null" };

	static LinkedList _ll_;
	static Entry _ln1_, _ln2_, _ln3_, _ln4_;

	static void _LHS_(int _l_) {
		if (_exps_[2 * _l_] == -1) {
			_exps_[2 * _l_] = Verify.getInt(0, _vars_.length - 2); // LHS
		}
		if (_exps_[2 * _l_ + 1] == -1) {
			// evaluate and find unique
			ArrayList<Integer> al = new ArrayList<Integer>();
			Set<Object> hs = new HashSet<Object>();
			// FIXME: ensure set membership is "==" and not "equals" method
			if (hs.add(_ln1_))
				al.add(0);
			if (hs.add(_ln2_))
				al.add(1);
			if (hs.add(_ln3_))
				al.add(2);
			if (hs.add(_ln4_))
				al.add(3);
			if (_ln1_ != null && hs.add(_ln1_.next))
				al.add(4);
			if (_ln2_ != null && hs.add(_ln2_.next))
				al.add(5);
			if (_ln3_ != null && hs.add(_ln3_.next))
				al.add(6);
			if (_ln4_ != null && hs.add(_ln4_.next))
				al.add(7);
			if (hs.add(null))
				al.add(6);
			// _exps_[2 * _l_ + 1] = Verify.getInt(0, _vars_.length - 1);
			_exps_[2 * _l_ + 1] = al.get(Verify.getInt(0, al.size() - 1)); //
			// RHS

		}
//		if (new InterStmtPruning(_exps_).prune(_l_)) {
//			Verify.ignoreIf(true);
//		}

		Entry rhs = null;
		switch (_exps_[2 * _l_ + 1]) {
		case 0:
			rhs = _ln1_;
			break;
		case 1:
			rhs = _ln2_;
			break;
		case 2:
			rhs = _ln3_;
			break;
		case 3:
			rhs = _ln4_;
			break;
		case 4:
			rhs = _ln1_.next;
			break;
		case 5:
			rhs = _ln2_.next;
			break;
		case 6:
			rhs = _ln3_.next;
			break;
		case 7:
			rhs = _ln4_.next;
			break;
		case 8:
			rhs = null;
			break;
		}

		switch (_exps_[2 * _l_]) {
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
			_ln4_ = rhs;
			break;
		case 4:
			_ln1_.next = rhs;
			break;
		case 5:
			_ln2_.next = rhs;
			break;
		case 6:
			_ln3_.next = rhs;
			break;
		case 7:
			_ln4_.next = rhs;
			break;
		}
	}

	// test harness

	public static void main(String[] a) {
		Verify.resetCounter(0);
		Verify.resetCounter(1);
		Verify.resetCounter(2);
		Verify.resetCounter(3);
		try {
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
			runTest(createList(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }),
					createList(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }), 5);
		} catch (NullPointerException npe) {
			// npe.printStackTrace();
			System.out.println("BACKTRACKING: null pointer exception");
			Verify.ignoreIf(true);
		}
		System.out.println("****ALL TESTS PASSED!");
		for (int i = 0; i < N_S; i++) {
			if (_exps_[2 * i] == -1)
				break;
			System.out.println(" -- " + _vars_[_exps_[2 * i + 0]] + " = " + _vars_[_exps_[2 * i + 1]] + ";");
		}
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

	static void runTest(LinkedList l, LinkedList l_copy, int counter) {
		iter_count = 0;
		LinkedList expected = expected_reverse(l);
		LinkedList actual = sketchme_reverse(l_copy);
		boolean outcome = checkEq(expected, actual);
		Verify.incrementCounter(counter);
		System.out.println("counter#" + counter + ": " + Verify.getCounter(counter));
		if (!outcome) {
			for (int i = 0; i < N_S; i++) {
				if (_exps_[2 * i] == -1)
					break;
				System.out.println(" -- " + _vars_[_exps_[2 * i + 0]] + " = " + _vars_[_exps_[2 * i + 1]] + ";");
			}

			System.out.println("BACKTRACKING: test failure");
			Verify.ignoreIf(true);
		}
	}

	static boolean checkEq(LinkedList x, LinkedList y) {
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

