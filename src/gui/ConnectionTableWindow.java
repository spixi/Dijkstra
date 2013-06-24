package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class ConnectionTableWindow  extends JFrame  {

	private static final long serialVersionUID = 4956218067122590646L;
	private Vector<Vector<Object>> rowData;
	
	public ConnectionTableWindow(Vector<Vector<Object>> rowData) {
		this.rowData = rowData;
	}
	
	public void draw() {
		
		Vector<String> columnNames =
				new Vector<String>(
				    Arrays.asList("Abflughafen", "Zielflughafen", "Flugzeit")
				);
		
		super.setTitle("Ihre Verbindungen");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTable connections = new JTable(rowData,columnNames);
		Container cp       = super.getContentPane();
		cp.setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(connections);
		
		cp.add(BorderLayout.NORTH, new JLabel("Verbindungsübersicht"));
		cp.add(BorderLayout.SOUTH, scroll);
		
		super.pack();
		super.setLocationRelativeTo(null);
		
		super.setVisible(true);
	}
}
