/**
 * @author Lisa Dec 2, 2016 SJExpression.java 
 */
package edSketch.frontend.exprGenerator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class SJExpressionGenerator extends HoleGenerator {
	private List<SJExpression> expressions = new ArrayList<SJExpression>();
	private List<SJValueCandidate> lastQuery;
	private int varLen;

	public void addTypeVals(Class type, String[] names, Object[] vals) {
		addTypeVals(type, names, vals, 1);
		varLen = vals.length;
	}

	public void addTypeVals(Class type, String[] names, Object[] vals, int fieldDeref) {
		expressions.clear();
		SJExpression exp = new SJExpression(type, fieldDeref);
		exp.setInitVal(names, vals);
		expressions.add(exp);
		varLen = vals.length;
	}

	public SJValueCandidate[] getSJCandidates(Class output, boolean hasNull) {
		SJValueCandidate nullCand = null;
		if (output.equals(Integer.class) || output.equals(int.class) || output.equals(Double.class)
				|| output.equals(double.class))
			nullCand = new SJValueCandidate(output, -1, new ArrayList<Field>(), "0", 0, false);
		else
			nullCand = new SJValueCandidate(output, -1, new ArrayList<Field>(), "null", null, false);
		lastQuery = new ArrayList<SJValueCandidate>();
		for (SJExpression exp : expressions) {
			if (exp != null) {
				lastQuery.addAll(exp.getSJCandidates(output));
				// break;
			}
		}
		if (hasNull && !output.equals(boolean.class) && !output.equals(Boolean.class))
			lastQuery.add(nullCand);
		if (output.equals(Boolean.class) || output.equals(boolean.class)) {
			lastQuery.add(new SJValueCandidate(Boolean.class, -1, null, "false", false, false));
			lastQuery.add(new SJValueCandidate(Boolean.class,-1, null, "true", true, false));
		}
		System.out.println("test getSJCandidates " + lastQuery.size() + " " + lastQuery);
		return lastQuery.toArray(new SJValueCandidate[lastQuery.size()]);
	}

	public SJValueCandidate[] updateSJCandidates(Class output, Object[] vals) {
		if (lastQuery == null)
			return new SJValueCandidate[0];
		for (SJValueCandidate cand : lastQuery) {
			if (cand == null || cand.getBaseID() == -1)
				continue;
			if (cand.getBaseID() < vals.length)
				cand.getUpdatedValue(vals[cand.getBaseID()]);
		}
		return lastQuery.toArray(new SJValueCandidate[lastQuery.size()]);
	}

	public int getVarLen() {
		return varLen;
	}
}
