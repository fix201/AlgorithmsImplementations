package project3;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ShortestPath pro3 = new ShortestPath();
		pro3.initialize("project3/data.txt");
		System.out.println(pro3);

		pro3.dijkstra("Math-Comp Science");
		pro3.shortestPaths("Math-Comp Science");
		pro3.shortestPaths("Lewis Science");
	}

}
