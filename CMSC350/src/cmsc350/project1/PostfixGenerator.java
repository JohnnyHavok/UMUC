/**
 * Author: Justin Smith
 * Course: CMSC 350
 * Project: 1
 * Date: Nov 3, 2012 7:49:51 PM
 */
package cmsc350.project1;

import java.lang.StringBuilder;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Static methods that return postfix expressions.
 * 
 * @author jsmith
 */
public class PostfixGenerator {
	// Regular expressions do all my heavy lifting. 
	private static final Pattern INFIX_REGEX;
	
	static { // Class load time
		INFIX_REGEX = Pattern.compile (
			"([\\+\\-\\*\\/\\^])|"             +  // group(1) matches only +, -, *, /, ^
			"(\\()|"                           +  // group(2) matches only (
			"(\\))|"                           +  // group(3) matches only )
			"([0-9]*\\.[0-9]+|[0-9]+)|"        +  // group(4) matches all double numbers
			"([A-Za-z])"                          // group(5) matches A-Z and a-z
		);
	}
	
	
	
	/**
	 * Returns a string formatted in postfix notation representing the input parameter 
	 * infix.
	 * 
	 * @param infix		The string to be evaluated in Infix notation
	 * @return			The input string in Postfix notation
	 * @throws			IllegalInfixException
	 */
	static String createFromString(String infix) throws IllegalInfixException {
		Stack<String> operands = new Stack<String>();
		Stack<AlgebraicSymbology> operators = new Stack<AlgebraicSymbology>();

		StringBuilder postfix = new StringBuilder();
		StringBuilder subExpr = new StringBuilder();

		// Keep track of counts for error detection.
		int operatorCount = 0;
		int operandCount = 0;
		int parenCount = 0;

		Matcher matcher = INFIX_REGEX.matcher(infix);

		while(matcher.find()) { // Cycle through input string with Matcher
			/* Main algorithm GIST:
			 * We always check parenCount first for each match to make sure we are not inside
			 * what will later be a subexpression; that is, an expression located between (...).  
			 * If we are in a subexpression then we just need to add that to a new string minus the outermost
			 * parenthesis and then recursively call createFromString() again and pass it that new string.
			 * This makes diving through parenthesis grouping easier and we always boil complex expressions 
			 * down to their simplest algebraic elements. 
			 * 
			 * Example:
			 * 		((a+b)/(c*d))^3 -> Original.
			 * 		(a+b)/(c*d) -> Passed to another call of createfromString().
			 * 		a+b -> Passed to another call of createFromString().
			 * 		a b + -> Returned as a string
			 */
			
			
			if(matcher.group(1) != null) {
				// Found operator
				if(parenCount == 0) {
					Operator currentOp = Operator.fromSymbol(matcher.group(1));
					
					if(operators.size() == 0 && operands.size() > 0) {
						operators.push(currentOp);
						operatorCount++;
					} else if(operandCount >= 2) {
						try {
							while(operators.peek().getPrecedence() <= currentOp.getPrecedence()) {
								if(operands.size() > 1) {
									String b = operands.pop();
									postfix.append(operands.pop() + " " + b + " " + operators.pop() + " ");
								} else {
									postfix.append(operands.pop() + " " + operators.pop() + " ");
								}
							}
							operators.push(currentOp);
							operatorCount++;
						} catch(EmptyStackException e) {
							operators.push(currentOp);
							operatorCount++;
						}
							
					} else {
						throw new IllegalInfixException("Invalid Infix Exception near: \""+currentOp.toString()+"\"");
					}
				} else {
					subExpr.append(matcher.group(1));
				}

			} else if(matcher.group(2) != null) {
				// Found (
				if(parenCount == 0) { // First ( encountered
					parenCount++;
				} else { // Second ( encountered, save it for the subexpression
					parenCount++;
					subExpr.append(matcher.group(2));
				}

			} else if(matcher.group(3) != null) {
				// Found )
				if(parenCount == 1) { // Outermost parenthesis found, run innermost string
					parenCount--;
					operands.push(createFromString(subExpr.toString()));
					operandCount++;
					subExpr.delete(0, subExpr.length()); // Clear subExpr contents after recursion call.
				} else if(parenCount > 1) { // Matched a ) with an inner-subexpression containing a (...) set
					subExpr.append(matcher.group(3));
					parenCount--;
				} else if(parenCount == 0) { // No matching (
					throw new IllegalInfixException("Mismatched closed parenthesis detected");
				}

			} else if(matcher.group(4) != null) {
				// Found double
				if(parenCount == 0) {
					operands.push(matcher.group(4));
					operandCount++;
				} else {
					subExpr.append(matcher.group(4));
				}

			} else if(matcher.group(5) != null) {
				// Found variable
				if(parenCount == 0) {
					operands.push(matcher.group(5));
					operandCount++;
				} else {
					subExpr.append(matcher.group(5));
				}

			}
		}
		
		/*
		 * Final string assembly code GIST:
		 * At this point the expression should be very simple and it might be spread out over
		 * the operands and operators stack as well as have some bits already in the StringBuilder
		 * object.  As long as we kept the operatorCount and operandCount variables correctly 
		 * updated when we added things to either stack, then we will always know mathematically that
		 * a valid expression will always have two operands per operator and with associativity of
		 * algebra taken into consideration that means only 1 more operand per operator.  This allows
		 * us to know quickly if we have an invalid expression and throw an error before we start 
		 * popping stacks and generating EmptyStackException exceptions. 
		 */
		
		// Infix Error Checking
		if(operatorCount + 1 != operandCount) { // Operators will always be one less than operands.
			throw new IllegalInfixException("Operator/Operand mismatch in infix expression");
		}
			
		while(!operands.isEmpty()) { // Empty the operand stack
			if(operands.size() >= 2) { // If the operand stack has two or more things on it
				// Then we know that the operator stack must have at least 1 item or it would have failed
				// long ago.
				String b = operands.pop();
				postfix.append(operands.pop() + " " + b + " " + operators.pop().toString() + " ");
			} else if(operators.size() > 0) { 
				// Case where operands and operators stacks only have 1 element each and the rest of the 
				// expression is already on the postfix StringBuilder because it was nested in a parenthesis
				postfix.append(operands.pop() + " " + operators.pop().toString() + " ");
			} else {
				// Case where it worked out that recursive calls handled all the work
				// and operands has the final postfix string sitting on top.  Also the case
				// where the given string was a single digit or variable which is technically valid.
				postfix.append(operands.pop());
			}
		}
		
		if(!operators.isEmpty()) {
			// TODO - See if this is still needed.  Have not been able to reproduce the case.
			postfix.append(operators.pop().toString() + " ");
		}
		
		
		/*
		 * TODO - Fix problem where expressions requiring recursive calls to createFromString() 
		 * have an extra space in their output between subexpressions. 
		 */
		return postfix.toString();
	}
	
	
	/**
	 * VERBOSE MODE
	 * Returns a string formatted in postfix notation representing the input parameter 
	 * infix.
	 * 
	 * @param infix		The string to be evaluated in Infix notation
	 * @return			The input string in Postfix notation
	 * @throws			IllegalInfixException
	 */
	static String createFromStringVerbose(String infix) throws IllegalInfixException {
		Stack<String> operands = new Stack<String>();
		Stack<AlgebraicSymbology> operators = new Stack<AlgebraicSymbology>();

		StringBuilder postfix = new StringBuilder();
		StringBuilder subExpr = new StringBuilder();

		// Keep track of counts for error detection.
		int operatorCount = 0;
		int operandCount = 0;
		int parenCount = 0;

		Matcher matcher = INFIX_REGEX.matcher(infix.trim());

		System.out.println("The input infix string is: " + infix);

		while(matcher.find()) {
			System.out.print("\nMatcher currently looking at: " + matcher.group(0));

			if(matcher.group(1) != null) {
				// Found operator
				System.out.println(" >> Executing group(1) block");
				if(parenCount == 0) {
					Operator currentOp = Operator.fromSymbol(matcher.group(1));
					
					if(operators.size() == 0 && operands.size() > 0) {
						System.out.println("Currently only operator");
						operators.push(currentOp);
						operatorCount++;
						printStacks(operators, operands);
					} else if(operandCount >= 2) {
						System.out.println("One or more operators present, total: " + operators.size());
						try {
							int i = 1;
							while(operators.peek().getPrecedence() <= currentOp.getPrecedence()) {
								System.out.println("Times through loop: " + i);
								System.out.println("Current OP: " + currentOp.toString());
								printStacks(operators, operands);
								
								if(operands.size() > 1) {
									String b = operands.pop();
									postfix.append(operands.pop() + " " + b + " " + operators.pop() + " ");
								} else {
									postfix.append(operands.pop() + " " + operators.pop() + " ");
								}
								
								i++;
							}
							System.out.println("Done, all other operators have greater precedence than current OP");
							operators.push(currentOp);
							operatorCount++;
							printStacks(operators, operands);
							System.out.println("Current postfix return string: " + postfix.toString());
						} catch(EmptyStackException e) {
							operators.push(currentOp);
							operatorCount++;
							System.out.println("Done, operators stack empty");
							printStacks(operators, operands);
							System.out.println("Current postfix return string: " + postfix.toString());
						}
							
					} else {
						printStacks(operators, operands);
						throw new IllegalInfixException("Invalid Infix Exception near: \""+currentOp.toString()+"\"");
					}
					
					
				} else {
					subExpr.append(matcher.group(1));
					System.out.println("Adding operator to subExpr, now: " + subExpr.toString());
				}

			} else if(matcher.group(2) != null) {
				// Found (
				System.out.println(" >> Executing group(2) block");
				if(parenCount == 0) {
					System.out.println("New open parenthesis found");
					parenCount++;
				} else {
					parenCount++;
					subExpr.append(matcher.group(2));
					System.out.println("Adding open parenthesis to subExpr, now: " + subExpr.toString());
				}

				System.out.println(" >> parenCount is now: " + parenCount);

			} else if(matcher.group(3) != null) {
				// Found )
				System.out.println(" >> Executing group(3) block");
				if(parenCount == 1) {
					parenCount--;
					System.out.println("Found complete inner sub expression, sending: " + subExpr.toString() + 
							" parenCount is now: " + parenCount);
					operands.push(createFromStringVerbose(subExpr.toString()));
					operandCount++;
					subExpr.delete(0, subExpr.length()); // Clear subExpr contents after recursion call.
					printStacks(operators, operands);
				} else if(parenCount > 1) {
					subExpr.append(matcher.group(3));
					parenCount--;
					System.out.println("Adding close parenthesis to subExpr, now: " + subExpr.toString());
				} else if(parenCount == 0) {
					throw new IllegalInfixException("Mismatched closed parenthesis detected");
				}

			} else if(matcher.group(4) != null) {
				// Found double
				System.out.println(" >> Executing group(4) block");
				if(parenCount == 0) {
					operands.push(matcher.group(4));
					System.out.println("Pushing double: " + matcher.group(4));
					printStacks(operators, operands);
					operandCount++;
				} else {
					subExpr.append(matcher.group(4));
					System.out.println("Adding double to subExpr, now: " + subExpr.toString());
				}

			} else if(matcher.group(5) != null) {
				// Found variable
				System.out.println(" >> Executing group(5) block");
				if(parenCount == 0) {
					operands.push(matcher.group(5));
					System.out.println("Pushing variable: " + matcher.group(5));
					printStacks(operators, operands);
					operandCount++;
				} else {
					subExpr.append(matcher.group(5));
					System.out.println("Adding variable to subExpr, now: " + subExpr.toString());
				}

			}
		}
		
		System.out.println("*******Finished processing string*******");
		printStacks(operators, operands);
		
		
		// Infix Error Checking
		if(operatorCount + 1 != operandCount) { // Operators will always be one less than operands.
			System.out.println("Operator Count: " + operatorCount);
			System.out.println("Operand Count: " + operandCount);
			throw new IllegalInfixException("Operator/Operand mismatch in infix expression");
		}
			
		while(!operands.isEmpty()) {
			if(operands.size() >= 2) {
				String b = operands.pop();
				postfix.append(operands.pop() + " " + b + " " + operators.pop().toString() + " ");
			} else if(operators.size() > 0) {
				postfix.append(operands.pop() + " " + operators.pop().toString() + " ");
			} else {
				postfix.append(operands.pop());
			}
		}
		
		if(!operators.isEmpty()) {
			postfix.append(operators.pop().toString() + " ");
		}
		
		return postfix.toString();
	}

	/**
	 * For debugging purposes, prints the contents of the two stacks.
	 * @param r Your operator stack
	 * @param d Your operand stack
	 */
	private static  void printStacks(Stack<AlgebraicSymbology> r, Stack<String> d) {
		System.out.println("Current Stack Structures");
		System.out.println("--------------------------------");
		System.out.println("Operator Stack: " + r.toString());
		System.out.println("Operand Stack: " + d.toString());
		System.out.println("--------------------------------");
	}
}