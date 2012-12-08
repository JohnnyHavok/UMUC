/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Jul 24, 2010
 * Project: Project 3
 * File: LibraryInterface.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project3;

public interface LibraryInterface<T> {
	// Formal Generic Type: T
	
	// Methods operating on Library items
	public void		add(T o, String s) throws LibraryException;
	public void 	delete(String s) throws LibraryException;
	
	// Methods checking in/out Library items
	public void checkOut(String uid) throws LibraryException;
	public void checkIn(String uid) throws LibraryException;
	
	// Methods of retreiving items.
	public Object   getAtUID(String uid) throws LibraryException; 
	public Object[] toArray();
	public int 		size();
	public Boolean  checkID(String s);
}