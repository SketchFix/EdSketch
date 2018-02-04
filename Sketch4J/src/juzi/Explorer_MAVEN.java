package juzi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
/**
*
* @author Yuklai Suen
*/
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("rawtypes")
public class Explorer_MAVEN {
	private static final boolean DEBUG = false; // true;
	private static boolean m_multipleRuns = false;

	private static ArrayList m_counters;
	private static int m_counterPointer;

	private static Counter get(int i) {
		return (Counter) m_counters.get(i);
	}

	public static void backtrack() throws BacktrackException {
		throw new BacktrackException();
	}

	public static void checkAssertion(boolean exp) throws BacktrackException {
		if (exp)
			return;
		System.out.println("an assertion evaluated to false");
		if (m_multipleRuns)
			throw new BacktrackException();
		throw new RuntimeException();
	}

	public static boolean incrementCounter() {
		deserialize();
		if (m_counters.size() == 0) { // if there's not a counter at the end of
										// an
			// execution, then end the run
			if (DEBUG)
				System.out.println("incrementCounter(): m_counter.size() == 0");
			return false;
		}

		// remove the last elements of the m_counter if it is at its max value
		// before increment
		while (m_counters.size() > 0) {
			int lastIndex = m_counters.size() - 1;
			try {
				Counter last = get(lastIndex);
				if (last.getValue() == (last.getMax() - 1)) {
					if (DEBUG)
						System.out.println("removed the last element reached at its max (" + last.getValue() + ")");

					m_counters.remove(last);

				} else {
					break;
				}
			} catch (Exception e) {
			}
		}
		int i = 0;
		boolean atMax = true;
		if (DEBUG)
			System.out.println("There are " + m_counters.size() + " counters");
		/*
		 * for (int j = 0; j < m_counter.size(); j++) { if (DEBUG)
		 * System.out.print("(" + get(j).getIndex() + ")" + get(j).getValue());
		 * }
		 */
		if (DEBUG)
			System.out.println();

		// Check to see if ALL counters has reached their maximum
		while (atMax && (i < m_counters.size())) {
			if (get(i).getValue() != (get(i).getMax() - 1)) {
				if (DEBUG)
					System.out.println("Counter with max " + (get(i).getMax() - 1) + " has value " + get(i).getValue());
				atMax = false;
				break;
			}
			atMax = atMax && (get(i).getValue() == (get(i).getMax() - 1));
			i++;
		}
		if (atMax) {
			if (DEBUG)
				System.out.println("atMax, return");
			return false;
		}

		// increment the counter, then carry over the rest
		get(m_counters.size() - 1).increment();
		// i = m_counter.size() - 1;
		/*
		 * while ( (i >= 0) ) { if (get(i).increment()) { i--; } else { break; }
		 * }
		 */
		if (DEBUG)
			System.out.println();
		if (DEBUG) {
			for (int j = 0; j < m_counters.size(); j++) {
				System.out.println("Counter[" + j + "]:" + get(j));
			}
		}
		m_counterPointer = 0;
		serialize();
		return true;
	}

	/*
	 * private static int exists(int _index) { for (int i = 0; i <
	 * m_counter.size(); i++) { if (get(i).getIndex() == _index) { return i; } }
	 * return -1; }
	 */

	public static void initialize() {
//		deserialize();
		initialize(false);
		
	}

	public static void initialize(boolean mRuns) {
		m_multipleRuns = mRuns;
		m_counters = new ArrayList();
		m_counterPointer = 0;
	}

	@SuppressWarnings("unchecked")
	public static int choose(int _max) {
//		System.out.println("choose "+_max);
		deserialize();
		// int vIndex = exists(_index);
		int value;
		/*
		 * if (vIndex < 0) { m_counter.add(new Counter(_index, _max)); value =
		 * 0; //return the current value of the newly added counter, which is 0;
		 * } else { value = get(vIndex).getValue(); }
		 */

		if (m_counterPointer >= m_counters.size()) { // add a new counter if
														// reaches a new choose
														// call
			if (DEBUG)
				System.out.println("adding counter...");
			m_counters.add(new Counter(_max));
			value = 0;
		} else { // read from a current pointer
			value = get(m_counterPointer).getValue();
		}
		if (DEBUG) {
			/*
			 * for (int i = 0; i < m_counters.size(); i++) {
			 * System.out.println("Counter[" + i +"]:" + get(i)); }
			 */ System.out.println("debug_ind: " + (m_counterPointer) + " max: " + _max + " choose: " + value);
		}
		m_counterPointer++;
		serialize();
		return value;
	}

	public static boolean chooseBoolean() {
		int i = choose(1);
		if (i == 0) {
			return false;
		} else { // i == 1
			return true;
		}
	}

	public static int getCounterPointer() {
		return m_counterPointer;
	}

	public static void main(String args[]) {
		if (!DEBUG) {
			System.out.println("Debugging is off, there won't be output!");
		}
		Explorer.initialize();

		do {
			if (Explorer.choose(2) == 1) {
				Explorer.choose(3);
			}

			Explorer.choose(1);
			// Explorer.choose(k--);
			for (int i = 0; i < 2; i++) {
				Explorer.choose(2);
			}

			if (DEBUG)
				System.out.println();
		} while (Explorer.incrementCounter());
	}

	private static void serialize() {
		try {

			PrintWriter writer = new PrintWriter("/Users/lisahua/projects/data/Lang_12_orig/src/test/resources/log");
			writer.println( m_counterPointer);
			for (Object o : m_counters) {
				Counter c = (Counter) o;
				writer.println(c.getMax()-1 + " " + c.getValue());
//				System.out.println("serialize " + c.getMax() + " " + c.getValue());
			}
			writer.close();
			initialize(false);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private static void deserialize() {
		initialize(false);
		try {
			Scanner scan = new Scanner(new File("/Users/lisahua/projects/data/Lang_12_orig/src/test/resources/log"));
			if (scan.hasNextInt()) {
				m_counterPointer = scan.nextInt();
//				System.out.println("deserialize " + m_counterPointer);
			}
			
			while (scan.hasNextInt()) {
				int max = scan.nextInt();
				int v = scan.nextInt();
				Counter c = new Counter(max);
				c.setValue(v);
				m_counters.add(c);
//				System.out.println("deserialize " + c.getMax() + " " + c.getValue());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

	}
}
