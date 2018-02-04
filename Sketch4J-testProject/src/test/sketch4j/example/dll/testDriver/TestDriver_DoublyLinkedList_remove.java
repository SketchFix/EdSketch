/**
 * @author Lisa Dec 1, 2016 TestDriver.java 
 */
package test.sketch4j.example.dll.testDriver;

import sketch4j.generator.SketchRequest;
import test.sketch4j.example.TestDriver;
import test.sketch4j.example.TestDriver_JUZI_multiSolution;
import test.sketch4j.example.dll.DoublyLinkedList_base;
import test.sketch4j.example.dll.DoublyLinkedList_expected;
import test.sketch4j.example.dll.DoublyLinkedList_remove;

/**
 * While this can be replaced with JUNIT, I use main for simplicity.
 * 
 * @author lisahua
 *
 */
public class TestDriver_DoublyLinkedList_remove extends TestDriver_DoublyLinkedList {
//	private boolean testEmptyList() {
//		return testHelper(new int[] {});
//	}
//
//	private boolean testOneNode() {
//		return testHelper(new int[] { 1 });
//	}
//
//	private boolean testTwoNode() {
//		return testHelper(new int[] { 1, 2 });
//	}
//
//	private boolean testThreeNode() {
//		return testHelper(new int[] { 1, 2, 3 });
//	}

	private boolean testMoreNode() {
		return testHelper(new int[] { 1, 2, 3, 4 });
	}

	@Override
	public boolean test() {
		// if(!testEmptyList()) return false;
		// if(!testOneNode()) return false;
		// if(!testTwoNode()) return false;
		// if(!testThreeNode()) return false;

		if (!testMoreNode())
			return false;
		return true;
	}

	@Override
	protected boolean testHelper(int[] arr) {
		DoublyLinkedList_remove sketch = new DoublyLinkedList_remove();
		for (int i : arr)
			sketch.addLast(i);
		sketch.remove(3);
		DoublyLinkedList_base expect = new DoublyLinkedList_expected();
		for (int i : arr)
			expect.addLast(i);
		expect.remove(3);
		return checkEq(sketch, expect);
	}

	public static void main(String[] a) {
		TestDriver driver = new TestDriver_DoublyLinkedList_remove();
		TestDriver_JUZI_multiSolution.counter++;
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

	public String toString() {
		return SketchRequest.getString();
	}
}
