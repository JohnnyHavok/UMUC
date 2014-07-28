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
import java.util.HashMap;
import java.util.Random;

public class FP {

  static final int MIN_VFRAME = 0;
  static final int MAX_VFRAME = 9;
  static final int MIN_PFRAME = 1;
  static final int MAX_PFRAME = 8;

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
          System.out.print("Please enter a number of Virtual Memory Page calls you want to generate > ");

          int c = getNextInt(input);
          refString = new int[c];
          Random rand = new Random();

          for (int i = 0; i < refString.length; ++i)
            refString[i] = rand.nextInt((MAX_VFRAME - MIN_VFRAME) + 1) + MIN_VFRAME;

          System.out.println("Completed generating random reference string");

          break;
        // -- End Generate Reference String --


        // -- Begin Display Current Reference String --
        case 3:
          if (refString != null) {
            System.out.println("The current reference string is >");

            for (int refValue : refString)
              System.out.print(refValue + " ");

            System.out.print("\n");
          } else {
            System.out.println("There is no current reference string!");
          }
          break;
        // -- End Display Current Reference String --


        // -- Begin Simulate FIFO Paging --
        case 4:
          if (refString != null) {
            System.out.println("Running FIFO Demand Paging Strategy");
            System.out.print("Please enter a number of physical frames [" + MIN_PFRAME + "-" + MAX_PFRAME + "] > ");
            int phyFrames = getNextInt(input);

            if (phyFrames > MAX_PFRAME || phyFrames < MIN_PFRAME) {
              System.out.println("Your number of physical frames is outside of the limit " +
                  "[" + MIN_PFRAME + "-" + MAX_PFRAME + "]");
              break;
            }

            runFIFO(refString, phyFrames);

          } else
            System.out.println("You must first create a reference string!");
          break;
        // -- End Simulate FIFO Paging--


        // -- Begin Simulate OPT Paging --
        case 5:
          if (refString != null) {
            System.out.println("Running OPT Demand Paging Strategy");
            System.out.print("Please enter a number of physical frames [" + MIN_PFRAME + "-" + MAX_PFRAME + "] > ");
            int phyFrames = getNextInt(input);

            if (phyFrames > MAX_PFRAME || phyFrames < MIN_PFRAME) {
              System.out.println("Your number of physical frames is outside of the limit " +
                  "[" + MIN_PFRAME + "-" + MAX_PFRAME + "]");
              break;
            }

            runOPT(refString, phyFrames);
          }
          else
            System.out.println("You must first create a reference string!");
          break;
        // -- End Simulate OPT Paging --


        // -- Begin Simulate LRU Paging --
        case 6:
          if (refString != null) {
            System.out.println("Running LRU Demand Paging Strategy");
            System.out.print("Please enter a number of physical frames [" + MIN_PFRAME + "-" + MAX_PFRAME + "] > ");
            int phyFrames = getNextInt(input);

            if (phyFrames > MAX_PFRAME || phyFrames < MIN_PFRAME) {
              System.out.println("Your number of physical frames is outside of the limit " +
                  "[" + MIN_PFRAME + "-" + MAX_PFRAME + "]");
              break;
            }

            runLRU(refString, phyFrames);
          }
          else
            System.out.println("You must first create a reference string!");
          break;
        // -- End Simulate LRU Paging --


        // -- Begin Simulate LFU Paging --
        case 7:
          if (refString != null) {
            System.out.println("Running LFU Demand Paging Strategy");
            System.out.print("Please enter a number of physical frames [" + MIN_PFRAME + "-" + MAX_PFRAME + "] > ");
            int phyFrames = getNextInt(input);

            if (phyFrames > MAX_PFRAME || phyFrames < MIN_PFRAME) {
              System.out.println("Your number of physical frames is outside of the limit " +
                  "[" + MIN_PFRAME + "-" + MAX_PFRAME + "]");
              break;
            }

            runLFU(refString, phyFrames);
          }
          else
            System.out.println("You must first create a reference string!");
          break;
        // -- End Simulate LFU Paging --


        default:
          System.out.println("Invalid option, try again");
      }
    }


  }

  // -- Method simulates FIFO Demand Paging when provided a reference string
  private static void runFIFO(int[] refString, int phyFrames) {
    ArrayList<Integer> memory = new ArrayList<>(phyFrames);
    String[][] table = genTable(refString, phyFrames);

    int victim = -1;
    boolean fault;
    int currentFrame = 0;
    int faultCount = 0;


    System.out.println("Beginning FIFO Simulation");

    System.out.println("Current Table\n");
    printTable(table);

    System.out.print("\nPress Enter to Continue > ");
    try {
      System.in.read();
    } catch (IOException e) {
      e.printStackTrace();
    }

    for (int i = 0; i < refString.length; ++i) {
      if (!memory.contains(refString[i])) {
        if (memory.size() < phyFrames) {
          memory.add(currentFrame, refString[i]);
          ++currentFrame;
          fault = true;
          faultCount++;
        } else { // Memory is full, does not contain reference, page fault replacement
          if (currentFrame >= phyFrames)
            currentFrame = 0;

          fault = true;
          faultCount++;
          victim = memory.get(currentFrame);
          memory.set(currentFrame, refString[i]);
          ++currentFrame;
        }
      } else { // Memory contains current reference, no fault
        fault = false;
      }

      for (int j = 0; j < memory.size(); ++j) {
        table[j + 1][i + 1] = String.valueOf(memory.get(j));
      }

      if (fault) {
        table[phyFrames + 1][i + 1] = "F";
        if (victim != -1)
          table[phyFrames + 2][i + 1] = String.valueOf(victim);
      }

      System.out.println("Current Table\n");
      printTable(table);

      System.out.print("\nPress Enter to Continue > ");
      try {
        System.in.read();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    System.out.println("\nFIFO Simulation is now complete");
    System.out.println("There were " + faultCount + " faults in this run");
  }

  // -- Method simulates OPT Demand Paging when provided a reference string
  private static void runOPT(int[] refString, int phyFrames) {
    ArrayList<Integer> memory = new ArrayList<>(phyFrames);
    ArrayList<Integer> refList = new ArrayList<>();

    for (int i : refString)  // Create a reference list to
      refList.add(i);        // to search future

    String[][] table = genTable(refString, phyFrames);

    int victim = -1;
    boolean fault;
    int currentFrame = 0;
    int faultCount = 0;
    int max = -1;
    int index;

    System.out.println("Beginning OPT Simulation");

    System.out.println("Current Table\n");
    printTable(table);

    System.out.print("\nPress Enter to Continue > ");
    try {
      System.in.read();
    } catch (IOException e) {
      e.printStackTrace();
    }

    for (int i = 0; i < refString.length; ++i) {
      if (!memory.contains(refString[i])) {
        if (memory.size() < phyFrames) {
          memory.add(currentFrame, refString[i]);
          refList.remove((Integer) refString[i]);  // Remove first occurrence of ref from list don't care about past
          ++currentFrame;
          fault = true;
          faultCount++;
        } else { // Page fault on full memory, swap
          fault = true;
          faultCount++;
          // Step 1 remove current item.
          int temp = refList.get(0);
          refList.remove(0);

          // Step 2 find ref that will not be used for longest period
          for (int m : memory) {
            index = refList.indexOf(m);

            // Simplest case, ref is never seen again
            if (index == -1) {
              victim = m;
              break;
            }

            if (index > max) { // Find max index into reference string
              victim = m;      // that will be the victim unless an index
              max = index;     // of -1 comes by.
            }
          }

          memory.set(memory.indexOf(victim), temp); // Swap
          max = -1;  // Reset max
        }
      } else { // Memory contains reference
        fault = false;
        refList.remove(0);
      }

      for (int j = 0; j < memory.size(); ++j) {
        table[j + 1][i + 1] = String.valueOf(memory.get(j));
      }

      if (fault) {
        table[phyFrames + 1][i + 1] = "F";
        if (victim != -1)
          table[phyFrames + 2][i + 1] = String.valueOf(victim);
      }

      System.out.println("Current Table\n");
      printTable(table);

      System.out.print("\nPress Enter to Continue > ");
      try {
        System.in.read();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    System.out.println("\nOPT Simulation is now complete");
    System.out.println("There were " + faultCount + " faults in this run");
  }

  // -- Method simulates LRU Demand Paging when provided a reference string
  private static void runLRU(int[] refString, int phyFrames) {
    ArrayList<Integer> memory = new ArrayList<>(phyFrames);
    int[] lruCount = new int[phyFrames];

    String[][] table = genTable(refString, phyFrames);

    int victim = -1;
    boolean fault;
    int currentFrame = 0;
    int faultCount = 0;
    int max, index;

    System.out.println("Beginning LRU Simulation");

    System.out.println("Current Table\n");
    printTable(table);

    System.out.print("\nPress Enter to Continue > ");
    try {
      System.in.read();
    } catch (IOException e) {
      e.printStackTrace();
    }

    for (int i = 0; i < refString.length; ++i) {
      if (!memory.contains(refString[i])) {
        if (memory.size() < phyFrames) {
          memory.add(currentFrame, refString[i]);
          for (int j = 0; j < lruCount.length; ++j)
            lruCount[j]++; // Increment every frames count
          lruCount[currentFrame] = 1; // Reset current back to 1
          ++currentFrame;
          fault = true;
          faultCount++;
        } else { // Page fault on full memory, swap
          max = -1;
          index = 0;
          fault = true;
          faultCount++;

          // Find max lruCount
          for (int j = 0; j < lruCount.length; ++j) {
            if (lruCount[j] > max) {
              max = lruCount[j];
              index = j;
            }
          }

          // Swap victim out
          victim = memory.get(index);
          memory.set(index, refString[i]);

          for (int j = 0; j < lruCount.length; ++j)
            lruCount[j]++; // Increment every frames count
          lruCount[memory.indexOf(refString[i])] = 1; // Reset LRU Counter for new ref.

        }
      } else { // Memory contains reference
        fault = false;
        for (int j = 0; j < lruCount.length; ++j)
          lruCount[j]++; // Increment every frames count
        lruCount[memory.indexOf(refString[i])] = 1; // Reset LRU counter for this reference
      }

      for (int j = 0; j < memory.size(); ++j) {
        table[j + 1][i + 1] = String.valueOf(memory.get(j));
      }

      if (fault) {
        table[phyFrames + 1][i + 1] = "F";
        if (victim != -1)
          table[phyFrames + 2][i + 1] = String.valueOf(victim);
      }

      System.out.println("Current Table\n");
      printTable(table);

      System.out.print("\nPress Enter to Continue > ");
      try {
        System.in.read();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    System.out.println("\nLRU Simulation is now complete");
    System.out.println("There were " + faultCount + " faults in this run");

  }

  // -- Method simulates LFU Demand Paging when provided a reference String
  private static void runLFU(int[] refString, int phyFrames) {
    ArrayList<Integer> memory = new ArrayList<>(phyFrames);
    HashMap<Integer, Integer> lfuCount = new HashMap<>();

    String[][] table = genTable(refString, phyFrames);

    int victim = -1;
    boolean fault;
    int currentFrame = 0;
    int faultCount = 0;
    int min, index, count;

    System.out.println("Beginning LFU Simulation");

    System.out.println("Current Table\n");
    printTable(table);

    System.out.print("\nPress Enter to Continue > ");
    try {
      System.in.read();
    } catch (IOException e) {
      e.printStackTrace();
    }

    for (int i = 0; i < refString.length; ++i) {
      if (!memory.contains(refString[i])) {
        if (memory.size() < phyFrames) {
          memory.add(currentFrame, refString[i]);
          lfuCount.put(refString[i], 1);
          ++currentFrame;
          fault = true;
          faultCount++;
        } else { // Page fault on full memory, swap
          min = lfuCount.get(memory.get(0)); // Set lowest to first PHY frame
          index = 0;
          fault = true;
          faultCount++;

          // Find lowest freq frame in phy memory
          for (int j = 0; j < memory.size(); ++j) {
            if (lfuCount.get(memory.get(j)) < min) {
              min = lfuCount.get(memory.get(j));
              index = j;
            }
          }

          victim = memory.get(index);
          memory.set(index, refString[i]);

          // Update LFU Freq map on new entry.
          if (lfuCount.containsKey(refString[i])) {
            count = lfuCount.get(refString[i]);
            count++;
            lfuCount.put(refString[i], count);
          } else {
            lfuCount.put(refString[i], 1);
          }
        }
      } else { // Memory contains reference
        fault = false;
        count = lfuCount.get(refString[i]);
        count++;
        lfuCount.put(refString[i], count); // Update frequency map
      }

      for (int j = 0; j < memory.size(); ++j) {
        table[j + 1][i + 1] = String.valueOf(memory.get(j));
      }

      if (fault) {
        table[phyFrames + 1][i + 1] = "F";
        if (victim != -1)
          table[phyFrames + 2][i + 1] = String.valueOf(victim);
      }

      System.out.println("Current Table\n");
      printTable(table);

      System.out.print("\nPress Enter to Continue > ");
      try {
        System.in.read();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    System.out.println("\nLFU Simulation is now complete");
    System.out.println("There were " + faultCount + " faults in this run");
  }

  private static String[][] genTable(int[] refString, int phyFrames) {
    String[][] table = new String[phyFrames + 3][refString.length + 1]; // Row x Column
    table[0][0] = "Reference String";
    for (int i = 1; i <= phyFrames; ++i)
      table[i][0] = "Physical Frame " + (i - 1);
    table[phyFrames + 1][0] = "Page Faults";
    table[phyFrames + 2][0] = "Victim Frames";

    for (int i = 0; i < refString.length; ++i)
      table[0][i + 1] = String.valueOf(refString[i]);

    return table;
  }

  private static void printTable(String[][] table) {
    for (String[] row : table) {
      for (int column = 0; column < row.length; ++column) {
        if (column == 0) {
          System.out.printf("%-18s", row[0]);
        } else {
          if (row[column] == null)
            System.out.printf("%4s", " ");
          else
            System.out.printf("%4s", row[column]);
        }
      }
      System.out.println();
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
    System.out.print("Please enter a reference string of numbers [" + MIN_VFRAME + "-" + MAX_VFRAME + "] " +
        "separated by a space > ");
    String[] input = getNextString(r).split("\\s+");
    int[] refString = new int[input.length];
    int t; // temp holder
    for (int i = 0; i < input.length; ++i) {
      try {
        t = Integer.parseInt(input[i]);
      } catch (NumberFormatException e) {
        System.out.println("Some of your input did not include numbers!");
        return null;
      }
      if (t > MAX_VFRAME || t < MIN_VFRAME) {
        System.out.println("You input a value out of range with [0-9], it was " + t);
        System.out.println("Please try again");
        return getReferenceString(r);
      }
      refString[i] = t;
    }
    return refString;
  }
}
