/**
 * @author Lisa Feb 1, 2017 FileSum.java 
 */
package test.sketch4j.example.io;

import java.io.File;
import java.util.Scanner;

import sketch4j.generator.SketchRequest;

public class FileSum {

	public int test() {
		Scanner scanner = null;
		int a = 0, b = 0;
		try {
			scanner = new Scanner(new File("a.txt"));
			a = scanner.nextInt();
			b = scanner.nextInt();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		return (int) SketchRequest.queryPrimOperator(new String[] { "a", "b" }, new double[] { a, b }, 0);

	}

	public static void main(String[] arg) {
		new FileSum().test();
	}
}
