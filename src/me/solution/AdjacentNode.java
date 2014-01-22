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

public class AdjacentNode {

	private int vertex;
	
	private int cost;
	
	public AdjacentNode( int ver, int cos ) {
		this.vertex = ver;
		this.cost = cos;
	}
	
	public int getVertex() {
		return vertex;
	}
	
	public int getCost() {
		return cost;
	}
	
	public boolean isKnown() {
		if( this.cost == Integer.MAX_VALUE )
			return false;
		return true;
	}
	
	public boolean equals( Object obj ) {
		if( obj instanceof AdjacentNode ) {
			AdjacentNode node = ( AdjacentNode ) obj;
			if( node.getVertex() == this.vertex )
				return true;
		}
		return false;
	}
}
