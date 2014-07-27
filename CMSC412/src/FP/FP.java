package FP;

/**
 * Created by Justin Smith
 * 7/26/14
 * CMSC 412 - Final Project
 * Requires Java JDK 7+
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


// Program relies on Java NIO.2 from JDK 7+.
// Importantly the new File and Path classes there.

public class FP {

  public static void main(String[] args) {

    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    int response;
    boolean quit = false;
    int[] refString = null;

    while (!quit) {
      System.out.println("\nSelect Option:\n" +
          "\t(0) Exit\n" +
          "\t(1) Read Reference String\n" +
          "\t(2) Generate Reference String\n" +
          "\t(3) Display Current Reference String\n" +
          "\t(4) Simulate FIFO Demand Paging\n" +
          "\t(5) Simulate OPT Demand Paging\n" +
          "\t(6) Simulate LRU Demand Paging\n" +
          "\t(7) Simulate LFU Demand Paging");
      System.out.print("Choose > ");

      response = getNextInt(input);

      switch (response) {
        case 0:
          quit = true;
          break;

        // -- Begin Read Reference String --
        case 1:
          refString = getReferenceString(input);
          break;
        // -- End Read Reference String --


        // -- Begin Generate Reference String --
        case 2:
          break;
        // -- End Generate Reference String --


        // -- Begin Display Current Reference String --
        case 3:
          break;
        // -- End Display Current Reference String --


        // -- Begin Simulate FIFO Paging --
        case 4:
          break;
        // -- End Simulate FIFO Paging--


        // -- Begin Simulate OPT Paging --
        case 5:
          break;
        // -- End Simulate OPT Paging --


        // -- Begin Simulate LRU Paging --
        case 6:
          break;
        // -- End Simulate LRU Paging --


        // -- Begin Simulate LFU Paging --
        case 7:
          break;
        // -- End Simulate LFU Paging --


        default:
          System.out.println("Invalid option, try again");
      }
    }


  }

  private static int getNextInt(BufferedReader r) {
    do {
      try {
        return Integer.parseInt(r.readLine());
      } catch (IOException e) {
        System.out.println("Error thrown from getNextInt input");
        e.printStackTrace();
      } catch (NumberFormatException e) {
        System.out.print("Enter numbers only, please try again > ");
      }
    } while (true);
  }

  private static String getNextString(BufferedReader r) {
    String s = null;
    try {
      s = r.readLine();
      if (s.length() == 0) return null;
    } catch (IOException e) {
      System.out.println("Thrown from readLine() on user input");
      e.printStackTrace();
    }
    return s;
  }

  private static int[] getReferenceString(BufferedReader r) {
    System.out.print("Please enter a reference string of numbers [0-9] separated by a space > ");
    String[] input = getNextString(r).split("\\s+");
    int[] refString = new int[input.length];
    int t; // temp holder
    for (int i = 0; i < input.length; i++) {
      try {
        t = Integer.parseInt(input[i]);
      } catch (NumberFormatException e) {
        System.out.println("Some of your input did not include numbers!");
        return null;
      }
      if (t > 9 || t < 0) {
        System.out.println("You input a value out of range with [0-9], it was " + t);
        System.out.println("Please try again");
        return getReferenceString(r);
      }
      refString[i] = t;
    }
    return refString;
  }
}
