// UMUC CMSC230 - Project 3
// Written by Justin D. Smith
// 13 August 2009
// Project3.java
package project3;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Project3 {
	public static void main(String[] args) {
		/*/
		 * Defines what is valid in our expression, breaks it into groups
		 * Group 1 will hold operators, Group 2 will hold operands,
		 * Group 3 hold will hold P for polynomials.
		/*/
		final String __EXPREGEX__ = "([\\+\\-\\*\\/])|(\\d*\\.?\\d+)|([pP])";
		final int __MAXEXP__ = 100;

		Scanner input = new Scanner(System.in);
		Pattern p = Pattern.compile(__EXPREGEX__);
		Matcher searchInput;

		char[] 	operators;
		double 	operands;
		double 	finalResults;
		int 	count;
		int		polyCount;
		boolean isError 		= false;

		EvalPolynomial poly;
		PrefixNode[] array;

		displayWelcome();
		do{
			System.out.print("Enter a prefix expression: ");
			searchInput = p.matcher(input.nextLine());
			array 		= new PrefixNode[__MAXEXP__]; // Memory is cheap right?
			count 		= 0;
			polyCount 	= 0;

			// Parse user input into an array for easier processing into a tree all at once below.
			while(searchInput.find()) {
				// Found an operator
				if(searchInput.group(1) != null) {
					operators = searchInput.group(1).toCharArray();
					array[count] = new PrefixNode(operators[0]);
					count++;
				}
				// Found an operand
				else if(searchInput.group(2) != null) {
					operands = Double.parseDouble(searchInput.group(2));
					array[count] = new PrefixNode(operands);
					count++;
				}
				// Found a P
				else if(searchInput.group(3) != null) {
					polyCount++;
					System.out.print("Enter polynomial number " + polyCount + " to evaluate: ");
					poly = new EvalPolynomial(input.nextLine());
					if(searchInput.find()) {
						if(searchInput.group(2) != null) {
							double results = poly.evaluate(Double.parseDouble(searchInput.group(2)));
							array[count] = new PrefixNode(results);
							count++;
						} else {
							isError = true;
							System.out.println("There was an error in your syntax");
							System.out.println("A value must follow the polynomial operation P");
							break;
						}
					} else {
						isError = true;
						System.out.println("There was an error in your syntax");
						System.out.println("No value given for the polynomial");
						break;
					}
				}
			}
			if(!isError) {
				try { // tree.calculate() calls function PrefixTree.isBalanced() which will throw exception.
					finalResults = new PrefixTree(array).calculate();
					System.out.println("The value of the expression is: " + finalResults);
				} catch(TreeUnbalancedException ex) { System.out.println(ex); }
				  catch(ArithmeticException ex) {
					  // Catch a divide by zero but my Java compilier isn't complaining just sets result to infinity.
					  System.out.println("Math error! attempt to divide by zero, solution may not exist.");
				  }
			}
			System.out.print("\nWould you like to process another expression? [Y/N] > ");
		} while(input.nextLine().matches("[Yy]"));
	}

	private static void displayWelcome() {
		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("| This program will compute the value of a given prefix based         |");
		System.out.println("| expression. The only valid characters other than those of a number  |");
		System.out.println("| are.  No more than 100 items can be processed at a time.            |");
		System.out.println("|             * : For Multiplication                                  |");
		System.out.println("|             + : For Addition                                        |");
		System.out.println("|             - : For Subtraction                                     |");
		System.out.println("|             / : For Divsion                                         |");
		System.out.println("|             P : For Polynomial (A value for X must follow a P)      |");
		System.out.println("|                                                                     |");
		System.out.println("| All other characters will be ignored.  Spaces are allowed.          |");
		System.out.println("+---------------------------------------------------------------------+");
	}
}
