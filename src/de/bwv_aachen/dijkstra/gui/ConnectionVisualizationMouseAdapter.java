package de.bwv_aachen.dijkstra.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import de.bwv_aachen.dijkstra.model.Airport;

public class ConnectionVisualizationMouseAdapter extends MouseAdapter {
    
        private ConnectionVisualization cv;
    
        public ConnectionVisualizationMouseAdapter(ConnectionVisualization cv) {
            this.cv = cv;
        }

        @Override
        public void mouseClicked(MouseEvent me) {
            Point clicked = me.getPoint();
            
            //TODO refactor this, maybe with the State design pattern
            //look through all actionAreas until you find the airport
            
            //Maybe we could also do the painting stuff in the paint method of the

           for (Map.Entry<Rectangle2D.Double,Airport> entry : cv.getActionAreas().entrySet()) {
               Rectangle2D.Double area = entry.getKey();
               Airport            ap   = entry.getValue();
               
                //check whether the clicked point is within a actionArea
                if (area.contains(clicked)) {
                     if (cv.getStartAirport() == null) {
                         //Selection of the start airport
                         cv.setStartAirport(ap);
                     }
                     
                     else if (cv.getDestinationAirport() == null) {
                         //Selection of the destinationAirport
                         
                         if (ap == cv.getStartAirport()) {
                             //The destination airport is equal to the start airport
                             //Undo the selection
                             cv.setStartAirport(null);
                         } else {
                             //Set the destination airport
                             cv.setDestinationAirport(ap);
                         }
                     }
                     
                     else {
                         //another airport is clicked, simply repaint the connection visualization
                         cv.setStartAirport(null);
                         cv.setDestinationAirport(null);
                     }

                     //repaint everything
                     cv.repaint();
                }
            }
      
         }

}
