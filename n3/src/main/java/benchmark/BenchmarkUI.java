package benchmark;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class BenchmarkUI {

    public void show(BenchmarkResult r) {
        JFrame frame = new JFrame("Benchmark Results");
        frame.setLayout(new GridLayout(2, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new ChartPanel(
                createLineChart(r.timeResults, "Time per Run (Lower is better)", UnitType.TIME)));

        frame.add(new ChartPanel(
                createLineChart(r.memoryResults, "Memory per Run (Lower is better)", UnitType.MEMORY)));

        frame.add(new ChartPanel(
                createBarChart(r.totalTime, "Total Time (Lower is better)", UnitType.TIME)));

        frame.add(new ChartPanel(
                createBarChart(r.totalMemory, "Total Memory (Lower is better)", UnitType.MEMORY)));
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // FULL SCREEN
        frame.setVisible(true);

    }

    private JFreeChart createLineChart(long[][] data, String title, UnitType type) {

        UnitScale scale = detectUnit(data, type);
        DefaultCategoryDataset ds = new DefaultCategoryDataset();

        for (int i = 0; i < data.length; i++) {
            ds.addValue(data[i][0] / scale.divisor, "BFS", String.valueOf(i + 1));
            ds.addValue(data[i][1] / scale.divisor, "DFS", String.valueOf(i + 1));
            ds.addValue(data[i][2] / scale.divisor, "A*",  String.valueOf(i + 1));
        }

        return ChartFactory.createLineChart(
                title,
                "Run",
                "Value (" + scale.label + ")",
                ds
        );
    }

    private JFreeChart createBarChart(long[] data, String title, UnitType type) {

        long max = Math.max(data[0], Math.max(data[1], data[2]));
        UnitScale scale = detectUnit(new long[][]{{max}}, type);

        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        ds.addValue(data[0] / scale.divisor, "Total", "BFS");
        ds.addValue(data[1] / scale.divisor, "Total", "DFS");
        ds.addValue(data[2] / scale.divisor, "Total", "A*");

        JFreeChart chart = ChartFactory.createBarChart(
                title,
                "Algorithm",
                "Value (" + scale.label + ")",
                ds
        );

        // CHỈ đổi màu khi là biểu đồ MEMORY
        if (type == UnitType.MEMORY) {
            CategoryPlot plot = chart.getCategoryPlot();
            BarRenderer renderer = (BarRenderer) plot.getRenderer();

            renderer.setSeriesPaint(0, new Color(0, 159, 180)); // BFS
            renderer.setSeriesPaint(1, new Color(0, 159, 180)); // DFS
            renderer.setSeriesPaint(2, new Color(0, 159, 180)); // A*
        }

        return chart;
    }

    enum UnitType {
        TIME, MEMORY
    }

    private static class UnitScale {
        double divisor;
        String label;

        UnitScale(double divisor, String label) {
            this.divisor = divisor;
            this.label = label;
        }
    }

    private UnitScale detectUnit(long[][] data, UnitType type) {
        long max = Long.MIN_VALUE;

        for (long[] row : data)
            for (long v : row)
                max = Math.max(max, v);

        if (type == UnitType.TIME) {
            if (max < 1_000) return new UnitScale(1, "ns");
            if (max < 1_000_000) return new UnitScale(1_000, "µs");
            if (max < 1_000_000_000) return new UnitScale(1_000_000, "ms");
            return new UnitScale(1_000_000_000, "s");
        } else {
            if (max < 1024) return new UnitScale(1, "B");
            if (max < 1024 * 1024) return new UnitScale(1024, "KB");
            return new UnitScale(1024 * 1024, "MB");
        }
    }
}
