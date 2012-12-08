// UMUC CMSC230 - Project 3
// Written by Justin D. Smith
// 13 August 2009
// PrefixNode.java
package project3;

public class PrefixNode implements Cloneable {
	private int 		operator;
	private double 		operand;
	private PrefixNode 	left;
	private PrefixNode 	right;
	
	public PrefixNode(char op) {
		switch(op) {
			case '+' : this.operator = 1;
				break;
			case '-' : this.operator = 2;
				break;
			case '*' : this.operator = 3;
				break;
			case '/' : this.operator = 4;
		}
		left  = null;
		right = null;
	}
	
	public PrefixNode(double n) {
		this.operator 	= 0;
		this.operand 	= n;
		this.left 		= null;
		this.right 		= null;
	}
	
	private PrefixNode() {}

	public PrefixNode clone() {
		PrefixNode clone = new PrefixNode();
		
		clone.operand  	= this.operand;
		clone.operator  = this.operator;
		clone.left		= this.left;
		clone.right		= this.right;
		
		return clone;
	}
	
	// Getters and Setters
	
	public void setLeft(PrefixNode target) { this.left = target; }
	
	public void setRight(PrefixNode target) { this.right = target; }
	
	public PrefixNode getLeft() { return left; }
	
	public PrefixNode getRight() { return right; }
	
	public int getOperator() { return operator; }
	
	public double getOperand() { return operand; }
}