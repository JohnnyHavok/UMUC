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
	AVLTree<String> avlt = new AVLTree<>();

	avlt.insert("Test");

	BinarySearchTreeND<String> bt = new BinarySearchTreeND<>();
	bt.insert("Test");

	List <MyTreeIF> nodes = new ArrayList <> ();
	Object data;

	public static void main (String args []) {
		Project4 tt1 = new Project4 ();
		tt1.setValuesA ();
		MyDrawTreeFrame mdtf1 = new MyDrawTreeFrame (tt1, "close this frame to exit");
//		TestTree tt2 =  new TestTree ();
//		tt2.setValuesB ();
//		MyDrawTreeFrame mdtf2 = new MyDrawTreeFrame (tt2, "test 1", false);
//		TestTree tt3 = new TestTree ();
//		tt3.setValuesC ();
//		MyDrawTreeFrame mdtf3 = new MyDrawTreeFrame (tt3, "test 2", false);
//		TestTree tt4 = new TestTree ();
//		tt4.setValuesD ();
//		MyDrawTreeFrame mdtf4 = new MyDrawTreeFrame (tt4, "test 3", false);
	} // end main

	Project4 () {
	} // end constructor

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

//	void setValuesB () {
//		data = "A";
//		TestTree a, b, c, d, e, f, g, h, i, j, k, r = this;
//		r.nodes.add (a = new TestTree ("B"));
//		a.nodes.add (b = new TestTree ("C"));
//		a.nodes.add (b = new TestTree ("D"));
//		a.nodes.add (b = new TestTree ("E"));
//		r.nodes.add (a = new TestTree ("F"));
//		r.nodes.add (a = new TestTree ("G"));
//		r.nodes.add (a = new TestTree ("H"));
//		a.nodes.add (b = new TestTree ("I"));
//		b.nodes.add (c = new TestTree ("I"));
//		c.nodes.add (d = new TestTree ("K"));
//		d.nodes.add (e = new TestTree ("L"));
//		e.nodes.add (f = new TestTree ("M"));
//		f.nodes.add (g = new TestTree ("N"));
//		g.nodes.add (h = new TestTree ("O"));
//		h.nodes.add (i = new TestTree ("P"));
//		i.nodes.add (j = new TestTree ("Q"));
//		j.nodes.add (k = new TestTree ("R"));
//	} // end setValuesB
//
//	void setValuesC () {
//		data = 1;
//		TestTree a, b, c, d, e, f, g, h, i, j, k, r = this;
//		r.nodes.add (a = new TestTree (2));
//		a.nodes.add (b = new TestTree (3));
//		a.nodes.add (b = new TestTree (4));
//		a.nodes.add (b = new TestTree (5));
//		r.nodes.add (a = new TestTree (6));
//		r.nodes.add (a = new TestTree (7));
//		r.nodes.add (a = new TestTree (8));
//		a.nodes.add (b = new TestTree (9));
//		b.nodes.add (c = new TestTree (10));
//		c.nodes.add (d = new TestTree (11));
//		d.nodes.add (e = new TestTree (12));
//		e.nodes.add (f = new TestTree (13));
//		e.nodes.add (g = new TestTree (14));
//		e.nodes.add (h = new TestTree (15));
//		e.nodes.add (i = new TestTree (16));
//		i.nodes.add (j = new TestTree (17));
//		j.nodes.add (k = new TestTree (18));
//	} // end setValuesC
//
//	void setValuesD () {
//		data = 1;
//		TestTree a, b, c, d, e, f, g, h, i, j, k, r = this;
//	} // end setValuesD

	public List <MyTreeIF> getChildren() {
		return nodes;
	} // end method getChildren;

	public String toString () {return data.toString();}
}
