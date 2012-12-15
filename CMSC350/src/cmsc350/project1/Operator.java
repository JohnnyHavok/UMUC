/**
 * Author: Justin Smith
 * Course: CMSC 350
 * Project: 1
 * Date: Nov 3, 2012 8:34:34 PM
 */
package cmsc350.project1;


import java.util.HashMap;
import java.util.Map;

/**
 * @author jsmith
 *
 */
public enum Operator implements AlgebraicSymbology {
	/**
	 * Enumeration Classes
	 */
	PLUS("+") {
		public int getPrecedence() {return 4;}
	},
	MINUS("-") {
		public int getPrecedence() {return 4;}
	},
	MULTIPLY("*") {
		public int getPrecedence() {return 3;}
	},
	DIVIDE("/") {
		public int getPrecedence() {return 3;}
	},
	POWER("^") {
		public int getPrecedence() {return 2;}
	};
		
	private final String symbol;
	
	/**
	 * Enumeration Class Constructor
	 * @param symbol	Human recognizable algebraic token.
	 */
	Operator(String symbol) { this.symbol = symbol; }
	
	public abstract int getPrecedence();
	public String toString() { return symbol; }
	
	/*
	 * The following code is derived from Effective Java 2nd Edition (Bloch, 2008, p. 154)
	 * It has been adapted to fit my requirement which was different then the code from the book
	 * Bloch's example required the string name to access the object and I require the character
	 * of the symbol to get the correct enumeration.
	 */
	private static final Map<String, Operator> symbolToEnum = new HashMap<>();
	static {
		for(Operator op: values())
			symbolToEnum.put(op.symbol, op);
	}
	
	/**
	 * 
	 * @param symbol The single character algebraic symbol for which you need an enumeration object
	 * @return Enumeration Operator Type
	 */
	public static Operator fromSymbol(String symbol) {
		return symbolToEnum.get(symbol);
	}
}
