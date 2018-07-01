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
public abstract class TestDriver_JUZI extends TestDriver {
	private static final int SOLUTION_BOUND = 5;

	public TestDriver_JUZI() {
	}

	public TestDriver_JUZI(int[] arr) {
	}

	public abstract boolean test();

	public static void main(String[] a) {

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(10);
		SketchExecutor.setType(ExecutorType.JUZI);
		long startTime = System.currentTimeMillis();
		int count = 0;
		try {
			SketchFix.reset();
			do {
				try {
					 TestDriver_LinkedList_Reverse_Assign.main(a);
					// if (TestArithmeticOperator.test())
//					if (TestBooleanDefault.test())
						// if (TestOperator.test())
//					if (TestExpressionGenerator.test2())
//						System.out.println("Test Pass: " + Sketch4J.getString());
					// if (!TestArithmeticOperator.test())
//					else
//						SketchExecutor.backtrack();

				} catch (BacktrackException e) {
					// System.out.println( Sketch4J.getString());
				}
				// throw new RuntimeException("TEST PASS");
			} while (SketchExecutor.incrementCounter());
		} catch (RuntimeException e) {
			e.printStackTrace();
			System.out.println("****ALL TESTS PASSED!  Time: "
					+ nf.format((System.currentTimeMillis() - startTime) * 1.0 / Math.pow(10, 3)));
			System.out.println(SketchFix.getString());

		}

	}

}
