/**
 * @author Lisa Feb 6, 2017 Sketch4J.java 
 */
package edSketch.request;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import edSketch.executor.SketchExecutor;
import edSketch.request.generator.AbstractRequest;
import edSketch.request.generator.ArithmeticExpressionRequest;
import edSketch.request.generator.ArithmeticRequest;
import edSketch.request.generator.BlockRequest;
import edSketch.request.generator.ConditionRequest;
import edSketch.request.generator.ExpressionRequest;

public class SketchFix {
	private static Map<RequestType, Map<Integer, AbstractRequest>> allHoles = new TreeMap<RequestType, Map<Integer, AbstractRequest>>();

	public SketchFix() {
	}

	public static AbstractRequest COND(Class<?> target, int id) {
		return  createRequest(RequestType.CONDITION, new ConditionRequest(), id);
	}

	public static AbstractRequest BLOCK(Class<?> target, int id) {
		return  createRequest(RequestType.ASSIGN_BLOCK, new BlockRequest(), id);
	}
	
	/**
	 * AOP: +, -, *, /, %
	 */
	public static AbstractRequest AOP(Class<?> target, int id) {
		return createRequest(RequestType.AOP, new ArithmeticRequest(), id);
	}

	public static AbstractRequest EXP(Class<?> target, int id) {
		return createRequest(RequestType.EXP, new ExpressionRequest(), id);
	}

	/**
	 * ROP: ==, !=, >,<,>=,<=
	 */
	public static AbstractRequest ROP(Class<?> target, int id) {
		return   createRequest(RequestType.ROP, new ArithmeticExpressionRequest(), id);
	}

	public static AbstractRequest COND(Object[] obj, int id, String[] str, Class<?> target) {
		return  createRequest(RequestType.CONDITION, new ConditionRequest(obj, str, target), id);
	}
	public static AbstractRequest COND(Object[] obj, int id, String[] str) {
		return  createRequest(RequestType.CONDITION, new ConditionRequest(obj, str), id);
	}
	
	public static AbstractRequest BLOCK(Object[] obj, int id, String[] str, Class<?> target) {
		return  createRequest(RequestType.ASSIGN_BLOCK, new BlockRequest(obj, str, target), id);
	}

	/**
	 * AOP: +, -, *, /, %
	 */
	public static AbstractRequest AOP(Object[] obj, int id, String[] str, Class<?> target) {
		return createRequest(RequestType.AOP, new ArithmeticRequest(obj, str, target), id);
	}

	public static AbstractRequest EXP(Object[] obj, int id, String[] str, Class<?> target) {
		return createRequest(RequestType.EXP, new ExpressionRequest(obj, str, target), id);
	}

	/**
	 * ROP: ==, !=, >,<,>=,<=
	 */
	public static AbstractRequest ROP(Object[] obj, int id, String[] str, Class<?> target) {
		return createRequest(RequestType.ROP, new ArithmeticExpressionRequest(obj, str, target), id);
	}

	private static AbstractRequest createRequest(RequestType type, AbstractRequest request, int id) {
		Map<Integer, AbstractRequest> list = allHoles.getOrDefault(type, new TreeMap<Integer, AbstractRequest>());
		if (list.containsKey(id)) {
			 return list.get(id);
		}
		list.put(id, request);
		allHoles.put(type, list);
		return list.get(id);
	}

	public static void reset() {
		for (Map<Integer, AbstractRequest> sketch : allHoles.values()) {
			for (AbstractRequest r : sketch.values())
				r.reset();
		}
	}

	public static String getString() {
		StringBuilder sb = new StringBuilder();
		for (Entry<RequestType, Map<Integer, AbstractRequest>> type : allHoles.entrySet()) {
			for (Entry<Integer, AbstractRequest> hole : type.getValue().entrySet())
				sb.append(type.getKey() + "-" + hole.getKey() + ":  " + hole.getValue());
		}
		return sb.toString();
	}
}
