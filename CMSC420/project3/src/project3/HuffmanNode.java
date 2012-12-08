/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Nov 4, 2010
 * Project: Project 3
 * File: HuffmanNode.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project3;

/**
 * @author jsmith
 * To be a HuffmanNode any class that implements this interface just needs
 * to include a weight.  Every method is just handy for comparisons, compareTo()
 * being the most important.
 */
public interface HuffmanNode extends Comparable<HuffmanNode> {
  public int getWeight();
  public int compareTo(HuffmanNode node);
  public String toString();
  public int hashCode();
}