package gui;

import helpers.ClassRouteHelper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;

@SuppressWarnings("serial")
public class Mainwindow extends JFrame implements MouseListener {
	
	private JList<String> leftList;
	private JList<String> rightList;
	
	public void draw() {
		super.setTitle("Dijkstra");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		super.setLayout(new BoxLayout(super.getContentPane(), BoxLayout.PAGE_AXIS));
		
		// Create the first list container
		JPanel lists = new JPanel();
		lists.setLayout(new GridLayout(1,3,2,0));
		//JList<String> leftList = new JList<>(ClassRouteHelper.getListModel().getLocations());
		this.leftList = new JList<>(ClassRouteHelper.getListModel().getLocations());
		//JList<String> rightList = new JList<>(new DefaultListModel<String>());
		this.rightList = new JList<>(new DefaultListModel<String>());
		
		JButton addButton = new JButton(">>");
		
		lists.add(leftList);
		lists.add(addButton);
		lists.add(rightList);
		
		// Action listener assignments
		leftList.addMouseListener(this);
		
		super.getContentPane().add(new JLabel("Demonstration des Dijkstra Algorithmus"), BorderLayout.NORTH);
		//super.getContentPane().add(leftList, BorderLayout.LINE_START);
		//super.getContentPane().add(rightList, BorderLayout.LINE_END);
		super.getContentPane().add(lists, BorderLayout.CENTER);
		
		// Do the rest for displaying the window
		super.pack();
		super.setLocationRelativeTo(null);
		
		// Show the window
		super.setVisible(true);
	}

	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2) {
			DefaultListModel<String> lm = (DefaultListModel<String>) this.rightList.getModel();
			if(!lm.contains(leftList.getSelectedValue()))
				lm.addElement(leftList.getSelectedValue());
		}
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
}
