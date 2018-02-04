package test.sketch4j.example.multiThread.jdkBug2;

class Shared extends Thread {

	static public Vector v;

	static {
		v = new Vector();
		for (int i=0;i<10;i++)
		v.addElement(v);
//		v.addElement(v);
//		v.addElement(v);
	}

	static void Sleep(int mills) {
		try {
			Thread.sleep(mills);
		} catch (Exception e) {
		}
	}

	public void run() {
		synchronized (v) {
			Sleep(200);
			v.removeElementAt(0);
			v.trimToSize();
		}
	}
}
