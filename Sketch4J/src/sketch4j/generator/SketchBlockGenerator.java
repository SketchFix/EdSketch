/**
 * @author Lisa Nov 10, 2016 InterStmtPruning.java 
 */
package sketch4j.generator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import sketch4j.frontend.ast.SJValueCandidate;
import sketch4j.generator.statement.AssignmentCandidate;
import sketch4j.generator.statement.StmtType;

/**
 * I define it as Iterator to support next version without JPF. Right now since
 * we use JPF for backtrack, hasNext return true and next only return one
 * element.
 *
 */

public class SketchBlockGenerator extends SketchGenerator {

	private Map<SJValueCandidate, Queue<Integer>> candMap = new HashMap<SJValueCandidate, Queue<Integer>>();
	private SketchCandidate cand = new SketchCandidate();
	private SJValueCandidate[] origin;

	public void setInitVals(SJValueCandidate[] vals) {
		origin = vals;
		parseVar(vals);
		// cand.setCandMap(candMap);
	}

	private void parseVar(SJValueCandidate[] vals) {
		for (int i = 0; i < vals.length; i++) {
			SJValueCandidate val = vals[i];
			// if (val.isNPE())
			// continue;
			Queue<Integer> sameExpr = candMap.get(val);
			if (sameExpr == null) {
				sameExpr = new LinkedList<Integer>();
				candMap.put(val, sameExpr);
			}
			sameExpr.add(i);
		}
	}

	public Object next() {
		return cand;
	}

	public void setSketch(List<StmtType> sketchType, int varLen) {
		cand = new SketchCandidate();
		cand.setList(sketchType, varLen);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (cand == null)
			return "";
		for (SymbolicCandidate oneline : cand.getList()) {
			AssignmentCandidate ac = (AssignmentCandidate) oneline;
			if (ac.getLHS() > -1 && ac.getRHS() > -1)
				sb.append("-- " + origin[(ac.getLHS() ) % origin.length] + " = "
						+ origin[(ac.getRHS() ) % origin.length] + ";\n");
		}
		return sb.toString();
	}
}