package edu.uiuc.detectRefactorings.detection;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;

import edu.uiuc.detectRefactorings.util.Node;

public class LikelinessVisitor {
	public static final LikelinessVisitor singleton = new LikelinessVisitor();

	/**
	 * We need to go from the node to the AST and get the actual method. Then we
	 * will call getSignature() on the IMethod to get the signature. We have to
	 * make sure the call graphs are checked, since we do not want to detect
	 * polymorphism as change method signature.
	 * @param original TODO
	 * @param version TODO
	 * @param changeMethodSignatureDetection TODO
	 */
	public double visit(Node original, Node version, ChangeMethodSignatureDetection changeMethodSignatureDetection) {
		// Need to find out if in V2 there is a node with the same signature
		// as the original
		if (changeMethodSignatureDetection.isDeprecatedOrRemoved(new Node[] { original, version }))
			return 1.0;
		else {
			double edgeGrade = changeMethodSignatureDetection.analyzeIncomingEdges(original, version);
			// This is when we have a method overload or deprecated. So when
			// we can check deprecated methods we need to add it here
			return edgeGrade;
		}
	}

	public double visit(Node node1, Node node12, MethodDetection methodDetection) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double visit(Node node1, Node node12, ClassDetection classDetection) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double visit(Node node1, Node node12, FieldDetection fieldDetection) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double visit(Node nodeOriginal, Node nodeVersion, MoveClassDetection moveClassDetection) {
		double edgeGrade;
		moveClassDetection.createCallGraph(nodeOriginal, nodeVersion);
		List incomingEdgesOriginal = moveClassDetection.filterNamedEdges(moveClassDetection.graph1
				.incomingEdgesOf(nodeOriginal));
		List incomingEdgesVersion = moveClassDetection.filterNamedEdges(moveClassDetection.graph2
				.incomingEdgesOf(nodeVersion));
		edgeGrade = moveClassDetection.computeLikelinessIncomingEdges(incomingEdgesOriginal,
				incomingEdgesVersion);
		return edgeGrade;
	}

	public double visit(Node original, Node version, MoveFieldDetection moveFieldDetection) {
		return moveFieldDetection.analyzeIncomingEdges(original, version);
	}

	public double visit(Node original, Node version, MoveMethodDetection moveMethodDetection) {
		double edgeGrade = 0.0;
	
		double referenceGrade = 0.0;
	
		if (moveMethodDetection.isTargetARenameOfSourceClass(original, version))
			return 0.0;
	
		// FIXME: Potential problem when we subtract 0.01 from reference grade
		referenceGrade = moveMethodDetection.referencesRemoved(original, version);
		edgeGrade = moveMethodDetection.analyzeIncomingEdges(original, version);
		return (edgeGrade + (referenceGrade - 0.01)) / 2.0;
	}

	public double visit(Node node1, Node node12, PackageDetection packageDetection) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * We should now check for the same method being in the parent class, thus
	 * for the two nodes, check if the version now resides in the superclass of
	 * the original method's parent class.
	 * @param original TODO
	 * @param version TODO
	 * @param pullUpMethodDetection TODO
	 */
	// TODO: Check why we get a null pointer exception with parentclassver and
	// parentclass orig)
	public double visit(Node original, Node version, PullUpMethodDetection pullUpMethodDetection) {
		double incomingEdgesGrade = 0.0;
		boolean isSuperclass = false;
		// TODO: Think about possible different cases that this might be
		// an error. pack2.Class1.main vs. pack2.Runner.main, it cannot
		// find it.
		// TODO: Think about the NULL case. Return 0.0 if you find null,
		// since clearly they are not "like" eachother.
		String parentClassOriginal = pullUpMethodDetection.extractFullyQualifiedParentName(original);
		parentClassOriginal = pullUpMethodDetection.extractPotentialRename(parentClassOriginal);
		String parentClassVersion = pullUpMethodDetection.extractFullyQualifiedParentName(version);
		Node parentClassOrig = pullUpMethodDetection.graph2.findNamedNode(parentClassOriginal);
		if (parentClassOrig == null)
			return 0.0;
		Node parentClassVer = pullUpMethodDetection.graph2.findNamedNode(parentClassVersion);
		// Now we should check if parentClassOrig is a subclass of
		// parentClassVer
		if (ClassDetection.isSuperClassOf(parentClassVer, parentClassOrig))
			isSuperclass = true;
	
		if (isSuperclass) {
			incomingEdgesGrade = pullUpMethodDetection.analyzeIncomingEdges(original, version);
			return incomingEdgesGrade;
		} else
			return 0.0;
	}

	public double visit(Node original, Node version, PushDownMethodDetection pushDownMethodDetection) {
		boolean superClassGrade = false;
		String parentClassOriginal = pushDownMethodDetection.extractFullyQualifiedParentName(original);
		String parentClassVersion = pushDownMethodDetection.extractFullyQualifiedParentName(version);
		parentClassOriginal = pushDownMethodDetection.extractPotentialRename(parentClassOriginal);
		Node parentClassOrig = pushDownMethodDetection.graph2.findNamedNode(parentClassOriginal);
		if (parentClassOrig == null)
			return 0.0;
		Node parentClassVer = pushDownMethodDetection.graph2.findNamedNode(parentClassVersion);
		// Now we should check if parentClassVer is a subclass of
		// parentClassOrig
		if (parentClassOriginal.indexOf("Priority") >= 0
				|| parentClassOriginal.indexOf("Level") >= 0)
			System.out.println("stop");
		if (ClassDetection.isSuperClassOf(parentClassOrig, parentClassVer))
			superClassGrade = true;
		if (superClassGrade) {
			double incomingEdgeGrade = pushDownMethodDetection.analyzeIncomingEdges(original, version);
			return (incomingEdgeGrade);
		} else
			return 0.0;
	}

	public double visit(Node nodeOriginal, Node nodeVersion, RenameClassDetection renameClassDetection) {
		return renameClassDetection.doEdgeAnalysis(nodeOriginal, nodeVersion);
	}

	public double visit(Node original, Node version, RenameMethodDetection renameMethodDetection) {
		// createCallGraph(original, version);
		// return computeLikelinessConsideringEdges(original, version);
		return renameMethodDetection.analyzeIncomingEdges(original, version);
	}

	public double visit(Node original, Node version, RenamePackageDetection renamePackageDetection) {
		renamePackageDetection.createCallerGraph(original, renamePackageDetection.graph1, new NullProgressMonitor());
		renamePackageDetection.createCallerGraph(version, renamePackageDetection.graph2, new NullProgressMonitor());
		List incomingEdgesOriginal = renamePackageDetection.filterNamedEdges(renamePackageDetection.graph1
				.incomingEdgesOf(original));
		List incomingEdgesVersion = renamePackageDetection.filterNamedEdges(renamePackageDetection.graph2
				.incomingEdgesOf(version));
		return renamePackageDetection.computeLikelinessIncomingEdges(incomingEdgesOriginal,
				incomingEdgesVersion);
	}
}
