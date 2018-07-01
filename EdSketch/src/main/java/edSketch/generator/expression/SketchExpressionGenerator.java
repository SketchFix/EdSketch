/**
 * @author Lisa Nov 10, 2016 InterStmtPruning.java 
 */
package edSketch.generator.expression;

import java.util.List;

import edSketch.executor.SketchExecutor;
import edSketch.frontend.exprGenerator.SJValueCandidate;
import edSketch.generator.statement.StmtType;
import edSketch.request.generator.SketchGenerator;

public class SketchExpressionGenerator extends SketchGenerator {
	private int index = -1;
	private String toString = "";

	public Object next(SJValueCandidate[] vals) {
		if (index == -1) {
			index = SketchExecutor.choose(vals.length-1);
			toString = vals[index]==null? "null": vals[index].getName()+"\n";
		}
//		System.out.println("select "+vals[index].getName());
		return vals[index].getValue();
	}

	public void reset() {
		index = -1;
	}

	public String toString() {
		return toString;
	}

	@Override @Deprecated
	public void setSketch(List<StmtType> sketchType) {
		// TODO Auto-generated method stub
		
	}
}