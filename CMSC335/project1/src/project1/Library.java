/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Jun 25, 2010
 * Project: Project 1
 * File: Library.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project1;

import java.util.ArrayList;

public class Library<T> implements LibraryInterface<T> {
	// Formal Generic Type: T
	// Container Class: ArrayList
	private ArrayList<T> list = new ArrayList<T>();
	
	public void add(T o) {
		this.list.add(o);
	}
	
	public void delete(T o) {
		this.list.remove(o);
	}
	
	public void deleteAtIndex(int i) {
		this.list.remove(i);
	}

    public int size() {
        return list.size();
    }
    
    public Object[] toArray() {
    	Object[] objA = new Object[list.size()];
    	for(int i = 0; i < list.size(); i++) {
//    		System.out.println(list.get(i).toString());
    		objA[i] = list.get(i);
    	}
    	return objA;
    }
	
	public Object getAtIndex(int i) {
		return this.list.get(i);
	}

	public void checkOut(T o) {
		// TODO Auto-generated method stub
	}
	
	public void checkIn(T o) {
		// TODO Auto-generated method stub
	}
}
