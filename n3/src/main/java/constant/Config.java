package constant;

/**
 * Quản lý các cấu hình tham số của hệ thống
 */
public class Config {
    
    // MAZE CONFIGURATION
    public static final int MIN_MAZE_SIZE = 5;
    public static final int MAX_MAZE_SIZE = 101;
    
    //  DISPLAY CONFIGURATION
    public static final String WALL_SYMBOL = "██";
    public static final String PATH_SYMBOL = "  ";
    public static final String ROUTE_SYMBOL = "··";
    public static final String START_SYMBOL = " S";
    public static final String END_SYMBOL = " E";
    public static final String PATH_ARROW = " → ";
    public static final int NODES_PER_LINE = 5;
    
    //  ALGORITHM CONFIGURATION
    public static final int ALGORITHM_BFS = 1;
    public static final int ALGORITHM_DFS = 2;
    public static final int ALGORITHM_ASTAR = 3;
    
    // Private constructor để ngăn khởi tạo
    private Config() {
        throw new AssertionError("Cannot instantiate constants class");
    }
}
