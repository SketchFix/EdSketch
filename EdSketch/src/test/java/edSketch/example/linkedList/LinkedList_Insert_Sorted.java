package edSketch.example.linkedList;

import java.util.Arrays;
import java.util.List;

import com.github.javaparser.ast.Node;

import edSketch.executor.SketchExecutor;
import edSketch.generator.statement.AssignmentCandidate;
import edSketch.generator.statement.SymbolicCandidate;
import edSketch.request.SketchFix;
import edSketch.request.generator.BlockRequest;

public class LinkedList_Insert_Sorted extends LinkedList {

	private static int iter_count = 0;
	private final static int ITER_BOUND = 11;

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
			if (iter_count++ > ITER_BOUND) {
				SketchExecutor.backtrack();
			}
			if (_ln1_.next.val < val) {

				// _ln1_ = _ln1_.next;
				_BLOCK_((BlockRequest) SketchFix.BLOCK(new Entry[] { _ln1_, _ln2_ },0,
						new String[] { "ln1", "ln2" }, Node.class));
			} else
				break;
		}

		// _ln2_.setNext(_ln1_.getNext());
		_ln2_.next = _ln1_.next;
		_ln1_.next = _ln2_;
		// _BLOCK_(SketchRequest.queryBlock(_ln1_.getClass(), new String[] {
		// "ln1", "ln2" }, new Entry[] { _ln1_, _ln2_ },
		// 1, 1, 0));
		size++;
	}

	private void assignment(BlockRequest request, int id) {
		AssignmentCandidate assign = (AssignmentCandidate) request.getSymbolicCandidates().get(id);
		AssignmentCandidate prev = (id == 0) ? null : (AssignmentCandidate) request.getSymbolicCandidates().get(id - 1);
		int lid = assign.getJPFLHS();
		int rid = assign.getJPFRHS();

		if (request.notValid(Arrays.asList(assign, prev))) {
			SketchExecutor.backtrack();
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
		// System.out.println(toString);
	}
}
