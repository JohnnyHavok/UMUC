/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Oct 16, 2010
 * Project: Project 2
 * File: Project2.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project2;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Project2 extends JFrame implements ActionListener {
  /** Constants */
  private static final int      TEXTBOXHEIGHT   = 30;
  private static final int      TEXTBOXWIDTH    = 15;
  
  /**
   * REGEX  Group 1: Operators
   *        Group 2: Open Parenthesis
   *        Group 3: Close Parenthesis
   *        Group 4: Numbers
   *        Group 5: Variable Letters
   */
  private static final String REGEX = 
        "([\\+\\-\\*\\/\\^])|" +            // Matches only +, -, *, /, ^
        "(\\()|" +                          // Matches only (
        "(\\))|" +                          // Matches only )
        "([0-9]*\\.[0-9]+|[0-9]+)|" +       // Matches all double numbers
        "([A-Za-z])";                       // Matches variable letters


  public static void main(String[] args) { new Project2(); }
  
  
  private Project2() {
    super();
    setTitle("Expression Builder");
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(true);
    setContentPane(getContentPanel());
    pack();
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == (JButton) exit)
      this.dispose();
    
    if(e.getSource() == (JButton) generate) {
      NodeType tree = parseInput(input.getText());
      zeroAddASM(tree);
      oneAddASM(tree);
      twoAddASM(tree);
      threeAddASM(tree);
    }
    
  }
  
  private static NodeType parseInput(String input) {
    Matcher matcher = Pattern.compile(REGEX).matcher(input);
    
    ExpressionTreeBuilder builder = new ExpressionTreeBuilder();

    /**
     * Parses input based on the regular expression defined earlier.
     */
    while(matcher.find()) {
      if(matcher.group(1) != null) {   // Found an operator
        Operator op = Operator.fromString(matcher.group(1));
        builder.addOperator(new OperatorNode(op));
        
      } if(matcher.group(2) != null) { // Found open parenthesis
        builder.addGrouping(new OperatorNode(Parenthesis.OPEN));
        
      } if(matcher.group(3) != null) { // Found closed parenthesis
        builder.addGrouping(new OperatorNode(Parenthesis.CLOSE));  
        
      } if(matcher.group(4) != null) { // Found a number
        OperandNode curOp = new OperandNode(Double.valueOf(matcher.group(4)));
        builder.addOperand(curOp);
        
      } if(matcher.group(5) != null) { // Found a variable name.
        VariableNode curOp = new VariableNode(matcher.group(5));
        builder.addVariable(curOp);
        
      } // End if
    } // End while
    
    return builder.getExpressionTree();
  }
  
  private static void zeroAddASM(NodeType root) {
    textArea[0].setText("0-Address\n----------\n");
    Stack<NodeType> stack = postOrderStack(root);
    NodeType n;
    while(!stack.isEmpty()) {
      n = stack.pop();
      if(n.getClass() == OperatorNode.class) {
        textArea[0].append(((OperatorNode) n).getASMOp() + "\n");
      } else {
        textArea[0].append("PUSH " + n.toString() + "\n");
      }
    }
  }
  
  private static void oneAddASM(NodeType root) {
    textArea[1].setText("1-Address\n----------\n");
    
    textArea[1].append("Not implemented yet");
    
  }
  
  private static void twoAddASM(NodeType root) {
    textArea[2].setText("2-Address\n----------\n");
    
    textArea[2].append("Not implemented yet");
    
  }
  
  private static void threeAddASM(NodeType root) {
    textArea[3].setText("3-Address\n----------\n");
    
    Stack<NodeType> stack = postOrderStack(root);
    Stack<NodeType> opStack = new Stack<NodeType>();
    
    int RC = 0;
    
    NodeType tmpNode;
    
    while(!stack.isEmpty()) {
      tmpNode = stack.pop();
      if(tmpNode.getClass() == OperatorNode.class) {
        textArea[3].append(((OperatorNode) tmpNode).getASMOp());
        
        NodeType t1 = opStack.pop();
        NodeType t2 = opStack.pop();
        
        /**
         * If the farthest back (t2) in the stack is a register node then that
         * register number will be reused and the lowest.  Else, if t1 is a 
         * register node then that will be reused.  If neither are a register
         * node then use a new register.
         */
        if(t2.getClass() == RegisterNode.class) {
          opStack.push(t2);
          textArea[3].append(" R" + t2.toString() + ", R" + t2.toString());
          if(t1.getClass() == RegisterNode.class) {
            textArea[3].append(", R" + t1.toString() + "\n");
            RC--;
          }
          else {
            textArea[3].append(", " + t1.toString() + "\n");
          }
          
        } else if(t1.getClass() == RegisterNode.class) {
          opStack.push(t1);
          textArea[3].append(" R" + t1.toString() + ", " + t2.toString() 
                             + ", " + t1.toString() + "\n");
          
        } else {
          opStack.push(new RegisterNode(RC));
          textArea[3].append(" R" + RC + ", " + t2.toString() + ", "
                                              + t1.toString() + "\n");
          RC++;
        }
        
      } else { // Non OperatorNodes just get pushed
        opStack.push(tmpNode);
      }
    }
  }
  
  /**
   * Converts an expression tree into a post ordered stack.
   * @param root : Root of the expression node
   * @return : Returns a post ordered stack of the tree.
   */
  private static Stack<NodeType> postOrderStack(NodeType root) {
    Stack<NodeType> stk1 = new Stack<NodeType>(),
                    stk2 = new Stack<NodeType>();
    
    stk1.push(root);
    NodeType tmp;
    while(!stk1.isEmpty()) {
      tmp = stk1.pop();
      
      NodeType left = tmp.getLeft();
      if(left != null)
        stk1.push(left);
      
      NodeType right = tmp.getRight();
      if(right != null) 
        stk1.push(right);
      
      stk2.push(tmp);
     
    }
    return stk2;
  }
  
  /**
   * Methods to build visual elements
   */
  private JPanel getContentPanel() {
    contentPane = new JPanel();
    contentPane.setLayout(new BorderLayout());
    contentPane.add(getInputPanel(), BorderLayout.NORTH);
    contentPane.add(getAddressPanel(), BorderLayout.CENTER);
    contentPane.add(getButtonPanel(), BorderLayout.SOUTH);
    return contentPane;
  }
  
  private JPanel getInputPanel() {
    inputPanel = new JPanel();
    inputPanel.add(new JLabel("Expression: "));
    input = new JTextField();
    input.setColumns(25);
    inputPanel.add(input);
    return inputPanel;
  }
  
  private JPanel getButtonPanel() {
    buttonPanel = new JPanel();
    exit = new JButton("Exit");
    generate = new JButton("Run");
    exit.addActionListener(this);
    generate.addActionListener(this);
    buttonPanel.add(generate);
    buttonPanel.add(exit);
    return buttonPanel;
  }
  
  private JPanel getAddressPanel() {
    addressPanel = new JPanel();
    textArea = new JTextArea[4];
    for(int i = 0; i < 4; i++) {
      textArea[i] = new JTextArea();
      textArea[i].setColumns(TEXTBOXWIDTH);
      textArea[i].setRows(TEXTBOXHEIGHT);
      textArea[i].setEditable(false);
      addressPanel.add(textArea[i]);
    }
    return addressPanel;
  }
  
  /**
   * Swing components that need access globally
   */
  private JPanel  contentPane     = null;
  private JPanel  inputPanel      = null;
  private JPanel  buttonPanel     = null;
  private JPanel  addressPanel    = null;
  
  private JButton exit  = null;
  private JButton generate  = null;
  
  private static JTextArea[] textArea = null;
  
  private JTextField input      = null;
}
