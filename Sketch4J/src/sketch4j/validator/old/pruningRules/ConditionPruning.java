/**
 * @author Lisa Nov 13, 2016 ConditionPruning.java 
 */
package sketch4j.validator.old.pruningRules;

import gov.nasa.jpf.jvm.Verify;

public class ConditionPruning<T> extends StmtPruningStrategy<T> {

	private int relation = -1;
	private int constant = -1;
	private String[] ops = { " == ", " != " };
	private final StmtPruningStrategy<T> left;
	private final StmtPruningStrategy<T> right;
	public ConditionPruning(String[] names) {
		super(names);
//		 strategy = new CondGreedyPrune<T>(names);
		left = new LazyInitPrune<T>(names);
//		right = new CondLazyInitPrune<T>(names);
		right = new CondGreedyPrune<T>(names);
	}

	public String toString() {
		if (lhs != -1 && rhs != -1)
			return names[lhs] + ops[relation] + names[rhs];
		else
			return constant == 1 ? String.valueOf(true) : String.valueOf(false);
	}

	
	@Override
	public Object prune(T[] vals) {
		if (constant == -1) {
			constant = Verify.getInt(0, 2);
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
		if (relation == -1)
			relation = Verify.getInt(0, 1); // equal or not equal
		
		Object le = left.prune(vals);
		System.out.println("condition lhs "+left.getRhs());
		right.setLhs(left.getRhs());
		lhs = left.getRhs();
		Object re = right.prune(vals);
		rhs = right.getRhs();

		System.out.println("relation "+ops[relation]);
		if (relation == 0)
			return le == re;
		else
			return le != re;
	}

}
