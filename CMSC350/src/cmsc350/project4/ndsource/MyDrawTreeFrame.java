package cmsc350.project4.ndsource;

//File: MyDrawTreeFrame.java
//Author: Nicholas  Duchon
//Date:   4/9/03, updated Nov 1, 2012
//Problem:  A frame to show a graphic display of a tree structure.
// Notes:
//   a. To use this class, create a tree node class that implements the MyTreeIF interface.
//   b. Create and/or edit a tree of nodes which implement that interface.
//   c. Instantiate the class MyDrawTreeFrame, passing the root node as the parameter.
//   d. See the code in TestTree.java for an example of how to use this class.
//   e. This class will use different fonts, different font sizes.
//   f. For an example of using this code, see the associated file TestTree.java
//   g. This code will also support:
//         1. Copying the display to the system clipboard (copy/paste).
//         2. Printing the display on a printer.
//         3. Creating an SVG (scalable vector graphics) file.
//            Uses internet files from w3.org
//   h. Uses JButton as the primary node display elements.
//   i. MyLine extends JComponent to take advantage of JComponent drawing defaults.
//   j. Classes and methods:
//         > MyDrawTreeFrame
//              - MyDrawTreeFrame (MyTreeIF)
//              - MyDrawTreeFrame (MyTreeIF, String) // display frame lable
//              - MyDrawTreeFrame (MyTreeIF, String, boolean) // closeOnExit?
//              - setupDisplay (MyTreeIF, String)
//              - paint (Graphics)
//              - actionPerformed (ActionEvent)
//         > MyTreePanel
//              - MyTreePanel (MyTreeIF)
//              - setMyFont (Font)
//              - copyTree (MyTreeIF, MyTreeNode) // MyTreeNode is internal to this file
//              - walkTree (MyTreeNode, int, int) // location of node recursive method
//              - doLocations (Graphics2D)        // compute based on fonts
//              - paint (graphics)                // checks printing and valid display
//              - draw (Graphics2D)               // do update graphics
//              - drawOnGraphics (Graphics2D)     // used by printing
//              - drawSVG (Graphics2D)            // used to create SVG file
//              - print (Graphics, PageFormat, int) // used to print page
//              - printMe ()                      // print dialog
//              - sendToClipboard ()              // name says it
//              - sendToSVG ()                    // svg dialog
//         > MyTreeNode extends JButton
//              - MyTreeNode (String, MyTreeNode) // constructor, String is button label
//              - addChild (MyTreeNode)           // adds children to this node
//              - paintSVG (Graphics2D)           // draws button on SVG
//              - paintAbsolute (Graphics)        // locations used for clipboard display
//         > MyLine extends JComponent
//              - MyLine (MyTreeNode, MyTreenode) // line between centers of nodes
//              - updateLine ()                   // set bounds for this component
//              - paint (Graphics)                // draw the line
//              - toString ()                     // help debugging print statements
//         > ImageSelection implements Transferable // copied code, see comments below
//         > MySVG                                // based on Apache example, see below

   import javax.swing.JFrame;
   import javax.swing.JPanel;
   import javax.swing.JScrollPane;
   import javax.swing.JButton;
   import javax.swing.JLabel;
   import javax.swing.JComboBox;
   import javax.swing.JOptionPane;
   import javax.swing.JComponent;

   import java.awt.datatransfer.DataFlavor;
   import java.awt.datatransfer.Transferable;
   import java.awt.datatransfer.UnsupportedFlavorException;

   import java.awt.event.ActionListener;
   import java.awt.event.ActionEvent;

   import java.awt.Component;
   import java.awt.Container;
   import java.awt.Graphics;
   import java.awt.Graphics2D;
   import java.awt.GraphicsEnvironment;
   import java.awt.Toolkit;
   import java.awt.Dimension;
   import java.awt.Font;
   import java.awt.GridLayout;
   import java.awt.BorderLayout;
   import java.awt.Color;
   import java.awt.Rectangle;
   import java.awt.Insets;
   import java.awt.image.BufferedImage;

   import java.awt.print.PageFormat;
   import java.awt.print.Printable;
   import java.awt.print.PrinterJob;

   import java.util.Date;
   import java.util.ArrayList;
   import java.util.List;

   import java.io.IOException; // thrown by class ImageSelection

// *****************************************************************
// for MySVG class and SVG processing
// *****************************************************************
   import javax.swing.JFileChooser;
   import javax.swing.JOptionPane;

//svg files:
//following in: jdk/jre/lib/rt.jar
//   import org.w3c.dom.Document;
//following in: jdk/jre/lib/rt.jar
//   import org.w3c.dom.DOMImplementation;

//following in: batik-dom.jar import org.apache.batik.dom.GenericDOMImplementation;
//following in: batik-svggen.jar import org.apache.batik.svggen.SVGGraphics2D;
//following in: batik-svggen.jar import org.apache.batik.svggen.SVGGraphics2DIOException;

//indirect reference, importing jar into runtime environment:
//batik-awt-util.jar - org.apache.batik.ext.awt.g2d.AbstractGraphics2D
//batik-util.jar     - org/apache/batik/util/SVGConstants
//batik-ext.jar      - org/w3c/dom/ElementTraversal
//batik-xml.jar      - org/apache/batik/xml/XMLUtilities

// all of the above included in:
// batik-all.jar
// *****************************************************************
// for MySVG class and SVG processing
// *****************************************************************

   public class MyDrawTreeFrame extends JFrame implements ActionListener {
      static final long serialVersionUID = -6203817511645018972L;
   
      final static int DISPLAY_W = 0;  // default width and height of the display
      final static int DISPLAY_H = 52;
      final static int MIN_BPW   = 250; // button panel width
      final static int MIN_BPH   = 170; // button panel height
   //       final static double ZOOM_JUMP = 1.1; // about zooming
   
      boolean inValid = true;
   
      MyTreePanel mtp;
      JScrollPane treeSP;
      Container cp;
   
      String  [] fontNames    = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
      Integer [] fontSizeList = {10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 35, 40, 45};
      int fontSize = 12;
   
      JPanel buttonsP        = new JPanel(new GridLayout(0, 1));
      JButton printB         = new JButton("Print");
      JButton toClipB        = new JButton("Copy to Clipboard");
      JButton toSVGB         = new JButton ("Copy to svg file");
   //       JButton zoomInB        = new JButton("Zoom In");
   //       JButton zoomOutB       = new JButton("Zoom Out");
   //       JButton zoomResetB     = new JButton("Zoom 1.0");
      JComboBox <String> fontMenu;
      JComboBox <Integer> fontSizeMenu  = new JComboBox <Integer> (fontSizeList); 
      JPanel buttonsPA       = new JPanel(new BorderLayout());
   
      public MyDrawTreeFrame(MyTreeIF mt) {
         this (mt, null, true);
      } // end MyTree constructor
   
      public MyDrawTreeFrame(MyTreeIF mt, String st) {
         this (mt, st, true);
      } // end MyTree constructor
   
      public MyDrawTreeFrame(MyTreeIF mt, String label, boolean closeOnExit) {
         setupDisplay(mt, label);
         if (closeOnExit) setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      } // end MyTree constructor
   
      public void setupDisplay(MyTreeIF mt, String label) {
         int found = 0;
         for (int i = fontNames.length - 1; i > 3; i--) {
            if (fontNames[i].equals("Serif"))      found++;
            if (fontNames[i].equals("SansSerif"))  found++;
            if (fontNames[i].equals("Monospaced")) found++;
            fontNames [i] = fontNames [i - found];
         } // end removing 3 fonts from list
         fontNames [0] = "Serif"; // default font
         fontNames [1] = "SansSerif";
         fontNames [2] = "Monospaced";
         fontMenu = new JComboBox <String> (fontNames);
         fontMenu.setMaximumRowCount(30);
         fontSizeMenu.setSelectedIndex(1);
         fontSizeMenu.setMaximumRowCount(30);
      
         setTitle("Duchon's Trees - " + label);
         setLocation(300, 100);
         setLayout(new BorderLayout());
      
         mtp = new MyTreePanel(mt);
         treeSP = new JScrollPane(mtp);
         treeSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
         treeSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         add(treeSP, BorderLayout.CENTER);
      
         fontMenu.setBackground(Color.white);
         fontSizeMenu.setBackground(Color.white);
         fontSizeMenu.setAlignmentY(Component.CENTER_ALIGNMENT);
      
         JPanel pFontSize = new JPanel ();
         pFontSize .setLayout (new GridLayout (1, 0));
         pFontSize.add (new JLabel ("Font Size"));
         pFontSize.add (fontSizeMenu);
      
         buttonsP.add (printB);
         buttonsP.add (toClipB);
         buttonsP.add (toSVGB);
      //          buttonsP.add (zoomInB);
      //          buttonsP.add (zoomOutB);
      //          buttonsP.add (zoomResetB);
         buttonsP.add (fontMenu);
         buttonsP.add (pFontSize);
      
         buttonsPA.add (buttonsP, BorderLayout.NORTH); 
         add(buttonsPA, BorderLayout.WEST); // Compact buttons on left side
      
         printB.addActionListener(this);
         toClipB.addActionListener(this);
         toSVGB.addActionListener(this);
      //          zoomInB.addActionListener(this);
      //          zoomOutB.addActionListener(this);
      //          zoomResetB.addActionListener(this);
         fontMenu.addActionListener(this);
         fontSizeMenu.addActionListener(this);
      
         setSize(DISPLAY_W, DISPLAY_H);
         setVisible(true);
      } // end setupDisplay
   
      public void paint(Graphics g) {
         super.paint(g);
         Graphics2D g2 = (Graphics2D) g;
         if (inValid) {
            mtp.doLocations(g2);
            // height + 2 to not show scroll bars by default - magic number???
            setSize(mtp.getWidth() + MIN_BPW + DISPLAY_W, Math.max (mtp.getHeight() + DISPLAY_H, MIN_BPH));
            inValid = false;
         } // end just once
      } // end paintComponent
   
      public void actionPerformed(ActionEvent e) {
         if (e.getSource() == printB) {
            mtp.printMe();
         } 
         else if (e.getSource() == toClipB) {
            mtp.sendToClipboard();
         } 
         else if (e.getSource() == toSVGB) {
//            mtp.sendToSVG();
         } 
         //          else if (e.getSource() == zoomInB) {
         //             mtp.zoomIn(ZOOM_JUMP); inValid = true;
         //          } 
         //          else if (e.getSource() == zoomOutB) {
         //             mtp.zoomOut(ZOOM_JUMP); inValid = true;
         //          } 
         //          else if (e.getSource() == zoomResetB) {
         //             mtp.zoomOne(); inValid = true;
         //          } 
         else if (e.getSource() == fontMenu || e.getSource() == fontSizeMenu) {
            fontSize = (Integer) fontSizeMenu.getSelectedItem();
            Font x = new Font ((String)fontMenu.getSelectedItem(), Font.PLAIN, fontSize);
            mtp.setMyFont(x);
            inValid = true;
         } 
      // settings
         repaint();
      } // end method actionPerformed
   
   } // end class MyDrawTreeFrame

   class MyTreePanel extends JPanel implements Printable {
      static final long serialVersionUID = 4765726650311548539L;
      final static int LEFT_MARGIN = 10;
      final static int TOP_MARGIN = 10;
      final static int DEPTH_DELTA = 10;
      final static int WIDTH_DELTA = 5;
   
      int printerLeftMargin = 20;
   
      Dimension dim = new Dimension(1, 1);
      boolean printFlag = false;
      boolean inValid = true;
      double printScale = 1.0;
   //       double zoom = 1.0;
      ArrayList <MyTreeNode> nodes = new ArrayList <MyTreeNode> ();
      ArrayList <MyLine    >  lines = new ArrayList <MyLine   >  ();
      MyTreeNode root;
   
   // public static Font treeFont = new Font ("SansSerif", Font.PLAIN, 12);
   // public static Font treeFont = new Font ("Serif", Font.PLAIN, 20);
   // public static Font treeFont = new Font ("Monospaced", Font.PLAIN, 14);
      Font treeFont = new Font("Serif", Font.PLAIN, 12);
   
      public MyTreePanel (MyTreeIF tp) {
         setLayout (null);
         setBackground (Color.yellow);
         root = copyTree (tp, null);
         for (MyLine d: lines) add (d);
         walkTree (root, 10, 10); // top of tree
      } // end constructor MyTreePanel
   
   //       public void zoomIn    (double z) {
   //          zoom *= z;}
   //       public void zoomOut   (double z) {
   //          zoom /= z;}
   //       public void zoomOne   ()         {
   //          zoom  = 1.0;}
      public void setMyFont (Font fp)  {
         treeFont = fp; 
         inValid = true;
      } // end method setMyFont
      
      MyTreeNode copyTree (MyTreeIF mt, MyTreeNode parent) {
         MyTreeNode jba = new MyTreeNode (mt.toString(), parent);
         add (jba);
         for (MyTreeIF mtf : mt.getChildren()) {
            MyTreeNode jx = copyTree (mtf, jba);
            jba.addChild (jx);
            lines.add (new MyLine (jba, jx));
         }
         nodes.add (jba);
         return jba;
      } // end method copyTree - MyTreeIF
   
      // locates nodes and lines arraylists
      MyTreeNode walkTree (MyTreeNode jba, int top, int leftEdge) {
         int minX = leftEdge;
         int next = leftEdge;
         int maxY = top;
         jba.setFont (treeFont);
         Dimension d = jba.getPreferredSize ();
         jba.centerY = top + d.height / 2;
         if (jba.children == null || jba.children.size() == 0) { // is leaf
            jba.minX = leftEdge;
            jba.maxX = leftEdge + d.width;
            jba.setBounds (leftEdge, top, d.width, d.height);
            jba.centerX = leftEdge + d.width/2;
         } 
         else {
            for (MyTreeNode mtif : jba.children)  {
               MyTreeNode jx = walkTree (mtif, top + DEPTH_DELTA + d.height, next);
               next = WIDTH_DELTA + jx.maxX;
               if (jx.maxY > maxY) maxY = jx.maxY;
            } // end handling child locations
            int maxX = next - WIDTH_DELTA; 
            int left = maxX;
            int right = minX;
            for (MyTreeNode ch: jba.children) {
               if (ch.centerX < left) left = ch.centerX;
               if (ch.centerX > right) right = ch.centerX;
            } // end for all the children
            jba.centerX = (left + right) / 2;
            jba.setBounds (jba.centerX - d.width/2, top, d.width, d.height);
            jba.minX = minX;
            jba.maxX = maxX;
         } // end if this is a leaf
         if (top + d.height > maxY) maxY = top + d.height;
         jba.maxY = maxY;
         return jba;
      //          add (new MyLine (10, 10, 100, 200), getComponentCount()); // bottom
      //          add (new MyLine (10, 10, 200, 100), 0); // top
      //          add (new MyLine (10, 10, 200, 200)); // default is bottom
      } // end recursive walkTree
   
      public void doLocations (Graphics2D g) {
         g.setFont (treeFont);
         MyTreeNode mtn = walkTree (root, TOP_MARGIN, LEFT_MARGIN);
         for (MyLine d: lines) d.updateLine ();
         setSize (mtn.maxX + LEFT_MARGIN, mtn.maxY + TOP_MARGIN);
         dim = getSize ();
         setPreferredSize (dim);
      } // end method doLocations
   
      public void paint(Graphics g) { // NOT Graphics2D!!!!
      // don't call super when printing or svg
         if (!printFlag) super.paint(g);
         if (inValid) 
            draw((Graphics2D) g);
      } // end paint
   
      public void draw(Graphics2D g) {
      //          g.scale(zoom, zoom);
         g.setFont(treeFont);
         doLocations (g);
         inValid = false;
      } // end draw
      
      public Rectangle drawOnGraphics(Graphics2D g2d) {
         g2d.setColor (Color.black);
         for (MyLine d: lines) d.paint (g2d);
         for (MyTreeNode n: nodes) n.paintAbsolute (g2d);
         return new Rectangle(dim);
      } // end method drawOnGraphics
      
      public void drawSVG (Graphics2D g2d) {
         g2d.setColor (Color.black);
         g2d.setFont (treeFont);
         for (MyLine d: lines) d.paint (g2d);
         for (MyTreeNode n: nodes) n.paintSVG (g2d);
      } // end drawSVG
   
      public int print(Graphics g, PageFormat pForm, int pi) {
         if (pi > 0)
            return Printable.NO_SUCH_PAGE;
         Graphics2D g2d = (Graphics2D) g;
         g2d.translate((int) Math.round(pForm.getImageableX()), (int) Math.round(pForm.getImageableY()));
         g2d.drawString("The date is: " + new Date(), printerLeftMargin, 30);
         g2d.translate(printerLeftMargin, 40);
         g2d.scale(printScale, printScale);
         printFlag = true;
         g2d.setColor(Color.black);
         Rectangle r = drawOnGraphics(g2d);
         g2d.drawRect(0, 0, r.width, r.height);
         printFlag = false;
         return Printable.PAGE_EXISTS;
      } // end print
   
      public void printMe() {
         PrinterJob printJob = PrinterJob.getPrinterJob();
         printJob.setPrintable(this);
         if (printJob.printDialog()) {
            try {
               String input = JOptionPane.showInputDialog("Enter printing scale: ", "" + printScale);
               if (input == null) 
                  return; // cancel button in dialog
               printScale = Double.parseDouble(input);
               printJob.print();
            } 
               catch (NullPointerException npe) {
                  JOptionPane.showMessageDialog(null, "MyDrawTreeFrame\nprintMe\nNullPointerException\n" + npe);
                  npe.printStackTrace();
                  return;
               } 
               catch (NumberFormatException nfe) {
                  JOptionPane.showMessageDialog(null,
                     "Bad scale, aborting print job.");
                  return;
               } 
               catch (Exception ex) { // NEVER crash
                  JOptionPane.showMessageDialog(null, "MyDrawTreeFrame\nprintMe\nException\n" + ex);
                  ex.printStackTrace();
               } // end try
         } // end if print selected
      } // end printMe
   
      public void sendToClipboard() {
         if (dim.width == 0 || dim.height == 0)
            return;
         BufferedImage bi = new BufferedImage(dim.width, dim.height,
            BufferedImage.TYPE_INT_RGB);
         Graphics2D g2d = bi.createGraphics();
         g2d.setColor(Color.WHITE);
         g2d.fillRect(0, 0, dim.width, dim.height);
         printFlag = true;
         drawOnGraphics(g2d);
         printFlag = false;
         ImageSelection is = new ImageSelection(bi);
         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(is, null);
      } // end method sendToClipboard
   
//      public void sendToSVG () {
//         try {
//            printFlag = true;
//            MySVG.sendToSVG (this);
//            printFlag = false;
//         }
//            catch (Error e) {
//               JOptionPane.showMessageDialog(null, "Error:\n" + e + "\nProbably the Batik files are not part of the classpath" +
//                  "\ntry something like:\n-cp .;\"c:\\program files\\java\\batik-1.7\\lib\\batik-all-1.7.jar\"");
//            } // end try
//      } // end method sendToSVG
   
   } // end class MyTreePanel
   
   class MyTreeNode extends JButton {
      public static final long serialVersionUID = 123978L;
      int centerX, centerY, minX, maxX, maxY;
      MyTreeNode parent;
      ArrayList <MyTreeNode> children = null;
   
      public MyTreeNode (String st, MyTreeNode pp) {
         super (st);
         parent = pp;
         setMargin (new Insets(-2, 2, -2, 2));
      } // constructor - String
      
      public void addChild (MyTreeNode c) {
         if (children == null) children = new ArrayList <MyTreeNode> ();
         children.add (c);
      } // end method addChild - MyTreeNode
      
      // for SVG painting, for some reason, default paint will not
      public void paintSVG (Graphics2D g) {
         Rectangle r = getBounds();
         g.setColor (Color.yellow);
         g.fillRect (r.x, r.y, r.width, r.height);
         g.setColor (Color.black);
         g.drawRect (r.x, r.y, r.width, r.height);
         g.drawString (getText(), r.x + 3, r.y + r.height - 4);
      } // end method paintSVG
      
      // for print and copy to clipboard   
      public void paintAbsolute (Graphics g) {
         Rectangle rb = getBounds ();
         paint (g.create (rb.x, rb.y, rb.width, rb.height));
      } // end method paintAbsolute
   
   } // end class MyTreeNode
   
   class MyLine extends JComponent {
      public static final long serialVersionUID = 123L;
      int a, b, c, d;
      MyTreeNode m1, m2;
   
      public MyLine (MyTreeNode n1, MyTreeNode n2) {
         m1 = n1; m2 = n2;
      } // end constructor - MyTreeNode, MyTreeNode
      
      public void updateLine () {
         a = m1.centerX; b = m1.centerY; c = m2.centerX; d = m2.centerY;
         int w = (a > c) ? a+1 : c+1; // add 1 to make sure vertical and horizontal lines show
         int h = (b > d) ? b+1 : d+1;
         setBounds (0, 0, w, h); // otherwise nothing will show on paint!
      } // end updateLine
   
      public void paint (Graphics g) {
         g.drawLine (a, b, c, d);
      } // end paintComponent for this instance
   
      public String toString () {
         return String.format ("Line from (%d,%d) to (%d,%d)", a, b, c, d);
      } // end method toString
   
   } // end class MyLine

// this code from :
// http://www.devx.com/Java/Article/22326/
// by: Kulvir Singh Bhogal
// Local class is used to hold an image while on the clipboard.
   class ImageSelection implements Transferable {
   // the Image object which will be housed by the ImageSelection
      private BufferedImage image;
   
      public ImageSelection(BufferedImage image) {
         this.image = image;
      } // end constructor - BufferedImage
   
   // Returns the supported flavors of our implementation
      public DataFlavor[] getTransferDataFlavors() {
         return new DataFlavor[] { DataFlavor.imageFlavor };
      } // end method getTransferDataFlavors
   
   // Returns true if flavor is supported
      public boolean isDataFlavorSupported(DataFlavor flavor) {
         return DataFlavor.imageFlavor.equals(flavor);
      } // end method isDataFlavorSupported - DataFlavor
   
   // Returns Image object housed by Transferable object
      public Object getTransferData(DataFlavor flavor)
      	throws UnsupportedFlavorException, IOException {
         if (!DataFlavor.imageFlavor.equals(flavor)) {
            throw new UnsupportedFlavorException(flavor);
         }
      // else return the payload
         return image;
      } // end method getTransferData - DataFlavor
      
   } // end class ImageSelection

//   class MySVG {
//   // NOTE: uses paint in calling method,
//   // >> If paint starts with a call to super, put the call inside a try/catch block
//   //     catching NullPointerException - SVG will not have a super instance
//   // based on example from apache batik
//   // http://xmlgraphics.apache.org/batik/using/svg-generator.html
//      public static void sendToSVG (MyTreePanel cmp) {
//         try {
//            String fileName = "tree.svg"; // file to save svg output
//         //             JFileChooser chooser = new JFileChooser("c:\\inet\\home");
//            JFileChooser chooser = new JFileChooser("Desktop");
//            int returnVal = chooser.showOpenDialog(null);
//            if ( returnVal == JFileChooser.APPROVE_OPTION ) {
//               fileName = chooser.getSelectedFile().getAbsolutePath();
//            }
//            else
//               return;
//
//         // Get a DOMImplementation.
//         // from: org.apache.batik.dom.GenericDOMImplementation
//         //	     import org.w3c.dom.Document;
//         //       import org.w3c.dom.DOMImplementation;
//         //       import org.apache.batik.dom.GenericDOMImplementation;
//            DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
//
//         // Create an instance of org.w3c.dom.Document.
//         // no special imports required here
//            String svgNS = "http://www.w3.org/2000/svg";
//            Document document = domImpl.createDocument(svgNS, "svg", null);
//
//         // Create an instance of the SVG Generator.
//         // from: org.apache.batik.svggen.SVGGraphics2D
//         //       import org.apache.batik.svggen.SVGGraphics2D;
//            SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
//
//         // Render into the SVG Graphics2D implementation.
//            cmp.drawSVG(svgGenerator);
//
//            svgGenerator.stream(fileName, true); // use CSS style
//            JOptionPane.showMessageDialog(null, "File saved as:\n" + fileName);
//            svgGenerator.dispose(); // flush the stream, I hope!
//         }
//            catch (SVGGraphics2DIOException e) {
//               JOptionPane.showMessageDialog(null, "MySVG\nsndToSVG\nSVGGraphics2DIOException\n" + e);
//            }
//            catch (Exception e) {
//               JOptionPane.showMessageDialog(null, "MySVG\nsndToSVG\nException\n" + e);
//               e.printStackTrace();
//            } // end try
//
//      } // end method sndToSVG
//   } // end class MySVG
