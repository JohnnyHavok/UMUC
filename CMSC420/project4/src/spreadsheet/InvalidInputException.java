/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Dec 4, 2010
 * Project: Project 4
 * File: InvalidInputException.java
 * Development Environment:  Apple Mac OS X 10.6.5
 *                           Eclipse 3.6 / Java 1.6.0_22
 */

package spreadsheet;


@SuppressWarnings("serial")
public class InvalidInputException extends Exception {
  protected InvalidInputException() { super(); }
  protected InvalidInputException(String mesg) { super(mesg); }
}
