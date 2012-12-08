/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Aug 7, 2010
 * Project: Project 4a
 * File: Library.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project4a;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Library<T> implements Serializable, LibraryInterface<T> {
	/**
	 *  Added automatically by Eclipse
	 */
	private static final long serialVersionUID = 2051080105760576319L;
	// Formal Generic Type: T
	// Container Class: HashMap<T, String> where T is the generic object and 
	// String is the unique ID of the item.
	private HashMap<String, T> list = new HashMap<String, T>();
	// Maintain database of borrowers - migrated feature from Book class
	private HashMap<String, ArrayList<Borrower>> borrowers = new HashMap<String, ArrayList<Borrower>>();
	// Maintain running count of items - migrated feature from Book class
	private HashMap<String, Integer> inventory = new HashMap<String, Integer>();	

	public void add(T o, String uid, int count) throws LibraryException {
		if(!checkID(uid)) {
			list.put(uid, o);
			borrowers.put(uid, new ArrayList<Borrower>());
			inventory.put(uid, count);
		} else {
			throw new LibraryException("Attempted to add duplicate items to library");
		}
	}
	
	public void delete(String uid) throws LibraryException {
		if(checkID(uid)) {
			list.remove(uid);
			borrowers.remove(uid);
			inventory.remove(uid);
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
    
    public Object getAtUID(String uid) throws LibraryException {
    	if(checkID(uid)) {
    		return list.get(uid);
    	} else {
    		throw new LibraryException("Attempted to retrieve an item that is not in the library");
    	}
    }
    
    public boolean hasBorrowers(String uid) throws LibraryException {
    	if(checkID(uid)) {
	    	if(borrowers.get(uid).size() > 0)
	    		return true;
	    	
	    	return false;
    	} else {
    		throw new LibraryException("Attempted to retrieve an item that is not in the library");
    	}
    }
    
    public ArrayList<String> getBorrowers(String uid) {
    	ArrayList<String> arrl = new ArrayList<String>();
    	for(Borrower b : borrowers.get(uid)) {
    		arrl.add(b.getClientName());
    	}
    	return arrl;
    }
    
    public int getInventoryCount(String uid) throws LibraryException {
    	if(checkID(uid)) {
    		return inventory.get(uid);
    	} else {
    		throw new LibraryException("Attempted to retrieve an item that is not in the library");
    	}
    }

	public void checkOut(String uid, String name, int days) throws LibraryException {
		if(checkID(uid)) {
			if(inventory.get(uid) > 0) {
				borrowers.get(uid).add(new Borrower(name, days));
				inventory.put(uid, inventory.get(uid) - 1);
			} else {
				throw new LibraryException("Attempted to check out item when no copies are available");
			}
		} else {
			throw new LibraryException("Attempted to check out item that does not exist in the library");
		}
	}
	
	public void checkIn(String uid, int listIndex) throws LibraryException {
		if(checkID(uid)) {
			if(listIndex < borrowers.get(uid).size()) {
				borrowers.get(uid).remove(listIndex);
				inventory.put(uid, inventory.get(uid) + 1);
			} else {
				throw new LibraryException("Attempted to check in item from client not in list");
			}
		} else {
			throw new LibraryException("Attempted to check in item that is not in the library");
		}
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
	
	public void debugBorrowersList(String uid) {
		System.out.println("Borrowers List ofr UID: "+uid);
		for(Borrower b : borrowers.get(uid)) {
			System.out.println(b.toString());
		}
	}
}
