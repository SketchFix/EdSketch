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
public class DoublyLinkedList_addLast extends DoublyLinkedList_base {
	/**
	 * Appends the specified element to the end of this list.
	 */
	public void addLast(int val) {
		assert val != 0;
		Node e = new Node(val);
		{
			_e_ = e;
		}
		e.next = header;
		 _BLOCK_(SketchRequest.queryBlock(Node.class, new String[] { "e", "header" }, new Node[] { _e_, header }, 2, 2,
					0));
		 
//		e.prev = header.prev;
//		 e.prev.next = e;
//		 _BLOCK_(SketchRequest.queryBlock(Node.class, new String[] { "e", "header" }, new Node[] { _e_, header }, 2, 2,
//					0));
		e.next.prev = e;
	

		size++;
	}

}
