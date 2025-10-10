import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int V = 8;
        Bfs g = new Bfs(V);

        // các cạnh đồ thị
        g.addEdge(0, 1);
        g.addEdge(0, 3);
        g.addEdge(1, 2);
        g.addEdge(3, 4);
        g.addEdge(3, 7);
        g.addEdge(4, 5);
        g.addEdge(4, 6);
        g.addEdge(4, 7);
        g.addEdge(5, 6);
        g.addEdge(6, 7);

        Scanner sc = new Scanner(System.in);
        System.out.println("nhập vào điểm bắt đầu:");
        int startNode= sc.nextInt();
        sc.nextLine();
        System.out.println("Nhập vào điểm kết thúc:");
        int endNode=sc.nextInt();
        sc.nextLine();

        g.findShortestPath(startNode, endNode);
    }
}