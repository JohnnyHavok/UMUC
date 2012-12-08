/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Oct 16, 2010
 * Project: Project 2
 * File: OperandNode.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project2;

public class OperandNode implements NodeType {
  private double value;

  public OperandNode(double value) { this.value = value; }
  
  public String toString() { return String.valueOf(value); }
  
  public double getValue() { return value; }
  public void setValue(double value) { this.value = value; }
  
  @Override
  public NodeType getLeft() { return null; }
  
  @Override
  public NodeType getRight() { return null; }

  @Override
  public double evaluate() { return getValue(); }

  @Override
  public String preOrderWalk() { return toString(); }

  @Override
  public String inOrderWalk() { return toString(); }

  @Override
  public String postOrderWalk() { return toString(); }
}
