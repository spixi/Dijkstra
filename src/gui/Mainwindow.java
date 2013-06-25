package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.parser.ParseException;

import controller.Controller;

import model.Airport;
import model.BadFileFormatException;
import model.ListDataModel;

@SuppressWarnings("serial")
public class Mainwindow extends JFrame implements ActionListener, View {
	
	private JComboBox<Airport> leftList;
	private JComboBox<Airport> rightList;
	
	public void draw() {
		super.setTitle("Dijkstra");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		super.setLayout(new GridLayout(3,2,2,2));
		
		((JComponent)getContentPane()).setBorder(BorderFactory.createMatteBorder( 4, 4, 4, 4, Color.LIGHT_GRAY ) ); 
		
		//File f = new File("test/testconnection.json");
		File f = Controller.INSTANCE.getDefaultConnectionFile();
		try {
			Controller.INSTANCE.readFile(f);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Die Datei konnte nicht geöffnet werden", "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		ListDataModel  model     = Controller.INSTANCE.getModel();
		Vector<Airport> locations = model.getLocations();
	
		// Create the lists
	    this.leftList  = new JComboBox<Airport>(locations);
		this.rightList = new JComboBox<Airport>(locations);
		
		// Create a button for being able to submit the action
		JButton startAction = new JButton("Berechnen");
		startAction.setActionCommand("calc");
		
		// Add elements to the frame
		super.add(new JLabel("Start"));
		super.add(leftList);
		super.add(new JLabel("Ziel"));
		super.add(rightList);
		super.add(startAction);
		super.add(new JLabel());
		
		startAction.addActionListener(this);
		
		// Create a menu for various actions
		JMenuBar menuBar = new JMenuBar();
		super.setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("Datei");
		menuBar.add(fileMenu);
		
		JMenuItem openFile = new JMenuItem("Datei öffnen...");
		openFile.addActionListener(this);
		openFile.setActionCommand("loadFile");
		fileMenu.add(openFile);
		
		// Do the rest for displaying the window
		super.pack();
		super.setLocationRelativeTo(null); // center the frame
		
		// Show the window
		super.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "calc":
				//JOptionPane.showMessageDialog(null, "Die Reise dauert: " + ClassRouteHelper.getListModel().getFormattedDuration().toString());
				Airport from = (Airport)rightList.getSelectedItem();
				Airport to   = (Airport)leftList.getSelectedItem();
				
				Vector<Vector<Object>> test = Controller.INSTANCE.getModel().getRoute(from,to);
				
				new ConnectionTableWindow(test).draw();
			break;
			
			case "loadFile":
			    JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Dateien (*.json)", "json");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(this);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	System.out.println(chooser.getSelectedFile().getAbsolutePath()); // TODO DEBUG
					try {
						Controller.INSTANCE.readFile(new File(chooser.getSelectedFile().getAbsolutePath()));
					} catch (IOException | ParseException | BadFileFormatException e1) {
						JOptionPane.showMessageDialog(this, "Die Datei konnte nicht geöffnet werden", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
			    }
			break;
		}

	}
	
}
