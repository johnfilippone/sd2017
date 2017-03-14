/**
 * 
 */
package edu.uiuc.detectRefactorings.util;

/**
 * @author Can Comertoglu
 *
 */
public abstract class Strategy {

	/**
	 * 
	 */
	public Strategy() {
		super();
	}
	
	abstract int upperBoundLimitForShinglesBag(int loc, int s_base);
	
	abstract int upperBoundForClassShingles(int numMethods, int s_base);

	abstract int upperBoundForPackageShingles(int numClasses, int s_base);
}
