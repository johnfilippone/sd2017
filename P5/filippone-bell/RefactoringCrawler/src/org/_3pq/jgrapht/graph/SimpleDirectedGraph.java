/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Lead:  Barak Naveh (barak_naveh@users.sourceforge.net)
 *
 * (C) Copyright 2003, by Barak Naveh and Contributors.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/* ------------------------
 * SimpleDirectedGraph.java
 * ------------------------
 * (C) Copyright 2003, by Barak Naveh and Contributors.
 *
 * Original Author:  Barak Naveh
 * Contributor(s):   -
 *
 * $Id: SimpleDirectedGraph.java,v 1.1 2005/04/13 19:21:25 dig Exp $
 *
 * Changes
 * -------
 * 05-Aug-2003 : Initial revision (BN);
 *
 */
package org._3pq.jgrapht.graph;

import org._3pq.jgrapht.DirectedGraph;
import org._3pq.jgrapht.EdgeFactory;
import org._3pq.jgrapht.edge.EdgeFactories;

/**
 * A simple directed graph. A simple directed graph is a directed graph in
 * which neither multiple edges between any two vertices nor loops are
 * permitted.
 */
public class SimpleDirectedGraph extends AbstractBaseGraph
    implements DirectedGraph {
    /**
     * @see AbstractBaseGraph
     */
    public SimpleDirectedGraph(  ) {
        this( new EdgeFactories.DirectedEdgeFactory(  ) );
    }


    /**
     * @see AbstractBaseGraph
     */
    public SimpleDirectedGraph( EdgeFactory ef ) {
        super( ef, false, false );
    }
}