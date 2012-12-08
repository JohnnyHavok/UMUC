/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Jun 25, 2010
 * Project: Project 1
 * File: BookInterface.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project1;

public interface BookInterface {
	// Getters
	public String 	getAuthor();
	public String 	getTitle();
	public String 	getCopies();
	public int		getIntCopies();
	
	// Setters
	public void 	setAuthor(String s);
	public void 	setTitle(String s);
	
	// Book checkout methods
	public boolean 	borrowBook();
	public boolean 	returnBook(); 
}
