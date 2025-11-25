package entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Đại diện cho đồ thị dạng danh sách kề
 */
public class Graph {

    private final int V; // Số đỉnh
    private final ArrayList<ArrayList<Integer>> adj; // Danh sách kề

    public Graph(int v) {
        this.V = v;
        adj = new ArrayList<>(v);
        for(int i = 0; i < v; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }

    public int getV() {
        return V;
    }

    public ArrayList<ArrayList<Integer>> getAdj() {
        return adj;
    }

    /**
     * In danh sách kề của đồ thị
     */
    public void printGraph() {
        for(int i = 0; i < V; i++) {
            System.out.print(i + ": ");
            for (int v : adj.get(i)) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
    }

    /**
     * In đồ thị dưới dạng cây
     */
    public void printAsTree(int start) {
        Set<Integer> visited = new HashSet<>();
        printTree(start, visited, 0, "");
    }

    private void printTree(int node, Set<Integer> visited, int level, String prefix) {
        if(visited.contains(node)) return;
        visited.add(node);

        System.out.println(prefix + node);

        ArrayList<Integer> neighbors = adj.get(node);
        for(int i = 0; i < neighbors.size(); i++) {
            int neighbor = neighbors.get(i);
            String newPrefix = prefix + (i == neighbors.size() - 1 ? "└─" : "├─");
            printTree(neighbor, visited, level + 1, newPrefix);
        }
    }
}
