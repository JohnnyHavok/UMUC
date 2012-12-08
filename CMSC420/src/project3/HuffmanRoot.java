/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Nov 6, 2010
 * Project: Project 3
 * File: HuffmanRoot.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */

package project3;

/**
 * @author jsmith
 *
 */
public class HuffmanRoot implements HuffmanNode {
  private int weight;
  
  private HuffmanNode left;
  private HuffmanNode right;
  
  HuffmanRoot(int weight, HuffmanNode left, HuffmanNode right) {
    this.weight = weight;
    this.left = left;
    this.right = right;
  }
  
  public void setWeight(int w)            { weight = w;         }
  public void setLeft(HuffmanNode left)   { this.left = left;   }
  public void setRight(HuffmanNode right) { this.right = right; }
  
  public int          getWeight() { return weight;      }
  public HuffmanNode  getLeft()   { return left;        }
  public HuffmanNode  getRight()  { return right;       }
  
  
  public boolean isChild() { return left == null && right == null; }
  
  @Override
  public int compareTo(HuffmanNode n) {
    if(weight < n.getWeight())
      return -1;
    
    if(weight > n.getWeight())
      return 1;
  
    return 0;
  }
  
  @Override
  public String toString() { return String.valueOf(weight); }

  @Override
  public int hashCode() {
    int code = 21;
    code = 31 * code + weight;
    return code;
  }
}
