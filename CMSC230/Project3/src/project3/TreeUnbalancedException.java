// UMUC CMSC230 - Project 3
// Written by Justin D. Smith
// 13 August 2009
// TreeUnbalancedException.java
package project3;

public class TreeUnbalancedException extends RuntimeException {
	public TreeUnbalancedException() {
		super();
	}
	
	public TreeUnbalancedException(String message) {
		super(message);
	}
}