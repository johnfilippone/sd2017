package edu.uiuc.detectRefactorings.detection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org._3pq.jgrapht.graph.AbstractBaseGraph;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.jdt.internal.core.ImportDeclaration;
import org.eclipse.jdt.internal.corext.util.SearchUtils;
import org.eclipse.jdt.internal.ui.JavaPlugin;

import edu.uiuc.detectRefactorings.util.Node;

public class RenamePackageDetection extends PackageDetection {

	public RenamePackageDetection(AbstractBaseGraph graph1,
			AbstractBaseGraph graph2) {
		super(graph1, graph2);
	}

	public double computeLikeliness(Node original, Node version) {
		  createCallerGraph(original, graph1, new NullProgressMonitor());
		  createCallerGraph(version, graph2, new NullProgressMonitor());
		List incomingEdgesOriginal = filterNamedEdges(graph1
				.incomingEdgesOf(original));
		List incomingEdgesVersion = filterNamedEdges(graph2
				.incomingEdgesOf(version));
		return 
				computeLikelinessIncomingEdges(incomingEdgesOriginal, incomingEdgesVersion);
	}
	
	//TODO
	// override in this class computeLikelinessIncomingEdges so that it takes into account
	// only the simple names, not the fully qualified names

	 private void createCallerGraph(Node originalNode, AbstractBaseGraph graph,
			IProgressMonitor progressMonitor) {

		final List results = new ArrayList();
		try {
			SearchRequestor searchRequestor = new SearchRequestor() {
				public void acceptSearchMatch(SearchMatch match)
						throws CoreException {
					results.add(match.getElement());
				}
			};
			SearchEngine searchEngine = new SearchEngine();

			IProgressMonitor monitor = new SubProgressMonitor(progressMonitor,
					95, SubProgressMonitor.SUPPRESS_SUBTASK_LABEL);
			IJavaSearchScope searchScope = SearchHelper
					.createProjectSearchScope(originalNode.getProjectName());
			SearchHelper.checkCanceled(progressMonitor);

			SearchPattern pattern =  
					
					SearchPattern.createPattern(originalNode
					.getFullyQualifiedName(), IJavaSearchConstants.PACKAGE, IJavaSearchConstants.REFERENCES, SearchUtils.GENERICS_AGNOSTIC_MATCH_RULE);
			  
					 searchEngine.search(pattern, new SearchParticipant[] { SearchEngine
					.getDefaultSearchParticipant() }, searchScope, searchRequestor, monitor);

			for (Iterator iter = results.iterator(); iter.hasNext();) {
				IJavaElement element = (IJavaElement) iter.next();
				if (element instanceof ImportDeclaration) {
					ImportDeclaration importDecl = (ImportDeclaration) element;
					ICompilationUnit cu = importDecl.getCompilationUnit();
					IType callingClass = cu.findPrimaryType();
					Node callingClassNode = graph.findNamedNode(callingClass
							.getFullyQualifiedName());
					  graph.addEdge(callingClassNode, originalNode, Node.IMPORT);
				}
			}

		} catch (CoreException e) {
			JavaPlugin.log(e);
		}

	}

	public List pruneOriginalCandidates(List candidates) {
		List prunnedCandidates = new ArrayList();
		for (Iterator iter = candidates.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			// TODO change this to use isTheSameModuloRename whose arguments are
			// the parent packages. VIP treat the special case when the package
			// to analyze does not have any other parent (it's the top level
			// parent package

			if (
					pair[0].getFullyQualifiedName().equals(pair[1].getFullyQualifiedName()))
				continue;
			prunnedCandidates.add(pair);
		}
		return prunnedCandidates;
	}

	public boolean accept(Visitor edu_uiuc_detectrefactorings_detection_visitor) {
		return visit();
	}

	public boolean visit() {
		return true;
	}

}