/**
 * @author Lisa Dec 1, 2016 TestDriver.java 
 */
package test.sketch4j.example.dll.testDriver;

import test.sketch4j.example.TestDriver;
import test.sketch4j.example.dll.DoublyLinkedList_base;
import test.sketch4j.example.dll.Node;
/**
 * While this can be replaced with JUNIT, I use main for simplicity.
 * 
 * @author lisahua
 *
 */
public abstract class TestDriver_DoublyLinkedList extends TestDriver {

	protected boolean checkEq(DoublyLinkedList_base expect, DoublyLinkedList_base sketch) {
		if (expect.size != sketch.size)
			return false;
		Node eh = expect.header;
		Node sh = sketch.header;
		Node ep = eh.next;
		Node sp = sh.next;
		int count = 0;
		for (; eh != ep; ep = ep.next, sp = sp.next, count++) {
			if (ep.val != sp.val)
				return false;
			if (ep.next.val != sp.next.val || ep.prev.val != sp.prev.val)
				return false;
		}
		return count == expect.size;

	}
	protected abstract boolean testHelper(int[] arr);
	
	
}
