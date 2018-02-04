/**
 * @author Lisa Jan 24, 2017 FindMedian.java 
 */
package test.sketch4j.example.median;

public class FindMedian {
	public int mid(int x, int y, int z) {
		int m = z;
		if (y < z) {
			if (x < y) {
				m = y;
			} else if (x < z) {
				m = x;
			}
		} else {
			if (x > y) {
				m = y;
			} else if (x > z) {
				m = x;
			}
		}
		return m;
	}
}
