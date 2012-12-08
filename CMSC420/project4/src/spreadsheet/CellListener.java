/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Dec 4, 2010
 * Project: Project 4
 * File: CellListener.java
 * Development Environment:  Apple Mac OS X 10.6.5
 *                           Eclipse 3.6 / Java 1.6.0_22
 */

package spreadsheet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import expressiontree.*;
import simplegraph.*;

public class CellListener implements ActionListener, 
                                     FocusListener    {
  
  private int SPREADSHEETSIZE;
  
  
  /**
   * TODO: Investigate a way to do a byte by byte character reading process
   *       instead of static regular expressions like these.
   * TODO: Break this up, make this class just handle calls to another class(s)
   *       that do all the work.  Think information expert but try to avoid
   *       lasagna code. 
   */
  private static final String TYPE_REGEX =
    "(\\\")|"                          +  // Matches input type literal string
    "(\\+)|"                           +  // Matches input type formula
    "(\\=)|"                           +  // Matches input type function
    "([0-9]*\\.[0-9]+|[0-9]+)|"        +  // Matches all double numbers
    "([A-Ja-j][1-9]|[A-Ja-j]?[1][0])"  ;  // Matches A-J, 1-10
  
  private static final String FORMULA_REGEX =
    "([\\+\\-\\*\\/\\^])|"             +  // Matches only +, -, *, /, ^
    "(\\()|"                           +  // Matches only (
    "(\\))|"                           +  // Matches only )
    "([0-9]*\\.[0-9]+|[0-9]+)|"        +  // Matches all double numbers
    "([A-Ja-j][1-9]|[A-Ja-j]?[1][0])|" +  // Matches A-J, 1-10
    "(.+)"                             ;  // Matches everything else
  
  private static final String FUNCTION_REGEX =
    "(MIN)|" +      // Matches literal MIN
    "(MAX)|" +      // Matches literal MAX
    "(AVG)"  ;      // Matches literal AVG
  
  private static final String RANGE_REGEX = 
    // Strict regex that matches exactly "(A1:B2)" type strings
    "((\\({1})"                                  +                        
    "([A-Ja-j][1-9]|[A-Ja-j]?[1][0]{1})"         +
    "(\\:{1})"                                   +
    "([A-Ja-j][1-9]|[A-Ja-j]?[1][0]{1})"         +
    "(\\){1}))"                                  ;
  
  private static final Pattern typeRegex      = Pattern.compile(TYPE_REGEX);
  private static final Pattern formulaRegex   = Pattern.compile(FORMULA_REGEX);
  private static final Pattern functionRegex  = Pattern.compile(FUNCTION_REGEX);
  private static final Pattern cellRangeRegex = Pattern.compile(RANGE_REGEX);

  private static SimpleGraph<Integer, SpreadsheetCell> graph 
                                  = new SimpleGraph<Integer, SpreadsheetCell>();
  
  public CellListener(int size) { SPREADSHEETSIZE = size; }
  
  
  private void parseInput(SpreadsheetCell cell)
                                             throws InvalidInputException {
    String inputString = cell.getText().trim();
    Matcher matcher = typeRegex.matcher(inputString);

    if(matcher.find()) { // Looking for operation identifying tokens.
      
      if(matcher.group(1) != null) { // Found a string
        if(graph.contains(cell.getUID()))
          graph.clearVertexEdges(cell.getUID()); // Clear all edges, new input.
        
        cell.resetCell();
        cell.setText(inputString);
        
      } else if(matcher.group(2) != null) { // Found a formula
        
        if(cell.hasFormula()) {
          if(!cell.getCurrent().equals(inputString)) { // New input
            updateGraph(cell);
            graph.clearVertexEdges(cell.getUID());
            double d = evalFormula(cell);
            cell.resetCell();
            cell.setFormula(inputString);
            cell.setSolution(String.valueOf(d));
            
            if(graph.hasIndegree(cell.getUID()))
              try {
                refactorList(graph.topsort());
              } catch (CycleException e) {
                throw new InvalidInputException("There is a cycle ");
              }
          }
        } else {
          updateGraph(cell);
          graph.clearVertexEdges(cell.getUID());
          double d = evalFormula(cell);
          cell.resetCell();
          cell.setFormula(inputString);
          cell.setSolution(String.valueOf(d));
          
          if(graph.hasIndegree(cell.getUID()))
            try {
              refactorList(graph.topsort());
            } catch (CycleException e) {
              throw new InvalidInputException("There is a cycle ");
            }
        }
        
      } else if(matcher.group(3) != null) { // Found a function
        
        if(cell.hasFunction()) {
          if(!cell.getCurrent().equals(inputString)) { // New input
            updateGraph(cell);
            graph.clearVertexEdges(cell.getUID());
            double d = evalFunction(cell);
            cell.resetCell();
            cell.setFormula(inputString);
            cell.setSolution(String.valueOf(d));
            
            if(graph.hasIndegree(cell.getUID()))
              try {
                refactorList(graph.topsort());
              } catch (CycleException e) {
                throw new InvalidInputException("There is a cycle ");
              }
          }
        } else {
          updateGraph(cell);
          graph.clearVertexEdges(cell.getUID());
          double d = evalFunction(cell);
          cell.resetCell();
          cell.setFormula(inputString);
          cell.setSolution(String.valueOf(d));
          
          if(graph.hasIndegree(cell.getUID()))
            try {
              refactorList(graph.topsort());
            } catch (CycleException e) {
              throw new InvalidInputException("There is a cycle ");
            }
        }
        
      } else if(matcher.group(4) != null) { // Found a single number
        double d;
        
        try {
           d = Double.valueOf(inputString);
        } catch (NumberFormatException e) { // Catches input ex: 1.032h
          throw new InvalidInputException("The input was invalid");
        }
        
        if(cell.hasSolution()) {
          if(d != Double.valueOf(cell.getSolution())) {
            updateGraph(cell);
            graph.clearVertexEdges(cell.getUID());
            cell.resetCell();
            cell.setDouble(inputString);
            
            if(graph.hasIndegree(cell.getUID()))
              try {
                refactorList(graph.topsort());
              } catch (CycleException e) {
                throw new InvalidInputException("There is a cycle ");
              }
          }
        } else {
          updateGraph(cell);
          graph.clearVertexEdges(cell.getUID());
          cell.resetCell();
          cell.setDouble(inputString);
          
          if(graph.hasIndegree(cell.getUID()))
            try {
              refactorList(graph.topsort());
            } catch (CycleException e) {
              throw new InvalidInputException("There is a cycle ");
            }
        }
   
      } else if(matcher.group(5) != null) { // Found a reference
        /**
         * TODO: Fix formula hack.
         */
        int[] dstID = stringToCellID(matcher.group(5));
        
        if(!graph.contains(dstID[2]))
          throw new InvalidInputException("Cell reference is empty ");
        
        SpreadsheetCell dstCell = graph.getVertex(dstID[2]).getReference();
        
        try {
          graph.addEdge(cell.getUID(), dstID[2]);
        } catch(VertexDoesNotExist e) {
          e.printStackTrace(); // Should be unreachable
        }
        
        if(!dstCell.hasSolution())
          throw new InvalidInputException("Cell reference has no value ");
        
        updateGraph(cell);
        graph.clearVertexEdges(cell.getUID());
        cell.resetCell();
        cell.setFormula("+"+matcher.group(5)+"+0"); // Hack
        cell.setSolution(dstCell.getSolution());
        
        if(graph.hasIndegree(cell.getUID()))
          try {
            refactorList(graph.topsort());
          } catch (CycleException e) {
            throw new InvalidInputException("There is a cycle ");
          }
      }
      
    } else {
      throw new InvalidInputException("The input was invalid");
    }
    SpreadsheetGUI.setErrorMsg(null);
  } // End parseInput()
  
  private double evalFormula(SpreadsheetCell cell) 
                                              throws InvalidInputException {
    
    ExpressionTree etree = new ExpressionTree();
    
    // Get rid of formula token identifier and start matching
    String inputString = cell.getText().trim().substring(1); 
    Matcher matcher = formulaRegex.matcher(inputString);    
    
    while(matcher.find()) {
      boolean error = true;
      
      if(matcher.group(1) != null) {
        // Found operator  
        try {
          etree.addOperator(Operator.fromSymbol(matcher.group(1).toString()));
        } catch (ExpressionException e) {
          throw new InvalidInputException(e.getMessage());
        }
        error = false;
        
      } else if(matcher.group(2) != null) {
        // Found (
        etree.addGrouping(Parenthesis.OPEN);
        error = false;
        
      } else if(matcher.group(3) != null) {
        // Found )
        etree.addGrouping(Parenthesis.CLOSE);
        error = false;
        
      } else if(matcher.group(4) != null) {
        // Found double
        try {
          etree.addOperand(Double.valueOf(matcher.group(4)));
        } catch (NumberFormatException e) {
          throw new InvalidInputException("Error with number " 
              + matcher.group(4));
        }
        error = false;
        
      } else if(matcher.group(5) != null) { 
        // Found reference
        int[] dstID = stringToCellID(matcher.group(5));
        
        if(!graph.contains(dstID[2]))
          throw new InvalidInputException("Cell reference is empty ");
        
        SpreadsheetCell dstCell = graph.getVertex(dstID[2]).getReference();
        
        try {
          graph.addEdge(cell.getUID(), dstID[2]);
        } catch(VertexDoesNotExist e) {
          e.printStackTrace(); // Should be unreachable
        }
        
        if(!dstCell.hasSolution())
          throw new InvalidInputException("Cell reference has no value ");
        
        etree.addOperand(Double.valueOf(dstCell.getSolution()));
        
        error = false;
      } 
      
      
      if(error) {
        throw new InvalidInputException("Error with input near " +
                                          matcher.group(6) );
      }
      
    }

    /**
     * Done parsing this formula, get the total from the expression tree.
     */
    try {
      return etree.getTotal();
    } catch (ExpressionException e) {
      throw new InvalidInputException(e.getMessage());
    }
  } // End evalFormula()
  
  private double evalFunction(SpreadsheetCell cell)  
                                          throws InvalidInputException {
    
    // Get rid of function token identifier and start matching
    String inputString = cell.getText().trim().substring(1); 
    Matcher matcher = functionRegex.matcher(inputString);
        
    int[] startID      = null; // Start of the function limit
    int[] endID        = null; // End of the function limit

    if(matcher.find()) {
      Matcher rangeMatcher = cellRangeRegex.matcher(inputString.substring(3));
      
      if(matcher.group(1) != null) { // Found MIN
        if(!rangeMatcher.matches()) 
          throw new InvalidInputException("There is an error in MIN " +
                                          "function input ");
        
        startID = stringToCellID(rangeMatcher.group(3));
        endID   = stringToCellID(rangeMatcher.group(5));
        
        
        if(endID[2] < startID[2]) // Cells are out of order
          throw new InvalidInputException("There is an error in MIN " +
                                          "function near : , check order ");
        
        double cur;
        double min = Double.valueOf(
                       graph.getVertex(startID[2]).getReference().getText() );
        
        for(int i = startID[2] + 1; i <= endID[2]; i++) {
          try {
            cur = Double.valueOf(graph.getVertex(i).getReference().getText());
          } catch (Exception e) {
            throw new InvalidInputException("A cell you in your MIN function"+
                                            "does not contain a number");
          }
          
          if(min > cur)
            min = cur;
        }
        // Update graph to include all new dependencies 
        updateGraphWithRange(cell, startID, endID);
        return min;        

      } else if(matcher.group(2) != null) { // Found MAX
        if(!rangeMatcher.matches())
          throw new InvalidInputException("There is an error in MAX " +
                                          "function input ");

        startID = stringToCellID(rangeMatcher.group(3));
        endID   = stringToCellID(rangeMatcher.group(5));

        if(endID[2] < startID[2]) // Cells are out of order
          throw new InvalidInputException("There is an error in MAX " +
                                          "function near : , check order ");

        double cur;
        double max = Double.valueOf(
                       graph.getVertex(startID[2]).getReference().getText() );
        
        for(int i = startID[2] + 1; i <= endID[2]; i++) {
          try {
            cur = Double.valueOf(graph.getVertex(i).getReference().getText());
          } catch (Exception e) {
            throw new InvalidInputException("A cell you in your MAX function "+
                                            "does not contain a number");
          }
          
          if(max < cur)
            max = cur;
        }
        
        // Update graph to include all new dependencies 
        updateGraphWithRange(cell, startID, endID);
        return max;
          
      } else if(matcher.group(3) != null) { // Found AVG
        /**
         * TODO:  Fix AVG function.  It does not appear to work for simple 
         * input such as =AVG(A1:B1) where A1 and B1 just contain numbers.
         */
        if(!rangeMatcher.matches())
          throw new InvalidInputException("There is an error in AVG " +
                                          "function input ");
     
        startID = stringToCellID(rangeMatcher.group(3));
        endID   = stringToCellID(rangeMatcher.group(5));
        
        
        if(endID[2] < startID[2]) // Cells are out of order
          throw new InvalidInputException("There is an error in AVG " +
                                          "function near : , check order ");
        
        double count = 0;
        double sum   = 0;
        
        for(int i = startID[2] + 1; i <= endID[2]; i++) {
          try {
            sum += Double.valueOf(
                                 graph.getVertex(i).getReference().getText() );
          } catch (Exception e) {
            throw new InvalidInputException("A cell you in your AVG function "+
                                            "does not contain a number");
          }
          count++;
        }
        // Update graph to include all new dependencies 
        updateGraphWithRange(cell, startID, endID);
        return (sum/count);
      }
         
    }

    throw new InvalidInputException("There is an error in your function " +
                                    "input ");
  } // End evalFunction()
  
  private void refactorList(Queue<Vertex<SpreadsheetCell>> q) {
    SpreadsheetCell cell;
    
    /**
     * TODO:  Find the bug that prevents the refactor from working correctly
     */

    while(q.peek() != null) {
      cell = q.poll().getReference();
      if(cell.hasFormula())
        cell.setText(cell.getCurrent());
      if(cell.hasFunction())
        cell.setText(cell.getCurrent());
      
      try {
        System.out.println("CELL ID"+cell.getUID()+"->"+cell.getSolution());
        parseInput(cell);
        
      } catch (InvalidInputException e) {
        // Should be unreachable, cells have already been parsed once.
        e.printStackTrace(); 
      }
    }
  }
  
  private void updateGraphWithRange(SpreadsheetCell cell, int[] startID, 
                                                          int[] endID)    {
    int vsrc = cell.getUID();
    int[] current = startID;
    
    while(current[2] != endID[2]) {
      if(current[1] <= current[0] * 10 + 9) { // Row limit of advance.
        try {
          graph.addEdge(vsrc, current[2]);
        } catch (VertexDoesNotExist e) {
          e.printStackTrace(); // Should not occur.
        }
        
        // Increment Column and UID to next cell.
        current[1]++;
        current[2]++;
      } else { // Move down row.
        current[0]++;
        current[1] = current[0] * 10;
        current[2] = current[1];
      }
    }
  }
  
  private void updateGraph(SpreadsheetCell cell) {
    if(!graph.contains(cell.getUID()))
      try {
        graph.addVertex(cell.getUID(), new Vertex<SpreadsheetCell>(cell));
      } catch (Exception e) {
        e.printStackTrace();
      }
  }
  
  private int[] stringToCellID(String s) {   
    int[] location = new int[3];
    location[0] = Integer.valueOf(s.substring(1)) - 1; // The row
    location[1] = ((Character) s.toUpperCase().charAt(0)).charValue() - 65;
    location[2] = SPREADSHEETSIZE * location[0] + location[1]; // CellID
    return location;
  }
  
  /**
   * This public gateway method allows other listeners to update
   * TODO: Break listeners into smaller classes and make a CellEvaluator class
   *       to do this work
   */
  public void sideGateway(SpreadsheetCell cell) { 
    if(cell.getText() != null)
      if(cell.getText().trim().length() > 0)
        try {
          parseInput(cell);
        } catch(InvalidInputException e) {
          int col = cell.getColID()+65;
          int row = cell.getRowID()+1;
          SpreadsheetGUI.setErrorMsg(e.getMessage() + " at " + (char)col + row);
          cell.requestFocus();
        }
  }
  
  @Override
  public void actionPerformed(ActionEvent ae) {
    SpreadsheetCell cell = (SpreadsheetCell) ae.getSource();
    
    if(cell.getText() != null)
      if(cell.getText().trim().length() > 0)
        try {
          parseInput(cell);
        } catch(InvalidInputException e) {
          int col = cell.getColID()+65;
          int row = cell.getRowID()+1;
          SpreadsheetGUI.setErrorMsg(e.getMessage() + " at " + (char)col + row);
          cell.requestFocus();
        }
  }

  @Override
  public void focusLost(FocusEvent fe) {
    SpreadsheetCell cell = (SpreadsheetCell) fe.getSource();
    
    if(cell.getText() != null)
      if(cell.getText().trim().length() > 0)
        try {
          parseInput(cell);
        } catch(InvalidInputException e) {
          int col = cell.getColID()+65;
          int row = cell.getRowID()+1;
          SpreadsheetGUI.setErrorMsg(e.getMessage() + " at " + (char)col + row);
          cell.requestFocus();
        }
  }
  
  @Override
  public void focusGained(FocusEvent fe) {
    SpreadsheetCell cell = (SpreadsheetCell) fe.getSource();
    SpreadsheetGUI.setCurrentCellID(cell.getUID());
    int col = cell.getColID()+65;
    int row = cell.getRowID()+1;
    SpreadsheetGUI.setEditLabel(String.valueOf((char)col) + row);
    if(cell.getCurrent() != null)
      SpreadsheetGUI.setEditString(cell.getCurrent());
    else
      SpreadsheetGUI.setEditString("");
  }
}