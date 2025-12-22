package ui;

import constant.Config;
import constant.Messages;

import java.util.Scanner;

/**
 * Xử lý các tương tác input/output với console
 */
public class ConsoleUI {
    
    private final Scanner scanner;
    private MazeVisualizer mazeVisualizer;
    
    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Hiển thị header của ứng dụng
     */
    public void displayHeader() {
        System.out.println(Messages.APP_HEADER);
    }
    
    /**
     * Hiển thị thông báo
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }
    
    /**
     * Hiển thị thông báo lỗi
     */
    public void displayError(String errorMessage) {
        System.err.println(errorMessage);
    }
    
    /**
     * Lấy input số nguyên hợp lệ từ người dùng
     */
    public int getValidIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println(String.format(Messages.INPUT_OUT_OF_RANGE, min, max));
            } catch (NumberFormatException e) {
                System.out.println(Messages.INPUT_INVALID_NUMBER);
            }
        }
    }
    
    /**
     * Lấy kích thước mê cung từ người dùng
     */
    public int[] getMazeSize() {
        displayMessage(Messages.INPUT_MAZE_SIZE);
        
        int width = getValidIntInput(Messages.INPUT_WIDTH, Config.MIN_MAZE_SIZE, Config.MAX_MAZE_SIZE);
        int height = getValidIntInput(Messages.INPUT_HEIGHT, Config.MIN_MAZE_SIZE, Config.MAX_MAZE_SIZE);
        
        // Đảm bảo là số lẻ
        if (width % 2 == 0) width++;
        if (height % 2 == 0) height++;
        
        return new int[]{width, height};
    }
    
    /**
     * Hiển thị mê cung
     */
    public void displayMaze(int[][] maze) {
        displayMessage(Messages.PROCESS_MAZE_GENERATED);
        mazeVisualizer = new MazeVisualizer(maze);
        mazeVisualizer.displayMaze();
        mazeVisualizer.displayLegend();
    }
    
    /**
     * Hiển thị điểm bắt đầu và kết thúc
     */
    public void displayStartEndPoints(int[] start, int[] end) {
        System.out.println(String.format(Messages.RESULT_START_POINT, start[0], start[1]));
        System.out.println(String.format(Messages.RESULT_END_POINT, end[0], end[1]));
    }
    
    /**
     * Hiển thị thông báo đang tìm kiếm
     */
    public void displaySearching(String algorithmName) {
        System.out.println(String.format(Messages.PROCESS_SEARCHING, algorithmName));
    }
    
    /**
     * Đóng scanner
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
    
    /**
     * Lấy MazeVisualizer
     */
    public MazeVisualizer getMazeVisualizer() {
        return mazeVisualizer;
    }

    public int getRunCount() {
        return getValidIntInput(Messages.INPUT_RUN_COUNT, 1, Integer.MAX_VALUE);
    }
}
