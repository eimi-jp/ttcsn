package ui;

import constant.Config;
import constant.Messages;
import entity.Node;

import java.util.List;

/**
 * Format và hiển thị kết quả tìm đường
 */
public class ResultDisplay {
    
    private final ConsoleUI consoleUI;
    
    public ResultDisplay(ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }
    
    /**
     * Hiển thị kết quả tìm đường thành công với mê cung
     */
    public void displaySuccess(List<Node> path, long executionTimeMs, int[] start, int[] end) {
        consoleUI.displayMessage(Messages.RESULT_SUCCESS);
        displayStatistics(path, executionTimeMs);
        
        // Hiển thị mê cung với đường đi
        MazeVisualizer visualizer = consoleUI.getMazeVisualizer();
        if (visualizer != null) {
            consoleUI.displayMessage("\n🗺️  Mê cung với đường đi:");
            visualizer.displayMazeWithPath(path, start, end);
        }
        
        displayPath(path);
    }
    
    /**
     * Hiển thị thống kê
     */
    private void displayStatistics(List<Node> path, long executionTimeMs) {
        consoleUI.displayMessage(Messages.RESULT_STATISTICS);
        System.out.println(String.format(Messages.RESULT_PATH_LENGTH, path.size() - 1));
        System.out.println(String.format(Messages.RESULT_NODES_VISITED, path.size()));
        System.out.println(String.format(Messages.RESULT_EXECUTION_TIME, executionTimeMs));
    }
    
    /**
     * Hiển thị đường đi
     */
    private void displayPath(List<Node> path) {
        consoleUI.displayMessage(Messages.RESULT_PATH_TITLE);
        
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) {
                System.out.print(Config.PATH_ARROW);
                if ((i + 1) % Config.NODES_PER_LINE == 0) {
                    System.out.println();
                }
            }
        }
        System.out.println("\n");
    }
    
    /**
     * Hiển thị lỗi khởi tạo
     */
    public void displayInitializationError(String message) {
        consoleUI.displayError(String.format(Messages.ERROR_INITIALIZATION, message));
    }
    
    /**
     * Hiển thị lỗi không tìm thấy đường
     */
    public void displayPathNotFoundError(String message) {
        consoleUI.displayError(String.format(Messages.ERROR_PATH_NOT_FOUND, message));
    }
    
    /**
     * Hiển thị lỗi input
     */
    public void displayInputError(String message) {
        consoleUI.displayError(String.format(Messages.ERROR_INVALID_INPUT, message));
    }
    
    /**
     * Hiển thị lỗi không xác định
     */
    public void displayUnknownError(String message) {
        consoleUI.displayError(String.format(Messages.ERROR_UNKNOWN, message));
    }
}
