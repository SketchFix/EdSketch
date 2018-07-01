package edSketch.example.linkedList;

import java.util.Arrays;
import java.util.List;

import com.github.javaparser.ast.Node;

import edSketch.executor.SketchExecutor;
import edSketch.generator.statement.AssignmentCandidate;
import edSketch.generator.statement.SymbolicCandidate;
import edSketch.request.SketchFix;
import edSketch.request.generator.BlockRequest;

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
		Entry ln4 = null;
		iter_count = 0;
		_ln1_ = ln1;
		_ln2_ = ln2;
		_ln3_ = ln3;
		_ln4_ = ln4;
		// boolean cond = SketchRequest.queryCondition(ln1.getClass(), new
		// String[] { "ln1", "ln2", "ln3","ln4" },
		// new Entry[] { ln1, ln2, ln3,ln4 }, 1, 1, true, 0);
		// while (cond) {
		while (_ln2_ != null) {
			if (iter_count++ > ITER_BOUND) {
				SketchExecutor.backtrack();
			}
			// _ln1_.next = _ln3_;
			_BLOCK_((BlockRequest) SketchFix.BLOCK(new Entry[] { _ln1_, _ln2_, _ln3_, _ln4_ }, 0, new String[] { "ln1", "ln2", "ln3", "ln4" }, Node.class).invoke());
					// _ln3_ = _ln1_;

			// _ln1_ = _ln2_;
			// _BLOCK_(SketchRequest.queryBlock(ln1.getClass(), new String[] {
			// "ln1", "ln2", "ln3" },
			// new Entry[] { _ln1_, _ln2_, _ln3_ }, 1, 1, 1));
			// _ln2_ = _ln2_.next;
			// cond = SketchRequest.queryCondition(ln1.getClass(), new String[]
			// { "ln1", "ln2", "ln3","ln4" },
			// new Entry[] { ln1, ln2, ln3,ln4 }, 1, 1, true, 0);
		}
		// _ln1_.next = _ln3_;
		// head = _ln1_;
		return this;
	}

	private void assignment(BlockRequest request, int id) {
		AssignmentCandidate assign = (AssignmentCandidate) request.getSymbolicCandidates().get(id);
		AssignmentCandidate prev = (id == 0) ? null : (AssignmentCandidate) request.getSymbolicCandidates().get(id - 1);
		int rid = assign.getJPFRHS();
		int lid = assign.getJPFLHS();
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

	private Entry getRHS(int rid) {
		switch (rid) {
		case 0:
			return _ln1_;
		case 1:
			return _ln2_;
		case 2:
			return _ln3_;
		case 3:
			return _ln4_;
		case 4:
			return _ln1_.next;
		case 5:
			return _ln2_.next;
		case 6:
			return _ln3_.next;
		case 7:
			return _ln4_.next;
		case 8:
			return null;
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
		System.out.println(request.toString());
	}
}
