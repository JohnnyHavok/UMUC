/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Aug 7, 2010
 * Project: Project 4a
 * File: SpecificGenreBookInterface.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project4a;

/**
 * @author jsmith
 *
 */
public interface SpecificGenreBookInterface {

	public String getGenre();

	public String getAttribute();
	
	public String toString();
	
	public void setAttribute(String attribute);
	
}