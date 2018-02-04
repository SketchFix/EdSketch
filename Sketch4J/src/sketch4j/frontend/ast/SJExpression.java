/**
 * @author Lisa Dec 2, 2016 SJExpression.java 
 */
package sketch4j.frontend.ast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
@SuppressWarnings("rawtypes")
public class SJExpression {

	
	private Class inputType;
	private int fieldDeref = 1;
	/* We need candList for level-order traversal */
	private Queue<SJValueCandidate> q = new LinkedList<SJValueCandidate>();
	private Map<Class, List<SJValueCandidate>> outputMap = new HashMap<Class, List<SJValueCandidate>>();
	private List<SJValueCandidate> lastQuery;

	public SJExpression(Class input) {
		inputType = input;
	}

	public SJExpression(Class input, int deref) {
		inputType = input;
		fieldDeref = deref;
	}

	// lass clazz, String name, Object value, boolean isNPE
	public void setInitVal(String[] names, Object[] vals) {
		outputMap.clear();
		q.clear();
		for (int i = 0; i < names.length; i++) {
			Object o = vals[i];
			Class type;
			if (o == null)
				type = inputType;
			else
				type = o.getClass();
			SJValueCandidate cand = new SJValueCandidate(type, i, new ArrayList<Field>(), names[i], o, false);
			q.add(cand);
			if (!outputMap.containsKey(type))
				outputMap.put(type, new ArrayList<SJValueCandidate>());
			outputMap.get(type).add(cand);
		}
		generateDeref();
	}

	public List<SJValueCandidate> updateValue(Class output, Object[] vals) {
		if (lastQuery == null)
			return new ArrayList<SJValueCandidate>();
		for (SJValueCandidate cand : lastQuery) {
			if (cand.getBaseID() == -1)
				continue;
			cand.getUpdatedValue(vals[cand.getBaseID()]);
		}
		return lastQuery;
	}

	public List<SJValueCandidate> getSJCandidates(Class output) {
		lastQuery = outputMap.get(output);
		if (lastQuery==null) lastQuery = new ArrayList<SJValueCandidate>();
		//handle primitive type int - Integer
		if (output.equals(Integer.class) && outputMap.containsKey(Integer.class))
			lastQuery.addAll(outputMap.get(int.class));
		else if (output.equals(int.class) && outputMap.containsKey(Integer.class))
			lastQuery.addAll(outputMap.get(Integer.class));
//handle reflection limitation for array length
//		if ( (output.equals(char[].class) || output.equals(int[].class) ||output.equals(String[].class) ||output.equals(double[].class)) && output.equals(int.class) )
//			 new SJValueCandidate(f.getType(), base.getBaseID(), list,
//					base.getName() + "." + f.getName(), f.get(base.getValue()), false);
//		System.out.println("test getSJCandidates " + lastQuery.size());
		if (lastQuery == null)
			return new ArrayList<SJValueCandidate>();
		return lastQuery;
	}

	/**
	 * Update candList;
	 */
	private void generateDeref() {

		for (int i = 1; i <= fieldDeref; i++) {
			int size = q.size();
			for (int j = 0; j < size; j++) {
				SJValueCandidate base = q.poll();
				if (base.getCandClass().equals(Integer.class))
					continue;
				Class bc = base.getCandClass();
				//handle special cases
				if ( bc.equals(char[].class) &&base.getValue()!=null ) {
					Class fType = Integer.class;
					SJValueCandidate c = new SJValueCandidate(fType, base.getBaseID(), new ArrayList<Field>(base.getDerefs()),
							base.getName() + ".length" , ((char[]) base.getValue()).length, false);
					if (!outputMap.containsKey(fType))
						outputMap.put(fType, new ArrayList<SJValueCandidate>());
					outputMap.get(fType).add(c);
				}
				
				
				Field[] fields = bc.getFields();
				for (Field f : fields) {
					SJValueCandidate c;
					List<Field> list = new ArrayList<Field>(base.getDerefs());
					list.add(f);
					try {
						c = new SJValueCandidate(f.getType(), base.getBaseID(), list,
								base.getName() + "." + f.getName(), f.get(base.getValue()), false);
					} catch (Exception e) {
						c = new SJValueCandidate(f.getType(), base.getBaseID(), list,
								base.getName() + "." + f.getName(), null, true);
					}
					q.add(c);
					Class cz = c.getCandClass();
					if (!outputMap.containsKey(cz))
						outputMap.put(cz, new ArrayList<SJValueCandidate>());
					outputMap.get(cz).add(c);
				}
			}
		}

	}
}
