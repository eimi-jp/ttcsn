package entity;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Sinh mê cung ngẫu nhiên sử dụng thuật toán Recursive Backtracking
 */
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

        // Khởi tạo toàn bộ là tường
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                maze[y][x] = WALL;
            }
        }
    }

    /**
     * Sinh mê cung
     * @return Ma trận mê cung
     */
    public int[][] generate() {
        carve(1, 1);
        addLoops((width * height) / 20);
        return maze;
    }

    private void carve(int x, int y) {
        maze[y][x] = PATH;

        // Trộn ngẫu nhiên 4 hướng
        int[] dirs = {0, 1, 2, 3};
        shuffle(dirs);

        for(int dir : dirs) {
            int dx = 0, dy = 0;

            switch (dir) {
                case 0 -> dx = 2;  // Phải
                case 1 -> dx = -2; // Trái
                case 2 -> dy = 2;  // Xuống
                case 3 -> dy = -2; // Lên
            }

            int nx = x + dx;
            int ny = y + dy;

            if(nx > 0 && ny > 0 && nx < width - 1 && ny < height - 1 && maze[ny][nx] == WALL) {
                maze[y + dy / 2][x + dx / 2] = PATH;
                carve(nx, ny);
            }
        }
    }

    private void shuffle(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            int j = rand.nextInt(arr.length);
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    public void addLoops(int count) {
        for (int i = 0; i < count; i++) {
            int x = rand.nextInt(width - 2) + 1;
            int y = rand.nextInt(height - 2) + 1;

            // Chỉ phá nếu là tường
            if (maze[y][x] == WALL) {
                maze[y][x] = PATH;
            }
        }
    }

    /**
     * Lấy điểm bắt đầu mặc định
     */
    public int[] getStart() {
        return new int[]{1, 1};
    }

    /**
     * Tìm điểm kết thúc xa nhất từ điểm bắt đầu
     */
    public int[] getEnd() {
        boolean[][] visited = new boolean[height][width];
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{1, 1, 0}); // x, y, distance
        visited[1][1] = true;

        int[] farthest = {1, 1, 0};

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], y = cur[1], d = cur[2];
            if (d > farthest[2]) farthest = cur;

            for (int[] dir : dirs) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx > 0 && ny > 0 && nx < width && ny < height && !visited[ny][nx] && maze[ny][nx] == PATH) {
                    visited[ny][nx] = true;
                    q.add(new int[]{nx, ny, d + 1});
                }
            }
        }
        return new int[]{farthest[0], farthest[1]};
    }

    /**
     * In mê cung ra console
     */
    public void printMaze() {
        for (int[] row : maze) {
            for (int cell : row) {
                System.out.print(cell == WALL ? "█" : " ");
            }
            System.out.println();
        }
    }

    public int[][] getMaze() {
        return maze;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
