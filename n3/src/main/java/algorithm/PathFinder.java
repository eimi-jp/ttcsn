package algorithm;

import entity.Node;
import java.util.List;

/**
 * Interface cho các thuật toán tìm đường
 */
public interface PathFinder {
    
    /**
     * Tìm đường đi từ điểm bắt đầu đến điểm kết thúc
     * @param startX Tọa độ x của điểm bắt đầu
     * @param startY Tọa độ y của điểm bắt đầu
     * @param endX Tọa độ x của điểm kết thúc
     * @param endY Tọa độ y của điểm kết thúc
     * @return Danh sách các nút tạo thành đường đi, null nếu không tìm thấy
     */
    List<Node> findPath(int startX, int startY, int endX, int endY);
    
    /**
     * Lấy tên thuật toán
     * @return Tên thuật toán
     */
    String getAlgorithmName();
}
