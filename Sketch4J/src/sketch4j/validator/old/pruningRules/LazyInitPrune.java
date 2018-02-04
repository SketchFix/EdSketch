/**
 * @author Lisa Nov 10, 2016 InterStmtPruning.java 
 */
package sketch4j.validator.old.pruningRules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import gov.nasa.jpf.jvm.Verify;

/**
 * 
 * consider same value in one iteration but different in another iteration
 *
 */
public class LazyInitPrune<T> extends StmtPruningStrategy<T> {
	private TreeMap<Integer, Set<Integer>> clusters = new TreeMap<Integer, Set<Integer>>();

	public LazyInitPrune(String[] names) {
		super(names);
	}

	public Object prune(T[] vals) {

		if (rhs == -1) {
			Map<T, Set<Integer>> valmap = new HashMap<T, Set<Integer>>();
			for (int i = 0; i < len; i++) {
				T e = vals[i];
				Set<Integer> sameExpr = valmap.get(e);
				if (sameExpr == null) {
					sameExpr = new HashSet<Integer>();
					valmap.put(e, sameExpr);
					clusters.put(i, sameExpr);
				}
				sameExpr.add(i);
			}
			int id = Verify.getInt(0, clusters.size() - 1);
			Object[] arr = clusters.keySet().toArray();
			rhs = (int) arr[id];
//			System.out.println("select rhs " + rhs);
//			for (int key : clusters.keySet()) {
//				System.out.println(key + " : " + clusters.get(key));
//			}
		} else {
			// check that all expressions in "my" cluster still have the same
			// value as "me". if not, update clusters on selected rhs only and
			// create more non-deterministic choice
			T curVal = vals[rhs];
			boolean hasChange = false;
			Iterator<Integer> itr = clusters.get(rhs).iterator();
			Map<T, Set<Integer>> valmap = new HashMap<T, Set<Integer>>();
			while (itr.hasNext()) {
				int i = itr.next();
				T e = vals[i];
				// FIXME should use == or equals?
				if ((e==null && curVal==null) || (e!=null && e.equals(curVal)))
					continue;
				// else found expression that *now* has a different value
				hasChange = true;
//				System.out.println("split " + rhs + "," + i);
				Set<Integer> sameExpr = valmap.get(e);
				if (sameExpr == null) {
					sameExpr = new HashSet<Integer>();
					valmap.put(e, sameExpr);
					clusters.put(i, sameExpr);
				}
				sameExpr.add(i);
				itr.remove();
			}
			if (hasChange) {
				int id = Verify.getInt(0, clusters.size() - 1);
				Object[] arr = clusters.keySet().toArray();
				rhs = (int) arr[id];
//				System.out.println("select rhs " + rhs);
//				for (int key : clusters.keySet()) {
//					System.out.println(key + " : " + clusters.get(key));
//				}
			}
		}

		return vals[rhs];
	}

}