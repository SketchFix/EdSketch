/**
 * @author Lisa Jan 2, 2017 List.java 
 */
package test.sketch4j.example.multiThread;

import java.util.HashSet;
import java.util.Set;

import sketch4j.generator.SketchRequest;

public class List {
	public Node header;
	private static final int ITER_BOUND = 5;

	public List() {
	}

	public List(int[] arr) {
		if (arr == null || arr.length == 0)
			return;
		header = new Node(arr[0]);
		Node p = header;
		for (int i = 1; i < arr.length; i++) {
			p.next = new Node(arr[i]);
			p = p.next;
		}
	}

	void distributedSort() {

		if (header == null)
			return;
		if (header.next == null)
			return;
		int i = 0;
		Node t = header;

		int count = 0;
		while (t.next != null) {
			if (count++ > ITER_BOUND) {
				System.out.println("******Exceed iteration bound");
				SketchRequest.backtrack();
			}
			new Swapper(t, ++i).start();
			t = t.next;
		}
		
	}

	boolean acyclic() {
		Set<Node> visited = new HashSet<Node>();
		Node current = header;
		while (current != null) {
			if (!visited.add(current))
				return false;
			current = current.next;
		}
		return true;
	}

	boolean ascendent() {
		Node current = header;
		int prev = Integer.MIN_VALUE;
		while (current != null) {
			if (current.elem < prev)
				return false;
			prev = current.elem;
		}
		return true;
	}

	public String toString() {
		String toString = "";
		for (Node p = header; p != null; p = p.next)
			toString += p.toString() + " ";
		return toString;
	}

}
