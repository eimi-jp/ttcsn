import algorithm.PathFinder;
import constant.Messages;
import entity.MazeGenerator;
import org.jfree.chart.plot.PlotOrientation;
import ui.ConsoleUI;
import constant.AlgorithmType;
import ui.MenuHandler;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BenchMark {

    public static void main(String[] args) {
        ConsoleUI consoleUI = new ConsoleUI();
        MenuHandler menuHandler = new MenuHandler(consoleUI);

        try {
            // Display header and instructions
            consoleUI.displayHeader();

            // Initialize maze


            // Get number of runs from user
            int runCount = consoleUI.getRunCount();

            // Initialize arrays to store results for each run
            long[][] timeResults = new long[runCount][3];  // Store time for each run and each algorithm
            long[][] memoryResults = new long[runCount][3]; // Store memory for each run and each algorithm

            // Loop for each run count
            for (int i = 0; i < runCount; i++) {
                MazeGenerator mazeGen = initializeMaze();
                int[][] maze = mazeGen.generate();

                int[] start = mazeGen.getStart();
                int[] end = mazeGen.getEnd();
                consoleUI.displayStartEndPoints(start, end);

                // Create PathFinder for each algorithm
                PathFinder bfs = menuHandler.createPathFinder(AlgorithmType.BFS, maze, mazeGen.getWidth());
                PathFinder dfs = menuHandler.createPathFinder(AlgorithmType.DFS, maze, mazeGen.getWidth());
                PathFinder aStar = menuHandler.createPathFinder(AlgorithmType.ASTAR, maze, mazeGen.getWidth());

                // Benchmark BFS
                timeResults[i][0] = measureExecutionTime(() -> bfs.findPath(start[0], start[1], end[0], end[1]));
                memoryResults[i][0] = measureMemoryUsage(() -> bfs.findPath(start[0], start[1], end[0], end[1]));

                // Benchmark DFS
                timeResults[i][1] = measureExecutionTime(() -> dfs.findPath(start[0], start[1], end[0], end[1]));
                memoryResults[i][1] = measureMemoryUsage(() -> dfs.findPath(start[0], start[1], end[0], end[1]));

                // Benchmark A*
                //timeResults[i][2] = measureExecutionTime(() -> aStar.findPath(start[0], start[1], end[0], end[1]));
                //memoryResults[i][2] = measureMemoryUsage(() -> aStar.findPath(start[0], start[1], end[0], end[1]));
            }

            // Calculate total time and memory for each algorithm across all runs
            long[] totalTimeResults = new long[3];
            long[] totalMemoryResults = new long[3];
            for (int i = 0; i < runCount; i++) {
                totalTimeResults[0] += timeResults[i][0];
                totalTimeResults[1] += timeResults[i][1];
                totalTimeResults[2] += timeResults[i][2];

                totalMemoryResults[0] += memoryResults[i][0];
                totalMemoryResults[1] += memoryResults[i][1];
                totalMemoryResults[2] += memoryResults[i][2];
            }

            // Convert time from nanoseconds to milliseconds or seconds if needed
            totalTimeResults = convertTimeUnits(totalTimeResults);

            // Create charts for time and memory
            JFreeChart timeChart = createLineChart(createLineDataset(timeResults), "Time Comparison (per run) - Lower is better");
            JFreeChart memoryChart = createLineChart(createLineDataset(memoryResults), "Memory Comparison (per run) - Lower is better");

            JFreeChart totalTimeChart = createBarChart(createBarDataset(totalTimeResults), "Total Time Comparison - Lower is better");
            JFreeChart totalMemoryChart = createBarChart(createBarDataset(totalMemoryResults), "Total Memory Comparison - Lower is better");

            // Create chart panels for each chart
            ChartPanel timeChartPanel = new ChartPanel(timeChart);
            ChartPanel memoryChartPanel = new ChartPanel(memoryChart);
            ChartPanel totalTimeChartPanel = new ChartPanel(totalTimeChart);
            ChartPanel totalMemoryChartPanel = new ChartPanel(totalMemoryChart);

            // Set panel sizes for each chart
            timeChartPanel.setPreferredSize(new java.awt.Dimension(800, 580));
            memoryChartPanel.setPreferredSize(new java.awt.Dimension(800, 580));
            totalTimeChartPanel.setPreferredSize(new java.awt.Dimension(800, 580));
            totalMemoryChartPanel.setPreferredSize(new java.awt.Dimension(800, 580));

            // Create a JFrame to display all the charts in one window
            JFrame frame = new JFrame("Benchmark Results");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Set layout to 2x2 grid
            frame.setLayout(new GridLayout(2, 2));

            // Add the panels to the JFrame
            frame.getContentPane().add(timeChartPanel);
            frame.getContentPane().add(memoryChartPanel);
            frame.getContentPane().add(totalTimeChartPanel);
            frame.getContentPane().add(totalMemoryChartPanel);

            // Pack and display the frame
            frame.pack();
            frame.setVisible(true);
            frame.toFront();  // Bring the window to the front to ensure it's active

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Measure execution time of an algorithm
    public static long measureExecutionTime(Runnable algorithm) {
        long startTime = System.nanoTime();
        algorithm.run();
        long endTime = System.nanoTime();
        return (endTime - startTime);  // Time in nanoseconds
    }

    // Measure memory usage of an algorithm
    public static long measureMemoryUsage(Runnable algorithm) {
        Runtime runtime = Runtime.getRuntime();
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        algorithm.run();
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        return afterMemory - beforeMemory;  // Memory usage in bytes
    }

    // Create dataset for line chart (time or memory per run)
    public static DefaultCategoryDataset createLineDataset(long[][] results) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < results.length; i++) {
            dataset.addValue(results[i][0], "BFS", String.valueOf(i + 1));
            dataset.addValue(results[i][1], "DFS", String.valueOf(i + 1));
            dataset.addValue(results[i][2], "A*", String.valueOf(i + 1));
        }

        return dataset;
    }

    // Create dataset for bar chart (total time or memory)
    public static DefaultCategoryDataset createBarDataset(long[] results) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Change color of Total Memory bar to green
        dataset.addValue(results[0], "Total", "BFS");
        dataset.addValue(results[1], "Total", "DFS");
        dataset.addValue(results[2], "Total", "A*");

        return dataset;
    }

    // Create a line chart from the dataset
    public static JFreeChart createLineChart(DefaultCategoryDataset dataset, String chartTitle) {
        JFreeChart chart = ChartFactory.createLineChart(
                chartTitle, // Chart title
                "Run", // X-axis label
                "Values (ns / bytes)", // Y-axis label (time in ns or memory in bytes)
                dataset
        );

        // Customize the chart
        CategoryPlot plot = chart.getCategoryPlot();
        plot.getRangeAxis().setLabel("Units");

        return chart;
    }

    // Create a bar chart from the dataset
    public static JFreeChart createBarChart(DefaultCategoryDataset dataset, String chartTitle) {
        JFreeChart chart = ChartFactory.createBarChart(
                chartTitle, // Chart title
                "Algorithms", // X-axis label
                "Values (ns / bytes)", // Y-axis label (time in ns or memory in bytes)
                dataset,
                PlotOrientation.VERTICAL, // Chart orientation
                true, // Include legend
                true, // Tooltips enabled
                false // URLs disabled
        );

        // Change color for Total Memory (green)
        chart.getCategoryPlot().getRenderer().setSeriesPaint(3, new java.awt.Color(0, 128, 0)); // Green for Total Memory

        return chart;
    }

    // Convert time from nanoseconds to milliseconds or seconds based on the time
    public static long[] convertTimeUnits(long[] totalTimeResults) {
        long[] convertedResults = new long[totalTimeResults.length];

        for (int i = 0; i < totalTimeResults.length; i++) {
            if (totalTimeResults[i] > 1_000_000_000) {
                convertedResults[i] = totalTimeResults[i] / 1_000_000;  // Convert to milliseconds if time > 1 second
            } else {
                convertedResults[i] = totalTimeResults[i] / 1_000_000;  // Otherwise, keep it in milliseconds
            }
        }

        return convertedResults;
    }

    // Initialize maze generator with random width and height
    private static MazeGenerator initializeMaze() {
        Random random = new Random();

        // Randomly generate width and height within a desired range
        int minSize = 10;
        int maxSize = 50;
        int width = random.nextInt((maxSize - minSize) + 1) + minSize;  // Random width between minSize and maxSize
        int height = random.nextInt((maxSize - minSize) + 1) + minSize; // Random height between minSize and maxSize

        // Ensure the dimensions are odd (optional for maze generation)
        if (width % 2 == 0) width++;
        if (height % 2 == 0) height++;

        MazeGenerator mazeGen = new MazeGenerator(width, height);
        mazeGen.generate();

        return mazeGen;
    }
}
