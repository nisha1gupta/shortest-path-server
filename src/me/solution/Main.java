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

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main( String[] args ) {
		ServerSocket soc = null;
		Socket sock = null;
		InputStream is = null;
		try {
			soc = new ServerSocket( 7777 );
			sock = soc.accept();
			is = sock.getInputStream();
			byte[] buff = new byte[2];
			int i = 0;
			int startVertex = 0;
			int endVertex = 0;
			int numEdges = 0;
			int v1 = 0, v2 = 0, c = 0;
			DirectedAcyclicGraph dag = new DirectedAcyclicGraph();
			List<Node> unknownNodes = new ArrayList<Node>();
			while( is.read( buff ) > 0 ) {
				i++;
				ByteBuffer bb = ByteBuffer.wrap( buff );
				bb.order( ByteOrder.LITTLE_ENDIAN );
				short dat = bb.getShort();
				//System.out.println( "Received " + i + " = " + dat );
				Node node = null;
				if( i == 1 ) {
					startVertex = dat;
					//System.out.println( "Start Vertex = " + startVertex );
					//if( startVertex < 1 )
						//return;
					node = new Node( startVertex );
				} else if( i == 2 ) {
					endVertex = dat;
					//System.out.println( "End Vertex = " + endVertex );
					//if( endVertex < 1 )
						//return;
					node = new Node( endVertex );
				} else if( i == 3 ) {
					numEdges = dat;
					//System.out.println( "Num Edges = " + numEdges );
					if( numEdges < 1 ) {
						System.out.println( "Error: Num Edges = " + numEdges );
						return;
					}
				} else {
					if( i % 3 == 1 ) {
						v1 = dat;
						node = new Node( v1 );
					} else if( i % 3 == 2 ) {
						v2 = dat;
						node = new Node( v2 );
					} else if( i % 3 == 0 ) {
						c = dat;
						//node = new Node( c );
						dag.addAdjacentNodeForVertex( v1, v2, c );
						//System.out.println( "Added node : " + v1 + " ---> " + v2 + " = " + c );
					}
				}
				if( node != null && !unknownNodes.contains( node ) ) {
					unknownNodes.add( node );
				}
			}
			/*if( i - ( numEdges * 3 ) > 3 ) {
				System.out.println( "Houston, we have extra rows!" );
			}
			System.out.println( "DIRECTED GRAPH:\n" + dag.toString() );*/
			
			dag.shortestPath( startVertex, endVertex, unknownNodes );
			//System.out.println( unknownNodes.toString() );
			Node n = unknownNodes.get( unknownNodes.indexOf( new Node( endVertex ) ) );
			int cost = n.getCost();
			String path = Integer.valueOf( n.getVertex() ).toString();
			while( n.getPreviousNode() != null && n.getPreviousNode().getVertex() != startVertex ) {
				n = n.getPreviousNode();
				path = Integer.valueOf( n.getVertex() ).toString() + " ---> " + path;
			}
			if( n.getPreviousNode() == null ) {
				System.out.println( "No path from '" + startVertex + "' to '" + endVertex + "'" );
			} else {
				path = Integer.valueOf( n.getPreviousNode().getVertex() ).toString() + " ---> " + path;
				System.out.println( startVertex + " ---> " + endVertex + " (" + cost + ")\n" + path );
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
				sock.close();
				soc.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
