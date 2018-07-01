/**
 * @author Lisa Dec 1, 2016 TestDriver.java 
 */
package edSketch.example.binaryTree.test;

import edSketch.example.TestDriver;
import edSketch.example.binaryTree.Node;

/**
 * While this can be replaced with JUNIT, I use main for simplicity.
 * 
 * @author lisahua
 *
 */
public abstract class TestDriver_BinaryTree extends TestDriver {

	protected boolean checkEq(Node x, Node y) {
		if (x == null && y == null)
			return true;
		if (x.key != y.key)
			return false;
		if (!checkEq(x.left, y.left))
			return false;
		if (!checkEq(x.right, y.right))
			return false;
		return true;
	}

	protected abstract boolean testHelper(int[] arr);
}
