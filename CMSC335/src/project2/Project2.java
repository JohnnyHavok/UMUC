/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Jul 10, 2010
 * Project: Project 2
 * File: Project2.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project2;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.Vector;


@SuppressWarnings("serial")
public class Project2 extends JFrame implements ActionListener {
	// Constants
	private final boolean 	DEBUG			= false; // Prints debug information to console
	private final boolean   USELIST			= false; // Switch between JList/JTable implementation, never finished leave false
	private final int 		BUTTONHEIGHT	= 29;    // Default button height
	private final int 		BUTTONWIDTH		= 110;   // default button width
	
	// Models
	private DefaultTableModel model 		= new DefaultTableModel(); // JTable Model
	private DefaultListModel  listModel     = new DefaultListModel();  // JList Model
	
	// Data Structures
	Library<Book> 			library 		= new Library<Book>();
	Vector<Vector<String>> 	rowElement		= new Vector<Vector<String>>(); // Will be a Vector of a Vector of Strings for table model.
	Vector<String> 			columnTitle 	= new Vector<String>();
	
	// Instantiate new JFrame window
	public static void main(String[] args) {
		new Project2();
	}
	
	/**
	 * This is the default constructor for the frame.
	 */
	public Project2() {
		super();
		this.setSize(477,323);
		this.setContentPane(getJContentPane());
		this.setTitle("Book Library");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent source) {
		/**
		 * All these actions, and this Java program in general, work around the thought
		 * that it is just as easy/efficent to maintain and synchronize what is in the 
		 * JTable GUI and the Library<Book> object separately as it would be to continuously 
		 * "redraw" the swing components everytime we preformed an action on the Library<Book> 
		 * object.  The data is still there in a convenient and updated Library<Book> object 
		 * in case in the future we want to add other features like persistance and file output. 
		 */
		
		/**
		 * Actions when clicking on the "Check Out" button
		 */
		if(source.getSource() == (JButton) btCheckOut) {
			if(DEBUG) {try {
				System.out.println("Button Press: btCheckOut\nBook UID: "+
												((Book) library.getAtIndex(bookTable.getSelectedRow())).getISBN());
			} catch (LibraryException e) {
				// Can fail only if DEBUG is set to true.
				System.out.println("getAtIndex out of bounds");
			}} // End DEBUG if
			
			try {
				// Check to see if we have copies available first
				Book current = ((Book)library.getAtIndex(bookTable.getSelectedRow()));
				if(current.getRemainingBooks() > 0) {
					String 	title 	= current.getTitle();
					String	author 	= current.getAuthor();
					String	isbn 	= current.getISBN();
					
					CheckOutDialog dialog = new CheckOutDialog(this, true, title, author, isbn);
					if(!dialog.getCanceled()) { // If they didn't cancel.
						current.borrowBook(dialog.getClient(), dialog.getDays());
						// Update the Swing table.
						bookTable.setValueAt(Integer.toString(current.getRemainingBooks()), bookTable.getSelectedRow(), 2);
					}
					
					if(DEBUG) {current.debugBorrowersList();}
					// Delete dialog object
					dialog = null;
				}
				else { // If we have no more copies left
					JOptionPane.showMessageDialog(this,
							"There are no more copies available to check out",
							"Insufficent Copies Available",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch(LibraryException e) { // Catches the exception from getAtIndex().
				JOptionPane.showMessageDialog(this,
						"Must select book to check out first",
						"No Book Selected",
						JOptionPane.ERROR_MESSAGE);
			}
		} // End action for "Check Out" button
		
		/**
		 * Actions when clicking on the "Return" button
		 */
		if(source.getSource() == (JButton) btReturn) {
			if(DEBUG) {System.out.println("Button Press: btReturn");}
			try {
				Book current = ((Book)library.getAtIndex(bookTable.getSelectedRow()));
				if(current.hasBorrowers()) { // Check to make sure we can return a book first.
					ArrayList<String> arrl = current.getBorrowers();
					String 	title 	= current.getTitle();
					String	author 	= current.getAuthor();
					String	isbn 	= current.getISBN();
					
					CheckInDialog dialog = new CheckInDialog(this, true, arrl, title, author, isbn);
					if(!dialog.getCanceled()) { // If they didn't cancel.
						current.returnBook(dialog.getIndex());
						// Update the Swing Table
						bookTable.setValueAt(Integer.toString(current.getRemainingBooks()), bookTable.getSelectedRow(), 2);
					}
					
					if(DEBUG) {current.debugBorrowersList();}
					// Delete dialog object
					dialog = null;
				} else { // No borrowers for this book yet.
					JOptionPane.showMessageDialog(this,
							"There are none of these books checked out yet",
							"No Books Checked Out",
							JOptionPane.ERROR_MESSAGE);
					
				}	
			} catch(LibraryException e) { // Catches the exception from getAtIndex().
				JOptionPane.showMessageDialog(this,
						"Must select book to check in first",
						"No Book Selected",
						JOptionPane.ERROR_MESSAGE);
			}
		} // End action for "Return" button
		
		/** 
		 * Actiosn when clicking on the "Delete" button
		 */
		if(source.getSource() == (JButton) btDelete) {
			if(DEBUG) {System.out.println("Button Press: btDelete\nRow Selected: " + bookTable.getSelectedRow());}
			try { // If user doesn't select a row an out of bounds exception will get thrown
				library.deleteAtIndex(bookTable.getSelectedRow());
				deleteRow(bookTable.getSelectedRow());
				if(DEBUG) {System.out.println("Removed Book");debugPrintLibrary();}
			} catch (IndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(this,
						"Must select book to delete first",
						"No Book Selected",
						JOptionPane.ERROR_MESSAGE);
			} catch (LibraryException e) {
				// Should not be reachable.
				e.printStackTrace();
			}
		} // End action for "Delete" button
		
		/**
		 * Actions when clicking on the "Exit" button
		 */
		if(source.getSource() == (JButton) btExit) {
			if(DEBUG) {System.out.println("Button Press: btExit");}
			library = null;
			rowElement = null;
			columnTitle = null;
			this.dispose();
		} // End action for "Exit" button
		
		/**
		 *  Actions when clicking on the "Add" button
		 */
		if(source.getSource() == (JButton) btAdd) {
			if(DEBUG) {System.out.println("Button Press: btAdd");}
			Boolean make 	= true;
			String 	author 	= null,
					title  	= null,
					isbn   	= null;
			
			// Check to see if an author is given
			if(!txtAuthor.getText().isEmpty()) {
				author = txtAuthor.getText();
			} else { // Display error message
				make = false;
				JOptionPane.showMessageDialog(this,
						"Cannot add book without Author",
						"No Author Given",
						JOptionPane.ERROR_MESSAGE);
			}
			
			// Check to see if a title is given.
			if(!txtTitle.getText().isEmpty()) { 
				title = txtTitle.getText();
			} else { // Display error message
				make = false;
				JOptionPane.showMessageDialog(this,
						"Cannot add book without Title",
						"No Title Given",
						JOptionPane.ERROR_MESSAGE);
			}
			
			// Check to see if a title is given.
			if(!txtISBN.getText().isEmpty()) { 
				isbn = txtISBN.getText();
			} else { // Display error message
				make = false;
				JOptionPane.showMessageDialog(this,
						"Cannot add book without ISBN",
						"No Title Given",
						JOptionPane.ERROR_MESSAGE);
			}
			
			// Set copies from drop down index plus 1
			int c = listCopies.getSelectedIndex() + 1;
			
			if(make) { // make goes false if something is missing from the text fields.
				try {
					Book b = new Book(author, title, isbn);
					b.setNumberOfBooks(c);
					b.setRemainingBooks(c);
					library.add(b, isbn);
					addRow(b);
					txtAuthor.setText(null);
					txtTitle.setText(null);
					txtISBN.setText(null);
					listCopies.setSelectedIndex(0);
					if(DEBUG) {System.out.println("Add action sucess"); debugPrintLibrary();}
				} catch(LibraryException e) {
					JOptionPane.showMessageDialog(this,
							"The ISBN for this book already exist in library",
							e.getMessage(),
							JOptionPane.ERROR_MESSAGE);
					if(DEBUG) {System.out.println("Duplicate ISBN"); debugPrintLibrary();}
				}
			} else if(DEBUG) {System.out.println("Add action failed"); debugPrintLibrary();}
			
		} // End action for "Add" button
	} // End public void actionPerformed()
	
	public void deleteRow(int i) {
		rowElement.removeElementAt(i);
		bookTable.clearSelection(); // Need to clear because getSelectedRow() will 
									// still hold index number on empty table (IndexOutOfBoundsException)
		bookTable.repaint();        // Repaint to visually get rid of row.
	}
	public void addRow(Book b) {
		Vector<String> t = new Vector<String>();
		t.addElement(b.getTitle());        
		t.addElement(b.getAuthor());       
		t.addElement(String.valueOf(b.getRemainingBooks()));
		t.addElement(b.getISBN());
		rowElement.addElement(t);
		bookTable.addNotify();
	}
	public void makeList() {
		// TODO: Implement JList version of GUI, never completed for Project 1
	}
	
	/**
	 * The following private methods setup the GUI elements
	 * Methods automatically generated by Eclipse Visual Editor
	 * and then modified by hand to fit needs
	 * 
	 * The default constructor will call getJContentPane which will 
	 * will create a new JPanel ontop of the frame and then place 
	 * all the other JPanels into their correct locations.  There is a
	 * panel for the button area, table area, and text entry area.
	 * The same thing goes for all the buttons and text areas they will
	 * be called into place by their "controlling" panels.
	 * 
	 * Each getComponent() style command creates a new swing object
	 * that is defined at the bottom of this file.
	 */
	private JPanel			getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getBtPanel(), BorderLayout.EAST);
			jContentPane.add(getEntryPanel(), BorderLayout.SOUTH);
			jContentPane.add(getTablePanel(), BorderLayout.WEST);
		}
		return jContentPane;
	}
	private JPanel 			getBtPanel() {

		if (btPanel == null) {
			GridBagConstraints c0 = new GridBagConstraints();
			c0.gridy = 0;
			c0.anchor = GridBagConstraints.NORTH;
			c0.gridx = 0;
			
			GridBagConstraints c1 = new GridBagConstraints();
			c1.gridy = 3;
			c1.gridx = 0;
			
			GridBagConstraints c2 = new GridBagConstraints();
			c2.gridy = 4;
			c2.insets = new Insets(35, 0, 0, 0);
			c2.gridx = 0;
			
			GridBagConstraints c3 = new GridBagConstraints();
			c3.gridy = 5;
			c3.insets = new Insets(35, 0, 0, 0);
			c3.gridx = 0;
			
			btPanel = new JPanel();
			btPanel.setLayout(new GridBagLayout());
			btPanel.add(getBtCheckOut(), c0);
			btPanel.add(getBtReturn(), c1);
			btPanel.add(getBtDelete(), c2);
			btPanel.add(getBtExit(), c3);
		}
		return btPanel;
	}
	private JPanel 			getEntryPanel() {
		if (entryPanel == null) {
			GridBagConstraints cLabelAuthor = new GridBagConstraints();
			cLabelAuthor.anchor = GridBagConstraints.EAST;
			cLabelAuthor.gridx = 0;
			cLabelAuthor.gridy = 0;
			
			GridBagConstraints cLabelTitle = new GridBagConstraints();
			cLabelTitle.gridx = 0;
			cLabelTitle.anchor = GridBagConstraints.EAST;
			cLabelTitle.gridy = 1;
			
			GridBagConstraints cLabelCopies = new GridBagConstraints();
			cLabelCopies.gridx = 0;
			cLabelCopies.gridy = 2;
			cLabelCopies.anchor = GridBagConstraints.EAST;
			
			GridBagConstraints cLabelISBN = new GridBagConstraints();
			cLabelISBN.gridx = 0;
			cLabelISBN.gridy = 3;
			cLabelISBN.anchor = GridBagConstraints.EAST;
			
			GridBagConstraints cTxtAuthor = new GridBagConstraints();
			cTxtAuthor.fill = GridBagConstraints.VERTICAL;
			cTxtAuthor.gridx = 1;
			cTxtAuthor.gridy = 0;
			cTxtAuthor.anchor = GridBagConstraints.WEST;
			cTxtAuthor.weightx = 1.0;
			
			GridBagConstraints cTxtTitle = new GridBagConstraints();
			cTxtTitle.fill = GridBagConstraints.VERTICAL;
			cTxtTitle.gridx = 1;
			cTxtTitle.gridy = 1;
			cTxtTitle.anchor = GridBagConstraints.WEST;
			cTxtTitle.weightx = 1.0;
			
			GridBagConstraints cListCopies = new GridBagConstraints();
			cListCopies.fill = GridBagConstraints.VERTICAL;
			cListCopies.gridx = 1;
			cListCopies.gridy = 2;
			cListCopies.anchor = GridBagConstraints.WEST;
			cListCopies.weightx = 1.0;
			
			GridBagConstraints cTxtISBN = new GridBagConstraints();
			cTxtISBN.gridx = 1;
			cTxtISBN.gridy = 3;
			cTxtISBN.anchor = GridBagConstraints.WEST;
			
			GridBagConstraints cBtAdd = new GridBagConstraints();
			cBtAdd.gridx = 2;
			cBtAdd.gridy = 1;

			jLabelAuthor = new JLabel();
			jLabelAuthor.setText("Author");
			
			jLabelTitle = new JLabel();
			jLabelTitle.setText("Title");
			
			jLabelCopies = new JLabel();
			jLabelCopies.setText("Copies");
			
			jLabelISBN = new JLabel();
			jLabelISBN.setText("ISBN");
			
			entryPanel = new JPanel();
			entryPanel.setLayout(new GridBagLayout());
			entryPanel.add(jLabelAuthor, cLabelAuthor);
			entryPanel.add(getTxtAuthor(), cTxtAuthor);
			entryPanel.add(jLabelTitle, cLabelTitle);
			entryPanel.add(getTxtTitle(), cTxtTitle);
			entryPanel.add(jLabelCopies, cLabelCopies);
			entryPanel.add(getListCopies(), cListCopies);
			entryPanel.add(getTxtISBN(), cTxtISBN);
			entryPanel.add(jLabelISBN, cLabelISBN);
			entryPanel.add(getBtAdd(), cBtAdd);
		}
		return entryPanel;
	}
	private JPanel 			getTablePanel() {
		if (tablePanel == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			jLabelBooks = new JLabel();
			jLabelBooks.setText("Books");
			
			GridBagConstraints cLabelBooks = new GridBagConstraints();
			cLabelBooks.gridx = 0;
			cLabelBooks.gridy = 0;
			
			GridBagConstraints cTableScrollPane = new GridBagConstraints();
			cTableScrollPane.fill = GridBagConstraints.BOTH;
			cTableScrollPane.weighty = 1.0;
			cTableScrollPane.weightx = 1.0;
			cTableScrollPane.gridx = 0;
			cTableScrollPane.gridy = 1;
			
			tablePanel = new JPanel();
			tablePanel.setLayout(new GridBagLayout());
			tablePanel.add(jLabelBooks, gridBagConstraints);
			tablePanel.add(getTableScrollPane(), cTableScrollPane);
		}
		return tablePanel;
	}
	private JButton 		getBtCheckOut() {
		if (btCheckOut == null) {
			btCheckOut = new JButton();
			btCheckOut.setText("Check Out");
			btCheckOut.setPreferredSize(new java.awt.Dimension(BUTTONWIDTH, BUTTONHEIGHT));
			btCheckOut.addActionListener(this);
		}
		return btCheckOut;
	}
	private JButton 		getBtReturn() {
		if (btReturn == null) {
			btReturn = new JButton();
			btReturn.setText("Return");
			btReturn.setPreferredSize(new java.awt.Dimension(BUTTONWIDTH, BUTTONHEIGHT));
			btReturn.addActionListener(this);
		}
		return btReturn;
	}
	private JButton 		getBtAdd() {
		if (btAdd == null) {
			btAdd = new JButton();
			btAdd.setText("Add");
			btAdd.addActionListener(this);
		}
		return btAdd;
	}
	private JButton 		getBtDelete() {
		if (btDelete == null) {
			btDelete = new JButton();
			btDelete.setText("Delete");
			btDelete.setPreferredSize(new java.awt.Dimension(BUTTONWIDTH, BUTTONHEIGHT));
			btDelete.addActionListener(this);
		}
		return btDelete;
	}
	private JButton 		getBtExit() {
		if (btExit == null) {
			btExit = new JButton();
			btExit.setText("Exit");
			btExit.setPreferredSize(new java.awt.Dimension(BUTTONWIDTH, BUTTONHEIGHT));
			btExit.addActionListener(this);
		}
		return btExit;
	}
	private JTextField 		getTxtAuthor() {
		if (txtAuthor == null) {
			txtAuthor = new JTextField();
			txtAuthor.setColumns(25);
		}
		return txtAuthor;
	}
	private JTextField 		getTxtTitle() {
		if (txtTitle == null) {
			txtTitle = new JTextField();
			txtTitle.setColumns(25);
		}
		return txtTitle;
	}
	private JTextField		getTxtISBN() {
		if(txtISBN == null) {
			txtISBN = new JTextField();
			txtISBN.setColumns(25);
		}
		return txtISBN;
	}
	private JScrollPane 	getTableScrollPane() {
		if (tableScrollPane == null) {
			tableScrollPane = new JScrollPane();
			if(!USELIST) { // For Project 1, completed using JTable
				tableScrollPane.setViewportView(getBookTable());
			} else { // If down the road its better to use JList then we can set USELIST to true
				tableScrollPane.setViewportView(getBookList());
				tableScrollPane.setPreferredSize(new java.awt.Dimension(350,75));
			}
		}
		return tableScrollPane;
	}
	private JComboBox 		getListCopies() {
		if(listCopies == null) {
			listCopies = new JComboBox();
			for(int i = 1; i <= 10; i++)
				listCopies.addItem((Integer) i);
		}
		return listCopies;
	}
	private JList			getBookList() {
		if(bookList == null) {
			bookList = new JList(listModel);
			bookList.setPreferredSize(new java.awt.Dimension(340,75));
		}
		return bookList;
	}
	private JTable 			getBookTable() {
		if (bookTable == null) {
			columnTitle.addElement("Title");
			columnTitle.addElement("Author");
			columnTitle.addElement("Copies");
			columnTitle.addElement("ISBN");
			
			bookTable = new JTable(model);
			bookTable.setPreferredScrollableViewportSize(new java.awt.Dimension(350,75));
			bookTable.setRowSelectionAllowed(true);
			
			model.setDataVector(rowElement, columnTitle);
		}
		return bookTable;
	}
	
	/**
	 * Swing Objects, automatically generated by Eclipse Visual Editor.
	 */
	private JPanel 		jContentPane 		= null;
	private JPanel 		btPanel 			= null;
	private JPanel 		entryPanel 			= null;
	private JPanel 		tablePanel 			= null;
	private JButton 	btCheckOut 			= null;
	private JButton 	btReturn 			= null;
	private JButton	 	btAdd 				= null;
	private JButton 	btDelete 			= null;
	private JButton 	btExit 				= null;
	private JLabel 		jLabelAuthor 		= null;
	private JLabel 		jLabelTitle 		= null;
	private JLabel 		jLabelBooks 		= null;
	private JLabel 		jLabelCopies 		= null;
	private JLabel		jLabelISBN			= null;
	private JTextField 	txtAuthor 			= null;
	private JTextField 	txtTitle		 	= null;
	private JTextField	txtISBN				= null;
	private JScrollPane tableScrollPane 	= null;
	private JTable 		bookTable 			= null;
	private JComboBox 	listCopies 			= null;
	private JList		bookList			= null;
	
	/**
	 * Debug methods
	 * @return 
	 */
	private void debugPrintLibrary() {
		System.out.println("Current Library is: ");
	
		if(library.size() != 0) {
			for(int i = 0; i < library.size(); i++) {
				try {
					System.out.println(library.getAtIndex(i).toString());
				} catch (LibraryException e) {
					// Should be unreachable, we check library.size() already.
					System.out.println("Problem in debugPrintLibrary()");
					e.printStackTrace();
				}
			}
		} else { 
			System.out.println("Empty"); 
		}
	}
}