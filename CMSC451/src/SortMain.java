/**
 * Name: Justin Smith
 * CMSC 451
 * Project:
 * Date: 3/10/13 7:10 PM
 * Requires: J2SE 7+
 */


public class SortMain {
    public static void main(String[] args) {
        int[] testSizes = {10, 20, 50, 100, 200,
                500, 10000, 15000, 20000, 100000};

        BenchmarkSorts sorter = new BenchmarkSorts(testSizes);
        sorter.runSorts();
        sorter.displayReports();
    }
}
