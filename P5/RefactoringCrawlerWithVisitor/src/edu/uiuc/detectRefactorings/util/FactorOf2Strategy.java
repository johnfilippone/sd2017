/**
 * 
 */
package edu.uiuc.detectRefactorings.util;

/**
 * @author comertog
 *
 */
public class FactorOf2Strategy extends Strategy {

	/**
	 * 
	 */
	public FactorOf2Strategy() {
		super();
	}

	/* (non-Javadoc)
	 * @Override
	 * @see edu.uiuc.detectRefactorings.util.Strategy#computeNumShingles(int, int)
	 */
	int upperBoundLimitForShinglesBag(int loc, int s_base) {
		return s_base + (2 * loc);
	}

	int upperBoundForClassShingles(int numMethods, int s_base) {
		return s_base + (2 * numMethods);
	}

	int upperBoundForPackageShingles(int numClasses, int s_base) {
		return s_base + (2 * numClasses);
	}

}