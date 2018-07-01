/**
 * @author Lisa Dec 1, 2016 TestDriver.java 
 */
package edSketch.example;

import java.text.NumberFormat;

import edSketch.example.linkedList.testDriver.TestDriver_LinkedList_Reverse_Assign;
import edSketch.executor.ExecutorType;
import edSketch.executor.SketchExecutor;
import edSketch.executor.stateless.BacktrackException;
import edSketch.request.SketchFix;

/**
 * While this can be replaced with JUNIT, I use main for simplicity.
 * 
 * @author lisahua
 *
 */
public abstract class TestDriver_JUZI_multiSolution extends TestDriver {
	private static final int SOLUTION_BOUND = 5;

	public TestDriver_JUZI_multiSolution() {
	}

	public TestDriver_JUZI_multiSolution(int[] arr) {
	}

	public abstract boolean test();

	public static void main(String[] a) {

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(10);
		SketchExecutor.setType(ExecutorType.JUZI);
		long startTime = System.currentTimeMillis();
		int count = 0;

		// try {
		do {
			SketchFix.reset();
			try {
				TestDriver_LinkedList_Reverse_Assign.main(a);
			} catch (BacktrackException e) {
				// System.out.println("**********Solution " + (++count) + " " +
				// TestDriver.getString());
			} catch (RuntimeException e) {
				// e.printStackTrace();
				System.out.println(SketchFix.getString());
			}
		} while (SketchExecutor.incrementCounter());

	}

}
