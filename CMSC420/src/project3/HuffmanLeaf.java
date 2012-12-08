/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Nov 6, 2010
 * Project: Project 3
 * File: HuffmanLeaf.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */

package project3;

/**
 * @author jsmith
 *
 */
public class HuffmanLeaf implements HuffmanNode {
  private int data;
  private int weight;
  
  HuffmanLeaf(int data, int weight) { 
    this.weight = weight;
    this.data = data;
  }
  
  public void setChar(char c)   { data = c;           }
  public void setInt(int i)     { data = i;           }
  public void setWeight(int w)  { weight = w;         }
  
  public char getChar()         { return (char) data; }
  public int  getInt()          { return data;        }
  public int  getWeight()       { return weight;      }
  
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
    code = 31 * code + data;
    return code;
  }
}
