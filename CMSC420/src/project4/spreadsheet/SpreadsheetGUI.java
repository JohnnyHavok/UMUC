/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Dec 4, 2010
 * Project: Project 4
 * File: SpreadsheetGUI.java
 * Development Environment:  Apple Mac OS X 10.6.5
 *                           Eclipse 3.6 / Java 1.6.0_22
 */
package spreadsheet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;


public class SpreadsheetGUI {
  private final int ELEMENTHEIGHT   = 20;
  private final int ELEMENTWIDTH    = 70;
  private final int SPREADSHEETSIZE = 10;
  
  private static int currentCellID = 0; // 0 is cell A1
  
  SpreadsheetGUI() {
    jframe = new JFrame();
    jframe.setTitle("Spreadsheet");
    jframe.setLocationRelativeTo(null);
    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jframe.setContentPane(buildContentPanel());
    jframe.pack();
    jframe.setVisible(true); 
  }
  
  /**
   * Methods for accessing visual elements
   */
  public static String getCellContents(int row, int col) {
    String s = cellArray[row][col].getText();
    return s;
  }
  
  public static int getCurrentCellID()       { return currentCellID; }
  
  public static void setCurrentCellID(int i) { currentCellID = i;    }
  public static void setErrorMsg(String s)   { ErrorBox.setText(s);  }
  public static void setEditString(String s) { EditBox.setText(s);   }
  public static void setEditLabel(String s)  { EditLabel.setText(s); }
  
  /**
   * Methods to build visual elements
   */
  private JPanel  buildContentPanel() {
    contentPanel = new JPanel();
    contentPanel.setLayout(new BorderLayout());
    contentPanel.add(buildEditPanel(), BorderLayout.NORTH);
    contentPanel.add(buildSpreadsheet(), BorderLayout.CENTER);
    contentPanel.add(buildErrorPanel(), BorderLayout.SOUTH);
    return contentPanel;
  }
  private JPanel  buildErrorPanel() {
    errorPanel = new JPanel();

    ErrorBox = new JTextField();
    ErrorBox.setPreferredSize(new Dimension(ELEMENTWIDTH*10, ELEMENTHEIGHT));
    ErrorBox.setEditable(false);
    errorPanel.add(ErrorBox);
    
    btExit = new JButton();
    btExit.setText("Exit");

    /**
     * Action when Exit button is pressed.  Simple ActionListener thread
     * that watches for ActionEvent from button and then disposes of the frame.
     */
    btExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) { jframe.dispose(); }
    });
    
    errorPanel.add(btExit);
    
    return errorPanel;
  }
  private JPanel  buildEditPanel() {
    editPanel = new JPanel();
    
    EditLabel = new JLabel("A1");
    EditLabel.setSize(new Dimension(ELEMENTWIDTH, ELEMENTHEIGHT));
    editPanel.add(EditLabel);
    
    EditBox = new JTextField();
    EditBox.setPreferredSize(new Dimension(ELEMENTWIDTH*10, ELEMENTHEIGHT));
    editPanel.add(EditBox);
    
    return editPanel;
  }
  private JPanel  buildSpreadsheet() {
    spreadsheet = new JPanel();
    spreadsheet.setLayout(new GridLayout(11,11));

    cellArray = new SpreadsheetCell[SPREADSHEETSIZE][SPREADSHEETSIZE];
    
    /**
     * 
     */
    CellListener changeAction = new CellListener(SPREADSHEETSIZE);

    
    JLabel label;
    LineBorder lborder = new LineBorder(Color.BLACK, 1);

    // Create the row with column titles
    for(int i = 64; i < 75; i++) {
      // int i will be converted to ASCII later
      label = new JLabel();
      if(i != 64)
        label.setText(String.valueOf((char) i));

      label.setBorder(lborder);
      label.setHorizontalAlignment(JLabel.CENTER);
      label.setPreferredSize(new Dimension(ELEMENTWIDTH, ELEMENTHEIGHT));
      spreadsheet.add(label);
    }

    // Create 10 rows each with a row title and 10 SpreadsheetCell components.
    for(int row = 0; row < cellArray.length; row++) {
      label = new JLabel();
      label.setText(String.valueOf(row+1));
      label.setBorder(lborder);
      label.setHorizontalAlignment(JLabel.RIGHT);
      label.setPreferredSize(new Dimension(ELEMENTWIDTH, ELEMENTHEIGHT));
      spreadsheet.add(label);

      for(int col = 0; col < cellArray[row].length; col++) {
        cellArray[row][col] = new SpreadsheetCell();
        cellArray[row][col].setRowID(row);
        cellArray[row][col].setColID(col);
        cellArray[row][col].setUID(SPREADSHEETSIZE * row + col);
        cellArray[row][col].setBorder(lborder);
        cellArray[row][col].addActionListener(changeAction);
        cellArray[row][col].addFocusListener(changeAction);
        spreadsheet.add(cellArray[row][col]);
      }
    }
    
    return spreadsheet;
  }

  /**
   * Swing components that need access globally
   */
  private JFrame      jframe         = null;
  private JPanel      spreadsheet    = null;
  private JPanel      contentPanel   = null;
  private JPanel      errorPanel     = null;
  private JPanel      editPanel      = null;
  private JButton     btExit         = null;

  private static JLabel              EditLabel  = null;
  private static JTextField          EditBox    = null;
  private static JTextField          ErrorBox   = null;
  private static SpreadsheetCell[][] cellArray  = null;
}
