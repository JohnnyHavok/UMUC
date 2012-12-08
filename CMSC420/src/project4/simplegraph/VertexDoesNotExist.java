/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Dec 5, 2010
 * Project: Project 4
 * File: VertexDoesNotExist.java
 * Development Environment:  Apple Mac OS X 10.6.5
 *                           Eclipse 3.6 / Java 1.6.0_22
 */

package project4.simplegraph;

/**
 * @author jsmith
 *
 */
@SuppressWarnings("serial")
public class VertexDoesNotExist extends Exception {
  VertexDoesNotExist() {super();}
  VertexDoesNotExist(String mesg) {super(mesg);}
}
