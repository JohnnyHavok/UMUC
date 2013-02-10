/**
 * Name: Justin Smith
 * CMSC 451
 * Project:
 * Date: 2/10/13 12:52 PM
 * Requires: J2SE 7+
 */


public class HeapSort implements SortInterface {
    public HeapSort() {}

    public static void main(String[] args) {
        HeapSort sorter = new HeapSort();
        int[] test = {1, 3, 4, 5, 2, 5, 7, 6, 0, 13};

        System.out.println("Running iterative heap sort");
        try {
            sorter.iterativeSort(test);
        } catch (UnsortedException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < test.length; i++)
            System.out.print(" " + test[i]);

        System.out.println();
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
    /* Build max-heap */
        for (int heapsize = 0; heapsize < array.length; heapsize++) {
        /* Step one in adding an element to the heap in the
         * place that element at the end of the heap array-
         * in this case, the element is already there. */
            int n = heapsize; // the index of the inserted int
            while (n > 0) { // until we reach the root of the heap
                int p = (n-1)/2; // the index of the parent of n

                if (array[n] > array[p]) { // child is larger than parent
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

            int n = 0; // index of the element being moved down the tree

            while (true) {
                int left = n * 2 + 1;
                if (left >= heapsize) // node has no left child
                    break; // reached the bottom; heap is heapified

                int right = left + 1;
                if (right >= heapsize) { // node has a left child, but no right child
                    if (array[left] > array[n]) // if left child is greater than node
                        arraySwap(array, left, n); // swap left child with node
                    break; // heap is heapified
                }

                if (array[left] > array[n]) { // (left > n)
                    if (array[left] > array[right]) { // (left > right) & (left > n)
                        arraySwap(array, left, n);
                        n = left; continue; // continue recursion on left child
                    } else { // (right > left > n)
                        arraySwap(array, right, n);
                        n = right; continue; // continue recursion on right child
                    }
                } else { // (n > left)
                    if (array[right] > array[n]) { // (right > n > left)
                        arraySwap(array, right, n);
                        n = right;
                        continue; // continue recursion on right child
                    } else { // (n > left) & (n > right)
                        break; // node is greater than both children, so it's heapified
                    }
                }
            }
        }

    /* Check that array is sorted */
        System.out.println("Checking to see the iterative HeapSort method " +
                "correctly sorted the array....");

        for(int k = 0; k < array.length - 1; k++)
            if(array[k] > array[k + 1])
                throw new UnsortedException("The array was not sorted " +
                    "correctly: \n" +
                    array[k] + " > " + array[k+1] + " at indices " + k +
                    " and " + k+1 + " respectively.");

        System.out.println("The array was correctly sorted by iterative the " +
            "HeapSort method.");
    }


    @Override
    public void recursiveSort(int[] list) throws UnsortedException {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public long getTime() {
        return 0;
    }

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
}
