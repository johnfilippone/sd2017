package edu.uiuc.detectRefactorings.detection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org._3pq.jgrapht.Edge;
import org._3pq.jgrapht.edge.DirectedEdge;
import org._3pq.jgrapht.edge.EdgeFactories;
import org._3pq.jgrapht.graph.AbstractBaseGraph;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.jdt.core.IMember;

import edu.uiuc.detectRefactorings.DetectRefactoringsPlugin;
import edu.uiuc.detectRefactorings.util.Node;
import edu.uiuc.detectRefactorings.views.RefactoringCategory;

public class MethodDetection extends RefactoringDetection {

	public MethodDetection(AbstractBaseGraph graph, AbstractBaseGraph graph2) {
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
			if (Node.METHOD_CALL.equals(edge.getLabel())) {
				results.add(edge);
			}
		}
		return results;
	}

	protected void createCallGraph(Node node, AbstractBaseGraph graph) {
		List callers = new ArrayList();
		if (this instanceof ChangeMethodSignatureDetection)
			
					 callers = SearchHelper.findMethodCallers(node, new NullProgressMonitor(), true);
		else
			
					 callers = SearchHelper.findMethodCallers(node, new NullProgressMonitor(), false);
		EdgeFactories.DirectedEdgeFactory factory = new EdgeFactories.DirectedEdgeFactory();
		for (Iterator iter = callers.iterator(); iter.hasNext();) {
			IMember element = (IMember) iter.next();
			String nodeName = element.getElementName();
			String qualifiername = element.getDeclaringType()
					.getFullyQualifiedName('.');
			Node caller = graph.findNamedNode(qualifiername + "." + nodeName);
			if (caller != null) {
				Edge edge =   factory.createEdge(caller, node, Node.METHOD_CALL);
				graph.addEdge(edge);
			}
		}
		node.setCreatedCallGraph();

	}

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

	/**
	 * @param original
	 * @param version
	 * @return
	 */

	public double analyzeIncomingEdges(Node original, Node version) {
		double incomingEdgesGrade;
		 createCallGraph(original, version);
		List incomingEdgesOriginal = filterNamedEdges(graph1
				.incomingEdgesOf(original));
		List incomingEdgesVersion = filterNamedEdges(graph2
				.incomingEdgesOf(version));
		
				 incomingEdgesGrade = computeLikelinessIncomingEdges(incomingEdgesOriginal, incomingEdgesVersion);
		return incomingEdgesGrade;
	}

	public boolean accept(Visitor edu_uiuc_detectrefactorings_detection_visitor) {
		return visit();
	}

	public boolean visit() {
		// TODO Auto-generated method stub
		return false;
	}


}