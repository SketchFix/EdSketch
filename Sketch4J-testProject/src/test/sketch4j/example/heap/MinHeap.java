/**
 * @author Lisa Apr 18, 2016 PriorityQueue_base.java 
 */
package test.sketch4j.example.heap;

public class MinHeap {
	int size;
	int[] queue = new int[100];// we assume the heap will have no more
								// than 100 elements.

	/**
	 * Inserts the specified element into this MinHeap.
	 */
	public boolean add(int value) {
		assert value != 0;
		int i = size;
		while (i > 0) {
			int parent = (i - 1) / 2;
			int p_val = queue[parent];
			if (value >= p_val)
				break;
			queue[i] = p_val;
			i = parent;
		}
		queue[i] = value;
		size++;
		return true;
	}

	public void heapSort(int[] arr ) {
		for (int i = size / 2 - 1; i >= 0; i--) {
			int half = size / 2;
			int moved = arr[i];
			while (i < half) {
				int child = i * 2 + 1;
				int c = arr[child];
				int right = child + 1;
				if (right < size && c > arr[right])
					c = arr[child = right];
				if (moved <= c)
					break;
				arr[i] = c;
				i = child;
			}
			arr[i] = moved;
		}
	}

	public void remove(int o) {
		for (int i = 0; i < size; i++) {
			if (o == queue[i]) {
				int s = --size;
				if (s == i) // removed last element
					queue[i] = 0;
				else {
					int moved = queue[s];
					queue[s] = 0;
					int half = size / 2;
					while (i < half) {
						int child = (i << 1) + 1;
						int c = queue[child];
						int right = child + 1;
						if (right < size && c > queue[right])
							c = queue[child = right];
						if (moved <= c)
							break;
						queue[i] = c;
						i = child;
					}
					queue[i] = moved;
					// if (queue[i] == moved) {
					while (i > 0) {
						int parent = (i - 1) / 2;
						int p_val = queue[parent];
						if (moved > p_val)
							break;
						queue[i] = p_val;
						i = parent;
					}
					queue[i] = moved;
				}
				// }
			}
		}
	}
}
