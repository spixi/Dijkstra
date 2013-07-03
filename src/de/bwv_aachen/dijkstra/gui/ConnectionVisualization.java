package de.bwv_aachen.dijkstra.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

import de.bwv_aachen.dijkstra.controller.Controller;

public class ConnectionVisualization extends View {
    
    public ConnectionVisualization(Controller c) {
        super("ConnectionVisualization",c);
    }
    
    class VisualizationPanel extends JPanel {
        private static final long serialVersionUID = -3979908130673351633L;

        @Override
        public void paintComponent( Graphics g ) {
            super.paintComponent( g ); // call superclass's paintComponent 
            
            setSize(200, 200);
            Graphics2D g2d = ( Graphics2D ) g; // cast g to Graphics2D
            g2d.setPaint( Color.BLACK);                                    
            g2d.fill( new Ellipse2D.Double( 5, 30, 65, 100 ) );    
        }
    }

    @Override
    public void draw() {
        this.getContentPane().add(new VisualizationPanel());
        this.pack();
        this.setSize(200, 200);
        this.setVisible(true);
    }


}
