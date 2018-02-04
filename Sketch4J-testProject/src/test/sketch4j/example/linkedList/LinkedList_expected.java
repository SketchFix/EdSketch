package test.sketch4j.example.linkedList;

public class LinkedList_expected extends LinkedList {
	public LinkedList_expected(int[] arr) {
		super(arr);
	}

	public String toString() {
		String res = "";
		for (Entry e = head; e != null; e = e.next)
			res += e.val + " ";
		return res;
	}

	public Entry getHead() {
		return head;
	}

	public LinkedList reverse() {
		if (head == null)
			return this;
		Entry ln1 = head;
		Entry ln2 = null;
		Entry ln3 = null;
		while (ln1 != null) {
			ln2 = ln1.next;
			ln1.next = ln3;
			ln3 = ln1;
			ln1 = ln2;
		}
//		ln1.next = ln3;
		head = ln3;
		return this;
	}
	/** Sorted Linked List insertion */
	public void insert(int val) {
		assert val != 0;
		Entry insert = new Entry(val);
		Entry e = head;
		while (!head.equals(e.next)) {
			if (e.next.val < val)
				e = e.next;
			else
				break;
		}
		insert.next = e.next;
		e.next = insert;
		size++;
	}
	public boolean hasLoop() {
		Entry ln1, ln2;
		if (head.next.equals(head))
			return false;
		ln1 = head;
		ln2 = head;
		while (true) {
			if (ln1.next.equals(head))
				return false;
			else
				ln1 = ln1.next;
			if (ln2.next.equals(head) || ln2.next.next.equals(head))
				return false;
			else
				ln2 = ln2.next.next;
			if (ln1 == ln2)
				return true;
		}
	}
}