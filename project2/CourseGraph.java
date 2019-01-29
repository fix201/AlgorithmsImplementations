package project2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class CourseGraph {

	//private String course_name;
	private String course_symbol;
	private Map<String, ArrayList<String>> rev_graph;//this is reverse 
	private Map<String, ArrayList<String>> graph_ori;
	private ArrayList<String> allKey;

	//
	private Integer[] preVisited;
	private Integer[] postVisited;
	private Boolean[] visited;

	private final int numOfCourse = 31;
	private int num;

	//Constructor
	public CourseGraph() {

		this.course_symbol = "";
		rev_graph = new TreeMap<String, ArrayList<String>>();
		graph_ori = new TreeMap<String, ArrayList<String>>();
		allKey = new ArrayList<String>();

		preVisited = new Integer[numOfCourse];
		postVisited = new Integer[numOfCourse];
		visited = new Boolean[numOfCourse];

		for(int a = 0; a < numOfCourse; a++) {
			preVisited[a] = 0;
			postVisited[a] = 0;
			visited[a] = false;
		}

		num = 0;
	}

	//Returns all the keys
	public ArrayList<String> getAllKey() {

		ArrayList<String> string = new ArrayList<String>();

		for(String key : rev_graph.keySet())
			string.add(key);

		return string;
	}

	//Returns values(ArrayList) of key(String) from the HashMap
	public ArrayList<String> getvaluelist(String key) {

		ArrayList<String> a = new ArrayList<String>();

		for(String x : rev_graph.get(key)) {
			a.add(x);
		}

		return a;
	}

	//Reads from the file and stores individual lines in an ArrayList
	public void initialize(String myFileName) {
		//create arraylist of lines
		ArrayList<String> lines = new ArrayList<String>();
		String line;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(myFileName);
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while((line = bufferedReader.readLine()) != null) {
				lines.add(line);// add lines to ArrayList of lines
			}
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

		//after reading file, start to split each line by calling split method
		for(String s : lines) {
			splitLine(s);
		}
	}

	// split method to split each line into course names and pre reqs
	public void splitLine(String line) {

		ArrayList<String> prerequsite = new ArrayList<String>();

		//Splits each line into course and course pre reqs
		if(line.contains("(")) {

			String array[] = line.split("\\(|\\)");
			course_symbol = array[0].substring(0, 9);
			String list = array[1];

			//if more than 1 pre req, add other prereqs to the ArrayList 
			if(list.contains(",")) {

				String m[] = list.split(",");

				for(int i = 0; i < m.length; i++) {

					if(m[i].charAt(0) != ' ') {
						prerequsite.add(m[i]);
					} else {
						m[i] = m[i].substring(1, m[i].length());
						prerequsite.add(m[i]);
					}
				}
			} else
				prerequsite.add(list);

		} else {
			course_symbol = line.substring(0, 9);
		}

		//Add the key(course_symbol) and value(prerequsite) to the rev_graph
		rev_graph.put(course_symbol, prerequsite);
		setOriGraph();
	}

	//set original rev_graph with prereqs
	public void setOriGraph() {

		//Adds courses prereqs to an array list then put it in the map
		for(String x : rev_graph.keySet()) {
			ArrayList<String> oriVal = new ArrayList<String>();
			for(String s : rev_graph.keySet()) {
				if(getvaluelist(s).contains(x))
					oriVal.add(s);
			}
			graph_ori.put(x, oriVal);
		}

	}

	//DFS
	public void dfs() {
		//set all visit false and store all key set in array 
		// assign false for all visited elements
		allKey = getAllKey();

		for(int i = 0; i < allKey.size(); i++)
			visited[i] = false;

		//go through every key in the rev_graph
		for(int i = 0; i < allKey.size(); i++) {
			if(!visited[i])
				//explore the values of the key[i]
				explore(allKey.get(i), graph_ori.get(allKey.get(i)));
		}
	}

	//Explore
	public void explore(String key, ArrayList<String> values) {

		//get the number of that key with this values as values
		int a = allKey.indexOf(key);

		visited[a] = true;
		preVisited[a] = ++num;

		for(int j = 0; j < values.size(); j++) {

			//get index of value[j] in allKey;
			int k = allKey.indexOf(values.get(j));

			//get the class name that have this value as prerequisite
			if(visited[k] == false) {
				explore(values.get(j), graph_ori.get(values.get(j)));
			}

		}

		postVisited[a] = ++num;

	}

	//Previsit
	public void previsit() {
		System.out.println("preVisited");
		System.out.println(this.preVisited);
	}

	//Postvisit
	public void postvisit() {
		System.out.println("postVisited");
		System.out.println(this.postVisited);
	}

	//Selection sorting algorithm to sort previsit, postvisit, and keys from highest to lowest
	public void selectionSort() {

		for(int i = 0; i < numOfCourse; i++) {

			int max = i;

			for(int j = i; j < numOfCourse; j++)
				if(postVisited[max] < postVisited[j])
					max = j;

			int tem = postVisited[i];
			postVisited[i] = postVisited[max];
			postVisited[max] = tem;

			tem = preVisited[i];
			preVisited[i] = preVisited[max];
			preVisited[max] = tem;

			String temKey = allKey.get(i);
			allKey.set(i, allKey.get(max));
			allKey.set(max, temKey);
		}

		for(int i = 0; i < numOfCourse; i++) {
			System.out.print(preVisited[i] + ", ");
		}

		System.out.println("\n Sorted post Visited");
		for(int i = 0; i < numOfCourse; i++) {
			System.out.print(postVisited[i] + ", ");
		}

		System.out.println();
		System.out.print(allKey);

	}

	//Run the DFS and prints original graph
	public void printOri() {

		for(String key : graph_ori.keySet()) {
			System.out.println(key + graph_ori.get(key));
		}
	}

	//prints previsit and post visited order then sorts them by calling the selectionSort method 
	public void print() {

		System.out.println(rev_graph);
		for(int i = 0; i < numOfCourse; i++) {
			System.out.print(preVisited[i] + ", ");
		}

		System.out.println("\npost Visited");
		for(int i = 0; i < numOfCourse; i++) {
			System.out.print(postVisited[i] + ", ");
		}

		selectionSort();
	}

}
