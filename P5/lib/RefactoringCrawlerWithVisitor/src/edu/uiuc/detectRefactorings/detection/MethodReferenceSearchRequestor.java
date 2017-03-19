package edu.uiuc.detectRefactorings.detection;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchRequestor;

public class MethodReferenceSearchRequestor extends SearchRequestor {

	 private List fSearchResults;
     private boolean fRequireExactMatch = true;

    public MethodReferenceSearchRequestor() {
        fSearchResults = new ArrayList();
    }
	
	public List getSearchResults() {
		return fSearchResults;
	}

    
    /* (non-Javadoc)
     * @see org.eclipse.jdt.core.search.SearchRequestor#acceptSearchMatch(org.eclipse.jdt.core.search.SearchMatch)
     */
    public void acceptSearchMatch(SearchMatch match) {
        if (fRequireExactMatch && (match.getAccuracy() != SearchMatch.A_ACCURATE)) {
            return;
        }
        
        if (match.isInsideDocComment()) {
            return;
        }

        if (match.getElement() != null && match.getElement() instanceof IMember) {
            IMember member= (IMember) match.getElement();
            switch (member.getElementType()) {
                case IJavaElement.METHOD:
                case IJavaElement.TYPE:
                case IJavaElement.FIELD:
                case IJavaElement.INITIALIZER:
                    fSearchResults.add(member);
                    break;
            }
        }
    }
	
}