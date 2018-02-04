/**
 * @author Lisa Dec 2, 2016 SJExpression.java 
 */
package sketch4j.frontend.ast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("rawtypes")
public class SJExpressionGenerator {
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
		SJValueCandidate nullCand = new SJValueCandidate(output, -1, new ArrayList<Field>(), "null", null, false);

		lastQuery = new ArrayList<SJValueCandidate>();
		for (SJExpression exp : expressions)
			lastQuery.addAll(exp.getSJCandidates(output));
		if (hasNull)
			lastQuery.add(nullCand);

		return lastQuery.toArray(new SJValueCandidate[lastQuery.size()]);
	}

	public SJValueCandidate[] updateSJCandidates(Class output, Object[] vals) {
		if (lastQuery == null)
			return new SJValueCandidate[0];
		for (SJValueCandidate cand : lastQuery) {
			if (cand.getBaseID() == -1)
				continue;
			cand.getUpdatedValue(vals[cand.getBaseID()]);
		}
		return lastQuery.toArray(new SJValueCandidate[lastQuery.size()]);
	}

	public int getVarLen() {
		return varLen;
	}
}
