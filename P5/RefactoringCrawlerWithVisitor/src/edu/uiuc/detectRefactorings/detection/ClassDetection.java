package edu.uiuc.detectRefactorings.detection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org._3pq.jgrapht.edge.DirectedEdge;
import org._3pq.jgrapht.graph.AbstractBaseGraph;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IType;

import edu.uiuc.detectRefactorings.util.Node;

public class ClassDetection extends RefactoringDetection {

	public ClassDetection(AbstractBaseGraph graph, AbstractBaseGraph graph2) {
		super(graph, graph2);
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

	 List filterNamedEdges(List list) {
		List results = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			DirectedEdge edge = (DirectedEdge) iter.next();
			if (Node.CLASS_REFERENCE.equals(edge.getLabel())) {
				results.add(edge);
			}
		}
		return results;
	}

	/**
	 * We need to find all the places that the original and version classes are
	 * instantiated. We will incorporate this into the likeliness grade.
	 * 
	 * @param original
	 * @param version
	 */
	protected void createCallGraph(Node original, Node version) {
		if (!original.hasCallGraph()) {
			 createCallGraph(original, graph1);
			original.setCreatedCallGraph();
		}
		if (!version.hasCallGraph()) {
			 createCallGraph(version, graph2);
			version.setCreatedCallGraph();
		}
	}

	protected void createCallGraph(Node node, AbstractBaseGraph graph) {
		  createClassReferenceGraph(node, graph, new NullProgressMonitor());
		node.setCreatedCallGraph();
	}

	/**
	 * Accepts two class nodes, and determines if first parameter is a
	 * superclass of the second parameter.
	 * 
	 * @param node1
	 * @param node2
	 * @return bool
	 */
	public static boolean isSuperClassOf(Node node1, Node node2) {
		boolean retVal = false;
		IType[] superClasses =  SearchHelper.findSuperClassesOf(node2, new NullProgressMonitor());
		for (int i = 0; i < superClasses.length; i++) {
			IType type = superClasses[i];
			if (
					type.getFullyQualifiedName().equals(node1.getFullyQualifiedName()))
				return true;
		}
		return retVal;
	}

	public boolean accept(Visitor edu_uiuc_detectrefactorings_detection_visitor) {
		return visit();
	}

	public boolean visit() {
		// TODO Auto-generated method stub
		return false;
	}
	

}