package scratch;

import java.util.Scanner;

public class TowersExample {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int n;
		System.out.println("Input the number of rings: ");
		if(in.hasNextInt()) {
			n = in.nextInt();
		} else {
			System.out.println("Error: you must enter an integer.");
			System.out.println("Terminating program.");
			return;
		}
		
		System.out.println("Towers of Hanoi with " + n + " rings\n");
		doTowers(n, 1, 2, 3);
	}
	
	public static  void doTowers(int n, int startPeg, int auxPeg, int endPeg) {
		if(n > 0) {			
			doTowers(n-1, startPeg, endPeg, auxPeg);
			System.out.println("Move ring " + n + " from peg " + startPeg + " to peg " + endPeg);
			doTowers(n-1, auxPeg, startPeg, endPeg);
		}
	}
}
