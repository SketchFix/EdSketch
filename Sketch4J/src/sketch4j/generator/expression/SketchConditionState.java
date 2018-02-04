/**
 * @author Lisa Nov 10, 2016 InterStmtPruning.java 
 */
package sketch4j.generator.expression;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

//import gov.nasa.jpf.jvm.Verify;
import sketch4j.executor.SketchExecutor;
import sketch4j.frontend.ast.SJValueCandidate;
import sketch4j.generator.SketchGenerator;

/**
 * I define it as Iterator to support next version without JPF. Right now since
 * we use JPF for backtrack, hasNext return true and next only return one
 * element.
 *
 */

public class SketchConditionState {
	private HashSet<int[]> trueSet = null;
	private HashSet<int[]> falseSet = null;

	@SuppressWarnings("unchecked")
	public SketchConditionState(HashSet<int[]> t, HashSet<int[]> f) {
		trueSet = (HashSet<int[]>) t.clone();
		falseSet = (HashSet<int[]>) f.clone();
//		System.out.println("Add state "+trueSet.size()+" "+falseSet.size());
	}

	public HashSet<int[]> getTrueSet() {
		return trueSet;
	}

	public HashSet<int[]> getFalseSet() {
		return falseSet;
	}

}