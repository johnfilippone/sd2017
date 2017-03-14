/**
 * 
 */
package edu.uiuc.detectRefactorings.detection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org._3pq.jgrapht.Edge;
import org._3pq.jgrapht.graph.AbstractBaseGraph;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IMethod;

import edu.uiuc.detectRefactorings.util.Node;

/**
 * @author Can Comertoglu
 * 
 * A change method signature has taken place if two candidates have the
 * following: - Same Name - prune original candidates - Similar content -
 * shingles candidates - Different Signature - Same call graph
 * 
 */
public class ChangeMethodSignatureDetection extends MethodDetection {

	/**
	 * @param graph
	 * @param graph2
	 */
	public ChangeMethodSignatureDetection(AbstractBaseGraph graph,
			AbstractBaseGraph graph2) {
		super(graph, graph2);
	}

	/**
	 * We need to go from the node to the AST and get the actual method. Then we
	 * will call getSignature() on the IMethod to get the signature. We have to
	 * make sure the call graphs are checked, since we do not want to detect
	 * polymorphism as change method signature.
	 */
	public double computeLikeliness(Node original, Node version) {
		// Need to find out if in V2 there is a node with the same signature
		// as the original
		if (isDeprecatedOrRemoved(new Node[] { original, version }))
			return 1.0;
		else {
			double edgeGrade = analyzeIncomingEdges(original, version);
			// This is when we have a method overload or deprecated. So when
			// we can check deprecated methods we need to add it here
			return edgeGrade;
		}
	}

	/**
	 * This will handle the same name condition, explained above.
	 */
	public List pruneOriginalCandidates(List candidates) {
		List prunnedCandidates = new ArrayList();
		for (Iterator iter = candidates.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			if (pair[0].getSignature().equals(pair[1].getSignature()))
				continue;

			if (!(pair[0].isAPI() && pair[1].isAPI()))
				continue;

			boolean hasSameNameAndSignature = hasTheSameSignatureAndName(pair);

			if (hasSameNameAndSignature)
				continue;

			if (isTheSameModuloRename(pair[0].getFullyQualifiedName(), pair[1]
					.getFullyQualifiedName()))
				prunnedCandidates.add(pair);
		}
		return prunnedCandidates;
	}

	/**
	 * @param pair
	 * @return
	 */
	private boolean hasTheSameSignatureAndName(Node[] pair) {
		// TODO here we have to take into account the RenamigsDictionary
		String parentClassOfVersion = extractFullyQualifiedParentName(pair[1]);
		Node n2ParentinV1 = graph1.findNamedNode(parentClassOfVersion);

		boolean hasSameNameAndSignature = false;

		if (n2ParentinV1 != null) {
			// Calling the overloaded method
			List allMethodEdges = filterNamedEdges(graph1
					.outgoingEdgesOf(n2ParentinV1), Node.METHOD);
			for (Iterator iterator = allMethodEdges.iterator(); iterator
					.hasNext();) {
				Edge methodEdge = (Edge) iterator.next();
				Node targetMethod = (Node) methodEdge.getTarget();
				if (targetMethod.getSimpleName()
						.equals(pair[1].getSimpleName())
						&& targetMethod.getSignature().equals(
								pair[1].getSignature()))
					hasSameNameAndSignature = true;
			}
		}
		return hasSameNameAndSignature;
	}

	public boolean isDeprecatedOrRemoved(Node[] pair) {
		Node source = pair[0];
		String parentOfOriginal = extractFullyQualifiedParentName(source);
		parentOfOriginal = extractPotentialRename(parentOfOriginal);
		Node parentOfOriginalInV2 = graph2.findNamedNode(parentOfOriginal);
		boolean isDeprecated = false;
		boolean isRemoved = true;
		if (parentOfOriginalInV2 != null) {
			List methodEdges = filterNamedEdges(graph2
					.outgoingEdgesOf(parentOfOriginalInV2), Node.METHOD);
			for (Iterator iter = methodEdges.iterator(); iter.hasNext();) {
				Edge edge = (Edge) iter.next();
				Node methodNode = (Node) edge.getTarget();
				if (methodNode.getSimpleName().equals(source.getSimpleName())
						&& methodNode.getSignature().equals(
								source.getSignature())) {
					isRemoved = false;
					isDeprecated = methodNode.isDeprecated();
				}

			}
		}

		return isDeprecated || isRemoved;
	}

	@Override
	public List pruneFalsePositives(List listWithFP) {
	   //  List prunnedInParent= super.pruneFalsePositives(listWithFP);
		List goodResults = new ArrayList<Node[]>();

		for (Iterator iter = listWithFP.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			String signatureN1 = pair[0].getSignature();
			String signatureN2 = pair[1].getSignature();
			if (!isTheSameSignature(signatureN1, signatureN2))
				goodResults.add(pair);
		}
		return goodResults;
	}

	private boolean isTheSameSignature(String signatureN1, String signatureN2) {
		// TODO filters out (IPluginDescriptor) with
		// (org.eclipse.core.runtime.IPluginDescriptor)
		// right now this is checked only for case when there is a one argument
		String simpleName1 = extractSimpleName(signatureN1.substring(1,
				signatureN1.length() - 1));
		String simpleName2 = extractSimpleName(signatureN2.substring(1,
				signatureN2.length() - 1));
		return simpleName1.equals(simpleName2);
	}

	private String extractSimpleName(String fqn) {
		int lastIndex = fqn.lastIndexOf(".");
		if (lastIndex < 0)
			return fqn;
		else
			return fqn.substring(lastIndex + 1, fqn.length());
	}

	public boolean isRename() {
		return false;
	}

}
