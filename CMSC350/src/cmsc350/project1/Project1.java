/**
 * Author: Justin Smith
 * Course: CMSC 350
 * Project: 1
 * Date: Nov 3, 2012 7:47:12 PM
 */
package cmsc350.project1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author jsmith
 *
 */
public class Project1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Boolean verbose = true;
		String file = null;
		String userInput = null;
		
		if(args.length == 0) {
			System.out.println("Usage:  Project1 [-f : file | -s : \"String\"] [-v : Verbose processing]");
			System.out.println("Nothing too fancy, it will break!");
			System.out.println("Running default examples instead: ");
			
			try {
				System.out.print("Processing: ((a*b)/(c*d))^3 = ");
				System.out.println(PostfixGenerator.createFromString("((a*b)/(c*d))^3"));
				System.out.print("Processing: (a*b)/(c*d) = ");
				System.out.println(PostfixGenerator.createFromString("(a*b)/(c*d)"));
				System.out.print("Processing: a+b*c+d+e = ");
				System.out.println(PostfixGenerator.createFromString("a+b*c+d+e"));
				System.out.print("Processing: (a*b)/(c*d)-12 = ");
				System.out.println(PostfixGenerator.createFromString("(a*b)/(c*d)-12"));
				System.out.print("Processing: ((a+b)^2/(c*d)) = ");
				System.out.println(PostfixGenerator.createFromString("((a+b)^2/(c*d))"));
				System.out.print("Processing: (((a))) = ");
				System.out.println(PostfixGenerator.createFromString("(((a)))"));
				System.out.print("Processing: a+b = ");
				System.out.println(PostfixGenerator.createFromString("a+b"));
				System.out.print("Processing: a+(b)) = ");
				System.out.println(PostfixGenerator.createFromString("a+(b))"));
			} catch (IllegalInfixException e) {
				System.out.println(e.getMessage());
			}
		} else {
			for(int i = 0; i < args.length; i++) {
				if(args[i].charAt(0) == '-')
					switch(args[i].charAt(1)) {
					case 'f':
						file = args[i+1];
						break;
					case 'v':
						verbose = true;
						break;
					case 's':
						userInput = args[i+1];
						break;
					default:
						System.out.println("Usage:  Project1 [-f : file | -s : \"String\"] [-v : Verbose processing]");
						System.out.println("Nothing too fancy, it will break!");
					}
			}
			
			if(file != null)
				processFile(file, verbose);
			
			if(userInput != null)
				try {
					System.out.print("Processing: " + " = ");
					System.out.println(generate(userInput, verbose));
				} catch (IllegalInfixException e) {
					System.out.println(e.getMessage());
				}
		}
		
		
		 
	}
	
	private static void processFile(String s, Boolean v) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(s));
			String in;
			while ((in = reader.readLine()) != null) {
				System.out.print("Processing: " + in + " = ");
				System.out.println(generate(in, v));
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File: "+ s + " not found!");
		} catch (IllegalInfixException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("Unknown error occured");
			e.printStackTrace();
		}
	}
	
	private static String generate(String s, boolean v) throws IllegalInfixException {
		if(v)
			return PostfixGenerator.createFromStringVerbose(s);
		return PostfixGenerator.createFromString(s);
	}
}