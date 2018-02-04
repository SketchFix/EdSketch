/**
 * @author Lisa Dec 1, 2016 TestDriver.java 
 */
package test.sketch4j.example.dll.testDriver;

import sketch4j.generator.SketchRequest;
import test.sketch4j.example.dll.DoublyLinkedList_addFirst;
import test.sketch4j.example.dll.DoublyLinkedList_base;
import test.sketch4j.example.dll.DoublyLinkedList_expected;

/**
 * While this can be replaced with JUNIT, I use main for simplicity.
 * 
 * @author lisahua
 *
 */
public class TestDriver_DoublyLinkedList_addFirst extends TestDriver_DoublyLinkedList {
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

	public boolean test2() {

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

	protected boolean testHelper(int[] arr) {
		DoublyLinkedList_addFirst sketch = new DoublyLinkedList_addFirst();
		for (int i : arr)
			sketch.addFirst(i);
		DoublyLinkedList_base expect = new DoublyLinkedList_expected();
		for (int i : arr)
			expect.addFirst(i);
		return checkEq(sketch, expect);
	}

	public static void main(String[] a) {
		TestDriver_DoublyLinkedList_addFirst driver = new TestDriver_DoublyLinkedList_addFirst();
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

	public String toString() {
		return SketchRequest.getString();
	}
}
