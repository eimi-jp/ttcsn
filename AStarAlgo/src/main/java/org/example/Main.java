package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        

        int[][] maze = {
            {0, 0, 1, 0, 0, 0},
            {0, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0}
        };

        int startX = 0, startY = 0;
        int endX = 4, endY = 5;

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