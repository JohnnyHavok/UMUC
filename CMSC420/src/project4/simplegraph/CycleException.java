/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Dec 4, 2010
 * Project: Project 4
 * File: CycleException.java
 * Development Environment:  Apple Mac OS X 10.6.5
 *                           Eclipse 3.6 / Java 1.6.0_22
 */
package project4.simplegraph;

@SuppressWarnings("serial")
public class CycleException extends Exception {
  protected CycleException() { super(); }
  protected CycleException(String mesg) { super(mesg); }
}
