package test.sketch4j.example.dll;

public class Node {
	public int val;
	public  Node next;
	public  Node prev;

	public Node(int val) {
		this.val = val;
	}
	
	public String toString() {
		return val+" "+next+" "+prev;
	}
}