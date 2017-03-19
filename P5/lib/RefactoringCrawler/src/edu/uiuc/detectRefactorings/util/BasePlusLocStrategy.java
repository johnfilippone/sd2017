package edu.uiuc.detectRefactorings.util;

public class BasePlusLocStrategy extends Strategy {

	public BasePlusLocStrategy() {
		super();
	}

	@Override
	int upperBoundLimitForShinglesBag(int loc, int s_base) {
		return loc + s_base;
	}

	@Override
	int upperBoundForClassShingles(int numMethods, int s_base) {
		return numMethods + s_base;
	}

	@Override
	int upperBoundForPackageShingles(int numClasses, int s_base) {
		return numClasses + s_base;
	}

}
