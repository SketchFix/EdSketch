/**
 * @author Lisa Apr 20, 2016 BinaryTree.java 
 */
package test.sketch4j.example.binaryTree;

import java.util.List;

import sketch4j.generator.SketchRequest;
import sketch4j.generator.SymbolicCandidate;
import sketch4j.generator.statement.AssignmentCandidate;
import test.sketch4j.example.binaryTree.test.TestDriver_BinaryTree_Insert;

public class BinaryTree_insert extends BinaryTree_base {
	public boolean insert(int k) {
		Node y = null;
		Node x = root;

		int count = 0;
		_n1_ = x;
		_n2_ = y;
		boolean cond1 = SketchRequest.queryCondition(Node.class, new String[] { "x", "y" }, new Object[] { _n1_, _n2_ },
				1, 1, true, 0);
		while (cond1) {
			// while ((_n1_ != null)) {
			if (count++ > ITER_BOUND) {
				TestDriver_BinaryTree_Insert.pruneCounter++;
				SketchRequest.backtrack();
			}
			// _BLOCK_(SketchRequest.queryBlock(_n1_.getClass(), new String[] {
			// "x", "y" }, new Node[] { _n1_, _n2_ }, 1,
			// 2, 0));
			_n2_ = _n1_;

//			boolean cond2 = SketchRequest.queryCondition(int.class, new String[] { "x", "y", "k" },
//					new Object[] { _n1_, _n2_, k }, 1, 1, false, 1);
//			if (cond2) {
//				 if (_n1_.key == k) {
//				return false;
//			}
//			boolean cond3 = SketchRequest.queryCondition(int.class, new String[] { "x", "y", "k" },
//					new Object[] { _n1_, _n2_, k }, 1, 1, false, 1);
			boolean cond4 = SketchRequest.queryCondition(int.class, new String[] { "x", "y", "k" },
					new Object[] { _n1_, _n2_, k }, 1, 1, false, 1);
			 if ((_n1_ != null) && k < _n1_.key) {
//			if (_n1_ != null && cond3) {
				_n1_ = _n1_.left;
				// _BLOCK_(SketchRequest.queryBlock(_n1_.getClass(), new
				// String[] { "x", "y" }, new Node[] { _n1_, _n2_ }, 1,
				// 2, 0));
			} else if ((_n1_ != null) && cond4) {
//			 }	 else if ((_n1_ != null) && (k > _n1_.key)) {
				_n1_ = _n1_.right;
				// _BLOCK_(SketchRequest.queryBlock(_n1_.getClass(), new
				// String[] { "x", "y" }, new Node[] { _n1_, _n2_ }, 1,
				// 2, 0));
			}
			cond1 = SketchRequest.queryCondition(Node.class, new String[] { "x", "y" }, new Object[] { _n1_, _n2_ }, 1,
					1, true, 0);
		}

		_n1_ = new Node(k);
		boolean cond2 = SketchRequest.queryCondition(Node.class, new String[] { "x", "y" }, new Object[] { _n1_, _n2_ },
				1, 1, true, 2);
	
//		 if (_n2_ == null) {
		if (cond2) {
			root = _n1_;
		} else {
			boolean cond3 = SketchRequest.queryCondition(int.class, new String[] { "x", "y","k" }, new Object[] { _n1_, _n2_,k },
					1, 1, false, 3);
			if (cond3) {
//				 if (k < _n2_.key) {
				_n2_.left = _n1_;
				// _BLOCK_(SketchRequest.queryBlock(_n1_.getClass(), new
				// String[] { "x", "y" }, new Node[] { _n1_, _n2_ }, 1,
				// 2, 0));
			} else {
				_n2_.right = _n1_;
				// _BLOCK_(SketchRequest.queryBlock(_n1_.getClass(), new
				// String[] { "x", "y" }, new Node[] { _n1_, _n2_ },
				// 1, 2, 0));
			}
		}
		size += 1;
		return true;
	}

	private void assignment(int id, AssignmentCandidate assign, AssignmentCandidate prev, int varLen) {
		int lid = assign.getJPFLHS();
		int rid = assign.getJPFRHS();
		toString += id + "--" + SketchRequest.toString(assign) + "   ";

		int[] vids = null;
		if (prev == null)
			vids = new int[] { varLen, lid, rid };
		else
			vids = new int[] { varLen, lid, rid, prev.getLHS(), prev.getRHS() };
		if (SketchRequest.notValid(vids)) {
			TestDriver_BinaryTree_Insert.pruneCounter++;
			SketchRequest.backtrack();
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
		case 6:
			_n1_.left.left = rhs;
			break;
		case 7:
			_n1_.left.right = rhs;
			break;
		case 8:
			_n1_.right.left = rhs;
			break;
		case 9:
			_n1_.right.right = rhs;
			break;
		case 10:
			_n2_.left.left = rhs;
			break;
		case 11:
			_n2_.left.right = rhs;
			break;
		case 12:
			_n2_.right.left = rhs;
			break;
		case 13:
			_n2_.right.right = rhs;
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
		case 6:
			return _n1_.left.left;
		case 7:
			return _n1_.left.right;
		case 8:
			return _n1_.right.left;
		case 9:
			return _n1_.right.right;
		case 10:
			return _n2_.left.left;
		case 11:
			return _n2_.left.right;
		case 12:
			return _n2_.right.left;
		case 13:
			return _n2_.right.right;

		}
		return null;

	}

	public void _BLOCK_(List<SymbolicCandidate> cand) {
		toString = "";
		for (int i = 0; i < cand.size(); i++) {
			switch (cand.get(i).getType()) {
			case ASSIGNMENT:
				assignment(i, (AssignmentCandidate) cand.get(i),
						(i == 0) ? null : (AssignmentCandidate) cand.get(i - 1), 2);
				break;
			default:
				break;
			}

		}
		// System.out.println(toString);
	}
}
