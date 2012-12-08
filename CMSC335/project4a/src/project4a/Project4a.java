/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Aug 7, 2010
 * Project: Project 4a
 * File: Project4a.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project4a;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.filechooser.FileFilter;

import java.util.ArrayList;
import java.util.Vector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;


@SuppressWarnings("serial")
public class Project4a extends JFrame implements ActionListener {
	// Constants
	private final boolean 	DEBUG			= false;      // Prints debug information to console
	private final String    FILE_EXT		= "libdata";  // Default file extension
	private final int 		BUTTONHEIGHT	= 29;         // Default button height
	private final int 		BUTTONWIDTH		= 110;        // Default button width
	private final int		FRAMEHEIGHT		= 420;		  // Default program frame height
	private final int		FRAMEWIDTH		= 550;        // Default program frame width
	
	// Models
	private DefaultTableModel model 		= new DefaultTableModel(); // JTable Model
	
	// Data Structures
	Library<Book> 			library 		= new Library<Book>();
	Vector<Vector<String>> 	rowElement		= new Vector<Vector<String>>();
	Vector<String> 			columnTitle 	= new Vector<String>();
	
	/**
	 * Enumeration to help organize books types.
	 * Getter that will return an attribute title for a book and a
	 * getter that will return a concrete instance of a specific book type.
	 */
	public enum BookType {
		/**
		 * To add a new genre of book to the program you add a new enumeration 
		 * element below in the format of 'GenreName ("Genre Attribute Title")'.
		 * Then add a new case statement to each of the switch blocks below.
		 */
		Literature ("Fiction Type"),
		Science ("Topic"),
		Childrens ("Age");
		
		private final String attribute;
		
		// Constructor for the enumeration types above
		BookType(String s) { attribute = s; }
		
		// Returns the Attribute Title for the BookType
		public static String getAttributeTitle(BookType bt) { return bt.attribute; }
		
		// Returns a concrete book instance based on which enumeration was passed
		public static Book getBook(BookType bt) {
			switch(bt) {
				case Literature: 	return new GenericBook();
				case Science:		return new ScienceBook();
				case Childrens:		return new ChildrenBook();
				default:			return new GenericBook();
			}
		}
	}
	
	// Instantiate new JFrame window
	public static void main(String[] args) {
		new Project4a();
	}
	
	
	
	/**
	 * This is the default constructor for the frame.
	 */
	public Project4a() {
		super();
		this.setSize(FRAMEWIDTH, FRAMEHEIGHT);
		this.setContentPane(getJContentPane());
		this.setTitle("Book Library");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	/** 
	 * actionPerformed method receives ActionEvents from all the registered swing components
	 * and based on the source of an incoming event we do certain actions.
	 */

	@Override
	public void actionPerformed(ActionEvent source) {
		/**
		 * All these actions, and this Java program in general, work around the thought
		 * that it is just as easy/efficient to maintain and synchronize what is in the 
		 * JTable GUI and the Library<Book> object separately as it would be to continuously 
		 * "redraw" the swing components every time we performed an action on the Library<Book> 
		 * object.  The data is still there in a convenient and updated Library<Book> object 
		 * in case in the future we want to add other features like persistence and file output. 
		 */
		
		/**
		 * Actions when clicking on the "Load Library" button
		 */
		if(source.getSource() == (JButton) btLoad) {
			if(DEBUG) {System.out.println("Button Press: btLoad");}
			
			JFileChooser jfc = new JFileChooser();
			jfc.setFileFilter(new LibDataFileFilter());
			int result = jfc.showOpenDialog(this);
			if(result == JFileChooser.APPROVE_OPTION) {
				if(DEBUG) {System.out.println("File Load Selected: " + jfc.getSelectedFile());}
				File f = jfc.getSelectedFile();
				loadLibrary(f);
			}
		}
		
		/**
		 * Actions when clicking on the "Save Library" button
		 */
		if(source.getSource() == (JButton) btSave) {
			if(DEBUG) {System.out.println("Button Press: btSave");}
			
			JFileChooser jfc = new JFileChooser();
			jfc.setFileFilter(new LibDataFileFilter());
			int result = jfc.showSaveDialog(this);
			if(result == JFileChooser.APPROVE_OPTION) {
				if(DEBUG) {System.out.println("File Save Selected: " + jfc.getSelectedFile());}
				File f = jfc.getSelectedFile();
				saveLibrary(f);
			}
		}
		
		/**
		 * Actions when clicking on the "Check Out" button
		 */
		if(source.getSource() == (JButton) btCheckOut) {
			if(DEBUG) {try {
				String bookID = (String) bookTable.getValueAt(bookTable.getSelectedRow(), 3);
				System.out.println("Button Press: btCheckOut\nBook UID: "+
												((BookInterface) library.getAtUID(bookID)).getISBN());
			} catch (LibraryException e) {
				// Can fail only if DEBUG is set to true.
				System.out.println("getAtUID not found in collection");
			} catch(ArrayIndexOutOfBoundsException e) { // Catches OOB Exception if an item in the table isn't selected.
				System.out.println("OOB Exception, item not selected");
			}} // End DEBUG if
			
			try {
				// Check to see if we have copies available first
				String bookID = (String) bookTable.getValueAt(bookTable.getSelectedRow(), 3);
				BookInterface current = ((BookInterface)library.getAtUID(bookID));
				if(library.getInventoryCount(bookID) > 0) {
					String 	title 	= current.getTitle();
					String	author 	= current.getAuthor();
					String	isbn 	= current.getISBN();
					
					CheckOutDialog dialog = new CheckOutDialog(this, true, title, author, isbn);
					if(!dialog.getCanceled()) { // If they didn't cancel.
						library.checkOut(isbn, dialog.getClient(), dialog.getDays());
						// Update the Swing table.
						bookTable.setValueAt(Integer.toString(library.getInventoryCount(bookID)), bookTable.getSelectedRow(), 2);
					}
					
					if(DEBUG) {library.debugBorrowersList(bookID);}
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
			} catch(ArrayIndexOutOfBoundsException e) { // Catches OOB Exception if an item in the table isn't selected.
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
				String bookID = (String) bookTable.getValueAt(bookTable.getSelectedRow(), 3);
				BookInterface current = ((BookInterface)library.getAtUID(bookID));

				if(library.hasBorrowers(bookID)) { // Check to make sure we can return a book first.
					ArrayList<String> arrl = library.getBorrowers(bookID);
					String 	title 	= current.getTitle();
					String	author 	= current.getAuthor();
					String	isbn 	= current.getISBN();
					
					CheckInDialog dialog = new CheckInDialog(this, true, arrl, title, author, isbn);
					if(!dialog.getCanceled()) { // If they didn't cancel.
						library.checkIn(bookID, dialog.getIndex());
						// Update the Swing Table
						bookTable.setValueAt(Integer.toString(library.getInventoryCount(bookID)), bookTable.getSelectedRow(), 2);
					}
					
					if(DEBUG) {library.debugBorrowersList(bookID);}
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
			} catch(ArrayIndexOutOfBoundsException e) { // Catches OOB Exception if an item in the table isn't selected.
				JOptionPane.showMessageDialog(this,
						"Must select book to check out first",
						"No Book Selected",
						JOptionPane.ERROR_MESSAGE);
			}
		} // End action for "Return" button
		
		/** 
		 * Actions when clicking on the "Delete" button
		 */
		if(source.getSource() == (JButton) btDelete) {
			if(DEBUG) {System.out.println("Button Press: btDelete\nRow Selected: " + bookTable.getSelectedRow());}
			try { // If user doesn't select a row an out of bounds exception will get thrown
				String bookID = (String) bookTable.getValueAt(bookTable.getSelectedRow(), 3);
				library.delete(bookID);
				deleteRow(bookTable.getSelectedRow());
				if(DEBUG) {System.out.println("Removed Book");debugPrintLibrary();}
			} catch (IndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(this,
						"Must select book to delete first",
						"No Book Selected",
						JOptionPane.ERROR_MESSAGE);
			} catch (LibraryException e) {
				JOptionPane.showMessageDialog(this,
						"The book you tried to delete was not in the library",
						"Book not found",
						JOptionPane.ERROR_MESSAGE);
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
					isbn   	= null,
					attr	= null;
			
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
						"No ISBN Given",
						JOptionPane.ERROR_MESSAGE);
			}
			
			// Check to see if a genre attribute is given.
			if(!txtAttribute.getText().isEmpty()) {
				attr = txtAttribute.getText();
			} else { // Display error message
				make = false;
				JOptionPane.showMessageDialog(this,
						"Cannot add book without Genre Attribute",
						"No Genre Attribute Given",
						JOptionPane.ERROR_MESSAGE);
			}
			
			// Set copies from drop down index plus 1
			int copies = listCopies.getSelectedIndex() + 1;

			
			if(make) { // make goes false if something is missing from the text fields.
				try {
					Book b = BookType.getBook((BookType) listGenres.getSelectedItem());
					b.setAuthor(author);
					b.setTitle(title);
					b.setISBN(isbn);
					b.setAttribute(attr);
					library.add(b, isbn, copies);
					addRow(b);
					txtAuthor.setText(null);
					txtTitle.setText(null);
					txtISBN.setText(null);
					txtAttribute.setText(null);
					listCopies.setSelectedIndex(0);
					listGenres.setSelectedIndex(0);
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
		
		/**
		 * Actions when genre list is changed
		 */
		if(source.getSource() == (JComboBox) listGenres) {
			updateAttributeLabel();
		}
	} // End public void actionPerformed()
	
	/**
	 * Helper method that changes the Genre Attribute Label based on which genre is selected from the
	 * listGenres combo box.
	 */
	public void updateAttributeLabel() {
		// Snatch the enum from the combo box drop down and ask the enum class what string value to display
		jLabelAttribute.setText(BookType.getAttributeTitle((BookType) listGenres.getSelectedItem()));
	}
	
	/**
	 * Helper method that removes a row of data from the program's bookTable swing object
	 * Input is an integer that corresponds to the index of the row to remove.
	 * @param i
	 */
	public void deleteRow(int i) {
		rowElement.removeElementAt(i);
		bookTable.clearSelection(); // Need to clear because getSelectedRow() will 
									// still hold index number on empty table (IndexOutOfBoundsException)
		bookTable.repaint();        // Repaint to visually get rid of row.
	}
	
	/**
	 * Helper method that adds a row of data to the program's bookTable swing object
	 * @param b
	 */
	public void addRow(Book b) {
		Vector<String> t = new Vector<String>();
		t.addElement(b.getTitle());        
		t.addElement(b.getAuthor());       
		
		try {
			t.addElement(String.valueOf(library.getInventoryCount(b.getISBN())));
		} catch (LibraryException e) {e.printStackTrace();} // Book was added to library before addRow called.
															// Should be there.
		t.addElement(b.getISBN());
		t.addElement(b.getGenre());
		rowElement.addElement(t);
		bookTable.addNotify();
	}
	
	/**
	 * Helper method that takes a file handle, opens and saves object data to it
	 * @param filehandle
	 */
	public void saveLibrary(File filehandle) {
		String s = checkExtension(filehandle.getAbsolutePath());
		if(DEBUG) {System.out.println("File Actual: "+s);}
		
		try {
			FileOutputStream fout = new FileOutputStream(s);
			ObjectOutputStream oout = new ObjectOutputStream(fout);
			oout.writeObject(library);
			oout.close();
			fout.close();
		} catch (FileNotFoundException e) {
			// Required by FileOutputStream(), I don't know how it could get thrown.
			System.out.println("File Error in saveLibrary()");
			e.printStackTrace();
		} catch (IOException e) {
			// Required by ObjectOutputStream(), catches if an object isn't serialized.
			System.out.println("Object Output Stream Error in saveLibrary()");
			e.printStackTrace();
		}
	}
	
	/**
	 * Helper method loadLibrary takes a file handle, opens and imports object data
	 * @param filehandle
	 */
	@SuppressWarnings("unchecked")
	public void loadLibrary(File filehandle) {
		String s = checkExtension(filehandle.getAbsolutePath());
		if(DEBUG) {System.out.println("File Actual: "+s);}
		
		try {
			FileInputStream fin = new FileInputStream(s);
			ObjectInputStream oin = new ObjectInputStream(fin);
			/**
			 * There is a safety issue in this block.  I do not know how to check
			 * to see if the object we are reading in is actually a Library of Book
			 * Library<Book>.  So I cast it and hope for the best.
			 */
			Object read = oin.readObject();
			
			if(read instanceof Library<?>) {
				rowElement.removeAllElements();
				library = (Library<Book>) read;
				// Add books to bookTable's rowElement Vector
				Object[] bArray = library.toArray();
				for(Object b : bArray)
					addRow((Book) b);
			} else {
				if(DEBUG) {System.out.println("File loaded was not a Library object");}
				JOptionPane.showMessageDialog(this,
						"File did not contain Library data, please choose another file",
						"Error Loading File",
						JOptionPane.ERROR_MESSAGE);
			}
			oin.close();
			fin.close();
		} catch (FileNotFoundException e) {
			if(DEBUG) {System.out.println("File not found in loadLibrary(), FileNotFoundException caught");}
			JOptionPane.showMessageDialog(this,
					"Cannot load library file",
					"File Not Found",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			// If IO Error occurs while reading stream
			if(DEBUG) {System.out.println("File had IOException error, IOException caught");e.printStackTrace();}
			JOptionPane.showMessageDialog(this,
					"There was an error trying to load the library file, please try again.",
					"Error Loading File",
					JOptionPane.ERROR_MESSAGE);
		} catch (ClassNotFoundException e) {
			// If the class of a serialized object cannot be found.
			if(DEBUG) {System.out.println("File might be corrupt or not a library file, ClassNotFoundException caught");}
			JOptionPane.showMessageDialog(this,
					"There was an error in the library file, file may be corrupted.\nPlease try again.",
					"Error Loading File",
					JOptionPane.ERROR_MESSAGE);
		} catch(ClassCastException e) {
			if(DEBUG) {System.out.println("File loaded was not a Library object, ClassCastException caught");}
			JOptionPane.showMessageDialog(this,
					"File did not contain Library data, please choose another file",
					"Error Loading File",
					JOptionPane.ERROR_MESSAGE);
		} catch(Exception e) {		
			if(DEBUG) {System.out.println("File loadeding caused an unknown exception, Exception caught");}
			JOptionPane.showMessageDialog(this,
					"There was an error trying to load the library file, please try again.",
					"Error Loading File",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Helper method that checks to see if the .libdata extension is present
	 * if so it just returns the filename passed to it.
	 * If not it will append the .libdata extension to the end of the filename.
	 * @param filename
	 * @return
	 */
	public String checkExtension(String filename) {
		int i = filename.lastIndexOf('.');
		if(i > 0 && i < filename.length()-1) {
			String ext = filename.substring(i+1).toLowerCase();
			if(ext.equalsIgnoreCase(FILE_EXT)) return filename;
		}
		return filename+"."+FILE_EXT;
	}
	
	
	
	/**
	 * The following private methods setup the GUI elements
	 * Methods automatically generated by Eclipse Visual Editor
	 * and then modified by hand to fit needs
	 * 
	 * The default constructor will call getJContentPane which will 
	 * will create a new JPanel on top of the frame and then place 
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
			
			GridBagConstraints c4 = new GridBagConstraints();
			c4.gridy = 6;
			c4.gridx = 0;
			
			GridBagConstraints c5 = new GridBagConstraints();
			c5.gridy = 7;
			c5.gridx = 0;
			
			btPanel = new JPanel();
			btPanel.setLayout(new GridBagLayout());
			btPanel.add(getBtCheckOut(), c0);
			btPanel.add(getBtReturn(), c1);
			btPanel.add(getBtDelete(), c2);
			btPanel.add(getBtExit(), c3);
			btPanel.add(getBtLoad(), c4);
			btPanel.add(getBtSave(), c5);
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
			
			GridBagConstraints cLabelGenre = new GridBagConstraints();
			cLabelGenre.gridx = 0;
			cLabelGenre.gridy = 4;
			cLabelGenre.anchor = GridBagConstraints.EAST;
			
			GridBagConstraints cLabelAttribute = new GridBagConstraints();
			cLabelAttribute.gridx = 2;
			cLabelAttribute.gridy = 4;
			cLabelAttribute.anchor = GridBagConstraints.EAST;
			
			GridBagConstraints cTxtAuthor = new GridBagConstraints();
			cTxtAuthor.fill = GridBagConstraints.VERTICAL;
			cTxtAuthor.gridx = 1;
			cTxtAuthor.gridy = 0;
			cTxtAuthor.anchor = GridBagConstraints.WEST;
			cTxtAuthor.weightx = 1.0;
			cTxtAuthor.gridwidth = 3;
			
			GridBagConstraints cTxtTitle = new GridBagConstraints();
			cTxtTitle.fill = GridBagConstraints.VERTICAL;
			cTxtTitle.gridx = 1;
			cTxtTitle.gridy = 1;
			cTxtTitle.anchor = GridBagConstraints.WEST;
			cTxtTitle.weightx = 1.0;
			cTxtTitle.gridwidth = 3;
			
			GridBagConstraints cTxtAttribute = new GridBagConstraints();
			cTxtAttribute.fill = GridBagConstraints.VERTICAL;
			cTxtAttribute.gridx = 3;
			cTxtAttribute.gridy = 4;
			cTxtAttribute.anchor = GridBagConstraints.WEST;
			cTxtAttribute.weightx = 1.0;
			
			GridBagConstraints cListCopies = new GridBagConstraints();
			cListCopies.fill = GridBagConstraints.VERTICAL;
			cListCopies.gridx = 1;
			cListCopies.gridy = 2;
			cListCopies.anchor = GridBagConstraints.WEST;
			cListCopies.weightx = 1.0;
			
			GridBagConstraints cListGenres = new GridBagConstraints();
			cListGenres.fill = GridBagConstraints.VERTICAL;
			cListGenres.gridx = 1;
			cListGenres.gridy = 4;
			cListGenres.anchor = GridBagConstraints.WEST;
			cListGenres.weightx = 1.0;
			
			GridBagConstraints cTxtISBN = new GridBagConstraints();
			cTxtISBN.gridx = 1;
			cTxtISBN.gridy = 3;
			cTxtISBN.anchor = GridBagConstraints.WEST;
			cTxtISBN.gridwidth = 3;
			
			GridBagConstraints cBtAdd = new GridBagConstraints();
			cBtAdd.gridx = 4;
			cBtAdd.gridy = 1;

			jLabelAuthor = new JLabel();
			jLabelAuthor.setText("Author");
			
			jLabelTitle = new JLabel();
			jLabelTitle.setText("Title");
			
			jLabelCopies = new JLabel();
			jLabelCopies.setText("Copies");
			
			jLabelISBN = new JLabel();
			jLabelISBN.setText("ISBN");
			
			jLabelGenre = new JLabel();
			jLabelGenre.setText("Genre");
			
			jLabelAttribute = new JLabel();
			getListGenres(); // Generate the Genre List for the Attribute Label
			updateAttributeLabel();
			
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
			entryPanel.add(jLabelGenre, cLabelGenre);
			entryPanel.add(getListGenres(), cListGenres);
			entryPanel.add(jLabelAttribute, cLabelAttribute);
			entryPanel.add(getTxtAttribute(), cTxtAttribute);
			
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
	private JButton			getBtLoad() {
		if(btLoad == null) {
			btLoad = new JButton();
			btLoad.setText("Load Library");
			btLoad.setPreferredSize(new java.awt.Dimension(BUTTONWIDTH, BUTTONHEIGHT));
			btLoad.addActionListener(this);
		}
		return btLoad;
	}
	private JButton			getBtSave() {
		if(btSave == null) {
			btSave = new JButton();
			btSave.setText("Save Library");
			btSave.setPreferredSize(new java.awt.Dimension(BUTTONWIDTH, BUTTONHEIGHT));
			btSave.addActionListener(this);
		}
		return btSave;
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
	private JTextField		getTxtAttribute() {
		if(txtAttribute == null) {
			txtAttribute = new JTextField();
			txtAttribute.setColumns(15);
		}
		return txtAttribute;
	}
	private JScrollPane 	getTableScrollPane() {
		if (tableScrollPane == null) {
			tableScrollPane = new JScrollPane();
			tableScrollPane.setViewportView(getBookTable());
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
	private JComboBox		getListGenres() {
		if(listGenres == null) {
			listGenres = new JComboBox();
			for(BookType b : BookType.values())
				listGenres.addItem(b);
			listGenres.addActionListener(this);
		}
		return listGenres;
	}
	private JTable 			getBookTable() {
		if (bookTable == null) {
			columnTitle.addElement("Title");
			columnTitle.addElement("Author");
			columnTitle.addElement("Copies");
			columnTitle.addElement("ISBN");
			columnTitle.addElement("Genre");
			
			bookTable = new JTable(model);
			bookTable.setPreferredScrollableViewportSize(new java.awt.Dimension(410,75));
			bookTable.setRowSelectionAllowed(true);
			
			model.setDataVector(rowElement, columnTitle);
		}
		return bookTable;
	}
	
	
	
	/**
	 * Swing Objects, automatically generated by Eclipse Visual Editor.
	 */
	private JPanel 			jContentPane 		= null;
	private JPanel 			btPanel 			= null;
	private JPanel 			entryPanel 			= null;
	private JPanel 			tablePanel 			= null;
	private JButton 		btCheckOut 			= null;
	private JButton 		btReturn 			= null;
	private JButton	 		btAdd 				= null;
	private JButton 		btDelete 			= null;
	private JButton 		btExit 				= null;
	private JButton			btLoad				= null;
	private JButton			btSave				= null;
	private JLabel 			jLabelAuthor 		= null;
	private JLabel 			jLabelTitle 		= null;
	private JLabel 			jLabelBooks 		= null;
	private JLabel 			jLabelCopies 		= null;
	private JLabel			jLabelISBN			= null;
	private JLabel			jLabelAttribute		= null;
	private JLabel			jLabelGenre			= null;
	private JTextField 		txtAuthor 			= null;
	private JTextField 		txtTitle		 	= null;
	private JTextField		txtISBN				= null;
	private JTextField		txtAttribute		= null;
	private JScrollPane 	tableScrollPane 	= null;
	private JTable 			bookTable 			= null;
	private JComboBox 		listCopies 			= null;
	private JComboBox		listGenres			= null;
	
	/**
	 * Custom helper class that limits what the JFileChooser can see and access.
	 */
	class LibDataFileFilter extends FileFilter {
		@Override
		public boolean accept(File f) {
			// Returns true if the file is a LibData file
			// Only checks file extensions
			if(f.getName().endsWith("."+FILE_EXT)) return true;
			if(f.isDirectory()) return true;
			
			return false;
		}

		@Override
		public String getDescription() {
			return "Library Data Files (."+FILE_EXT+")";
		}
		
	}
	
	
	
	/**
	 * Debug methods
	 */
	private void debugPrintLibrary() {
		Object[] bArray = library.toArray();
		System.out.println("Current Library is: ");
		
		if(bArray.length != 0) {
			for(int i = 0; i < bArray.length; i++) {
				System.out.println(bArray[i].toString());
			}
		} else { 
			System.out.println("Empty"); 
		}
	}
}