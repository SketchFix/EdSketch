/**
 * @author Lisa Dec 1, 2016 TestDriver.java 
 */
package test.sketch4j.example.linkedList.testDriver;

import java.text.NumberFormat;

import sketch4j.generator.SketchRequest;
import test.sketch4j.example.TestDriver;
import test.sketch4j.example.linkedList.LinkedList;
import test.sketch4j.example.linkedList.LinkedList_Insert_Sorted;
import test.sketch4j.example.linkedList.LinkedList_expected;

/**
 * While this can be replaced with JUNIT, I use main for simplicity.
 * 
 * @author lisahua
 *
 */
public class TestDriver_LinkedList_Insert_Sorted extends TestDriver_LinkedList {
	private boolean testEmptyList() {
		return testHelper(new int[] {});
	}

	private boolean testInsertFront() {
		return testHelper(new int[] { 1 });
	}

	private boolean testInsertEnd() {
		return testHelper(new int[] { 1, 2 });
	}

	private boolean testInsertMiddle() {
		return testHelper(new int[] { 1, 2, 4 });
	}

	@Override
	public boolean test() {
		// if(!testEmptyList()) return false;
		// if(!testOneNode()) return false;
		// if(!testTwoNode()) return false;
		// if(!testThreeNode()) return false;
		if (!testInsertMiddle())
			return false;
		return true;
	}

	@Override
	protected boolean testHelper(int[] arr) {
		LinkedList_Insert_Sorted sketch = new LinkedList_Insert_Sorted(arr);
		sketch.insert(3);
		LinkedList expect = new LinkedList_expected(arr);
		expect.insert(3);
		return checkEq(sketch, expect);
	}
	
	public static void main(String[] a) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(10);
		TestDriver driver = new TestDriver_LinkedList_Insert_Sorted();
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
