package test.sketch4j.example.linkedList;

import sketch4j.generator.SketchRequest;

public class LL_Reverse_While extends LinkedList {

	private static int iter_count = 0;
	private final static int ITER_BOUND = 11;

	public LL_Reverse_While(int[] arr) {
		super(arr);
	}

	// translated program to sketch
	// operator hole is replaced with method call
	public LinkedList reverse() {
		if (head == null)
			return this;
		Entry ln1 = head;
		Entry ln2 = head.next;
		Entry ln3 = null;
		iter_count = 0;
		boolean cond = SketchRequest.queryCondition(ln1.getClass(), new String[] { "ln1", "ln2", "ln3" },
				new Entry[] { ln1, ln2, ln3 }, 1, 1, true, 0);
		while (cond) {
			if (iter_count++ > ITER_BOUND) {
				SketchRequest.backtrack();
			}
			ln1.next = ln3;
			ln3 = ln1;
			ln1 = ln2;
			ln2 = ln2.next;
			cond = SketchRequest.queryCondition(ln1.getClass(), new String[] { "ln1", "ln2", "ln3" },
					new Entry[] { ln1, ln2, ln3 }, 1, 1, true, 0);
		}
		ln1.next = ln3;
		head = ln1;
		return this;
	}

}
