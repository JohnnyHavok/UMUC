/**
 * Name: Justin Smith
 * CMSC 451
 * Project:
 * Date: 2/10/13 12:52 PM
 * Requires: J2SE 7+
 */


public class HeapSort implements SortInterface {

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

    public HeapSort() {}

    @Override
    public void iterativeSort(int[] list) throws UnsortedException {
        for(int i = list.length; i > 1; i--)
            iterativeSort(list, i - 1);
    }

    private static void iterativeSort(int[] a, int n) {
        int i, o;
        int lChild, rChild, mChild, root, temp;
        root = (n - 1)/2;

        for(o = root; o >= 0; o--) {
            for(i = root; i >= 0; i--) {
                lChild = 2 * i + 1;
                rChild = 2 * i + 2;

                if((lChild <= n) && (rChild <= n)) {
                    if(a[rChild] >= a[lChild])
                        mChild = rChild;
                    else mChild = lChild;
                } else {
                    if(rChild > n)
                        mChild = lChild;
                    else
                        mChild = rChild;
                }

                if(a[i] < a[mChild]) {
                    temp = a[i];
                    a[i] = a[mChild];
                    a[mChild] = temp;
                }
            }
        }
        temp = a[0];
        a[0] = a[n];
        a[n] = temp;
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
}
