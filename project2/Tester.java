package project2;

//Main Tester class
public class Tester {

	public static void main(String[] args) {

		//Create a CourseGraph object
		CourseGraph a1 = new CourseGraph();

		//Initialize it
		String filename = "project2/courses.txt";
		a1.initialize(filename);

		//Run the DFS
		a1.dfs();

		//Print out result
		System.out.println("original graph");
		System.out.println("----------------------------------");
		a1.printOri();
		a1.print();
	}
}
