/**
 *   Document   : CountBean
 *   Created on : Mar 31, 2013, 7:06:13 PM
 *   Author     : Justin Smith
 *   Course     : CMIS 440
 *   Project    : Homework 2
 */
package HW2;

/**
 * Basic bare bones java bean.
 * @author Justin Smith
 */
public class CountBean {
    private int count;

    // Default constructor public constructor with no arguments
    public CountBean() { count = 0; }
    
    // Getters and Setters
    public void incrementCount() { ++count; }
    public int getCount() { return count; }

}
