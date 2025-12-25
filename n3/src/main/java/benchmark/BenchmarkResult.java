package benchmark;

public class BenchmarkResult {
    public long[][] timeResults;
    public long[][] memoryResults;
    public long[] totalTime;
    public long[] totalMemory;
    public int runCount;

    public BenchmarkResult(int runCount) {
        this.runCount = runCount;
        timeResults = new long[runCount][3];
        memoryResults = new long[runCount][3];
        totalTime = new long[3];
        totalMemory = new long[3];
    }
}

