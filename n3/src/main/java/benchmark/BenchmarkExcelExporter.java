package benchmark;

import entity.Node;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Export benchmark ra Excel:
 * - Sheet Summary: tổng hợp time/memory
 * - Mỗi Run: vẽ 3 mê cung cạnh nhau (BFS | DFS | A*)
 *
 * Mỗi mê cung:
 * - Tô màu path trực tiếp (KHÔNG dropdown, KHÔNG ghi đè)
 * - Rất dễ nhìn, phù hợp báo cáo thuật toán
 */
public class BenchmarkExcelExporter {

    // ================= PUBLIC API =================

    public static void export(
            String filePath,
            BenchmarkResult result,
            int[][][] mazes,
            int[][] starts,
            int[][] ends,
            int[] widths,
            int[] heights,
            List<Node>[][] paths // paths[run][0=BFS,1=DFS,2=A*]
    ) throws IOException {

        Workbook wb = new XSSFWorkbook();

        createSummarySheet(wb, result, widths, heights);

        for (int i = 0; i < result.runCount; i++) {
            createRunSheet(
                    wb,
                    i,
                    mazes[i],
                    starts[i],
                    ends[i],
                    result,
                    paths[i]
            );
        }

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            wb.write(fos);
        }
        wb.close();

        System.out.println("Excel file: " + Paths.get(filePath).toAbsolutePath());
        openExcelFile(filePath);
    }

    // ================= SUMMARY SHEET =================

    private static void createSummarySheet(
            Workbook wb,
            BenchmarkResult r,
            int[] widths,
            int[] heights
    ) {
        Sheet sheet = wb.createSheet("Summary");

        Row header = sheet.createRow(0);
        String[] cols = {"Run", "Algorithm", "Time (ns)", "Memory (bytes)", "Width", "Height"};
        for (int i = 0; i < cols.length; i++) {
            header.createCell(i).setCellValue(cols[i]);
        }

        int rowIdx = 1;

        for (int i = 0; i < r.runCount; i++) {
            rowIdx = writeRow(sheet, rowIdx, i, "BFS",
                    r.timeResults[i][0], r.memoryResults[i][0],
                    widths[i], heights[i]);
            rowIdx = writeRow(sheet, rowIdx, i, "DFS",
                    r.timeResults[i][1], r.memoryResults[i][1],
                    widths[i], heights[i]);
            rowIdx = writeRow(sheet, rowIdx, i, "A*",
                    r.timeResults[i][2], r.memoryResults[i][2],
                    widths[i], heights[i]);
        }

        CellStyle bold = boldStyle(wb);
        String[] algos = {"BFS", "DFS", "A*"};
        for (int i = 0; i < 3; i++) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue("TOTAL");
            row.createCell(1).setCellValue(algos[i]);
            row.createCell(2).setCellValue(r.totalTime[i]);
            row.createCell(3).setCellValue(r.totalMemory[i]);
            row.getCell(0).setCellStyle(bold);
            row.getCell(1).setCellStyle(bold);
        }

        for (int i = 0; i < cols.length; i++) sheet.autoSizeColumn(i);
    }

    private static int writeRow(
            Sheet sheet, int rowIdx, int run, String algo,
            long time, long mem, int w, int h
    ) {
        Row row = sheet.createRow(rowIdx++);
        row.createCell(0).setCellValue(run + 1);
        row.createCell(1).setCellValue(algo);
        row.createCell(2).setCellValue(time);
        row.createCell(3).setCellValue(mem);
        row.createCell(4).setCellValue(w);
        row.createCell(5).setCellValue(h);
        return rowIdx;
    }

    // ================= RUN SHEET (3 MAZES) =================

    private static void createRunSheet(
            Workbook wb,
            int runIndex,
            int[][] maze,
            int[] start,
            int[] end,
            BenchmarkResult r,
            List<Node>[] paths
    ) {
        Sheet sheet = wb.createSheet("Run_" + (runIndex + 1));

        int mazeHeight = maze.length;
        int mazeWidth = maze[0].length;
        int gap = 3; // khoảng cách giữa các mê cung

        // Styles
        CellStyle wall   = mazeStyle(wb, IndexedColors.BLACK);
        CellStyle empty  = mazeStyle(wb, IndexedColors.WHITE);
        CellStyle startS = mazeStyle(wb, IndexedColors.BRIGHT_GREEN);
        CellStyle endS   = mazeStyle(wb, IndexedColors.RED);

        CellStyle bfsPath   = mazeStyle(wb, IndexedColors.LIGHT_BLUE);
        CellStyle dfsPath   = mazeStyle(wb, IndexedColors.LAVENDER);
        CellStyle astarPath = mazeStyle(wb, IndexedColors.LIGHT_YELLOW);

        // Title
        Row title = sheet.createRow(0);
        title.createCell(0).setCellValue("BFS");
        title.createCell(mazeWidth + gap).setCellValue("DFS");
        title.createCell(2 * (mazeWidth + gap)).setCellValue("A*");

        // Vẽ 3 mê cung
        drawMaze(sheet, maze, start, end, wall, empty, startS, endS,
                bfsPath, paths[0], 1, 0);

        drawMaze(sheet, maze, start, end, wall, empty, startS, endS,
                dfsPath, paths[1], 1, mazeWidth + gap);

        drawMaze(sheet, maze, start, end, wall, empty, startS, endS,
                astarPath, paths[2], 1, 2 * (mazeWidth + gap));

        // Info table
        int infoCol = 3 * (mazeWidth + gap);

        Row h = sheet.getRow(1);
        if (h == null) h = sheet.createRow(1);
        h.createCell(infoCol).setCellValue("Algorithm");
        h.createCell(infoCol + 1).setCellValue("Time (ns)");
        h.createCell(infoCol + 2).setCellValue("Memory (bytes)");

        writeAlgoRow(sheet, 2, infoCol, "BFS",
                r.timeResults[runIndex][0], r.memoryResults[runIndex][0]);
        writeAlgoRow(sheet, 3, infoCol, "DFS",
                r.timeResults[runIndex][1], r.memoryResults[runIndex][1]);
        writeAlgoRow(sheet, 4, infoCol, "A*",
                r.timeResults[runIndex][2], r.memoryResults[runIndex][2]);

        sheet.autoSizeColumn(infoCol);
        sheet.autoSizeColumn(infoCol + 1);
        sheet.autoSizeColumn(infoCol + 2);
    }

    // ================= DRAW MAZE =================

    private static void drawMaze(
            Sheet sheet,
            int[][] maze,
            int[] start,
            int[] end,
            CellStyle wall,
            CellStyle empty,
            CellStyle startS,
            CellStyle endS,
            CellStyle pathStyle,
            List<Node> path,
            int rowOffset,
            int colOffset
    ) {
        for (int y = 0; y < maze.length; y++) {
            Row row = sheet.getRow(y + rowOffset);
            if (row == null) row = sheet.createRow(y + rowOffset);
            row.setHeightInPoints(20);

            for (int x = 0; x < maze[y].length; x++) {
                sheet.setColumnWidth(colOffset + x, 600);
                Cell cell = row.createCell(colOffset + x);

                if (maze[y][x] == 1) cell.setCellStyle(wall);
                else cell.setCellStyle(empty);

                if (x == start[0] && y == start[1]) cell.setCellStyle(startS);
                if (x == end[0] && y == end[1]) cell.setCellStyle(endS);
            }
        }

        if (path == null) return;

        for (Node n : path) {
            int x = n.x;
            int y = n.y;

            if ((x == start[0] && y == start[1]) ||
                    (x == end[0] && y == end[1])) continue;

            sheet.getRow(y + rowOffset)
                    .getCell(colOffset + x)
                    .setCellStyle(pathStyle);
        }
    }

    // ================= STYLES =================

    private static CellStyle mazeStyle(Workbook wb, IndexedColors color) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(color.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private static CellStyle boldStyle(Workbook wb) {
        Font f = wb.createFont();
        f.setBold(true);
        CellStyle s = wb.createCellStyle();
        s.setFont(f);
        return s;
    }

    private static void writeAlgoRow(
            Sheet sheet,
            int rowIdx,
            int col,
            String algo,
            long time,
            long mem
    ) {
        Row row = sheet.getRow(rowIdx);
        if (row == null) row = sheet.createRow(rowIdx);
        row.createCell(col).setCellValue(algo);
        row.createCell(col + 1).setCellValue(time);
        row.createCell(col + 2).setCellValue(mem);
    }

    private static void openExcelFile(String filePath) {
        try {
            File file = Paths.get(filePath).toAbsolutePath().toFile();

            if (!file.exists()) {
                System.out.println("❌ File không tồn tại: " + file.getAbsolutePath());
                return;
            }

            System.out.println("📂 Excel file: " + file.getAbsolutePath());

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file); // mở Excel
            } else {
                System.out.println("⚠ Desktop not supported, mở file thủ công.");
            }
        } catch (Exception e) {
            System.out.println("❌ Không thể mở file Excel");
        }
    }
}
