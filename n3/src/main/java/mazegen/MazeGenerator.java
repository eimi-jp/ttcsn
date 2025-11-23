package mazegen;

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
