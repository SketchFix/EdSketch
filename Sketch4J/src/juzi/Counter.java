package juzi;

/**
 *
 * @author Yuklai Suen
 */

public class Counter {
	private int m_max;
	private int m_value = 0;
	// private int m_index;

	public Counter(int _max) {
		// SK if (_max <= 0) {
		if (_max < 0) { // SK
			throw new RuntimeException("_max < 0");
		}
		m_max = _max + 1; // the counter can return from 0 to _max inclusively
		// m_index = _index;
	}

	// increment the value of the counter
	// if there's a carry, return true
	public boolean increment() {
		m_value += 1;
		if (m_value >= m_max) {
			m_value -= m_max;
			return true;
		}
		return false;
	}

	public int getValue() {
		return m_value;
	}

	public void setValue(int v) {
		m_value = v;
	}

	/*
	 * public int getIndex() { return m_index; }
	 */
	public int getMax() {
		return m_max;
	}

	public String toString() {
		return "m(" + (m_max - 1) + ") v(" + m_value + ")";
	}
}
