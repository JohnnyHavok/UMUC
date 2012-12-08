/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Jul 10, 2010
 * Project: Project 2
 * File: Title.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project2;

/**
 * Creates immutable objects that store book information.
 * 
 */
public class Title implements TitleInterface {
	private String 	author,
					title,
					ISBN;
	
	// Constructor
	public Title(String a, String t, String i) {
		this.author = a;
		this.title	= t;
		this.ISBN	= i;
	}
	
	// Getters
	public String getTitle() {return this.title;}
	public String getAuthor() {return this.author;}
	public String getISBN() {return this.ISBN;}
}
