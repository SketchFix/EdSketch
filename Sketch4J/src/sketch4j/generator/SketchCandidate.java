/**
 * @author Lisa Nov 26, 2016 EntryCandidates.java 
 */
package sketch4j.generator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import sketch4j.frontend.ast.SJValueCandidate;
import sketch4j.generator.statement.AssignmentCandidate;
import sketch4j.generator.statement.StmtType;

public class SketchCandidate {

	private List<SymbolicCandidate> list = new ArrayList<SymbolicCandidate>();
	private Map<SJValueCandidate, Queue<Integer>> candMap;

	public Object concretize() {
		return list;
	}

	public List<SymbolicCandidate> getList() {
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setExistList(List list) {
		this.list = list;
	}

	public void setList(List<StmtType> sketchType, int varLen) {
		ArrayList<Integer> l = new ArrayList<Integer>();
		for (int i=0;i<varLen;i++)
			l.add(i);
		for (StmtType type : sketchType) {
			switch (type) {
			case ASSIGNMENT:
				AssignmentCandidate assign = new AssignmentCandidate(StmtType.ASSIGNMENT, varLen);
				
//				System.out.println("test setList "+varLen);
				assign.setLHSGroup(l);
				assign.setRHSGroup(l);
				list.add(assign);

				break;
			default:
				break;
			}
		}
	}

	public void setCandMap(Map<SJValueCandidate, Queue<Integer>> map) {
		candMap = map;
		setAssignValueGroup();
	}

	private void setAssignValueGroup() {
		PriorityQueue<SJValueCandidate> q = new PriorityQueue<SJValueCandidate>(10, new Comparator<SJValueCandidate>() {
			@Override
			public int compare(SJValueCandidate o, SJValueCandidate v) {
				return candMap.get(v).size() - candMap.get(o).size();
			}
		});

		for (SJValueCandidate e : candMap.keySet())
			if (e != null)
				q.add(e);
		List<SJValueCandidate> origin = new ArrayList<SJValueCandidate>();
		while (!q.isEmpty())
			origin.add(q.poll());
		if (candMap.containsKey(null))
			origin.add(null);
		int stmtLen = list.size();
		int groupLen = origin.size();
		for (int i = 0; i < stmtLen; i++) {
			List<SJValueCandidate> valGroup = new ArrayList<SJValueCandidate>();
			for (int j = 0; j < groupLen; j++) {
				valGroup.add(origin.get((i + j) % groupLen));
			}
			List<Integer> idList = getIdList(valGroup);
			((AssignmentCandidate) list.get(i)).setLHSGroup(idList);
			if (i > 0)
				((AssignmentCandidate) list.get(i - 1)).setRHSGroup(idList);
		}
		List<SJValueCandidate> valGroup = new ArrayList<SJValueCandidate>();
		for (int j = 0; j < groupLen; j++) {
			valGroup.add(origin.get((stmtLen + j) % groupLen));
		}
		List<Integer> idList = getIdList(valGroup);
		((AssignmentCandidate) list.get(list.size() - 1)).setRHSGroup(idList);
	}

	private List<Integer> getIdList(List<SJValueCandidate> valGroup) {
		List<Integer> list = new ArrayList<Integer>();
		for (SJValueCandidate e : valGroup)
			list.addAll(candMap.get(e));
		return list;
	}
}
