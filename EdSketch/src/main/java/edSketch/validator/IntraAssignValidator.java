/**
 * @author Lisa Nov 10, 2016 InterStmtPruning.java 
 */
package edSketch.validator;

import java.util.ArrayList;
import java.util.List;

import edSketch.frontend.exprGenerator.SJValueCandidate;
import edSketch.generator.statement.AssignmentCandidate;

public class IntraAssignValidator implements SketchValidator {

	private List<SketchValidationRule> list = new ArrayList<SketchValidationRule>();


	public IntraAssignValidator() {
		list.add(new SameLHS_RHS());
	}
	
	@Override
	public boolean notValid(List<Object> var) {
		for (SketchValidationRule rule : list)
			if (rule.notValid(var))
				return true;
		return false;
	}

	public void setCandidates(SJValueCandidate[] candidates) {
		for (SketchValidationRule rule: list)
			rule.setCandidates(candidates);
		
	}

}

class SameLHS_RHS implements SketchValidationRule {

	protected SJValueCandidate[] candidates;
	public void setCandidates(SJValueCandidate[] candidates) {
		this.candidates = candidates;
	}
	@Override
	public boolean notValid(List<Object> var) {
//		return var[1] == var[2];
		 AssignmentCandidate assign = ( AssignmentCandidate) var.get(0);
		return candidates[assign.getLHS()].equals(candidates[assign.getRHS()]);
	}
}
