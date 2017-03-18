package edu.uiuc.detectRefactorings.detection;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org._3pq.jgrapht.Edge;
import org._3pq.jgrapht.edge.DirectedEdge;
import org._3pq.jgrapht.graph.AbstractBaseGraph;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.internal.core.ResolvedSourceField;
import org.eclipse.jdt.internal.core.ResolvedSourceMethod;
import org.eclipse.jdt.internal.core.ResolvedSourceType;
import org.eclipse.jdt.internal.corext.dom.ASTNodes;
import org.eclipse.jdt.internal.ui.JavaPlugin;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import edu.uiuc.detectRefactorings.util.Node;
import edu.uiuc.detectRefactorings.util.ShinglesUtil;

public abstract class RefactoringDetection {

	 private double threshold;

	protected AbstractBaseGraph graph1;

	protected AbstractBaseGraph graph2;

	 private double lowerThreshold;

	// protected static List potentialRenames;

	/**
	 * Dictionary contains <Original, Version> pairs for the renamings.
	 */
	 static private Dictionary<String, String> renamingsDictionary;

	public RefactoringDetection(AbstractBaseGraph graph,
			AbstractBaseGraph graph2) {
		this.graph1 = graph;
		this.graph2 = graph2;
	}

	public abstract double computeLikeliness(Node node1, Node node12);

	/**
	 * TEMPLATE METHOD Describes the algorithm for detecting any particular
	 * refactoring The original candidates are prunned (for getting rid of
	 * obvious extraneous ones, then the likeliness of each pair is computed. In
	 * the end we eliminate FalsePositives. Subclasses must override
	 * computeLikeliness and pruneOriginalCandidates.
	 * 
	 * @param candidates
	 * @param monitor
	 * @param unitOfWorkPerRefactoring
	 * @return
	 */
	public List detectRefactorings(List candidates, IProgressMonitor monitor,
			int unitOfWorkPerRefactoring) {

		List<Node[]> refactoredNodes = new ArrayList<Node[]>();
		List listWithFP =  
				 doDetectRefactorings(candidates, refactoredNodes, monitor, unitOfWorkPerRefactoring);
		monitor.subTask("Prune false positives");
		return pruneFalsePositives(listWithFP);
	}

	/**
	 * 
	 * @param listWithFP
	 * @return
	 * 
	 * Alternative would be to check whether n1InV2 has the same call graph as
	 * n1InV1. If it has, then it means that the refactoring did not take place.
	 */
	public List pruneFalsePositives(List listWithFP) {
		List nodesToRemove = new ArrayList<Node[]>();
		for (Iterator iter = listWithFP.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			Node original = pair[0];
			Node version = pair[1];
			Node originalInV2 =  findNamedNodeWithSignature(graph2, original);
			if (originalInV2 != null) {
//				//drastic method to prune away all the methods that are not deprecated in 
//				//the second version
//				if (!originalInV2.isDeprecated()) {
//					nodesToRemove.add(pair);
//					continue;
//				}
					
				//if (!originalInV2.hasCallGraph())
					 createCallGraph(originalInV2, graph2);
				List origIncomingEdges = filterNamedEdges(graph2
						.incomingEdgesOf(originalInV2));
				List verIncomingEdges = filterNamedEdges(graph2
						.incomingEdgesOf(version));
				List origInVer1IncomingEdges = filterNamedEdges(graph1
						.incomingEdgesOf(original));


				List<Node> origInV2Callers = getCallers(origIncomingEdges);
				List<Node> verCallers= getCallers(verIncomingEdges);
				List<Node> origInV1Callers= getCallers(origInVer1IncomingEdges);

				// remove those pairs where N1InV2 has at least one call site as N2inV2.
				// since a call site cannot be calling both the old and the new entity at the same time
				for (Iterator iterator = verCallers.iterator(); iterator
						.hasNext();) {
					Node node = (Node) iterator.next();
					if (origInV2Callers.contains(node))
						if (!nodesToRemove.contains(pair)) {
							System.out.println("1st Prune in RD:" + pair[0] + pair[1]);
							nodesToRemove.add(pair);
						}
				}
				
				//check to see whether the N1inV1 has at least one call site as N1inV2. If it has, then the pair
				//is a false positive (since there should be either no more callers for N1inV2 or their call sites
				// should be different
				for (Iterator iterator = origInV1Callers.iterator(); iterator
						.hasNext();) {
					Node node = (Node) iterator.next();
					for (Iterator iterator1 = origInV2Callers.iterator(); iterator1
							.hasNext();) {
						Node callingNode = (Node) iterator1.next();
						if (node.getFullyQualifiedName().equals(callingNode.getFullyQualifiedName())) {
							if (!nodesToRemove.contains(pair)) {
								System.out.println("2nd Prune in RD:" + pair[0] + pair[1]);
								nodesToRemove.add(pair);
							}
							break;
						}
					}
						
				}

			}
		}
		for (Iterator iter = nodesToRemove.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			listWithFP.remove(pair);
			Dictionary<String, String> dictionary = getRenamingsDictionary();
			dictionary.remove(pair[0].getFullyQualifiedName());
		}

		pruneOverloadedMethodFP(listWithFP);

		return listWithFP;
	}

	/**
	 * @param incomingEdges
	 * @return
	 */
	 private List<Node> getCallers(List incomingEdges) {
		List<Node> callers = new ArrayList<Node>();
		for (Iterator iterator = incomingEdges.iterator(); iterator
				.hasNext();) {
			Edge edge = (Edge) iterator.next();
			callers.add((Node) edge.getSource());
		}
		return callers;
	}

	/**
	 * This prunes cases like m(i) -> m'(i)
	 *                        m(i) -> m'(S)
	 *                        
	 * This method prunes away the pair m(i)->m'(S) since it is likely that this is 
	 * generated because of a method overload.
	 * 
	 * This method is never called in the ChangeMethodSignature detection. 
	 * @param listWithFP
	 */
	 private void pruneOverloadedMethodFP(List listWithFP) {
		List nodesToRemove = new ArrayList<Node[]>();
		for (int i = 0; i < listWithFP.size(); i++) {
			boolean hasSameNameAndSignature = false;
			Node[] pair = (Node[]) listWithFP.get(i);
			Node source = pair[0];
			for (int j = i; j < listWithFP.size(); j++) {
				Node[] pair2 = (Node[]) listWithFP.get(j);
				Node source2 = pair2[0];
				if (source.equals(source2)) {
					Node target2 = pair2[1];
					if (source.getSimpleName().equals(target2.getSimpleName()))
//						if (source.getSignature()
//								.equals(target2.getSignature()))
						if( signatureEqualsModuloMoveMethod(source, target2))
							hasSameNameAndSignature = true;
				}
			}
			if (hasSameNameAndSignature) {
				for (int j = i; j < listWithFP.size(); j++) {
					Node[] pair2 = (Node[]) listWithFP.get(j);
					Node source2 = pair2[0];
					if (source.equals(source2)) {
						Node target2 = pair2[1];
						if (
								source.getSimpleName().equals(target2.getSimpleName()))
							if ( !signatureEqualsModuloMoveMethod(source, target2))
								if (!nodesToRemove.contains(pair2))
									nodesToRemove.add(pair2);
					}
				}
			}

		}
		for (Iterator iter = nodesToRemove.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			System.out.println("2nd REMOVE: " + pair[0] + ", " + pair[1]);
			listWithFP.remove(pair);

			Dictionary<String, String> dictionary = getRenamingsDictionary();
			dictionary.remove(pair[0].getFullyQualifiedName());
		}
	}

	/**
	 * This takes into account the possible renamings in the parent of the node
	 * @param g is the Version2 graph
	 * @param original is a node from Version1
	 * @return
	 */
	 private Node findNamedNodeWithSignature(AbstractBaseGraph g, Node original) {
		Dictionary<String, String> dictionary = getRenamingsDictionary();
		String fqnParent = extractFullyQualifiedParentName(original);
		String possiblyRenamedFQN= dictionary.get(fqnParent);
		if (possiblyRenamedFQN != null)
			fqnParent= possiblyRenamedFQN;
		Node parentNode = g.findNamedNode(fqnParent);
		
		if (parentNode != null) {
			List parentEdges = g.outgoingEdgesOf(parentNode);
			List filteredEdges =  filterNamedEdges(parentEdges, Node.METHOD);
			for (Iterator iter = filteredEdges.iterator(); iter.hasNext();) {
				Edge edge = (Edge) iter.next();
				Node child = (Node) edge.getTarget();
				if (original.getSimpleName().equals(child.getSimpleName()))
					if (original.getSignature() != null) {
						// This handles the method nodes
						if (original.getSignature()
								.equals(child.getSignature()))
							return child;
					} else
						// Classes and packages
						return child;
			}
		}
		return null;
	}


	/**
	 * eg. IWorkbenchPage.openEditor(IFile) is signatureEqualsModuloMoveMethod
	 * IDE.openEditor(IWorkbenchPage, IFile)
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	 private boolean signatureEqualsModuloMoveMethod(Node source, Node target) {
		boolean retval = false;
		
		if (source.getSignature() == null)
			return false;
		
		retval = source.getSignature().equals(target.getSignature());
		
		if (!retval && (this instanceof MoveMethodDetection)) {
			String sourceParent = extractParentSimpleName(source);
			StringTokenizer sourceTokenizer = new StringTokenizer(source
					.getSignature(), "( , )");
			StringTokenizer targetTokenizer = new StringTokenizer(target
					.getSignature(), "( , )");
			String[] sourceTokens = new String[sourceTokenizer.countTokens()];
			String[] targetTokens = new String[targetTokenizer.countTokens()];

			for (int i = 0; i < sourceTokens.length; i++) {
				sourceTokens[i] = sourceTokenizer.nextToken();
			}

			for (int i = 0; i < targetTokens.length; i++) {
				targetTokens[i] = targetTokenizer.nextToken();
			}

			if (targetTokens.length == sourceTokens.length + 1) {
				if (!targetTokens[0].trim().equals(sourceParent))
					return false;
				else {
					for (int i = 0; i < sourceTokens.length; i++) {
						if (
								!sourceTokens[i].trim().equals(targetTokens[i + 1].trim()))
							return false;
					}
					retval = true;
				}
			}
		}
		return retval;
	}
	
	protected void createCallGraph(Node originalInV2, AbstractBaseGraph graph22) {

	}

	/**
	 * @param candidates
	 * @param monitor
	 * @param unitOfWorkPerRefactoring
	 * @param howManyTimes
	 * @return
	 */
	 private List doDetectRefactorings(List candidates, List refactoredNodes,
			IProgressMonitor monitor, int unitOfWorkPerRefactoring) {
		// List<Node[]> potentialRefactorings = new ArrayList<Node[]>();
		List prunnedCandidates = pruneOriginalCandidates(candidates);
		boolean foundNewRefactoring = false;
		int count = 0;
		int size = prunnedCandidates.size();
		int tenth = size / 10;
		IProgressMonitor submonitor = new SubProgressMonitor(monitor,
				unitOfWorkPerRefactoring);
		String msg = "Analyze " + size + " " + this.getClass().getSimpleName()
				+ " Candidates ";
		System.out.println(msg);
		 submonitor.beginTask(msg, 100);
		submonitor.subTask(msg);
		for (Iterator iter = prunnedCandidates.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			Node original = pair[0];
			Node version = pair[1];
			count++;
			if ((count == tenth) || (count == tenth * 2)
					|| (count == tenth * 3) || (count == tenth * 4)
					|| (count == tenth * 5) || (count == tenth * 6)
					|| (count == tenth * 7) || (count == tenth * 8)
					|| (count == tenth * 9)) {
				submonitor.worked(10);
			}
			submonitor.subTask("Analyzed " + count + " of " + size + " "
					+ this.getClass().getSimpleName() + " candidates");

			if (monitor.isCanceled())
				return new ArrayList(0);
			System.out.println("counter= " + count + " "
					+ original.getFullyQualifiedName() + " "
					+ version.getFullyQualifiedName());

			double likeliness =  computeLikeliness(original, version);
			if (likeliness >= threshold) {
				if (!refactoredNodes.contains(pair)) {
					refactoredNodes.add(pair);
					foundNewRefactoring = true;
				}
				// candidates.remove(pair); acivating this line would fail to
				// detect those cases when two
				// types of refactorings happened to the same node

				updateFeedbackLoop(pair);

			}
			// else if (likeliness >= lowerThreshold) {
			// potentialRefactorings.add(pair);
			//
			// }

		}

		if (!foundNewRefactoring) {
			submonitor.done();
			return refactoredNodes;
		} else {
			  
					doDetectRefactorings(candidates, refactoredNodes, submonitor, unitOfWorkPerRefactoring);
			return refactoredNodes;
		}
	}

	public abstract List pruneOriginalCandidates(List candidates);

	/**
	 * A default implementation that prunes all those candidates that have the
	 * same qualified name. Subclasses might reuse this when they implement the
	 * abstract pruneOriginalCanditates, or they can augment to this initial
	 * implementation.
	 * 
	 * @param candidates
	 * @return
	 */
	public List pruneOriginalCandidatesImpl(List candidates) {
		List prunnedCandidates = new ArrayList();
		for (Iterator iter = candidates.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			if (!(pair[0].getFullyQualifiedName().equals(pair[1]
					.getFullyQualifiedName()))) {

				if (pair[0].isAPI() && pair[1].isAPI()) {
					Node n2inV1 = graph1.findNamedNode(pair[1]
							.getFullyQualifiedName());

					if ((n2inV1 == null)) {
						prunnedCandidates.add(pair);
					}
				}
			}
		}
		return prunnedCandidates;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
		// TODO lower threshold is hardcoded here. There should be APIs so that
		// user can
		// specify what is the lower threshold
		lowerThreshold = threshold * .6;
	}

	public double getThreshold() {
		return threshold;
	}

	public double computeLikelinessIncomingEdges(List edges1, List edges2) {
		double count = 0;

		Edge[] arrEdge2 = (Edge[]) edges2.toArray(new Edge[edges2.size()]);

		for (Iterator iter = edges1.iterator(); iter.hasNext();) {
			Edge edge1 = (Edge) iter.next();
			Node node1 = (Node) edge1.getSource();
			for (int i = 0; i < arrEdge2.length; i++) {
				Edge edge2 = arrEdge2[i];
				if (edge2 != null) {
					Node node2 = (Node) edge2.getSource();
					if (
							isTheSameModuloRename(node1.getFullyQualifiedName(), node2.getFullyQualifiedName())) {
						count++;
						// we mark this edge as already counted so that we don't
						// count it
						// twice when there are multiple edges between two nodes
						arrEdge2[i] = null;
					}
				}
			}
		}

		double fraction1 = (edges1.size() == 0 ? 0 : count / edges1.size());
		double fraction2 = edges2.size() == 0 ? 0 : count / edges2.size();

		return (fraction1 + fraction2) / 2.0;
	}

	protected void checkCanceled(IProgressMonitor progressMonitor) {
		if (progressMonitor != null && progressMonitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}

	/**
	 * This helper method takes a string containing the dot separated name of a
	 * node and it returns the subtring from the beginning up to the last dot
	 * (e.g. for pack1.class1.method1 it returns class1)
	 * 
	 * @param original
	 * @return
	 */
	protected String extractParentSimpleName(Node original) {
		String originalName = original.getFullyQualifiedName();
		String parentName =  originalName.substring(0, originalName
				.lastIndexOf("."));
		
				parentName = parentName.substring(parentName.lastIndexOf(".") + 1, parentName.length());
		return parentName;
	}

	protected String extractFullyQualifiedParentName(Node original) {
		String originalName = original.getFullyQualifiedName();
		return extractFullyQualifiedParentName(originalName);
	}

	/**
	 * @param originalName
	 * @return
	 */
	public String extractFullyQualifiedParentName(String originalName) {
		String fq_parentName = "";
		int lastIndex = originalName.lastIndexOf(".");
		if (lastIndex > 0)
			 fq_parentName = originalName.substring(0, lastIndex);
		return fq_parentName;
	}

	/**
	 * The client is assumed to be passing it's parent to this method to
	 * determine if they are Modulo Renames of each other.
	 * 
	 * @param original
	 * @param version
	 * @return
	 */
	protected boolean isTheSameModuloRename(String original, String version) {
		Dictionary<String, String> dictionary = getRenamingsDictionary();
		if (version.equals(dictionary.get(original)))
			return true;
		if (original.lastIndexOf(".") == -1 || version.lastIndexOf(".") == -1)
			return original.equals(version);
		else if (
				
				 original.substring(original.lastIndexOf("."), original.length()).equals(version.substring(version.lastIndexOf("."), version.length())))
			return 
					
					isTheSameModuloRename(extractFullyQualifiedParentName(original), extractFullyQualifiedParentName(version));
		else
			return false;
	}

	abstract List filterNamedEdges(List list);

	public static Dictionary<String, String> getRenamingsDictionary() {
		if (renamingsDictionary == null)
			renamingsDictionary = new Hashtable<String, String>();
		return renamingsDictionary;
	}

	public static void resetRenamingsDictionary() {
		renamingsDictionary = null;
	}

	// VIP Make sure that we don't call this method before clearing the rename
	// dictionary
	public static void seedRenamingsDictionary(String[][] pairs) {
		for (int i = 0; i < pairs.length; i++) {
			String[] renamePair = pairs[i];
			Dictionary<String, String> dictionary = getRenamingsDictionary();
			 dictionary.put(renamePair[0], renamePair[1]);
		}
	}

	/**
	 * 
	 * @param pair
	 */
	 private void updateFeedbackLoop(Node[] pair) {
		if (accept(edu.uiuc.detectRefactorings.detection.Visitor.instance)) {
			Node original = pair[0];
			Node renamed = pair[1];
			Dictionary<String, String> dict = getRenamingsDictionary();
			 dict.put(original.getFullyQualifiedName(), renamed
					.getFullyQualifiedName());
		}
	}

	public abstract boolean accept(Visitor edu_uiuc_detectrefactorings_detection_visitor);

	protected String extractPotentialRename(String parentClassOriginal) {
		String renamedName = getRenamingsDictionary().get(parentClassOriginal);
		return renamedName == null ? parentClassOriginal : renamedName;
	}

	protected void createClassReferenceGraph(Node originalNode,
			AbstractBaseGraph graph, IProgressMonitor progressMonitor) {
		try {
			final List results = 
					SearchHelper.findClassReferences(originalNode, progressMonitor);

			// Possible change to methods that instantiate classes
			// from class -> class edges.
			for (Iterator iter = results.iterator(); iter.hasNext();) {
				IJavaElement resultNode = (IJavaElement) iter.next();
				String callingNode = null;
				if (resultNode instanceof IMethod) {
					IMethod rsm1 = (IMethod) resultNode;
					callingNode = rsm1.getDeclaringType()
							.getFullyQualifiedName('.');
					callingNode += "." + rsm1.getElementName();
				} else if (resultNode instanceof IType) {
					IType rst = (IType) resultNode;
					callingNode = rst.getFullyQualifiedName('.');
				} else if (resultNode instanceof IField) {
					IField rsf1 = (IField) resultNode;
					// Workaround
					callingNode = rsf1.getDeclaringType()
							.getFullyQualifiedName('.');
					callingNode += ".";
					callingNode += rsf1.getElementName();
				} else if (resultNode instanceof Initializer) {
					Initializer initializer = (Initializer) resultNode;
					VariableDeclarationFragment fieldDeclarationFragment = 
									(VariableDeclarationFragment) ASTNodes
							.getParent(initializer, VariableDeclarationFragment.class);
					SimpleName simpleName = fieldDeclarationFragment.getName();
					IType parentType = (IType) ASTNodes.getEnclosingType(initializer);
					callingNode = parentType.getFullyQualifiedName('.');
					callingNode += "." + simpleName.getFullyQualifiedName();
				}

				// TODO treat the case when resultNode is instance of
				// ImportDeclaration
				// TODO treat the case when resultNode is instance of
				// Initializer
				// this appears in Loj4j1.3.0 in class LogManager, references to
				// Level
				if (callingNode == null) {
					System.out.print("");
				}
				if (callingNode != null) {
					Node callerNode = graph.findNamedNode(callingNode);
					if (callerNode != null)
						 
								graph.addEdge(callerNode, originalNode, Node.CLASS_REFERENCE);
				}

			}

		} catch (CoreException e) {
			JavaPlugin.log(e);
		}
	}

	protected List filterNamedEdges(List list, String label) {
		List results = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			DirectedEdge edge = (DirectedEdge) iter.next();
			if (label.equals(edge.getLabel())) {
				results.add(edge);
			}
		}
		return results;
	}

}