package ui;

import algorithm.*;
import constant.AlgorithmType;
import constant.Config;
import constant.Messages;
import entity.Graph;
import entity.GraphBuilder;

/**
 * Xử lý menu và điều hướng
 */
public class MenuHandler {
    
    private final ConsoleUI consoleUI;
    
    public MenuHandler(ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }
    
    /**
     * Hiển thị menu chọn thuật toán
     */
    public void displayAlgorithmMenu() {
        consoleUI.displayMessage(Messages.MENU_ALGORITHM_TITLE);
        consoleUI.displayMessage(Messages.MENU_ALGORITHM_BFS);
        consoleUI.displayMessage(Messages.MENU_ALGORITHM_DFS);
        consoleUI.displayMessage(Messages.MENU_ALGORITHM_ASTAR);
    }
    
    /**
     * Lấy lựa chọn thuật toán từ người dùng
     */
    public AlgorithmType selectAlgorithmType() {
        displayAlgorithmMenu();
        int choice = consoleUI.getValidIntInput(Messages.MENU_PROMPT, 1, 3);
        
        return switch (choice) {
            case Config.ALGORITHM_BFS -> AlgorithmType.BFS;
            case Config.ALGORITHM_DFS -> AlgorithmType.DFS;
            case Config.ALGORITHM_ASTAR -> AlgorithmType.ASTAR;
            default -> AlgorithmType.BFS; // Default fallback
        };
    }
    
    /**
     * Tạo instance thuật toán dựa trên lựa chọn
     */
    public PathFinder createPathFinder(AlgorithmType type, int[][] maze, int width) {
        return switch (type) {
            case BFS -> {
                Graph graph = GraphBuilder.fromMaze(maze);
                yield new BFSAlgorithm(graph, width);
            }
            case DFS -> {
                Graph graph = GraphBuilder.fromMaze(maze);
                yield new DFSAlgorithm(graph, width);
            }
            case ASTAR -> new AStarAlgorithm(maze);
        };
    }
}
