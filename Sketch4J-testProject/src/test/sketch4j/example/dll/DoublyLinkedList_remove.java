/**
 * @author Lisa Apr 17, 2016 LinkedList_7.java 
 */
package test.sketch4j.example.dll;

import sketch4j.generator.SketchRequest;

/**
 * Adapted from jdk / openjdk / 6-b27 / java.util.LinkedList (@see <a href=
 * "http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b27/java/util/LinkedList.java">
 * LinkedList.java</a>). This file was changed to improve performance in jdk-7
 * (@see
 * <a href="http://hg.openjdk.java.net/jdk7/jdk7/jdk/rev/6d24852165ba">jdk7-
 * revision-6d24852165ba</a>).
 * <p>
 * With sentinel header. I remove the generic type for simplicity. I remove the
 * transient property for simplicity. I assume the int 0 represents null in
 * generic type.
 * </p>
 * 
 * @author lisahua
 *
 */
public class DoublyLinkedList_remove extends DoublyLinkedList_base {
	private static final int iter_bound = 5;

	/**
	 * Removes a single instance of the specified element from this queue, if it
	 * is present. More formally, removes an element e such that o.equals(e), if
	 * this queue contains one or more such elements. Returns true if and only
	 * if this queue contained the specified element (or equivalently, if this
	 * queue changed as a result of the call).
	 * 
	 * @param o
	 * @return
	 */
	public boolean remove(int o) {
		assert o != 0;
		int count = 0;
		Node e = header.next;
//		boolean cond1 = SketchRequest.queryCondition(e.getClass(), new String[] { "e", "header" },
//				new Object[] { e, header }, 1, 1, true, 0);
//		while (cond1) {
			 while (e != header) {
			if (count++ > iter_bound)
				SketchRequest.backtrack();
			boolean cond2 = SketchRequest.queryCondition(int.class, new String[] { "e", "header" },
					new Object[] { e, header }, 1, 1, false, 0);
			if (cond2) {
//				 if (o == e.val) {
				remove(e);
				return true;
			}
			e = e.next;
//			cond1 = SketchRequest.queryCondition(e.getClass(), new String[] { "e", "header" },
//					new Object[] { e, header }, 1, 1, true, 0);
		}
		return false;
	}

	private int remove(Node e) {
		{
			_e_ = e;
		}
		if (e == header)
			return 0;

		int result = e.val;
		 e.prev.next = e.next;
//		 e.next.prev = e.prev;
		_BLOCK_(SketchRequest.queryBlock(Node.class, new String[] { "e", "header" }, new Node[] { _e_, header }, 1, 2,
				0));

		e.next = null;
		e.prev = null;

		e.val = 0;
		size--;
		return result;
	}
}
