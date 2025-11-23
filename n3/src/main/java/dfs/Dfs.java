package dfs;

import mazegen.Graph;
import java.util.ArrayList;
import java.util.LinkedList;

public class Dfs {

    private final int V; // Số đỉnh
    private final ArrayList<ArrayList<Integer>> adj; // danh sách đỉnh kề

    // khởi tạo đồ thị từ Graph
    public Dfs(Graph graph) {
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
     * Phương thức tìm và in một đường đi từ start đến end.
     * Lưu ý DFS không đảm bảo đường đi ngắn nhất.
     *
     * @param start Đỉnh bắt đầu
     * @param end   Đỉnh kết thúc
     */
    public void findPath(int start, int end) {
        // Mảng để đánh dấu các đỉnh đã được duyệt
        boolean[] visited = new boolean[V];
        // Mảng để lưu đỉnh cha, giúp truy vết đường đi
        int[] parent = new int[V];

        // Khởi tạo trạng thái ban đầu
        for (int i = 0; i < V; i++) {
            visited[i] = false;
            parent[i] = -1;
        }

        dfs(start, end, visited, parent);

        // truy vết đường đi
        printPath(parent, start, end);
    }

    /**
     * Hàm DFS đệ quy
     *
     * @param current đỉnh đang xét
     * @param end     đỉnh đích
     * @param visited mảng đánh dấu đã duyệt
     * @param parent  mảng lưu đỉnh cha
     * @return true nếu tìm thấy đỉnh end
     */
    private boolean dfs(int current, int end, boolean[] visited, int[] parent) {
        visited[current] = true;

        if (current == end) return true; // tìm thấy

        // Duyệt các đỉnh kề
        for (int v : adj.get(current)) {
            if (!visited[v]) {
                parent[v] = current;
                boolean found = dfs(v, end, visited, parent);
                if (found) return true; // dừng khi tìm thấy
            }
        }
        return false;
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

        System.out.println("DFS tìm được đường đi từ " + start + " đến " + end + " là:");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println("\nĐộ dài: " + (path.size() - 1));
    }
}
