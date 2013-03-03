/**
 * Name: Justin Smith
 * CMSC 451
 * Project: 1
 * Date: 2/10/13 12:52 PM
 * Requires: J2SE 7+
 */


public class HeapSort implements SortInterface {
    private int count;
    private long time;

    public HeapSort() {
        count = 0;
        time = 0;
    }

    /**
     * Source Attribution:
     * http://en.wikibooks.org/wiki/Algorithm_implementation/Sorting/Heapsort#Java_2
     *
     * Precondition: A complete array of integers, no null elements
     * Postcondition: A sorted (in place) array of integers
     * @param array The array to be sorted
     * @throws UnsortedException Indicates that the array was not successfully
     * sorted by this algorithm.
     */
    @Override
    public void iterativeSort(int[] array) throws UnsortedException {
        System.out.println("\nBeginning iterative HeapSort");
        count = 0; // Counts calls to arraySwap. All other options seem trivial..
        long start = System.nanoTime();

    /* Build max-heap */
        for (int heapsize = 0; heapsize < array.length; heapsize++) {
        /* Step one in adding an element to the heap in the
         * place that element at the end of the heap array-
         * in this case, the element is already there. */
            int n = heapsize; // the index of the inserted int
            while (n > 0) { // until we reach the root of the heap
                int p = (n-1)/2; // the index of the parent of n

                if (array[n] > array[p]) { // child is larger than parent
                    count += 1;
                    arraySwap(array, n, p); // swap child with parent
                    n = p; // check parent
                } else { // parent is larger than child
                    break; // all is good in the heap
                }
            }
        }

    /* In place heap sort */
        for (int heapsize = array.length; heapsize > 0;) {
            // Swap root with last element in place.
            arraySwap(array, 0, --heapsize);
            count += 1;

            int n = 0; // index of the element being moved down the tree

            while (true) {
                int left = n * 2 + 1;
                if (left >= heapsize) // node has no left child
                    break; // reached the bottom; heap is heapified

                int right = left + 1;
                if (right >= heapsize) { // node has a left child, but no right child
                    if (array[left] > array[n]) // if left child is greater than node
                        arraySwap(array, left, n); // swap left child with node
                        count += 1;
                    break; // heap is heapified
                }

                if (array[left] > array[n]) { // (left > n)
                    if (array[left] > array[right]) { // (left > right) & (left > n)
                        arraySwap(array, left, n);
                        count += 1;
                        n = left; continue; // continue recursion on left child
                    } else { // (right > left > n)
                        arraySwap(array, right, n);
                        count += 1;
                        n = right; continue; // continue recursion on right child
                    }
                } else { // (n > left)
                    if (array[right] > array[n]) { // (right > n > left)
                        arraySwap(array, right, n);
                        count += 1;
                        n = right;
                        continue; // continue recursion on right child
                    } else { // (n > left) & (n > right)
                        break; // node is greater than both children, so it's heapified
                    }
                }
            }
        }

        time = System.nanoTime() - start;

    /* Check that array is sorted */
        System.out.println("Checking to see the iterative HeapSort method " +
                "correctly sorted the array....");

        try {
            checkArray(array);
        } catch (UnsortedException e) {
            throw new UnsortedException(e.getMessage());
        }

        System.out.println("The array was correctly sorted by the iterative " +
            "HeapSort method.");
    }

    /**
     * Source Attribution:
     * Java implementation of Heap Sort Algorithm
     * Introduction to Algorithms 3rd Edition (2009), Thomas H. Cormen (et al.)
     *
     * TODO: Randomly passes or fails sort, fix this!
     *
     * Precondition: A complete array of integers, no null elements
     * Postcondition: A sorted (in place) array of integers
     * @param array The array to be sorted
     * @throws UnsortedException Indicates that the array was not successfully
     * sorted by this algorithm.
     */
    @Override
    public void recursiveSort(int[] array) throws UnsortedException {
    /* Set counts and time to starting point. */
        System.out.println("\nBeginning recursive HeapSort");
        count = 0; // Reflects every time maxHeapify is called.
        long start = System.nanoTime();

    /* Turn array into a Max-Heap (in place) */
        buildMaxHeap(array);

    /* The idea is that we swap the first element in the max-heap (the largest
     * element) and the last element (the smallest) and then call maxHeapify
     * to put the array back into a binary-max-heap form minus the last element.
     * We control the bounds of the array that maxHeapify will work on thus
     * protected the sorted part of the array (the back end).
     */
        for(int i = array.length - 1; i >= 1; i--) {
            arraySwap(array, 0, i);
            maxHeapify(array, 0, i - 1);
        }

     /* Array has been sorted - stop the clock */
        time = System.nanoTime() - start;

    /* Check that array is sorted */
        System.out.println("Checking to see if recursive HeapSort method " +
                "correctly sorted the array....");

        try {
            checkArray(array);
        } catch (UnsortedException e) {
            throw new UnsortedException(e.getMessage());
        }

        System.out.println("The array was correctly sorted by the recursive " +
                "HeapSort method.");
    }

    /**
     * Internal method that turns an unsorted array into a binary-max-heap so
     * that it can later be rearranged into a min-heap.
     * @param a Array to be sorted into a binary-max-heap
     */
    private void buildMaxHeap(int[] a) {
    /* We take the array divided by 2 so that we work backwards through all
     * the leaves of the binary-tree and run maxHeapify on them.
     */
        for(int i = (int) Math.floor((a.length - 1) / 2); i >= 0; i --) {
            maxHeapify(a, i, a.length - 1);
        }
    }

    /**
     * Internal method that takes an array and turns it into a binary-max-heap.
     * Much trouble was encountered making this work from pseudo code to Java.
     * @param a Array to be sorted into a binary-max-heap
     * @param root The root index of the binary-tree
     * @param heapArea The length of the array that contains the heap
     */
    private void maxHeapify(int[] a, int root, int heapArea) {
        count+=1;
        int left = root + 1; // Left child of root
        int right = root + 2; // Right child of root
        int max;

        if(left <= heapArea && a[left] > a[root]) {
            max = left;
        } else {
            max = root;
        }

        if(right <= heapArea && a[right] > a[max]) {
            max = right;
        }

        if(max != root) {
            arraySwap(a, root, max);
            maxHeapify(a, max, heapArea);
        }
    }

    @Override
    public int getCount() { return count; }

    @Override
    public long getTime() { return time; }

    /**
     * Swaps integer at array position at i with array position j.
     * @param a Reference of array to perform swap on
     * @param i Index of an element to swap
     * @param j Index of an element to swap
     */
    private static void arraySwap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static void checkArray(int[] a) throws UnsortedException {
        for (int i = 0; i < a.length - 1; i++)
            if(a[i] > a[i + 1]) {
                for(int j = 0; j < a.length -1; j++)
                    System.out.print(" "  + a[j]);
                throw new UnsortedException("The array was not sorted " +
                        "correctly: \n" +
                        a[i] + " > " + a[i+1] + " at indices " + i +
                        " and " + (i+1) + " respectively.\n");
            }
    }

    // Main method for testing during initial work.
    public static void main(String[] args) {
        HeapSort sorter = new HeapSort();
        int[] base = new int[20];
        int[] itTest = new int[base.length];
        int[] recTest = new int[base.length];

        for(int k = 0; k < base.length; k++)
            base[k] = (int) (Math.random() * 10);

        System.arraycopy(base, 0, itTest, 0, base.length);

        try {
            sorter.iterativeSort(itTest);
        } catch (UnsortedException e) {
            System.out.println("\n" + e.getMessage());
            System.exit(1);
        }
        for(int i = 0; i < itTest.length; i++)
            System.out.print(" " + itTest[i]);

        System.out.println();

        System.arraycopy(base, 0, recTest, 0, base.length);

        for(int j = 0; j < 500; j++) {
            System.arraycopy(base, 0, recTest, 0, base.length);
            try {
                sorter.recursiveSort(recTest);
            } catch (UnsortedException e) {
                System.out.println("\n" + e.getMessage());
                System.exit(1);
            }
        }

        for(int i = 0; i < recTest.length; i++)
            System.out.print(" " + recTest[i]);

        System.out.println();

    }
}
