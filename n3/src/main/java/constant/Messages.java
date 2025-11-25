package constant;

/**
 * Quản lý tập trung tất cả các message trong hệ thống
 */
public class Messages {
    

    
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
