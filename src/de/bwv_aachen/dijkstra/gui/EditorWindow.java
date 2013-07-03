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
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.joda.time.Duration;

import de.bwv_aachen.dijkstra.controller.Controller;
import de.bwv_aachen.dijkstra.helpers.DateHelper;
import de.bwv_aachen.dijkstra.model.Airport;
import de.bwv_aachen.dijkstra.model.Connection;

@SuppressWarnings("serial")
public class EditorWindow extends View  implements ActionListener, ListSelectionListener {

    // Beans
    JPanel          connectionsContainer;
    JList<Airport>  locationJList;
    
    JButton lAdd;
    JButton lRem;
    JButton rAdd;
    
    // Helper Window(s)
    EditorWindow_AirportSelector airportSel;
    
    // Model(s)
    DefaultListModel<Airport> lm = new DefaultListModel<>();

    public EditorWindow(Controller c) {
        super("EditorWindow",c);
        
        // generate Airport List Model
        for(Airport ca: controller.getModel().getAirportList().values()) { // assign every location to the jList Model
            this.lm.addElement(ca);
        }
    }

    public void draw() {
        Container cp = super.getContentPane();
        
        cp.removeAll(); // making this function being able to repaint the mainwindow
        super.setTitle("Bearbeiten");
        super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        super.setResizable(false);

        cp.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
        
        ((JComponent)cp).setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));

        // Build the UI Elems
        locationJList = new JList<Airport>(lm);
        locationJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Only one airport can be selected
        locationJList.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        connectionsContainer = new JPanel();
        connectionsContainer.setLayout(new BoxLayout(connectionsContainer, BoxLayout.PAGE_AXIS));
        
        // Some Look and feel helper captions
        JLabel formTitle = new JLabel("Daten bearbeiten");
        formTitle.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
        JLabel leftAreaCaption = new JLabel("Flughäfen");
        JLabel rightAreaCaption = new JLabel("Verbindungen");
        
        // Panels for the captions
        JPanel headArea = new JPanel();
        headArea.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        headArea.setLayout(new BorderLayout());
        headArea.add(formTitle, BorderLayout.LINE_END);
        
        JPanel subHeaderArea = new JPanel();
        subHeaderArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        subHeaderArea.setLayout(new BorderLayout());
        subHeaderArea.add(leftAreaCaption, BorderLayout.WEST);
        subHeaderArea.add(rightAreaCaption, BorderLayout.EAST);
        
        JPanel footerArea = new JPanel();
        footerArea.setLayout(new BorderLayout());
        footerArea.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        // Add button to footer
        JButton saveDummy = new JButton("Speichern");
        // NOTE: DONT REMOVE THE SAVE BUTTON AGAIN -> I added it so users dont get confused when editing forms
        // for its not very common that changes are saved when u click the red X of the window... This is a
        // look and feel Feature!
        saveDummy.setActionCommand("saveButton");
        saveDummy.addActionListener(this);
        footerArea.add(saveDummy);
        
        // Add caption
        cp.add(headArea);
        cp.add(subHeaderArea);
        
        // Container for the left and the right side
        JPanel dataArea = new JPanel(); // this panel contains all date elems (list and select boxes)
        dataArea.setLayout(new BoxLayout(dataArea, BoxLayout.LINE_AXIS));
        
        JPanel leftContainer = new JPanel();
        leftContainer.setLayout(new BorderLayout());
        leftContainer.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        JPanel rightContainer = new JPanel();
        rightContainer.setLayout(new BorderLayout());
        rightContainer.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        
        // Buttons
        this.lAdd = new JButton("+");
        this.lAdd.setActionCommand("lAdd");
        this.lRem = new JButton("-");
        this.lRem.setEnabled(false);
        this.lRem.setActionCommand("lRem");
        this.rAdd = new JButton("+");
        this.rAdd.setActionCommand("rAdd");
        this.rAdd.setEnabled(false);
        
        // Container for the buttons
        JPanel lButtons = new JPanel();
        lButtons.setLayout(new FlowLayout());
        JPanel rButtons = new JPanel();
        rButtons.setLayout(new FlowLayout());
        
        // Add buttons to container
        lButtons.add(lAdd);
        lButtons.add(lRem);
        rButtons.add(rAdd);

        // Add ActionListening
        locationJList.addListSelectionListener(this);
        this.lAdd.addActionListener(this);
        this.rAdd.addActionListener(this);
        this.lRem.addActionListener(this);
        
        if(locationJList.getSelectedIndex()==-1) // initially start with selecting the first elem in list
            locationJList.setSelectedIndex(0);
        
        // Add lists and buttons to the correct jpanel
        leftContainer.add(locationJList, BorderLayout.CENTER);
        leftContainer.add(lButtons, BorderLayout.SOUTH);
        
        rightContainer.add(connectionsContainer, BorderLayout.NORTH);
        rightContainer.add(rButtons, BorderLayout.SOUTH);
        
        // Add elems (panels) to frame
        dataArea.add(leftContainer);
        dataArea.add(rightContainer);
        
        cp.add(dataArea);
        
        // Add footer
        cp.add(footerArea);

        // Do the rest for displaying the window
        super.pack();
        super.setLocationRelativeTo(null); // center the frame

        // Show the window
        super.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        //JButton button = (JButton)e.getSource();
        switch(e.getActionCommand()){
            case "lAdd": // add FROM/source airport
                String input = JOptionPane.showInputDialog("Name des Flughafens:");
                if(input != null) { // prevents some nullpointer exceptions (which would not take any effect for the program, but disturbed me)
                    if(!input.equals("")) {
                        DefaultListModel<Airport> lm = (DefaultListModel<Airport>)this.locationJList.getModel();
                        
                        Long id = 0L;
                        try {
                           id = lm.lastElement().getId()+1;
                        }
                        //Last element not found, so create a new airport with ID 1
                        catch (NoSuchElementException | NullPointerException ex) {
                           id = 1L;
                        }
                        
                        Airport nAp = new Airport(id, input); // create an temp airport that will later be assigned as connection  
                        
                        lm.addElement(nAp);      // add the String as given Airport to the JList Model
                        
                        //Put the new airport to the real data model
                        controller.getModel().getAirportList().put(id, nAp);
                        
                        //refresh the list
                        this.repaint();
                    }
                }
            break;
            
            case "lRem":
                Airport oldAirport = lm.remove(this.locationJList.getSelectedIndex());
                controller.getModel().getAirportList().remove(oldAirport.getId());
            break;
            
            case "rAdd":
                // Show our self made selection box modal
                this.airportSel = new EditorWindow_AirportSelector(controller, this);
                this.airportSel.draw();
            break;
            
            case "approveAPselection":
                int elem = this.lm.indexOf(locationJList.getSelectedValue());
                Airport ap = this.lm.get(elem);
                ap.getConnections().put(airportSel.getSelection(), new Connection(Duration.ZERO));
                this.airportSel.dispose();
            break;
            
            case "saveButton":
                super.dispose();
            break;
        }
        int selection = this.locationJList.getSelectedIndex(); // repainting makes the form lose its selection so lets manually save and restore them
        this.draw(); // repaint
        this.locationJList.setSelectedIndex(selection);
    }

    /**
     * Triggered as soon as the list selection changes in any way
     */
    public void valueChanged(ListSelectionEvent e) {     
        
        // first enable the action buttons
        this.lRem.setEnabled(true);
        this.rAdd.setEnabled(true);

        // Render Form
        connectionsContainer.removeAll();
        
        //Index points to a deleted Airport
        if (locationJList.getSelectedIndex() == -1) {
            return;
        }
        
        Airport ap = this.lm.elementAt(locationJList.getSelectedIndex());
        
        if (ap == null) {
            return;
        }
        
        //connectionsContainer.setLayout(new GridLayout(ap.getConnections().size(), 4));

        for (Map.Entry<Airport, Connection> entry : ap.getConnections().entrySet()) {
            // Create a flowing panel for each row
            JPanel row = new JPanel();
            row.setLayout(new GridLayout(1, 4, 15,0));
            
            // one more container for the buttons
            JPanel buttonContainer = new JPanel();
            buttonContainer.setLayout(new FlowLayout());
            
            // create beans
            JTextField textDuration = new JTextField();
            JButton deleteButton = new JButton(""); 
            deleteButton.addActionListener(new ActionListener() {
                private Airport ap;
                private Map.Entry<Airport, Connection> entry;
                
                public void actionPerformed(ActionEvent ev) {
                    ap.getConnections().remove(entry.getKey());
                    connectionsContainer.repaint();
                }
                
                public ActionListener fakeConstructor(Airport ap, Map.Entry<Airport, Connection> entry) {
                    this.ap    = ap;
                    this.entry = entry;
                    return this;
                }
            }.fakeConstructor(ap,entry));
            deleteButton.setActionCommand("removeConnection");
            deleteButton.addActionListener(this); // TODO this needs to be deleted later for its obsolete
            // delete button styles
            deleteButton.setIcon(new ImageIcon("res/delete_icon.gif"));
            deleteButton.setBackground(new Color(0,0,0,0));
            deleteButton.setOpaque(false);
            deleteButton.setBorder(BorderFactory.createEmptyBorder());
            
            buttonContainer.add(new ConnectionChangeButton(entry.getValue(),textDuration));
            buttonContainer.add(deleteButton);
            
            row.add(new JLabel(entry.getKey().toString())); // The Airport Name
            row.add(textDuration); // The Duration Placeholder
            row.add(buttonContainer);
            
            connectionsContainer.add(row);

        }

        pack();
        setLocationRelativeTo(null);
        connectionsContainer.repaint();
    }

}