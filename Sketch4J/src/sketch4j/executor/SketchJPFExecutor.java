/**
 * @author Lisa Dec 12, 2016 SketchJPFExecutor.java 
 */
package sketch4j.executor;

import gov.nasa.jpf.jvm.Verify;

public class SketchJPFExecutor extends SketchAbstractExecutor {


	@Override
	public boolean incrementCounter() {
		return Verify.incrementCounter(0)==0? false: true;
	}

	@Override
	public int choose(int _max) {

		return Verify.getInt(0, _max);
	}

	@Override
	public void backtrack() {
		Verify.ignoreIf(true);
	}

	@Override
	public void initialize(int id) {
		Verify.resetCounter(id);
		
	}

	@Override
	public int getCounterPointer() {
		return Verify.getCounter(0);
	}

}
