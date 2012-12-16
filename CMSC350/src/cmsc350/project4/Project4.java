package cmsc350.project4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Name: Justin Smith
 * CMSC 350
 * Project: 4
 * Date: 12/15/12 1:42 PM
 * Requires: J2SE 7+
 */

public class Project4 {
	public static void main (String[] args) { new Project4(); }

	Integer[][] bookTestCases = { 	{5, 3, 10, 13, 7, 15},		// - Make left rotation
									{13, 7, 15, 5, 10, 3},		// - Make right rotation
									{13, 5, 15, 3, 7, 10},		// - Make left-right rotation
									{5, 3, 13, 10, 15, 7} };	// - Make right-left rotation

	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	List<MyDrawTreeFrame> windowList;

	private Project4() {
		windowList = new ArrayList<>();
		System.out.println("AVLTree algorithm builder");

		boolean quit = false;
		int input;

		while(!quit) {
			System.out.println("\nSelect Option:\n" +
					"\t(1) Use prebuilt left rotation simulator\n" +
					"\t(2) Use prebuilt right rotation simulator\n" +
					"\t(3) Use prebuilt left-right rotation simulator\n" +
					"\t(4) Use prebuilt right-left rotation simulator\n" +
					"\t(5) Free text input\n" +
					"\t(0) Quit");
			System.out.print("Choose > ");
			input = getNextInt();

			switch(input) {
				case 1 :
					runBookArray(0);
					break;
				case 2 :
					runBookArray(1);
					break;
				case 3 :
					runBookArray(2);
					break;
				case 4 :
					runBookArray(3);
					break;
				case 5 :
					runFreeText();
					break;
				case 0 :
					quit = true;
					break;
				default :
					System.out.println("Invalid option, try again");
			}
		}

		for(MyDrawTreeFrame f: windowList)
			f.dispose();
	}

	private void runBookArray(int flag) {
		windowList.add(new MyDrawTreeFrame(new AVLTree<>(bookTestCases[flag]), "Final Tree", false));
	}

	private void runFreeText() {
		List<String> input = new ArrayList<>();
		System.out.println("Free text mode selected.  Enter one string per line");
		boolean read = true;
		while(read) {
			String s = getNextString();
			if(s == null) read = false;
			else input.add(s);
		}
		windowList.add(new MyDrawTreeFrame(new AVLTree<>(input), "Final Tree", false));
	}

	private int getNextInt() {
		do {
			try {
				return Integer.parseInt(in.readLine());
			} catch (IOException e) {
				System.out.println("Error thrown from getNextInt input");
				e.printStackTrace();
			} catch (NumberFormatException e) {
				System.out.print("Enter numbers only, please try again > ");
			}
		} while(true);
	}

	private String getNextString() {
		String s = null;
		try {
			s = in.readLine();
			if(s.length() == 0) return null;
		} catch (IOException e) {
			System.out.println("Thrown from getNextString() on user input");
			e.printStackTrace();
		}
		return s;
	}
}
