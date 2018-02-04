/**
 * @author Lisa Dec 1, 2016 TestDriver.java 
 */
package test.sketch4j.example.linkedList.testDriver;

import sketch4j.generator.SketchRequest;
import test.sketch4j.example.TestDriver;
import test.sketch4j.example.TestDriver_JUZI_multiSolution;
import test.sketch4j.example.linkedList.LinkedList_hasLoop;

/**
 * While this can be replaced with JUNIT, I use main for simplicity.
 * 
 * @author lisahua
 *
 */
public class TestDriver_LinkedList_HasLoop extends TestDriver_LinkedList {
	private boolean testEmptyList() {
		return testHelper(new int[] {});
	}

	private boolean testOneNode() {
		LinkedList_hasLoop one = new LinkedList_hasLoop(new int[]{1});
		return !one.hasLoop();
	}

	private boolean testNoLoop() {
		LinkedList_hasLoop sketch = new LinkedList_hasLoop(new int[]{1,2,3,4});
		return !sketch.hasLoop();
	}

	private boolean testHasLoop() {
		LinkedList_hasLoop sketch = new LinkedList_hasLoop(new int[]{1,2,3,4});
		sketch.head.next.next.next = sketch.head.next.next;
		return sketch.hasLoop();
	}

	@Override
	public boolean test() {
		// if(!testEmptyList()) return false;
		 if(!testOneNode()) return false;
		// if(!testTwoNode()) return false;
		 if(!testNoLoop()) return false;
		if (!testHasLoop())
			return false;
		return true;
	}

	@Override
	protected boolean testHelper(int[] arr) {
		
		return false;
	}
	public static void main(String[] a) {
		TestDriver driver = new TestDriver_LinkedList_HasLoop();
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
