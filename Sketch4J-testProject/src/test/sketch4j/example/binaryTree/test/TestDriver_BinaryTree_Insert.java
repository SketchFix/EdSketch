/**
 * @author Lisa Dec 1, 2016 TestDriver.java 
 */
package test.sketch4j.example.binaryTree.test;

import sketch4j.generator.SketchRequest;
import test.sketch4j.example.TestDriver;
import test.sketch4j.example.binaryTree.BinaryTree_base;
import test.sketch4j.example.binaryTree.BinaryTree_expected;
import test.sketch4j.example.binaryTree.BinaryTree_insert;

/**
 * While this can be replaced with JUNIT, I use main for simplicity.
 * 
 * @author lisahua
 *
 */
public class TestDriver_BinaryTree_Insert extends TestDriver_BinaryTree {
	public static int counter = 0;
	public static int pruneCounter = 0;

//	private boolean test1() {
//		return testHelper(new int[] { 2 });
//
//	}
//
//	private boolean test2() {
//		return testHelper(new int[] { 2, 1 }) && testHelper(new int[] { 1, 2 });
//	}

	private boolean test3() {
		return testHelper(new int[] { 1, 2, 3 }) && testHelper(new int[] { 3, 1, 2 })
				&& testHelper(new int[] { 2, 1, 3 }) && testHelper(new int[] { 1, 3, 2 });
	}

	private boolean test4() {
		return testHelper(new int[] { 1, 2, 3, 4 }) && testHelper(new int[] { 3, 1, 2, 4 })
				&& testHelper(new int[] { 2, 1, 3, 4 }) && testHelper(new int[] { 1, 3, 2, 4 })
				&& testHelper(new int[] { 1, 2, 4, 3 }) && testHelper(new int[] { 3, 1, 4, 2 })
				&& testHelper(new int[] { 2, 1, 4, 3 }) && testHelper(new int[] { 1, 3, 4, 2 });
	}

	@Override
	public boolean test() {
		// if(!testEmptyList()) return false;
//		 if (!test1()) {
//		 System.out.println("1 fail");
//		 return false;
//		 }
//		 if (!test2()){
//		 System.out.println("2 fail");
//		 return false;
//		 }
		if (!test3()) {
			return false;
		}
		if (!test4()) {
			return false;
		}
		// return false;
		return true;
	}

	@Override
	protected boolean testHelper(int[] arr) {
		BinaryTree_insert sketch = new BinaryTree_insert();
		for (int i : arr)
			sketch.insert(i);
		BinaryTree_base expect = new BinaryTree_expected();
		for (int i : arr)
			expect.insert(i);
		return checkEq(sketch.root, expect.root);
	}

	public static void main(String[] a) {
		TestDriver driver = new TestDriver_BinaryTree_Insert();
		 counter++;
		try {
			if (!driver.test())
				throw new RuntimeException("****TEST FAILURE");
		} catch (NullPointerException npe) {
//			 npe.printStackTrace();
			// System.out.println( "*****BACKTRACKING: null pointer exception");
			// System.out.println(TestDriver.getString());
			SketchRequest.backtrack();
		} catch (RuntimeException re) {
//			re.printStackTrace();
			// System.out.println( re.getMessage());
			// System.out.println(TestDriver.getString());
			SketchRequest.backtrack();
		}
		// System.out.println("****ALL TESTS PASSED! Time: " +
		// nf.format((System.nanoTime() - startTime) * 1.0 / Math.pow(10, 9)));
		 System.out.println(counter);
		throw new RuntimeException("****FOUND FIRST SOLUTION");
	}

	public String toString() {
		return SketchRequest.getString();
	}
}
