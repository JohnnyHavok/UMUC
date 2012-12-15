package cmsc350.project4.ndsource;// File: MyTreeIF.java
// Author: Nicholas Duchon
// Date: Nov 1, 2012
// Purpose: interface to support the MyDrawTreeFrame class
//   to simplify the graphic display of trees.
// Instructions:
//   To use these classes, create a data container class, a kind of node, say MyNode, 
//   which will implement the getChildren () method - returning
//   a list of children. The return value may be null.
//   MyNode should implement MyTreeIF and return a list of children which
//   are also instances of MyNode.
// See - the TestTree.java file for an example using these classes.
// Also: the MyDrawTreeFrame uses the toString method of the class that 
//   implements MyTreeIF, so you will want to make sure that toString makes sense.

import java.util.List;

public interface MyTreeIF {
  public List <MyTreeIF> getChildren();
} // end MyTreeIF
