// UMUC CMSC230 - Project 1
// Written by Justin D. Smith
// 17 June 2009
// Project1.java
package project1;

import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

public class Project1 {
	public static void main(String[] args) throws FileUnsorted, IOException {
		// Make file handlers
		File input1, input2, output;
		if(args.length != 3) {
			// Default settings
			input1 = new File("file1.txt");
			input2 = new File("file2.txt");
			output = new File("out.txt");
		} else {
			// Running the program from command line using 
			// "java project1.Project1 file1.txt file2.txt out.txt" syntax
			input1 = new File(args[0]);
			input2 = new File(args[1]);
			output = new File(args[2]);
		}
		
		
		// Some user messages followed by array processing
		System.out.println("Processing polynomials in file: " + input1.getName());
		Polynomial[] p1 = checkSorted(input1);
		
		System.out.println("Processing polynomials in file: " + input2.getName());
		Polynomial[] p2 = checkSorted(input2);
		
		System.out.println("Merging the contents of the polynomial files into: " + output.getName());
		merge(p1, p2, output);	
	}
	
	private static Polynomial[] checkSorted(File file) throws FileUnsorted, IOException {	
		Scanner input = new Scanner(file);
		Stack<Polynomial> stk = new Stack<Polynomial>();
		String errMessage = "\nIncorrect relation between the polynomials\n";
		Polynomial temp;

		while(input.hasNextLine()) {
			if(!stk.empty()) {
				temp = new Polynomial(input.nextLine());
				if(temp.compareTo(stk.peek()) != -1)
					stk.push(temp);
				else
					throw new FileUnsorted(errMessage + stk.peek() + " and " + temp);		
			} else
				stk.push(new Polynomial(input.nextLine()));
		}

		Polynomial[] results = new Polynomial[stk.size()];
		
		for(int i = results.length - 1; i >= 0; i--) {
			results[i] = stk.pop();
		}
		return results;
	}
	
	private static void merge(Polynomial[] a, Polynomial[] b, File output) throws IOException {
		PrintWriter out = new PrintWriter(output);
		int j = 0, 
			k = 0;
		
		// Walk both arrays until one of them runs out of stuff.
		while(j < a.length && k < b.length) {
			if(a[j].compareTo(b[k]) != 1) {
				out.println(a[j].outputFormat());
				System.out.println(a[j]);
				j++;
			} else {
				out.println(b[k].outputFormat());
				System.out.println(b[k]);
				k++;
			}
		}
		// Now find out which one has stuff left and walk it out.
		while(j < a.length) { 
			out.println(a[j].outputFormat());
			System.out.println(a[j]);
			j++;
		}
		while(k < b.length) {
			out.println(b[k].outputFormat());
			System.out.println(b[k]);
			k++;
		}
		out.close();
	}
}