/**
 * 
 */
package edu.uiuc.detectRefactorings.util;

/**
 * @author Can Comertoglu
 *
 */
public class DefaultStrategy extends Strategy {

	/**
	 * 
	 */
	public DefaultStrategy() {
		super();
	}

	/* (non-Javadoc)
	 * @see edu.uiuc.detectRefactorings.util.Strategy#computeNumShingles(int, int)
	 */
	int upperBoundLimitForShinglesBag(int loc, int s_base) {
		return s_base;
	}

	int upperBoundForClassShingles(int numMethods, int s_base) {
		return s_base;
	}

	int upperBoundForPackageShingles(int numClasses, int s_base) {
		return s_base;
	}

}
