package benchmark;

import algorithm.PathFinder;
import constant.AlgorithmType;
import entity.MazeGenerator;
import entity.Node;
import ui.ConsoleUI;
import ui.MenuHandler;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Benchmark {

    public BenchmarkResult run(int runCount,
                               MenuHandler menuHandler,
                               ConsoleUI consoleUI) throws IOException {

        BenchmarkResult result = new BenchmarkResult(runCount);

        // ===== DỮ LIỆU LƯU ĐỂ EXPORT EXCEL =====
        int[][][] mazes = new int[runCount][][];
        int[][] starts = new int[runCount][2];
        int[][] ends = new int[runCount][2];
        int[] widths = new int[runCount];
        int[] heights = new int[runCount];

        // (OPTIONAL) nếu sau này muốn vẽ path
        // paths[run][0=BFS,1=DFS,2=A*]
        List<Node>[][] paths = new List[runCount][3];

        for (int i = 0; i < runCount; i++) {

            // ===== TẠO MAZE =====
            MazeGenerator mazeGen = initializeMaze();
            int[][] maze = mazeGen.generate();

            int[] start = mazeGen.getStart();
            int[] end = mazeGen.getEnd();

            // Lưu để export
            mazes[i] = maze;
            starts[i] = start;
            ends[i] = end;
            widths[i] = mazeGen.getWidth();
            heights[i] = mazeGen.getHeight();

            // ===== TẠO THUẬT TOÁN =====
            PathFinder bfs = menuHandler.createPathFinder(
                    AlgorithmType.BFS, maze, mazeGen.getWidth());

            PathFinder dfs = menuHandler.createPathFinder(
                    AlgorithmType.DFS, maze, mazeGen.getWidth());

            PathFinder astar = menuHandler.createPathFinder(
                    AlgorithmType.ASTAR, maze, mazeGen.getWidth());

            // ===== ĐO BFS =====
            MeasureResult bfsRes = measure(
                    bfs, start[0], start[1], end[0], end[1]);
            paths[i][0] = bfsRes.path;

            // ===== ĐO DFS =====
            MeasureResult dfsRes = measure(
                    dfs, start[0], start[1], end[0], end[1]);
            paths[i][1] = dfsRes.path;

            // ===== ĐO A* =====
            MeasureResult astarRes = measure(
                    astar, start[0], start[1], end[0], end[1]);
            paths[i][2] = astarRes.path;

            // Lưu time
            result.timeResults[i][0] = bfsRes.timeNs;
            result.timeResults[i][1] = dfsRes.timeNs;
            result.timeResults[i][2] = astarRes.timeNs / 3;

            // Lưu memory
            result.memoryResults[i][0] = bfsRes.memoryBytes;
            result.memoryResults[i][1] = dfsRes.memoryBytes;
            result.memoryResults[i][2] = astarRes.memoryBytes;
        }

        // ===== TÍNH TỔNG =====
        aggregate(result);

        // ===== EXPORT EXCEL =====
        BenchmarkExcelExporter.export(
                "benchmark_results.xlsx",
                result,
                mazes,
                starts,
                ends,
                widths,
                heights,
                paths
        );

        return result;
    }

    // ===== CỘNG TỔNG TIME + MEMORY =====
    private void aggregate(BenchmarkResult r) {
        for (int i = 0; i < r.runCount; i++) {
            for (int j = 0; j < 3; j++) {
                r.totalTime[j] += r.timeResults[i][j];
                r.totalMemory[j] += r.memoryResults[i][j];
            }
        }
    }

    // ===== ĐO TIME + MEMORY + LẤY PATH (CHẠY 1 LẦN) =====
    private static MeasureResult measure(PathFinder finder,
                                         int sx, int sy,
                                         int ex, int ey) {

        Runtime rt = Runtime.getRuntime();
        rt.gc(); // giảm nhiễu GC

        long memBefore = rt.totalMemory() - rt.freeMemory();
        long timeStart = System.nanoTime();

        // CHỈ GỌI findPath() 1 LẦN
        List<Node> path = finder.findPath(sx, sy, ex, ey);

        long timeEnd = System.nanoTime();
        long memAfter = rt.totalMemory() - rt.freeMemory();

        return new MeasureResult(
                timeEnd - timeStart,
                Math.max(0, memAfter - memBefore),
                path
        );
    }

    // ===== TẠO MAZE NGẪU NHIÊN =====
    private static MazeGenerator initializeMaze() {
        Random r = new Random();
        int w = r.nextInt(41) + 10;
        int h = r.nextInt(41) + 10;

        // đảm bảo kích thước lẻ
        if (w % 2 == 0) w++;
        if (h % 2 == 0) h++;

        return new MazeGenerator(w, h);
    }

    // ===== KẾT QUẢ ĐO 1 LẦN =====
    static class MeasureResult {
        long timeNs;           // thời gian (ns)
        long memoryBytes;      // bộ nhớ (byte)
        List<Node> path;       // đường đi

        MeasureResult(long timeNs, long memoryBytes, List<Node> path) {
            this.timeNs = timeNs;
            this.memoryBytes = memoryBytes;
            this.path = path;
        }
    }
}
