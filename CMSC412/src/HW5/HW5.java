package HW5;

/**
 * Created by Justin Smith
 * 7/5/14
 * CMSC 412
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HW5 {

  public static void main(String[] args) {

    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    Path dir, file;
    int response;
    boolean hasDir = false;
    boolean quit = false;

    while(!quit) {
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
        case 1:
          System.out.print("Please enter absolute path to directory > ");
          dir = Paths.get(getNextString(input));
          hasDir = true;
          System.out.println("The working directory is now: " + dir.toString());
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
      System.out.println("Thrown from getNextString() on user input");
      e.printStackTrace();
    }
    return s;
  }

}
