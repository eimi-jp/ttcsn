package mazegen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Graph {

    private final int V;
    private final ArrayList<ArrayList<Integer>> adj;

    public Graph(int v) {
        this.V = v;
        adj = new ArrayList<>(v);
        for (int i = 0; i < v; i++) {
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

    // in adjacency list của graph
    public void printGraph() {
        for (int i = 0; i < V; i++) {
            System.out.print(i + ": ");
            for (int v : adj.get(i)) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
    }

    public void printAsTree(int start) {
        Set<Integer> visited = new HashSet<>();
        printTree(start, visited, 0, ""); // thêm dấu nhánh
    }

    private void printTree(int node, Set<Integer> visited, int level, String prefix) {
        if (visited.contains(node)) return;
        visited.add(node);

        System.out.println(prefix + node);

        ArrayList<Integer> neighbors = adj.get(node);
        for (int i = 0; i < neighbors.size(); i++) {
            int neighbor = neighbors.get(i);
            String newPrefix = prefix + (i == neighbors.size() - 1 ? "└─" : "├─");
            printTree(neighbor, visited, level + 1, newPrefix);
        }
    }

}
