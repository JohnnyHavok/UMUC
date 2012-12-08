/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Nov 4, 2010
 * Project: Project 3
 * File: Project3.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project3;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.PriorityQueue;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;


@SuppressWarnings("serial")
public class Project3 extends JFrame implements ActionListener {
  /** Constants **/
  static final boolean  DEBUG           = true;
  static final String   OUTPUTDEFAULT   = "output.txt";
  
  /** Custom Hash Map classes for this project **/
  static ASCIIHuffmanMap hmap  = new ASCIIHuffmanMap();
  static ASCIIIntegerMap map   = new ASCIIIntegerMap();
  
  /** For JTable **/
  static Vector<Vector<String>> rowVector   = new Vector<Vector<String>>();
  static Vector<String>         columnID    = new Vector<String>();
  
  public static void main(String[] args) { new Project3(); }
  
  private Project3() {
    super();
    setTitle("Huffman Codes");
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(true);
    setContentPane(getContentPanel());
    pack();
    setVisible(true);
  }
  

  @Override
  public void actionPerformed(ActionEvent source) {
    if(source.getSource() == (JButton) exit) { this.dispose(); }
    
    if(source.getSource() == (JButton) loadFile) {
      JFileChooser jfc = new JFileChooser();
      int result = jfc.showOpenDialog(this);
      if(result == JFileChooser.APPROVE_OPTION) {
        if(DEBUG)
          System.out.println("File Load Selected: " + jfc.getSelectedFile());
        
        try {
          FileReader in = new FileReader(jfc.getSelectedFile());
          int k;
          
          /**
           * For each character read in from the file increment the statically
           * defined IntegerASCIIMap map.
           */
          while((k = in.read()) != -1)
            map.incrementKeyCount(k);
          
          in.close();
          
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }

        // Build a Huffman Code hash map from a Huffman Tree
        // Tree is derived from the ASCII/Frequency map.
        buildHuffmanMap(buildHuffmanTree(map));
        
        if(DEBUG) {
          for(int i = 0; i < hmap.size(); i++) {
            if(hmap.getCode(i) != null)
              System.out.println( getASCIIString(i)+":"
                                  + map.getKeyCount(i)+"->"
                                  + hmap.getCode(i) );
          }
        }
        
        printTable();
        
        encodeFile(jfc.getSelectedFile(), new File(txtOutputFile.getText()));
      }   
    } //END ACTION FOR BUTTON: loadFile    
  }
  
  /**
   * buildHuffmanTree():  Takes all non-zero frequency ASCII codes in the 
   * IntegerASCIIMap passed and creates a HuffmanNode for each.  Adds each
   * HuffmanLeaf into the PriorityQueue.  Then using a simple algorithm
   * combines the smallest weighted nodes continuously until a full tree is
   * made.
   * @param map : IntegerASCIIMap 
   * @return HuffmanNode : Represents the root of the HuffmanTree
   */
  private HuffmanNode buildHuffmanTree(ASCIIIntegerMap map) {
    PriorityQueue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>(128);
    HuffmanNode n;
    
    for(int i = 0; i < map.size(); i++) {
      if(map.getKeyCount(i) > 0) {
        // Creates a HuffmanLeaf for each non-zero frequency ASCII Code
        n = new HuffmanLeaf(i, map.getKeyCount(i));
        pq.add(n);
      }
    }
    
    if(pq.size() == 0)
      return null;
    
    if(pq.size() == 1)
      return pq.poll();
    
    HuffmanNode leaf1, leaf2, head;
    
    while(pq.size() > 2) {
      leaf1 = pq.poll();
      leaf2 = pq.poll();
      head = new HuffmanRoot(leaf1.getWeight()+leaf2.getWeight(), leaf1, leaf2);   
      pq.add(head);
      
      if(DEBUG) {
        System.out.print("L1:"+leaf1.getWeight());
        if(leaf1 instanceof HuffmanLeaf) 
          System.out.print(" ("+((HuffmanLeaf) leaf1).getInt()+")");
        else
          System.out.print(" (Not Child)");
        System.out.print(" L2:"+leaf2.getWeight());
        if(leaf2 instanceof HuffmanLeaf) 
          System.out.println(" ("+((HuffmanLeaf) leaf2).getInt()+")");
        else
          System.out.println(" (Not Child)");
      }
      
    } // pq.size() == 2
    
    leaf1 = pq.poll();
    leaf2 = pq.poll();
    return new HuffmanRoot(leaf1.getWeight()+leaf2.getWeight(), leaf1, leaf2);
  }
  
  /**
   * buildHuffmanMap():  Cover method for the recursive method
   * specialPostOrderWalk().  Requires a completed Huffman tree be passed
   * to it.  It passes the tree and a StringBuilder object to the recursive
   * method.
   * @param tree : A completed Huffman Tree
   */
  private void buildHuffmanMap(HuffmanNode tree) {
    StringBuilder sb = new StringBuilder();
    specialPostOrderWalk(tree, sb);
  }
  
  /**
   * specialPostOrderWalk():  Recursive method that does a post order walk of
   * the Huffman tree.  Special because it does not attempt to process anything
   * at a root node, a node that has a weight but no ASCII data.  Each time it
   * calls itself to go left or right it appends a 0 or 1 to the StringBuilder.
   * When it gets to a leaf node it adds that String to a HashMap (hmap) which
   * must be defined statically for the entire class.  As it backs out a branch
   * of the tree it removes the last 0 or 1 on the String.
   * @param tree
   * @param sb
   */
  private void specialPostOrderWalk(HuffmanNode tree, StringBuilder sb) {
    if(tree instanceof HuffmanRoot) {
      specialPostOrderWalk(((HuffmanRoot) tree).getLeft(), sb.append('0'));
      sb = sb.deleteCharAt(sb.length() - 1);
    }
    
    if(tree instanceof HuffmanRoot) {
      specialPostOrderWalk(((HuffmanRoot) tree).getRight(), sb.append('1'));
      sb = sb.deleteCharAt(sb.length() - 1);
    }
    
    if(tree instanceof HuffmanLeaf) {
      hmap.setCode(((HuffmanLeaf) tree).getInt(), sb.toString());
    }
  }

  /**
   * printTable():  Takes the two completed statically defined maps and 
   * updates the GUI elements.
   */
  private void printTable() {
    Vector<String> t;
    for(int i = 0; i < map.size(); i++) {
      if(map.getKeyCount(i) == 0)
        continue;
      
      t = new Vector<String>();
      t.add(getASCIIString(i));
      t.add(String.valueOf(map.getKeyCount(i)));
      t.add(hmap.getCode(i));
      
      rowVector.add(t);
    }
    codeTable.addNotify();
  }
  
  /**
   * encodeFile():  Reopen the selected file, read it in character by character
   * and for each character write the HuffmanCode of that character into the 
   * selected output file.  Draw back of the program is that I close/unlock
   * the input file between creating the Huffman map and encoding into the 
   * output file so the state/contents of the input file could change.
   * @param input : File handle for the input file
   * @param output : File handle for the output file
   */
  private void encodeFile(File input, File output) {
    try {
      FileReader in = new FileReader(input);
      FileWriter out = new FileWriter(output);
      int k;
      
      while((k = in.read()) != -1)
        out.write(hmap.getCode(k));
      
      in.close();
      out.close();
      
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }    
  }
  
  /**
   * getASCIIString(): Translate an integer into a human readable ASCII string.
   * Also makes unprintable characters like BELL readable on the screen.
   * @param code : ASCII Code in integer format
   * @return String : String representation of  the ASCII code
   */
  private String getASCIIString(int code) {
    if(code > 128) return null;
    
    // Handle not so easy characters to print.
    switch(code) {
      case 0:   return "NUL";
      case 1:   return "SOH";
      case 2:   return "STX";
      case 3:   return "ETX";
      case 4:   return "EOT";
      case 5:   return "ENQ";
      case 6:   return "ACK";
      case 7:   return "BEL";
      case 8:   return "BS";
      case 9:   return "TAB";
      case 10:  return "NL";
      case 11:  return "VT";
      case 12:  return "FF";
      case 13:  return "CR";
      case 14:  return "SO";
      case 15:  return "SI";
      case 16:  return "DLE";
      case 17:  return "DC1";
      case 18:  return "DC2";
      case 19:  return "DC3";
      case 20:  return "DC4";
      case 21:  return "NAK";
      case 22:  return "SYN";
      case 23:  return "ETB";
      case 24:  return "CAN";
      case 25:  return "EM";
      case 26:  return "SUB";
      case 27:  return "ESC";
      case 28:  return "FS";
      case 29:  return "GS";
      case 30:  return "RS";
      case 31:  return "US";
      case 32:  return "SP";
      case 127: return "DEL";
      default: 
        return String.valueOf((char) code);
    }    
  }
  
  
  
  
  
  /**
   * Methods to build visual elements
   */
  private JPanel getContentPanel() {
    contentPane = new JPanel();
    contentPane.setLayout(new BorderLayout());
    contentPane.add(getTablePane(), BorderLayout.NORTH);
    contentPane.add(getFilePane(), BorderLayout.CENTER);
    contentPane.add(getButtonPane(), BorderLayout.SOUTH);
    return contentPane;
  }
  
  private JPanel getTablePane() {
    tablePane = new JPanel();
    tablePane.add(getTableScrollPane());
    return tablePane;
  }
  
  private JScrollPane getTableScrollPane() {
    tableScrollPane = new JScrollPane();
    tableScrollPane.setViewportView(getCodeTable());
    return tableScrollPane;
  }
  
  private JTable getCodeTable() {
    columnID.add("ASCII");
    columnID.add("Frequency");
    columnID.add("Huffman Code");
    
    DefaultTableModel model = new DefaultTableModel();
    model.setDataVector(rowVector, columnID);
    
    codeTable = new JTable(model);
    codeTable.setPreferredScrollableViewportSize(new Dimension(410,325));
    codeTable.setRowSelectionAllowed(true);
    
    return codeTable;
  }
  
  private JPanel getButtonPane() {
    buttonPane = new JPanel();
    loadFile = new JButton("Load File");
    exit = new JButton("Exit");
    loadFile.addActionListener(this);
    exit.addActionListener(this);
    buttonPane.add(loadFile);
    buttonPane.add(exit);
    return buttonPane;
  }
  
  private JPanel getFilePane() {
    filePane = new JPanel();
    filePane.add(new JLabel("Output File:"));
    txtOutputFile = new JTextField("output.txt");
    txtOutputFile.setColumns(25);
    filePane.add(txtOutputFile);
    return filePane;
  }
  
  /** Swing Elements */
  private JPanel      contentPane     = null;
  private JPanel      buttonPane      = null;
  private JPanel      tablePane       = null;
  private JPanel      filePane        = null;
  private JButton     loadFile        = null;
  private JButton     exit            = null;
  private JTable      codeTable       = null;
  private JTextField  txtOutputFile   = null;
  private JScrollPane tableScrollPane = null;
}