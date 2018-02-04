/**
 * @author Lisa Jan 24, 2017 FindMedian.java 
 */
package test.sketch4j.example.median;

import java.util.List;

import sketch4j.generator.SketchRequest;
import sketch4j.generator.SymbolicCandidate;
import sketch4j.generator.statement.AssignmentCandidate;
import test.sketch4j.example.TestDriver_JUZI_multiSolution;

public class FindMedian_Sketch {
	private String toString;
	private int _x_, _y_, _z_, _m_;

	public int mid(int x, int y, int z) {
		int m = z;
		{
			_x_ = x;
			_y_ = y;
			_z_ = z;
			_m_ = m;
		}
//		boolean cond1 = SketchRequest.queryCondition(int.class,  new String[] { "x", "y", "z", "m" },
//				new Object[] { _x_, _y_, _z_, _m_ }, 1, 1,false, 0);
//		if (cond1) {
		if (_y_ < _z_) {
//			boolean cond2 = SketchRequest.queryCondition(int.class,  new String[] { "x", "y", "z", "m" },
//					new Object[] { _x_, _y_, _z_, _m_ }, 1, 1, false, 0);
//			boolean cond3 = SketchRequest.queryCondition(int.class,  new String[] { "x", "y", "z", "m" },
//					new Object[] { _x_, _y_, _z_, _m_ }, 1, 1, false, 1);
			
//			if (cond2) {
			if (_x_ < _y_) {
				_m_ = _y_;
//				_BLOCK_(SketchRequest.queryBlock(int.class, new String[] { "x", "y", "z", "m" },
//						new Object[] { _x_, _y_, _z_, _m_ }, 1, 1, 1));
//			} else if (cond3) {
			} else if (_x_ < _z_) {
				 _m_ = _x_;
//				
//				_BLOCK_(SketchRequest.queryBlock(int.class, new String[] { "x", "y", "z", "m" },
//						new Object[] { _x_, _y_, _z_, _m_ }, 1, 1, 0));
			}
		} else {
//			boolean cond2 = SketchRequest.queryCondition(int.class,  new String[] { "x", "y", "z", "m" },
//					new Object[] { _x_, _y_, _z_, _m_ }, 1, 1, false, 0);
			boolean cond3 = SketchRequest.queryCondition(int.class,  new String[] { "x", "y", "z", "m" },
					new Object[] { _x_, _y_, _z_, _m_ }, 1, 1,false, 0);
//			if (cond2) {
			if (_x_ > _y_) {
//				_BLOCK_(SketchRequest.queryBlock(int.class, new String[] { "x", "y", "z", "m" },
//						new Object[] { _x_, _y_, _z_, _m_ }, 1, 1, 0));
				_m_ = _y_;
			} else if (cond3) {
//			} else if (_x_ > _z_) {
				_m_ = _x_;
//				_BLOCK_(SketchRequest.queryBlock(int.class, new String[] { "x", "y", "z", "m" },
//						new Object[] { _x_, _y_, _z_, _m_ }, 1, 1, 0));
			}
		}
		return _m_;
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
			TestDriver_JUZI_multiSolution.pruneCounter++;
			SketchRequest.backtrack();

		}
		int rhs = getRHS(assign.getJPFRHS());
		switch (lid) {
		case 0:
			_x_ = rhs;
			break;
		case 1:
			_y_ = rhs;
			break;
		case 2:
			_z_ = rhs;
			break;
		case 3:
			_m_ = rhs;
			break;
		}

	}

	private int getRHS(int rid) {
		switch (rid) {
		case 0:
			return _x_;
		case 1:
			return _y_;
		case 2:
			return _z_;
		case 3:
			return _m_;
		}
		return 0;
	}

	public void _BLOCK_(List<SymbolicCandidate> cand) {
		toString = "";
		for (int i = 0; i < cand.size(); i++) {
			switch (cand.get(i).getType()) {
			case ASSIGNMENT:
				assignment((AssignmentCandidate) cand.get(i), (i == 0) ? null : (AssignmentCandidate) cand.get(i - 1),
						4);
				break;
			default:
				break;
			}
		}
//		 System.out.println(toString);
	}

	public String toString() {
		return toString;
	}
}
