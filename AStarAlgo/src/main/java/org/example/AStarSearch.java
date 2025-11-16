package org.example;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;


public class AStarSearch {

    private int[][] maze;
    private int rows, cols;
    
    private Node[][] gridNodes;

    private PriorityQueue<Node> openSet;
    
    private Set<Node> closedSet;

    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    public AStarSearch(int[][] maze) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;

        this.openSet = new PriorityQueue<>();
        this.closedSet = new HashSet<>();
        
        this.gridNodes = new Node[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gridNodes[i][j] = new Node(i, j);
            }
        }
    }


    private int calculateHeuristic(Node node, Node endNode) {
        return Math.abs(node.x - endNode.x) + Math.abs(node.y - endNode.y);
    }

    private boolean isValid(int x, int y) {
        return (x >= 0 && x < rows && y >= 0 && y < cols && maze[x][y] == 0);
    }

    public List<Node> findPath(int startX, int startY, int endX, int endY) {
        
        Node startNode = gridNodes[startX][startY];
        Node endNode = gridNodes[endX][endY];

        startNode.gCost = 0;
        startNode.hCost = calculateHeuristic(startNode, endNode);
        startNode.calculateFCost();

        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            
            Node currentNode = openSet.poll();

            if (currentNode.equals(endNode)) {
                return retracePath(currentNode);
            }

            closedSet.add(currentNode);

            for (int i = 0; i < 4; i++) {
                int neighborX = currentNode.x + dx[i];
                int neighborY = currentNode.y + dy[i];

                if (!isValid(neighborX, neighborY)) {
                    continue;
                }

                Node neighborNode = gridNodes[neighborX][neighborY];

                if (closedSet.contains(neighborNode)) {
                    continue;
                }


                int tentativeGCost = currentNode.gCost + 1;

                if (tentativeGCost < neighborNode.gCost) {
                    neighborNode.parent = currentNode;
                    neighborNode.gCost = tentativeGCost;
                    neighborNode.hCost = calculateHeuristic(neighborNode, endNode);
                    neighborNode.calculateFCost(); 

                    if (!openSet.contains(neighborNode)) {
                        openSet.add(neighborNode);
                    }
                }
            }
        }

        return null;
    }


    private List<Node> retracePath(Node endNode) {
        List<Node> path = new ArrayList<>();
        Node currentNode = endNode;
        while (currentNode != null) {
            path.add(currentNode);
            currentNode = currentNode.parent;
        }
        Collections.reverse(path);
        return path;
    }
}