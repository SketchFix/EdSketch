/**
 * @author Lisa Jan 15, 2017 ConditionRequest.java 
 */
package edSketch.request.generator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edSketch.executor.SketchExecutor;

@SuppressWarnings("rawtypes")
public class ArithmeticExpressionRequest extends AbstractRequest {
	public ArithmeticExpressionRequest(){}
	public ArithmeticExpressionRequest(Object[] inputValues, String[] inputNames, Class<?> targetType) {
		super(inputValues, inputNames, targetType);
	}

	private int relation = -1, lhs = -1, rhs = -1;
	private String[] ops = { "+ ", " - ", " * ", " / ", " % " };
	private Set<Class> set = new HashSet<Class>(Arrays.asList(int.class, Integer.class, double.class, Double.class,
			Long.class, long.class, Character.class, char.class));
	private String toString = "";


	public Object query(Class candClass, String[] names, Object[] vals, int count, int fieldDeref, boolean hasNull) {
		if (vals.length < 2 || !set.contains(vals[0].getClass()))
			return 0;
		if (!hasInit) {
			initCandidates(candClass, names, vals, fieldDeref, hasNull);
			hasInit = true;
		} else
			candidates = exprGenerator.updateSJCandidates(candClass, vals);

		if (relation == -1) {
			lhs = SketchExecutor.choose(candidates.length - 1);
			rhs = SketchExecutor.choose(candidates.length - 1);
			if (lhs >= rhs)
				SketchExecutor.backtrack();
			relation = SketchExecutor.choose(ops.length - 1);
		}
		toString = candidates[lhs] + ops[relation] + candidates[rhs];
		return fetchVal();
	}

	private double fetchVal() {
		double lhs_v = 0, rhs_v = 0;
		if (candidates[lhs].getClass().equals(Character.class))
			lhs_v = (double) ((char) candidates[lhs].getValue());
		else if (candidates[lhs].getClass().equals(Integer.class))
			lhs_v = (double) ((int) candidates[lhs].getValue());
		else
			lhs_v = (double) candidates[lhs].getValue();
		if (candidates[rhs].getClass().equals(Character.class))
			rhs_v = (double) ((char) candidates[rhs].getValue());
		else if (candidates[rhs].getClass().equals(Integer.class))
			rhs_v = (double) ((int) candidates[rhs].getValue());
		else
			rhs_v = (double) candidates[rhs].getValue();

		switch (relation) {
		case 0:
			return lhs_v + rhs_v;
		case 1:
			return lhs_v - rhs_v;
		case 2:
			return lhs_v * rhs_v;
		case 3:
			return lhs_v / rhs_v;
		case 4:
			return lhs_v % rhs_v;
		}
		return 0;
	}

	public String toString() {
		return toString;
	}

	public void reset() {
		relation = -1;
	}

	@Override
	public Object invoke() {
		// TODO Auto-generated method stub
		return null;
	}
}
