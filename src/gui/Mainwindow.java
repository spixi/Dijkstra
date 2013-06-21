package gui;

import helpers.ClassRouteHelper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListModel;

import org.json.simple.parser.ParseException;

import calc.Calculation;

import sun.management.snmp.jvmmib.JvmCompilationMeta;

import model.BadFileFormatException;

@SuppressWarnings("serial")
public class Mainwindow extends JFrame implements ActionListener {
	
	private JComboBox<String> leftList;
	private JComboBox<String> rightList;
	
	public void draw() {
		super.setTitle("Dijkstra");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		super.setLayout(new GridLayout(3,2,2,2));
		
		((JComponent)getContentPane()).setBorder(BorderFactory.createMatteBorder( 4, 4, 4, 4, Color.LIGHT_GRAY ) ); 
	
		// Create the lists
		try {
			this.leftList = new JComboBox<String>(ClassRouteHelper.getListModel().getLocations());
			this.rightList = new JComboBox<String>(ClassRouteHelper.getListModel().getLocations());
		} catch (FileNotFoundException e) { e.printStackTrace(); // TODO why doing exception handling here within the view ?
		} catch (IOException e) { e.printStackTrace();
		} catch (ParseException e) { e.printStackTrace();
		} catch (BadFileFormatException e) {e.printStackTrace(); }
		
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
		JOptionPane.showMessageDialog(null, "Preform!");
		Calculation c = new Calculation();
		c.setIdFrom(-1);
		c.setIdTo(-1);
	}
	
}
