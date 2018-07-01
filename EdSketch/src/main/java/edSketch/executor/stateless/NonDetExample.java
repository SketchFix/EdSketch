package edSketch.executor.stateless;

public class NonDetExample {
	public static void main(String args[]) {
		Explorer.initialize();
		do {
			// non-deterministic program goes in this block
			// initialize_state();   reset operator-hole-assignment RHS to null; reset *all* hole-assignments to null
//			Sketch_Operator_Int._aop_ = null;
//			LL_Reverse_Condition.condGenerator.reset();
			try {
			nondet();
			} catch (BacktrackException e) {
				System.out.println("BACTRACKING");
			}
		} while (Explorer.incrementCounter());
	}
	
	static void nondet() {
//		int x = Explorer.choose(2); // Verify.getInt(2);
//		boolean b = (Explorer.choose(1) == 0) ? false : true; // Verify.getBoolean();
//		System.out.println(x + ", " + b);
//		 Sketch_Operator_Int example = new Sketch_Operator_Int();
//		 Sketch_Operator_Int.main(new String[]{});
//		LL_Reverse_Condition.main(new String[]{});
	}
}
