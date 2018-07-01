/**
 * @author Lisa Dec 12, 2016 SketchJPFExecutor.java 
 */
package edSketch.executor;

//import gov.nasa.jpf.jvm.Verify;

/**
 * I move the EdSketch-JPF to sub-module to isolate the basic EdSketch. This is
 * just a stub. Don't use it.
 * @author lisahua
 *
 */
@Deprecated
public class SketchJPFExecutor extends SketchAbstractExecutor {

	@Override
	public boolean incrementCounter() {
		// return Verify.incrementCounter(0)==0? false: true;
		throw new UnsupportedOperationException();
	}

	@Override
	public int choose(int _max) {
		throw new UnsupportedOperationException();
		// return Verify.getInt(0, _max);
	}

	@Override
	public void backtrack() {
		// Verify.ignoreIf(true);
		throw new UnsupportedOperationException();
	}

	@Override
	public void initialize(int id) {
		// Verify.resetCounter(id);
		throw new UnsupportedOperationException();
	}

	@Override
	public int getCounterPointer() {
		// return Verify.getCounter(0);
		throw new UnsupportedOperationException();
	}

}
