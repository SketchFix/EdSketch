/**
 * @author Lisa Jan 27, 2017 JNICompare.java 
 */
package test.sketch4j.example.jni;

public class JNISwap {
	static {
		System.loadLibrary("CToString");
	}

	

	public static void main(String[] args) {
		new JNISwap().swap(1,2);
	}
	public native String nativeToString(int x, int y);
	public String swap(int x, int y) {
		int tmp = x;
		x = y;
		y  = tmp;
		String str = nativeToString(x,y);
		return str;
	}
}
