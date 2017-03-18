/*
 * Created on Apr 11, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.uiuc.tests;

import junit.framework.TestCase;

import org._3pq.jgrapht.DirectedGraph;
import org._3pq.jgrapht.edge.EdgeFactories;
import org._3pq.jgrapht.graph.DirectedMultigraph;
import org._3pq.jgrapht.traverse.BreadthFirstIterator;

import edu.uiuc.detectRefactorings.util.Node;

/**
 * @author dig
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CreateGraphTest extends TestCase {

	public void testCreateDummyGraph() {
		DirectedGraph g = new DirectedMultigraph();
		EdgeFactories.DirectedEdgeFactory factory= new EdgeFactories.DirectedEdgeFactory();
		
		Node project = new Node("myProject", "project");
		Node pack1 = new Node("pack1", "package");
		Node class1 = new Node("class1", "class");
		Node m1 = new Node("m1", "method");
		Node m2 = new Node("m2", "method");

		// add the vertices
		g.addVertex(project);
		g.addVertex(pack1);
		g.addVertex(class1);
		g.addVertex(m1);
		g.addVertex(m2);

		// add edges to create linking structure
		
		g.addEdge(factory.createEdge(project, pack1, "package"));
		g.addEdge(factory.createEdge(pack1, class1, "class"));
		g.addEdge(factory.createEdge(class1, m1, "method"));
		  g.addEdge(factory.createEdge(class1, m2, "method"));
		
		// create method call edge from m1 to m2
		g.addEdge(factory.createEdge(m1, m2, "methodcall"));
		
		assertTrue(g.containsVertex(m2));
		 assertTrue(g.containsEdge(m1, m2));
		 assertEquals(5, g.vertexSet().size());
		 assertEquals(5, g.edgeSet().size());
		
	}
	
	public void testSimpleComparison()
	{
		DirectedGraph g = new DirectedMultigraph();
		EdgeFactories.DirectedEdgeFactory factory= new EdgeFactories.DirectedEdgeFactory();
		
		Node project = new Node("myProject", "project");
		Node pack1 = new Node("pack1", "package");
		Node class1 = new Node("class1", "class");
		Node m1 = new Node("m1", "method");
		Node m2 = new Node("m2", "method");

		// add the vertices
		g.addVertex(project);
		g.addVertex(pack1);
		g.addVertex(class1);
		g.addVertex(m1);
		g.addVertex(m2);

		// add edges to create linking structure
		
		g.addEdge(factory.createEdge(project, pack1, "package"));
		g.addEdge(factory.createEdge(pack1, class1, "class"));
		g.addEdge(factory.createEdge(class1, m1, "method"));
		  g.addEdge(factory.createEdge(class1, m2, "method"));
		
		// create method call edge from m1 to m2
		g.addEdge(factory.createEdge(m1, m2, "methodcall"));
		
		
		// Create graph2
		DirectedGraph g2 = new DirectedMultigraph();
		EdgeFactories.DirectedEdgeFactory factory2= new EdgeFactories.DirectedEdgeFactory();
		
		Node g2_project = new Node("myProject", "project");
		Node g2_pack1 = new Node("pack1", "package");
		Node g2_class1 = new Node("class1", "class");
		Node g2_m1 = new Node("method1", "method");
		Node g2_m2 = new Node("m2", "method");

		// add the vertices
		g2.addVertex(g2_project);
		g2.addVertex(g2_pack1);
		g2.addVertex(g2_class1);
		g2.addVertex(g2_m1);
		g2.addVertex(g2_m2);

		// add edges to create linking structure
		
		g2.addEdge(factory2.createEdge(g2_project, g2_pack1, "package"));
		g2.addEdge(factory2.createEdge(g2_pack1, g2_class1, "class"));
		g2.addEdge(factory2.createEdge(g2_class1, g2_m1, "method"));
		  g2.addEdge(factory2.createEdge(g2_class1, g2_m2, "method"));
		
		// create method call edge from m1 to m2
		g2.addEdge(factory2.createEdge(g2_m1, g2_m2, "methodcall"));
		
		BreadthFirstIterator bfi = new BreadthFirstIterator(g);
		BreadthFirstIterator bfi2 = new BreadthFirstIterator(g2);
		
		while (bfi.hasNext() && bfi2.hasNext())
		{
			Node curr1 = (Node) bfi.next(); 
			Node curr2 = (Node) bfi2.next();
			if (curr1.toString().equals(curr2.toString()))
			{
				System.out.println(curr1.toString() + " and " + curr2.toString() + " are the same");
			}
			else{
				System.out.print("Two graphs g1 & g2 are different");
				System.out.println(": they differ at" + curr1.toString() + " & " + curr2.toString());
			}
		}
		assertTrue(true);
	}
	
	
}