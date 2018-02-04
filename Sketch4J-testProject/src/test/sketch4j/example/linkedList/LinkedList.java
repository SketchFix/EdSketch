package test.sketch4j.example.linkedList;

public class LinkedList {
	public Entry head = null;
	public Entry _ln1_, _ln2_, _ln3_, _ln4_;
	String toString = "";
	public int size = 0;

	public LinkedList() {
	}

	public LinkedList(int[] arr) {
		if (arr == null || arr.length == 0)
			return;
		head = new Entry(arr[0]);
		Entry p = head;
		for (int i = 1; i < arr.length; i++) {
			p.next = new Entry(arr[i]);
			p = p.next;
		}
	}

	public String toString() {
		String res = "";
		for (Entry e = head; e != null; e = e.next)
			res += e.val + " ";
		return res;
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

	public Entry getHead() {
		return head;
	}

	public LinkedList reverse() {
		if (head == null)
			return this;
		Entry ln1 = head;
		Entry ln2 = head.next;
		Entry ln3 = null;
		while (ln2 != null) {
			ln1.next = ln3;
			ln3 = ln1;
			ln1 = ln2;
			ln2 = ln2.next;
		}
		ln1.next = ln3;
		head = ln1;
		return this;
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
	
	public String getToString() {
		return toString;
	}
}