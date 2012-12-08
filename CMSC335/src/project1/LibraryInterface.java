/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Jun 25, 2010
 * Project: Project 1
 * File: LibraryInterface.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project1;

public interface LibraryInterface<T> {
	// Formal Generic Type: T
	
	// Methods operating on Library items
	public void add(T o);
	public void delete(T o);
	
	// Methods checking in/out Library items
	public void checkOut(T o);
	public void checkIn(T o);
	
	// Methods of retreiving items.
	public Object 	getAtIndex(int i);
	public Object[] toArray();
	public int 		size();
	public void 	deleteAtIndex (int i);
}