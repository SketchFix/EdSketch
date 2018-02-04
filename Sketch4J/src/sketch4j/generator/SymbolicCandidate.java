/**
 * @author Lisa Nov 26, 2016 SymbolicCandidate.java 
 */
package sketch4j.generator;

import sketch4j.generator.statement.StmtType;

public abstract class SymbolicCandidate {
	protected StmtType type;
	protected int varLen;
	public SymbolicCandidate(){}
	public SymbolicCandidate(StmtType type, int varLen) {
		this.type = type;
		this.varLen = varLen;
	}


	public StmtType getType() {
		return type;
	}

	public int getVarLen() {
		return varLen;
	}
	public abstract void reset() ;
}
