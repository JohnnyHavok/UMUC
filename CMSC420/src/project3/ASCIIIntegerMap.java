/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Nov 4, 2010
 * Project: Project 3
 * File: IntegerMapImpl.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project3;

/**
 * @author jsmith
 * A fast hash map that keeps track of occurrences of simple integers such as 
 * character counting in decimal format.  Must know the size of all the numbers
 * you want to count ahead of time.
 */
public class ASCIIIntegerMap {
  private int[] array;
  
  ASCIIIntegerMap() {
    array = new int[128];
    for(int i = 0; i<128; i++) 
      array[i] = 0;
  }

  public void incrementKeyCount(int k) { array[k] = array[k] + 1; }
  public int getKeyCount(int k) { return array[k]; }
  public int size() { return 128; }
}