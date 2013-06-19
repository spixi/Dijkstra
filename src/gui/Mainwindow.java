package gui;

import helpers.ClassRouteHelper;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import model.DummyImplListDataModel;

public class Mainwindow extends JFrame {

	public void draw() {
		super.setTitle("Dijkstra");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		super.setLayout(new FlowLayout());
		
		// Create the first list container
		//JPanel leftList = new JPanel();
		JList<String> leftList = new JList<>(ClassRouteHelper.getLocalListModel().getPossibleLocations());
		
		super.getContentPane().add(leftList);
		
		super.pack();
		
		// Show the window
		super.setVisible(true);
	}
	
}
