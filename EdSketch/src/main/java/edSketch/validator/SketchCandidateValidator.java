/**
 * @author Lisa Nov 26, 2016 SketchCandidateValidator.java 
 */
package edSketch.validator;

import java.util.List;

import edSketch.frontend.exprGenerator.SJValueCandidate;

public class SketchCandidateValidator implements SketchValidator {
	private InterAssignValidator inter = new InterAssignValidator();
	private IntraAssignValidator intra = new IntraAssignValidator();

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean notValid(List input) {
		if (intra.notValid(input)) {
//			System.out.println("***intra prune"+input[1]+input[2]);
			return true;
		}
		if (input.get(1)!=null) {
			if (inter.notValid(input)) {
				return true;
			}
		}
		return false;
	}

	public void setCandidates(SJValueCandidate[] candidates) {
		intra.setCandidates(candidates);
		inter.setCandidates(candidates);
	}
	
	
}
