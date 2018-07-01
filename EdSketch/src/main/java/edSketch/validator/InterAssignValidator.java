/**
 * @author Lisa Nov 10, 2016 InterStmtPruning.java 
 */
package edSketch.validator;

import java.util.ArrayList;
import java.util.List;

import edSketch.frontend.exprGenerator.SJValueCandidate;
import edSketch.generator.statement.AssignmentCandidate;

public class InterAssignValidator implements SketchValidator {

	private List<SketchValidationRule> list = new ArrayList<SketchValidationRule>();

	public InterAssignValidator() {
		String[] values = null;
		// Sketch4JConfigFileParser.getInterAssignRules();
		if (values == null) {
			list.add(new SameVarLHS());
			list.add(new SwapLHS_RHS());
			list.add(new SameLHSRHS());
		}

		// else {
		// for (String s : values) {
		// if (s.equals(Sketch4JConfigFileParser.same_lhs))
		// list.add(new SameVarLHS());
		// else if (s.equals(Sketch4JConfigFileParser.swap_lhs_rhs))
		// list.add(new SwapLHS_RHS());
		// else if (s.equals(Sketch4JConfigFileParser.same_rhs))
		// list.add(new SameLHSRHS());
		// }
		// }
	}

	@Override
	public boolean notValid(List<Object> input) {
		for (SketchValidationRule rule : list)
			if (rule.notValid(input)) {
				return true;
			}
		return false;
	}

	public void setCandidates(SJValueCandidate[] candidates) {
		for (SketchValidationRule rule : list)
			rule.setCandidates(candidates);
	}

}

abstract class InterAssignRule implements SketchValidationRule {
	protected SJValueCandidate[] candidates;

	public void setCandidates(SJValueCandidate[] candidates) {
		this.candidates = candidates;
	}
}

class SameVarLHS extends InterAssignRule {

	@Override
	public boolean notValid(List<Object> var) {
		SJValueCandidate lhs1 = candidates[((AssignmentCandidate) var.get(0)).getLHS()];
		SJValueCandidate lhs2 = candidates[((AssignmentCandidate) var.get(1)).getLHS()];
		return lhs1.isVar() && lhs2.isVar() && lhs1.equals(lhs2);
	}
}

// class NullDereference extends InterAssignRule {
// // TODO
// @Override
// public boolean notValid(List<Object> var) {
// return false;
// }
// }

// class IndependentLHS_RHS extends InterAssignRule {
//
// @Override
// public boolean notValid(List<Object> var) {
//// Set<Integer> cur = new HashSet<Integer>(Arrays.asList(var[1] >= var[0] ?
// var[1] - var[0] : var[1],
//// var[2] >= var[0] ? var[2] - var[0] : var[2]));
//// for (Integer i : new HashSet<Integer>(Arrays.asList(var[3] >= var[0] ?
// var[3] - var[0] : var[3],
//// var[4] >= var[0] ? var[4] - var[0] : var[4])))
//// if (cur.contains(i))
//// return false;
//// // independent, so enforce first lhs smaller than second
//// if (var[3] > var[1]) {
// // System.out.println("Independent prune \n --" + prev.toString() +
// // "\n --" + cur.toString());
//// return true;
//// }
// return false;
// }
//
// }

// class IndependentRHS extends InterAssignRule {
//
// @Override
// public boolean notValid(List<Object> var) {
// if (var[2] == var[4] && var[3] > var[1]) {
// return true;
// }
// return false;
// }
// }

class SwapLHS_RHS extends InterAssignRule {
	@Override
	public boolean notValid(List<Object> var) {
		AssignmentCandidate cur = (AssignmentCandidate) var.get(0);
		AssignmentCandidate prev = (AssignmentCandidate) var.get(1);
		if (candidates[cur.getRHS()].equals(candidates[prev.getLHS()])
				&& candidates[cur.getLHS()].equals(candidates[prev.getRHS()])) {
			// System.out.println("IndependentRHS prune \n --" + prev.toString()
			// + "\n --" + cur.toString());
			return true;
		}
		return false;
	}
}

class SameLHSRHS extends InterAssignRule {

	@Override
	public boolean notValid(List<Object> var) {
		// if (var[3] - var[1] == var[0] && var[4] - var[2] == var[0])
		AssignmentCandidate cur = (AssignmentCandidate) var.get(0);
		AssignmentCandidate prev = (AssignmentCandidate) var.get(1);
		return candidates[cur.getLHS()].equals(candidates[prev.getLHS()])
				&& candidates[cur.getRHS()].equals(candidates[prev.getRHS()]);
	}

	// boolean canPuune(Assignment curr, Assignment prev) {
	//// if (rule1(curr, prev)) return true;
	//// if (rule2(curr, prev)) return true;
	//
	// Expression c_l = curr.lhs;
	// Expression c_r = curr.rhs;
	// Expression p_l = prev.lhs;
	// Expression p_r = prev.rhs;
	//
	// if (p_l.isVar() && p_r.isVar()) {
	// if (sameVar(p_l_var(), c_l)) return true;
	// if (sameVarDeref(p_l.var(), p_r.var(), c_l, c_r)) return true;
	// }
	// }
	//
	// boolean sameVarDeref(Var vl, Var vr, Expr el, Expr er) {
	// if (el.isFieldDeref(vl) && er.isFieldDeref(vr)) return true;
	// return false;
	// }

}