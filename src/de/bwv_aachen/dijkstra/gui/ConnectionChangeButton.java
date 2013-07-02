package de.bwv_aachen.dijkstra.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.text.JTextComponent;

import org.joda.time.Duration;

import de.bwv_aachen.dijkstra.helpers.DateHelper;
import de.bwv_aachen.dijkstra.model.Connection;

public class ConnectionChangeButton extends JToggleButton implements ItemListener {
    private Connection c;
    private JTextComponent tx;
    private Color oldColor;
    
    public ConnectionChangeButton(Connection c, JTextComponent tx) {
        super("");
        this.c  = c;
        this.tx = tx;
        initializeTextField();
        editOff();
        this.addItemListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent ev) {
        Duration d = null;
        
        if(ev.getStateChange()==ItemEvent.SELECTED){
            editOn();
        }
        else if(ev.getStateChange()==ItemEvent.DESELECTED){
            try {
                d = DateHelper.INSTANCE.stringToDuration(tx.getText());
                editOff();
                c.setDuration(d);
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(this,e.getLocalizedMessage(),
                                              "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        tx.repaint();
    }
    
    private void initializeTextField() {
        tx.setText(DateHelper.INSTANCE.durationToString(c.getDuration()));
    }
    
    private void editOff() {
        tx.setEditable(false);
        this.setText("Bearbeiten");
    }
    
    private void editOn() {
        tx.setEditable(true);
        this.setText("Speichern");
    }

}
