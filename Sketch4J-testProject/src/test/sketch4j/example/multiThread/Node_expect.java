package test.sketch4j.example.multiThread;

import java.util.List;

import sketch4j.generator.SketchRequest;
import sketch4j.generator.SymbolicCandidate;
import sketch4j.generator.statement.AssignmentCandidate;

public class Node_expect extends Node {

	public Node_expect(int val) {
		super(val);
	}

	synchronized boolean swapElem() {
		synchronized (next) {
			if (elem > next.elem) {
				int t = elem;
				elem = next.elem;
				next.elem = t;
				return true;
			}
		}
		return false;
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
		int rhs = getRHS(assign.getJPFRHS());
		switch (lid) {
		case 0:
			elem = rhs;
			break;
		case 1:
			_t_ = rhs;
			break;
		case 2:
			next.elem = rhs;
			break;
		}
	}

	private int getRHS(int rid) {
		switch (rid) {
		case 0:
			return elem;
		case 1:
			return _t_;
		case 2:
			return next.elem;
		}
		return 0;
	}

	public void _BLOCK_(List<SymbolicCandidate> cand) {
		toString = "";
		for (int i = 0; i < cand.size(); i++) {
			switch (cand.get(i).getType()) {
			case ASSIGNMENT:
				assignment((AssignmentCandidate) cand.get(i), (i == 0) ? null : (AssignmentCandidate) cand.get(i - 1),
						3);
				break;
			default:
				break;
			}
		}
	}

	public String toString() {
		return toString;
	}
}