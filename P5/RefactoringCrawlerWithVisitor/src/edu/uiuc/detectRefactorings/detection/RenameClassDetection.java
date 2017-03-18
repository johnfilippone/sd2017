package edu.uiuc.detectRefactorings.detection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org._3pq.jgrapht.Edge;
import org._3pq.jgrapht.edge.DirectedEdge;
import org._3pq.jgrapht.graph.AbstractBaseGraph;

import edu.uiuc.detectRefactorings.util.Node;

public class RenameClassDetection extends ClassDetection {

	public RenameClassDetection(AbstractBaseGraph graph1,
			AbstractBaseGraph graph2) {
		super(graph1, graph2);
	}

	public double computeLikeliness(Node nodeOriginal, Node nodeVersion) {
		return  doEdgeAnalysis(nodeOriginal, nodeVersion);
	}

	/**
	 * Calls createCallGraph in ClassDetection
	 * Calls filterNamedEdges in ClassDetection
	 * Calls computeLikelinessIncomingEdges in RefactoringDetection
	 * @param nodeOriginal
	 * @param nodeVersion
	 * @return
	 */
	 private double doEdgeAnalysis(Node nodeOriginal, Node nodeVersion) {
		double edgeGrade;
		 createCallGraph(nodeOriginal, nodeVersion);
		List incomingEdgesOriginal = filterNamedEdges(graph1
				.incomingEdgesOf(nodeOriginal));
		List incomingEdgesVersion = filterNamedEdges(graph2
				.incomingEdgesOf(nodeVersion));
		
				edgeGrade = computeLikelinessIncomingEdges(incomingEdgesOriginal, incomingEdgesVersion);
		return edgeGrade;
	}

	public List pruneOriginalCandidates(List candidates) {
		List prePruned = super.pruneOriginalCandidatesImpl(candidates);
		List candidatesWithSameParentPackage = new ArrayList();
		for (Iterator iter = prePruned.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			Node original = pair[0];
			Node version = pair[1];
			String parentPackageOriginal = extractParentSimpleName(original);
			String parentPackageVersion = extractParentSimpleName(version);
			if ( isTheSameModuloRename(parentPackageOriginal, parentPackageVersion)
					&& (!(original.getSimpleName().equals(version
							.getSimpleName())))) {
				candidatesWithSameParentPackage.add(pair);
			}
		}

		return candidatesWithSameParentPackage;
	}

	public boolean accept(Visitor edu_uiuc_detectrefactorings_detection_visitor) {
		return visit();
	}

	public boolean visit() {
		return true;
	}

	
}