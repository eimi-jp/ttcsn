package ui;

import constant.AnsiColors;
import constant.Config;
import entity.Node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Hiển thị mê cung với đường đi một cách trực quan
 */
public class MazeVisualizer {
    
    private final int[][] maze;
    private final int width;
    private final int height;
    
    public MazeVisualizer(int[][] maze) {
        this.maze = maze;
        this.height = maze.length;
        this.width = maze[0].length;
    }
    
    /**
     * Hiển thị mê cung với đường đi được highlight
     */
    public void displayMazeWithPath(List<Node> path, int[] start, int[] end) {
        // Tạo set chứa các điểm trong đường đi để tra cứu nhanh
        Set<String> pathSet = new HashSet<>();
        if (path != null) {
            for (Node node : path) {
                pathSet.add(node.x + "," + node.y);
            }
        }
        
        System.out.println("\n" + createBorder());
        
        // Duyệt qua từng ô trong mê cung
        for (int y = 0; y < height; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < width; x++) {
                String cellKey = x + "," + y;
                
                if (maze[y][x] == 1) {
                    // Tường
                    line.append(colorize(Config.WALL_SYMBOL, AnsiColors.WALL_COLOR));
                } else if (x == start[0] && y == start[1]) {
                    // Điểm bắt đầu
                    line.append(colorize(Config.START_SYMBOL, AnsiColors.START_COLOR));
                } else if (x == end[0] && y == end[1]) {
                    // Điểm kết thúc
                    line.append(colorize(Config.END_SYMBOL, AnsiColors.END_COLOR));
                } else if (pathSet.contains(cellKey)) {
                    // Đường đi
                    line.append(colorize(Config.ROUTE_SYMBOL, AnsiColors.ROUTE_COLOR));
                } else {
                    // Đường trống
                    line.append(Config.PATH_SYMBOL);
                }
            }
            System.out.println(line);
        }
        
        System.out.println(createBorder());
    }
    
    /**
     * Hiển thị mê cung không có đường đi
     */
    public void displayMaze() {
        System.out.println("\n" + createBorder());
        
        for (int y = 0; y < height; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < width; x++) {
                if (maze[y][x] == 1) {
                    line.append(colorize(Config.WALL_SYMBOL, AnsiColors.WALL_COLOR));
                } else {
                    line.append(Config.PATH_SYMBOL);
                }
            }
            System.out.println(line);
        }
        
        System.out.println(createBorder());
    }
    
    /**
     * Tạo border cho mê cung
     */
    private String createBorder() {
        return "═".repeat(width * 2);
    }
    
    /**
     * Colorize text nếu terminal hỗ trợ ANSI
     */
    private String colorize(String text, String color) {
        return AnsiColors.colorize(text, color);
    }
    
    /**
     * Hiển thị legend (chú thích)
     */
    public void displayLegend() {
        System.out.println("\nChú thích:");
        System.out.println("  " + colorize(Config.WALL_SYMBOL, AnsiColors.WALL_COLOR) + " = Tường");
        System.out.println("  " + Config.PATH_SYMBOL + " = Lối đi");
        System.out.println("  " + colorize(Config.START_SYMBOL, AnsiColors.START_COLOR) + " = Điểm bắt đầu");
        System.out.println("  " + colorize(Config.END_SYMBOL, AnsiColors.END_COLOR) + " = Điểm kết thúc");
        System.out.println("  " + colorize(Config.ROUTE_SYMBOL, AnsiColors.ROUTE_COLOR) + " = Đường đi tìm được");
    }
}
