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
import java.util.ArrayList;


// Program relies on Java NIO.2 from JDK 7+.
// Importantly the new File and Path classes there.

public class FP {

  public static void main(String[] args) {

    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    int response;
    boolean quit = false;

    while (!quit) {
      System.out.println("\nSelect Option:\n" +
          "\t(0) Exit\n" +
          "\t(1) Select Directory\n" +
          "\t(2) List Directory Content\n" +
          "\t(3) List Directory Content (Recursive)\n" +
          "\t(4) Delete File\n" +
          "\t(5) Display File\n" +
          "\t(6) Encrypt File\n" +
          "\t(7) Decrypt File");
      System.out.print("Choose > ");

      response = getNextInt(input);

      switch (response) {
        case 0:
          quit = true;
          break;

        // -- Begin Read Reference String --
        case 1:
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

  private static ArrayList<Integer> getReferenceString(BufferedReader r) {
    ArrayList<Integer> list = new ArrayList<>();


    return list;
  }
}
