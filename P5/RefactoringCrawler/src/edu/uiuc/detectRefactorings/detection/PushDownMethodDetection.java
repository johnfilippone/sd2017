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
	public double accept(Node original, Node version, LikelinessVisitor visitor) {
		return visitor.visit(original, version, this);
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
			if (!isTheSameModuloRename(parentClassOriginal, parentClassVersion)
					&& ((original.getSimpleName().equals(version
							.getSimpleName())))) {
				candidatesWithDifferentParentClass.add(pair);
			}
		}

		return candidatesWithDifferentParentClass;
	}

	@Override
	public boolean isRename() {
		return false;
	}

}
