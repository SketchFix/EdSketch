/**
 * @author Lisa Dec 2, 2016 SJExpression.java 
 */
package edSketch.frontend.exprGenerator;

import java.lang.reflect.Field;
import java.util.List;
@SuppressWarnings("rawtypes")
public class SJValueCandidate implements Comparable<SJValueCandidate> {
	private Class candClass;
	private String name;
	private Object value;
	private boolean isNPE;
	private int baseID;
	private List<Field> derefs;

	public SJValueCandidate(Class clazz, int id, List<Field> deref, String name, Object value, boolean isNPE) {
		this.candClass = clazz;
		this.name = name;
		this.value = value;
		this.isNPE = isNPE;
		this.baseID = id;
		derefs = deref;
	}

	public Class getCandClass() {
		return candClass;
	}

	public void setCandClass(Class candClass) {
		this.candClass = candClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isNPE() {
		return isNPE;
	}

	public void setNPE(boolean isNPE) {
		this.isNPE = isNPE;
	}

	public boolean valueMatch(SJValueCandidate cand) {
		return value == null ? cand.getValue() == null : value.equals(cand.getValue());
	}

	public Object getUpdatedValue(Object o) {
		try {
			for (Field f : derefs)
				o = f.get(o);
		} catch (Exception e) {
			isNPE = true;
		}
		value = o;
		return value;
	}

	public int getBaseID() {
		return baseID;
	}

	public void setBaseID(int baseID) {
		this.baseID = baseID;
	}

	public List<Field> getDerefs() {
		return derefs;
	}

	public void setDerefs(List<Field> derefs) {
		this.derefs = derefs;
	}

	public String toString() {
		return name;
	}

	public boolean isVar() {
		return derefs.isEmpty();
	}

	public boolean equals(Object o) {
		return ((SJValueCandidate) o).getName().equals(name);
	}

	public int hashCode() {
		return 0;
	}

	@Override
	public int compareTo(SJValueCandidate o) {
		return name.compareTo(o.getName());
	}

}
