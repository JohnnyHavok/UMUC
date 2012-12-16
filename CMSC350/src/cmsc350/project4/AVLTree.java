package cmsc350.project4;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Name: Justin Smith
 * CMSC 350
 * Project: 4
 * Date: 12/15/12 2:30 PM
 * Requires: J2SE 7+
 */

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTreeND<T> implements MyTreeIF {
	private BSTNodeND<T> root; // Starting node

	private int opCount; // Counts number of operations between inserts
	private int insertCount; // Counts total number of inserts
	private int size; // Size of the tree
	private List<T> objectTraverseList; // Traverse list that holds contents of tree for getPreOrderList()
	List<MyTreeIF> nodes; // List of nodes generated when getChildren() is called.


	// Default constructor
	public AVLTree() {
		root = null;
		insertCount = 0;
		size = 0;
	}

	// Convenience constructor that takes an iterable collection of T
	public AVLTree(Collection<T> c) {
		root = null;
		insertCount = 0;
		size = 0;
		for(T t: c) this.insert(t);
	}

	// Convenience constructor that takes an array of T
	public AVLTree(T[] a) {
		root = null;
		insertCount = 0;
		size = 0;
		for(T t: a) this.insert(t);
	}

	// Private constructors for building node list through getChildren()
	private AVLTree(BSTNodeND<T> t) { this.root = t; }
	private AVLTree(String s) { root = new AVLNode<>((T) s); } // Not safe, never use in a tree! List only!

	/**
	 * Insert object into AVLTree
	 * @param t : Comparable type object to store
	 */
	@Override
	public void insert(T t) {
		++insertCount;
		++size;
		opCount = 0;
		System.out.print("+-("+insertCount+") Insert: ["+t+"] into > ");
		if(root == null) {
			root = new AVLNode<>(t);
			System.out.println("Empty Tree");
		} else {
			System.out.println(toString(TreeTraversalOrder.LEVELORDER));
			root = insert(new AVLNode<>(t), root);
		}
		System.out.println("\nNew Tree:");
		System.out.println(this.toTreeString());
	}

	/**
	 * Recursive insert algorithm learned from CMSC 420 but (A) Fully complete and (B) Keeps track of parent pointers
	 * Step (1) : Recursively find a place for your object to go that satisfies the BST-ADT, this will create a trail
	 * of visited nodes on the execution stack.
	 * Step (2) : As the execution stack decompresses update a balance factor that is the difference in children
	 * heights.
	 * Step (3) : If there is a balance factor that is equal to 2 down the path we took last we know that the newly
	 * inserted node has caused an unbalanced tree.
	 * Step (4) : Determine if the corrective rotation is a single or double rotation based on the path the node took
	 * to reach it's final spot.
	 * Step (5) : Return this executions root node to the previous caller on the stack so they know if they need to
	 * update their children (left,right) links
	 * @param n : New node to insert
	 * @param r : Root node of this tree (subtree)
	 * @return : Root node of this current execution branch.
	 */
	private BSTNodeND<T> insert(BSTNodeND<T> n, BSTNodeND<T> r) {
		++opCount;
		int balanceFactor;

		if(r == null) { // Found empty space at the bottom of the tree
			System.out.println("|--("+opCount+") Placed ["+n.data+"] -child of- "+"["+n.parent.data+"]");
			return n;
		}

		if(n.data.compareTo(r.data) < 0) { // If element belongs on the left of 'r'
			System.out.println("|--("+opCount+") Insert ["+n.data+"] -left of- "+"["+r.data+"]");
			n.parent = r; // Keep updating n on it's new parent until bottom is found
			r.left = insert(n, r.left); // Recursively locate the bottom of the tree
			balanceFactor = height(r.left) - height(r.right);
			System.out.println("|---("+ ++opCount +") Checking balance factor of ["+r.data+"] is "+balanceFactor);
			if(balanceFactor == 2) {
				System.out.print("|----("+ ++opCount +") Balance factor of ["+r.data+"] is off");
				if(n.data.compareTo(r.left.data) < 0) {
					System.out.println("due to an imbalance in the left subtree of the left child of ["+r.data+"]");
					r = rRotation(r);
				} else {
					System.out.println("due to an imbalance in the right subtree of the left child of ["+r.data+"]");
					r = lrRotate(r);
					System.out.println("|-----("+ ++opCount +") <END> Left-Right rotation around ["+r.left.data+"]");
				}
			}
		} else if(n.data.compareTo(r.data) > 0) { // If element belongs on the right of 'r'
			System.out.println("|--("+opCount+") Insert ["+n.data+"] -right of- "+"["+r.data+"]");
			n.parent = r; // Keep updating n on it's new parent until bottom is found
			r.right = insert(n, r.right); // Recursively locate the bottom of tree
			balanceFactor = height(r.right) - height(r.left);
			System.out.println("|---("+ ++opCount +") Checking balance factor of ["+r.data+"] is "+balanceFactor);
			if(balanceFactor == 2) {
				System.out.print("|----("+ ++opCount +") Balance factor of ["+r.data+"] is off ");
				if(n.data.compareTo(r.right.data) > 0) {
					System.out.println("due to an imbalance in the right subtree of the right child of ["+r.data+"]");
					r = lRotation(r);
				} else {
					System.out.println("due to an imbalance in the left subtree of the right child of ["+r.data+"]");
					r = rlRotate(r);
					System.out.println("|-----("+ ++opCount +") <END> Right-Left rotation around ["+r.left.data+"]");
				}
			}
		} else { // If elements are equal
			// TODO: Figure out what to do with duplicates.
		}

		System.out.print("|---(" + ++opCount + ") Updating height on [" + r.data + "] from " + ((AVLNode<T>) r).height);
		((AVLNode<T>)r).height = Math.max(height(r.left), height(r.right)) + 1; // Continuously update the heights as we through tree
		System.out.println(" to "+((AVLNode<T>)r).height);
		return r; // Return root, which may have changed through rotations.
	}

	/**
	 * Performs Right Rotation around the given root and the root's right child.  Fixes imbalances in the left subtree
	 * of the left child of a given root.
	 * @param r : root AVLNode to right-rotate around.
	 * @return : New root AVLNode
	 */
	private AVLNode<T> rRotation(BSTNodeND<T> r) { // Right Rotation
		System.out.println("|-----("+ ++opCount +") <START> Right rotation around ["+r.data+"]");
		AVLNode<T> n = (AVLNode<T>) r.left; // (1) Copy reference old-root->left = new-root
		System.out.println("|-----("+ ++opCount +") New root will be ["+n.data+"]");
		n.parent = r.parent; // Update parent pointer
		r.left = n.right; // (2) Move right tree of new-root to left tree of old-root
		if(r.left != null) r.left.parent = r; // Change parent pointer of new left node
		n.right = r; // (3) Make old-root the right child of the new-root
		r.parent = n; // Change parent pointer of old-root to new-root
		((AVLNode<T>)r).height = Math.max(height(r.left), height(r.right)) + 1; // Update old-root height
		System.out.println("|-----("+ ++opCount +") Old root ["+r.data+"] now has height "+((AVLNode<T>)r).height);
		n.height = Math.max(height(n.left), height(n.right)) + 1; // Update new-root height
		System.out.println("|-----("+ ++opCount +") New root ["+n.data+"] now has height "+n.height);
		System.out.println("|-----("+ ++opCount +") <END> Right rotation around ["+r.data+"]");
		return n;
	}

	/**
	 * Performs Left Rotation around the given root and root's left child.  Fixes imbalances in the right subtree
	 * of the right child of given root.
	 * @param r : root AVLNode to left-rotate around.
	 * @return : New root AVLNode
	 */
	private AVLNode<T> lRotation(BSTNodeND<T> r) { // Left Rotation
		System.out.println("|-----("+ ++opCount +") <START> Left rotation around ["+r.data+"]");
		AVLNode<T> n = (AVLNode<T>) r.right; // (1) Copy reference old-root->right = new-root
		System.out.println("|-----("+ ++opCount +") New root will be ["+n.data+"]");
		n.parent = r.parent; // Update parent pointer
		r.right = n.left; // (2) Move left tree of new-root to right tree of old-root
		if(r.right != null) r.right.parent = r; // Change parent pointer of new left node
		n.left = r; // (3) Make old-root the left child of the new-root
		r.parent = n; // Change parent pointer of old-root to new-root
		((AVLNode<T>)r).height = Math.max(height(r.left), height(r.right)) + 1; // Update old-root height
		System.out.println("|-----("+ ++opCount +") Old root ["+r.data+"] now has height "+((AVLNode<T>)r).height);
		n.height = Math.max(height(n.left), height(r.right)) + 1; // Update new-root height
		System.out.println("|-----("+ ++opCount +") New root ["+n.data+"] now has height "+n.height);
		System.out.println("|-----("+ ++opCount +") <END> Left rotation around ["+r.data+"]");
		return n;
	}

	/**
	 * Performs Right-Left Rotation around the given root and the root's right child.  Fixes imbalances in the left
	 * subtree of the right child.
	 * @param r : AVLNode to rotate subtree around
	 * @return : New root AVLNode
	 */
	private AVLNode<T> rlRotate(BSTNodeND<T> r) { // Right-Left Rotation
		System.out.println("|-----("+ ++opCount +") <START> Right-Left rotation around ["+r.data+"]");
		r.right = rRotation(r.right);
		return lRotation(r);
	}

	/**
	 * Performs Left-Right Rotation around the given root and the root's left child.  Fixes imbalances in the right
	 * subtree of the left child.
	 * @param r : AVLNode to rotate subtree around
	 * @return : New root AVLNode
	 */
	private AVLNode<T> lrRotate(BSTNodeND<T> r) { // Left-Right Rotation
		System.out.println("|-----("+ ++opCount +") <START> Left-Right rotation around ["+r.data+"]");
		r.left = lRotation(r.left);
		return rRotation(r);
	}

	/**
	 * Returns the height of a given node if it exist.  Else returns sentinel value of -1
	 * @param n : AVLNode you need the height of
	 * @return : Height of node or -1 if null reference
	 */
	private int height(BSTNodeND<T> n) { // Fancy null-pointer check when calculating height
		if(n == null)
			return -1;
		return ((AVLNode<T>)n).height;
	}

	@Override
	public int getSize() { return size; }

	/**
	 * Returns a pre-ordered list of the tree's objects.
	 * @return : pre-ordered list of tree objects
	 */
	public List<T> getPreOrderList() {
		objectTraverseList = new ArrayList<>();
		this.preOrderList(root);
		return objectTraverseList;
	}

	// Called by getPreOrderList().
	private void preOrderList(BSTNodeND<T> r) {
		objectTraverseList.add(r.data);
		if(r.left != null) preOrderList(r.left);
		else objectTraverseList.add(null);
		if(r.right != null) preOrderList(r.right);
		else objectTraverseList.add(null);
	}

	/**
	 * Returns this nodes data's toString() to user of AVLTree.  Useful for things like MyDrawTreeFrame
	 * @return : String representing this nodes data after calling data.toString();
	 */
	@Override
	public String toString() {
		return root.data.toString();
	}

	/**
	 * Public wrapper for BinarySearchTreeND toString() that prints a text tree to console.
	 * @return : Text based representation of the AVL tree.
	 */
	public String toTreeString() {
		if(root == null) return null;
		return super.toString(root);
	}

	/**
	 * Wrapped toString(int ord) in BinarySearchTreeND to use enum constants instead of integer pattern.
	 * @param type : enum from TreeTraversalOrder
	 * @return : String representing tree after a given traversal
	 */
	public String toString(TreeTraversalOrder type) {
		return super.toString(type.getMap(), root);
	}

	/**
	 * MyTreeIf implemented method for using this tree with MyDrawTreeFrame
	 * @return : List of subtree children objects of type AVLTree<T>
	 */
	@Override
	public List<MyTreeIF> getChildren() {
		nodes = new ArrayList<>();

		if(root.left == null && root.right == null) return nodes;

		if(root.left != null)
			nodes.add(new AVLTree<>(root.left));
		else
			nodes.add(new AVLTree<String>("empty"));

		if(root.right != null)
			nodes.add(new AVLTree<>(root.right));
		else
			nodes.add(new AVLTree<String>("empty"));

		return nodes;
	}

	/**
	 * Node class for AVLTree, extends BSTNodeND and adds an integer for height storage.
	 * @param <T> : Any comparable items
	 */
	class AVLNode<T extends Comparable<? super T>> extends BSTNodeND<T> {
		private int height;

		AVLNode(T t) {
			super(t);
			height = 0;
		}
	} // end inner AVLNode class
} // end AVLTree class

/**
 * Enum constants to wrap BinarySearchTreeND use of integer pattern constants.
 */
enum TreeTraversalOrder {
	INORDER(1),
	PREORDER(2),
	POSTORDER(3),
	LEVELORDER(4);

	private int map;

	TreeTraversalOrder(int id) {this.map = id;}
	public int getMap() {return map;}
}
