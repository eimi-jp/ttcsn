package entity;

/**
 * Xây dựng đồ thị từ mê cung dạng ma trận
 */
public class GraphBuilder {
    
    /**
     * Chuyển đổi mê cung 2D thành đồ thị
     * @param maze Ma trận mê cung (0: đường đi, 1: tường)
     * @return Đồ thị tương ứng
     */
    public static Graph fromMaze(int[][] maze) {
        int rows = maze.length;
        int cols = maze[0].length;
        int V = rows * cols;
        Graph g = new Graph(V);

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for(int x = 0; x < rows; x++){
            for(int y = 0; y < cols; y++){
                if(maze[x][y] == 1) continue; // Bỏ qua tường
                int u = x * cols + y; // ID của node
                for(int[] d : dirs){
                    int nx = x + d[0];
                    int ny = y + d[1];
                    if (nx >= 0 && nx < rows && ny >= 0 && ny < cols && maze[nx][ny] == 0){
                        int v = nx * cols + ny;
                        g.addEdge(u, v);
                    }
                }
            }
        }

        return g;
    }
}
