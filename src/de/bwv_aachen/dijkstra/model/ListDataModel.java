/**
 * ImplListDataModel
 * <p>
 * This is an interface for the data model.
 * 
 * @author Serjoscha Bassauer
 */

package de.bwv_aachen.dijkstra.model;

import java.util.Map;
import java.util.Vector;

import org.json.simple.JSONStreamAware;

public interface ListDataModel extends JSONStreamAware {

    /**
     * getLocations()
     * <p>
     * 
     * @return see below Returns a list of the Airports in the data model
     */
    @Deprecated //Use getAirportList() instead
    public Vector<Airport> getLocations();
    
    public Map<Long,Airport> getAirportList();

    /**
     * getLocations()
     * <p>
     * 
     * @return see below Returns a table of the route from one airport to
     *         another.
     */
    public Vector<Vector<Object>> getRoute(Airport from, Airport to);

}
