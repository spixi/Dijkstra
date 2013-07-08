package de.bwv_aachen.dijkstra.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.bwv_aachen.dijkstra.controller.Controller;
import de.bwv_aachen.dijkstra.helpers.Pathfinder;
import de.bwv_aachen.dijkstra.model.Airport;

public class ConnectionVisualization extends View implements ActionListener {

    private static final long serialVersionUID = 1527659470763038523L;
    
    private BufferedImage airportPicture;
    private final Dimension        panelDimension = new Dimension(660,660);
    private final Color            labelColor  = Color.BLACK;
    private final Font             labelFont   = new Font("sans-serif", Font.BOLD, 14);
    private final ConnectionVisualizationMouseAdapter mouseAdapter = new ConnectionVisualizationMouseAdapter(this);
    private Point2D.Double         picCenter;
    private HashMap<Airport,Point2D.Double> points;
    private HashMap<Rectangle2D.Double,Airport> actionAreas;
    private final JButton calcButton;
    
    private Airport startAirport = null;
    private Airport destinationAirport = null;
    private Collection<Airport> route = null;
    private final String space = " ";
    
    private JLabel statusText;
    
    private final double circleRadius = 200D;
    
    public void setStartAirport(Airport ap) {
        this.startAirport = ap;
        this.route        = null;
    }
    
    public void setDestinationAirport(Airport ap) {
        this.destinationAirport = ap;
        this.route              = null;
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
        
        //Pseudo initialization of the JPanel
        statusText   = new JLabel(space);
        
        //Initialization of the JButton
        calcButton   = new JButton("Berechnen");
        calcButton.setActionCommand("calculate");
        calcButton.addActionListener(this);
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
                    g.setColor(Color.RED);
                }
                
                if (a == ConnectionVisualization.this.destinationAirport) {
                    g.setColor(Color.CYAN);
                }
               
                //Paint the airport's name
                g.drawString(a.toString(), (int)Math.round(p.x), (int)Math.round(p.y));
                
                //Set paint mode on ...
                g.setColor(labelColor);
                g.setPaintMode();
                
                //Draw the connections to the destinations
                drawConnectionsOfAirport(a, g);
            }
            
            //Set the status text
            statusText.setText(getStatusText());
            
            //Set the calculation button
            if (startAirport == null || destinationAirport == null) {
                calcButton.setEnabled(false);
            }
            else {
            //Only enable when startAirport and destinationAirport are selected
                calcButton.setEnabled(true);
            }

            //a given route is painted in green
            if (route != null) {
                Airport source = null, dest = null;
                
                g.setColor(Color.GREEN);
                
                Iterator<Airport> it = route.iterator();
                while(it.hasNext()) {
                    dest = it.next();
                    drawConnectionBetween(source, dest, g);
                    source = dest;
                }
            }
            else {
            //No route is given. Assume that we are in selection mode
            
            //redraw the connections of the startAirport
            //Note: we are using XOR mode to show bidirectional connection as RED xor CYAN = WHITE !!
            g.setXORMode(Color.RED);
            drawConnectionsOfAirport(startAirport, g);
            
            //redraw the connections of the destinationAirport
            g.setXORMode(Color.CYAN);
            drawConnectionsOfAirport(destinationAirport, g);
            
            }
        }
    }
    
    private void drawConnectionsOfAirport(Airport ap, Graphics g) {
        //do nothing if ap is null
        if (ap == null) {
            return;
        }

        for(Airport dest: ap.getConnections().keySet()) {
            //Draw the connections to the destinations
            drawConnectionBetween(ap, dest, g);
        }  
    }
    
    private void drawConnectionBetween(Airport source, Airport dest, Graphics g) {
       Point2D.Double sourceP, destP;
       //return if the source or the destination is null
       if (source == null || dest == null) {
           return;
       }
       sourceP    = points.get(source);
       destP      = points.get(dest);
       g.drawLine((int)Math.round(sourceP.x), (int)Math.round(sourceP.y), (int)Math.round(destP.x), (int)Math.round(destP.y));
    }

    @Override
    public void draw() {
        this.getContentPane().removeAll();
        
        JPanel statusbar = new JPanel();
        statusbar.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        statusbar.add(statusText);
        statusbar.add(calcButton);
        
        if (startAirport != null && destinationAirport != null) {
            calcButton.setEnabled(true);
        }
        
        this.getContentPane().add(statusbar, BorderLayout.SOUTH);
        
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
    
    public String getStatusText() {
        String statusTextValue;
        
        if(startAirport != null) {
            statusTextValue = "Verbindung von " + startAirport.getName();
            if(destinationAirport != null) {
                statusTextValue += " nach " + destinationAirport.getName();
            }
        }
        else {
            statusTextValue = space;
        }
        
        return statusTextValue;
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

    @Override
    public void actionPerformed(ActionEvent ev) {
        switch (ev.getActionCommand()) {
        case "calculate": {
                route = new Pathfinder(startAirport,points.keySet()).determineShortestPathTo(destinationAirport);
                this.getContentPane().repaint();
                break;
               }
            }
        }
        
    }
