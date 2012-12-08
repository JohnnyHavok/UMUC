/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Aug 7, 2010
 * Project: Project 4a
 * File: CheckOutDialog.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project4a;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JDialog;

@SuppressWarnings("serial")
public class CheckOutDialog extends JDialog implements ActionListener {
	private final boolean 	DEBUG	= false; // Prints debug information to console

	// Answers to the user questions
	private int 	days;
	private String 	client;
	private boolean cancel = true;
	
	// Book information
	private String	title,
					author,
					isbn;
	
	// Getters for those answers.
	public int 		getDays() {return days;}
	public String 	getClient() {return client;}
	public boolean	getCanceled() {return cancel;}
	
	// Constructors for dialog
	public CheckOutDialog(JFrame parent, boolean modal, String t, String a, String i) {
		super(parent, modal);
		setTitle("Book Check Out");
		title = t;
		author = a;
		isbn = i;
		getContentPane().add(getJContentPane());        
		pack();
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent source) {
		if(source.getSource() == (JButton) cancelButton) {
			if(DEBUG) {System.out.println("Button Press: cancelButton");}
			cancel = true;
			setVisible(false);
		} // End action for "Cancel" button
		
		if(source.getSource() == (JButton) checkOutButton) {
			if(DEBUG) {System.out.println("Button Press: checkOutButton");}
			
			this.days = (Integer) listDayCount.getSelectedItem();
			if(!txtClient.getText().isEmpty()) {
				client = txtClient.getText();
				cancel = false;
				setVisible(false);
			} else {
				// Display error message
				JOptionPane.showMessageDialog(this,
						"Cannot check out book without patron name",
						"No Name Given",
						JOptionPane.ERROR_MESSAGE);
			}
		} // end action for "Check Out" button
	}
	
	/**
	 * Swing components
	 */
	private JPanel 		jContentPane 		= null;
	private JPanel 		jPanelButtons 		= null;
	private JPanel 		jPanelInfo 			= null;
	private JPanel 		jPanelCopies 		= null;
	private JLabel 		jLabelTitle 		= null;
	private JLabel 		jLabelAuthor 		= null;
	private JLabel 		jLabelISBN 			= null;
	private JLabel 		jLabelDays 			= null;
	private JLabel		jLabelClient		= null;
	private JComboBox 	listDayCount 		= null;
	private JButton 	cancelButton 		= null;
	private JButton 	checkOutButton 		= null;
	private JTextField  txtClient			= null;
	
	/**
	 * Initialize Swing components
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getBtPanel(), BorderLayout.SOUTH);
			jContentPane.add(getInfoPanel(), BorderLayout.NORTH);
			jContentPane.add(getCopiesPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}
	private JPanel getBtPanel() {
		if (jPanelButtons == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			jPanelButtons = new JPanel();
			jPanelButtons.setLayout(new GridBagLayout());
			jPanelButtons.add(getCancelButton(), gridBagConstraints);
			jPanelButtons.add(getCheckOutButton(), gridBagConstraints1);
		}
		return jPanelButtons;
	}
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(this);
		}
		return cancelButton;
	}
	private JButton getCheckOutButton() {
		if (checkOutButton == null) {
			checkOutButton = new JButton();
			checkOutButton.setText("Check Out");
			checkOutButton.addActionListener(this);
		}
		return checkOutButton;
	}
	private JPanel getInfoPanel() {
		if (jPanelInfo == null) {
			GridBagConstraints cLabelISBN = new GridBagConstraints();
			cLabelISBN.gridx = 0;
			cLabelISBN.gridy = 2;
			cLabelISBN.anchor = GridBagConstraints.WEST;
			cLabelISBN.fill = GridBagConstraints.HORIZONTAL;
			jLabelISBN = new JLabel();
			jLabelISBN.setText("ISBN: "+isbn);
			GridBagConstraints cLabelAuthor = new GridBagConstraints();
			cLabelAuthor.gridx = 0;
			cLabelAuthor.gridy = 1;
			cLabelAuthor.anchor = GridBagConstraints.WEST;
			cLabelAuthor.fill = GridBagConstraints.HORIZONTAL;
			jLabelAuthor = new JLabel();
			jLabelAuthor.setText("Author: "+author);
			GridBagConstraints cLabelTitle = new GridBagConstraints();
			cLabelTitle.gridx = 0;
			cLabelTitle.gridy = 0;
			cLabelTitle.anchor = GridBagConstraints.WEST;
			cLabelTitle.fill = GridBagConstraints.HORIZONTAL;
			jLabelTitle = new JLabel();
			jLabelTitle.setText("Title: "+title);
			jPanelInfo = new JPanel();
			jPanelInfo.setLayout(new GridBagLayout());
			jPanelInfo.add(jLabelTitle, cLabelTitle);
			jPanelInfo.add(jLabelAuthor, cLabelAuthor);
			jPanelInfo.add(jLabelISBN, cLabelISBN);
		}
		return jPanelInfo;
	}
	private JPanel getCopiesPanel() {
		if (jPanelCopies == null) {
			GridBagConstraints cCopiesBox = new GridBagConstraints();
			cCopiesBox.fill = GridBagConstraints.VERTICAL;
			cCopiesBox.gridy = 0;
			cCopiesBox.weightx = 1.0;
			cCopiesBox.gridx = 1;
			cCopiesBox.anchor = GridBagConstraints.WEST;
			GridBagConstraints cNumberLabel = new GridBagConstraints();
			cNumberLabel.gridx = 0;
			cNumberLabel.gridy = 0;
			GridBagConstraints cLabelClient = new GridBagConstraints();
			cLabelClient.gridx = 0;
			cLabelClient.gridy = 1;
			GridBagConstraints cTxtClient = new GridBagConstraints();
			cTxtClient.gridx = 1;
			cTxtClient.gridy = 1;
			cTxtClient.anchor = GridBagConstraints.WEST;
			cTxtClient.fill = GridBagConstraints.HORIZONTAL;
			jLabelDays = new JLabel();
			jLabelDays.setText("Number of days");
			jLabelClient = new JLabel();
			jLabelClient.setText("Library Patron");
			jPanelCopies = new JPanel();
			jPanelCopies.setLayout(new GridBagLayout());
			jPanelCopies.add(jLabelDays, cNumberLabel);
			jPanelCopies.add(getListDayCount(), cCopiesBox);
			jPanelCopies.add(jLabelClient, cLabelClient);
			jPanelCopies.add(getTxtClient(), cTxtClient);
		}
		return jPanelCopies;
	}
	private JComboBox getListDayCount() {
		if (listDayCount == null) {
			listDayCount = new JComboBox();
			listDayCount.addItem((Integer) 10);
			listDayCount.addItem((Integer) 15);
			listDayCount.addItem((Integer) 20);
			listDayCount.addItem((Integer) 30);
		}
		return listDayCount;
	}
	private JTextField getTxtClient() {
		if (txtClient == null) {
			txtClient = new JTextField();
			txtClient.setColumns(25);
		}
		return txtClient;
	}
}