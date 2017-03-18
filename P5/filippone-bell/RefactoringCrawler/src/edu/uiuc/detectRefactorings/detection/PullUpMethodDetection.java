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
public class PullUpMethodDetection extends MethodDetection {

	/**
	 * @param graph
	 * @param graph2
	 */
	public PullUpMethodDetection(AbstractBaseGraph graph,
			AbstractBaseGraph graph2) {
		super(graph, graph2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uiuc.detectRefactorings.detection.RefactoringDetection#computeLikeliness(edu.uiuc.detectRefactorings.util.Node,
	 *      edu.uiuc.detectRefactorings.util.Node)
	 */
	/**
	 * We should now check for the same method being in the parent class, thus
	 * for the two nodes, check if the version now resides in the superclass of
	 * the original method's parent class.
	 */
	// TODO: Check why we get a null pointer exception with parentclassver and
	// parentclass orig)
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
