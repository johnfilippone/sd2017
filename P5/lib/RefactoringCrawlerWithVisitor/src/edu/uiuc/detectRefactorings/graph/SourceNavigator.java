/*
 * Created on Apr 11, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.uiuc.detectRefactorings.graph;

import org._3pq.jgrapht.edge.EdgeFactories;
import org._3pq.jgrapht.graph.AbstractBaseGraph;
import org._3pq.jgrapht.graph.DirectedMultigraph;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.internal.core.PackageFragment;

import edu.uiuc.detectRefactorings.util.Node;
import edu.uiuc.detectRefactorings.util.ShinglesUtil;

/**
 * @author dig TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SourceNavigator {
	IProgressMonitor monitor;

	AbstractBaseGraph graph;

	EdgeFactories.DirectedEdgeFactory factory;

	public  int apiClasses= 0;
	public  int allClasses= 0;
	public  int packages= 0;
	public  int apiMethods= 0;
	public  int allMethods= 0;
	
	public static boolean useJavadocComments= false;
	
	 private ShinglesUtil shinglesUtil;

	 private String projectName;

	public SourceNavigator(IProgressMonitor monitor) {
		this.monitor = monitor;
		graph = new DirectedMultigraph();
		factory = new EdgeFactories.DirectedEdgeFactory();
	}

	public void browseWorkspace() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IProject[] allProjects = root.getProjects();
		// monitor.beginTask("Creating Graph from Workspace",
		// allProjects.length);

		try {
			for (int i = 0; i < allProjects.length; i++) {
				if (monitor.isCanceled())
					return;
				IProject project = allProjects[i];
				monitor.subTask("Graphing in " + project.getName());
				browseProject(project);
				// monitor.worked(1);
			}
		} finally {
			// monitor.done();
		}
	}

	public void browseProject(String projectName) {
		this.projectName = projectName;
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		// monitor.beginTask("Creating Graph from Project", 1);
		try {
			if (monitor.isCanceled())
				return;
			IProject project = root.getProject(projectName);
			monitor.subTask("Graphing in " + project.getName());
			browseProject(project);
			monitor.worked(1);
		} finally {
			// monitor.done();
		}
	}

	/**
	 * @param project
	 */
	 private void browseProject(IProject project) {
		IJavaProject jProject = JavaCore.create(project);
		if (!jProject.exists()) {
			// TODO handle non-java projects - currently non-Java projects are
			// not visited
			// System.out.println("non Java Project");
		} else {
			Node projNode = new Node(project.getName(), Node.PROJECT);
			graph.addNamedVertex(projNode);
			IPackageFragmentRoot[] roots = null;
			try {
				roots = jProject.getPackageFragmentRoots();
				for (int i = 0; i < roots.length; i++) {
					IPackageFragmentRoot root = roots[i];
					if ((!root.isArchive())
							&& (!(root.getKind() == IPackageFragmentRoot.K_BINARY))) {
						 browseInPackageRoot(root, projNode);
					}
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param root
	 * @param projNode
	 * @throws JavaModelException
	 */
	 private void browseInPackageRoot(IPackageFragmentRoot root, Node projNode)
			throws JavaModelException {
		IJavaElement[] childrenPackages = root.getChildren();
		for (int j = 0; j < childrenPackages.length; j++) {
			PackageFragment pack = (PackageFragment) childrenPackages[j];
			Node packNode = new Node(pack.getElementName(), Node.PACKAGE);
			packNode.setProjectName(projectName);
			graph.addNamedVertex(packNode);
			  graph.addEdge(factory.createEdge(projNode, packNode, Node.PACKAGE));
			 browseInPackage(pack, packNode);
			packages += 1;
		}

	}

	/**
	 * @param pack
	 * @param packNode
	 */
	 private void browseInPackage(PackageFragment pack, Node packNode) {
		try {
			ICompilationUnit[] cUnits = pack.getCompilationUnits();
			for (int i = 0; i < cUnits.length; i++) {
				ICompilationUnit cu = cUnits[i];
				IType primaryType = cu.findPrimaryType();
				if (primaryType != null) {
					Node classNode = new Node(primaryType
							.getFullyQualifiedName('.'), Node.CLASS);
					classNode.setProjectName(projectName);
					classNode.setFlags(primaryType.getFlags());
					graph.addNamedVertex(classNode);
					 
							graph.addEdge(factory.createEdge(packNode, classNode, Node.CLASS));
					 browseClass(primaryType, classNode);
					classNode.setDeprecated(Flags.isDeprecated(classNode
							.getFlags()));
					classNode.setInterface(Flags.isInterface(classNode.getFlags()));
					allClasses += 1;
					// prune away public but not API classes in Eclipse
					if (projectName.indexOf("Eclipse") != -1)
						if(classNode.getFullyQualifiedName().indexOf("internal") != -1)
							continue;
					
					if (Flags.isPublic(primaryType.getFlags())
							|| Flags.isProtected(primaryType.getFlags())) {
						classNode.setAPIStatus();
						apiClasses += 1;
					}
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param primaryType
	 * @param classNode
	 */
	 private void browseClass(IType primaryType, Node classNode) {
		try {
			IMethod[] methods = primaryType.getMethods();
			 browseMethods(classNode, methods);
			IField[] fields = primaryType.getFields();
			 browseFields(classNode, fields);

		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}

	 private void browseFields(Node classNode, IField[] fields)
			throws JavaModelException {
		for (int i = 0; i < fields.length; i++) {
			IField field = fields[i];
			int[] shingles = shinglesUtil.computeMethodShingles(field.getSource());
			String qualifiedName = classNode.getFullyQualifiedName() + "."
					+ field.getElementName();
			Node fieldNode = new Node(qualifiedName, Node.FIELD);
			fieldNode.setProjectName(projectName);
			fieldNode.setShingles(shingles);
			fieldNode.setFlags(field.getFlags());
			fieldNode.setSignature(field.getTypeSignature());
			fieldNode.setDeprecated(Flags.isDeprecated(field.getFlags()));
			graph.addNamedVertex(fieldNode);
			  graph.addEdge(factory.createEdge(classNode, fieldNode, Node.FIELD));
		}
	}

	 private void browseMethods(Node classNode, IMethod[] methods)
			throws JavaModelException {
		for (int i = 0; i < methods.length; i++) {
			IMethod method = methods[i];
			String statementBody = "";
			if (classNode.isInterface() || (useJavadocComments)) {
				statementBody = method.getSource().trim();
			} else
				statementBody = statementBody(method.getSource()).trim();
			int[] shingles = shinglesUtil.computeMethodShingles(statementBody);
			String qualifiedName = classNode.getFullyQualifiedName() + "."
					+ method.getElementName();
			Node methodNode = new Node(qualifiedName, Node.METHOD);
			
			allMethods +=1;
			if (Flags.isPublic(method.getFlags())
					|| Flags.isProtected(method.getFlags()))
				// Eliminate the Public but not API (internal) methods in
				// Eclipse
				if (qualifiedName.indexOf("internal") == -1) {
					{methodNode.setAPIStatus();
					apiMethods += 1;}
				}
			if (Flags.isDeprecated(method.getFlags()))
				methodNode.setDeprecated(true);
			methodNode.setProjectName(projectName);
			methodNode.setShingles(shingles);
			methodNode.setFlags(method.getFlags());
			methodNode.setSignature(getUnqualifiedMethodSignature(method));
			graph.addNamedVertex(methodNode);
			  graph.addEdge(factory
					.createEdge(classNode, methodNode, Node.METHOD));
		}
	}

	 private String getUnqualifiedMethodSignature(IMethod method) {
		StringBuffer buffer = new StringBuffer();

		buffer.append('(');

		String[] types = method.getParameterTypes();
		for (int i = 0; i < types.length; i++) {
			if (i > 0)
				buffer.append(", "); //$NON-NLS-1$
			String typeSig = Signature.toString(types[i]);
			buffer.append(typeSig);
		}
		buffer.append(')');

		return buffer.toString();
	}

	/**
	 * This prunes away the javadoc comments
	 * @param source
	 * @return
	 */
	 private String statementBody(String source) {
		int lastAtChar = source.lastIndexOf("@");
		if (lastAtChar == -1)
			lastAtChar = 0;
		int openingBracket =  source.indexOf("{", lastAtChar);
		int closingBracket = source.lastIndexOf("}");
		if (openingBracket != -1)
			return  source.substring(openingBracket + 1, closingBracket);
		return source;
	}

	/**
	 * @return Returns the graph.
	 */
	public AbstractBaseGraph getGraph() {
		return graph;
	}

	public void setShinglesUtil(ShinglesUtil se) {
		shinglesUtil = se;
	}

	public static void useJavadocComments(boolean useJavadocComments) {
		SourceNavigator.useJavadocComments = useJavadocComments;
	}
}