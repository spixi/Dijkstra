package gui;

import helpers.ClassRouteHelper;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.BadFileFormatException;

import org.json.simple.parser.ParseException;

@SuppressWarnings("serial")
public class Mainwindow extends JFrame implements MouseListener, ActionListener {
	
	private JList<String> leftList;
	private JList<String> rightList;
	
	public void draw() {
		super.setTitle("Dijkstra");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		super.setLayout(new BoxLayout(super.getContentPane(), BoxLayout.PAGE_AXIS));
		
		// Create a container for the lists and the lists them self
		JPanel lists = new JPanel();
		lists.setLayout(new GridLayout(1,3,2,0));
		//lists.setLayout(new FlowLayout());
		try {
			this.leftList = new JList<>(ClassRouteHelper.getListModel().getLocations());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadFileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.rightList = new JList<>(new DefaultListModel<String>());
		
		JButton addButton = new JButton(">>");
		
		// Add the elements to the container
		lists.add(leftList);
		lists.add(addButton);
		lists.add(rightList);
		
		// Create a action button making everything preform
		JButton startAction = new JButton("Berechnen");
		
		// Action listener assignments
		leftList.addMouseListener(this);
		leftList.setName("ll");
		rightList.addMouseListener(this);
		rightList.setName("rl");
		
		startAction.addActionListener(this);
		
		super.getContentPane().add(new JLabel("Demonstration des Dijkstra Algorithmus"), BorderLayout.NORTH);
		super.getContentPane().add(lists, BorderLayout.CENTER);
		super.getContentPane().add(startAction, BorderLayout.SOUTH);
		
		// Do the rest for displaying the window
		super.pack();
		super.setLocationRelativeTo(null);
		
		// Show the window
		super.setVisible(true);
	}

	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2) {
			// Get the list which fired the event
			JList<String> list = (JList<String>)e.getSource();
			// List Model/Data for the right list
			DefaultListModel<String> lm = (DefaultListModel<String>)this.rightList.getModel();
			if(list.getName().equals("ll")) {
				if(!lm.contains(leftList.getSelectedValue()))
					lm.addElement(leftList.getSelectedValue());
			}
			else if(list.getName().equals("rl")) {
				lm.removeElement(rightList.getSelectedValue());
			}
		}
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "Preform!");
	}
	
}
