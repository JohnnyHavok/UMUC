package cmsc350.project3;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Name: Justin Smith
 * CMSC 350
 * Project: 3
 * Date: 12/1/12 1:31 PM
 * Requires: J2SE 7+
 */

public class Project3 {
    public static void main(String[] args) {
        // Creating a new Project3 will run through an interactive dialog allowing the user to create several
        // sorting scenarios involving integers and then test the 5 sorting algorithms against them.
        new Project3();


        // Uncomment this section to test an algorithms listed in the test() method on the sample data provided
        // Also runs this sample data through the the time trial mode where all five algorithms are run and timed.
//		Contact[] friends = new Contact[7];
//		friends[0] = new Contact ("John", "Smith", "610-555-7384");
//		friends[1] = new Contact ("Sarah", "Barnes", "215-555-3827");
//		friends[2] = new Contact ("Mark", "Riley", "733-555-2969");
//		friends[3] = new Contact ("Laura", "Getz", "663-555-3984");
//		friends[4] = new Contact ("Larry", "Smith", "464-555-3489");
//		friends[5] = new Contact ("Frank", "Phelps", "322-555-2284");
//		friends[6] = new Contact ("Marsha", "Grant", "243-555-2837");
//		test(friends);
//		new Project3(friends);


        // Uncomment this section to test the algorithms themselves on the sample integer list provided
        // Does not run these through the time trial mode where all five algorithms are run.
//		test (new Integer [] {1, 2});
//		test (new Integer [] {2, 1});
//		test (new Integer [] {1, 2, 3});
//		test (new Integer [] {1, 3, 2});
//		test (new Integer [] {2, 1, 3});
//		test (new Integer [] {2, 3, 1});
//		test (new Integer [] {3, 1, 2});
//		test (new Integer [] {3, 2, 1});
//		test (new Integer [] {1, 2, 3, 4, 5, 6});
//		test (new Integer [] {6, 5, 4, 3, 2, 1});
//		test (new Integer [] {6, 5, 4, 3, 2, 1, 10, 11, 12, 13, 14, 15, 16, 17, 25, 24, 23, 22, 21, 20});
//		test (new Integer [] {6, 5, 4, 3, 2, 1, 10, 11, 12, 13, 2, 2, 2, 14, 15, 16, 17, 25, 24, 23, 22, 21, 20});
//		test (new Integer [] {6, 5, 4, 3, 2, 1, 7, 8, 9, 10, 2, 2, 2, 11, 12, 13, 14, 20, 19, 18, 17, 16, 15});
//		test (new Integer [] {3, 7, 8, 9, 10, 4, 5, 6});
//		test (new Integer [] {23});
//		test (new Integer [] {1, 2, 2, 2, 3, 4, 5});
//		test (new Integer [] {5, 4, 3, 2, 2, 1});
//		test (new Integer [] {5, 6, 2, 2, 3, 4, 1, 2});

        // Uncomment this section to test the algorithms themselves on the sample string list provided
//		test (new String [] {"Dog", "Bird", "Zebra", "Cat", "Fish", "Monkey"});
//		new Project3(new String [] {"aaa", "6ytg", "hyyc", ":754", "908*", "las<", "las>"});



        System.out.println("\nFinished");
    }


    /**
     * Non-interactive constructor that runs the given comparable item array through all five algorithms.
     * This is good for building test cases that do not involve simple integers.
     * @param d : Array<Comparable> to be tested
     */
    public <T extends Comparable<? super T>> Project3(T[] d) {
        System.out.println("\nRunning non-interactive mode");
        System.out.println("Sorting algorithm evaluator\nAll arrays will be sorted into ascending order");
        printResults(runTest(d));
    }

    /**
     * Interactive constructor that builds an Integer array based on user input that dictates size and pre-sort order.
     */
    public Project3() {
        System.out.println("Sorting algorithm evaluator\nAll arrays will be sorted into ascending order");

        boolean quit = false;
        char input;

        while(!quit) {
            System.out.println("\nOptions: (A)scending, (D)escending, (R)andom, or (Q)uit");
            System.out.print("Array starting order > ");
            input = getNextChar();

            switch(input) {
                case 'A' :
                case 'a' :
                    printResults(runTest(genArray(1)));
                    break;
                case 'D' :
                case 'd' :
                    printResults(runTest(genArray(-1)));
                    break;
                case 'R' :
                case 'r' :
                    printResults(runTest(genArray(0)));
                    break;
                case 'Q' :
                case 'q' :
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    /**
     * runTest() - Runs the provided comparable array against the five different sorting algorithms and
     * returns an array of SortingStatsNode containing the results
     * @param data : Comparable array with information to be sorted
     * @return SortingStatsNode array containing the results from the five different sorting algorithms
     */
    private <T extends Comparable<? super T>> SortingStatsNode[] runTest(T[] data) {
        T[] t;
        SortingStatsNode[] results = new SortingStatsNode[5];

        System.out.println("Beginning time trial test of algorithms on array containing " + data.length +
                " elements.\n");

        t = data.clone();
        System.out.print("Calling selection sort....");
        results[0] = SortAlgorithmTester.selectionSort(t);
        System.out.println("Done");

        t = data.clone();
        System.out.print("Calling insertion sort....");
        results[1] = SortAlgorithmTester.insertionSort(t);
        System.out.println("Done");

        t = data.clone();
        System.out.print("Calling bubble sort....");
        results[2] = SortAlgorithmTester.bubbleSort(t);
        System.out.println("Done");

        t = data.clone();
        System.out.print("Calling quick sort....");
        results[3] = SortAlgorithmTester.quickSort(t);
        System.out.println("Done");

        t = data.clone();
        System.out.print("Calling merge sort....");
        results[4] = SortAlgorithmTester.mergeSort(t);
        System.out.println("Done");

        return results;
    }

    /**
     * genArray() - Creates an array based on user input.
     * @param flag : 1 > Ascending Order, -1 > Descending Order, 0 Random Order
     * @return Integer[] array sorted based on flag and sized based on user input
     */
    private Integer[] genArray(int flag) {
        System.out.print("Enter size of integer array to sort > ");
        int arraySize = 0;
        boolean done = false;
        while(!done) {
            arraySize = getNextInt();
            if(arraySize > 1)
                done = true;
            else
                System.out.println("Array size must be larger than 1, try again > ");
        }
        Integer[] data = new Integer[arraySize];

        if(flag == 1) {
            System.out.println("Generating ascending order array prior to sorting");
            for(int i = 1; i <= arraySize; i++)
                data[i-1] = i;
        } else if(flag == -1) {
            System.out.println("Generating descending order array prior to sorting");
            for(int i = arraySize-1; i >= 0; i--)
                data[arraySize-1-i] = i+1;
        } else {
            System.out.println("Generating random order array, values between [0,1000], duplicates allowed.");
            for(int i = 0; i < arraySize; i++)
                data[i] = (int) (Math.random() * 1000);
        }
        return data;
    }

    /**
     * printResults() - Prints the results found in an array of SortingStatsNode
     * @param a : Array of SortingStatsNode
     */
    private void printResults(SortingStatsNode[] a) {
        // Sort the array so things are ascending by runTime.  Bubble sort should work fast enough for 5 elements.
        int position, scan;
        SortingStatsNode temp;
        for(position = a.length - 1; position >= 0; position--) {
            for(scan = 0; scan <= position - 1; scan++) {
                if(a[scan].compareTo(a[scan+1]) > 0) {
                    // Swap values
                    temp = a[scan];
                    a[scan] = a[scan + 1];
                    a[scan + 1] = temp;
                }
            }
        }

        System.out.print("+");
        for(int i = 0; i < 99; i++) System.out.print("-");
        System.out.println("+");
        System.out.printf("|%-19s|%19s|%19s|%19s|%19s|%n", "Algorithm", "Run Time (s)", "Comparisons", "Swaps",
                "Recursive Calls");
        System.out.print("+");
        for(int i = 0; i < 99; i++) System.out.print("-");
        System.out.println("+");
        for(SortingStatsNode n : a) {
            System.out.printf("|%-19s|%19f|%,19d|%,19d|%,19d|%n", n.getName(), n.getRunTime()/1000000000.0,
                    n.getComparisons(), n.getSwaps(), n.getRecursiveCalls());
        }
        System.out.print("+");
        for(int i = 0; i < 99; i++) System.out.print("-");
        System.out.println("+");

    }

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    private char getNextChar() {
        String s;
        do {
            try {
                s = in.readLine();
                if(s.length() == 1)
                    return s.charAt(0);
                else
                    System.out.print("Invalid input, please try again > ");
            } catch (IOException e) {
                System.out.println("Error thrown from getNextChar input");
                e.printStackTrace();
            }
        } while(true);
    }

    private int getNextInt() {
        do {
            try {
                return Integer.parseInt(in.readLine());
            } catch (IOException e) {
                System.out.println("Error thrown from getNextInt input");
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.print("Enter numbers only, please try again > ");
            }
        } while(true);
    }

    // Test method provided by Prof. Duchon
    private static void test(Comparable[] list) {
        System.out.println("\n\nBefore:");
        for(Comparable i: list)
            System.out.println("    " + i);
        SortAlgorithmTester.quickSort(list);
        System.out.println("\nAfter: ");
        for(Comparable i: list)
            System.out.println("    " + i);
    }

    // Test method provided by Prof. Duchon
    private static void test(Integer[] list) {
        System.out.print("\n\nBefore: ");
        for(Integer i: list)
            System.out.printf("%3d", i);
        SortAlgorithmTester.mergeSort(list);
        System.out.print("\nAfter: ");
        for(Integer i: list)
            System.out.printf("%3d", i);
    }
}
