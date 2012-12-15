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
import cmsc350.project4.ndsource.*;

public class Project4 implements  MyTreeIF {
	List <MyTreeIF> nodes = new ArrayList <> ();
	Object data;

	public static void main (String args []) {
		AVLTree<String> avlt = new AVLTree<>();
		avlt.insert("M");
		avlt.insert("N");
		avlt.insert("P");
		avlt.insert("O");
		avlt.insert("Q");
		System.out.println(avlt);

//		Project4 tt1 = new Project4 ();
//		tt1.setValuesA ();
//		MyDrawTreeFrame mdtf1 = new MyDrawTreeFrame (tt1, "close this frame to exit");
	}

	Project4 (Object n) {
		data = n;
	} // end int constructor

	void setValuesA () {
		data = "1 - one";
		Project4 a, b, c, d, e, f, g, h, i, j, k, r = this;
		r.nodes.add (a = new Project4 ("1.1 - first"));
		a.nodes.add (b = new Project4 ("1.1.1 - first"));
		a.nodes.add (b = new Project4 ("1.1.2 - second"));
		a.nodes.add (b = new Project4 ("1.1.3 - third"));
		r.nodes.add (a = new Project4 ("1.2 - second"));
		r.nodes.add (a = new Project4 ("1.3 - third"));
		r.nodes.add (a = new Project4 ("1.4 - fourth"));
		a.nodes.add (b = new Project4 ("1.4.1 - first"));
		b.nodes.add (c = new Project4 ("1.4.1.1 - first"));
		c.nodes.add (d = new Project4 ("1.4.1.1.1 - first"));
		d.nodes.add (e = new Project4 ("1.4.1.1.1.1 - first"));
		e.nodes.add (f = new Project4 ("1.4.1.1.1.1.1 - first"));
		e.nodes.add (g = new Project4 ("1.4.1.1.1.1.2 - second"));
		e.nodes.add (h = new Project4 ("1.4.1.1.1.1.3 - third"));
		e.nodes.add (i = new Project4 ("1.4.1.1.1.1.4 - fourth"));
		i.nodes.add (j = new Project4 ("1.4.1.1.1.1.4.1 - first"));
		j.nodes.add (k = new Project4 ("1.4.1.1.1.1.4.1.1 - first"));
	} // end setValuesA

	public List <MyTreeIF> getChildren() {
		return nodes;
	} // end method getChildren;

	public String toString () {return data.toString();}
}
