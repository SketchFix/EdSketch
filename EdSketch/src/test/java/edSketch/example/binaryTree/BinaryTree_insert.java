/**
 * @author Lisa Apr 20, 2016 BinaryTree.java 
 */
package edSketch.example.binaryTree;

import java.util.Arrays;
import java.util.List;

import edSketch.executor.SketchExecutor;
import edSketch.generator.statement.AssignmentCandidate;
import edSketch.generator.statement.SymbolicCandidate;
import edSketch.request.SketchFix;
import edSketch.request.generator.BlockRequest;

public class BinaryTree_insert extends BinaryTree_base {
	public boolean insert(int k) {
		Node y = null;
		Node x = root;

		int count = 0;
		_n1_ = x;
		_n2_ = y;

		while ((_n1_ != null)) {
			if (count++ > ITER_BOUND)
				SketchExecutor.backtrack();
			// _BLOCK_(SketchRequest.queryBlock(_n1_.getClass(), new String[] {
			// "x", "y" }, new Node[] { _n1_, _n2_ }, 1,
			// 1, 0));
			_n2_ = _n1_;

			boolean cond1 = (Boolean) SketchFix.COND(new Node[] { _n1_, _n2_ }, 0, new String[] { "x", "y" })
					.invoke();
			if (cond1) {
				// if (_n1_.key == k) {
				return false;
			}

			if ((_n1_ != null) && k < _n1_.key) {
				_n1_ = _n1_.left;
				// _BLOCK_(SketchRequest.queryBlock(_n1_.getClass(), new
				// String[] { "x", "y" }, new Node[] { _n1_, _n2_ }, 1,
				// 1, 3));
			}

			else if ((_n1_ != null) && (k > _n1_.key)) {
				_n1_ = _n1_.right;
				// _BLOCK_(SketchRequest.queryBlock(_n1_.getClass(), new
				// String[] { "x", "y" }, new Node[] { _n1_, _n2_ }, 1,
				// 1, 1));
			}
		}

		_n1_ = new Node(k);
		boolean cond2 = (Boolean) SketchFix.COND(new Object[] { _n1_, _n2_ }, 0, new String[] { "x", "y" })
				.invoke();
		// if (_n2_ == null) {
		if (cond2) {
			root = _n1_;
		} else {

			if (k < _n2_.key) {
				_n2_.left = _n1_;
				// _BLOCK_(SketchRequest.queryBlock(_n1_.getClass(), new
				// String[] { "x", "y" }, new Node[] { _n1_, _n2_ }, 1,
				// 1, 4));
			} else {
				_n2_.right = _n1_;
				// _BLOCK_(SketchRequest.queryBlock(_n1_.getClass(), new
				// String[] { "x", "y" }, new Node[] { _n1_, _n2_ }, 1,
				// 1, 2));
			}
		}
		size += 1;
		return true;
	}

	private void assignment(BlockRequest request, int i) {
		AssignmentCandidate assign = (AssignmentCandidate) request.getSymbolicCandidates().get(i);
		AssignmentCandidate prev = (i == 0) ? null : (AssignmentCandidate) request.getSymbolicCandidates().get(i - 1);
		int lid = assign.getJPFLHS();
		int rid = assign.getJPFRHS();
		// toString += Sketch4J.getString() + " ";
		if (request.notValid(Arrays.asList(assign, prev))) {
			SketchExecutor.backtrack();
		}
		Node rhs = getRHS(assign.getJPFRHS());
		switch (lid) {
		case 0:
			_n1_ = rhs;
			break;
		case 1:
			_n2_ = rhs;
			break;
		case 2:
			_n1_.left = rhs;
			break;
		case 3:
			_n1_.right = rhs;
			break;
		case 4:
			_n2_.left = rhs;
			break;
		case 5:
			_n2_.right = rhs;
			break;
		}
	}

	private Node getRHS(int rid) {
		switch (rid) {
		case 0:
			return _n1_;
		case 1:
			return _n2_;
		case 2:
			return _n1_.left;
		case 3:
			return _n1_.right;
		case 4:
			return _n2_.left;
		case 5:
			return _n2_.right;
		}
		return null;

	}

	public void _BLOCK_(BlockRequest request) {
		toString = "";
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
