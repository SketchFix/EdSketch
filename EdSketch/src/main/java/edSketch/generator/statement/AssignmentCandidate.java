/**
 * @author Lisa Nov 26, 2016 StmtCandidate.java 
 */
package edSketch.generator.statement;

import java.util.ArrayList;
import java.util.List;

import edSketch.executor.SketchExecutor;

public class AssignmentCandidate extends SymbolicCandidate {
	private List<Integer> lhsGroup = new ArrayList<Integer>();
	private List<Integer> rhsGroup = new ArrayList<Integer>();
	private int lhs = -1;
	private int rhs = -1;

	public AssignmentCandidate() {
		type = StmtType.ASSIGNMENT;
	}

	public int getJPFRHS() {
		if (rhs == -1) {
			rhs = rhsGroup.get(SketchExecutor.choose(rhsGroup.size() - 1));
			// if (SketchRequest.isJUZI()) rhs = rhs/2;
			// rhs = rhsGroup.get(Verify.getInt(0, rhsGroup.size() - 1));
			// System.out.println("rhs "+ toString());
		}
		return rhs;
	}

	public int getJPFLHS() {
		if (lhs == -1) {
			lhs = lhsGroup.get(SketchExecutor.choose(lhsGroup.size() - 1));
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
