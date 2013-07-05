package de.bwv_aachen.dijkstra.gui;

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
import java.awt.geom.Rectangle2D;
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
    private Point2D.Double         picCenter;
    private HashMap<Airport,Point2D.Double> points;
    private HashMap<Rectangle2D.Double,Airport> actionAreas;
    
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
        
        picCenter    = new Point2D.Double(airportPicture.getWidth()/2, airportPicture.getHeight()/2);
        points       = new HashMap<Airport,Point2D.Double>();
        actionAreas  = new HashMap<Rectangle2D.Double,Airport>();
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

                //Paint the airport ...
                g.drawImage(airportPicture, (int)Math.round(p.x-picCenter.x), (int)Math.round(p.y-picCenter.y), this);
               
                
                //... and its name.
                g.drawString(a.toString(), (int)Math.round(p.x), (int)Math.round(p.y));
                
                
                final double arrowEndHeight = 7D;
                final double arrowEndWeight = 6D;
                final double arrowAngle    = Math.toRadians( 30D );
                
                
                for(Airport dest: a.getConnections().keySet()) {
                    Point2D.Double destP = points.get(dest);
                    
                    //Draw the connections to the destinations
                    g.drawLine((int)Math.round(p.x), (int)Math.round(p.y), (int)Math.round(destP.x), (int)Math.round(destP.y));

   
                    //TODO: add arrow ends to differ mono- and bidirectional connections
                    //This was too difficult. I spend about six hours and was not able to draw the arrow end
                     /*
                    //The arrow end is an isosceles triangle with its base being orthogonal to a point on the line between the airports.
                    Point2D.Double baseOfTriangle = new Point2D.Double(((destP.x - p.x) - arrowEndHeight) + p.x, ((destP.y - p.y) - arrowEndHeight) + p.y);
                    
                    //Determine the slope (m) with the equation m = (y2-y1) / (x2-x1)
                    double slope = (baseOfTriangle.y - destP.y) / (baseOfTriangle.x - destP.x);
                    
                    //inverse the slope because we want to have the perpendicular
                    double inversedSlope = 1 / slope;
                    
                    //Determine Y-axis intercept (b) with the equation y = mx + b
                    //We know that the baseOfTriangle point is on this line.
                    //m is known, take x and y from the known point 
                    //Adjust the formula to b = y - mx
                    double yIntercept = baseOfTriangle.y - (inversedSlope * baseOfTriangle.x);
                    
                    //Now we know the Y-axis intercept and can reuse the formula x = mx+b
                    //
                     */
                    
                    //Workaround
                    //g.fillOval((int)(destP.x-arrowEndWeight/2), (int)(destP.y-arrowEndWeight/2),(int)Math.round(arrowEndWeight),(int)Math.round(arrowEndWeight));

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
        
        
        
        this.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                Point clicked = me.getPoint();
                
                //look through all airports until you find 
                
                for(Map.Entry<Airport, Point2D.Double> entry: points.entrySet()) {
                    Airport        a = entry.getKey();
                    Point2D.Double p = entry.getValue();
                    
                    //TODO: Maybe we can buffer this
                    Rectangle dummy = new Rectangle((int)Math.round(p.x-picCenter.x),
                                                    (int)Math.round(p.y-picCenter.y),
                                                     airportPicture.getWidth(),
                                                     airportPicture.getHeight());
                
                    if (dummy.contains(clicked)) {
                        System.out.println(a + " wurde geklickt!");
                    }
                
                }
                
             }
            });
        
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

            int x = (int)Math.round(panelCenter.x + Math.sin(alpha) * circleRadius);
            int y = (int)Math.round(panelCenter.y - Math.cos(alpha) * circleRadius);
            
            //Remember the points for further use
            points.put((Airport) airports[i], new Point2D.Double(x,y));
            
            //create actionAreas for catching mouse events
            actionAreas.clear();
            for(Map.Entry<Airport, Point2D.Double> entry: points.entrySet()) {
                Airport        a = entry.getKey();
                Point2D.Double p = entry.getValue();

                actionAreas.put( new Rectangle2D.Double(p.x-picCenter.x,
                                                        p.y-picCenter.y,
                                                        airportPicture.getWidth(),
                                                        airportPicture.getHeight()),
                                a);
            }
        }  
        
    }

}
