/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Oct 16, 2010
 * Project: Project 2
 * File: OperatorNode.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project2;

public class OperatorNode implements NodeType {
  private NodeType left, right;
  private OperationsInterface operator;
  
  public OperatorNode(Operator operator,
                      NodeType left,
                      NodeType right) {
    this.operator = operator;
    this.left = left;
    this.right = right;
  }
  
  public OperatorNode(Operator operator) {
    this.operator = operator;
    left = null;
    right = null;
  }

  public OperatorNode(Parenthesis p) {
    operator = p;
    left = null;
    right = null;
  }
  
  public int getPrecedence() { return operator.precedence(); }
  
  public OperationsInterface getOperator() { return operator; }
  
  public void setLeft(NodeType node) { this.left = node; }
  public void setRight(NodeType node) { this.right = node; }
  
  @Override
  public NodeType getLeft() { return left; }
  
  @Override
  public NodeType getRight() { return right; }
  
  public String toString() { return operator.toString(); }
  
  public String getASMOp() { return operator.shortName(); }
  
  @Override
  public double evaluate() {
    double leftValue = left.evaluate();
    double rightValue = right.evaluate();
    return ((Operator) operator).compute(rightValue, leftValue);
  }
  
  @Override
  public String preOrderWalk() {
    String leftValue = left.preOrderWalk();
    String rightValue = right.preOrderWalk();
    return "" + operator + " " + leftValue + " " + rightValue;
  }
  
  @Override
  public String inOrderWalk() {
    String leftValue = left.inOrderWalk();
    String rightValue = right.inOrderWalk();
    return "(" + leftValue + " " + operator + " " + rightValue + ")";
  }
  
  @Override
  public String postOrderWalk() {
    String leftValue = left.postOrderWalk();
    String rightValue = right.postOrderWalk();
    return leftValue + " " + rightValue + " " + operator;
  }
}