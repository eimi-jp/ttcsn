package mazegen;

public class GraphBuilder {
    public static Graph fromMaze(int[][] maze) {
        int rows = maze.length;
        int cols = maze[0].length;
        int V = rows * cols;
        Graph g = new Graph(V);

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {

                if (maze[x][y] == 1)
                    continue; // tường thì bỏ

                int u = x * cols + y; // node id

                for (int[] d : dirs) {
                    int nx = x + d[0];
                    int ny = y + d[1];

                    if (nx >= 0 && nx < rows &&
                            ny >= 0 && ny < cols &&
                            maze[nx][ny] == 0) {

                        int v = nx * cols + ny;
                        g.addEdge(u, v);
                    }
                }
            }
        }

        return g;
    }
}
