/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Jul 11, 2010
 * Project: Project 2
 * File: CheckInDialog.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project2;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class CheckInDialog extends JDialog implements ActionListener {
	private final boolean 	DEBUG	= false; // Prints debug information to console

	// Book information
	private String	title,
					author,
					isbn;
	
	private ArrayList<String> borrowers;
	
	// Answer
	private int		index;
	private boolean	cancel = true;
	
	// Getters for answeres
	public int		getIndex() {return index;}
	public boolean	getCanceled() {return cancel;}
	
	public CheckInDialog(JFrame parent, boolean modal, ArrayList<String> list, String t, String a, String i) {
		super(parent, modal);
		setTitle("Book Check In");
		title = t;
		author = a;
		isbn = i;
		borrowers = list;
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
		
		if(source.getSource() == (JButton) checkInButton) {
			if(DEBUG) {System.out.println("Button Press: checkInButton");}
			
			index = clientComboBox.getSelectedIndex();
			if(index != -1) {
				cancel = false;
				setVisible(false);
			} else {
				// Display error message
				JOptionPane.showMessageDialog(this,
						"Please select a patron to return a book",
						"No Name Given",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Swing inializers
	 */
	private JPanel 		getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
			jContentPane.add(getClientPanel(), BorderLayout.CENTER);
			jContentPane.add(getInfoPanel(), BorderLayout.NORTH);
		}
		return jContentPane;
	}
	private JButton 	getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(this);
		}
		return cancelButton;
	}
	private JButton 	getCheckInButton() {
		if (checkInButton == null) {
			checkInButton = new JButton();
			checkInButton.setText("Check In");
			checkInButton.addActionListener(this);
		}
		return checkInButton;
	}
	private JPanel 		getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			buttonPanel.add(getCancelButton(), new GridBagConstraints());
			buttonPanel.add(getCheckInButton(), new GridBagConstraints());
		}
		return buttonPanel;
	}
	private JPanel 		getClientPanel() {
		if (clientPanel == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 0;
			labelClient = new JLabel();
			labelClient.setText("Client:");
			clientPanel = new JPanel();
			clientPanel.setLayout(new GridBagLayout());
			clientPanel.add(labelClient, gridBagConstraints2);
			clientPanel.add(getClientComboBox(), gridBagConstraints3);
		}
		return clientPanel;
	}
	private JPanel 		getInfoPanel() {
		if (infoPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 2;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 1;
			labelAuthor = new JLabel();
			labelAuthor.setText("Author: "+author);
			labelTitle = new JLabel();
			labelTitle.setText("Title: "+title);
			labelISBN = new JLabel();
			labelISBN.setText("ISBN: "+isbn);
			infoPanel = new JPanel();
			infoPanel.setLayout(new GridBagLayout());
			infoPanel.add(labelTitle, new GridBagConstraints());
			infoPanel.add(labelAuthor, gridBagConstraints);
			infoPanel.add(labelISBN, gridBagConstraints1);
		}
		return infoPanel;
	}
	private JComboBox 	getClientComboBox() {
		if (clientComboBox == null) {
			clientComboBox = new JComboBox();
			for(String s : borrowers)
				clientComboBox.addItem(s);
		}
		return clientComboBox;
	}
	
	
	/**
	 * Swing components
	 */
	private JPanel 		jContentPane 	= null;
	private JButton 	cancelButton	= null;
	private JButton 	checkInButton 	= null;
	private JPanel 		buttonPanel 	= null;
	private JPanel 		clientPanel 	= null;
	private JPanel 		infoPanel 		= null;
	private JLabel 		labelTitle 		= null;
	private JLabel 		labelAuthor 	= null;
	private JLabel 		labelISBN 		= null;
	private JLabel 		labelClient 	= null;
	private JComboBox 	clientComboBox 	= null;
}