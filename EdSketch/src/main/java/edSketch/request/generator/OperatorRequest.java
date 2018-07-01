/**
 * @author Lisa Jan 15, 2017 ConditionRequest.java 
 */
package edSketch.request.generator;

import java.util.List;

import edSketch.executor.SketchExecutor;

@SuppressWarnings("rawtypes")
public class OperatorRequest extends AbstractRequest {

	public OperatorRequest() {}
	public OperatorRequest(Object[] inputValues, String[] inputNames, Class<?> targetType) {
		super(inputValues, inputNames, targetType);
	}

	private int relation = -1;
	private String[] ops = { " == ", " != ", ">", "<", "<=", ">=" };

	public Object query(Class candClass, String[] names, Object[] vals, int count, int fieldDeref, boolean hasNull) {
		if (vals.length != 2)
			return false;
		Class cz = vals[0].getClass();
		boolean isPrime = false;
		if (cz.equals(Character.class) || cz.equals(Integer.class) || cz.equals(Double.class))
			isPrime = true;
		if (relation == -1) {
			if (isPrime)
				relation = SketchExecutor.choose(ops.length - 1);
			else
				relation = SketchExecutor.choose(1);
		}
		if (isPrime)
			return fetchVal(vals);
		else
			return relation == 0 ? vals[0] == vals[1] : vals[0] != vals[1];
	}

	private boolean fetchVal(Object[] vals) {
		double lhs_v = 0, rhs_v = 0;
		if (vals[0].getClass().equals(Character.class))
			lhs_v = (double) ((char) vals[0]);
		else if (vals[0].getClass().equals(Integer.class))
			lhs_v = (double) ((int) vals[0]);
		else
			lhs_v = (double) vals[0];
		if (vals[1].getClass().equals(Character.class))
			rhs_v = (double) ((char) vals[1]);
		else if (vals[1].getClass().equals(Integer.class))
			rhs_v = (double) ((int) vals[1]);
		else
			rhs_v = (double) vals[1];

		switch (relation) {
		case 0:
			return lhs_v == rhs_v;
		case 1:
			return lhs_v != rhs_v;
		case 2:
			return lhs_v > rhs_v;
		case 3:
			return lhs_v < rhs_v;
		case 4:
			return lhs_v <= rhs_v;
		case 5:
			return lhs_v >= rhs_v;
		}
		return false;
	}

	public String toString() {
		return relation < 0 ? "" : ops[relation] + " ";
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
