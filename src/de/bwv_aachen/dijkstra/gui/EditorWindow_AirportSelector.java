package de.bwv_aachen.dijkstra.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.sun.xml.internal.ws.api.server.Container;

import de.bwv_aachen.dijkstra.model.Airport;

@SuppressWarnings("serial")
public class EditorWindow_AirportSelector extends JFrame implements View {

    Vector<Airport> locations;
    ActionListener al;
    
    // Beans
    private JComboBox<Airport> cb;
    
    public EditorWindow_AirportSelector(Vector<Airport> locations, ActionListener al) {
        this.locations = locations;
        this.al = al;
    }
    
    public void draw() {
        super.setTitle("W�hle...");
        super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        super.setResizable(false);
        super.setAlwaysOnTop(true); // this is a bit hacky..
        
        super.getContentPane().setLayout(new BoxLayout(super.getContentPane(), BoxLayout.Y_AXIS));

        ((JComponent) getContentPane()).setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.LIGHT_GRAY));
        
        this.cb = new JComboBox<>(this.locations);
        
        // Buttons
        JButton approveButton = new JButton("Ok");
        approveButton.setActionCommand("approveAPselection");
        approveButton.addActionListener(this.al);
        
        JButton cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // inline window disposal ...
                JButton src = (JButton)e.getSource();
                JFrame superFrame = (JFrame)src.getParent().getParent().getParent().getParent(); // this is a bunch of layers we need to travel for finally finding the JFrame o.O 
                superFrame.dispose();
            }
        });
        
        super.getContentPane().add(cb);
        super.getContentPane().add(approveButton);
        super.getContentPane().add(cancelButton);
        
        cb.setAlignmentX(CENTER_ALIGNMENT);
        approveButton.setAlignmentX(CENTER_ALIGNMENT);
        cancelButton.setAlignmentX(CENTER_ALIGNMENT);
        
        super.pack();
        super.setLocationRelativeTo(null);
        super.setVisible(true);
    }
    
    public Airport getSelection() {
        return (Airport)cb.getModel().getSelectedItem();
    }
    
}