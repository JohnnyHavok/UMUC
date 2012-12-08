/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Jul 10, 2010
 * Project: Project 2
 * File: LibraryException.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project2;

/**
 * @author jsmith
 * Library exception class
 */
@SuppressWarnings("serial")
public class LibraryException extends Exception {
	// Default exception
	public LibraryException() {}
	
	public LibraryException(String e) {
		super(e);
	}
}
