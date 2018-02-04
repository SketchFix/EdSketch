package test.sketch4j.example.linkedList;

import java.util.List;

import sketch4j.generator.SketchRequest;
import sketch4j.generator.SymbolicCandidate;
import sketch4j.generator.statement.AssignmentCandidate;

public class LinkedList_hasLoop extends LinkedList {
	private final static int ITER_BOUND = 20;

	public LinkedList_hasLoop(int[] arr) {
		super(arr);
	}

	public String toString() {
		String res = "";
		for (Entry e = head; e != null; e = e.next)
			res += e.val + " ";
		return res;
	}

	public Entry getHead() {
		return head;
	}

	public boolean hasLoop() {
		Entry ln1, ln2;
		if (head.equals(head.next))
			return false;
		ln1 = head;
		ln2 = head;

		int count = 0;
		_ln1_ = ln1;
		_ln2_ = ln2;
		
		
		while (true) {
			if (count++ > ITER_BOUND) {
//				System.out.println("****count exceed"+toString);
				SketchRequest.backtrack();
			}
			boolean cond1 = SketchRequest.queryCondition(ln1.getClass(), new String[] { "ln1", "ln2"},
					new Entry[] { _ln1_, _ln2_}, 1, 1, true, 0);
			if (cond1)
//			if (_ln1_.next==null)
				return false;
			else {
				 _ln1_ = _ln1_.next;
//				_BLOCK_(SketchRequest.queryBlock(_ln1_.getClass(), new String[] { "ln1", "ln2" },
//						new Entry[] { _ln1_, _ln2_ }, 1, 2, 0));
			}
			if (_ln2_.next==null || _ln2_.next.next==null)
				return false;
			else {
				_ln2_ = _ln2_.next.next;
//				_BLOCK_(SketchRequest.queryBlock(_ln1_.getClass(), new String[] { "ln1", "ln2" },
//						new Entry[] { _ln1_, _ln2_ }, 1, 2, 1));				
			}
//			boolean cond2 = SketchRequest.queryCondition(ln1.getClass(), new String[] { "ln1", "ln2"},
//					new Entry[] { _ln1_, _ln2_}, 1, 1, true, 1);
			if (_ln1_ == _ln2_)
//			if (cond2)
				return true;
		}
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
		case 4:
			_ln1_.next.next = rhs;
			break;
		case 5:
			_ln2_.next.next = rhs;
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
		case 4:
			return _ln1_.next.next;
		case 5:
			return _ln2_.next.next;
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