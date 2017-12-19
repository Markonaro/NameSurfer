/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;

import java.awt.event.*;

import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base
	 * and initializing the interactors at the top of the window.
	 */
	public void init() {
	    // You fill this in, along with any helper methods //
	// Adds all of the JContainers to the display
		nameLabel = new JLabel("Name: ");
		add(nameLabel, NORTH);
		
		nameText = new JTextField(20);
		nameText.addActionListener(this);
		add(nameText, NORTH);

		graphName = new JButton("Graph");
		add(graphName, NORTH);
		
		clear = new JButton("Clear");
		add(clear, NORTH);
	
		// Adds a NameSurferGraph GCanvas to the display
		canvas = new NameSurferGraph();
		add(canvas);
		
		// Allows the JContainer objects to control actions
		addActionListeners();

		// Compiles a new local database of entry type NameSurferEntry from the source file 
		myDatabase = new NameSurferDataBase("names-data.txt");		
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are
	 * clicked, so you will have to define a method to respond to
	 * button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		// You fill this in //
		if(e.getSource() == clear){
			canvas.clear();
			nameText.setText("");
		} else if(e.getSource() == graphName || e.getSource() == nameText){
			if(nameText.getText().length() > 0){
				theEntry = myDatabase.findEntry(properName(nameText.getText()));
				if(theEntry == null){
					nameText.setText("");
				} else {
					canvas.catalogEntry(theEntry);
					canvas.addEntry(theEntry);
					nameText.setText(properName(nameText.getText()));
				}
			}
		}
	}
	
	public String properName(String entry){
		return entry.substring(0, 1).toUpperCase() + entry.substring(1).toLowerCase();
	}
	
	private NameSurferGraph canvas;
	private NameSurferEntry theEntry;
	private NameSurferDataBase myDatabase; 
	private JLabel nameLabel;
	private JTextField nameText;
	private JButton graphName;
	private JButton clear;
	
}
