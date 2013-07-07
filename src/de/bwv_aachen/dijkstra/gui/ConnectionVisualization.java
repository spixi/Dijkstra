package de.bwv_aachen.dijkstra.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.bwv_aachen.dijkstra.controller.Controller;
import de.bwv_aachen.dijkstra.model.Airport;
import de.bwv_aachen.dijkstra.model.Connection;

public class ConnectionVisualization extends View {

    private static final long serialVersionUID = 1527659470763038523L;
    
    private BufferedImage airportPicture;
    private final Dimension        panelDimension = new Dimension(660,660);
    private final Color            labelColor  = Color.BLACK;
    private final Font             labelFont   = new Font("sans-serif", Font.BOLD, 14);
    private final ConnectionVisualizationMouseAdapter mouseAdapter = new ConnectionVisualizationMouseAdapter(this);
    private Point2D.Double         picCenter;
    private HashMap<Airport,Point2D.Double> points;
    private HashMap<Rectangle2D.Double,Airport> actionAreas;
    
    private Airport startAirport = null;
    private Airport destinationAirport = null;
    private final String space = " ";
    
    private JLabel statusText;
    
    private final double circleRadius = 200D;
    
    public void setStartAirport(Airport ap) {
        this.startAirport = ap;
    }
    
    public void setDestinationAirport(Airport ap) {
        this.destinationAirport = ap;
    }
    
    public Airport getStartAirport() {
        return this.startAirport;
    }
    
    public Airport getDestinationAirport() {
        return this.destinationAirport;
    }
    
    public HashMap<Rectangle2D.Double,Airport> getActionAreas() {
        return this.actionAreas;
    }
    
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
        
        picCenter    = new Point2D.Double(airportPicture.getWidth()/2, airportPicture.getHeight()/2);
        points       = new HashMap<Airport,Point2D.Double>();
        actionAreas  = new HashMap<Rectangle2D.Double,Airport>();
        
        //We initialize the JLabel with a space because a switch from the empty string "" to a non-empty string will cause a repaint
        statusText   = new JLabel(space);
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
            
            for(Map.Entry<Airport, Point2D.Double> entry: points.entrySet()) {
                Airport        a = entry.getKey();
                Point2D.Double p = entry.getValue();
                
                //Set paint mode on ...
                g.setColor(labelColor);
                g.setPaintMode();

                //Paint the airport ...
                g.drawImage(airportPicture, (int)Math.round(p.x-picCenter.x), (int)Math.round(p.y-picCenter.y), this);
                
                //If the airport is the start or the destination airport, change the color
                if (a == ConnectionVisualization.this.startAirport) {
                    g.setColor(Color.YELLOW);
                }
                
                if (a == ConnectionVisualization.this.destinationAirport) {
                    g.setColor(Color.BLUE);
                }
               
                //... and its name.
                g.drawString(a.toString(), (int)Math.round(p.x), (int)Math.round(p.y));
                
                //Set paint mode on ...
                g.setColor(labelColor);
                g.setPaintMode();
                
                for(Airport dest: a.getConnections().keySet()) {
                    Point2D.Double destP = points.get(dest);
                    
                    //Draw the connections to the destinations
                    g.drawLine((int)Math.round(p.x), (int)Math.round(p.y), (int)Math.round(destP.x), (int)Math.round(destP.y));
                }
            }
            
            //TODO refactor this stuff here
            
            //redraw the connections of the startAirport
            if (startAirport != null) {
                g.setXORMode(Color.YELLOW); //Note: we are using XOR mode to show bidirectional connection as YELLOW xor BLUE = WHITE !!
                for(Airport dest: startAirport.getConnections().keySet()) {
                    Point2D.Double destP = points.get(dest);
                    
                    //Draw the connections to the destinations
                    g.drawLine((int)Math.round(points.get(startAirport).x), (int)Math.round(points.get(startAirport).y), (int)Math.round(destP.x), (int)Math.round(destP.y));
                }  
            }
            
            
            //redraw the connections of the destinationAirport
            if (destinationAirport != null) {
                g.setXORMode(Color.BLUE);
                for(Airport dest: destinationAirport.getConnections().keySet()) {
                    Point2D.Double destP = points.get(dest);
                    
                    //Draw the connections to the destinations
                    g.drawLine((int)Math.round(points.get(destinationAirport).x), (int)Math.round(points.get(destinationAirport).y), (int)Math.round(destP.x), (int)Math.round(destP.y));
                }  
            }
        }
    }

    @Override
    public void draw() {
        String statusTextValue;
        this.getContentPane().removeAll();
        
        if(startAirport != null) {
            statusTextValue = "Verbindung von " + startAirport.getName();
            if(destinationAirport != null) {
                statusTextValue += " nach " + destinationAirport.getName();
            }
        }
        else {
            statusTextValue = space;
        }
        
        statusText.setText(statusTextValue);
        
        this.getContentPane().add(statusText, BorderLayout.SOUTH);
        
        this.setMinimumSize(panelDimension);
        this.getContentPane().add(new VisualizationPanel());
        
        if ( points.isEmpty() ) {
            //determine the points for the airports
            determinePoints();
        }
        
        this.addMouseListener(mouseAdapter);
        
        super.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    
    //The controller may also call this method. So we make it public!
    public void determinePoints() {     
        Object[] airports    = controller.getModel().getAirportList().values().toArray();
        Point    panelCenter = new Point(this.getWidth()/2, this.getHeight()/2);
        
        points.clear();
        
        int numOfNodes = airports.length;
        if (numOfNodes == 0)
                return;
        
        final double angle          = Math.toRadians( 360D / numOfNodes );

        for (int i = 0; i < numOfNodes; i++) {
            double alpha = angle * i;
            
            //The points are arranged in a n-gon with the radius circleRadius

            double x = panelCenter.x + Math.sin(alpha) * circleRadius;
            double y = panelCenter.y - Math.cos(alpha) * circleRadius;
            
            //Remember the points for further use
            points.put((Airport) airports[i], new Point2D.Double(x,y));
            
            //create actionAreas for catching mouse events
            actionAreas.clear();
            for(Map.Entry<Airport, Point2D.Double> entry: points.entrySet()) {
                Airport        a = entry.getKey();
                Point2D.Double p = entry.getValue();

                actionAreas.put( new Rectangle2D.Double(p.x-picCenter.x,
                                                        p.y-picCenter.y,
                                                        airportPicture.getHeight(),
                                                        airportPicture.getWidth()),
                                a);
            }
        }  
        
    }

}
