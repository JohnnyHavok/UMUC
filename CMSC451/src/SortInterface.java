/**
 * Name: Justin Smith
 * CMSC 451
 * Project: 1
 * Date: 2/10/13 12:42 PM
 * Requires: J2SE 7+
 */


public interface SortInterface {
    void recursiveSort(int[] list) throws UnsortedException;
    void iterativeSort(int[] list) throws UnsortedException;
    int getCount();
    long getTime();
}
