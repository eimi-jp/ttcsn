package dfs;

import bfs.Bfs;
import mazegen.Graph;
import mazegen.GraphBuilder;
import mazegen.MazeGenerator;

public class Main {
    public static void main(String[] args) {
        MazeGenerator mg = new MazeGenerator(21, 15);
        int[][] maze = mg.generate();
        mg.printMaze();

        Graph g = GraphBuilder.fromMaze(maze);
        g.printAsTree(0);
        Dfs dfs = new Dfs(g);

        int[] start = mg.getStart(); // {1,1}
        int[] end = mg.getEnd();     // farthest cell
        int startNode = start[1] * 21 + start[0];
        int endNode = end[1] * 21 + end[0];
        dfs.findPath(startNode, endNode);



//        Scanner sc = new Scanner(System.in);
//        System.out.println("nhập vào điểm bắt đầu:");
//        int startNode= sc.nextInt();
//        sc.nextLine();
//        System.out.println("Nhập vào điểm kết thúc:");
//        int endNode=sc.nextInt();
//        sc.nextLine();

       // bfs.findShortestPath(startNode, endNode);
    }
}