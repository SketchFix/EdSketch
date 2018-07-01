/**
 * @author Lisa Dec 12, 2016 SketchExecutor.java 
 */
package edSketch.executor;

public abstract class SketchAbstractExecutor {

	public abstract boolean  incrementCounter();

	public abstract int choose(int _max);

	public abstract void backtrack();
	
	public abstract void initialize(int id);

	public abstract int getCounterPointer();
}
