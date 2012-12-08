// UMUC CMSC230 - Project 3
// Written by Justin D. Smith
// 13 August 2009
// PrefixTree.java
package project3;

public class PrefixTree {
	protected PrefixNode 	root;
	protected PrefixNode[]  array;
	
	protected int			index 			= 0;
	protected int			operatorCount 	= 0;
	protected int			operandCount 	= 0;
	
	public PrefixTree(PrefixNode[] a) {
		array = a; // Make an instance wide variable reference.
		root  = buildTree();
	}
	
	private PrefixNode buildTree() throws TreeUnbalancedException {
		// Based on the example Module 5 I-E tree will build itself from the bottom up.
		// So all elements must be known before you can begin.
		index = operatorCount + operandCount;
		PrefixNode node;
		try { 
			if(array[index].getOperator() > 0) {
				node = array[index]; // Reference the current PrefixNode in the array.
				operatorCount++;
				node.setLeft(buildTree());
				node.setRight(buildTree());
			} else {
				node = array[index]; // Reference the current PrefixNode in the array.
				operandCount++;
			}
		} catch(NullPointerException ex) {
			throw new TreeUnbalancedException("There was an error in your syntax!\n" +
				"Check your prefix expression and try again\n");
			}
		
		return node;
	}
	
	public double calculate() throws TreeUnbalancedException {
		if(!this.isBalanced())
			throw new TreeUnbalancedException("There was an error in your syntax!\n" +
					"The number of operands in the expression\n" +
					"must be equal to the number of operators plus one\n");
		return recCalculate(root);
	}
	
	private double recCalculate(PrefixNode root) {
		double local = 0;
		// Since our tree has the property that only operators can have children the root
		// of a subtree will evaluate two of its children recursively.
		switch(root.getOperator()) {
			case 0 : local = root.getOperand();
				break;
			case 1 : local = recCalculate(root.getLeft()) + recCalculate(root.getRight());
				break;
			case 2 : local = recCalculate(root.getLeft()) - recCalculate(root.getRight());
				break;
			case 3 : local = recCalculate(root.getLeft()) * recCalculate(root.getRight());
				break;
			case 4 : local = recCalculate(root.getLeft()) / recCalculate(root.getRight());
				break;
		}
		return local;
	}
	
	public boolean isBalanced() {
		return (operandCount - operatorCount == 1);
	}
	
	private void debugTreeInOrder(PrefixNode tree) {
		if(tree != null) {
			System.out.println("LEFT");
			debugTreeInOrder(tree.getLeft());
			System.out.println("["+tree.getOperator()+"] ["+tree.getOperand()+"]");
			System.out.println("RIGHT");
			debugTreeInOrder(tree.getRight());
		}
	}
}