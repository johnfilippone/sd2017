package edu.uiuc.detectRefactorings.detection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org._3pq.jgrapht.graph.AbstractBaseGraph;

import edu.uiuc.detectRefactorings.util.Node;

public class MoveFieldDetection extends FieldDetection {

	/*
	 * We already have Class - Field edges. So we need to make sure that they
	 * are different parents, and also make sure that their call graph is still
	 * the same.
	 */

	public MoveFieldDetection(AbstractBaseGraph graph, AbstractBaseGraph graph2) {
		super(graph, graph2);
	}

	public double computeLikeliness(Node original, Node version) {
		return  analyzeIncomingEdges(original, version);
	}

	public List pruneOriginalCandidates(List candidates) {
		List prePrunnedFields = super.pruneOriginalCandidatesImpl(candidates);
		List candidatesWithDifferentParentClass = new ArrayList();
		for (Iterator iter = prePrunnedFields.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			Node original = pair[0];
			Node version = pair[1];
			String parentClassOriginal = extractFullyQualifiedParentName(original);
			String parentClassVersion = extractFullyQualifiedParentName(version);
			boolean isModRen = 
					isTheSameModuloRename(parentClassOriginal, parentClassVersion);
			
			if (!isModRen
					&& ((original.getSimpleName().equals(version
							.getSimpleName())))) {
				candidatesWithDifferentParentClass.add(pair);
			}
		}
		return candidatesWithDifferentParentClass;
	}

}