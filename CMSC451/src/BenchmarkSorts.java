/**
 * Name: Justin Smith
 * CMSC 451
 * Project: 1
 * Date: 2/10/13 7:35 PM
 * Requires: J2SE 7+
 */


public class BenchmarkSorts {
    private final int runsPerArray = 50;
    private int[] testSizes;
    private int[][][] counts;
    private long[][][] times;
    HeapSort sorter;

    BenchmarkSorts(int[] sizes) {
        testSizes = sizes;
        counts = new int[testSizes.length][2][runsPerArray];
        times = new long[testSizes.length][2][runsPerArray];
        sorter = new HeapSort();
        runSorts();
        displayReports();
    }

    public static void main(String[] args) {
        int[] testSizes = {10, 20, 50, 100, 200,
                500, 10000, 15000, 20000, 100000};
        new BenchmarkSorts(testSizes);
    }

    public void runSorts() {
        for(int i = 0; i < testSizes.length; i++) {
            for(int j = 0; j < runsPerArray; j++) {
                int[] test = generateIntegerArray(testSizes[i]);
                int[] recTest = new int[testSizes[i]];
                int[] itTest = new int[testSizes[i]];
                try {

                    // Currently recursiveSort does not work as intended
                    // it will randomly pass or fail the sort.  The larger the
                    // array the more chance it has of failing.
//                    System.arraycopy(test, 0, recTest, 0, test.length);
//                    sorter.recursiveSort(recTest);
//                    counts[i][1][j] = sorter.getCount();
//                    times[i][1][j] = sorter.getTime();

                    System.arraycopy(test, 0, itTest, 0, test.length);
                    sorter.iterativeSort(itTest);
                    counts[i][0][j] = sorter.getCount();
                    times[i][0][j] = sorter.getTime();

                } catch (UnsortedException e) {
                    System.out.println("\nSort failed!");
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void displayReports() {
        double[] data;

        System.out.println("\n\nReports from the runs");

        System.out.print("+");
        for(int i = 0; i < 119; i++) System.out.print("-");
        System.out.println("+");
        System.out.printf("|%-7s|%28s%27s|%28s%27s|%n", "Size N", "Iterative", "", "Recursive", "");
        System.out.print("+");
        for(int i = 0; i < 119; i++) System.out.print("-");
        System.out.println("+");
        System.out.printf("|%7s|", "");
        for(int i = 0; i < 2; i++) {
            System.out.printf("%13s|%13s|%13s|%13s|", "Average", "Standard", "Average", "Standard");
        }
        System.out.printf("%n|%7s|", "");
        for(int i = 0; i < 2; i++) {
            System.out.printf("%13s|%13s|%13s|%13s|", "Critical", "Deviation", "Execution", "Deviation");
        }
        System.out.printf("%n|%7s|", "");
        for(int i = 0; i < 2; i++) {
            System.out.printf("%13s|%13s|%13s|%13s|", "Operation", "of Count", "Time", "of Time");
        }
        System.out.printf("%n|%7s|", "");
        for(int i = 0; i < 2; i++) {
            System.out.printf("%13s|%13s|%13s|%13s|", "Count", "", "(Seconds)", "(Seconds)");
        }
        System.out.print("\n+");
        for(int i = 0; i < 119; i++) System.out.print("-");
        System.out.print("+");

        for(int i = 0; i < testSizes.length; i++) {
            System.out.printf("%n|%7d|", testSizes[i]);
            for(int j = 0; j < 2; j++) {
                data = getStats(counts[i][j], times[i][j]);
                System.out.printf("%,13.2f|%13.4f|%13.6f|%13.6f|", data[0], data[1], data[2], data[3]);
            }
        }

        System.out.print("\n+");
        for(int i = 0; i < 119; i++) System.out.print("-");
        System.out.println("+");
    }

    private static int[] generateIntegerArray(int size) {
        // Generate a random order array values between [0,1000], duplicates
        // allowed.

        int[] data = new int[size];

        for(int i = 0; i < size; i++)
            data[i] = (int) (Math.random() * 1000);

        return data;
    }

    private double[] getStats(int[] counts, long[] times) {
        double AverageCount = 0;
        double AverageTime = 0;
        double countVariance = 0;
        double DeviationCount = 0;
        double timeVariance = 0;
        double DeviationTime = 0;

        for(int i = 0; i < counts.length; i++) {
            AverageCount += counts[i];
            AverageTime += times[i] / 1000000000.0;
        }

        AverageCount = AverageCount / counts.length;
        AverageTime = AverageTime / times.length;

        for(int i = 0; i < counts.length; i++) {
            countVariance += (AverageCount - counts[i]) * (AverageCount - counts[i]);
            timeVariance += (AverageTime - (times[i]/1000000000.0)) * (AverageTime - (times[i]/1000000000.0));
        }

        countVariance = countVariance / counts.length;
        timeVariance = timeVariance / times.length;

        DeviationCount = Math.sqrt(countVariance);
        DeviationTime = Math.sqrt(timeVariance);



        double[] data = {AverageCount, DeviationCount, AverageTime, DeviationTime};
        return data;
    }
}
