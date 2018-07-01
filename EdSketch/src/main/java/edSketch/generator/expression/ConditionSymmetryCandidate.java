/**
 * @author Lisa Nov 10, 2016 InterStmtPruning.java 
 */
package edSketch.generator.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import edSketch.executor.SketchExecutor;
import edSketch.frontend.exprGenerator.SJValueCandidate;
import edSketch.generator.statement.StmtType;

/**
 * A single condition candidate, that can be combined to generate condition,
 * e.g., a==b && b==c.
 * 
 * @author lisahua
 *
 */
public class ConditionSymmetryCandidate extends ConditionCandidate {
	private int constant = -1;
	private int lhs = -1;
	private int rhs = -1;
	private int relation = -1;
	private String[] ops = { " == ", " != ", ">", "<", "<=", ">=" };
	private SJValueCandidate[] candidates = null;
	@SuppressWarnings("rawtypes")
	private Set<Class> set = new HashSet<Class>(Arrays.asList(int.class, Integer.class, double.class, Double.class,
			Long.class, long.class, Character.class, char.class));

	public ConditionSymmetryCandidate() {
		type = StmtType.CONDITION;
	}

	public Boolean next(SJValueCandidate[] vals) {
		candidates = vals;
		if (candidates == null || candidates.length == 0)
			return false;
		if (constant == -1) {
			constant = SketchExecutor.choose(2);
			switch (constant) {
			case 1:
				return false;
			case 2:
				return true;
			case 0:
				break;
			}
		}
		if (constant == 1)
			return false;
		else if (constant == 2)
			return true;
		return construct();
	}

	private boolean construct() {
		if (relation == -1) {
			lhs = SketchExecutor.choose(candidates.length - 1);
			rhs = SketchExecutor.choose(candidates.length - 1);
			if (set.contains(candidates[0].getCandClass()))
				relation = SketchExecutor.choose(ops.length - 1);
			else
				relation = SketchExecutor.choose(1);
		}
		boolean res = fetchBoolVal();
		// System.out.println(toString()+" "+res);
		return res;
	}
	
	private boolean fetchBoolVal() {
		if (candidates[lhs].isNPE() || candidates[rhs].isNPE() || lhs >= rhs)
			SketchExecutor.backtrack();
		// System.out.println(toString());
		if (set.contains(candidates[0].getCandClass())) {
			try {
			boolean res  = fetchDoubleVal();
			return res;
			} catch (Exception e) {
				return relation == 0 ? candidates[lhs].getValue() == candidates[rhs].getValue()
						: candidates[lhs].getValue() != candidates[rhs].getValue();
			}
		} else {
			return relation == 0 ? candidates[lhs].getValue() == candidates[rhs].getValue()
					: candidates[lhs].getValue() != candidates[rhs].getValue();
		}

	}

	private boolean fetchDoubleVal() {
		double lhs_v = 0, rhs_v = 0;
		Object o1 = candidates[lhs].getValue();
		Object o2 = candidates[rhs].getValue();
		if (o1.getClass().equals(Character.class))
			lhs_v = (double) ((char) o1);
		else if (o1.getClass().equals(Integer.class))
			lhs_v = (double) ((int) o1);
		else if (o1.getClass().equals(Long.class))
			lhs_v = (double) ((long) o1);
		else
			lhs_v = (double) o1;
		if (o2.getClass().equals(Character.class))
			rhs_v = (double) ((char) o2);
		else if (o2.getClass().equals(Integer.class))
			rhs_v = (double) ((int) o2);
		else if (o2.getClass().equals(Long.class))
			rhs_v = (double) ((long) o2);
		else
			rhs_v = (double) o2;
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
		return lhs == -1 ? (constant == -1 ? "" : (constant == 2 ? "true" : "false"))
				: candidates[lhs].getName() + ops[relation] + candidates[rhs].getName();
	}

	public void reset() {
		constant = -1;
		lhs = -1;
		rhs = -1;
		relation = -1;
	}

}