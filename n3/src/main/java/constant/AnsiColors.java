package constant;

/**
 * Quản lý màu ANSI cho terminal
 */
public class AnsiColors {
    
    // Reset
    public static final String RESET = "\u001B[0m";
    
    // Regular Colors
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    
    // Bold
    public static final String BOLD = "\u001B[1m";
    
    // Background Colors
    public static final String BG_BLACK = "\u001B[40m";
    public static final String BG_RED = "\u001B[41m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String BG_YELLOW = "\u001B[43m";
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_MAGENTA = "\u001B[45m";
    public static final String BG_CYAN = "\u001B[46m";
    public static final String BG_WHITE = "\u001B[47m";
    
    // Bright Colors
    public static final String BRIGHT_BLACK = "\u001B[90m";
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_MAGENTA = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_WHITE = "\u001B[97m";
    
    // Maze specific colors
    public static final String WALL_COLOR = BG_WHITE + BLACK;
    public static final String PATH_COLOR = RESET;
    public static final String ROUTE_COLOR = BRIGHT_GREEN + BOLD;
    public static final String START_COLOR = BRIGHT_CYAN + BOLD;
    public static final String END_COLOR = BRIGHT_YELLOW + BOLD;
    
    // Kiểm tra xem terminal có hỗ trợ ANSI colors không
    private static final boolean ANSI_SUPPORTED = checkAnsiSupport();
    
    private static boolean checkAnsiSupport() {
        String os = System.getProperty("os.name").toLowerCase();
        // Windows 10+ hỗ trợ ANSI
        if (os.contains("win")) {
            try {
                // Enable ANSI support on Windows
                new ProcessBuilder("cmd", "/c", "").inheritIO().start().waitFor();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Wrap text với màu, tự động reset sau đó
     */
    public static String colorize(String text, String color) {
        if (!ANSI_SUPPORTED) {
            return text;
        }
        return color + text + RESET;
    }
    
    /**
     * Kiểm tra xem ANSI colors có được hỗ trợ không
     */
    public static boolean isAnsiSupported() {
        return ANSI_SUPPORTED;
    }
    
    // Private constructor
    private AnsiColors() {
        throw new AssertionError("Cannot instantiate constants class");
    }
}
