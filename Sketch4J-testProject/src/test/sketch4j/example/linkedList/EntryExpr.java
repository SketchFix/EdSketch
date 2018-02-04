package test.sketch4j.example.linkedList;

public class EntryExpr {
		Entry val;
		int id;
		String expStr;
		
		public EntryExpr (int id, String expStr) {

			this.id = id;
			this.expStr = expStr;
		}
		
		public static EntryExpr[] genAllExp(String[] vars) {
			int i=0; int len = vars.length;
			EntryExpr[] list = new EntryExpr[len];
			for (; i < vars.length; i++) 
				list[i] = new EntryExpr(i,vars[i]);
			return list;
		}
		
		public String toString() {
			return expStr;
		}

		public Entry getVal() {
			return val;
		}

		public void setVal(Entry val) {
			this.val = val;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getExpStr() {
			return expStr;
		}

		public void setExpStr(String expStr) {
			this.expStr = expStr;
		}
		
		
	}

