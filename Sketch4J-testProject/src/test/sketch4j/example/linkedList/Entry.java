package test.sketch4j.example.linkedList;
public class Entry {
		public Entry next = null;
		public int val;

		public Entry(int val) {
			this.val = val;
		}

		public Entry getNext() {
			return next;
		}

		public void setNext(Entry next) {
			this.next = next;
		}

		public int getVal() {
			return val;
		}

		public String toString() {
			return String.valueOf(val);
		}
	}