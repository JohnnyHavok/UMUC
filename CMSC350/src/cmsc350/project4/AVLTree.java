package cmsc350.project4;

import java.util.ArrayDeque;

/**
 * Name: Justin Smith
 * CMSC 350
 * Project: 4
 * Date: 12/15/12 2:30 PM
 * Requires: J2SE 7+
 */

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTreeND<T> {
	private BSTNodeND<T> root = null;

	@Override
	public void insert(T t) {
		if(root == null) {root = new AVLNode<>(t);}
		else root = insert(t, root);
	}

	private BSTNodeND<T> insert(T t, BSTNodeND<T> r) {
		System.out.println("Insert > " + t);


		if(r == null) {
			return new AVLNode<>(t);
		}

		if(t.compareTo(r.data) < 0) { // If element belongs on the left of 'r'
			r.left = insert(t, r.left); // Recursively locate the bottom of the tree
			if(height(r.left) - height(r.right) == 2)
				if(t.compareTo(r.left.data) < 0)
					r = lRotation(r);
				else
					r = rlRotate(r);
		} else if(t.compareTo(r.data) > 0) { // If element belongs on the right of 'r'
			System.out.println(t.compareTo(r.data));
			r.right = insert(t, r.right); // Recursively locate the bottom of tree
			if(height(r.right) - height(r.left) == 2)
				if(t.compareTo(r.right.data) > 0)
					r = rRotation(r);
				else
					r = lrRotate(r);
		} else { // If elements are equal
			// TODO: Figure out what to do with duplicates.
		}

		((AVLNode<T>)r).height = Math.max(height(r.left), height(r.right)) + 1; // Continuously update the heights as we through tree
		return r; // Return root, which may have changed through rotations.
	}

	private int height(BSTNodeND<T> r) { // Fancy null-pointer check when calculating height
		if(r == null)
			return -1;
		return ((AVLNode<T>)r).height;
	}

	private AVLNode<T> lRotation(BSTNodeND<T> r) { // Right Rotation
		AVLNode<T> n = (AVLNode<T>) r.left; // (1) Copy reference old-root->left = new-root
		r.left = n.right; // (2) Move right tree of new-root to left tree of old-root
		n.right = r; // (3) Make old-root the right child of the new-root
		((AVLNode<T>)r).height = Math.max(height(r.left), height(r.right)) + 1; // Update old-root height
		n.height = Math.max(height(n.left), height(n.right)) + 1; // Update new-root height
		return n;
	}

	private AVLNode<T> rRotation(BSTNodeND<T> r) { // Left Rotation
		AVLNode<T> n = (AVLNode<T>) r.right; // (1) Copy reference old-root->right = new-root
		r.right = n.left; // (2) Move left tree of new-root to right tree of old-root
		n.left = r; // (3) Make old-root the left child of the new-root
		((AVLNode<T>)r).height = Math.max(height(r.left), height(r.right)) + 1; // Update old-root height
		n.height = Math.max(height(n.left), height(r.right)) + 1; // Update new-root height
		return n;
	}

	private AVLNode<T> rlRotate(BSTNodeND<T> r) { // Right-Left Rotation
		r.left = rRotation(r.left);
		return lRotation(r);
	}

	private AVLNode<T> lrRotate(BSTNodeND<T> r) { // Left-Right Rotation
		r.right = lRotation(r.right);
		return rRotation(r);
	}

//	@Override
//	public String toString() {
//		if(root == null) return null;
//
//		StringBuilder sb = new StringBuilder();
//		AVLNode<T> current;
//
//		java.util.ArrayDeque<AVLNode<T>> queue = new ArrayDeque<>();
//
//		queue.add(root);
//
//		while(queue.size() > 0) {
//			current = queue.remove();
//			sb.append("[").append(current.data).append("] ");
//			if(current.left != null) queue.add(current.left);
//			if(current.right != null) queue.add(current.right);
//		}
//
//		return sb.toString();
//	}

	@Override
	public String toString() {
		if(root == null) return null;
		return toString(root);
	}

	public String toString(TreeTraversalOrder type) {
		return super.toString(type.getMap(), root);
	}

	public



	class AVLNode<T extends Comparable<? super T>> extends BSTNodeND<T> {
//		private T data;
//		private AVLNode<T> left = null, right = null, parent = null;
		private int height;

		AVLNode(T t) {
			super(t);
			height = 0;
		}
	} // end inner AVLNode class
} // end AVLTree class

enum TreeTraversalOrder {
	INORDER(1),
	PREORDER(2),
	POSTORDER(3),
	LEVELORDER(4);

	private int map;

	TreeTraversalOrder(int id) {this.map = id;}
	public int getMap() {return map;}
}
