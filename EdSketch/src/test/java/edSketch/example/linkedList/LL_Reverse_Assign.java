package edSketch.example.linkedList;

import java.util.Arrays;
import java.util.List;

import com.github.javaparser.ast.Node;

import edSketch.executor.SketchExecutor;
import edSketch.generator.statement.AssignmentCandidate;
import edSketch.generator.statement.SymbolicCandidate;
import edSketch.request.SketchFix;
import edSketch.request.generator.BlockRequest;

public class LL_Reverse_Assign extends LinkedList {

	public static int iter_count = 0;
	private final static int ITER_BOUND = 11;

	public LL_Reverse_Assign(int[] arr) {
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
		while ((Boolean) SketchFix.COND(new Entry[] { head, _ln1_, _ln2_, _ln3_ },0,
				new String[] { "head", "ln1", "ln2", "ln3" }, Node.class).invoke()) {
			// while (_ln1_ != null) {
			if (iter_count++ > ITER_BOUND) {
				SketchExecutor.backtrack();
			}
			// _BLOCK_((BlockRequest) Sketch4J.BLOCK(ln1.getClass(), new
			// String[] { "head", "ln1", "ln2", "ln3" },
			// new Entry[] { head, _ln1_, _ln2_, _ln3_ }, 1, false, 4, 0));
			head = _ln1_;
			_ln1_ = head.next;
			head.next = _ln2_;
			_ln2_ = head;
		}
		return this;
	}

	private void _BLOCK_(BlockRequest request) {
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

	private void assignment(BlockRequest request, int id) {
		AssignmentCandidate assign = (AssignmentCandidate) request.getSymbolicCandidates().get(id);
		AssignmentCandidate prev = (id == 0) ? null : (AssignmentCandidate) request.getSymbolicCandidates().get(id - 1);
		int rid = assign.getJPFRHS();
		int lid = assign.getJPFLHS();
		if (request.notValid(Arrays.asList(assign, prev))) {
			SketchExecutor.backtrack();
		}
		Entry rhs = getRHS(rid);
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
