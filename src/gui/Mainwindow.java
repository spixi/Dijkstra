package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Mainwindow extends JFrame {

	public void draw() {
		super.setTitle("Dijkstra");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create the first list container
		JPanel leftList = new JPanel();
		
		
		// Show the window
		super.setVisible(true);
	}
	
}
