/**
 * @author Lisa Nov 26, 2016 SketchCandidateValidator.java 
 */
package sketch4j.validator;

public class SketchCandidateValidator implements SketchValidator {
	private InterAssignValidator inter = new InterAssignValidator();
	private IntraAssignValidator intra = new IntraAssignValidator();
//	private SJValueCandidate[] candidates;

	@Override
	public boolean notValid(int[] input) {
		if (intra.notValid(input)) {
//			System.out.println("***intra prune"+input[1]+input[2]);
			return true;
		}
		if (input.length > 3) {
			if (inter.notValid(input)) {
				return true;
			}
		}
		return false;
	}

//	public void setCandidates(SJValueCandidate[] candidates) {
//		this.candidates = candidates;
//	}
}
