/**
 * @author Lisa Dec 12, 2016 SketchExecutor.java 
 */
package sketch4j.executor;

public  class SketchExecutor {
	SketchAbstractExecutor exec;

	public SketchExecutor(ExecutorType type) {
		switch (type) {
		case JPF:
			exec = new SketchJPFExecutor();
			break;
		case JUZI:
//			exec = new SketchJuziExecutor_MAVEN();
			exec = new SketchJuziExecutor();
			break;
		}
		exec = new SketchJuziExecutor();
//		exec = new SketchJuziExecutor_MAVEN();
	}

	public boolean incrementCounter() {
		return exec.incrementCounter();
	}

	public int choose(int _max) {
		return exec.choose(_max);
	}

	public void backtrack() {
		exec.backtrack();
	}
	
	public void initialize(int id) {
		exec.initialize(id);
	}
	public int getCounterPointer() {
		return exec.getCounterPointer();
	}
}
