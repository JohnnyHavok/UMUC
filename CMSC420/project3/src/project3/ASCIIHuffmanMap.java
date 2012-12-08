/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Nov 5, 2010
 * Project: Project 3
 * File: ASCIIHuffmanMap.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */

package project3;

/**
 * @author jsmith
 * A fast hash map where the ASCII code is the key on the map and the 
 * Huffman code is stored in a string of 0s and 1s representing a binary
 * pattern.
 */
public class ASCIIHuffmanMap {
  private String[] array;
  
  ASCIIHuffmanMap() { array = new String[128]; }
  
  public void setCode(int ascii, String hcode) { array[ascii] = hcode; }
  public String getCode(int ascii) { return array[ascii]; }
  public int size() { return 128; }
}