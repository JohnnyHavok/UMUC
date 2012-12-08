/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Dec 4, 2010
 * Project: Project 4
 * File: DuplicateVertexException.java
 * Development Environment:  Apple Mac OS X 10.6.5
 *                           Eclipse 3.6 / Java 1.6.0_22
 */

package simplegraph;

@SuppressWarnings("serial")
public class DuplicateVertexException extends Exception {
  protected DuplicateVertexException() { super(); }
  
  protected DuplicateVertexException(String mesg) { super(mesg); }
}
