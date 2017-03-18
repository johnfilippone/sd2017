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
public class MoveClassDetection extends ClassDetection {

	/**
	 * @param graph
	 * @param graph2
	 */
	public MoveClassDetection(AbstractBaseGraph graph, AbstractBaseGraph graph2) {
		super(graph, graph2);
	}

	public double accept(Node nodeOriginal, Node nodeVersion, LikelinessVisitor visitor) {
		return visitor.visit(nodeOriginal, nodeVersion, this);
	}

	public List pruneOriginalCandidates(List candidates) {
		List prePrunnedClasses = super.pruneOriginalCandidatesImpl(candidates);
		List candidatesInDifferentPackages = new ArrayList();
		for (Iterator iter = prePrunnedClasses.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			Node original = pair[0];
			Node version = pair[1];
			String parentPackageOriginal = extractFullyQualifiedParentName(original);
			String parentPackageVersion = extractFullyQualifiedParentName(version);
			if (!isTheSameModuloRename(parentPackageOriginal,
					parentPackageVersion)
					&& ((original.getSimpleName().equals(version
							.getSimpleName())))) {
				candidatesInDifferentPackages.add(pair);
			}
		}
		return candidatesInDifferentPackages;
	}

	public boolean isRename() {
		return false;
	}

}
