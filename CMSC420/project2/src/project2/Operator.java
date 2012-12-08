/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Sep 25, 2010
 * Project: Project 1
 * File: Operations.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project2;

import java.util.HashMap;
import java.util.Map;

public enum Operator implements OperationsInterface {
  ADD("+") {
    public int precedence() {return 1;}
    public double compute(double x, double y) {return x + y;}
    public String shortName() {return "ADD";}
  },
  SUBTRACT("-") {
    public int precedence() {return 1;}
    public double compute(double x, double y) {return y - x;}
    public String shortName() {return "SUB";}
  },
  MULTIPLY("*") {
    public int precedence() {return 2;}
    public double compute(double x, double y) {return x * y;}
    public String shortName() {return "MUL";}
  },
  DIVIDE("/") {
    public int precedence() {return 2;}
    public double compute(double x, double y) {return y / x;}
    public String shortName() {return "DIV";}
  },
  POWER("^") {
    public int precedence() {return 3;}
    public double compute(double x, double y) {return Math.pow(y, x);}
    public String shortName() {return "POW";}
  };

  /**
   * All operators defined in this Enum must implement a method to
   * correctly calculate one number if given two.
   */
  abstract public double compute(double x, double y);

  private final String symbol;

  Operator(String symbol) { this.symbol = symbol; }

  public static String getSymbol(Operator op) { return op.symbol; }
  
  public static String getShortName(OperationsInterface operator) { 
    return operator.shortName(); 
  }
  
  public String toString() { return symbol; }

  /**
   * The following code is adapted from Effective Java 2nd Edition 
   * (Bloch, 2008, p. 154).  It creates a HashMap where the symbol 
   * (e.g. +, -, etc.) in string format will be the key that returns a concrete 
   * Operator object.  The HashMap is generated at compile time.  I slightly 
   * changed Bloch's example to return an object.  Bloch's required the string 
   * name to access the object, I use the character (symbol).
   */
  private static final Map<String, Operator> symbolToEnum = 
            new HashMap<String, Operator>();
    
  static {
    for(Operator op: values())
      symbolToEnum.put(op.toString(), op);
  }

  public static Operator fromString(String symbol) { 
    return symbolToEnum.get(symbol); 
  }
}
