/**
 * @author Lisa Nov 10, 2016 InterStmtPruning.java 
 */
package sketch4j.generator.expression;

//import gov.nasa.jpf.jvm.Verify;
import sketch4j.executor.SketchExecutor;
import sketch4j.frontend.ast.SJValueCandidate;

/**
 * I define it as Iterator to support next version without JPF. Right now since
 * we use JPF for backtrack, hasNext return true and next only return one
 * element.
 *
 */
@SuppressWarnings("rawtypes")
public class SketchConditionGenerator_WithoutGroup extends SketchConditionGenerator {

	public SketchConditionGenerator_WithoutGroup(SketchExecutor exec) {
		super(exec);
	}

	private int relation = -1;
	private int constant = -1;
	private String[] ops = { " == ", " != ", ">", "<", "<=", ">=" };
	private int lhs = -1;
	private int rhs = -1;
	private SJValueCandidate[] candidates = null;

	

	public boolean next(SJValueCandidate[] vals) {
		candidates = vals;
		if (constant == -1) {
			constant = exec.choose(2);
			switch (constant) {
			case 0:
				break;
			case 1:
				return true;
			case 2:
				return false;
			}
		}
		if (constant == 1)
			return true;
		else if (constant == 2)
			return false;
		return construct();
	}

	private boolean construct() {
		if (relation == -1) {
			if (candidates != null && candidates.length > 0 && isPrime(candidates[0].getCandClass())) {
				primeConstruct();
			} else {
				nonPrimeConstruct();
			}
//			String str = toString();

//			if (str.contains("y<z")) {
//				System.out.println(SketchRequest.getString());
//			}
		}
System.out.println(toString());
		switch (relation) {
		case 0:
			return candidates[lhs].getValue() == candidates[rhs].getValue();
		case 1:
			return candidates[lhs].getValue() != candidates[rhs].getValue();
		}

		Class type = candidates[0].getCandClass();
		if (type.equals(int.class) || type.equals(Integer.class)) {
			int lhs_v = (int) candidates[lhs].getValue();
			int rhs_v = (int) candidates[rhs].getValue();
			switch (relation) {
			case 2:
				return lhs_v > rhs_v;
			case 3:
				return lhs_v < rhs_v;
			case 4:
				return lhs_v <= rhs_v;
			case 5:
				return lhs_v >= rhs_v;
			}
		} else if (type.equals(double.class) || type.equals(Double.class)) {
			double lhs_v = (double) candidates[lhs].getValue();
			double rhs_v = (double) candidates[rhs].getValue();
			switch (relation) {
			case 2:
				return lhs_v < rhs_v;
			case 3:
				return lhs_v > rhs_v;
			case 4:
				return lhs_v <= rhs_v;
			case 5:
				return lhs_v >= rhs_v;
			}
		}
		return false;
	}

	private boolean isPrime(Class type) {
		// System.out.println(type.toString()+" "+type.equals(Integer.class));
		return type.equals(int.class) || type.equals(double.class) || type.equals(Integer.class)
				|| type.equals(Double.class);
	}

	private void primeConstruct() {
		int len = candidates.length;
		lhs = exec.choose(len);
		rhs = exec.choose(len);
		relation = exec.choose(ops.length);

	}

	private void nonPrimeConstruct() {
		int len = candidates.length;
		lhs = exec.choose(len);
		rhs = exec.choose(len);
		relation = exec.choose(1);
	}

	public String toString() {
		if (relation == -1 || candidates == null)
			return "";
		try {
			return "  CONDITION " + candidates[lhs].getName() + ops[relation] + candidates[rhs].getName();
			
		} catch (Exception e) {
		}
		return "";
		}

	public void reset() {
		relation = -1;
		constant = -1;
		lhs = -1;
		lhs = -1;
	}

}