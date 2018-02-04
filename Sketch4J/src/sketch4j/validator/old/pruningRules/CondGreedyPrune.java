/**
 * @author Lisa Nov 10, 2016 InterStmtPruning.java 
 */
package sketch4j.validator.old.pruningRules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import gov.nasa.jpf.jvm.Verify;

/**
 * didn't consider same value in one iteration but different in another
 * iteration
 *
 */
public class CondGreedyPrune<T> extends StmtPruningStrategy<T> {

	public CondGreedyPrune(String[] names) {
		super(names);
	}

	@Override
	public T prune(T[] vals) {
		if (lhs == -1) {
			lhs = Verify.getInt(0, len - 2);
			System.out.println("condition prune lhs " + lhs);

		}
		if (rhs == -1) {
			ArrayList<Integer> al = new ArrayList<Integer>();
			Set<Object> hs = new HashSet<Object>();
			// FIXME: ensure set membership is "==" and not "equals" method
			for (int i = (lhs == -1 ? 0 : lhs + 1); i < len; i++) {
				if (hs.add(vals[i]))
					al.add(i);
			}
			rhs = al.get(Verify.getInt(0, al.size() - 1));
		}
		return vals[rhs];
	}

}
