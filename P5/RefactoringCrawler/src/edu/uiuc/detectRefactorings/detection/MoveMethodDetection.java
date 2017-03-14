package edu.uiuc.detectRefactorings.detection;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org._3pq.jgrapht.GraphHelper;
import org._3pq.jgrapht.edge.DirectedEdge;
import org._3pq.jgrapht.graph.AbstractBaseGraph;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.Flags;

import edu.uiuc.detectRefactorings.DetectRefactoringsPlugin;
import edu.uiuc.detectRefactorings.util.Node;
import edu.uiuc.detectRefactorings.util.ShinglesUtil;
import edu.uiuc.detectRefactorings.views.RefactoringCategory;

public class MoveMethodDetection extends MethodDetection {

	private Node targetClassInVerGraph;

	private Node targetClassInOrigGraph;

	/**
	 * Checks for MoveMethod
	 * 
	 * 1. Check that from the old method, all the references to objects having
	 * the same type as the destination class were removed
	 * 
	 * 2. Check that the new target class is either a previous argument or a
	 * field in the old class
	 * 
	 * 
	 * @param graph1
	 * @param graph2
	 */

	public MoveMethodDetection(AbstractBaseGraph graph1,
			AbstractBaseGraph graph2) {
		super(graph1, graph2);
	}

	public double computeLikeliness(Node original, Node version) {
		double edgeGrade = 0.0;

		double referenceGrade = 0.0;

		if (isTargetARenameOfSourceClass(original, version))
			return 0.0;

		// FIXME: Potential problem when we subtract 0.01 from reference grade
		referenceGrade = referencesRemoved(original, version);
		edgeGrade = analyzeIncomingEdges(original, version);
		return (edgeGrade + (referenceGrade - 0.01)) / 2.0;
	}

	/**
	 * @param original
	 * @param version
	 * @return
	 */
	public boolean isTargetARenameOfSourceClass(Node original, Node version) {
		String sourceInOriginal = extractFullyQualifiedParentName(original);
		String targetInVersion = extractFullyQualifiedParentName(version);
		// treat case 1
		return (isTheSameModuloRename(sourceInOriginal, targetInVersion));
	}

	/**
	 * 1. Check that from the old method, all the references to objects having
	 * the same type as the destination class were removed
	 * 
	 * @param original
	 * @param version
	 * @return
	 */
	public double referencesRemoved(Node original, Node version) {
		String targetInVersion = extractFullyQualifiedParentName(version);

		targetClassInVerGraph = graph2.findNamedNode(targetInVersion);
		targetClassInOrigGraph = graph1.findNamedNode(targetInVersion);
		// treat case 2
		if (targetClassInOrigGraph == null) {
			Dictionary<String, String> dictionary = getRenamingsDictionary();
			Enumeration<String> keys = dictionary.keys();
			for (; keys.hasMoreElements();) {
				String aKey = keys.nextElement();
				String aValue = dictionary.get(aKey);
				if (targetInVersion.equals(aValue)) {
					targetClassInOrigGraph = graph1.findNamedNode(aKey);
				}
			}

			// treat case 3
			if (targetClassInOrigGraph == null)
				return 1.0;

		}

		// treat case 2 and 4
		if (!targetClassInVerGraph.hasCallGraph()) {
			createClassReferenceGraph(targetClassInVerGraph, graph2,
					new NullProgressMonitor());
			targetClassInVerGraph.setCreatedCallGraph();
		}
		if (!targetClassInOrigGraph.hasCallGraph()) {
			createClassReferenceGraph(targetClassInOrigGraph, graph1,
					new NullProgressMonitor());
			targetClassInOrigGraph.setCreatedCallGraph();
		}

		List originalClassReferences = graph1.getAllEdges(original,
				targetClassInOrigGraph);
		List versionClassReferences = graph2.getAllEdges(version,
				targetClassInVerGraph);
		if (originalClassReferences.size() == 0) {
			if (Flags.isStatic(original.getFlags()))
				return 1.0;
			if (isTargetClassAFieldInSourceClass(original,
					targetClassInOrigGraph))
				return 1.0;
			if (versionClassReferences.size() == 0)
				return 1.0;
			if (versionClassReferences.size() != 0)
				return 0.0;
		} else
			return Math
					.abs(((originalClassReferences.size() - versionClassReferences
							.size()) / originalClassReferences.size()));
		return 0.0;
	}

	private boolean isTargetClassAFieldInSourceClass(Node original,
			Node theTargetClassInOrigGraph) {
		Node parentClass = graph1
				.findNamedNode(extractFullyQualifiedParentName(original));
		List edges = graph1.outgoingEdgesOf(parentClass);
		List<Node> fields = new ArrayList<Node>();
		for (Iterator iter = edges.iterator(); iter.hasNext();) {
			DirectedEdge edge = (DirectedEdge) iter.next();
			if (Node.FIELD_REFERENCE.equals(edge.getLabel())) {
				fields.add((Node) edge.getTarget());
			}
		}

		return fields.contains(theTargetClassInOrigGraph);
	}

	public List pruneOriginalCandidates(List candidates) {
		List prePrunnedMethods = super.pruneOriginalCandidatesImpl(candidates);
		List candidatesWithDifferentParentClass = new ArrayList();
		for (Iterator iter = prePrunnedMethods.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			Node original = pair[0];
			Node version = pair[1];

			// Prune toString since the SearchEngine finds all the toString()
			// methods, even those that are called from different classes
			if ("toString".equals(original.getSimpleName()))
				continue;

			String parentClassOriginal = extractFullyQualifiedParentName(original);
			String parentClassVersion = extractFullyQualifiedParentName(version);
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

	/**
	 * Overriden here to prune false positives due to overlapping PullUp and
	 * PushDown detection
	 */
	public List pruneFalsePositives(List listWithFP) {
		List withoutFP = removePairsDetectedInPUM_PDM(listWithFP);
		withoutFP = addPairsFromMMtoPUM_PDM(withoutFP);
		return withoutFP;
	}

	private List addPairsFromMMtoPUM_PDM(List withoutFP) {
		boolean needsOneMorePass= false;
		List addToPUM = new ArrayList<Node[]>();
		List addToPDM = new ArrayList<Node[]>();
		for (Iterator iter = withoutFP.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			String parentClassOfM1 = extractFullyQualifiedParentName(pair[0]);
			String parentClassOfM2 = extractFullyQualifiedParentName(pair[1]);
			Node sourceClass = graph2.findNamedNode(parentClassOfM1);
			Node destinationClass = graph2.findNamedNode(parentClassOfM2);
			if (sourceClass != null && destinationClass != null) {
				if (ClassDetection
						.isSuperClassOf(sourceClass, destinationClass))
					addToPDM.add(pair);
				else if (ClassDetection.isSuperClassOf(destinationClass,
						sourceClass))
					addToPUM.add(pair);
			}
		}

		DetectRefactoringsPlugin plugin = DetectRefactoringsPlugin.getDefault();
		List<RefactoringCategory> refactoringsList = plugin
				.getRefactoringList();

		RefactoringCategory pulledUpCategory = null;
		RefactoringCategory pushedDownCategory = null;
		// TODO this only checks whether we already have such a category
		// created. It might be
		// that such a category has not been created previously (because no
		// results were found
		// for that category. In this case, will need to create a brand new
		// Category object.
		for (Iterator iter = refactoringsList.iterator(); iter.hasNext();) {
			RefactoringCategory category = (RefactoringCategory) iter.next();
			if (category.getName().equals("PulledUpMethods"))
				pulledUpCategory = category;

			else if (category.getName().equals("PushedDownMethods"))
				pushedDownCategory = category;
		}

		for (Iterator iter = addToPDM.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			if (pushedDownCategory != null)
				pushedDownCategory.getRefactoringPairs().add(pair);
			withoutFP.remove(pair);
			System.out.println("Prunned from MM to PDM" + pair[0] + ","
					+ pair[1]);
			needsOneMorePass= true;
		}

		for (Iterator iter = addToPUM.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			if (pulledUpCategory != null)
				pulledUpCategory.getRefactoringPairs().add(pair);
			withoutFP.remove(pair);
			System.out.println("Prunned from MM to PUM" + pair[0] + ","
					+ pair[1]);
			needsOneMorePass= true;
		}
		if (needsOneMorePass)
			return pruneFalsePositives(withoutFP);
		else return withoutFP;
	}

	/**
	 * @param listWithFP
	 * @return
	 */
	private List removePairsDetectedInPUM_PDM(List listWithFP) {
		List prunedList = super.pruneFalsePositives(listWithFP);
		List pairsToRemove = new ArrayList();
		DetectRefactoringsPlugin plugin = DetectRefactoringsPlugin.getDefault();
		List<RefactoringCategory> refactoringsList = plugin
				.getRefactoringList();
		for (Iterator iter = refactoringsList.iterator(); iter.hasNext();) {
			RefactoringCategory category = (RefactoringCategory) iter.next();
			if (category.getName().equals("PulledUpMethods")
					|| category.getName().equals("PushedDownMethods")) {
				for (Iterator iterator = category.getRefactoringPairs()
						.iterator(); iterator.hasNext();) {
					Node[] pair = (Node[]) iterator.next();
					for (Iterator iterator2 = prunedList.iterator(); iterator2
							.hasNext();) {
						Node[] prunedPair = (Node[]) iterator2.next();
						// The OR below takes care about n->1 and 1->n
						// overlappings
						// between PullUp/PushDown and MoveMethod
						if (prunedPair[0] == pair[0]
								|| prunedPair[1] == pair[1])
							pairsToRemove.add(prunedPair);
					}
				}
			}
		}
		for (Iterator iter = pairsToRemove.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			prunedList.remove(pair);
			System.out.println("Prunned in MM" + pair[0] + "," + pair[1]);
		}
		return prunedList;
	}
}
