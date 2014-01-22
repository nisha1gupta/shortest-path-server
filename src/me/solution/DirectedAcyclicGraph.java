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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectedAcyclicGraph {
	
	private Map<Integer, List<AdjacentNode>> vertexAdjacentNodes;
	
	DirectedAcyclicGraph() {
		vertexAdjacentNodes = new HashMap<Integer, List<AdjacentNode>>();
	}
	
	public void addAdjacentNodeForVertex( int vertex, int adjVertex, int cost ) {
		List<AdjacentNode> adjNodes = getAdjacentNodesForVertex( vertex );
		if( adjNodes == null )
			adjNodes = new ArrayList<AdjacentNode>();
		adjNodes.add( new AdjacentNode( adjVertex, cost ) );
		vertexAdjacentNodes.put( Integer.valueOf( vertex ), adjNodes );
	}
	
	public int getCostFromVertexToVertex( int startVertex, int endVertex ) throws IllegalArgumentException {
		List<AdjacentNode> adjNodes = getAdjacentNodesForVertex( startVertex );
		if( adjNodes == null )
			throw new IllegalArgumentException( "Start vertex " + startVertex + " not found." );
		for( AdjacentNode node : adjNodes ) {
			if( node.getVertex() == endVertex )
				return node.getCost();
		}
		throw new IllegalArgumentException( "No edge found from start vertex " + startVertex + " to end vertex " + endVertex + "." );
	}
	
	public List<AdjacentNode> getAdjacentNodesForVertex( int vertex ) {
		return vertexAdjacentNodes.get( Integer.valueOf( vertex ) );
	}
	
	public Map<Integer, List<AdjacentNode>> getVertexAdjacentNodes() {
		return vertexAdjacentNodes;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for( Map.Entry<Integer, List<AdjacentNode>> entry : vertexAdjacentNodes.entrySet() ) {
			Integer v = entry.getKey();
			sb.append( v.toString() + " ---> \n" );
			for( AdjacentNode n : entry.getValue() ) {
				sb.append( "\t" + n.getVertex() + " = " + n.getCost() + "\n" );
			}
		}
		return sb.toString();
	}
	
	public void shortestPath( int startVertex, int endVertex, List<Node> nodes ) {
		//System.out.println( "Start Vertex = " + startVertex );
		Node node = nodes.get( nodes.indexOf( new Node( startVertex ) ) );
		node.setCost( 0 );
		Node lowestNode = null;
		//System.out.println( "NODES:\n" + nodes.toString() );
		while( ( lowestNode = getLowestUnknown( nodes ) ) != null ) {
			lowestNode.setKnown( true );
			int lowestVertex = lowestNode.getVertex();
			int lowestCost = lowestNode.getCost();
			//System.out.println( "Lowest unknown vertex = " + lowestVertex + " with cost = " + lowestCost );
			List<AdjacentNode> adjNodes = getAdjacentNodesForVertex( lowestVertex );
			if( adjNodes != null ) {
				//System.out.println( "Adjacent nodes = " + adjNodes.size() );
			for( AdjacentNode adjNode : adjNodes ) {
				int adjVertex = adjNode.getVertex();
				int adjCost = adjNode.getCost();
				//System.out.println( "Found adjacent node " + adjVertex + " with cost = " + adjCost );
				int cost = lowestCost + adjCost;
				Node xNode = nodes.get( nodes.indexOf( new Node( adjVertex ) ) );
				int xCost = xNode.getCost();
				//System.out.println( "Comparing computed cost = " + cost + " to adjacent node's unknown cost = " + xCost );
				if( cost < xCost ) {
					xNode.setCost( cost );
					xNode.setPreviousNode( lowestNode );
				}
			}
			}
		}
		//Node path = nodes.get( nodes.indexOf( new Node( endVertex ) ) );
		//return path;
		//System.out.println( "NODES:\n" + nodes.toString() );
	}
	
	public Node getLowestUnknown( List<Node> nodes ) {
		Node node = null;
		for( Node n : nodes ) {
			if( !n.isKnown() ) {
				if( node == null )
					node = n;
				else if( n.getCost() < node.getCost() )
					node = n;
			}
		}
		return node;
	}
}
