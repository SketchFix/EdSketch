/**
 * @author Lisa Dec 1, 2016 TestDriver.java 
 */
package test.sketch4j.example.linkedList.testDriver;

import sketch4j.generator.SketchRequest;
import test.sketch4j.example.TestDriver;
import test.sketch4j.example.TestDriver_JUZI_multiSolution;
import test.sketch4j.example.linkedList.LL_Reverse_While;
import test.sketch4j.example.linkedList.LinkedList;
import test.sketch4j.example.linkedList.LinkedList_expected;

/**
 * While this can be replaced with JUNIT, I use main for simplicity.
 * 
 * @author lisahua
 *
 */
public class TestDriver_LinkedList_Reverse_While extends TestDriver_LinkedList {

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
//		if(!testEmptyList()) return false;
//		if(!testOneNode()) return false;
//		if(!testTwoNode()) return false;
//		if(!testThreeNode()) return false;
		if(!testMoreNode()) return false;
		return true;
	}

	protected boolean testHelper(int[] arr) {
		LL_Reverse_While sketch = new LL_Reverse_While(arr);
		sketch.reverse();
		LinkedList expect = new LinkedList_expected(arr);
		expect.reverse();
		return checkEq(expect, sketch);
	}
	
	public static void main(String[] a) {
		TestDriver driver = new TestDriver_LinkedList_Reverse_While();
		TestDriver_JUZI_multiSolution.counter++;
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
