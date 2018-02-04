/**
 * @author Lisa Nov 10, 2016 InterStmtPruning.java 
 */
package sketch4j.validator.old.pruningRules;

import gov.nasa.jpf.jvm.Verify;

/**
 * Naive way to explore all options without pruning
 *
 */
public class NaiveNoPrune<T> extends StmtPruningStrategy<T> {

	public NaiveNoPrune(String[] names) {
		super(names);
	}

	public Object prune(T[] vals) {
		if (rhs == -1) {
			rhs = Verify.getInt(0, len - 1);
		}
		return vals[rhs];
	}

}
