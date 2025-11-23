package mazegen;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MazeGenerator {

    private static final int WALL = 1;
    private static final int PATH = 0;
    private final int width;
    private final int height;
    private final int[][] maze;
    private final Random rand = new Random();

    public MazeGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        maze = new int[height][width];

        // fill tất cả là tường
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                maze[y][x] = WALL;
            }
        }
    }

    public int[][] generate() {
        carve(1, 1);
        return maze;
    }

    private void carve(int x, int y) {
        // đổi ô hiện tại thành đường
        maze[y][x] = PATH;

        // random thứ tự 4 hướng
        int[] dirs = {0, 1, 2, 3};
        shuffle(dirs);

        for (int dir : dirs) {
            int dx = 0, dy = 0;

            switch (dir) {
                case 0 -> dx = 2; // phải
                case 1 -> dx = -2; // trái
                case 2 -> dy = 2; // xuống
                case 3 -> dy = -2; // lên
            }

            int nx = x + dx;
            int ny = y + dy;

            // check trong map & chưa được đào
            if (nx > 0 && ny > 0 && nx < width - 1 && ny < height - 1 && maze[ny][nx] == WALL) {
                // đục tường ở giữa
                maze[y + dy / 2][x + dx / 2] = PATH;
                carve(nx, ny); // đệ quy
            }
        }
    }

    private void shuffle(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int j = rand.nextInt(arr.length);
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    public int[] getStart() {
        return new int[]{1,1}; // mặc định là cell đầu carve
    }

    public int[] getEnd() {
        boolean[][] visited = new boolean[height][width];
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{1,1,0}); // x, y, distance
        visited[1][1] = true;

        int[] farthest = {1,1,0};

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], y = cur[1], d = cur[2];
            if(d > farthest[2]) farthest = cur;

            for(int[] dir : dirs) {
                int nx = x + dir[0], ny = y + dir[1];
                if(nx>0 && ny>0 && nx<width && ny<height && !visited[ny][nx] && maze[ny][nx]==PATH) {
                    visited[ny][nx] = true;
                    q.add(new int[]{nx, ny, d+1});
                }
            }
        }
        return new int[]{farthest[0], farthest[1]}; // x,y end
    }

    public void printMaze() {
        for (int[] row : maze) {
            for (int cell : row) {
                System.out.print(cell); // tường vs đường
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        MazeGenerator mg = new MazeGenerator(21, 15);
        int[][] maze = mg.generate();

        // in mê cung
        for (int[] row : maze) {
            for (int cell : row) {
                System.out.print(cell); // tường vs đường
            }
            System.out.println();
        }
    }
}
