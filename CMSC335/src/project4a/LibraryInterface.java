/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Aug 8, 2010
 * Project: Project 4a
 * File: LibraryInterface.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project4a;

import java.util.ArrayList;

/**
 * @author jsmith
 *
 * @param <T>
 */
public interface LibraryInterface<T> {

	public void add(T o, String uid, int count) throws LibraryException;

	public void delete(String uid) throws LibraryException;

	public int size();

	public Boolean checkID(String id);

	public Object getAtUID(String uid) throws LibraryException;

	public boolean hasBorrowers(String uid) throws LibraryException;

	public ArrayList<String> getBorrowers(String uid);

	public int getInventoryCount(String uid) throws LibraryException;

	public void checkOut(String uid, String name, int days) throws LibraryException;

	public void checkIn(String uid, int listIndex) throws LibraryException;

	public Object[] toArray();

	public void debugBorrowersList(String uid);

}