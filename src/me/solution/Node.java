/*
 * LICENSE
 * 
 * This file is part of Shortest-Path-Server.
 * 
 * Copyright (c) 2013 Nisha Gupta
 * 
 * Shortest-Path-Server is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Shortest-Path-Server is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Shortest-Path-Server.  If not, see http://www.gnu.org/licenses/.
 * 
 */

package me.solution;

public class Node {

	private int vertex;
	
	private int cost = Integer.MAX_VALUE;
	
	private boolean known = false;
	
	private Node previousNode = null;
	
	public Node( int vert ) {
		this.vertex = vert;
	}
	
	public int getVertex() {
		return this.vertex;
	}
	
	public int getCost() {
		return this.cost;
	}
	
	public void setCost( int cost ) {
		this.cost = cost;
	}
	
	public boolean isKnown() {
		return known;
	}
	
	public void setKnown( boolean known ) {
		this.known = known;
	}
	
	public Node getPreviousNode() {
		return previousNode;
	}
	
	public void setPreviousNode( Node prevNode ) {
		this.previousNode = prevNode;
	}
	
	public void reset() {
		this.cost = Integer.MAX_VALUE;
		this.known = false;
		this.previousNode = null;
	}
	
	public boolean equals( Object obj ) {
		if( obj instanceof Node ) {
			Node node = ( Node ) obj;
			if( node.getVertex() == this.vertex )
				return true;
		}
		return false;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( "Vertex = " + this.vertex + ", Cost = " + this.cost + ", Known = " + this.known + ", Previous Node = " + ( previousNode == null ? "null" : previousNode.getVertex() ) + "\n" );
		return sb.toString();
	}
}
