/**
 * @author Lisa Jan 15, 2017 AbstractRequest.java 
 */
package edSketch.request.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edSketch.frontend.exprGenerator.SJExpressionGenerator;
import edSketch.frontend.exprGenerator.SJValueCandidate;

public abstract class AbstractRequest {
	protected SJValueCandidate[] candidates;
	protected SketchGenerator sketches;
	protected boolean hasInit = false;
	protected SJExpressionGenerator exprGenerator = new SJExpressionGenerator();
	protected List<Class<?>> inputTypes = new ArrayList<>();
	protected List<String> inputNames = new ArrayList<>();
	protected List<Object> inputValues = new ArrayList<>();
	protected Class<?> targetType = null;
	protected int minDepth = 1;
	protected int maxDepth = 4;
	protected boolean hasDefault = false;
	protected int paramLength = 1;

	public AbstractRequest() {
		
	}
	public AbstractRequest(Object[] inputValues, String[] inputNames) {
		this.inputNames = Arrays.asList(inputNames);
		this.inputValues = Arrays.asList(inputValues);
	}
	
	public AbstractRequest(Object[] inputValues, String[] inputNames, Class<?> targetType) {
		this.inputNames = Arrays.asList(inputNames);
		this.inputValues = Arrays.asList(inputValues);
		this.targetType = targetType;
	}
	
	public AbstractRequest addArgument(Class<?> type, String name, Object val) {
		inputTypes.add(type);
		inputNames.add(name);
		inputValues.add(val);
		return this;
	}

	/**
	 * 
	 * @param depth
	 *            number of assignments or number of API calls
	 * @return
	 */
	public AbstractRequest setMaxDepth(int depth) {
		maxDepth = depth;
		return this;
	}

	/**
	 * 
	 * @param val
	 *            if contains default candidates: 0 for int and null for
	 *            non-primitive type
	 * @return
	 */
	public AbstractRequest hasDefault(boolean val) {
		hasDefault = val;
		return this;
	}

	/**
	 * 
	 * @param len
	 *            field dereference for expressions
	 * @return
	 */
	public AbstractRequest setParamLength(int len) {
		paramLength = len;
		return this;
	}

	public AbstractRequest setTargetType(Class<?> type) {
		targetType = type;
		return this;
	}

	public abstract Object invoke();

	// public SJValueCandidate[] getCandidates() {
	// return candidates;
	// }
	//
	// public void setCandidates(SJValueCandidate[] candidates) {
	// this.candidates = candidates;
	// }
	//
	// public boolean getHasInit() {
	// return hasInit;
	// }
	//
	// public void setHasInit(boolean isInit) {
	// hasInit = isInit;
	// }
	//
	// deprecate it later
	protected void initCandidates(Class candClass, String[] names, Object[] vals, int fieldDeref, boolean hasNull) {
		exprGenerator.addTypeVals(candClass, names, vals, fieldDeref);
		candidates = exprGenerator.getSJCandidates(candClass, hasNull);
	}

	public void reset() {
		if (sketches != null)
			sketches.reset();
	}

	public String toString() {
		return sketches == null ? "" : sketches.toString();
	}

	public boolean hasInit() {
		return hasInit;
	}
}
