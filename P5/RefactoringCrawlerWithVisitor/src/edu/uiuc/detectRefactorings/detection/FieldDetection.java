/**
 * 
 */
package edu.uiuc.detectRefactorings.detection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org._3pq.jgrapht.edge.DirectedEdge;
import org._3pq.jgrapht.graph.AbstractBaseGraph;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.core.ResolvedSourceField;
import org.eclipse.jdt.internal.core.ResolvedSourceMethod;
import org.eclipse.jdt.internal.core.ResolvedSourceType;
import org.eclipse.jdt.internal.corext.dom.ASTNodes;
import org.eclipse.jdt.internal.ui.JavaPlugin;

import edu.uiuc.detectRefactorings.util.Node;

/**
 * @author Can Comertoglu
 * 
 */
public class FieldDetection extends RefactoringDetection {

	/**
	 * @param graph
	 * @param graph2
	 */
	public FieldDetection(AbstractBaseGraph graph, AbstractBaseGraph graph2) {
		super(graph, graph2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uiuc.detectRefactorings.detection.RefactoringDetection#computeLikeliness(edu.uiuc.detectRefactorings.util.Node,
	 *      edu.uiuc.detectRefactorings.util.Node)
	 */
	@Override
	public double computeLikeliness(Node node1, Node node12) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uiuc.detectRefactorings.detection.RefactoringDetection#pruneOriginalCandidates(java.util.List)
	 */
	@Override
	public List pruneOriginalCandidates(List candidates) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uiuc.detectRefactorings.detection.RefactoringDetection#filterNamedEdges(java.util.List)
	 */
	@Override
	List filterNamedEdges(List list) {
		List results = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			DirectedEdge edge = (DirectedEdge) iter.next();
			if (Node.FIELD_REFERENCE.equals(edge.getLabel())) {
				results.add(edge);
			}
		}
		return results;
	}

	protected void createFieldReferenceGraph(Node originalNode,
			AbstractBaseGraph graph, IProgressMonitor progressMonitor) {
		try {
			final List results = 
					SearchHelper.findFieldReferences(originalNode, progressMonitor);

			for (Iterator iter = results.iterator(); iter.hasNext();) {
				IMember resultNode = (IMember) iter.next();
				String callingNode = null;
				if (resultNode instanceof IMethod) {
					IMethod rsm1 = (IMethod) resultNode;
					callingNode = rsm1.getDeclaringType()
							.getFullyQualifiedName('.');
					callingNode += "." + rsm1.getElementName();
				} else if (resultNode instanceof Initializer) {
					Initializer initializer = (Initializer) resultNode;
					VariableDeclarationFragment fieldDeclarationFragment = 
									(VariableDeclarationFragment) ASTNodes
							.getParent(initializer, VariableDeclarationFragment.class);
					SimpleName simpleName = fieldDeclarationFragment.getName();
					callingNode = resultNode.getDeclaringType()
							.getFullyQualifiedName('.');
					callingNode += "." + simpleName.getFullyQualifiedName();
				}

				Node callerNode = graph.findNamedNode(callingNode);
				if (callerNode != null)
					 
							graph.addEdge(callerNode, originalNode, Node.FIELD_REFERENCE);
			}

		} catch (CoreException e) {
			JavaPlugin.log(e);
		}
	}

	protected void createCallGraph(Node original, Node version) {
		if (!original.hasCallGraph()) {
			 
					createFieldReferenceGraph(original, graph1, new NullProgressMonitor());
			original.setCreatedCallGraph();
		}
		if (!version.hasCallGraph()) {
			 
					createFieldReferenceGraph(version, graph2, new NullProgressMonitor());
			version.setCreatedCallGraph();
		}
	}

	public double analyzeIncomingEdges(Node original, Node version) {
		double incomingEdgesGrade;
		 createCallGraph(original, version);
		List incomingEdgesOriginal = filterNamedEdges(graph1
				.incomingEdgesOf(original));
		List incomingEdgesVersion = filterNamedEdges(graph2
				.incomingEdgesOf(version));
		
				 incomingEdgesGrade = computeLikelinessIncomingEdges(incomingEdgesOriginal, incomingEdgesVersion);
		return incomingEdgesGrade;
	}

	public boolean accept(Visitor edu_uiuc_detectrefactorings_detection_visitor) {
		return visit();
	}

	public boolean visit() {
		return false;
	}

}