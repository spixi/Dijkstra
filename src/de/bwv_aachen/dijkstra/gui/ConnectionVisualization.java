package de.bwv_aachen.dijkstra.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.bwv_aachen.dijkstra.controller.Controller;
import de.bwv_aachen.dijkstra.model.Airport;

public class ConnectionVisualization extends View {

    private static final long serialVersionUID = 1527659470763038523L;
    
    private BufferedImage airportPicture;
    private final Dimension        panelDimension = new Dimension(660,660);
    private final Color            labelColor  = Color.BLACK;
    private final Font             labelFont   = new Font("sans-serif", Font.BOLD, 14);
    private Point                  picCenter;
    private HashMap<Airport,Point> points;
    
    static int testNoRepaint = 0;
    
    private final double circleRadius = 200D;
    
    public ConnectionVisualization(Controller c) {
        super("ConnectionVisualization",c);
        try {
            //Let the controller do this?
            airportPicture = ImageIO.read(new File("res/airport-icon_2.gif"));
        }
        catch (IOException e) {
            //TODO
            System.out.println("Bild nicht gefunden");
        }
        
        picCenter = new Point(airportPicture.getWidth()/2, airportPicture.getHeight()/2);
        points    = new HashMap<Airport,Point>();
    }
    
    class VisualizationPanel extends JPanel {
        private static final long serialVersionUID = -3979908130673351633L;

        @Override
        public void paint(Graphics g) {
            //TODO: Replace with a Panel with an ImageIcon and a JLabel per Airport ???
            
            super.paint(g); // call superclass's paint
            
            //Causes recalculation on each change of the window size, position etc ...
            //determinePoints();
            
            //No runtime-intensive calculations here, since this method is called a huge amount of times!
            g.setFont(labelFont);
            g.setColor(labelColor);
            
            for(Map.Entry<Airport, Point> entry: points.entrySet()) {
                Airport a = entry.getKey();
                Point   p = entry.getValue();
                
                g.drawImage(airportPicture, p.x-picCenter.x, p.y-picCenter.y, null);
                g.drawString(a.toString(), p.x, p.y);
                for(Airport dest: a.getConnections().keySet()) {
                    Point destP = points.get(dest);
                    //TODO: add arrow ends to differ mono- and bidirectional connections
                    g.drawLine(p.x, p.y, destP.x, destP.y);
                }
            }
        }
    }

    @Override
    public void draw() {
        this.getContentPane().removeAll();
        
        this.setMinimumSize(panelDimension);
        this.getContentPane().add(new VisualizationPanel());
        
        if ( points.isEmpty() ) {
            //determine the points for the airports
            determinePoints();
        }
        
        super.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    
    //The controller may also call this method. So we make it public!
    public void determinePoints() {     
        Object[] airports    = controller.getModel().getAirportList().values().toArray();
        Point    panelCenter = new Point(this.getWidth()/2, this.getHeight()/2);
        
        int numOfNodes = airports.length;
        if (numOfNodes == 0)
                return;
        
        final double angle          = Math.toRadians( 360D / numOfNodes );

        for (int i = 0; i < numOfNodes; i++) {
            double alpha = angle * i;
            
            //The points are arranged in a n-gon with the radius circleRadius

            int x = (int)Math.round(panelCenter.x + Math.sin(alpha) * circleRadius);
            int y = (int)Math.round(panelCenter.y - Math.cos(alpha) * circleRadius);
            
            //Remember the points for further use
            points.put((Airport) airports[i], new Point(x,y));
        }  
        
    }

}
