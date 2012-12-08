/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Oct 17, 2010
 * Project: Project 2
 * File: RegisterNode.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */

package project2;

public class RegisterNode implements NodeType {
  private int num;
  private double value;

  public RegisterNode(int number) { 
    this.num = number; 
    this.value = 0;
  }

  public RegisterNode(int number, double value) {
    this.num = number;
    this.value = value;
  }
  
  public String toString() { return String.valueOf(num); }
  
  public void setValue(double value) { this.value = value; }
  public double getValue() { return value; }
  public int getNumber() {return num;}
  
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