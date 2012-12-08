/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Jul 10, 2010
 * Project: Project 2
 * File: Borrower.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project2;

import java.util.Calendar;

public class Borrower implements BorrowerInterface, Comparable<Borrower> {
	private String 		clientName;
	
	private Calendar 	borrowDate, // Borrow date will also be the ID
						dueDate;
	
	public Borrower(String c, int days) {
		this.clientName = c;
		this.borrowDate = Calendar.getInstance();
		this.dueDate = (Calendar) this.borrowDate.clone();
		this.dueDate.add(Calendar.DAY_OF_YEAR, days);  // People can check out books for i days.
	}
	
	public String getClientName() {
		return this.clientName;
	}

	public Calendar getBorrowDate() {
		return this.borrowDate;
	}

	public Calendar getDueDate() {
		return this.dueDate;
	}
	
	public long getID() {
		return this.dueDate.getTimeInMillis();
	}
	
	public long getLongDueDate() {
		return this.dueDate.getTimeInMillis();
	}
	
	public long getLongBorrowDate() {
		return this.borrowDate.getTimeInMillis();
	}
	
	public int compareTo(Borrower b) {
		return this.dueDate.compareTo(b.getDueDate());
	}
	
	public String toString() {
		String s = this.clientName+":"
					+String.valueOf(getLongBorrowDate())+":"
					+String.valueOf(getLongDueDate());
		return s;
	}
}
