/**
 * @author Lisa Nov 26, 2016 SymbolicCandidate.java 
 */
package edSketch.generator.statement;

public abstract class SymbolicCandidate {
	protected StmtType type;

	public StmtType getType() {
		return type;
	}

	public void setType(StmtType type) {
		this.type = type;
	}

	public abstract void reset();
}
