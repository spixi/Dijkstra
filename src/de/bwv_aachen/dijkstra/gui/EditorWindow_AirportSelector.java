package de.bwv_aachen.dijkstra.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


import de.bwv_aachen.dijkstra.controller.Controller;
import de.bwv_aachen.dijkstra.model.Airport;

@SuppressWarnings("serial")
public class EditorWindow_AirportSelector extends View {

    Vector<Airport> locations;
    ActionListener al;
    
    // Beans
    private JComboBox<Object> cb;
    
    public EditorWindow_AirportSelector(Controller c, ActionListener al) {
        super("EditorWindow_AriportSelector",c);
        this.al = al;
    }
    
    public void draw() {
        super.setTitle("Ziel wähle...");
        super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        super.setResizable(false);
        super.setAlwaysOnTop(true); // this is a bit hacky..
        
        super.setLayout(new BoxLayout(super.getContentPane(), BoxLayout.PAGE_AXIS));

        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        ComboBoxModel<Object> cbm = new DefaultComboBoxModel<Object>(controller.getModel().getAirportList().values().toArray());
        this.cb = new JComboBox<Object>(cbm);
        
        // Buttons
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new FlowLayout());
        buttonContainer.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        
        JButton approveButton = new JButton("Ok");
        approveButton.setActionCommand("approveAPselection");
        approveButton.addActionListener(this.al);
        
        JButton cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(new ActionListener() {
            JFrame thisWin;
            public ActionListener c(JFrame f) {
                thisWin = f;
                return this;
            }
            public void actionPerformed(ActionEvent e) { // inline window disposal ...
                thisWin.dispose();
            }
        }.c(this));
        
        buttonContainer.add(approveButton);
        buttonContainer.add(cancelButton);
        
        super.getContentPane().add(cb);
        //super.getContentPane().add(approveButton);
        //super.getContentPane().add(cancelButton);
        super.getContentPane().add(buttonContainer);
        
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
