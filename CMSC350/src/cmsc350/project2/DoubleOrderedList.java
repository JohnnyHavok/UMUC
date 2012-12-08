package cmsc350.project2;

import java.util.Collection;
import java.util.Iterator;

/**
 * Name: Justin Smith
 * CMSC 350
 * Project: 2
 * Date: 11/17/12 3:34 PM
 * Requires: J2SE 7+
 */


public class DoubleOrderedList<T extends Comparable<T>> implements Iterable<T> {
    private DoubleNode<T> first, last;
    private int size;

    
    /**
     * Default constructor. Creates empty list.
     */
    public DoubleOrderedList() {
        first = last = null;
        size = 0;
    }
    
    
    /**
     * Constructor creates sorted list from collection passed as a parameter.
     * @param c : Collection of <T extends Comparable<T>> elements.
     */
    public DoubleOrderedList(Collection<T> c) {
    	first = last = null;
    	size = 0;
    	addAll(c);
    }

    /**
     * Constructor creates sorted list from array passed as parameter
     * @param a : Array of <T extends Comparable<T>> elements.
     */
    public DoubleOrderedList(T[] a) {
        first = last = null;
        size = 0;
        addAll(a);
    }

    /**
     * Adds single element of type <T extends Comparable<T>> to the sorted list.  
     * Duplicates, elements that return 0 on their comparison test, will be
     * added in the first available spot.  Efficiency is O(n) because we must
     * step through (n) nodes to find the right place in the sorted list.
     * @param o : Object of type <T extends Comparable<T>>.
     */
    public void add(T o) {
        if(isEmpty()) { // Case 1: List is empty.
            first = new DoubleNode<>(o);
            last = first;
        } else {
        	DoubleNode<T> current = first;
        	boolean placed = false;
        	while(!placed) { 
        		if(current.getData().compareTo(o) >= 0) {
        			if(current.getPrevious() == null) { // Case 2: New first element
        				current.setPrevious(new DoubleNode<>(current, null, o));
        				first = current.getPrevious();
        			} else { // Case 3: Middle of the list
        				current.getPrevious().setNext(new DoubleNode<>(current, current.getPrevious(), o));
        				current.setPrevious(current.getPrevious().getNext());
        			}
        			placed = true;
        		} else if(current == last) { // Case 4: New last element
        			current.setNext(new DoubleNode<>(null, current, o));
        			last = current.getNext();
        			placed = true;
        		}
        		current = current.getNext();
        	}
        }
        size++;
    } // end method add()
    
    /**
     * Adds a collection of elements of type <T extends Comparable<T>> to the sorted list.
     * Duplicate elements, that is, elements that return 0 on their comparison test, will be
     * added in the first available spot as they are encountered.
     * @param c : Collection of <T extends Comparable<T>> elements.
     */
    public void addAll(Collection<T> c) {
    	for(T o : c)
    		add(o);
    }

    /**
     * Adds an array of elements of type <T extends Comparable<T>> to the sorted list.
     * Duplicate elements, that is, elements that return 0 on their comparison test, will be
     * added in the first available spot as they are encountered.
     * @param a : Array of <T extends Comparable<T>> elements.
     */
    public void addAll(T[] a) {
        for(T o : a)
            add(o);
    }

    /**
     * Removes a single element of type <T extends Comparable<T>> from the list and returns
     * it if found or else returns null.
     * @param o : Element to find and remove from the list.
     * @return : The element found or null.
     */
    public T remove(T o) {
    	if(isEmpty()) return null;
    	
    	DoubleNode<T> current = find(o);
    	if(current == null) // Case 0 : Element not found
    		return null;
    	
    	remove(current); // Just removing it's references let the GC collect it after we return the data.
    	return current.getData();
    }

    /**
     * Removes all elements of type <T extends Comparable<T>> from the list and returns
     * a count of the elements removed this way.
     * @param o : Elements to find from remove from the list.
     * @return : The number of elements found and removed.
     */
    public int removeAll(T o) {
    	if(isEmpty()) return 0;
    	DoubleNode<T> current;
    	int c = 0;
    	
    	while((current = find(o)) != null) {
    		remove(current);
    		c++;
    	}
    	
    	return c;
    }
    
    /**
     * Private method that implements the algorithm for the four different cases in which we may find a node in.
     * @param current : Reference to the node we want to remove.
     */
    private void remove(DoubleNode<T> current) {
    	if(size == 1) { // Case 1 : Only node
    		first = last = null;
    	} else if(first == current) { // Case 2: Element to remove is first node
    		current.getNext().setPrevious(null);
    		first = current.getNext();
    	} else if(last == current) { // Case 3: Element to remove is last node
    		current.getPrevious().setNext(null);
    		last = current.getPrevious();
    	} else { // Case 4 : Last case, middle of the list.
    		current.getPrevious().setNext(current.getNext());
        	current.getNext().setPrevious(current.getPrevious());
    	}
    	
    	current.setPrevious(null); // Make sure the GC picks it up
    	current.setNext(null); 
    	
    	size--;
    }
    
    /**
     * Private method that searches the list to find a node and returns a reference to that node.
     * Efficiency is O(n) - Must search through the entire list of elements.
     * @param o : The element you wish you find.
     * @return : Reference to the found node or null if no node found.
     */
    private DoubleNode<T> find(T o) {
    	for(DoubleNode<T> i = first; i != null; i = i.getNext())
    		if(i.getData().compareTo(o) == 0)
    			return i;

    	return null;
    }

    /**
     * Search the list for element <T extends Comparable<T>> that matches the input.
     * @param o : The element you wish you find.
     * @return : True if found, false if not.
     */
    public boolean contains(T o) { return !isEmpty() && find(o) != null; }

    /**
     * Count the number of elements <T extends Comparable<T>> that matches the input.
     * Efficiency is O(n) because the second for loop just picks up where find() left off.
     * @param o : The element you wish to find and count.
     * @return : The number of times a matching element was counted.
     */
    public int containsAll(T o) {
    	if(isEmpty()) return 0;
    	int c = 0;
    	
    	for(DoubleNode<T> i = find(o); i != null; i = i.getNext())
    		if(i.getData().compareTo(o) == 0)
    			c++;
    		else
    			return c;

    	return c;
    }

    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }

	@Override
    public Iterator<T> iterator() {
        return new LinkedAscendingIterator<>(first);
    }

    public Iterator<T> descendingIterator() {
        return new LinkedDescendingIterator<>(last);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for(DoubleNode<T> i = first; i != null; i = i.getNext())
            output.append("[").append(i.getData().toString()).append("]");

        if(!isEmpty()) {
            assert first != null;
            output.append("\n").append("First: ").append(first.getData()).append(" Last: ").append(last.getData());
        }

        return output.toString();
    }
}
