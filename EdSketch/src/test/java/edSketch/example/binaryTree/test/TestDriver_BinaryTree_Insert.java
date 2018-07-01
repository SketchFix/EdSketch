/**
 * @author Lisa Dec 1, 2016 TestDriver.java 
 */
package edSketch.example.binaryTree.test;

import edSketch.example.TestDriver;
import edSketch.example.binaryTree.BinaryTree_base;
import edSketch.example.binaryTree.BinaryTree_expected;
import edSketch.example.binaryTree.BinaryTree_insert;
import edSketch.executor.SketchExecutor;

/**
 * While this can be replaced with JUNIT, I use main for simplicity.
 * 
 * @author lisahua
 *
 */
public class TestDriver_BinaryTree_Insert extends TestDriver_BinaryTree {
	

	private boolean test1() {
		return testHelper(new int[]{2});
		
	}
	private boolean test2() {
		return testHelper(new int[]{2,1}) && testHelper(new int[]{1,2}) ;
	}
	private boolean test3() {
		return testHelper(new int[]{1,2,3}) && testHelper(new int[]{3,1,2}) &&testHelper(new int[]{2,1,3})  && testHelper(new int[]{1,3,2})   ;
	}

	@Override
	public boolean test() {
		// if(!testEmptyList()) return false;
		// if(!testOneNode()) return false;
		// if(!testTwoNode()) return false;
		 if(!test3()) return false;
//			return false;
		return true;
	}

	@Override
	protected boolean testHelper(int[] arr) {
		BinaryTree_insert sketch = new BinaryTree_insert();
		for (int i: arr)
			sketch.insert(i);
		BinaryTree_base expect = new BinaryTree_expected();
		for (int i: arr)
			expect.insert(i);
		return checkEq(sketch.root, expect.root);
	}
	
	public static void main(String[] a) {
		TestDriver driver = new TestDriver_BinaryTree_Insert();
		try {
			if (!driver.test())
				throw new RuntimeException("****TEST FAILURE");
		} catch (NullPointerException npe) {
			//npe.printStackTrace();
			//System.out.println( "*****BACKTRACKING: null pointer exception");
			SketchExecutor.backtrack();
		} catch (RuntimeException re) {
		//	System.out.println( re.getMessage());
			SketchExecutor.backtrack();
		}
	//	System.out.println("****ALL TESTS PASSED!  Time: " + nf.format((System.nanoTime() - startTime) * 1.0 / Math.pow(10, 9)));
		throw new RuntimeException("****FOUND FIRST SOLUTION");
	}
}
