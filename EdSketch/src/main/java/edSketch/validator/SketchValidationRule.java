/**
 * @author Lisa Nov 26, 2016 SketchValidationRule.java 
 */
package edSketch.validator;

import java.util.List;

import edSketch.frontend.exprGenerator.SJValueCandidate;

public interface SketchValidationRule {

	public boolean notValid(List<Object> list);

	public void setCandidates(SJValueCandidate[] candidates) ;
}
