/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Jul 24, 2010
 * Project: Project 3
 * File: BookInterface.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project3;

import java.util.ArrayList;

public interface BookInterface {
	// Getters
	public String[]				getBookInfo(String isbn);
	public int					getNumberOfBooks();
	public int					getRemainingBooks();
	public String   			getTitle();
	public String   			getISBN();
	public String   			getAuthor();
	public ArrayList<String> 	getBorrowers();
	
	// Setters
	public void		setNumberOfBooks(int i);
	public void		setRemainingBooks(int i);
	
	// Book checkout methods
	public boolean 	borrowBook(String n, int days);
	public boolean 	returnBook(int i); 
	public boolean  hasBorrowers();
	
	// Misc methods
	public void 	debugBorrowersList();
	public String 	toString();
}
