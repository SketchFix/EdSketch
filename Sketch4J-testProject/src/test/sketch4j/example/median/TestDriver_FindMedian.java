/**
 * @author Lisa Jan 24, 2017 TestDriver_FindMedian.java 
 */
package test.sketch4j.example.median;

import java.text.NumberFormat;

import sketch4j.generator.SketchRequest;
import test.sketch4j.example.TestDriver;
import test.sketch4j.example.TestDriver_JUZI_multiSolution;

public class TestDriver_FindMedian extends TestDriver{
	
	public static void main(String[] arg) {
		int[][] arr = { { 3, 3, 5, 3 }, { 1, 2, 3, 2 }, { 3, 2, 1, 2 }, { 5, 5, 5, 5 }, { 5, 3, 4, 4 },
				{ 2, 1, 3, 2 },{2,3,1,2 }};
		FindMedian_Sketch median = new FindMedian_Sketch();
		TestDriver_JUZI_multiSolution.counter++;
		try {
			for (int[] a : arr) {
				if (median.mid(a[0], a[1], a[2]) != a[3]) {
					throw new RuntimeException("********Test Failure");
				}
			}
		} catch (NullPointerException npe) {
			 npe.printStackTrace();
			// System.out.println( "*****BACKTRACKING: null pointer exception");
			SketchRequest.backtrack();
		} catch (RuntimeException re) {
			// System.out.println( re.getMessage());
			SketchRequest.backtrack();
		}
		System.out.println(SketchRequest.getString());
		//System.out.println("****ALL TESTS PASSED!  Time: " + nf.format((System.currentTimeMillis() - startTime) * 1.0 / Math.pow(10, 3)));
		throw new RuntimeException("****FOUND FIRST SOLUTION");
	}

	@Override
	public boolean test() {
		
		return false;
	}

}
