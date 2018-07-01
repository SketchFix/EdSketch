/**
 * @author Lisa Jan 15, 2017 ConditionRequest.java 
 */
package edSketch.request.generator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import edSketch.executor.SketchExecutor;

@SuppressWarnings("rawtypes")
public class ArithmeticRequest extends AbstractRequest {

public ArithmeticRequest() {}
	public ArithmeticRequest(Object[] inputValues, String[] inputNames, Class<?> targetType) {
		super(inputValues, inputNames, targetType);
		// TODO Auto-generated constructor stub
	}

	private int relation = -1;
	private String[] ops = { "+ ", " - ", " * ", " / ", " % " };
	private Set<Class> set = new HashSet<Class>(Arrays.asList(int.class, Integer.class, double.class, Double.class,
			Long.class, long.class, Character.class, char.class));

	public Object query(Class candClass, String[] names, Object[] vals, int count, int fieldDeref, boolean hasNull) {
		if (vals.length != 2)
			return 0;
		if (!set.contains(vals[0].getClass()))
			return 0;
		if (relation == -1)
			relation = SketchExecutor.choose(ops.length - 1);
		return fetchVal(vals);
	}

	private double fetchVal(Object[] vals) {
		double lhs_v = 0, rhs_v = 0;
		if (vals[0].getClass().equals(Character.class) ||vals[0].getClass().equals(char.class))
			lhs_v = (double) ((char) vals[0]);
		else if (vals[0].getClass().equals(Integer.class) || vals[0].getClass().equals(int.class) )
			lhs_v = (int) vals[0];
		else
			lhs_v = (double) vals[0];
		if (vals[1].getClass().equals(Character.class) || vals[1].getClass().equals(char.class))
			rhs_v = (double) ((char) vals[1]);
		else if (vals[1].getClass().equals(Integer.class) || vals[1].getClass().equals(int.class))
			rhs_v = (int) vals[1];
		else
			rhs_v = (double) vals[1];
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
		return relation < 0 ? "" : ops[relation] + " ";
	}

	public void reset() {
		relation = -1;
	}

	@Override
	public Object invoke() {
		if (inputValues.size() != 2)
			return 0;
		if (!set.contains(targetType))
			return 0;
		if (relation == -1)
			relation = SketchExecutor.choose(ops.length - 1);
		double res = fetchVal(inputValues.toArray(new Object[inputValues.size()]));
		if (targetType.equals(int.class) || targetType.equals(Integer.class))
			return (int) res;
		return res;
	}
}
