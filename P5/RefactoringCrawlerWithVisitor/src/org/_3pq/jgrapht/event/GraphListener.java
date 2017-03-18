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
/* ------------------
 * GraphListener.java
 * ------------------
 * (C) Copyright 2003, by Barak Naveh and Contributors.
 *
 * Original Author:  Barak Naveh
 * Contributor(s):   -
 *
 * $Id: GraphListener.java,v 1.1 2005/04/13 19:21:25 dig Exp $
 *
 * Changes
 * -------
 * 24-Jul-2003 : Initial revision (BN);
 * 10-Aug-2003 : Adaptation to new event model (BN);
 *
 */
package org._3pq.jgrapht.event;

/**
 * A listener that is notified when the graph changes.
 * 
 * <p>
 * If only notifications on vertex set changes are required it is more
 * efficient to use the VertexSetListener.
 * </p>
 *
 * @author Barak Naveh
 *
 * @see org._3pq.jgrapht.event.VertexSetListener
 * @since Jul 18, 2003
 */
public interface GraphListener extends VertexSetListener {
    /**
     * Notifies that an edge has been added to the graph.
     *
     * @param e the edge event.
     */
    public void edgeAdded( GraphEdgeChangeEvent e );


    /**
     * Notifies that an edge has been removed from the graph.
     *
     * @param e the edge event.
     */
    public void edgeRemoved( GraphEdgeChangeEvent e );
}