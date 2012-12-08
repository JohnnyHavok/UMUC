package cmsc350.project2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Name: Justin Smith
 * CMSC 350
 * Project: 2
 * Date: 11/18/12 7:50 AM
 * Requires: J2SE 7+
 */


class LinkedDescendingIterator<T extends Comparable<T>> implements Iterator<T> {
    private DoubleNode<T> current;

    public LinkedDescendingIterator(DoubleNode<T> last) {
        current = last;
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
        current = current.getPrevious();
        return d;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Thrown from DoubleLinkedIterator remove()");
    }
}
