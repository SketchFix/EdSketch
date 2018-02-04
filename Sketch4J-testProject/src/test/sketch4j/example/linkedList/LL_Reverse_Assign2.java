package test.sketch4j.example.linkedList;

import java.util.List;

import sketch4j.generator.SketchRequest;
import sketch4j.generator.SymbolicCandidate;
import sketch4j.generator.statement.AssignmentCandidate;
import test.sketch4j.example.linkedList.testDriver.TestDriver_LinkedList_Reverse_Assign;

public class LL_Reverse_Assign2 extends LinkedList {

	private static int iter_count = 0;
	private final static int ITER_BOUND = 11;

	public LL_Reverse_Assign2(int[] arr) {
		super(arr);
	}

	// translated program to sketch
	// operator hole is replaced with method call
	public LinkedList reverse() {
		if (head == null)
			return this;
		Entry ln1 = head;
		Entry ln2 = null;
		Entry ln3 = null;
		iter_count = 0;
		{
			_ln1_ = ln1;
			_ln2_ = ln2;
			_ln3_ = ln3;
		}
		boolean cond = SketchRequest.queryCondition(ln1.getClass(), new String[] { "head", "ln1", "ln2", "ln3" },
				new Entry[] { head, _ln1_, _ln2_, _ln3_ }, 1, 1, true, 0);
		while (cond) {
			// while (_ln1_ != null) {
			if (iter_count++ > ITER_BOUND) {
				SketchRequest.backtrack();
			}
//			_BLOCK_(SketchRequest.queryBlock(ln1.getClass(), new String[] { "head", "ln1", "ln2", "ln3" },
//					new Entry[] { head, _ln1_, _ln2_, _ln3_ }, 4, 1, 0));
			 head = _ln1_;
			 _ln1_ = head.next;
			 head.next = _ln2_;
			 _ln2_ = head;
			cond = SketchRequest.queryCondition(ln1.getClass(), new String[] { "head", "ln1", "ln2", "ln3" },
					new Entry[] { head, _ln1_, _ln2_, _ln3_ }, 1, 1, true, 0);
		}
		return this;
	}

	private void _BLOCK_(List<SymbolicCandidate> cand) {
		toString = "";
		for (int i = 0; i < cand.size(); i++) {
			switch (cand.get(i).getType()) {
			case ASSIGNMENT:
				assignment((AssignmentCandidate) cand.get(i), (i == 0) ? null : (AssignmentCandidate) cand.get(i - 1),
						1);
				break;
			default:
				break;
			}

		}
		// System.out.println(toString);
	}

	private void assignment(AssignmentCandidate assign, AssignmentCandidate prev, int varLen) {
		int rid = assign.getJPFRHS();
		int lid = assign.getJPFLHS();

		toString += SketchRequest.toString(assign) + "   ";
		int[] vids = null;
		if (prev == null)
			vids = new int[] { varLen, lid, rid };
		else
			vids = new int[] { varLen, lid, rid, prev.getLHS(), prev.getRHS() };
		if (SketchRequest.notValid(vids)) {
			SketchRequest.backtrack();
		}
		Entry rhs = getRHS(assign.getJPFRHS());
		switch (lid) {
		case 0:
			head = rhs;
			break;
		case 1:
			_ln1_ = rhs;
			break;
		case 2:
			_ln2_ = rhs;
			break;
		case 3:
			_ln3_ = rhs;
			break;
		case 4:
			head.next = rhs;
			break;
		case 5:
			_ln1_.next = rhs;
			break;
		case 6:
			_ln2_.next = rhs;
			break;
		case 7:
			_ln3_.next = rhs;
			break;
		}
	}

	private Entry getRHS(int rid) {
		switch (rid) {
		case 0:
			return head;
		case 1:
			return _ln1_;
		case 2:
			return _ln2_;
		case 3:
			return _ln3_;
		case 4:
			return head.next;
		case 5:
			return _ln1_.next;
		case 6:
			return _ln2_.next;
		case 7:
			return _ln3_.next;
		}
		return null;

	}
}
