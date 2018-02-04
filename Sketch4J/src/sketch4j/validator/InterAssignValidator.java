/**
 * @author Lisa Nov 10, 2016 InterStmtPruning.java 
 */
package sketch4j.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InterAssignValidator implements SketchValidator {

	private List<SketchValidationRule> list = new ArrayList<SketchValidationRule>();

	public InterAssignValidator() {
//		String[] values = Sketch4JConfigFileParser.getInterAssignRules();
//		if (values == null) {
			list.add(new SameVarLHS());
			// list.add(new NullDereference(cur, prev));
			list.add(new IndependentLHS_RHS());
			list.add(new IndependentRHS());
			list.add(new SwapLHS_RHS());
			list.add(new SameVarDeref());
//		} else {
//			for (String s : values) {
//				if (s.equals(Sketch4JConfigFileParser.same_lhs))
//					list.add(new SameVarLHS());
//				else if (s.equals(Sketch4JConfigFileParser.independent_order))
//					list.add(new IndependentLHS_RHS());
//				else if (s.equals(Sketch4JConfigFileParser.same_rhs))
//					list.add(new IndependentRHS());
//				else if (s.equals(Sketch4JConfigFileParser.null_deref))
//					list.add(new NullDereference());
//				else if (s.equals(Sketch4JConfigFileParser.swap_lhs_rhs))
//					list.add(new SwapLHS_RHS());
//			}
//		}
	}

	@Override
	public boolean notValid(int[] var) {
//		System.out.println("pruning rules "+list.size());
		for (SketchValidationRule rule : list) {
			if (rule.notValid(var)) {
//				System.out.println("***prune "+var[1]+" "+var[2]+" " + rule.getClass().getName());
				return true;
			}
		}
		return false;
	}

}

abstract class InterAssignRule implements SketchValidationRule {

}

class SameVarLHS extends InterAssignRule {

	@Override
	public boolean notValid(int[] var) {
		return var[1] == var[3];
	}
}

class NullDereference extends InterAssignRule {
	// TODO
	@Override
	public boolean notValid(int[] var) {
		return false;
	}
}

class IndependentLHS_RHS extends InterAssignRule {

	@Override
	public boolean notValid(int[] var) {
		Set<Integer> cur = new HashSet<Integer>(Arrays.asList(var[1] >= var[0] ? var[1] - var[0] : var[1],
				var[2] >= var[0] ? var[2] - var[0] : var[2]));
		for (Integer i : new HashSet<Integer>(Arrays.asList(var[3] >= var[0] ? var[3] - var[0] : var[3],
				var[4] >= var[0] ? var[4] - var[0] : var[4])))
			if (cur.contains(i))
				return false;
		// independent, so enforce first lhs smaller than second
		if (var[3] > var[1]) {
			// System.out.println("Independent prune \n --" + prev.toString() +
			// "\n --" + cur.toString());
			return true;
		}
		return false;
	}

}

class IndependentRHS extends InterAssignRule {

	@Override
	public boolean notValid(int[] var) {
		if (var[2] == var[4] && var[3] > var[1]) {
			return true;
		}
		return false;
	}
}

class SwapLHS_RHS extends InterAssignRule {
	@Override
	public boolean notValid(int[] var) {
		if (var[1] == var[4] && var[2] == var[3]) {
			// System.out.println("IndependentRHS prune \n --" + prev.toString()
			// + "\n --" + cur.toString());
			return true;
		}
		return false;
	}
}

class SameVarDeref extends InterAssignRule {

	@Override
	public boolean notValid(int[] var) {
		if (var[3] - var[1] == var[0] && var[4] - var[2] == var[0])
			return true;
		return false;
	}
	
//	boolean canPuune(Assignment curr, Assignment prev) {
////		if (rule1(curr, prev)) return true;
////		if (rule2(curr, prev)) return true;
//		
//		Expression c_l = curr.lhs;
//		Expression c_r = curr.rhs;
//		Expression p_l = prev.lhs;
//		Expression p_r = prev.rhs;
//		
//		if (p_l.isVar() && p_r.isVar()) {
//			if (sameVar(p_l_var(), c_l)) return true;
//			if (sameVarDeref(p_l.var(), p_r.var(), c_l, c_r)) return true;
//		}
//	}
//	
//	boolean sameVarDeref(Var vl, Var vr, Expr el, Expr er) {
//		if (el.isFieldDeref(vl) && er.isFieldDeref(vr)) return true;
//		return false;
//	}

}