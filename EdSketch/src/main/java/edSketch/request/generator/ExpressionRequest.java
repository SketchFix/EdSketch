/**
 * @author Lisa Jan 15, 2017 ConditionRequest.java 
 */
package edSketch.request.generator;

import java.util.List;

import edSketch.generator.expression.SketchExpressionGenerator;
@SuppressWarnings("rawtypes")
public class ExpressionRequest extends AbstractRequest {
	public ExpressionRequest(){}
	public ExpressionRequest(Object[] inputValues, String[] inputNames, Class<?> targetType) {
		super(inputValues, inputNames, targetType);
	}

	public Object query(Class candClass, String[] names, Object[] vals, int count, int fieldDeref, boolean hasNull) {
		if (hasInit) {
			candidates = exprGenerator.updateSJCandidates(candClass, vals);
			return sketches.next(candidates);
		}
		initCandidates(candClass, names, vals, fieldDeref, hasNull);
		sketches = new SketchExpressionGenerator();
		hasInit = true;
		return sketches.next(candidates);

	}

	@Override
	public Object invoke() {
		if (hasInit) {
			candidates = exprGenerator.updateSJCandidates(targetType, inputValues.toArray(new Object[inputValues.size()]));
			return sketches.next(candidates);
		}
		initCandidates(targetType, inputNames.toArray(new String[inputNames.size()]), inputValues.toArray(new Object[inputValues.size()]),0, true);
		sketches = new SketchExpressionGenerator();
		hasInit = true;
		return sketches.next(candidates);
	}

}
