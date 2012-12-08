/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Dec 4, 2010
 * Project: Project 4
 * File: MyText.java
 * Development Environment:  Apple Mac OS X 10.6.5
 *                           Eclipse 3.6 / Java 1.6.0_22
 */
package spreadsheet;

import javax.swing.JTextField;

/**
 * TODO: Documentation, clean up a little bit.  Maybe even make an interface.
 * TODO: Try to implement a better way of keeping track of cell history and
 *       the current contents/formulas.
 * 
 * @author jsmith
 *
 */
@SuppressWarnings("serial")
public class SpreadsheetCell extends JTextField {
  /**
   * My JTextField needs to have a row/col ID number so we know
   * where the cell is in the spreadsheet.  
   */
  private int row, col, uid;
  
  private String current,  // Holds the user entered formula/function
                 solution; // Holds the results 
  
  private boolean hasFormula  = false, // Holds current cell type information
                  hasFunction = false,
                  hasDouble   = false;
  
  public SpreadsheetCell() { super(); }

  public boolean hasFormula()  { return hasFormula;                    }
  public boolean hasFunction() { return hasFunction;                   }
  public boolean hasDouble()   { return hasDouble;                     }
  public boolean hasSolution() { return solution != null ? true:false; }
  public String  getCurrent()  { return current;                       }
  public String  getSolution() { return solution;                      }
  public int     getRowID()    { return row;                           }
  public int     getColID()    { return col;                           }
  public int     getUID()      { return uid;                           }
  
  public void setRowID(int i)       { row = i;                       }
  public void setColID(int i)       { col = i;                       }
  public void setUID(int i)         { uid = i;                       }
  public void setSolution(String s) { solution = s; this.setText(s); }
  
  public void setFormula(String s)  {
    current = s; 
    hasFormula = true;
    hasFunction = false;
    hasDouble = false;
  }
  
  public void setFunction(String s) {
    current = s; 
    hasFormula = false;
    hasFunction = true;
    hasDouble = false;
  }
  
  public void setDouble(String s)   {
    current = null;
    solution = s; 
    this.setText(s);
    hasFormula = false;
    hasFunction = false;
    hasDouble = true;
  }
  
  public void removeFormula() {
    this.setText(null); 
    current = null; 
    hasFormula = false;
  }
  
  public void removeSolution() {
    this.setText(null);
    solution = null;
  }
  
  public void resetCell() {
    this.setText(null);
    current = null;
    solution = null;
    hasFormula = false;
    hasFunction = false;
    hasDouble = false;
  }
}