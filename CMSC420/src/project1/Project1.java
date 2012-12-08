/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Sep 25, 2010
 * Project: Project 1
 * File: Project1.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("serial")
public class Project1 extends JFrame implements ActionListener {
  /** Constants */
  private static final boolean  DEBUG           = true;
  private static final int      FRAMEHEIGHT     = 300;
  private static final int      FRAMEWIDTH      = 810;
  private static final int      ELEMENTHEIGHT   = 20;
  private static final int      ELEMENTWIDTH    = 70;

  /**
   * REGEX  Group 1: Operators
   *        Group 2: Open Parenthesis
   *        Group 3: Close Parenthesis
   *        Group 4: Numbers
   *        Group 5: Equals Sign
   *        Group 6: Letters for column headers
   */
  private static final String REGEX = 
        "([\\+\\-\\*\\/\\^])|" +            // Matches only +, -, *, /, ^
        "(\\()|" +                          // Matches only (
        "(\\))|" +                          // Matches only )
        "([0-9]*\\.[0-9]+|[0-9]+)|" +       // Matches all double numbers
        "(\\=)|" +                          // Matches only =
        "([A-Ja-j][1-9]|[A-Ja-j]?[1][0])";  // Matches A-J, 1-10

  /** Instantiate new JFrame window */
  public static void main(String[] args) { new Project1(); }

  private Project1() {
    super();
    setSize(FRAMEWIDTH, FRAMEHEIGHT);
    setTitle("Simple Spreadsheet");
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(true);
    setContentPane(getContentPanel());
    edit.setEnabled(false);
    pack();
    setVisible(true);
  }

  /** translateCell() 
   * Takes a string formated in column-row format such as A5
   * and turns it into an integer array [row][column] that correctly gives
   * the indexes of a computer array. 
   */
  private static int[] translateCell(String s) {
    int[] location = new int[2];

    // Relates to j in all tables, being the column
    location[1] = ((Character) s.toUpperCase().charAt(0)).charValue() - 65;
    // Relates to i in all tables, being the row
    location[0] = Integer.valueOf(s.substring(1)) - 1;

    return location;
  }

  /** parseInput()
   * Parses the input and passes the operators and operands to the Calculator
   * instance. Returns a double of the value for the input expression.  It will
   * also follow and evaluate expressions in other cells anywhere in the 
   * spreadsheet.  If a cell has already been visited, marked by having a value 
   * in the lblArray it will not be recalculated.   
   */
  private static double parseInput(String input) {
    Calculator machine = new Calculator();
    Matcher matcher = Pattern.compile(REGEX).matcher(input);

    while(matcher.find()) {
      if(matcher.group(1) != null) {
        // Found an operator
        if(DEBUG) {machine.debugPrintStacks();}
        machine.addOperator(Operator.fromSymbol(matcher.group(1).toString()));
      }

      if(matcher.group(2) != null) {
        // Found open parenthesis
        if(DEBUG) {machine.debugPrintStacks();}
        machine.addGrouping(Parenthesis.OPEN);
      }

      if(matcher.group(3) != null) {
        // Found close parenthesis
        if(DEBUG) {machine.debugPrintStacks();}
        machine.addGrouping(Parenthesis.CLOSE);
      }

      if(matcher.group(4) != null) {
        // Found a digit
        if(DEBUG) {machine.debugPrintStacks();}
        machine.addOperand(Double.valueOf(matcher.group(4)));
      }

      if(matcher.group(5) != null) {
        // Found equals sign. They mean nothing to my implementation right now.
      }

      if(matcher.group(6) != null) {
        // Found cell number
        // Convert human format to machine format array indexes.
        if(DEBUG) {machine.debugPrintStacks();}
        int[] indices = translateCell(matcher.group(6));

        if(!(lblArray[indices[0]][indices[1]].getText().isEmpty())) {
          // Value has already been calculated once.
          machine.addOperand(Double.valueOf(lblArray[indices[0]][indices[1]].getText()));
        } else {
          // Value isn't there. Follow it.
          machine.addOperand(parseInput(txtArray[indices[0]][indices[1]].getText()));
        }
      }
    }
    if(DEBUG) {machine.debugPrintStacks();}
    return machine.getTotal();
  }

  /**
   * Actions to perform when a button is clicked.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == (JButton) edit) {
      if(DEBUG) {System.out.println("Button Press: Edit");}
      calcPane.setVisible(false);
      contentPane.remove(calcPane);
      contentPane.add(editPane, BorderLayout.NORTH);
      editPane.setVisible(true);
      calc.setEnabled(true);
      edit.setEnabled(false);
    }

    if(e.getSource() == (JButton) calc) {
      if(DEBUG) {System.out.println("Button Press: Calculate");}
      editPane.setVisible(false);
      contentPane.remove(editPane);
      contentPane.add(calcPane, BorderLayout.NORTH);
      calcPane.setVisible(true);
      edit.setEnabled(true);
      calc.setEnabled(false);

      for(int i = 0; i < txtArray.length; i++)
        for(int j = 0; j < txtArray[i].length; j++)
          if(txtArray[i][j].getText().isEmpty())
            lblArray[i][j].setText("0.0");
          else
            lblArray[i][j].setText(String.valueOf(parseInput(txtArray[i][j].getText())));
    }
  }

  /**
   * Methods to build visual elements
   */
  private JPanel	getContentPanel() {
    contentPane = new JPanel();
    contentPane.setLayout(new BorderLayout());
    contentPane.add(getCalcPane(), BorderLayout.NORTH);
    contentPane.add(getEditPane(), BorderLayout.NORTH);
    contentPane.add(getButtonPanel(), BorderLayout.SOUTH);
    return contentPane;
  }

  private JPanel	getButtonPanel() {
    JPanel pane = new JPanel();
    pane.setLayout(new FlowLayout(FlowLayout.CENTER));

    edit = new JButton("Edit");
    calc = new JButton("Calculate");

    edit.addActionListener(this);
    calc.addActionListener(this);

    pane.add(edit);
    pane.add(calc);

    return pane;
  }

  private JPanel	getEditPane() {
    editPane = new JPanel();
    editPane.setLayout(new GridLayout(11,11));

    txtArray = new JTextField[10][10];

    JLabel label;
    LineBorder lborder = new LineBorder(Color.BLACK, 1);

    // Create the row with column titles
    for(int i = 64; i < 75; i++) {
      // int i will be converted to ASCII later
      // explains weird starting index.
      label = new JLabel();
      if(i != 64)
        label.setText(String.valueOf((char) i));

      label.setBorder(lborder);
      label.setHorizontalAlignment(JLabel.CENTER);
      label.setPreferredSize(new Dimension(ELEMENTWIDTH, ELEMENTHEIGHT));
      editPane.add(label);
    }

    // Create 10 rows each with a row title and 10 JTextField components.
    for(int i = 0; i < txtArray.length; i++) {
      label = new JLabel();
      label.setText(String.valueOf(i+1));
      label.setBorder(lborder);
      label.setHorizontalAlignment(JLabel.RIGHT);
      label.setPreferredSize(new Dimension(ELEMENTWIDTH, ELEMENTHEIGHT));
      editPane.add(label);

      for(int j = 0; j < txtArray[i].length; j++) {
        txtArray[i][j] = new JTextField();
        txtArray[i][j].setBorder(lborder);
        txtArray[i][j].setText(String.valueOf(10*i + j));
        editPane.add(txtArray[i][j]);
      }

    }
    return editPane;
  }

  private JPanel getCalcPane() {
    JLabel label;
    LineBorder lborder = new LineBorder(Color.BLACK, 1);

    calcPane = new JPanel();
    calcPane.setLayout(new GridLayout(11,11));

    // Create the row with column titles
    for(int i = 64; i < 75; i++) {
      // int i will be converted to ASCII later
      // explains weird starting index.
      label = new JLabel();
      if(i != 64) // First box 0,0 is empty
        label.setText(String.valueOf((char) i));

      label.setBorder(lborder);
      label.setHorizontalAlignment(JLabel.CENTER);
      label.setPreferredSize(new Dimension(ELEMENTWIDTH, ELEMENTHEIGHT));
      calcPane.add(label);
    }

    lblArray = new JLabel[10][10];

    // Create 10 rows each with a row number and fill it with zeros
    for(int i = 0; i < lblArray.length; i++) {
      label = new JLabel();
      label.setText(String.valueOf(i+1));
      label.setBorder(lborder);
      label.setHorizontalAlignment(JLabel.RIGHT);
      label.setPreferredSize(new Dimension(ELEMENTWIDTH, ELEMENTHEIGHT));
      calcPane.add(label);

      for(int j = 0; j < lblArray[i].length; j++) {
        lblArray[i][j] = new JLabel();
        lblArray[i][j].setHorizontalAlignment(JLabel.RIGHT);
        lblArray[i][j].setBorder(lborder);
        calcPane.add(lblArray[i][j]);
      }
    }
    return calcPane;
  }


  /**
   * Swing components that need access globally
   */
  private JButton edit           = null;
  private JButton calc           = null;
  private JPanel  editPane       = null;
  private JPanel  calcPane       = null;
  private JPanel  contentPane    = null;

  private static JTextField[][] txtArray = null;
  private static JLabel[][]     lblArray = null;
}
