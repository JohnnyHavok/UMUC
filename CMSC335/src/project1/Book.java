/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Jun 25, 2010
 * Project: Project 1
 * File: Book.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project1;

public class Book implements BookInterface {
	// Simple Book object
	private String 	author,
					title;
	
	private int		copies;
	
	// Constructor
	public Book(String a, String t, int c) {
		this.author = a;
		this.title 	= t;
		this.copies	= c;
		
	}
	
	public String getAuthor() {
		return this.author;
	}

	
	public String getCopies() {
		return String.valueOf(this.copies);
	}
	
	public int getIntCopies() {
		return this.copies;
	}

	public String getTitle() {
		return this.title;
	}

	// Sets book author to string s
	public void setAuthor(String s) {
		this.author = s;
	}

	// Sets book title to string s
	public void setTitle(String s) {
		this.title = s;
	}
	
	// If we have one or more copies, reduce the number of copies by 1
	// boolean value is flag for calling methods to know if sucessful
	public boolean borrowBook() {
		if(this.copies > 0) {
			this.copies--;
			return true;
		} else 
			return false;
	}

	// Increment copies by 1 for this particular book
	public boolean returnBook() {
		this.copies++;
		return true;
	}
}
