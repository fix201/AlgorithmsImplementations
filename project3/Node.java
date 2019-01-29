package project3;

import java.util.*;

class Node {

	// Private variables
	private Map<String, Integer> nodes; // key: building name, value: path to another building
	private ArrayList<String> nameVertex; // building names
	private ArrayList<Integer> lengthEdge; // building paths

	// Constructor
	public Node() {
		// private variable initialization
		nodes = new HashMap<String, Integer>();
		nameVertex = new ArrayList<String>();
		lengthEdge = new ArrayList<Integer>();
	}

	// Add a building and its length from another building
	public void addNode(String name, int length) {
		nodes.put(name, length);
		lengthEdge.add(length);
		nameVertex.add(name);
	}

	//
	@Override
	public String toString() {
		String tr = "";

		for (String x : nodes.keySet())
			tr += x + "=" + nodes.get(x) + ",";

		return tr;
	}

	// Getter: vertex
	public ArrayList<String> getVertex() {
		return nameVertex;
	}

	// Getter: edge length
	public int getEdgeLength(String vertex) {
		return nodes.get(vertex);
	}



}
