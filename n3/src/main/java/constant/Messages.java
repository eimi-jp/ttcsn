package constant;

/**
 * Quản lý tập trung tất cả các message trong hệ thống
 */
public class Messages {

    // ==================== HEADER MESSAGES ====================
    public static final String APP_HEADER =
            "╔═══════════════════════════════════════════════════════╗\n" +
                    "║     CHƯƠNG TRÌNH TÌM ĐƯỜNG ĐI TRONG MÊ CUNG           ║\n" +
                    "╚═══════════════════════════════════════════════════════╝";

    // ==================== MENU MESSAGES ====================
    public static final String MENU_ALGORITHM_TITLE = "\n Chọn thuật toán tìm đường:";
    public static final String MENU_ALGORITHM_BFS = "  1. BFS (Breadth-First Search) - Đảm bảo tìm được đường đi";
    public static final String MENU_ALGORITHM_DFS = "  2. DFS (Depth-First Search) - Tìm nhanh nhưng không đảm bảo ngắn nhất";
    public static final String MENU_ALGORITHM_ASTAR = "  3. A* (A-Star) - Tối ưu với heuristic";
    public static final String MENU_PROMPT = "   Lựa chọn của bạn (1-3): ";

    // ==================== INPUT MESSAGES ====================
    public static final String INPUT_MAZE_SIZE = "\n Nhập kích thước mê cung:";
    public static final String INPUT_WIDTH = "  - Chiều rộng (số lẻ, tối thiểu 5): ";
    public static final String INPUT_HEIGHT = "  - Chiều cao (số lẻ, tối thiểu 5): ";
    public static final String INPUT_INVALID_NUMBER = "Vui lòng nhập một số nguyên hợp lệ";
    public static final String INPUT_OUT_OF_RANGE = "Vui lòng nhập số từ %d đến %d";
    public static final String INPUT_RUN_COUNT = "Please enter the number of runs for benchmarking: ";

    // ==================== PROCESS MESSAGES ====================
    public static final String PROCESS_GENERATING_MAZE = "Đang tạo mê cung %dx%d...";
    public static final String PROCESS_MAZE_GENERATED = "\nMê cung được tạo:";
    public static final String PROCESS_SEARCHING = "\nĐang tìm đường đi bằng thuật toán %s...";

    // ==================== RESULT MESSAGES ====================
    public static final String RESULT_SUCCESS = "\nTìm thấy đường đi!";
    public static final String RESULT_STATISTICS = "Thống kê:";
    public static final String RESULT_PATH_LENGTH = "  - Độ dài đường đi: %d bước";
    public static final String RESULT_NODES_VISITED = "  - Số nút đi qua: %d nút";
    public static final String RESULT_EXECUTION_TIME = "  - Thời gian thực thi: %d ms";
    public static final String RESULT_PATH_TITLE = "\nĐường đi:";
    public static final String RESULT_START_POINT = "\nĐiểm bắt đầu: (%d, %d)";
    public static final String RESULT_END_POINT = "Điểm kết thúc: (%d, %d)";

    // ==================== ERROR MESSAGES ====================
    public static final String ERROR_INITIALIZATION = "Lỗi khởi tạo: %s";
    public static final String ERROR_PATH_NOT_FOUND = "%s";
    public static final String ERROR_INVALID_INPUT = "Lỗi input: %s";
    public static final String ERROR_UNKNOWN = "Lỗi không xác định: %s";
    
    // EXCEPTION MESSAGES
    public static final String EXCEPTION_INVALID_MAZE = "Mê cung không hợp lệ";
    public static final String EXCEPTION_INVALID_GRAPH = "Đồ thị không hợp lệ";
    public static final String EXCEPTION_INVALID_START = "Điểm bắt đầu không hợp lệ";
    public static final String EXCEPTION_INVALID_END = "Điểm kết thúc không hợp lệ";
    public static final String EXCEPTION_PATH_NOT_FOUND = "Không tìm thấy đường đi từ điểm bắt đầu đến điểm kết thúc";
    public static final String EXCEPTION_CANNOT_BUILD_PATH = "Không thể xây dựng đường đi";
    public static final String EXCEPTION_INVALID_CHOICE = "Lựa chọn không hợp lệ";
    
    // Private constructor để ngăn khởi tạo
    private Messages() {
        throw new AssertionError("Cannot instantiate constants class");
    }
}
