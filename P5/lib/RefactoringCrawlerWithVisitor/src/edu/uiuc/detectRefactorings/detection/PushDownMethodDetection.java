/**
 * 
 */
package edu.uiuc.detectRefactorings.detection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org._3pq.jgrapht.graph.AbstractBaseGraph;

import edu.uiuc.detectRefactorings.util.Node;

/**
 * @author Can Comertoglu
 * 
 */
public class PushDownMethodDetection extends MethodDetection {

	/**
	 * @param graph
	 * @param graph2
	 */
	public PushDownMethodDetection(AbstractBaseGraph graph,
			AbstractBaseGraph graph2) {
		super(graph, graph2);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uiuc.detectRefactorings.detection.RefactoringDetection#computeLikeliness(edu.uiuc.detectRefactorings.util.Node,
	 *      edu.uiuc.detectRefactorings.util.Node)
	 */
	public double computeLikeliness(Node original, Node version) {
		boolean superClassGrade = false;
		String parentClassOriginal = extractFullyQualifiedParentName(original);
		String parentClassVersion = extractFullyQualifiedParentName(version);
		parentClassOriginal = extractPotentialRename(parentClassOriginal);
		Node parentClassOrig = graph2.findNamedNode(parentClassOriginal);
		if (parentClassOrig == null)
			return 0.0;
		Node parentClassVer = graph2.findNamedNode(parentClassVersion);
		// Now we should check if parentClassVer is a subclass of
		// parentClassOrig
		if (parentClassOriginal.indexOf("Priority") >= 0
				|| parentClassOriginal.indexOf("Level") >= 0)
			System.out.println("stop");
		if ( ClassDetection.isSuperClassOf(parentClassOrig, parentClassVer))
			superClassGrade = true;
		if (superClassGrade) {
			double incomingEdgeGrade =  analyzeIncomingEdges(original, version);
			return (incomingEdgeGrade);
		} else
			return 0.0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uiuc.detectRefactorings.detection.RefactoringDetection#pruneOriginalCandidates(java.util.List)
	 */
	public List pruneOriginalCandidates(List candidates) {
		List prePrunnedMethods = super.pruneOriginalCandidatesImpl(candidates);
		List candidatesWithDifferentParentClass = new ArrayList();
		for (Iterator iter = prePrunnedMethods.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			Node original = pair[0];
			Node version = pair[1];
			String parentClassOriginal = extractParentSimpleName(original);
			String parentClassVersion = extractParentSimpleName(version);
			if ( !isTheSameModuloRename(parentClassOriginal, parentClassVersion)
					&& ((original.getSimpleName().equals(version
							.getSimpleName())))) {
				candidatesWithDifferentParentClass.add(pair);
			}
		}

		return candidatesWithDifferentParentClass;
	}

	public boolean accept(Visitor edu_uiuc_detectrefactorings_detection_visitor) {
		return visit();
	}

	public boolean visit() {
		return false;
	}

}