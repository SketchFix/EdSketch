/**
 * @author Lisa Nov 10, 2016 InterStmtPruning.java 
 */
package sketch4j.validator;

import java.util.ArrayList;
import java.util.List;

public class IntraAssignValidator implements SketchValidator {

	private List<SketchValidationRule> list = new ArrayList<SketchValidationRule>();

	public IntraAssignValidator() {
		list.add(new SameLHS_RHS());
	}

	@Override
	public boolean notValid(int[] var) {
		for (SketchValidationRule rule : list)
			if (rule.notValid(var))
				return true;
		return false;
	}

}

class SameLHS_RHS implements SketchValidationRule {
	@Override
	public boolean notValid(int[] var) {
		return var[1] == var[2];
	}

}
