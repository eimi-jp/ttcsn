package constant;

/**
 * Enum định nghĩa các loại thuật toán tìm đường
 */
public enum AlgorithmType {
    BFS("BFS (Breadth-First Search)", "Đảm bảo tìm được đường đi"),
    DFS("DFS (Depth-First Search)", "Tìm nhanh nhưng không đảm bảo tối ưu"),
    ASTAR("A* (A-Star)", "Tối ưu với heuristic");
    
    private final String name;
    private final String description;
    
    AlgorithmType(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
