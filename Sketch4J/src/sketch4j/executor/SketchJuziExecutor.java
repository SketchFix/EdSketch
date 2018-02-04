/**
 * @author Lisa Dec 12, 2016 SketchJuziExecotor.java 
 */
package sketch4j.executor;

import juzi.Explorer;

public class SketchJuziExecutor extends SketchAbstractExecutor {
	public SketchJuziExecutor() {
		Explorer.initialize();
	}

	@Override
	public boolean incrementCounter() {
		return Explorer.incrementCounter();
	}

	@Override
	public int choose(int _max) {
		return Explorer.choose(_max);
	}

	@Override
	public void backtrack() {
		Explorer.backtrack();
	}

	@Override
	public void initialize(int id) {
		Explorer.initialize();

	}

	public int getCounterPointer() {
		return Explorer.getCounterPointer();
	}

}
