package edSketch.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.model.typesystem.Type;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public class ComputeVariableInfo {
	/** The path to the source folder.  This should be specified by the user. */
	public static final String SOURCE_FOLDER_PATH = "src";
	
	public static CompilationUnit findCompilationUnit(String fqn) {
		String sourceFilePath = Paths.get(SOURCE_FOLDER_PATH, fqn.replaceAll("\\.", File.separator) + ".java").toString();
		FileInputStream in = null;
		try {
			in = new FileInputStream(sourceFilePath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File " + sourceFilePath + " not found.");
		}
		return JavaParser.parse(in);
	}
	
	/**
	 * This method is very unstable because it replies on the stack trace
	 * which is easy to mess up with if you change the invocation chain.
	 * @return
	 */
	public static Pair<Class<?>,String> findTypeAndName(int holeId, int order) {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[4];
		String currentMethodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		CompilationUnit cu = findCompilationUnit(ste.getClassName());
		VariableInfoCollector collector = new VariableInfoCollector(currentMethodName, 1, holeId, order);
		collector.visit(cu, null);
		return new Pair<Class<?>, String>(collector.getParamType(), collector.getParamName());
	}
	
	public static class VariableInfoCollector extends VoidVisitorAdapter<Void> {
		
		private String methodName;
		private int argNumber;
		private int holeId;
		private int order;
		private String paramName;
		private Class<?> paramType;
		
		private boolean startSearch;
		private int count;
		
		public VariableInfoCollector(String methodName, int argNumber, int holeId, int order) {
			this.methodName = methodName;
			this.argNumber = argNumber;
			this.holeId = holeId;
			this.order = order;
			this.paramName = null;
			this.startSearch = false;
			this.count = 0;
		}
		
		/**
		 * Try to find the method invocation that introduce holes.  We assume
		 * the method invocations cannot be at the same line for now.
		 * E.g. METHOD_INVOCATION(...); METHOD_INVOCATION(...); // Same line
		 * We also assume the user pass array initialization to the
		 * METHOD_INVOCATION for variables.
		 */
		@Override
		public void visit(MethodCallExpr n, Void arg) {
			super.visit(n, arg);
			if (n.getNameAsString().equals("newBatchInvocation") || n.getNameAsString().equals("newInvocation")) {
				startSearch = n.getArgument(0).toString().equals(String.valueOf(holeId));
			}
			if (startSearch) {
//				System.out.println(n.getNameAsString() + ", " + n.getArguments().size() + ", " + order);
				if (methodName.equals(n.getNameAsString()) && n.getArguments().size() == argNumber && order == count) {
					Expression expr = n.getArgument(0);
					paramName = expr.toString();
					TypeSolver typeSolver = new CombinedTypeSolver(new ReflectionTypeSolver(),
							new JavaParserTypeSolver(new File(SOURCE_FOLDER_PATH)));
					try {
						Type type = JavaParserFacade.get(typeSolver).getType(expr);
						String typeName;
						if (type.isPrimitive()) {
							typeName = abbreviationMap.get(type.describe());
						} else if (type.isArray()) {
							String description = type.describe();
							typeName = description.substring(0, description.indexOf("["));
							if (abbreviationMap.containsKey(typeName)) {
								typeName = abbreviationMap.get(typeName);
							}
							for (int i = 0; i < description.length(); i++) {
								if (description.charAt(i) == '[') {
									typeName = "[" + typeName;
								}
							}
						} else { // Reference
							typeName = type.describe();
						}
						paramType = Class.forName(typeName);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						paramType = null;
					}
					count++;
				}
			}
//			System.out.println(n.getNameAsString() + ", " + methodName.equals(n.getNameAsString()) + ", " + (lineNumber == n.getBegin().get().line) + ", " + (n.getArguments().size() == argNumber));
//			System.out.println(n.getBegin().get().line);
//			if (methodName.equals(n.getNameAsString()) && n.getArguments().size() == argNumber) {
//				int value_index = 0;
//				Expression expr = n.getArgument(value_index);
//				System.out.println(expr);
//				System.out.println(expr.getClass());
//				String initializer = ((ArrayCreationExpr) expr).getInitializer().get().toString();
//				int len = initializer.length();
//				if (len > 2) {
//					String elems = initializer.substring(1, len - 1);
//					for (String elem : elems.split(",")) {
//						parameterNames.add(elem.trim());
//					}
//				}
//			}
		}
		
		public String getParamName() {
			return paramName;
		}
		
		public Class<?> getParamType() {
			return paramType;
		}
	}
	
	/**
	 * For each non-null value, we convert to its class.  For null,
	 * we use Object class.  This implementation requires that we treat
	 * Object type of values assignable from anything.
	 * @param argValues
	 * @return
	 */
	@Deprecated
	public static Class<?>[] getParameterTypes(Object[] argValues) {
		Class<?>[] parameterTypes = new Class<?>[argValues.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			parameterTypes[i] = getParamType(argValues[i]);
		}
		return parameterTypes;
	}
	
	@Deprecated
	public static Class<?> getParamType(Object value) {
		if (value == null) return Object.class;
		return ((Object) value).getClass();
	}
	
	private static final Map<String, String> abbreviationMap;
	@SuppressWarnings("unused")
    private static final Map<String, String> reverseAbbreviationMap;
	
	static {
        final Map<String, String> m = new HashMap<>();
        m.put("int", "I");
        m.put("boolean", "Z");
        m.put("float", "F");
        m.put("long", "J");
        m.put("short", "S");
        m.put("byte", "B");
        m.put("double", "D");
        m.put("char", "C");
        final Map<String, String> r = new HashMap<>();
        for (final Map.Entry<String, String> e : m.entrySet()) {
            r.put(e.getValue(), e.getKey());
        }
        abbreviationMap = Collections.unmodifiableMap(m);
        reverseAbbreviationMap = Collections.unmodifiableMap(r);
    }
}
