/**
 * @author Lisa Nov 10, 2016 InterStmtPruning.java 
 */
package sketch4j.validator.old.pruningRules;

public class InterStmtPruning<T> {
	protected StmtPruningStrategy<T> cur;
	protected StmtPruningStrategy<T> prev;
	
	public InterStmtPruning(StmtPruningStrategy<T> cur, StmtPruningStrategy<T> prev) {
		this.cur = cur;
		this.prev = prev;
	}

	public boolean prune() {
		return cur.lhsSameRhs() || new SameVarLHS<T>(cur, prev).prune() || new NullDereference<T>(cur, prev).prune();
	}
}

class SameVarLHS<T> extends InterStmtPruning<T> {

	public SameVarLHS(StmtPruningStrategy<T> cur, StmtPruningStrategy<T> prev) {
		super(cur, prev);
	}

	@Override
	public boolean prune() {
		return prev != null && cur != null && cur.lhsIsVar() && cur.getLhs() == prev.getLhs();
	}
}

class NullDereference<T> extends InterStmtPruning<T> {

	public NullDereference(StmtPruningStrategy<T> cur, StmtPruningStrategy<T> prev) {
		super(cur, prev);
	}

	@Override
	public boolean prune() {
		return prev != null && cur != null && prev.isLhsDeref(cur.lhs);
	}

}