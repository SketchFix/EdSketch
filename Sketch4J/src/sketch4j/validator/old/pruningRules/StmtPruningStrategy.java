/**
 * @author Lisa Nov 13, 2016 StmtPruningStrategy.java 
 */
package sketch4j.validator.old.pruningRules;

import gov.nasa.jpf.jvm.Verify;

public abstract class StmtPruningStrategy<T> {
	protected int lhs = -1;
	protected int rhs = -1;
	protected final String[] names;
	protected final int len;

	public StmtPruningStrategy(String[] names) {
		this.names = names;
		len = names.length;
		
	}

	public int lhs() {
		if (lhs == -1) {
			lhs = Verify.getInt(0, len - 2);
		}
		return lhs;
	}

	public abstract Object prune(T[] vals);

	public String toString() {
		if (lhs != -1 && rhs != -1)
			return names[lhs] + " = " + names[rhs] + ";";
		else if (lhs != -1)
			return names[lhs];
		else if (rhs != -1)
			return names[rhs];
		return "";
	}

	public boolean lhsSameRhs() {
		return lhs == rhs;
	}

	public int getLhs() {
		return lhs;
	}
	public void setLhs(int lhs) {
		this.lhs = lhs;
	}
	public int getRhs() {
		return rhs;
	}

	public boolean lhsIsVar() {
		return lhs < 4;
	}

	public boolean rhsIsNull() {
		return rhs == len;
	}

	public boolean isLhsDeref(int i) {
		return i == lhs + len / 2;
	}
}
