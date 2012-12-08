/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Aug 8, 2010
 * Project: Project 4b
 * File: BookInterface.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project4b;


public interface BookInterface {

	/**
	 * Built-in getters common to all types of books
	 */
	public String getAuthor();

	public String getTitle();

	public String getISBN();

	public String toString();
	
	/**
	 * Built-in setters common to all types of books
	 */
	public void setAuthor(String author);
	
	public void setTitle(String title);
	
	public void setISBN(String isbn);
}