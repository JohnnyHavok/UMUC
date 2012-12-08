// UMUC CMSC230 - Project 3
// Written by Justin D. Smith
// 13 August 2009
// EvalPolynomial.java
package project3;

import project1.Polynomial;

// Extend our original class from Project 1 and add some extra operations.
public class EvalPolynomial extends Polynomial {
	public EvalPolynomial(String str) {
		super(str);
	}
	
	public double evaluate(double n) {
		double results = 0;
		for(int i = 0; i < contents[0].length; i++)
			results += (double)contents[0][i] * Math.pow(n, (double)contents[1][i]);
		return results;
	}
}