package gui;

import helpers.ClassRouteHelper;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

@SuppressWarnings("serial")
public class Mainwindow extends JFrame {
	
	public void draw() {
		super.setTitle("Dijkstra");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		super.setLayout(new BorderLayout());
		
		// Create the first list container
		//JPanel leftList = new JPanel();
		JList<String> leftList = new JList<>(ClassRouteHelper.getListModel().getLocations());
		JList<String> rightList = new JList<>(new DefaultListModel<String>());
		
		leftList.addMouseListener(new ActionHandling());
		
		super.getContentPane().add(leftList, BorderLayout.WEST);
		super.getContentPane().add(rightList, BorderLayout.EAST);
		
		super.pack();
		
		// Show the window
		super.setVisible(true);
	}
	
}
