/**
 * @author Lisa Dec 1, 2016 TestDriver.java 
 */
package test.sketch4j.example.linkedList.testDriver;

import sketch4j.generator.SketchRequest;
import test.sketch4j.example.TestDriver;
import test.sketch4j.example.linkedList.Entry;
import test.sketch4j.example.linkedList.LinkedList;
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

	public String toString() {
//		return sketch.getToString();
		return SketchRequest.getString();
	}
	
	protected abstract boolean testHelper(int[] arr);
}
