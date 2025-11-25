package algorithm;

import entity.Graph;
import entity.Node;
import exception.InvalidGraphException;
import exception.PathNotFoundException;

import java.util.*;

/**
 * Thuật toán BFS (Breadth-First Search) để tìm đường đi ngắn nhất
 */
public class BFSAlgorithm implements PathFinder {

    private final int V;
    private final ArrayList<ArrayList<Integer>> adj;
    private final int width;

    public BFSAlgorithm(Graph graph, int mazeWidth) throws InvalidGraphException {
        if (graph == null) {
            throw new InvalidGraphException(constant.Messages.EXCEPTION_INVALID_GRAPH);
        }
        
        this.V = graph.getV();
        this.adj = graph.getAdj();
        this.width = mazeWidth;
    }

    @Override
    public List<Node> findPath(int startX, int startY, int endX, int endY) {
        int start = startY * width + startX;
        int end = endY * width + endX;
        
        if (start < 0 || start >= V || end < 0 || end >= V) {
            throw new PathNotFoundException(constant.Messages.EXCEPTION_INVALID_START);
        }

        boolean[] visited = new boolean[V];
        int[] parent = new int[V];
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < V; i++) {
            visited[i] = false;
            parent[i] = -1;
        }

        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            if (u == end) {
                return reconstructPath(parent, start, end);
            }

            for (int v : adj.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    parent[v] = u;
                    queue.add(v);
                }
            }
        }

        throw new PathNotFoundException(constant.Messages.EXCEPTION_PATH_NOT_FOUND);
    }

    private List<Node> reconstructPath(int[] parent, int start, int end) {
        LinkedList<Integer> path = new LinkedList<>();
        int crawl = end;

        while (crawl != -1) {
            path.addFirst(crawl);
            crawl = parent[crawl];
        }

        if (path.isEmpty() || path.getFirst() != start) {
            throw new PathNotFoundException(constant.Messages.EXCEPTION_CANNOT_BUILD_PATH);
        }

        List<Node> nodePath = new ArrayList<>();
        for (int nodeId : path) {
            int x = nodeId % width;
            int y = nodeId / width;
            nodePath.add(new Node(x, y));
        }

        return nodePath;
    }

    @Override
    public String getAlgorithmName() {
        return "BFS (Breadth-First Search)";
    }
}
