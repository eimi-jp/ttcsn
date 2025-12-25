package benchmark;

import ui.ConsoleUI;
import ui.MenuHandler;

import java.io.IOException;

public class BenchmarkApp {
    public static void main(String[] args) {
        ConsoleUI consoleUI = new ConsoleUI();
        MenuHandler menuHandler = new MenuHandler(consoleUI);

        int runCount = consoleUI.getRunCount();

        Benchmark benchmark = new Benchmark();
        BenchmarkResult result = null;
        try {
            result = benchmark.run(runCount, menuHandler, consoleUI);
        } catch (IOException e) {
            System.out.println("Có lỗi xảy ra, hãy thử đóng file xlsl và thử lại");
            return;
        }

        new BenchmarkUI().show(result);
    }
}
