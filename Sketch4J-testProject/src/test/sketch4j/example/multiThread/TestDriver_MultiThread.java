/**
 * @author Lisa Dec 1, 2016 TestDriver.java 
 */
package test.sketch4j.example.multiThread;

import sketch4j.generator.SketchRequest;
import test.sketch4j.example.TestDriver;

/**
 * While this can be replaced with JUNIT, I use main for simplicity.
 * 
 * @author lisahua
 *
 */
public class TestDriver_MultiThread extends TestDriver {
	protected List sketch;

	protected boolean checkEq(List x, List y) {
		if (x == null && y == null)
			return true;
		Node xh = x.header, yh = y.header;
		for (; xh != null && yh != null; xh = xh.next, yh = yh.next) {
			if (xh.elem != yh.elem) {
				return false;
			}
		}
		return xh == null && yh == null;
	}

	public String toString() {
		return sketch.toString();
	}

	private boolean testHelper(int[] arr) {
		sketch = new List_sketch(arr);
		List expect = new List_expect(arr);
		if (!sketch.acyclic() || !expect.acyclic())
			return false;
		sketch.distributedSort();
		expect.distributedSort();
		return checkEq(sketch, expect) && sketch.ascendent();
	}

	@Override
	public boolean test() {
//		if (!testHelper(new int[] { 1, 7, 8, 5, 3, 2 }))
//			return false;
		if (!testHelper(new int[] { 3, 2, 1 }))
			return false;
//		if (!testHelper(new int[] { 1, 2, 3 }))
//			return false;
		return true;
	}
	public static void main(String[] a) {
		TestDriver driver = new TestDriver_MultiThread();
		try {
			if (!driver.test())
				throw new RuntimeException("****TEST FAILURE");
		} catch (NullPointerException npe) {
			//npe.printStackTrace();
			//System.out.println( "*****BACKTRACKING: null pointer exception");
			SketchRequest.backtrack();
		} catch (RuntimeException re) {
		//	System.out.println( re.getMessage());
			SketchRequest.backtrack();
		}
	//	System.out.println("****ALL TESTS PASSED!  Time: " + nf.format((System.nanoTime() - startTime) * 1.0 / Math.pow(10, 9)));
		throw new RuntimeException("****FOUND FIRST SOLUTION");
	}
}
