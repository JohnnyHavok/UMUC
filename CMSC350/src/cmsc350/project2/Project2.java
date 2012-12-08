package cmsc350.project2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Name: Justin Smith
 * CMSC 350
 * Project: 2
 * Date: 11/17/12 11:22 AM
 * Requires: J2SE 7+
 */


public class Project2 {
    public static void main(String[] args) {

        if(args.length == 0) {
            System.out.println("Usage:  Project2 [-f : file | -s : \"String\"]\n");
            System.out.println("Note: When running input from a file or the command line all data will be treated\n" +
                    "as a string and evaluated based on the String class compareTo() method.  You must\n" +
                    "define your own objects to in order to use DoubleOrderedList in any other way.");
            System.out.println("\nRunning default example that adds randomly generated integers to the list\n");

            DoubleOrderedList<Integer> list = new DoubleOrderedList<>();


            int rand;
            for(int i = 0; i < 20; i++) {
                rand = (int) (Math.random() * 1000);
                System.out.println("Adding " + rand + " to list");
                list.add(rand);
            }
            System.out.println("Printing ordered list contents");
            System.out.println(list);
            System.exit(0);
        } // End no args present

        String fileName = null;
        String userInput = null;

        if(args[0].charAt(0) == '-') {
            switch(args[0].charAt(1)) {
                case 'f':
                    fileName = args[1];
                    break;
                case 's':
                    userInput = args[1];
                    break;
                default:
                    System.out.println("Usage:  Project2 [-f : file | -s : \"String\"]");
                    System.out.println("Nothing too fancy, it will break!");
            }
        } else {
            System.out.println("Usage:  Project2 [-f : file | -s : \"String\"]");
            System.out.println("Nothing too fancy, it will break!");
        }

        DoubleOrderedList<String> list = new DoubleOrderedList<>();

        if(fileName != null) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String in;
                while((in = reader.readLine()) != null) {
                    list.addAll(in.split("[\\p{Punct}\\s]+"));
                }
                reader.close();
            } catch (FileNotFoundException e) {
                System.err.println("File: " + fileName + " not found.");
                System.exit(1);
            } catch (IOException e) {
                System.err.println("IOException on file: " + fileName);
                System.exit(1);
            }
        } else if(userInput != null) {
            list.addAll(userInput.split("\\s+"));
        } else {
            System.out.println("Usage:  Project2 [-f : file | -s : \"String\"]");
            System.out.println("Nothing too fancy, it will break!");
            System.exit(1);
        }

        System.out.println("Printing list: \n" + list);
        System.out.println("Total strings read: " + list.size());
    }
}
