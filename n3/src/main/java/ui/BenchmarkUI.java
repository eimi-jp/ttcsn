package ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class BenchmarkUI {

    public void displayCharts(long[][] timeResults, long[][] memoryResults, long[] totalTimeResults, long[] totalMemoryResults) {
        // Tạo các biểu đồ cho thời gian và bộ nhớ
        JFreeChart timeChart = createLineChart(createLineDataset(timeResults), "Time Comparison (per run) - Lower is better");
        JFreeChart memoryChart = createLineChart(createLineDataset(memoryResults), "Memory Comparison (per run) - Lower is better");

        JFreeChart totalTimeChart = createBarChart(createBarDataset(totalTimeResults), "Total Time Comparison - Lower is better");
        JFreeChart totalMemoryChart = createBarChart(createBarDataset(totalMemoryResults), "Total Memory Comparison - Lower is better");

        // Tạo các chart panel cho từng biểu đồ
        ChartPanel timeChartPanel = new ChartPanel(timeChart);
        ChartPanel memoryChartPanel = new ChartPanel(memoryChart);
        ChartPanel totalTimeChartPanel = new ChartPanel(totalTimeChart);
        ChartPanel totalMemoryChartPanel = new ChartPanel(totalMemoryChart);

        // Cài đặt kích thước cho các chart panel
        timeChartPanel.setPreferredSize(new Dimension(800, 580));
        memoryChartPanel.setPreferredSize(new Dimension(800, 580));
        totalTimeChartPanel.setPreferredSize(new Dimension(800, 580));
        totalMemoryChartPanel.setPreferredSize(new Dimension(800, 580));

        // Tạo JFrame để hiển thị các biểu đồ
        JFrame frame = new JFrame("Benchmark Results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sử dụng layout 2x2
        frame.setLayout(new GridLayout(2, 2));

        // Thêm các panel vào JFrame
        frame.getContentPane().add(timeChartPanel);
        frame.getContentPane().add(memoryChartPanel);
        frame.getContentPane().add(totalTimeChartPanel);
        frame.getContentPane().add(totalMemoryChartPanel);

        // Hiển thị cửa sổ
        frame.pack();
        frame.setVisible(true);
        frame.toFront();
    }

    // Tạo dataset cho biểu đồ line (thời gian hoặc bộ nhớ mỗi lần chạy)
    private DefaultCategoryDataset createLineDataset(long[][] results) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < results.length; i++) {
            dataset.addValue(results[i][0], "BFS", String.valueOf(i + 1));
            dataset.addValue(results[i][1], "DFS", String.valueOf(i + 1));
            dataset.addValue(results[i][2], "A*", String.valueOf(i + 1));
        }

        return dataset;
    }

    // Tạo dataset cho biểu đồ bar (tổng thời gian hoặc bộ nhớ)
    private DefaultCategoryDataset createBarDataset(long[] results) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(results[0], "Total", "BFS");
        dataset.addValue(results[1], "Total", "DFS");
        dataset.addValue(results[2], "Total", "A*");

        return dataset;
    }

    // Tạo biểu đồ line từ dataset
    private JFreeChart createLineChart(DefaultCategoryDataset dataset, String chartTitle) {
        JFreeChart chart = ChartFactory.createLineChart(
                chartTitle,
                "Run",
                "Values (ns / bytes)",
                dataset
        );
        return chart;
    }

    // Tạo biểu đồ bar từ dataset
    private JFreeChart createBarChart(DefaultCategoryDataset dataset, String chartTitle) {
        JFreeChart chart = ChartFactory.createBarChart(
                chartTitle,
                "Algorithms",
                "Values (ns / bytes)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        return chart;
    }
}
