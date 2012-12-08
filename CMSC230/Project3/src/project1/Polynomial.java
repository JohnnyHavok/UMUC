// UMUC CMSC230 - Project 1
// Written by Justin D. Smith
// 17 June 2009
// Polynomial.java
package project1;


public class Polynomial implements Comparable<Polynomial> {
	// The data - [0][X] = Coefficent
	//			  [1][X] = Exponent
	protected int[][] contents;

	public Polynomial(String str) {		
		String[] temp = str.split(" ");
		
		contents = new int[2][Integer.parseInt(temp[1]) + 1]; // + 1 to handle the lone integer case
		
		for(int i = 0; i < temp.length; i+=2) {
			// Since we always jump forwards 2 spaces then dividing i by 2 keeps
			// contents inline 1:1
			contents[0][i/2] = Integer.parseInt(temp[i]);
			contents[1][i/2] = Integer.parseInt(temp[i+1]);
		}
	}
	
	public int compareTo(Polynomial o) {
		// Best Case Scenarios - The "longer" array will have the highest exponent upfront.
		if(this.contents[0].length > o.contents[0].length)
			return 1;
		else if(this.contents[0].length < o.contents[0].length)
			return -1;
		
		// Worse Case Scenarios - Walk the arrays checking exponents first then coeffeicents.
		for(int i = 0; i < this.contents[0].length; i++) {
			if(this.contents[1][i] == o.contents[1][i]) { // If the exponents match
				if(this.contents[0][i] > o.contents[0][i])
					return 1;
				else if(this.contents[0][i] < o.contents[0][i])
					return -1;
			} else { // They do not match one has to be to greater.
				if(this.contents[1][i] > o.contents[1][i])
					return 1;
				else
					return -1;
			}
		}
		// Deadly worse case scenario, just walked the arrays to the end.
		return 0;
	}
	
	public String toString() {
		StringBuilder results = new StringBuilder();
		
	    results.append(contents[0][0]);
		results.append(contents[1][0] > 0 ? "X":"");
		results.append(contents[1][0] > 1 ? "^" + contents[1][0] : "");
		
		for(int i = 1; i < contents[0].length; i++) {
			if(contents[0][i] != 0) {
				results.append(contents[0][i] > 0 ? " + " + contents[0][i] 
				                                  : " - " + Math.abs(contents[0][i]));
				results.append(contents[1][i] > 0 ? "X" : "");
				results.append(contents[1][i] > 1 ? "^" + contents[1][i] : "");
			} else break;
		}
		
		return results.toString();
	}
	
	public String outputFormat() {
		StringBuilder results = new StringBuilder();
		
		for(int i = 0; i < contents[0].length; i++) {
			if(contents[0][i] != 0)
				results.append(contents[0][i] + " " + contents[1][i] + " ");
			else break;
		}
		
		return results.toString();
	}
}