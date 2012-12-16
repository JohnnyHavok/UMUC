package cmsc350.project4;

/**
 * Name: Justin Smith
 * CMSC 350
 * Project: 4
 * Date: 12/15/12 1:42 PM
 * Requires: J2SE 7+
 */

import java.util.List;
import java.util.ArrayList;

public class Project4 implements  MyTreeIF {
	List <MyTreeIF> nodes = new ArrayList <> ();
	Object data;

	public static void main (String[] args) {
//		AVLTree<String> avlt = new AVLTree<>();
//		String[] a = {"M", "N", "P", "O", "Q"};

//		for(String s : a) {
//			avlt.insert(s);
//			System.out.println(avlt.toString(TreeTraversalOrder.LEVELORDER));
//			System.out.println(avlt);
//		}


//		Integer[] a = {5, 3, 10, 13, 7, 15}; // - Make left rotation
//		Integer[] a = {13, 7, 15, 5, 10, 3}; // - Make right rotation
//		Integer[] a = {13, 5, 15, 3, 7, 10}; // - Make left-right rotation
		Integer[] a = {5, 3, 13, 10, 15, 7}; // - Make right-left rotation
//		for(Integer i : a) {
//			avlt.insert(i);
//		}

		AVLTree<Integer> avlt = new AVLTree<>(a);


		Project4 p = new Project4();
		p.setValuesA();
		System.out.print("P's Nodes> ");
		System.out.println(p.nodes.toString());

		MyDrawTreeFrame mdtf1 = new MyDrawTreeFrame (p, "close this frame to exit");
	}

	Project4 () {

	}

	Project4 (Object n) {
		data = n;
	} // end int constructor

	void setValuesA () {
		data = "A";
		Project4 a, b, c, d, e, f, g, h, i, j, k, r = this;
		r.nodes.add (a = new Project4 ("B"));
		a.nodes.add (b = new Project4 ("C"));
		a.nodes.add (b = new Project4 ("D"));
		a.nodes.add (b = new Project4 ("E"));
		r.nodes.add (a = new Project4 ("F"));
		r.nodes.add (a = new Project4 ("G"));
		r.nodes.add (a = new Project4 ("H"));
		a.nodes.add (b = new Project4 ("I"));
		b.nodes.add (c = new Project4 ("I"));
		c.nodes.add (d = new Project4 ("K"));
		d.nodes.add (e = new Project4 ("L"));
		e.nodes.add (f = new Project4 ("M"));
		f.nodes.add (g = new Project4 ("N"));
		g.nodes.add (h = new Project4 ("O"));
		h.nodes.add (i = new Project4 ("P"));
		i.nodes.add (j = new Project4 ("Q"));
		j.nodes.add (k = new Project4 ("R"));
	} // end setValuesA

	/**
	 * TODO - Fix this to return a list of nodes from the AVLTree.
	 */
	public List <MyTreeIF> getChildren() {
		return nodes;
	} // end method getChildren;

//	/**
//	 * TODO - Fix this or remove this if not needed to print from the AVLTree.
//	 * @return
//	 */
	public String toString () {return data.toString();}
}
