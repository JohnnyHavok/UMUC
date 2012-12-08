/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Jul 24, 2010
 * Project: Project 3
 * File: Library.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project3;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Set;

public class Library<T> implements LibraryInterface<T>, Serializable {
	/**
	 *  Added automatically by Eclipse
	 */
	private static final long serialVersionUID = 2051080105760576319L;
	// Formal Generic Type: T
	// Container Class: HashMap<T, String> where T is the generic object and 
	// String is the unique ID of the item.
	private HashMap<String, T> list = new HashMap<String, T>();
		
	public void add(T o, String uid) throws LibraryException {
		if(!checkID(uid)) {
			list.put(uid, o);
		} else {
			throw new LibraryException("Attempted to add duplicate items to library");
		}
	}
	
	public void delete(String uid) throws LibraryException {
		if(checkID(uid)) {
			list.remove(uid);
		} else {
			throw new LibraryException("Attempted to delete invalid item from library");
		}
	}

    public int size() {
        return list.size();
    }
    
    public Boolean checkID(String id) {
    	return list.containsKey(id);
    }
    
    public Object[] toArray() {
    	Object[] objA = new Object[list.size()];
    	Set<String> uidList = list.keySet();
    	int i = 0;
    	for(String uid : uidList) {
    		objA[i] = list.get(uid);
    		i++;
    	}
    	return objA;
    }
    
    public Object getAtUID(String uid) throws LibraryException {
    	if(checkID(uid)) {
    		return list.get(uid);
    	} else {
    		throw new LibraryException("Attemted to retrieve an item that is not in the library");
    	}
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
