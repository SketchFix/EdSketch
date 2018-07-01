/**
 * @author Lisa Dec 2, 2016 SJExpression.java 
 */
package edSketch.frontend.exprGenerator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * This class is messy now to generate candidates, split to builders, to support
 * method call expr and field deref. update valueCandidate correspondingly
 * 
 * @author lisahua
 *
 */
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
		for (int i = 0; i < vals.length; i++) {
			Object o = vals[i];
			Class type;
			// FIXME I know this is bug
			if (o == null) {
				type = inputType;
				if (inputType.isPrimitive())
					continue;
			} else
				type = o.getClass();
			SJValueCandidate cand = new SJValueCandidate(type, i, new ArrayList<Field>(), names[i], o, false);
			q.add(cand);
			if (!outputMap.containsKey(type))
				outputMap.put(type, new ArrayList<SJValueCandidate>());
			outputMap.get(type).add(cand);
		}
		generateDeref();
	}

	public SJValueCandidate[] updateValue(Class output, Object[] vals) {
		if (lastQuery == null)
			return new SJValueCandidate[] {};
		for (SJValueCandidate cand : lastQuery) {
			if (cand.getBaseID() == -1)
				continue;
			cand.getUpdatedValue(vals[cand.getBaseID()]);
		}
		return lastQuery.toArray(new SJValueCandidate[lastQuery.size()]);
	}

	@SuppressWarnings("unchecked")
	public Collection<SJValueCandidate> getSJCandidates(Class output) {
		lastQuery = new ArrayList<SJValueCandidate>();
		java.util.Iterator<Class> itr = outputMap.keySet().iterator();
		while (itr.hasNext()) {
			Class e = itr.next();
			if (output.isAssignableFrom(e)) {
				// FIXME no idea which method call should choose
				lastQuery.addAll(outputMap.get(e));
//				itr.remove();
			}
		}
		// handle primitive type int - Integer
		if (output.equals(Integer.class) || outputMap.containsKey(int.class))
			lastQuery.addAll(outputMap.get(int.class));
		if (output.equals(int.class) && outputMap.containsKey(Integer.class))
			lastQuery.addAll(outputMap.get(Integer.class));
		if (output.equals(Boolean.class) && outputMap.containsKey(boolean.class))
			lastQuery.addAll(outputMap.get(boolean.class));
		if (output.equals(boolean.class) && outputMap.containsKey(Boolean.class))
			lastQuery.addAll(outputMap.get(Boolean.class));
		if (output.equals(Double.class) && outputMap.containsKey(double.class))
			lastQuery.addAll(outputMap.get(double.class));
		 if (output.equals(double.class) && outputMap.containsKey(Double.class))
			lastQuery.addAll(outputMap.get(Double.class));
	 if (output.equals(long.class) && outputMap.containsKey(Long.class))
			lastQuery.addAll(outputMap.get(Long.class));
	if (output.equals(Long.class) && outputMap.containsKey(long.class))
			lastQuery.addAll(outputMap.get(long.class));
	 if (output.equals(float.class) && outputMap.containsKey(Float.class))
			lastQuery.addAll(outputMap.get(Float.class));
	if (output.equals(Float.class) && outputMap.containsKey(float.class))
			lastQuery.addAll(outputMap.get(float.class));
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
				if (base.getCandClass().equals(Integer.class) || base.getName().equals("this"))
					continue;
				Class bc = base.getCandClass();
				// handle special cases: reflection not support array length
				if (bc.equals(char[].class) && base.getValue() != null) {
					Class fType = Integer.class;
					SJValueCandidate c = new SJValueCandidate(fType, base.getBaseID(),
							new ArrayList<Field>(base.getDerefs()), base.getName() + ".length",
							((char[]) base.getValue()).length, false);
					if (!outputMap.containsKey(fType))
						outputMap.put(fType, new ArrayList<SJValueCandidate>());
					outputMap.get(fType).add(c);
				}
				// boolean default value:
				if (bc.equals(boolean.class) || bc.equals(Boolean.class)) {
					Class fType = boolean.class;
					if (!outputMap.containsKey(fType))
						outputMap.put(fType, new ArrayList<SJValueCandidate>());

					// outputMap.get(fType).add(new
					// SJValueCandidate(boolean.class, base.getBaseID(),
					// new ArrayList<Field>(base.getDerefs()), "true", true,
					// false));
					// outputMap.get(fType).add(new
					// SJValueCandidate(boolean.class, base.getBaseID(),
					// new ArrayList<Field>(base.getDerefs()), "false", false,
					// false));
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
