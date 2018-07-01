/**
 * @author Lisa Dec 1, 2016 TestDriver.java 
 */
package edSketch.example;

import java.text.NumberFormat;

import edSketch.example.linkedList.testDriver.TestDriver_LinkedList_Reverse_Assign;
import edSketch.executor.ExecutorType;
import edSketch.executor.SketchExecutor;
import edSketch.request.SketchFix;

/**
 * While this can be replaced with JUNIT, I use main for simplicity.
 * 
 * @author lisahua
 *
 */
public abstract class TestDriver {

	public TestDriver() {
	}

	public TestDriver(int[] arr) {
	}

	public abstract boolean test();

	public static void main(String[] a) {
		
		TestDriver driver = new TestDriver_LinkedList_Reverse_Assign();
		 SketchExecutor.setType(ExecutorType.JPF);
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(10);
		long startTime = System.currentTimeMillis();
		try {
			if (!driver.test())
				throw new RuntimeException("****TEST FAILURE");
		} catch (NullPointerException npe) {
//			npe.printStackTrace();
//			System.out.println(driver.toString() + "*****BACKTRACKING: null pointer exception");
//			Sketch4J.countBacktrack();
		} catch (RuntimeException re) {
//			System.out.println(driver.toString() + re.getMessage());
			 SketchExecutor.backtrack();
		}
		System.out.println( driver.toString());
		System.out.println("****ALL TESTS PASSED!  Time: " + nf.format((System.currentTimeMillis() - startTime) * 1.0 / Math.pow(10, 3)));
		throw new RuntimeException("****FOUND FIRST SOLUTION");

	}
	public static String getString() {
		return SketchFix.getString();
	}
}
