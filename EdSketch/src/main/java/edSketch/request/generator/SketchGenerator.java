/**
 * @author Lisa Nov 10, 2016  
 */
package edSketch.request.generator;

import java.util.List;

import edSketch.frontend.exprGenerator.SJValueCandidate;
import edSketch.generator.statement.StmtType;

public abstract class SketchGenerator {
	protected SJValueCandidate[] candidates;

	public abstract Object next(SJValueCandidate[] candidates);

	public abstract void reset();

	public abstract void setSketch(List<StmtType> sketchType);

	public void setInitVals(SJValueCandidate[] candidates) {
		this.candidates = candidates;
	}
	

}