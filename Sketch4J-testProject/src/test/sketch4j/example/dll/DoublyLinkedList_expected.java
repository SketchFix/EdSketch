/**
 * @author Lisa Apr 17, 2016 LinkedList_7.java 
 */
package test.sketch4j.example.dll;

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
public class DoublyLinkedList_expected extends DoublyLinkedList_base {
	public DoublyLinkedList_expected() {
		header = new Node(0);
		header.next = header.prev = header;
	}

	public void addFirst(int val) {
		assert val != 0;
		Node e = new Node(val);
		e.next = header.next;
		e.prev = header;
		e.prev.next = e;
		e.next.prev = e;
		size++;
	}

	/**
	 * Appends the specified element to the end of this list.
	 */
	public void addLast(int val) {
		assert val != 0;
		Node e = new Node(val);
		e.next = header;
		e.prev = header.prev;
		e.prev.next = e;
		e.next.prev = e;
		size++;
	}

	private int remove(Node e) {
		if (e == header)
			return 0;

		int result = e.val;
		e.prev.next = e.next;
		e.next.prev = e.prev;
		e.next = null;
		e.prev = null;
		e.val = 0;
		size--;
		return result;
	}

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
		for (Node e = header.next; e != header; e = e.next) {
			if (o == e.val) {
				remove(e);
				return true;
			}
		}
		return false;
	}
}
