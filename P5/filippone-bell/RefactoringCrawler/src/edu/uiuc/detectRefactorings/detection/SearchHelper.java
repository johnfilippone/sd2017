package edu.uiuc.detectRefactorings.detection;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.jdt.internal.corext.util.SearchUtils;
import org.eclipse.jdt.internal.ui.JavaPlugin;

import edu.uiuc.detectRefactorings.DetectRefactoringsPlugin;
import edu.uiuc.detectRefactorings.util.Node;

public class SearchHelper {

	public SearchHelper() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * If there is no project name specified for this detector, then it returns
	 * the whole workspace as a search scope. If this detector has specified a
	 * project, then it returns (for efficiency reasons) a limited search scope
	 * (only for the specified project and only in the source folders)
	 * 
	 * @return
	 */
	public static IJavaSearchScope createProjectSearchScope(String projectName) {
		if (projectName == null) {
			return SearchEngine.createWorkspaceScope();
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IProject project = root.getProject(projectName);
		IJavaProject javaProject = JavaCore.create(project);
		Assert.assertNotNull(javaProject);
		return SearchEngine.createJavaSearchScope(
				new IJavaElement[] { javaProject }, IJavaSearchScope.SOURCES);
	}

	public static void checkCanceled(IProgressMonitor progressMonitor) {
		if (progressMonitor != null && progressMonitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}


	public static IType[] findSuperClassesOf(Node originalNode,
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
			IJavaSearchScope searchScope = createProjectSearchScope(originalNode
					.getProjectName());
			checkCanceled(progressMonitor);

			SearchPattern pattern = SearchPattern.createPattern(originalNode
					.getFullyQualifiedName(), IJavaSearchConstants.CLASS,
					IJavaSearchConstants.DECLARATIONS,
					SearchUtils.GENERICS_AGNOSTIC_MATCH_RULE);
			searchEngine.search(pattern, new SearchParticipant[] { SearchEngine
					.getDefaultSearchParticipant() }, searchScope,
					searchRequestor, monitor);
			// at this point results contains the class that we need to find the
			// subclasses of - that is the class the node is used to represent.
			IType[] allSuperClasses = new IType[0];
			if (!results.isEmpty()) {
				IType clasz = (IType) results.get(0);
				allSuperClasses = clasz.newSupertypeHierarchy(monitor)
						.getAllSupertypes(clasz);
			}
			return allSuperClasses;
		} catch (CoreException e) {
			JavaPlugin.log(e);
			return new IType[0];
		}

	}

	/**
	 * @param originalNode
	 * @param progressMonitor
	 * @return
	 * @throws CoreException
	 */
	public static List findClassReferences(Node originalNode,
			IProgressMonitor progressMonitor) throws CoreException {
		final List results = new ArrayList();

		SearchRequestor searchRequestor = new SearchRequestor() {
			public void acceptSearchMatch(SearchMatch match)
					throws CoreException {
				results.add(match.getElement());
			}
		};
		SearchEngine searchEngine = new SearchEngine();

		IProgressMonitor monitor = new SubProgressMonitor(progressMonitor, 95,
				SubProgressMonitor.SUPPRESS_SUBTASK_LABEL);
		IJavaSearchScope searchScope = createProjectSearchScope(originalNode
				.getProjectName());
		checkCanceled(progressMonitor);

		SearchPattern pattern = SearchPattern.createPattern(originalNode
				.getFullyQualifiedName(), IJavaSearchConstants.CLASS,
				IJavaSearchConstants.REFERENCES,
				SearchUtils.GENERICS_AGNOSTIC_MATCH_RULE);
		searchEngine.search(pattern, new SearchParticipant[] { SearchEngine
				.getDefaultSearchParticipant() }, searchScope, searchRequestor,
				monitor);
		return results;
	}

	public static List findFieldReferences(Node originalNode,
			IProgressMonitor progressMonitor) throws CoreException {
		final List results = new ArrayList();
		SearchRequestor searchRequestor = new SearchRequestor() {
			public void acceptSearchMatch(SearchMatch match)
					throws CoreException {
				results.add(match.getElement());
			}
		};
		SearchEngine searchEngine = new SearchEngine();
		IProgressMonitor monitor = new SubProgressMonitor(progressMonitor, 95,
				SubProgressMonitor.SUPPRESS_SUBTASK_LABEL);
		IJavaSearchScope searchScope = createProjectSearchScope(originalNode
				.getProjectName());
		checkCanceled(progressMonitor);

		SearchPattern pattern = SearchPattern.createPattern(originalNode
				.getFullyQualifiedName(), IJavaSearchConstants.FIELD,
				IJavaSearchConstants.REFERENCES,
				SearchUtils.GENERICS_AGNOSTIC_MATCH_RULE);
		searchEngine.search(pattern, new SearchParticipant[] { SearchEngine
				.getDefaultSearchParticipant() }, searchScope, searchRequestor,
				monitor);
		return results;
	}

	// FIXME check if it returns multiple method declarations for the case when
	// there are methods overloaded
	public static List getASTMethod(Node methodNode,
			IProgressMonitor progressMonitor) throws CoreException {
		final List results = new ArrayList();
		SearchRequestor searchRequestor = new SearchRequestor() {
			public void acceptSearchMatch(SearchMatch match)
					throws CoreException {
				results.add(match.getElement());
			}
		};
		SearchEngine searchEngine = new SearchEngine();
		IProgressMonitor monitor = new SubProgressMonitor(progressMonitor, 95,
				SubProgressMonitor.SUPPRESS_SUBTASK_LABEL);
		IJavaSearchScope searchScope = createProjectSearchScope(methodNode
				.getProjectName());
		checkCanceled(progressMonitor);

		SearchPattern pattern = SearchPattern.createPattern(methodNode
				.getFullyQualifiedName(), IJavaSearchConstants.METHOD,
				IJavaSearchConstants.DECLARATIONS,
				SearchUtils.GENERICS_AGNOSTIC_MATCH_RULE);
		searchEngine.search(pattern, new SearchParticipant[] { SearchEngine
				.getDefaultSearchParticipant() }, searchScope, searchRequestor,
				monitor);
		return results;
	}

	public static List findMethodCallers(Node node,
			IProgressMonitor progressMonitor, boolean withSignature) {
		try {
			MethodReferenceSearchRequestor searchRequestor = new MethodReferenceSearchRequestor();
			SearchEngine searchEngine = new SearchEngine();

			IProgressMonitor monitor = new SubProgressMonitor(progressMonitor,
					95, SubProgressMonitor.SUPPRESS_SUBTASK_LABEL);

			IJavaSearchScope searchScope = createProjectSearchScope(node
					.getProjectName());
			checkCanceled(progressMonitor);
			String patternStr = withSignature ? node.getFullyQualifiedName()
					+ node.getSignature() : node.getFullyQualifiedName();

			SearchPattern pattern = SearchPattern.createPattern(patternStr, IJavaSearchConstants.METHOD,
					IJavaSearchConstants.REFERENCES,
					SearchUtils.GENERICS_AGNOSTIC_MATCH_RULE);
			searchEngine.search(pattern, new SearchParticipant[] { SearchEngine
					.getDefaultSearchParticipant() }, searchScope,
					searchRequestor, monitor);

			return searchRequestor.getSearchResults();
		} catch (Exception e) {
			DetectRefactoringsPlugin.getDefault().getLog().log(
					new Status(0, "blah", 0, "hello", e));
			JavaPlugin.log(e);
			return new ArrayList();
		}
	}

}
