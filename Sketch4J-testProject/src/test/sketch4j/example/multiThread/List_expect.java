/**
 * @author Lisa Jan 2, 2017 List.java 
 */
package test.sketch4j.example.multiThread;

public class List_expect extends List {

	public List_expect(int[] arr) {
		if (arr == null || arr.length == 0)
			return;
		header = new Node_expect(arr[0]);
		Node p = header;
		for (int i = 1; i < arr.length; i++) {
			p.next = new Node_expect(arr[i]);
			p = p.next;
		}
	}

}
