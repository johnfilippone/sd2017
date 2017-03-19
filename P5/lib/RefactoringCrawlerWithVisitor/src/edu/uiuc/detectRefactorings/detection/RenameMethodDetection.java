package edu.uiuc.detectRefactorings.detection;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;

import org._3pq.jgrapht.graph.AbstractBaseGraph;

import edu.uiuc.detectRefactorings.util.Node;

public class RenameMethodDetection extends MethodDetection {

	public RenameMethodDetection(AbstractBaseGraph graph1,
			AbstractBaseGraph graph2) {
		super(graph1, graph2);
	}

	/**
	 * @param candidates
	 *            List containing clone methods
	 * @return A List containing only the candidate methods that are in the same
	 *         class
	 */
	public List pruneOriginalCandidates(List candidates) {
		List prePrunnedMethods = super.pruneOriginalCandidatesImpl(candidates);
		List candidatesWithSameParentClass = new ArrayList();
		for (Iterator iter = prePrunnedMethods.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			Node original = pair[0];
			Node version = pair[1];
			String parentClassOriginal = extractFullyQualifiedParentName(original);
			String parentClassVersion = extractFullyQualifiedParentName(version);
			if ( isTheSameModuloRename(parentClassOriginal, parentClassVersion)
					&& (!(original.getSimpleName().equals(version
							.getSimpleName()))))
				candidatesWithSameParentClass.add(pair);
		}

		return candidatesWithSameParentClass;
	}

	public double computeLikeliness(Node original, Node version) {
		// createCallGraph(original, version);
		// return computeLikelinessConsideringEdges(original, version);
		return  analyzeIncomingEdges(original, version);
	}

	/**
	 * Prune further for cases that have n-to-1 mappings. 
	 * (eg. {start, end, pointAt} -> getStartConnector) in JHD5.3 )
	 */
	public List pruneFalsePositives(List listWithFP) {
		List prunedList = super.pruneFalsePositives(listWithFP);
		for (int i = 0; i < prunedList.size(); i++) {
			Node[] pair = (Node[]) prunedList.get(i);
			Node target = pair[1];
			String targetName = target.getSimpleName().toLowerCase().trim();
			List allPairsWithSameTarget = new ArrayList<Node[]>();
			for (int j = 0; j < prunedList.size(); j++) {
				Node[] potentialPairTarget = (Node[]) prunedList.get(j);
				Node potentialTarget = potentialPairTarget[1];
				if (target == potentialTarget)
					allPairsWithSameTarget.add(potentialPairTarget);
			}
			if (allPairsWithSameTarget.size() > 1) {
				for (Iterator iter = allPairsWithSameTarget.iterator(); iter
						.hasNext();) {
					Node[] sameTargetPair = (Node[]) iter.next();
					Node sourceNode = sameTargetPair[0];
					String sourceName = sourceNode.getSimpleName()
							.toLowerCase().trim();
					// Changed from || to && and changed the !='s to =='s
					if ((targetName.indexOf(sourceName) == -1)
							&& (sourceName.indexOf(targetName) == -1)) {
						System.out.println("Prunned in Rename: "
								+ sameTargetPair[0] + " ," + sameTargetPair[1]);
						prunedList.remove(sameTargetPair);
						Dictionary<String, String> dictionary = getRenamingsDictionary();
						dictionary.remove(sourceNode.getFullyQualifiedName());
					}
				}
			}
		}
		return prunedList;
	}

	public boolean accept(Visitor edu_uiuc_detectrefactorings_detection_visitor) {
		return visit();
	}

	public boolean visit() {
		return true;
	}

}