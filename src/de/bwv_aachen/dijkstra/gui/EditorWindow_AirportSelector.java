package de.bwv_aachen.dijkstra.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
        
        super.getContentPane().setLayout(new GridLayout(1, 2));

        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        ComboBoxModel<Object> cbm = new DefaultComboBoxModel<Object>(controller.getModel().getAirportList().values().toArray());
        this.cb = new JComboBox<Object>(cbm);
        
        // Containers for a left and a right ares
        JPanel leftArea = new JPanel();
        JPanel rightArea = new JPanel(); // for the modals form contents
        rightArea.setLayout(new BoxLayout(rightArea, BoxLayout.Y_AXIS));
        
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
        
        // Icon for the left side
        JLabel imgLabel = new JLabel(new ImageIcon("res/airport-icon.gif"));
        //imgLabel.setOpaque(false);
        //imgLabel.setBackground(new Color(0, 0, 0, 0));
        leftArea.add(imgLabel);

        // label for the right side
        JLabel decoText = new JLabel("Bitte wählen Sie das Ziel:");
        decoText.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // add stuff to the panel
        rightArea.add(decoText);
        rightArea.add(cb);
        rightArea.add(buttonContainer);
        
        // align beans
        decoText.setAlignmentX(CENTER_ALIGNMENT); // TODO why the f does this not work with left?!
        cb.setAlignmentX(CENTER_ALIGNMENT);
        
        // add panels to the the frame
        super.getContentPane().add(leftArea);
        super.getContentPane().add(rightArea);
        
        // rest stuff
        super.pack();
        super.setLocationRelativeTo(null);
        super.setVisible(true);
    }
    
    public Airport getSelection() {
        return (Airport)cb.getModel().getSelectedItem();
    }
    
}
