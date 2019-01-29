package project3;

import java.io.*;
import java.util.*;

public class ShortestPath {

	// Private variables
	private Map<String, Node> myMap = new TreeMap<String, Node>(); //
	private ArrayList<String> allKey = new ArrayList<String>(); // building names

	private String[] prev;
	private Integer[] dist;

	// Constructor
	public ShortestPath() {

		prev = new String[19];
		dist = new Integer[19];

		for (int i = 0; i < 19; i++) {
			prev[i] = null;
			dist[i] = 9999;
		}
	}

	// Getter for all keys
	public void getAllKey() {
		// List of all services
		for (String key : myMap.keySet())
			allKey.add(key);
	}

	@Override
	public String toString() {
		String tr = "";
		for (String x : myMap.keySet()) {
			System.out.println(x + ": " + myMap.get(x));
		}
		return tr;
	}

	// Reads from the file and stores individual lines in an ArrayList
	public void initialize(String myFileName) {

		// create arraylist of lines
		ArrayList<String> lines = new ArrayList<String>();
		String line;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(myFileName);
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null)
				lines.add(line);// add lines to ArrayList of lines

			System.out.println();
			System.out.println("!!!!!!!!!!DONE READING FILE!!!!!!!!!!");
			System.out.println();

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + myFileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + myFileName + "'");
		}

		// after reading file, start to split each line by calling split method
		for (String s : lines) {
			splitLine(s);
		}
	}

	// split method extracts useful data from any line that's passed in
	public void splitLine(String line) {

		// create a new node
		Node node = new Node();
		// tokens array of string save string after split by ":"
		// token array splits the string into 2 parts key: building and (value: another
		// building and its distance from the key) -> description
		String[] tokens = line.split(":");
		// description array of string save string tokens[1] after split by ","
		// the description contains the length of the key from another building
		String[] description = tokens[1].split(",");

		// convert 1st string of description into double data type
		for (int i = 0; i < description.length; i++) {
			String[] split = description[i].split("=");
			int foo = Integer.parseInt(split[1]);
			node.addNode(split[0], foo);
		}
		myMap.put(tokens[0], node);
	}

	// This method implements Dijkstra's single source shortest path algorithm for a
	// graph represented using adjacency matrix representation
	public void dijkstra(String building) {

		// Call method to get all keys
		getAllKey();

		// Checks first if the building exists in the map
		if (allKey.contains(building)) {
			int x = allKey.indexOf(building);
			dist[x] = 0;// dist from building is 0
			String[] queueH;// make queue

			ArrayList<String> unsettleNode = new ArrayList<String>();
			for (String copy : allKey)
				unsettleNode.add(copy);

			while (unsettleNode.size() != 0) {
				queueH = unsettleNode.toArray(new String[unsettleNode.size()]);
				String u = this.deletemin(queueH);
				unsettleNode.remove(u);

				Node nextBuildings = new Node();
				nextBuildings = myMap.get(u);
				ArrayList<String> allNextbuildings = nextBuildings.getVertex();

				String v = "";
				int length = 0;
				for (int m = 0; m < allNextbuildings.size(); m++) {
					v = allNextbuildings.get(m);
					length = nextBuildings.getEdgeLength(v);
					update(u, v, length);
				}
			}
		}

		System.out.println("Dijkstra:   ");

		for (int i = 0; i < dist.length; i++) {
			System.out.print("distance from " + building + " to  " + allKey.get(i) + " is   " + dist[i] + "   previous building is  " + prev[i]);
			System.out.println();
		}

	}

	public String deletemin(String[] queueH) {
		// get all index of queueH and compare the dist from it index in allKey, to find
		// min and return the key of the min
		ArrayList<Integer> indexofQueue = new ArrayList<Integer>();
		int min = 0;
		if (queueH.length != 0) {

			for (int i = 0; i < queueH.length; i++) {
				indexofQueue.add(allKey.indexOf(queueH[i]));
			}
			min = indexofQueue.get(0);
			for (int index : indexofQueue) {
				// min = index;
				if (dist[min] > dist[index])
					min = index;
			}
			return allKey.get(min);
		}
		return null;
	}

	// Bellman Ford
	public void shortestPaths(String building) {

		allKey.clear();

		for (String x : myMap.keySet()) {
			allKey.add(x);
		}
		System.out.println(allKey);
		for (int i = 0; i < allKey.size(); i++) {
			dist[i] = 9999;
			prev[i] = null;
		}
		int index_building = allKey.indexOf(building);
		dist[index_building] = 0;
		// repeat |V|-1 times:
		// for all e in E:
		for (int k = 0; k < allKey.size() - 1; k++) {

			for (int n = 0; n < allKey.size(); n++) {

				Node nextBuildings = new Node();
				nextBuildings = myMap.get(allKey.get(n));
				ArrayList<String> allNextbuildings = nextBuildings.getVertex();
				String u = allKey.get(n);
				String v = "";
				int length = 0;
				for (int m = 0; m < allNextbuildings.size(); m++) {
					v = allNextbuildings.get(m);
					length = nextBuildings.getEdgeLength(v);
					update(u, v, length);
				}
			}

		}
		System.out.println("Bellman Ford: ");

		for (int i = 0; i < dist.length; i++) {

			System.out.print("distance from " + building + " to  " + allKey.get(i) + " is   " + dist[i] + ".   previous building is  " + prev[i]);
			System.out.println();
		}
	}

	public void update(String u, String v, int length) {
		if (dist[allKey.indexOf(v)] > dist[allKey.indexOf(u)] + length) {
			dist[allKey.indexOf(v)] = dist[allKey.indexOf(u)] + length;
			prev[allKey.indexOf(v)] = u;
		}

	}

}
