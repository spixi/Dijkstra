/**
 * EditorWindow
 * <p>
 * The window for the Airport editor
 * 
 * @author Serjoscha Bassauer
 */
package de.bwv_aachen.dijkstra.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.WindowConstants;

import org.joda.time.Duration;

import de.bwv_aachen.dijkstra.helpers.DateHelper;
import de.bwv_aachen.dijkstra.model.Airport;
import de.bwv_aachen.dijkstra.model.Connection;

@SuppressWarnings("serial")
public class EditorWindow extends JFrame implements View, MouseListener, ActionListener {

    Vector<Airport> locations;

    // Beans
    JPanel          connectionsContainer;
    JList<Airport>  locationJList;
    
    JButton lAdd;
    JButton lRem;
    JButton rAdd;
    JButton rRem;
    
    // Model(s)
    DefaultListModel<Airport> lm = new DefaultListModel<>();

    public EditorWindow(Vector<Airport> locations) {
        this.locations = locations;
        
        // generate Airport List Model
        Iterator<Airport> it = locations.iterator();
        while(it.hasNext()) { // assign every location to the jList Model
            Airport ca = (Airport)it.next();
            this.lm.addElement(ca);
        }
    }

    public void draw() {
        super.getContentPane().removeAll(); // making this function being able to repaint the mainwindow
        super.setTitle("Bearbeiten");
        super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        super.setResizable(false);

        super.setLayout(new GridLayout(1, 2, 10, 0));

        ((JComponent) getContentPane()).setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.LIGHT_GRAY));

        // Build the UI Elems
        //locationJList = new JList<Airport>(locations); // this will create a jlist without an model -> completly unusable
        locationJList = new JList<Airport>(lm);
        connectionsContainer = new JPanel();
        
        // Container for the left and the right side
        JPanel leftContainer = new JPanel();
        leftContainer.setLayout(new BorderLayout());
        JPanel rightContainer = new JPanel();
        rightContainer.setLayout(new BorderLayout());
        
        // Buttons
        this.lAdd = new JButton("+");
        this.lAdd.setActionCommand("lAdd");
        this.lRem = new JButton("-");
        this.lRem.setActionCommand("lRem");
        this.rAdd = new JButton("+");
        this.rAdd.setActionCommand("rAdd");
        this.rRem = new JButton("-");
        this.rRem.setActionCommand("rRem");
        
        // Container for the buttons
        JPanel lButtons = new JPanel();
        lButtons.setLayout(new FlowLayout());
        JPanel rButtons = new JPanel();
        rButtons.setLayout(new FlowLayout());
        
        // Add buttons to container
        lButtons.add(lAdd);
        lButtons.add(lRem);
        
        rButtons.add(rAdd);
        rButtons.add(rRem);

        // Add ActionListening
        locationJList.addMouseListener(this);
        this.lAdd.addActionListener(this);
        this.rAdd.addActionListener(this);
        this.lRem.addActionListener(this);
        this.rRem.addActionListener(this);
        
        // Add lists and buttons to the correct jpanel
        leftContainer.add(locationJList, BorderLayout.CENTER);
        leftContainer.add(lButtons, BorderLayout.SOUTH);
        
        rightContainer.add(connectionsContainer, BorderLayout.CENTER);
        rightContainer.add(rButtons, BorderLayout.SOUTH);
        
        // Add elems (panels) to frame
        super.getContentPane().add(leftContainer);
        super.getContentPane().add(rightContainer);

        // Do the rest for displaying the window
        super.pack();
        super.setLocationRelativeTo(null); // center the frame

        // Show the window
        super.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Determine which element was clicked
        //int elem = locations.indexOf(locationJList.getSelectedValue());
        //Airport ap = locations.get(elem); // the object of type Airport that has been chosen from the list
        
        int elem = this.lm.indexOf(locationJList.getSelectedValue());
        Airport ap = this.lm.get(elem); // the object of type Airport that has been chosen from the list

        // Render Form
        connectionsContainer.removeAll();
        
        if(ap.getConnections().size() == 0)
            return;
        
        connectionsContainer.setLayout(new GridLayout(ap.getConnections().size(), 2));

        for (Map.Entry<Airport, Connection> entry : ap.getConnections().entrySet()) {
            JComboBox<Airport> apSelect = new JComboBox<>(locations);
            apSelect.setSelectedIndex(locations.indexOf(entry.getKey()));
            connectionsContainer.add(apSelect);
            connectionsContainer.add(new JTextField(DateHelper.INSTANCE.durationToString(entry.getValue().getDuration())));
        }

        pack();
        this.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        //JButton button = (JButton)e.getSource();
        switch(e.getActionCommand()){
            case "lAdd":
                String input = JOptionPane.showInputDialog("Name des Flughafens:");
                if(input != null) { // prevents some nullpointer exceptions (which would not take any effect for the program, but disturbed me)
                    if(!input.equals("")) {
                        DefaultListModel<Airport> lm = (DefaultListModel<Airport>)this.locationJList.getModel();
                        
                        Airport nAp = new Airport(lm.lastElement().getId()+1, input); // create an temp airport that will later be assigned as connection
                        
                        nAp.getConnections().put(new Airport(1337l, "Prag"), new Connection(new Duration(1338))); // TEST!!
                        
                        lm.addElement(nAp); // add the String as given Airport to the JList Model
                    }
                }
            break;
            
            case "lRem":
                lm.remove(this.locationJList.getSelectedIndex());
            break;
        }
        this.draw(); // repaint
    }

    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }

}
