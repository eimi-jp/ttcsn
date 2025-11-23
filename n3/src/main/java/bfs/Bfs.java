package bfs;

import mazegen.Graph;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Bfs {

    private final int V; // Số đỉnh
    private final ArrayList<ArrayList<Integer>> adj; // danh sách đỉnh kề

    //    khởi tạo đồ thị từ Graph
    public Bfs(Graph graph) {
        this.V = graph.getV();
        this.adj = graph.getAdj();
    }

    /**
     *
     * @param u Đỉnh kề
     * @param v Đỉnh kề
     *
     */
    public void addEdge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }
    /**
     * Phương thức tìm và in đường đi ngắn nhất từ start đến end.
     *
     * @param start Đỉnh bắt đầu
     * @param end   Đỉnh kết thúc
     */
    public void findShortestPath(int start, int end) {
        // Mảng để đánh dấu các đỉnh đã được duyệt
        boolean[] visited = new boolean[V];
        // Mảng để lưu đỉnh cha, giúp truy vết đường đi
        int[] parent = new int[V];

        Queue<Integer> queue = new LinkedList<>();

        // Khởi tạo trạng thái ban đầu
        for (int i = 0; i < V; i++) {
            visited[i] = false;
            parent[i] = -1;
        }

        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            // Nếu đã đến đỉnh đích, dừng tìm kiếm
            if (u == end) {
                break;
            }

            // Duyệt các đỉnh kề của u
            for (int v : adj.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    parent[v] = u;
                    queue.add(v);
                }
            }
        }
        // truy vết đường đi
        printPath(parent, start, end);
    }

    /**
     * Phương thức riêng tư để truy vết và in ra đường đi.
     */
    private void printPath(int[] parent, int start, int end) {
        LinkedList<Integer> path = new LinkedList<>();
        int crawl = end;

        // Bắt đầu từ đỉnh cuối, đi ngược về đỉnh đầu theo mảng parent
        while (crawl != -1) {
            path.addFirst(crawl);
            crawl = parent[crawl];
        }

        if (path.isEmpty() || path.getFirst() != start) {
            System.out.println("Không tồn tại đường đi từ " + start + " đến " + end);
            return;
        }

        System.out.println("Đường đi ngắn nhất từ " + start + " đến " + end + " là:");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println("\nĐộ dài: " + (path.size() - 1));
    }
}
