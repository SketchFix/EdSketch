package test.sketch4j.example.multiThread;

public class Node {
	public int elem;
	public Node next;
	int _t_;
	String toString = "";

	public Node(int val) {
		elem = val;
	}

	synchronized boolean swapElem() {
		synchronized (next) {
			if (elem > next.elem) {
				int t = elem;
				elem = next.elem;
				next.elem = t;
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return elem+" ";
	}
}