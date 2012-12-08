package cmsc350.project2;

/**
 * Name: Justin Smith
 * CMSC 350
 * Project: 2
 * Date: 11/17/12 5:42 PM
 * Requires: J2SE 7+
 */

class DoubleNode<T extends Comparable<T>> {
    private DoubleNode<T> next, prev;
    private T data;

    // Constructors
    public DoubleNode() {
        next = null;
        prev = null;
        data = null;
    }

    public DoubleNode(T d) {
        next = null;
        prev = null;
        data = d;
    }

    public DoubleNode(DoubleNode<T> n, DoubleNode<T> p, T d) {
        next = n;
        prev = p;
        data = d;
    }

    // Getters
    public T getData() { return data; }
    public DoubleNode<T> getNext() { return next; }
    public DoubleNode<T> getPrevious() { return prev; }

    // Setters
    public void setData(T d) { data = d; }
    public void setNext(DoubleNode<T> n) { next = n; }
    public void setPrevious(DoubleNode<T> p) { prev = p; }
}
