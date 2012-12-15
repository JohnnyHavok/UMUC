package cmsc350.project4.ndsource;
// File  : BinarySearchTreeND.java
// Author: Nicholas Duchon
// Date  : July 31, 2008
// updates: 
//        Nov 16, 2008
//        Nov 22, 2008
//        Apr 17, 2009 - fixed toTreeString
//        Apr 17, 2009 - added parents to nodes and \ and / to tree display.
//
// Simplified, linked version of a binary search tree
// uses generic class implementing Comparable
// no exceptions thrown
//   static fields
//       all associated with toString displays
//       .. Selectors: INORDER, PREORDER, POSTORDER, LEVELORDER
//       .. DEPTHDELTA - used to present a tree-like display using spacing
//   This code implements the following public methods
//            T is a Comparable class
//       insert   (T)  : void
//       find     (T)  : T
//       max      ()   : T
//       getSize  ()   : int
//       remove   (T)  : void
//       toString ()   : String // to tree string
//       toString (int): String // int is one of the Selectors
//
//    private mutator/accessor methods
//       insertValue (T, BSTNodeND < T >): void - uses comparator from L
//       findValue   (T, BSTNodeND < T >): T - finds an T, returns null if not found
//       findMax                       (): T - returns max value in this subtree
//       getSize        (BSTNodeND < T >): int
//       removeRoot     (BSTNodeND < T >): BSTNodeND < T >
//       remove      (T, BSTNodeND < T >): BSTNodeND < T >
//
//    private toString methods
//       toString            (BSTNodeND) : uses toTreeString (int) to create a tree-like display
//       toTreeString   (int, BSTNodeND) : recursive in-order traversal with depth offsets
//       toInOrderString     (BSTNodeND) : recursive in-order string
//       toPreOrderString    (BSTNodeND) : recursive pre-order string
//       toPostOrderString   (BSTNodeND) : recursive post-order string
//       toLevelOrderString  (BSTNodeND) : level-order string, uses a queue (ArrayDeque)
//
// Inside the BSTNodeND class:
//    Instace fields:
//       data  < L >   generic parameter of the BSTNodeND class, NOT the BinarySearchTreeND class
//       BSTNodeND left, right, parent - links to create the tree
//    constructors
//       < L > generic parameterized constructor, left and right are null
//       < L >, NSTNodeND - links to parent also, for tree display
//
//    import java.util.ArrayDeque;

   public class BinarySearchTreeND 
      < K extends Comparable < ? super K > > 
      // ANY class that extends the base comparable type (K)
      // of this data structure instance may be inserted
   {
      static final int INORDER    = 1; // select toString (int)
      static final int PREORDER   = 2; // select toString (int)
      static final int POSTORDER  = 3; // select toString (int)
      static final int LEVELORDER = 4; // select toString (int)
      static final int DEPTHDELTA = 5; // used to create a text tree display
   
      public static void main (String args []) {
         BinarySearchTreeND < Integer > x = new BinarySearchTreeND < Integer > ();
//          int arr [] = {40, 20, 60, 10, 30, 50, 70, 5, 15, 25, 35, 45, 55, 65, 75};
//          int rem [] = {60, 45, 50, 40, 55};
         int arr [] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
         int rem [] = {};
         for (int y: arr) x.insert (y);
      
         System.out.println ("X:\n" + x);
         System.out.println ("X in-order:\n   "    + x.toString(INORDER));
         System.out.println ("X pre-order:\n   "   + x.toString(PREORDER));
         System.out.println ("X post-order:\n   "  + x.toString(POSTORDER));
         System.out.println ("X level-order:\n   " + x.toString(LEVELORDER));
      
         Integer t = x.find(10);
         System.out.println ("find: " + t);
         System.out.println ("find: " + x.find(20));
         System.out.println ("Size: " + x.getSize());
         System.out.println ("MAX: " + x.max());
         for (int y: rem) {
            System.out.println ("Removing: " + y);
            x.remove (y);
            System.out.println ("result:\n" + x);
         }
         System.out.println ("X:\n"  + x);
         
         // The following is an example using a user-defined class, see below
         // notice the use of the generic parameter Example
         String [] sa = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
         BinarySearchTreeND < Example > ex = new BinarySearchTreeND < Example > ();
         for (String s: sa) ex.insert (new Example (s));
         System.out.println ("Example: \n" + ex);
      
      } // end main
   
      private BSTNodeND < K > root = null;
   
      public void insert (K d) {
         if (root == null) root = new BSTNodeND < K > (d);
         else insertValue (d, root);
      } // end insert, public version
   
      public K find (K d) {
         if (root == null) 
            return null;
         BSTNodeND < K > t = findValue (d, root);
         return (t==null)?null:t.data;
      } // end find method
      
      public K max () {
         if (root == null) 
            return null;
         return findMax(root).data;
      } // end max method
   
      public int getSize () {
         return getSize (root);}
   
      public void remove (K d) {
         root = remove (d, root);
      } // end remove data
   
      public String toString () {
         if (root == null) 
            return null;
         return toString(root);}
         
      public String toString (int ord) {
         if (root == null) 
            return null;
         return toString(ord, root);}
   
      private void insertValue (K d, BSTNodeND < K > n) {
         if (d.compareTo (n.data)  > 0) 
            if (n.right == null) n.right = new BSTNodeND < K > (d, n);
            else insertValue (d, n.right);
         else 
            if (n.left == null) n.left = new BSTNodeND < K > (d, n);
            else insertValue (d, n.left);
      } // end method insertValue
      
      private BSTNodeND < K > findValue (K d, BSTNodeND < K > n) {
         if (n.data.compareTo(d) == 0) 
            return n;
         if (n.data.compareTo (d) > 0) 
            return (n.left==null)?null:findValue (d, n.left);
         return (n.right == null)?null:findValue(d, n.right);
      } // end findValue
      
      private BSTNodeND < K > findMax (BSTNodeND < K > n) {
         if (n.right == null) 
            return n;
         return findMax(n.right);
      } // end findValue
      
      private int getSize (BSTNodeND < K > t) {
         if (t == null) 
            return 0;
         return getSize (t.left) + getSize (t.right) + 1;
      } // end getSize node
      
      private BSTNodeND < K > removeRoot (BSTNodeND < K > t) {
         if (t.left  == null) {
            if (t.right != null) 
               t.right.parent = t.parent;
            return t.right;
         }
         if (t.right == null) {
            t.left.parent = t.parent; // t.left != null because of earlier if test case
            return t.left;
         }
         BSTNodeND < K > newTop = findMax(t.left);
         remove (newTop.data, t); // lose the node instance, leave tree intact
         t.data = newTop.data;    // just replace the data at the internal node
         return t;
      } // end remove data, tree
   
      private BSTNodeND < K > remove (K d, BSTNodeND < K > t) {
         if (t == null) 
            return null;
         if (d.compareTo (t.data) < 0) 
            t.left  = remove (d, t.left );
         else 
            if (d.compareTo (t.data)> 0) 
               t.right = remove (d, t.right);
            else // d equals t.data
               t = removeRoot (t);
         return t;
      } // end remove data, tree
   
      private String toString (BSTNodeND n) {
         return toTreeString (5, n); 
      } // end toString
         
      private String toTreeString (int depth, BSTNodeND n) { // depth = 0 is bad
         StringBuffer st = new StringBuffer ();
         char d = '\\';                         // default = this is right child
         if (n.parent == null) d = ' ';         // case of root
         else if (n == n.parent.left) d = '/';  // case that this is left child
         st.append ((n.left  == null)?"":toTreeString  (depth + DEPTHDELTA, n.left));
         st.append (String.format ("%" + depth + "s%s\n", d, n.data)); // ND: fixed 4/17/2009
         st.append ((n.right == null)?"":toTreeString (depth + DEPTHDELTA, n.right));
         return st.toString();
      } // end method toTreeString
         
      private String toInOrderString (BSTNodeND n) {
         StringBuffer st = new StringBuffer ();
         st.append ((n.left  == null)?"":toInOrderString(n.left));
         st.append (n.data + " ");
         st.append ((n.right == null)?"":toInOrderString(n.right));
         return st.toString();
      } // end toInOrderString
         
      private String toPreOrderString (BSTNodeND n) {
         StringBuffer st = new StringBuffer ();
         st.append (n.data + " " );
         st.append ((n.left  == null)?"":toPreOrderString(n.left));
         st.append ((n.right == null)?"":toPreOrderString(n.right));
         return st.toString();
      } // end toPreOrderString
         
      private String toPostOrderString (BSTNodeND n) {
         StringBuffer st = new StringBuffer ();
         st.append ((n.left  == null)?"":toPostOrderString(n.left));
         st.append ((n.right == null)?"":toPostOrderString(n.right));
         st.append (n.data + " ");
         return st.toString();
      } // end to PostOrderString
         
         // See: http://en.wikipedia.org/wiki/Tree_traversal
      private String toLevelOrderString (BSTNodeND n) {
         StringBuffer st = new StringBuffer ();
         BSTNodeND node;
         java.util.ArrayDeque < BSTNodeND > q 
               = new java.util.ArrayDeque < BSTNodeND > ();
         q.add (n);          // start queue by adding this (root?) to queue
         while (q.size() > 0) { 
            node = q.remove();                          // remove the head of queue
            st.append (node.data + " ");                // process head data to String
            if (node.left != null) q.add (node.left);   // insert left child at end of queue
            if (node.right != null) q.add (node.right); // insert right child at end or queue
         } // end queue processing
         return st.toString();
      } // end to LevelOrderString
         
      private String toString (int order, BSTNodeND n) {
         String st = null;
         switch (order) {
            case INORDER:    st = toInOrderString   (n); 
               break;
            case PREORDER:   st = toPreOrderString  (n); 
               break;
            case POSTORDER:  st = toPostOrderString (n); 
               break;
            case LEVELORDER: st = toLevelOrderString(n); 
               break;
         }
         return st;
      } // end toString int
      
   } // end class BinarySearchTreeND

   class BSTNodeND 
         < L extends Comparable< ? super L > > 
   {
      L data;
      BSTNodeND < L > left = null, right = null, parent = null;
      
      BSTNodeND (L d) {
         data = d;}
         
      BSTNodeND (L d, BSTNodeND p) {
         data = d;
         parent = p;
      } // end data + parent
      
   } // end class BSTNodeND
   
// A class that can be used with the BinarySearchTreeND data structure
// Notice the use of the generic parameter Example
   class Example implements Comparable < Example > {
      String data;
    
      public Example (String d) {
         data = d;}
    
   // you, of course, will want a more interesting compareTo method
      public int compareTo (Example e) {
         return data.compareTo (e.data);
      } // end compareTo method
      
      public String toString () {
         return data;
      } // end toString
   
   } // end class Example
