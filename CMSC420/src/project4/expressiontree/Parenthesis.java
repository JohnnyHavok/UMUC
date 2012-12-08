/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Sep 26, 2010
 * Project: Project 1
 * File: Parenthesis.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */

package project4.expressiontree;

public enum Parenthesis implements OperationsInterface {
  /** 
   * I changed the precedence of "(" from 4 to -1 for my
   * implementation.  It makes more sense to me since "("
   * is a false bottom that can only be removed by a ")"
   * so all other operators "sit" on top of it and thus
   * have greater precedence.
   */
  OPEN("(") { public int precedence() {return -1;} },
  CLOSE(")") { public int precedence() {return 4;} };

  private final String symbol;

  Parenthesis(String symbol) { this.symbol = symbol; }

  public static String getSymbol(Parenthesis op) { return op.symbol; }
  
  public String toString() { return symbol; }
}
