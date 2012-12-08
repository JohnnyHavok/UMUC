/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Dec 4, 2010
 * Project: Project 4
 * File: Project4.java
 * Development Environment:  Apple Mac OS X 10.6.5
 *                           Eclipse 3.6 / Java 1.6.0_22
 */
package project4.spreadsheet;

import javax.swing.SwingUtilities;

public class Spreadsheet {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new SpreadsheetGUI();
      }
    });
  }
}
