/**
 * @author Lisa Nov 10, 2016 InterStmtPruning.java 
 */
package edSketch.generator.expression;

import java.util.ArrayList;
import java.util.List;

import edSketch.executor.SketchExecutor;
import edSketch.frontend.exprGenerator.SJValueCandidate;
import edSketch.generator.statement.StmtType;
import edSketch.request.generator.SketchGenerator;

/**
 * A combination of multiple condition expressions
 * 
 * @author lisahua
 *
 */
public class SketchConditionGenerator extends SketchGenerator {

	private List<ConditionCandidate> predicates = new ArrayList<ConditionCandidate>();
	private int constant = -1;

	@SuppressWarnings("unused")
	public void setSketch(List<StmtType> sketchType) {
		for (StmtType type : sketchType) {
//			predicates.add(new ConditionCandidate());
			predicates.add(new ConditionSymmetryCandidate());
		}
	}

	public Boolean next(SJValueCandidate[] vals) {
		List<Boolean> bools = new ArrayList<Boolean>();
		for (ConditionCandidate cc : predicates)
			bools.add(cc.next(vals));
		if (predicates.size() < 2)
			return bools.get(0);
		if (constant == -1) {
			constant = SketchExecutor.choose(1);
		}
		switch (constant) {
		case 0:
			return bools.get(0) && bools.get(1);
		case 1:
			return bools.get(0) || bools.get(1);
		}
		return false;
	}

	@Override
	public void reset() {
		for (ConditionCandidate cc : predicates)
			cc.reset();
	}

	public String toString() {
		String str = predicates.size() == 0 ? "" : constant == 0 ? " && " : " || ";
		for (ConditionCandidate cc : predicates)
			str += cc.toString();
		return str;
	}

}