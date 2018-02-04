package test.sketch4j.example.linkedList;

import java.util.List;

import sketch4j.generator.SketchRequest;
import sketch4j.generator.SymbolicCandidate;
import sketch4j.generator.statement.AssignmentCandidate;

public class LinkedList_Insert_Sorted extends LinkedList {

//	private static int iter_count = 0;
//	private final static int ITER_BOUND = 11;

	public LinkedList_Insert_Sorted(int[] arr) {
		super(arr);
	}

	// translated program to sketch
	// operator hole is replaced with method call
	/** Sorted Linked List insertion */
	public void insert(int val) {
		assert val != 0;
		Entry e = new Entry(val);
		Entry ln1 = head;
		{
			_ln1_ = ln1;
			_ln2_ = e;
		}
		while (!head.equals(_ln1_.next)) {
			if (_ln1_.next.val < val) {
//				_ln1_ = _ln1_.next;
				_BLOCK_(SketchRequest.queryBlock(_ln1_.getClass(), new String[] { "ln1", "ln2" },
						new Entry[] { _ln1_, _ln2_ }, 1, 1, 0));
			} else
				break;
		}
		
		 _ln2_.next = _ln1_.next;
		 _ln1_.next = _ln2_;
//		 _BLOCK_(SketchRequest.queryBlock(_ln1_.getClass(), new String[] { "ln1", "ln2" }, new Entry[] { _ln1_, _ln2_ },
//					1, 1, 0));
		size++;
	}

	private void assignment(AssignmentCandidate assign, AssignmentCandidate prev, int varLen) {
		int lid = assign.getJPFLHS();
		int rid = assign.getJPFRHS();
		toString += SketchRequest.toString(assign) + "   ";
		int[] vids = null;
		if (prev == null)
			vids = new int[] { varLen, lid, rid };
		else
			vids = new int[] { varLen, lid, rid, prev.getLHS(), prev.getRHS() };
		if (SketchRequest.notValid(vids)) {
			SketchRequest.backtrack();
		}
		// SJValueCandidate c = candidates[rid];
		// if (c.isNPE())
		// Verify.ignoreIf(true);
		// Entry rhs = (Entry) c.getValue();
		Entry rhs = getRHS(assign.getJPFRHS());
		switch (lid) {
		case 0:
			_ln1_ = rhs;
			break;
		case 1:
			_ln2_ = rhs;
			break;
		case 2:
			_ln1_.next = rhs;
			break;
		case 3:
			_ln2_.next = rhs;
			break;
		}
	}

	private Entry getRHS(int rid) {
		switch (rid) {
		case 0:
			return _ln1_;
		case 1:
			return _ln2_;
		case 2:
			return _ln1_.next;
		case 3:
			return _ln2_.next;
		}
		return null;

	}

	public void _BLOCK_(List<SymbolicCandidate> cand) {
		toString = "";
		for (int i = 0; i < cand.size(); i++) {
			switch (cand.get(i).getType()) {
			case ASSIGNMENT:
				assignment((AssignmentCandidate) cand.get(i), (i == 0) ? null : (AssignmentCandidate) cand.get(i - 1),
						2);
				break;
			default:
				break;
			}

		}
	}
}
