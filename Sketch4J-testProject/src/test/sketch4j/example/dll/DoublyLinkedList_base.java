/**
 * @author Lisa Apr 17, 2016 LinkedList_7.java 
 */
package test.sketch4j.example.dll;

import java.util.List;

import sketch4j.generator.SketchRequest;
//import gov.nasa.jpf.jvm.Verify;
import sketch4j.generator.SymbolicCandidate;
import sketch4j.generator.statement.AssignmentCandidate;
import test.sketch4j.example.dll.testDriver.TestDriver_DoublyLinkedList_addLast;

public abstract class DoublyLinkedList_base {
	public Node header;
	public  int size = 0;
	public Node _e_;
	private String toString = "";
//private boolean pUpdate;
	public DoublyLinkedList_base() {
		header = new Node(0);
		header.next = header.prev = header;
	}

	public void addFirst(int val) {
		assert val != 0;
		Node e = new Node(val);
		e.next = header.next;
		e.prev = header;
		e.prev.next = e;
		e.next.prev = e;
		size++;
	}

	/**
	 * Appends the specified element to the end of this list.
	 */
	public void addLast(int val) {
		assert val != 0;
		Node e = new Node(val);
		e.next = header;
		e.prev = header.prev;
		e.prev.next = e;
		e.next.prev = e;
		size++;
	}

	private int remove(Node e) {
		if (e == header)
			return 0;

		int result = e.val;
		e.prev.next = e.next;
		e.next.prev = e.prev;
		e.next = null;
		e.prev = null;
		e.val = 0;
		size--;
		return result;
	}

	/**
	 * Removes a single instance of the specified element from this queue, if it
	 * is present. More formally, removes an element e such that o.equals(e), if
	 * this queue contains one or more such elements. Returns true if and only
	 * if this queue contained the specified element (or equivalently, if this
	 * queue changed as a result of the call).
	 * 
	 * @param o
	 * @return
	 */
	public boolean remove(int o) {
		assert o != 0;
		for (Node e = header.next; e != header; e = e.next) {
			if (o == e.val) {
				remove(e);
				return true;
			}
		}
		return false;
	}

	public void _BLOCK_(List<SymbolicCandidate> cand) {
	
		toString = "counter "+SketchRequest.incrementPointer()+"  ";
		
		for (int i = 0; i < cand.size(); i++) {
			switch (cand.get(i).getType()) {
			case ASSIGNMENT:
				assignment((AssignmentCandidate) cand.get(i), (i == 0) ? null : (AssignmentCandidate) cand.get(i - 1),
						2);
				break;
			default:
				break;
			}

		}
//		System.out.println(toString);
	}

	private void assignment(AssignmentCandidate assign, AssignmentCandidate prev, int len) {
		int rid = assign.getJPFRHS();
		int lid = assign.getJPFLHS();
	
	
		toString += SketchRequest.toString(assign) + "   ";
//		System.out.println(lid+" "+rid);
		int[] vids = null;
		if (prev == null)
			vids = new int[] { len, lid, rid };
		else
			vids = new int[] { len, lid, rid, prev.getLHS(), prev.getRHS() };
		if (SketchRequest.notValid(vids)) {
//			System.out.println("prune" + toString);
			TestDriver_DoublyLinkedList_addLast.pruneCounter++;
			SketchRequest.backtrack();
		}
		Node rhs = getRHS(rid);
		switch (lid) {
		case 0:
			_e_ = rhs;
			break;
		case 1:
			header = rhs;
			break;
		case 2:
			_e_.next = rhs;
			break;
		case 3:
			_e_.prev = rhs;
			break;
		case 4:
			header.next = rhs;
			break;
		case 5:
			header.prev = rhs;
			break;
		case 6:
			_e_.next.next = rhs;
			break;
		case 7:
			_e_.next.prev = rhs;
			break;
		case 8:
			_e_.prev.next = rhs;
			break;
		case 9:
			_e_.prev.prev = rhs;
			break;
		case 10:
			header.next.next = rhs;
			break;
		case 11:
			header.next.prev = rhs;
			break;
		case 12:
			header.prev.next = rhs;
			break;
		case 13:
			header.prev.prev = rhs;
			break;
		}
		
	}

	private Node getRHS(int rid) {
		switch (rid) {
		case 0:	return _e_;
		case 1: return 	header;
		case 2: return _e_.next;
		case 3:return _e_.prev ;
		case 4: return header.next ;
		case 5: return header.prev ;
		case 6: return	_e_.next.next;
		case 7: return	_e_.next.prev ;
		case 8: return	_e_.prev.next;
		case 9:return _e_.prev.prev;
		case 10: return	header.next.next ;
		case 11: return header.next.prev;
		case 12:return header.prev.next ;
		case 13:return header.prev.prev ;
		}
		return null;
//		return (Node)SketchRequest.getCandidate(rid).getValue();
	}

	public String toString() {
		return toString;
	}
}
