/**
 * @author Lisa Nov 26, 2016 StmtCandidate.java 
 */
package sketch4j.generator.statement;

import java.util.ArrayList;
import java.util.List;

import sketch4j.generator.SketchRequest;
import sketch4j.generator.SymbolicCandidate;

public class AssignmentCandidate extends SymbolicCandidate {
	private List<Integer> lhsGroup = new ArrayList<Integer>();
	private List<Integer> rhsGroup = new ArrayList<Integer>();

	public AssignmentCandidate() {
	}

	public AssignmentCandidate(StmtType type, int varLen) {
		super(type, varLen);
	}

	private int lhs = -1;
	private int rhs = -1;

	public int getJPFRHS() {
		if (rhs == -1) {
			rhs = rhsGroup.get(SketchRequest.choose(rhsGroup.size() - 1));
//			if (SketchRequest.isJUZI()) rhs = rhs/2;
			// rhs = rhsGroup.get(Verify.getInt(0, rhsGroup.size() - 1));
			// System.out.println("rhs "+ toString());
		}
		return rhs;
	}

	public int getJPFLHS() {
		if (lhs == -1) {
			lhs = lhsGroup.get(SketchRequest.choose(lhsGroup.size() - 1));
			// lhs = lhsGroup.get(Verify.getInt(0, lhsGroup.size() - 1));
			// System.out.println("lhs "+ toString());
		}
		return lhs;
	}

	public int getLHS() {
		return lhs;
	}

	public int getRHS() {
		return rhs;
	}

	public boolean isLhsDeref(int lhs) {
		return Math.abs(lhs - rhs) < varLen;
	}

	public void setLHSGroup(List<Integer> idList) {
		// System.out.println("lhs group "+ idList);
		lhsGroup = idList;

	}

	public void setRHSGroup(List<Integer> idList) {
		// System.out.println("rhs group "+ idList);
		rhsGroup = idList;
	}

	public String toString() {
		return "lhs " + lhs + " rhs " + rhs;
	}

	@Override
	public void reset() {
		lhs = -1;
		rhs = -1;
	}
}
