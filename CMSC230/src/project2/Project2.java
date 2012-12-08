// UMUC CMSC230 - Project 2
// Written by Justin D. Smith
// 17 July 2009
// Project2.java
package project2;

import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Project2 {
	// Defines what a complex number looks like and what operators are allowed.
	private final static String __CREGEX__ = "[\\+\\-\\*]|\\([\\s]?[-]?\\d+[\\.]?\\d*\\,[\\s]?[-]?\\d+[\\.]?\\d*\\)";
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		Stack<Complex> stk;
		boolean moreToProcess,
				error = false;
		
		// The pattern we will be looking for that matches complex numbers and operators.
		Pattern p = Pattern.compile(__CREGEX__);
		Matcher searchInput;
		String token;
		
		displayWelcome(); // Greet the user.
		do {
			System.out.print("Enter your postfix expression > ");
			searchInput = p.matcher(in.nextLine());
			
			stk = new Stack<Complex>();
			
			while(searchInput.find()) {
				token = searchInput.group(0);
				if(token.length() > 1)
					stk.push(new Complex.Builder().fromString(token).build());
				else {
					if(stk.size() >= 2)
						stk.push(eval2(stk.pop(), stk.pop(), token.charAt(0)));
					else {
						System.out.println("Illegal operation detected!");
						error = true;
						continue;
					}
				}
			}
			
			// User Interaction
			if(stk.size() != 0 && !error)
				if(stk.size() == 1)
					System.out.println("The results of the expression is: " + stk.pop().toString());
				else {
					System.out.println("The expression did not fully evaluate!");
					System.out.println("The last result of an operation was: " + stk.pop().toString());
					System.out.println("This is not the final answer, please check your syntax and try again");
				}
			else
				System.out.println("There was no expression evaluated, check your syntax and try again");
			
			System.out.print("\nWould you like to process another expression? [Y/N] > ");
			moreToProcess = in.nextLine().matches("[Yy]");
		} while(moreToProcess);
	}

	private static Complex eval2(Complex h, Complex j, char op) {
		switch(op) {
			case '+': // Add
				return h.add(j);
			case '-': // Subtract
				return h.subtract(j);
			case '*': // Multiply
				return h.multiply(j);
		}
		return null; // Unreachable but required.
	}
	
	private static void displayWelcome() {
		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("| This program will compute postfix expressions of complex numbers in |");
		System.out.println("| the form of a+bi.  The input format for each complex number must be |");
		System.out.println("| (a,b). The only valid characters other than those of a complex      |");
		System.out.println("| number are: * : For Multiplication                                  |");
		System.out.println("|             + : For Addition                                        |");
		System.out.println("|             - : For Subtraction                                     |");
		System.out.println("|                                                                     |");
		System.out.println("| All other characters will be ignored.  Spaces are allowed.          |");
		System.out.println("+---------------------------------------------------------------------+");
	}
}