package astar;

import mazegen.MazeGenerator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        

//        int[][] maze = {
//            {0, 0, 1, 0, 0, 0},
//            {0, 0, 1, 0, 1, 0},
//            {0, 0, 0, 0, 1, 0},
//            {0, 1, 1, 1, 1, 0},
//            {0, 0, 0, 0, 0, 0}
//        };


        MazeGenerator mg = new MazeGenerator(21, 15);
        int[][] maze = mg.generate();
        mg.printMaze();

        int startX = mg.getStart()[0];
        int startY = mg.getStart()[1];
        int endX = mg.getEnd()[1];
        int endY = mg.getEnd()[0];

        AStarSearch aStar = new AStarSearch(maze);

        List<Node> path = aStar.findPath(startX, startY, endX, endY);

        if (path != null) {
            System.out.println("Tìm thấy đường đi:");
            for (Node node : path) {
                System.out.print(node + " -> ");
            }
            System.out.println("ĐÍCH!");
        } else {
            System.out.println("Không tìm thấy đường đi.");
        }
    }
}