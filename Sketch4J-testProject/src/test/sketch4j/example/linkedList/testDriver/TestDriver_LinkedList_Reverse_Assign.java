/**
 * @author Lisa Dec 1, 2016 TestDriver.java 
 */
package test.sketch4j.example.linkedList.testDriver;

import sketch4j.generator.SketchRequest;
import test.sketch4j.example.TestDriver;
import test.sketch4j.example.linkedList.LL_Reverse_Assign2;
import test.sketch4j.example.linkedList.LinkedList;
import test.sketch4j.example.linkedList.LinkedList_expected;

/**
 * While this can be replaced with JUNIT, I use main for simplicity.
 * 
 * @author lisahua
 *
 */
public class TestDriver_LinkedList_Reverse_Assign extends TestDriver_LinkedList {
	LL_Reverse_Assign2 sketch;
	public static int counter = 0;
	public static int pruneCounter = 0;

	private boolean testEmptyList() {
		return testHelper(new int[] {});
	}

	private boolean testOneNode() {
		return testHelper(new int[] { 1 });
	}

	private boolean testTwoNode() {
		return testHelper(new int[] { 1, 2 });
	}

	private boolean testThreeNode() {
		return testHelper(new int[] { 1, 2, 3 });
	}

	private boolean testMoreNode() {
		return testHelper(new int[] { 1, 2, 3, 4 });
	}

	@Override
	public boolean test() {
		if (!testMoreNode())
			return false;
		if (!testThreeNode())
			return false;
		if (!testTwoNode())
			return false;
		if (!testOneNode())
			return false;
		if (!testEmptyList())
			return false;

		return true;
	}

	public boolean test2() {
		if (!testEmptyList())
			return false;
		if (!testOneNode())
			return false;
		if (!testTwoNode())
			return false;
		if (!testThreeNode())
			return false;
		if (!testMoreNode())
			return false;

		return true;
	}

	@Override
	protected boolean testHelper(int[] arr) {
		sketch = new LL_Reverse_Assign2(arr);
		sketch.reverse();
		LinkedList expect = new LinkedList_expected(arr);
		expect.reverse();
		return checkEq(expect, sketch);
	}

	public static void main(String[] a) {
		TestDriver_LinkedList_Reverse_Assign driver= new TestDriver_LinkedList_Reverse_Assign();
		counter++;
		try {
			if (!driver.test())
				throw new RuntimeException("****TEST FAILURE");
		} catch (NullPointerException npe) {
			// npe.printStackTrace();
			// System.out.println( "*****BACKTRACKING: null pointer exception");
			SketchRequest.backtrack();
		} catch (RuntimeException re) {
			// System.out.println( re.getMessage());
			SketchRequest.backtrack();
		}
		// System.out.println("****ALL TESTS PASSED! Time: " +
		// nf.format((System.nanoTime() - startTime) * 1.0 / Math.pow(10, 9)));
		throw new RuntimeException("****FOUND FIRST SOLUTION");
	}

}
