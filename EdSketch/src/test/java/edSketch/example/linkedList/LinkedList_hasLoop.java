package edSketch.example.linkedList;

import java.util.Arrays;
import java.util.List;

import com.github.javaparser.ast.Node;

import edSketch.executor.SketchExecutor;
import edSketch.generator.statement.AssignmentCandidate;
import edSketch.generator.statement.SymbolicCandidate;
import edSketch.request.SketchFix;
import edSketch.request.generator.BlockRequest;

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
		if (head.next.equals(head))
			return false;
		ln1 = head;
		ln2 = head;

		int count = 0;
		_ln1_ = ln1;
		_ln2_ = ln2;

		while (true) {
			if (count++ > ITER_BOUND) {
				System.out.println("****count exceed" + toString);
				SketchExecutor.backtrack();
			}
			// boolean cond1 = SketchRequest.queryCondition(ln1.getClass(), new
			// String[] { "ln1", "ln2"},
			// new Entry[] { _ln1_, _ln2_}, 1, 1, true, 0);
			// if (cond1)
			if (_ln1_.next == null)
				return false;
			else {
				_ln1_ = _ln1_.next;
				// _BLOCK_(SketchRequest.queryBlock(_ln1_.getClass(), new
				// String[] { "ln1", "ln2" },
				// new Entry[] { _ln1_, _ln2_ }, 1, 2, 0));
			}
			if (_ln2_.next == null || _ln2_.next.next == null)
				return false;
			else {
				_ln2_ = _ln2_.next.next;
				_BLOCK_((BlockRequest) SketchFix.BLOCK(new Entry[] { _ln1_, _ln2_ },0,
						new String[] { "ln1", "ln2" }, Node.class));
			}
			boolean cond2 =  (Boolean)SketchFix.COND(new Entry[] { _ln1_, _ln2_ },0,
					new String[] { "ln1", "ln2" }, Node.class).invoke();
			// if (_ln1_ == _ln2_)
			if (cond2)
				return true;
		}
	}

	private void assignment(BlockRequest request, int id) {
		AssignmentCandidate assign = (AssignmentCandidate) request.getSymbolicCandidates().get(id);
		AssignmentCandidate prev = (id == 0) ? null : (AssignmentCandidate) request.getSymbolicCandidates().get(id - 1);

		int lid = assign.getJPFLHS();
		int rid = assign.getJPFRHS();
		if (request.notValid(Arrays.asList(assign, prev))) {
			SketchExecutor.backtrack();
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

	public void _BLOCK_(BlockRequest request) {
		List<SymbolicCandidate> list = request.getSymbolicCandidates();
		for (int i = 0; i < list.size(); i++) {
			switch (list.get(i).getType()) {
			case ASSIGNMENT:
				assignment(request, i);
				break;
			default:
				break;
			}

		}
	}
}