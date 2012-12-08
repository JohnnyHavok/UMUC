/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Jun 25, 2010
 * Project: Project 2
 * File: Library.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project2;

import java.util.ArrayList;

public class Library<T> implements LibraryInterface<T> {
	// Formal Generic Type: T
	// Container Class: ArrayList
	private ArrayList<T> list = new ArrayList<T>();
	
	// We will keep a private list of unique identifiers and only the
	// add function and delete functions can get rid of it.
	private ArrayList<String> idList = new ArrayList<String>();
		
	public void add(T o, String uid) throws LibraryException {
		if(!checkID(uid)) {
			this.list.add(o);
			addID(uid);
		} else {
			throw new LibraryException("Attempted to add duplicate items to library");
		}
	}
	
	public void delete(T o, String uid) throws LibraryException {
		if(!checkID(uid)) {
			this.list.remove(o);
			delID(uid);
		} else {
			throw new LibraryException("Attempted to delete invalid item from library");
		}
	}
	
	public void deleteAtIndex(int i) throws LibraryException {
		this.list.remove(i);
	}

    public int size() {
        return list.size();
    }
    
    public Boolean checkID(String id) {
    	for(String cur : idList) {
    		if(cur.compareTo(id) == 0)
    			return true;
    	}
    	return false;
    }
    
    private void addID(String id) {
    	this.idList.add(id);
    }
    
    private void delID(String id) {
    	this.idList.remove(id);
    }
    
    public Object[] toArray() {
    	Object[] objA = new Object[list.size()];
    	for(int i = 0; i < list.size(); i++) {
    		objA[i] = list.get(i);
    	}
    	return objA;
    }
	
	public Object getAtIndex(int i) throws LibraryException {
		if(i > list.size() || i == -1) {
			throw new LibraryException("Attempted to retrieve invalid item from library");
		} else
			return this.list.get(i);
	}

	public void checkOut(String uid) throws LibraryException {
		if(!checkID(uid))
			throw new LibraryException("Attempted to check out book that does not exist in the library");
	}
	
	public void checkIn(String uid) throws LibraryException {
		if(!checkID(uid))
			throw new LibraryException("Attempted to check in book that is not in the library");
	}
}
