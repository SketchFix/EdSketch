package test.sketch4j.example.multiThread;

import juzi.BacktrackException;
import sketch4j.generator.SketchRequest;

public class Swapper extends Thread {
	Node current;
	int maxSwaps;

	Swapper(Node m, int n) {
		current = m;
		maxSwaps = n;

	}

	public void run() {

		int swapCount = 0;
		// FIXME change back with JPF 
//		do {
//			SketchRequest.initialize();
//			try {
				for (int i = 0; i < maxSwaps; i++) {
					if (current.swapElem())
						swapCount++;
				}
//				if (swapCount!=maxSwaps) SketchRequest.backtrack();;
//			} catch (BacktrackException e) {
//				// System.out.println("****** JUZI BACKTRACK");
//			}
//		} while (SketchRequest.getExecutor().incrementCounter());

	}
}