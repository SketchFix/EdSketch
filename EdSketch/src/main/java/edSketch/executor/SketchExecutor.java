/**
 * @author Lisa Dec 12, 2016 SketchExecutor.java 
 */
package edSketch.executor;

public class SketchExecutor {
	private static SketchAbstractExecutor exec = new SketchJuziExecutor();
	
	@SuppressWarnings("deprecation")
	public static void setType(ExecutorType type) {
		switch (type) {
		case JPF:
			exec = new SketchJPFExecutor();
			break;
		case JUZI:
			exec = new SketchJuziExecutor();
			break;
		}
	}

	public static boolean incrementCounter() {
		return exec.incrementCounter();
	}

	public static int choose(int _max) {
		return exec.choose(_max);
	}

	public static void backtrack() {
		exec.backtrack();
	}

	public static void initialize(int id) {
		exec.initialize(id);
	}

	public static int getCounterPointer() {
		return exec.getCounterPointer();
	}
}
