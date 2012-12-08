/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Sep 26, 2010
 * Project: Project 1
 * File: ExpressionTree.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package expressiontree;

import java.util.Stack;

public class ExpressionTree {
  private Stack<Double> operands;
  private Stack<OperationsInterface> operators;

  public ExpressionTree() {
    operands = new Stack<Double>();
    operators = new Stack<OperationsInterface>();
  }
  
  public void clear() {
    operands.clear();
    operators.clear();
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
            operands.push(((Operator) operators.pop()).compute(operands.pop() , 
                                                               operands.pop()));

      operators.pop(); // Remove open parenthesis left on the stack.
    }
  }

  /** addOperator()
   * Runs Operator through calculate() as soon as it gets it so the stack is
   * always up to date.
   * @throws ExpressionException 
   */
  public void addOperator(Operator currentOp) throws ExpressionException { 
    calculate(currentOp); 
  }

  /** getTotal() : Updated from Project 1 @ 5 DEC 2010
   * Returns total on top of operands stack.  Throws exception that indicates
   * that the input formula was not correct.
   */
  public double getTotal()  throws ExpressionException {    
    if(operands.size() == 1 && operators.isEmpty())
      return operands.pop();
    
    if(operators.size() == 1) {
      if(operands.size() == 2) {
        return ((Operator) operators.pop()).compute( operands.pop() , 
                                                     operands.pop() );
      }
    }
    
    throw new ExpressionException("Error with formula");
  }

  /** calculate()
   * Does the work of keeping the stacks up to date based on the infix 
   * algorithm in Module 1.  It is recursive.  The two base cases are 1: If the 
   * current interation's operator has a greater precedence than the operator 
   * on the top of the stack.  2: If the stack is exhausted in the process.
   */
  private void calculate(Operator currentOp) throws ExpressionException {
    if(!operators.isEmpty()) {
      if(currentOp.precedence() <= operators.peek().precedence()) {
        // Pop top two operands and do the math of the top operator
        // and push results back to the operands stack.
        if(operands.size() >= 2) 
          operands.push(((Operator) operators.pop()).compute(operands.pop() , 
                                                             operands.pop() ) );
        else
          throw new ExpressionException("Error with forumula near " +
                                          operators.peek().toString());
        calculate(currentOp);
      } else { // Base case #1
        operators.push(currentOp);
      }
    } else { // Base case #2
      operators.push(currentOp);
    }
  }
  
  public int size() {
    return operands.size()+operators.size();
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
