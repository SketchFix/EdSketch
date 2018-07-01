package edSketch.example.linkedList;

import com.github.javaparser.ast.Node;

import edSketch.executor.SketchExecutor;
import edSketch.request.SketchFix;

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

		boolean cond = (Boolean) SketchFix
				.COND(new Entry[] { ln1, ln2, ln3 }, 0, new String[] { "ln1", "ln2", "ln3" }, Node.class).invoke();
		while (cond) {
			if (iter_count++ > ITER_BOUND) {
				SketchExecutor.backtrack();
			}
			ln1.next = ln3;
			ln3 = ln1;
			ln1 = ln2;
			ln2 = ln2.next;
			cond = (Boolean)SketchFix.COND(new Entry[] { ln1, ln2, ln3 },0,
					new String[] { "ln1", "ln2", "ln3" }, Node.class).invoke();
		}
		ln1.next = ln3;
		head = ln1;
		return this;
	}

}
