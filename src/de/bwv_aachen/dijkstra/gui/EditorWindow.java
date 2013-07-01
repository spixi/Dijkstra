/**
 * EditorWindow
 * <p>
 * The window for the Airport editor
 * 
 * @author Serjoscha Bassauer
 */
package de.bwv_aachen.dijkstra.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import de.bwv_aachen.dijkstra.helpers.DateHelper;
import de.bwv_aachen.dijkstra.model.Airport;
import de.bwv_aachen.dijkstra.model.Connection;

@SuppressWarnings("serial")
public class EditorWindow extends JFrame implements View, MouseListener {

    Vector<Airport> locations;

    // Beans
    JPanel          connectionsContainer;
    JList<Airport>  locationJList;

    public EditorWindow(Vector<Airport> locations) {
        this.locations = locations;
    }

    
    @Override
    public void draw() {
        super.getContentPane().removeAll(); // making this function being able
                                            // to repaint the mainwindow
        super.setTitle("Bearbeiten");
        super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        super.setResizable(false);

        super.setLayout(new GridLayout(1, 2, 10, 0));

        ((JComponent) getContentPane()).setBorder(BorderFactory
                .createMatteBorder(4, 4, 4, 4, Color.LIGHT_GRAY));

        // Build the UI Elems
        locationJList = new JList<Airport>(locations);
        connectionsContainer = new JPanel();

        // Add ActionListening
        locationJList.addMouseListener(this);

        // Add elems to frame
        super.getContentPane().add(locationJList);
        super.getContentPane().add(connectionsContainer);

        // Do the rest for displaying the window
        super.pack();
        super.setLocationRelativeTo(null); // center the frame

        // Show the window
        super.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Determine which element was clicked
        int elem = locations.indexOf(locationJList.getSelectedValue());
        Airport ap = locations.get(elem); // the object of type Airport that has
                                          // been chosen from the list

        // Render Form
        connectionsContainer.removeAll();
        connectionsContainer.setLayout(new GridLayout(ap.getConnections()
                .size(), 2));

        for (Map.Entry<Airport, Connection> entry : ap.getConnections()
                .entrySet()) {
            // System.out.print(entry.getKey());
            // System.out.print(" -> ");
            // System.out.println(entry.getValue().getName());
            connectionsContainer.add(new JLabel(entry.getKey().getName())); // THIS
                                                                            // WILL
                                                                            // BECOME
                                                                            // A
                                                                            // SELECTBOX
                                                                            // LATER!!!!
            connectionsContainer.add(new JTextField(DateHelper.INSTANCE
                    .durationToString(entry.getValue().getDuration())));
        }

        /*
         * Iterator foo = ap.getConnections().values().iterator();
         * while(foo.hasNext()){ Connection quxx = (Connection)foo.next();
         * System.out.println(quxx.getName()); }
         */

        pack();
        this.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}
