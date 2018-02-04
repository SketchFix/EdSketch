package test.sketch4j.example.multiThread.jdkBug2;

/**
 * There is a race condition in the java.util.Vector class. It occurs because
 * the single argument version of lastIndexOf reads elementCount without being
 * synchronized. A different thread can remove elements and trim the array
 * before the synchronized version of lastIndexOf is invoked, causing an array
 * bounds exception.
 *
 */
public class Tester extends Thread {
	public void run() {
		Shared.Sleep(100);
		Shared.v.lastIndexOf(Shared.v);
	}

	public static void main(String args[]) {
		new Shared().start();
		new Tester().start();
	}
}
