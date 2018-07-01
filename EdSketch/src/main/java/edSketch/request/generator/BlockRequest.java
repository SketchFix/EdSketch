/**
 * @author Lisa Jan 15, 2017 ConditionRequest.java 
 */
package edSketch.request.generator;

import java.util.ArrayList;
import java.util.List;

import edSketch.generator.statement.SketchBlockGenerator;
import edSketch.generator.statement.SketchCandidate;
import edSketch.generator.statement.StmtType;
import edSketch.generator.statement.SymbolicCandidate;
import edSketch.validator.SketchCandidateValidator;
@SuppressWarnings("rawtypes")
public class BlockRequest extends AbstractRequest {

public BlockRequest() {}
	public BlockRequest(Object[] inputValues, String[] inputNames, Class<?> targetType) {
		super(inputValues, inputNames, targetType);
		// TODO Auto-generated constructor stub
	}

	private List<SymbolicCandidate> cand = new ArrayList<SymbolicCandidate>();
	private SketchCandidateValidator validator = new SketchCandidateValidator();

	public Object query(Class candClass, String[] names, Object[] vals, int count, int fieldDeref, boolean hasNull) {
		if (!hasInit) {
			initCandidates(candClass, names, vals, fieldDeref, false);
			validator.setCandidates(candidates);
			hasInit = true;
			List<StmtType> stmts = new ArrayList<StmtType>();
			for (int i = 0; i < count; i++)
				stmts.add(StmtType.ASSIGNMENT);
			sketches = new SketchBlockGenerator();
			sketches.setInitVals(candidates);
			sketches.setSketch(stmts);
			cand = ((SketchCandidate) sketches.next(candidates)).getList();
		}
		return this;
	}

	public boolean notValid(List stmts) {
		return validator.notValid(stmts);
	}

	public void reset() {
		for (SymbolicCandidate sc : cand)
			sc.reset();
	}

	public List<SymbolicCandidate> getSymbolicCandidates() {
		return cand;
	}

	@Override
	public Object invoke() {
		// TODO Auto-generated method stub
		return null;
	}

}
