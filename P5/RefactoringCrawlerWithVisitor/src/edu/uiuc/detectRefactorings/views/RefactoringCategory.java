package edu.uiuc.detectRefactorings.views;

import java.util.Iterator;
import java.util.List;

import edu.uiuc.detectRefactorings.util.Node;

public class RefactoringCategory {
	
	 private String name;
	 private List refactoringPairs;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List getRefactoringPairs() {
		return refactoringPairs;
	}
	public void setRefactoringPairs(List refactoringPairs) {
		this.refactoringPairs = refactoringPairs;
	}
	
	public Node[][] getElements(){
		Node[][] elements= new Node[refactoringPairs.size()][];
		int i= 0;
		for (Iterator iter = refactoringPairs.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			elements[i++]= pair;
		}
		return elements;
	}
	
	public String toString() {
		return name + printElements(getElements());
	}
	
	 private String printElements(Node[][] array) {
		String retval = "";
		for (int i = 0; i < array.length; i++) {
			Node[] nodes = array[i];
			retval += "[" + nodes[0] + "," + nodes[1] + "]";
		}
		return retval;
	}

}