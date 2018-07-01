/**
 * @author Lisa Jan 15, 2017 ConditionRequest.java 
 */
package edSketch.request.generator;

import java.util.ArrayList;
import java.util.List;

import edSketch.generator.expression.SketchConditionGenerator;
import edSketch.generator.statement.StmtType;

@SuppressWarnings("rawtypes")
public class ConditionRequest extends AbstractRequest {
	public ConditionRequest() {}
	public ConditionRequest(Object[] inputValues, String[] inputNames, Class<?> targetType) {
		super(inputValues, inputNames, targetType);
	}
	public ConditionRequest(Object[] inputValues, String[] inputNames) {
		super(inputValues, inputNames);
	}
	public Object query(Class candClass, String[] names, Object[] vals, int count, int fieldDeref, boolean hasNull) {
		if (hasInit) {
			candidates = exprGenerator.updateSJCandidates(candClass, vals);
			return sketches.next(candidates);
		}
		initCandidates(candClass, names, vals, fieldDeref, hasNull);
		sketches = new SketchConditionGenerator();
		hasInit = true;
		List<StmtType> conds = new ArrayList<StmtType>();
		for (int i = 0; i < count; i++)
			conds.add(StmtType.CONDITION);
		sketches.setSketch(conds);
		return sketches.next(candidates);
	}

	@Override
	public Object invoke() {
		// TODO Auto-generated method stub
		return null;
	}
}
