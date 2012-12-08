/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Aug 7, 2010
 * Project: Project 4a
 * File: BorrowerInterface.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project4a;

import java.util.Calendar;

public interface BorrowerInterface {
	// Getters
	public String 		getClientName();
	public Calendar		getBorrowDate();
	public Calendar		getDueDate();
	public long			getLongDueDate();
	public long			getLongBorrowDate();
	public long			getID();
	public String		toString();
}
