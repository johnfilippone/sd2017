/*
 * Created on Apr 11, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.uiuc.detectRefactorings.util;

/**
 * @author dig
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Node {

	private String fullyQualifiedName;

	private String type;

	public static final String PROJECT = "project";

	public static final String PACKAGE = "package";

	public static final String CLASS = "class";

	public static final String METHOD = "method";

	public static final String FIELD = "field";

	public static final String METHOD_CALL = "methodCall";

	public static final String IMPORT = "import";

	public static final String CLASS_REFERENCE = "classReference";

	public static final String FIELD_REFERENCE = "fieldReference";

	private int[] shingles;

	private boolean hasCallGraph;

	private String projectName;

	private boolean isAPI = false;

	private String signature;

	private int flags;

	private boolean deprecated = false;

	private boolean isInterface = false;

	/**
	 * @param label
	 * @param type
	 */
	public Node(String label, String type) {
		this.fullyQualifiedName = label;
		this.type = type;
	}

	/**
	 * @return Returns the label.
	 */
	public String getFullyQualifiedName() {
		return fullyQualifiedName;
	}

	/**
	 * @param label
	 *            The label to set.
	 */
	public void setFullyQualifiedName(String label) {
		this.fullyQualifiedName = label;
	}

	public String getSimpleName() {
		int pos = fullyQualifiedName.lastIndexOf(".");
		if (pos != -1) {
			return fullyQualifiedName.substring(pos + 1, fullyQualifiedName
					.length());
		}
		return fullyQualifiedName;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		if (getSignature() != null)
			return getType() + "= " + getFullyQualifiedName() + getSignature();
		else
			return getType() + "= " + getFullyQualifiedName();
	}

	public void setShingles(int[] shingles) {
		this.shingles = shingles;
	}

	public int[] getShingles() {
		return shingles;
	}

	public boolean hasCallGraph() {
		return hasCallGraph;
	}

	public void setCreatedCallGraph() {
		hasCallGraph = true;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setAPIStatus() {
		isAPI = true;
	}

	public boolean isAPI() {
		return isAPI;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSignature() {
		return signature;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public int getFlags() {
		return flags;
	}

	public void setDeprecated(boolean b) {
		deprecated = b;
	}

	public boolean isDeprecated() {
		return deprecated;
	}

	public boolean isInterface() {
		return isInterface;
	}

	public void setInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}
}
