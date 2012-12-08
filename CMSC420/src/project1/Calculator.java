/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Sep 26, 2010
 * Project: Project 1
 * File: Calculator.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project1;

import java.util.Stack;

public class Calculator {
  // Data structures used.
  private Stack<Double> operands;
  private Stack<OperationsInterface> operators;

  Calculator() {
    operands = new Stack<Double>();
    operators = new Stack<OperationsInterface>();
  }

  public void addOperand(Double d) { operands.push(d); }

  /** addGrouping()
   * If the grouping symbol is a "(" push it on the stack.  If it is a 
   * ")" being calculating operand pairs with available operators until
   * a "(" is found or the stack is exhausted. 
   */
  public void addGrouping(Parenthesis op) { 
    if(op == Parenthesis.OPEN) {
      operators.push(op);
    } else {
      if(!operators.isEmpty())
        while(operators.peek() != Parenthesis.OPEN)
          if(!operators.isEmpty() && operands.size() >= 2)
            operands.push(((Operator) operators.pop()).compute(operands.pop(), operands.pop()));

      operators.pop(); // Remove open parenthesis left on the stack.
    }
  }

  /** addOperator()
   * Runs Operator through calculate() as soon as it gets it so the stack is
   * always up to date.
   */
  public void addOperator(Operator currentOp) { calculate(currentOp); }

  /** getTotal()
   * In the ideal case it returns the top operand on the stack.  If an operand
   * other than a grouping symbol "(" is still on it's stack and there are two
   * or more operands left in their stack then it will use the last operand.
   * Of course this process like calculate() believes the users infix equation
   * is correct (bad programming but within the project specifications).
   */
  public double getTotal() {
    if(!operators.isEmpty()) // This is the case of a simple equation like 2+2
      if(operands.size() >= 2 && operators.peek() != Parenthesis.OPEN)
        return ((Operator) operators.pop()).compute(operands.pop(), operands.pop());

    if(!operands.isEmpty()) // The case where operators is empty and operands available
      return operands.pop();

    return 0; // If operands is empty
  }

  /** calculate()
   * Does the work of keeping the stacks up to date based on the infix 
   * algorithm in Module 1.  It is recursive.  The two base cases are 1: If the 
   * current interation's operator has a greater precedence than the operator 
   * on the top of the stack.  2: If the stack is exhausted in the process.
   */
  private void calculate(Operator currentOp) {
    if(!operators.isEmpty()) {
      if(currentOp.precedence() <= operators.peek().precedence()) {
        // Pop top two operands and do the math of the top operator
        // and push results back to the operands stack.
        operands.push(((Operator) operators.pop()).compute(operands.pop(), operands.pop()));
        calculate(currentOp);
      } else { // Base case #1
        operators.push(currentOp);
      }
    } else { // Base case #2
      operators.push(currentOp);
    }
  }

  /** Some Debug methods */
  public void debugPrintStacks() {
    System.out.println("Printing Stacks");
    if(operands.isEmpty()) {
      System.out.println("Operands empty");
    } else {
      for(Object num: operands.toArray()) {
        System.out.print(num+":");
      }
      System.out.println();
    }
    if(operators.isEmpty()) {
      System.out.println("Operators empty");
    } else {
      for(Object op: operators.toArray()) {
        System.out.print(op+":");
      }
      System.out.println();
    }
  }
}
