package cmsc350.project2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Name: Justin Smith
 * CMSC 350
 * Project: 2
 * Date: 11/17/12 5:35 PM
 * Requires: J2SE 7+
 */


class LinkedAscendingIterator<T extends Comparable<T>> implements Iterator<T> {
    private DoubleNode<T> current;

    public LinkedAscendingIterator(DoubleNode<T> start) {
        current = start;
    }

    @Override
    public boolean hasNext() {
        return (current != null);
    }

    @Override
    public T next() {
        if(!hasNext())
            throw new NoSuchElementException("Thrown from DoubleLinkedIterator next()");
        T d = current.getData();
        current = current.getNext();
        return d;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Thrown from DoubleLinkedIterator remove()");
    }
}