package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controller.Controller;

import calc.Calculation;

import model.ListDataModel;

@SuppressWarnings("serial")
public class Mainwindow extends JFrame implements ActionListener {
	
	private JComboBox<String> leftList;
	private JComboBox<String> rightList;
	
	public void draw() {
		super.setTitle("Dijkstra");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		super.setLayout(new GridLayout(3,2,2,2));
		
		((JComponent)getContentPane()).setBorder(BorderFactory.createMatteBorder( 4, 4, 4, 4, Color.LIGHT_GRAY ) ); 
		
		File f = new File("test/testconnection.json");
		try {
			Controller.INSTANCE.readFile(f);
		} catch (Exception e) {
			//TODO e.getMessage() in Dialogbox ausgeben!
		}
		
		ListDataModel  model     = Controller.INSTANCE.getModel();
		Vector<String> locations = model.getLocations();
	
		// Create the lists
	    this.leftList  = new JComboBox<String>(locations);
		this.rightList = new JComboBox<String>(locations);
		
		// Create a button for beeding able to submit the action
		JButton startAction = new JButton("Berechnen");
		
		// Add elements to the frame
		super.add(new JLabel("Start"));
		super.add(leftList);
		super.add(new JLabel("Ziel"));
		super.add(rightList);
		super.add(startAction);
		super.add(new JLabel());
		
		startAction.addActionListener(this);
		
		// Do the rest for displaying the window
		super.pack();
		super.setLocationRelativeTo(null);
		
		// Show the window
		super.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "Die Reise dauert: " + ClassRouteHelper.getListModel().getFormattedDuration().toString());
		Calculation c = new Calculation();
		c.setIdFrom(-1);
		c.setIdTo(-1);
	}
	
}
