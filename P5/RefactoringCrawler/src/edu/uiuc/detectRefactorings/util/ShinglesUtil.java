package edu.uiuc.detectRefactorings.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

import org._3pq.jgrapht.Edge;
import org._3pq.jgrapht.graph.AbstractBaseGraph;
import org._3pq.jgrapht.traverse.BreadthFirstIterator;
import org.eclipse.core.runtime.IProgressMonitor;

public class ShinglesUtil {

	public List methodList = new ArrayList();

	public List classList = new ArrayList();

	public List packageList = new ArrayList();

	public List fieldList = new ArrayList();

	private int w = 3;

	private int s_method = 8;

	private int s_class = 10;

	private int s_package = 15;

	private Strategy strategy;

	private double method_treshold = 0.0;

	private double class_treshold = 0.0;

	private double package_treshold = 0.0;

	private double renamePackage_threshold = 0.0;

	private double renameClass_threshold = 0.0;

	private double renameMethod_threshold = 0.0;
	
	private double pullUp_treshold = 0.0;

	private double pushDown_treshold = 0.0;

	private double moveMethod_treshold = 0.0;

	private double moveField_treshold = 0.0;

	private double changeMethodSignature_treshold = 0.0;

	private ArrayList origMethods;

	private ArrayList origClasses;

	private ArrayList origPackages;

	private ArrayList origFields;

	private ArrayList verMethods;

	private ArrayList verPackages;

	private ArrayList verClasses;

	private ArrayList verFields;

	private AbstractBaseGraph origGraph;

	private AbstractBaseGraph verGraph;

	final private double tresholdModifier = 0.80;

	private double moveClass_treshold;

	public ShinglesUtil() {
		// strategy = new DefaultStrategy();
		strategy = new FactorOf2Strategy();
	}

	public int[] computeMethodShingles(String str) {
		return computeMethodShingles(str, w,
				strategy.upperBoundLimitForShinglesBag(findNumberOfLines(str),
						s_method));
	}

	/**
	 * Computes the Shingles Hash for the passed String, with parameters for
	 * window size and the number of hash values in the returned array
	 */
	public int[] computeMethodShingles(String str, int window,
			int upperBoundLimit) {
		/*
		 * We are now introducing the idea of finding the number of lines in the
		 * method, and incorporate that into the calculation of shingles, so
		 * that, if there are more lines, then there are going to be more
		 * shingles associated with the method. However, a 1-1 correspondance
		 * will be misleading, thus another method is required.
		 */
		BloomFilter bloomFilter = new BloomFilter();
		List tokenList = tokenizer(str);
		List bagOfWindowedTokens = computeSlidingWindowTokens(tokenList);

		int[] shinglesValues = new int[bagOfWindowedTokens.size()];
		int numberOfWindowedTokens = 0;
		ListIterator bagOfWindowedTokensIter = bagOfWindowedTokens
				.listIterator();

		while (bagOfWindowedTokensIter.hasNext()) {
			List tempList = (List) bagOfWindowedTokensIter.next();
			String tokensInOneWindow = new String();
			for (int i = 0; i < window; i++) {
				tokensInOneWindow += tempList.get(i);
				if (i != window - 1)
					tokensInOneWindow += " ";
			}
			int shingle = bloomFilter.hashRabin(tokensInOneWindow);
			shinglesValues[numberOfWindowedTokens] = shingle;
			numberOfWindowedTokens++;
		}

		Arrays.sort(shinglesValues);

		int correctNumberOfShingles = Math.min(upperBoundLimit,
				numberOfWindowedTokens);

		int[] retVal = new int[correctNumberOfShingles];
		for (int i = 0; i < correctNumberOfShingles; i++) {
			retVal[i] = shinglesValues[i];
		}
		return retVal;
	}

	private int findNumberOfLines(String str) {
		int retval = 0;
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\n')
				retval++;
		}
		return retval;
	}

	/**
	 * Populates bagOfTokensList with tokens found in tokenList with the idea of
	 * sliding windows.
	 * 
	 * eg. if tokenList = {a, s, d, f} and window size (w) = 2, then the
	 * bagOfTokensList is {(a, s), (s, d), (d, f)}
	 * 
	 * @param tokenList
	 * @param bagOfTokensList
	 * @param iter
	 */
	private List computeSlidingWindowTokens(List tokenList) {

		List bagOfTokensList = new ArrayList();
		ListIterator iter = tokenList.listIterator();
		while (iter.nextIndex() <= (tokenList.size() - w))
		// W=2, iterIndex = size - W identifies the last window
		{
			List tempList = new ArrayList();
			for (int i = 1; i <= w; i++) // W=3
			{
				tempList.add(iter.next());
			}
			bagOfTokensList.add(tempList);
			for (int i = 1; i <= w - 1; i++)
				// i<= W-1
				iter.previous();
		}
		return bagOfTokensList;
	}

	public List tokenizer(String s) {
		List list = new ArrayList();
		StringTokenizer st = new StringTokenizer(s, " \t \n . \r \" ");
		while (st.hasMoreElements()) {
			list.add(st.nextToken());
		}
		return list;
	}

	/**
	 * Checks the first shingles values (arr1) against the second set of values
	 * (arr2). Then does the same for arr2 against arr1. Returns the mean of the
	 * individual results as the similarity grade.
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public double howMuchAlike(int[] arr1, int[] arr2) {
		double finalGrade, similarityFromArr1ToArr2, similarityFromArr2ToArr1;
		similarityFromArr1ToArr2 = howMuchIs1Like2(arr1, arr2);
		similarityFromArr2ToArr1 = howMuchIs1Like2(arr2, arr1);
		finalGrade = (similarityFromArr1ToArr2 + similarityFromArr2ToArr1) / 2.0;
		return finalGrade;
	}

	/**
	 * When a match is found in the arrays, we replace that value in arr2 so
	 * that duplicate matches do not result in wrong grades
	 * 
	 * eg. arr1 = {1, 2, 2}; arr2= {2, 3, 4} this would return 1/3 similarity
	 * and the resulting tempArr would be {MIN_VALUE, 3, 4}
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	private double howMuchIs1Like2(int[] arr1, int[] arr2) {
		int[] tempArr = (int[]) arr2.clone();
		double grade = 0.0;
		for (int i = 0; i < arr1.length; i++) {
			for (int j = 0; j < tempArr.length; j++) {
				if (arr1[i] == tempArr[j]) {
					grade += 1.0 / arr1.length;
					tempArr[j] = Integer.MIN_VALUE;
					break;
				}
			}
		}
		return grade;
	}

	/**
	 * Finds the candidate Method, Class and Packges in the given versions of
	 * two graphs. Doesn't return anything. Need to use getCandidateX methods to
	 * receive the needed lists.
	 * 
	 * @param origGraph
	 * @param verGraph
	 */
	public void initialize(AbstractBaseGraph origGraph,
			AbstractBaseGraph verGraph) {
		this.origGraph = origGraph;
		this.verGraph = verGraph;

		origMethods = new ArrayList();
		origClasses = new ArrayList();
		origPackages = new ArrayList();
		origFields = new ArrayList();

		verMethods = new ArrayList();
		verClasses = new ArrayList();
		verPackages = new ArrayList();
		verFields = new ArrayList();

		initializeElementsLists(origGraph, origMethods, origClasses,
				origPackages, origFields);

		initializeElementsLists(verGraph, verMethods, verClasses, verPackages,
				verFields);
	}

	public List findSimilarPackages(IProgressMonitor monitor) {
		if (packageList.isEmpty()) {
			if (classList.isEmpty())
				findSimilarClasses(monitor);
			monitor.subTask("Compute Package Shingles");
			computePackageShingles(origPackages, origGraph);
			computePackageShingles(verPackages, verGraph);
			List simPackage = new ArrayList();
			monitor.subTask("Find Packages with Similar Shingles");
			for (Iterator iter = origPackages.iterator(); iter.hasNext();) {
				Node c = (Node) iter.next();
				if (!c.getFullyQualifiedName().equals("")) {
					for (Iterator iterator = verPackages.iterator(); iterator
							.hasNext();) {
						if (monitor.isCanceled())
							return new ArrayList(0);
						Node c2 = (Node) iterator.next();
						if (!c2.getFullyQualifiedName().equals("")) {
							if (howMuchAlike(c.getShingles(), c2.getShingles()) > package_treshold) {
								Node[] arr = { c, c2 };
								simPackage.add(arr);
							}
						}
					}
				}
			}
			packageList = simPackage;
		}
		return packageList;
	}

	public List findSimilarClasses(IProgressMonitor monitor) {
		if (classList.isEmpty()) {
			monitor.subTask("Compute Class Shingles");
			computeClassShingles(origClasses, origGraph);
			computeClassShingles(verClasses, verGraph);
			List simClass = new ArrayList();
			monitor.subTask("Find Classes with Similar Shingles");
			for (Iterator iter = origClasses.iterator(); iter.hasNext();) {
				Node c = (Node) iter.next();

				if (!c.isAPI())
					continue;

				for (Iterator iterator = verClasses.iterator(); iterator
						.hasNext();) {
					if (monitor.isCanceled()) {
						return new ArrayList(0);
					}
					Node c2 = (Node) iterator.next();

					if (!c2.isAPI())
						continue;

					if (howMuchAlike(c.getShingles(), c2.getShingles()) > class_treshold) {
						Node[] arr = { c, c2 };
						simClass.add(arr);
					}
				}
			}
			classList = simClass;
		}
		return classList;
	}

	/**
	 * Goes through all methods in v1 and v2 and compares them pairwise (only
	 * takes into account API methods) with shingles Uses #minThreshold as the
	 * threshold to compare.
	 * 
	 * @param monitor
	 * @return
	 */
	public List findSimilarMethods(IProgressMonitor monitor) {
		if (methodList.isEmpty()) {
			List similarMethods = new ArrayList();
			monitor.subTask("Find Methods With Similar Shingles");
			for (Iterator iter = origMethods.iterator(); iter.hasNext();) {
				Node m = (Node) iter.next();
				if (!m.isAPI())
					continue;

				if (monitor.isCanceled())
					return new ArrayList(0);
				for (Iterator iterator = verMethods.iterator(); iterator
						.hasNext();) {
					Node m2 = (Node) iterator.next();
					if (!m2.isAPI())
						continue;

					if (howMuchAlike(m.getShingles(), m2.getShingles()) > method_treshold) {
						Node[] arr = { m, m2 };
						// if (!isThisArrayInTheList(simMet, arr))
						similarMethods.add(arr);
					}
				}
			}
			methodList = similarMethods;
		}
		return methodList;
	}

	/**
	 * For the passed graph, fills the respective arrays for packages, classes,
	 * methods and fields by using a breadth first iterator.
	 * 
	 * @param graph
	 * @param methods
	 * @param classes
	 * @param packages
	 * @param fields
	 */
	private void initializeElementsLists(AbstractBaseGraph graph, List methods,
			List classes, List packages, List fields) {
		// Create a BreadthFirstIterator for the graph
		BreadthFirstIterator bfi = new BreadthFirstIterator(graph);
		while (bfi.hasNext()) {
			Node n = (Node) bfi.next();
			if (n.getType().equals(Node.CLASS)) {
				classes.add(n);
			} else if (n.getType().equals(Node.PACKAGE)) {
				packages.add(n);
			} else if (n.getType().equals(Node.METHOD)) {
				methods.add(n);
			} else if (n.getType().equals(Node.FIELD)) {
				fields.add(n);
			}
		}
	}

	/**
	 * @param classes
	 * @param graph
	 *            <br>
	 *            For each class nodes in classes, find the shingles by
	 *            concatenating shingles in methods of its subtree. The
	 *            parameter s_class will determine the maximum size of shingles
	 */
	private void computeClassShingles(List classes, AbstractBaseGraph graph) {
		for (int i = 0; i < classes.size(); i++) {
			Node clasz = (Node) classes.get(i);
			// We will keep the number of methods for the class with the
			// numberOfMethods variable.
			int numberOfMethods = 0;
			List outEdges = graph.outgoingEdgesOf(clasz);
			int methodsTotalShingleSize = 0;
			for (Iterator iter = outEdges.iterator(); iter.hasNext();) {
				Edge e = (Edge) iter.next();
				Node neighbor = (Node) e.oppositeVertex(clasz);
				if (neighbor.getType().equalsIgnoreCase(Node.METHOD)) {
					methodsTotalShingleSize += neighbor.getShingles().length;
					// Here we update the method count.
					numberOfMethods++;
				}
			}

			// fill allShinglesFromMethods with shingles from all the methods in
			// the class
			int[] allShinglesFromMethods = new int[methodsTotalShingleSize];
			Arrays.fill(allShinglesFromMethods, Integer.MAX_VALUE);
			int index = 0;
			for (Iterator iter = outEdges.iterator(); iter.hasNext();) {
				Edge e = (Edge) iter.next();
				Node neighbor = (Node) e.oppositeVertex(clasz);
				if (neighbor.getType().equalsIgnoreCase(Node.METHOD)) {
					for (int j = 0; j < neighbor.getShingles().length; j++) {
						allShinglesFromMethods[index] = neighbor.getShingles()[j];
						index++;
					}
				}
			}

			int upperBoundForClassShingles = strategy
					.upperBoundForClassShingles(numberOfMethods, s_class);
			Arrays.sort(allShinglesFromMethods);

			upperBoundForClassShingles = Math.min(upperBoundForClassShingles,
					allShinglesFromMethods.length);

			int[] retVal = new int[upperBoundForClassShingles];
			for (int j = 0; j < upperBoundForClassShingles; j++) {
				retVal[j] = allShinglesFromMethods[j];
			}
			clasz.setShingles(retVal);
		}
	}

	private void computePackageShingles(List packages, AbstractBaseGraph graph) {
		// Compute Shingles from classes in the subtree
		for (int i = 0; i < packages.size(); i++) {
			Node pack = (Node) packages.get(i);
			int numberOfClasses = 0; // Analogous to numOfMethods in
			// computeClassShingles
			if (!pack.getFullyQualifiedName().equals("")) {
				List outEdges = graph.outgoingEdgesOf(pack);
				int totalNumOfShinglesFromClasses = 0;
				for (Iterator iter = outEdges.iterator(); iter.hasNext();) {
					Edge e = (Edge) iter.next();
					Node neighbor = (Node) e.oppositeVertex(pack);
					if (neighbor.getType().equalsIgnoreCase(Node.CLASS)) {
						totalNumOfShinglesFromClasses += neighbor.getShingles().length;
						numberOfClasses++;
					}
				}
				int[] allShinglesFromClasses = new int[totalNumOfShinglesFromClasses];
				Arrays.fill(allShinglesFromClasses, Integer.MAX_VALUE);
				int index = 0;
				for (Iterator iter = outEdges.iterator(); iter.hasNext();) {
					Edge e = (Edge) iter.next();
					Node aClass = (Node) e.oppositeVertex(pack);
					if (aClass.getType().equalsIgnoreCase(Node.CLASS)) {
						for (int j = 0; j < aClass.getShingles().length; j++) {
							allShinglesFromClasses[index] = aClass
									.getShingles()[j];
							index++;
						}
					}
				}
				Arrays.sort(allShinglesFromClasses);

				int upperBoundForPackage = Math.min(strategy
						.upperBoundForPackageShingles(numberOfClasses,
								s_package), allShinglesFromClasses.length);

				int[] retVal = new int[upperBoundForPackage];
				for (int j = 0; j < upperBoundForPackage; j++) {
					retVal[j] = allShinglesFromClasses[j];
				}
				pack.setShingles(retVal);
			}
		}
	}

	/**
	 * Sets method_treshold, that's used for returning candidate methods
	 * 
	 * @param method_treshold
	 */
	public void setMethod_treshold(double method_treshold) {
		this.method_treshold = method_treshold;
	}

	public double getMethod_treshold() {
		return method_treshold;
	}

	/**
	 * Sets class_treshold, that's used for returning candidate class
	 * 
	 * @param class_treshold
	 */
	public void setClass_treshold(double class_treshold) {
		this.class_treshold = class_treshold;
	}

	public double getClass_treshold() {
		return class_treshold;
	}

	public void setPackage_treshold(double package_treshold) {
		this.package_treshold = package_treshold;
	}

	public double getPackage_treshold() {
		return package_treshold;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getW() {
		return w;
	}

	public void setS_method(int s_method) {
		this.s_method = s_method;
	}

	public int getS_method() {
		return s_method;
	}

	public void setS_class(int s_class) {
		this.s_class = s_class;
	}

	public int getS_class() {
		return s_class;
	}

	public void setS_package(int s_package) {
		this.s_package = s_package;
	}

	public int getS_package() {
		return s_package;
	}

	public void setPullUp_treshold(double pullUp_treshold) {
		this.pullUp_treshold = pullUp_treshold;
	}

	public double getPullUp_treshold() {
		return pullUp_treshold;
	}

	public void setPushDown_treshold(double pushDown_treshold) {
		this.pushDown_treshold = pushDown_treshold;
	}

	public double getPushDown_treshold() {
		return pushDown_treshold;
	}

	public static boolean isThisArrayInTheList(List l, int[] a) {
		boolean retVal = false;
		for (Iterator iter = l.iterator(); iter.hasNext();) {
			int[] element = (int[]) iter.next();
			if (Arrays.equals(element, a))
				retVal = true;
			break;
		}
		return retVal;
	}

	public static boolean isThisArrayInTheList(List l, Node[] a) {
		boolean retVal = false;
		for (Iterator iter = l.iterator(); iter.hasNext();) {
			Node[] element = (Node[]) iter.next();
			if (Arrays.equals(element, a))
				retVal = true;
			break;
		}
		return retVal;
	}

	public List findPullUpMethodCandidates(IProgressMonitor monitor) {
		return findSimilarMethods(monitor);
	}

	public List findPushDownMethodCandidates(IProgressMonitor monitor) {
		return findSimilarMethods(monitor);
	}

	public List findMoveFieldCandidates(IProgressMonitor monitor) {
		if (fieldList.isEmpty()) {
			List fields = new ArrayList();
			monitor.subTask("Find all fields");
			for (Iterator iter = origFields.iterator(); iter.hasNext();) {
				Node m = (Node) iter.next();
				if (monitor.isCanceled())
					return new ArrayList(0);
				for (Iterator iterator = verFields.iterator(); iterator
						.hasNext();) {
					Node m2 = (Node) iterator.next();
					Node[] arr = { m, m2 };
					if (!isThisArrayInTheList(fields, arr))
						fields.add(arr);
				}
			}
			fieldList = fields;
		}
		return fieldList;
	}

	public void setMoveMethod_treshold(double moveMethod_treshold) {
		this.moveMethod_treshold = moveMethod_treshold;
	}

	public double getMoveMethod_treshold() {
		return moveMethod_treshold;
	}

	public double getMoveField_treshold() {
		return moveField_treshold;
	}

	public void setMoveField_treshold(double moveField_treshold) {
		this.moveField_treshold = moveField_treshold;
	}

	public double getChangeMethodSignature_treshold() {
		return changeMethodSignature_treshold;
	}

	public void setChangeMethodSignature_treshold(
			double changeMethodSignature_treshold) {
		this.changeMethodSignature_treshold = changeMethodSignature_treshold;
	}

	public void setMoveClass_treshold(double moveClass_treshold) {
		this.moveClass_treshold = moveClass_treshold;
	}

	public double getMoveClass_treshold() {
		return moveClass_treshold;
	}

	public double getRenamePackage_treshold() {
		return renamePackage_threshold;
	}

	public void setRenamePackage_threshold(double th) {
		renamePackage_threshold = th;
	}

	public double getRenameClass_treshold() {
		return renameClass_threshold;
	}

	public void setRenameClass_threshold(double th) {
		renameClass_threshold = th;
	}

	public double getRenameMethod_treshold() {
		return renameMethod_threshold;
	}

	public void setRenameMethod_threshold(double th) {
		renameMethod_threshold = th;
	}
}
