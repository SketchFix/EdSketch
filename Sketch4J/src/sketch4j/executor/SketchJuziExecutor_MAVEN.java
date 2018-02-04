/**
 * @author Lisa Dec 12, 2016 SketchJuziExecotor.java 
 */
package sketch4j.executor;

import juzi.Explorer;
import juzi.Explorer_MAVEN;

public class SketchJuziExecutor_MAVEN extends SketchAbstractExecutor {
	public SketchJuziExecutor_MAVEN() {
		Explorer_MAVEN.initialize();
	}

	@Override
	public boolean incrementCounter() {
		return Explorer_MAVEN.incrementCounter();
	}

	@Override
	public int choose(int _max) {
		return Explorer_MAVEN.choose(_max);
	}

	@Override
	public void backtrack() {
		Explorer_MAVEN.backtrack();
	}

	@Override
	public void initialize(int id) {
		Explorer_MAVEN.initialize();

	}

	public int getCounterPointer() {
		return Explorer_MAVEN.getCounterPointer();
	}
}
