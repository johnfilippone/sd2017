package edu.uiuc.detectRefactorings.detection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org._3pq.jgrapht.Edge;
import org._3pq.jgrapht.edge.DirectedEdge;
import org._3pq.jgrapht.graph.AbstractBaseGraph;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IType;

import edu.uiuc.detectRefactorings.util.Node;

public class PackageDetection extends RefactoringDetection {

	public PackageDetection(AbstractBaseGraph graph, AbstractBaseGraph graph2) {
		super(graph, graph2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double computeLikeliness(Node node1, Node node12) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List pruneOriginalCandidates(List candidates) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List filterNamedEdges(List list) {
		List results = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			DirectedEdge edge = (DirectedEdge) iter.next();
			if (Node.IMPORT.equals(edge.getLabel())) {
				results.add(edge);
			}
		}
		return results;
	}

	@Override
	public boolean isRename() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Accepts two class nodes, and determines if first parameter is a
	 * superclass of the second parameter.
	 * 
	 * @param node1
	 * @param node2
	 * @return bool
	 */
	protected boolean isSuperClassOf(Node node1, Node node2) {
		boolean retVal = false;
		IType[] superClasses = SearchHelper.findSuperClassesOf(node2,
				new NullProgressMonitor());
		for (int i = 0; i < superClasses.length; i++) {
			IType type = superClasses[i];
			if (type.getFullyQualifiedName().equals(
					node1.getFullyQualifiedName()))
				return true;
		}
		return retVal;
	}

	// Override this in package detection to take account not the fully
	// qualified names but the simplenames for the class nodes in import
	// statements
	public double computeLikelinessIncomingEdges(List edges1, List edges2) {
		double count = 0;

		Edge[] arrEdge2 = (Edge[]) edges2.toArray(new Edge[edges2.size()]);

		for (Iterator iter = edges1.iterator(); iter.hasNext();) {
			Edge edge1 = (Edge) iter.next();
			Node node1 = (Node) edge1.getSource();
			for (int i = 0; i < arrEdge2.length; i++) {
				Edge edge2 = arrEdge2[i];
				if (edge2 != null) {
					Node node2 = (Node) edge2.getSource();
					if (node1.getSimpleName().equals(node2.getSimpleName())) {
						count++;
						// we mark this edge as already counted so that we don't
						// count it
						// twice when there are multiple edges between two nodes
						arrEdge2[i] = null;
					}
				}
			}
		}

		double fraction1 = (edges1.size() == 0 ? 0 : count / edges1.size());
		double fraction2 = edges2.size() == 0 ? 0 : count / edges2.size();

		return (fraction1 + fraction2) / 2.0;
	}

}
