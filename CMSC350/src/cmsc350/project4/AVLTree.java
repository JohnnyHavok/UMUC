package cmsc350.project4;

import cmsc350.project4.ndsource.BinarySearchTreeND;

/**
 * Name: Justin Smith
 * CMSC 350
 * Project: 4
 * Date: 12/15/12 2:30 PM
 * Requires: J2SE 7+
 */


public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTreeND<T> {

}

enum TreeTraversalOrder {
	INORDER(1),
	PREORDER(2),
	POSTORDER(3),
	LEVELORDER(4),
	DELTADEPTH(5);

	private int id;

	TreeTraversalOrder(int id) {this.id = id;}
	public int getId() {return id;}
}
