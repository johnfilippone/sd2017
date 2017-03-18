/**
 * 
 */
package edu.uiuc.detectRefactorings.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import edu.uiuc.detectRefactorings.util.Node;

/**
 * @author Can Comertoglu
 * 
 * This class will be used to create the XML output that we will use to export
 * our findings. It is usable with the Refactoring Automation tool CatchUp!
 */
public class ExportToXML {
	/**
	 * We will use a string to store the xml file, and at the last step, we will
	 * export it to a file in the project workspace.
	 */

	 private String xmlOutputString;

	 private boolean isFinalized = false;

	public ExportToXML() {
		xmlOutputString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n"
				+ "<refactoringsession>" + "\n";
	}

	public void addDetectedRefactorings(List pairList, String categoryName) {
		for (Iterator iter = pairList.iterator(); iter.hasNext();) {
			Node[] pair = (Node[]) iter.next();
			xmlOutputString += "<refactoring name=" + "\"" + categoryName
					+ "\"> \n";
			xmlOutputString += "<parameter name= \"";
			String oldFullyQualifiedName = extractMethodNameAndSignature(pair[1]);
			xmlOutputString += "new element\">" + oldFullyQualifiedName
					+ "</parameter>";
			xmlOutputString += "<parameter name= \"";
			xmlOutputString += "old element\">"
					+ extractMethodNameAndSignature(pair[0]) + "</parameter>";
			xmlOutputString += "</refactoring>";

		}
	}

	/**
	 * @param pair
	 * @return
	 */
	 private String extractMethodNameAndSignature(Node node) {
		if (node.getSignature() != null)
			return node.getFullyQualifiedName() + node.getSignature();
		else
			return node.getFullyQualifiedName();
	}

	public void closeXMLOutput(String filename) {
		if (filename != null) {
			if (!isFinalized) {
				xmlOutputString += "</refactoringsession>";
				isFinalized = true;
			}
			// Write the string out to the file here.
			try {
				if (filename.lastIndexOf(".xml") < 0)
					filename += ".xml";
				File file = new File(filename);
				BufferedWriter out = new BufferedWriter(new FileWriter(file));
				out.write(xmlOutputString);
				out.close();
			} catch (IOException e) {
				// handle the exception here.
				System.out.println("IO exception occurred");
			}
		}
	}

	public void closeXMLOutput() {
		closeXMLOutput("c:\\Temp\\refactoringsSession.xml");
	}

}