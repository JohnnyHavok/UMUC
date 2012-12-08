/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Oct 16, 2010
 * Project: Project 2
 * File: ExpressionBuilder.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project2;

import java.util.Stack;

public class ExpressionTreeBuilder {
  private Stack<OperatorNode> operators;
  private Stack<NodeType>     operands;
  
  ExpressionTreeBuilder() {
    operators = new Stack<OperatorNode>();
    operands  = new Stack<NodeType>();
  }
  
  public void addOperator(OperatorNode node) {
    if(operators.isEmpty()) {
      operators.push(node);
    } else {
      if(node.getPrecedence() <= operators.peek().getPrecedence()) {
        OperatorNode c = operators.pop();
        c.setRight(operands.pop());
        c.setLeft(operands.pop());
        operands.push(c);
        addOperator(node);
      } else {
        operators.push(node);
      }
    }
  }
  
  public void addGrouping(OperatorNode node) {
    if(node.getOperator() == Parenthesis.OPEN) {
      operators.push(node);
    } else {
      if(!operators.isEmpty()) {
        while(operators.peek().getOperator() != Parenthesis.OPEN) {
          OperatorNode c = operators.pop();
          c.setRight(operands.pop());
          c.setLeft(operands.pop());
          operands.push(c);
        }
        operators.pop();
      }
    }
  }
  
  public void addOperand(OperandNode node) { operands.push(node); }
  
  public void addVariable(VariableNode node) { operands.push(node); }
  
  public NodeType getExpressionTree() {
    if(operators.isEmpty()) {
      if(operands.isEmpty())
        return new OperandNode(0);
      else 
        return operands.pop();
    } else {
      if(operands.size() >= 2) {
        OperatorNode c = operators.pop();
        c.setRight(operands.pop());
        c.setLeft(operands.pop());
        operands.push(c);
        return getExpressionTree();
      }
    }
    return new OperandNode(0); // bad expression is given.  
  }
  
  public void debugPrintStacks() {
    System.out.println("Printing Stacks");
    
    System.out.print("Operands: ");
    for(Object o: operands.toArray()) {
      System.out.print(o+" ");
    }
    System.out.println();
    
    System.out.print("Operators: ");
    for(Object o: operators.toArray()) {
      System.out.print(o+" ");
    }
    System.out.println();
  }
}
