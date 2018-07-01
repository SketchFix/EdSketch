/**
 * @author Lisa Dec 1, 2016 TestDriver.java 
 */
package edSketch.example.linkedList.testDriver;

import edSketch.example.TestDriver;
import edSketch.example.linkedList.Entry;
import edSketch.example.linkedList.LinkedList;
/**
 * While this can be replaced with JUNIT, I use main for simplicity.
 * 
 * @author lisahua
 *
 */
public abstract class TestDriver_LinkedList extends TestDriver {


	protected boolean checkEq(LinkedList x, LinkedList y) {
		if (x == null && y == null)
			return true;
		Entry xh = x.head, yh = y.head;
		for (; xh != null && yh != null; xh = xh.next, yh = yh.next) {
			if (xh.val != yh.val) {
				return false;
			}
		}
		return xh == null && yh == null;
	}
	
	protected abstract boolean testHelper(int[] arr);
}
