package test.sketch4j.example.linkedList.deprecated;

import gov.nasa.jpf.jvm.Verify;

public class Sketch_Gallary_LL_Reverse {

	static class Entry {
		Entry next = null;
		int val;

		public Entry(int val) {
			this.val = val;
		}
	}

	static class LinkedList {
		Entry head = null;

		public String toString() {
			String res = "";
			for (Entry e = head; e != null; e = e.next)
				res += e.val + " ";
			return res;
		}
	}
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
		while (_EXP_(_ln1_, _ln2_, _ln3_, _ln4_)) {
			if (iter_count++ > ITER_BOUND)
				Verify.ignoreIf(true);
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

	static int _cond = -1;

	static boolean _EXP_(Entry ln1, Entry ln2, Entry ln3, Entry ln4) {
		if (_exps_[2*N_S+2] == -1) {
			_exps_[0] = Verify.getInt(0, 5);
		}
		if (_exps_[2*N_S+3] == -1) {
			_exps_[1] = Verify.getInt(0, 6); // RHS can be null
		}
		if (_cond == -1) {
			_cond = Verify.getInt(0, 1); // equal or not equal
		}
		Entry exp1 = null;
		Entry exp2 = null;

		switch (_exps_[0]) {
		case 0:
			exp1 = ln1;
			break;
		case 1:
			exp1 = ln2;
			break;
		case 2:
			exp1 = ln3;
			break;
		case 3:
			exp1 = ln4;
			break;
		case 4:
			exp1 = ln1.next;
			break;
		case 5:
			exp1 = ln2.next;
			break;
		}
		switch (_exps_[1]) {
		case 0:
			exp1 = ln1;
			break;
		case 1:
			exp1 = ln2;
			break;
		case 2:
			exp1 = ln3;
			break;
		case 3:
			exp1 = ln4;
			break;
		case 4:
			exp1 = ln1.next;
			break;
		case 5:
			exp1 = ln2.next;
			break;
		case 6:
			exp2 = null;
			break;
		}
		if (_cond == 0)
			return exp1 == exp2;
		else
			return exp1 != exp2;
	}

	static int N_S = 0;
	static int[] _lhs_ = new int[] { -1, -1, -1, -1, -1 };
	static int[] _exps_ = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1 };
	static String[] _vars_ = { "ln1", "ln2", "ln3", "ln4", "ln1.next", "ln2.next",
			"null" /* "ln3.next", "ln4.next", */ };

	static LinkedList _ll_;
	static Entry _ln1_, _ln2_, _ln3_, _ln4_;

	static void _LHS_(int _l_) {
		if (_exps_[2 * _l_] == -1) {
			_exps_[2 * _l_] = Verify.getInt(0, _vars_.length - 2); // LHS
		}
		if (_exps_[2 * _l_ + 1] == -1) {
			_exps_[2 * _l_ + 1] = Verify.getInt(0, _vars_.length - 1); // RHS
																		// can
																		// be
																		// null
		}
		if (_exps_[2 * _l_] == _exps_[2 * _l_ + 1]) {
			Verify.ignoreIf(true);
		}

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
		// case 6:
		// rhs = _ln3_.next;
		// break;
		// case 7:
		// rhs = _ln4_.next;
		// break;
		case 6:
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
		// case 6:
		// _ln3_.next = rhs;
		// break;
		// case 7:
		// _ln4_.next = rhs;
		// break;
		}
	}

	// test harness

	public static void main(String[] a) {
		try {
			// Test #1
			runTest(createList(new int[] {}), createList(new int[] {}));

			// Test #2
			runTest(createList(new int[] { 1 }), createList(new int[] { 1 }));

			// Test #3
			runTest(createList(new int[] { 1, 2 }), createList(new int[] { 1, 2 }));

			// Test #4
			runTest(createList(new int[] { 1, 2, 3 }), createList(new int[] { 1, 2, 3 }));

			// Test #5
			runTest(createList(new int[] { 1, 2, 3, 4, 5, 6 }), createList(new int[] { 1, 2, 3, 4, 5, 6 }));

			// Test #5
			runTest(createList(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }),
					createList(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }));
		} catch (NullPointerException npe) {
			System.out.println("BACKTRACKING: null pointer exception");
			Verify.ignoreIf(true);
		}

		System.out.println("****ALL TESTS PASSED!");
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

	static void runTest(LinkedList l, LinkedList l_copy) {
		iter_count = 0;
		LinkedList expected = expected_reverse(l);
		LinkedList actual = sketchme_reverse(l_copy);
		boolean outcome = checkEq(expected, actual);
		// String out = outcome ? "PASS" : "FAIL";
		// System.out.println("[Test] expected: " + expected + ", actual: " +
		// actual + " **" + out);
		// for (int i = 0; i < N_S; i++) {
		// System.out.println(" -- " + _vars_[_exps_[2 * i + 0]] + " = " +
		// _vars_[_exps_[2 * i + 1]] + ",");
		// }
		 System.out.println(" -- " + _vars_[_exps_[2*N_S+2]] + " = " +
				 _vars_[_exps_[2*N_S+3]] + ",");
		if (!outcome) {
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
				System.out.print("check return false");
				return false;
			}
		}
		return xh == null && yh == null;
	}
}
