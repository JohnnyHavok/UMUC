/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Oct 16, 2010
 * Project: Project 2
 * File: NodeType.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project2;

public interface NodeType {
  
  public double evaluate();
  
  public String preOrderWalk();
  public String inOrderWalk();
  public String postOrderWalk();
  
  public NodeType getLeft();
  public NodeType getRight();

}