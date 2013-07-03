/**
 * ImplListDataModel
 * <p>
 * This is the real implementation of the data model.
 * 
 * @author Marius Spix
 */

package de.bwv_aachen.dijkstra.model;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.joda.time.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import de.bwv_aachen.dijkstra.controller.Controller;
import de.bwv_aachen.dijkstra.helpers.DateHelper;
import de.bwv_aachen.dijkstra.helpers.Pathfinder;

public class ImplListDataModel implements ListDataModel {

    private HashMap<Long, Airport> airportList;

    public ImplListDataModel() {
        airportList = new HashMap<Long, Airport>();
    }

    @Override
    public Vector<Airport> getLocations() {
        Vector<Airport> v = new Vector<Airport>();
        for (Airport a : airportList.values()) {
            v.add(a);
        }
        return v;
    }

    public HashMap<Long, Airport> getAirportList() {
        return airportList;
    }

    @Override
    public Vector<Vector<Object>> getRoute(Airport from, Airport to) {
        Pathfinder pf = new Pathfinder(from, airportList.values());
        Vector<Vector<Object>> routeHops = new Vector<Vector<Object>>();
        Airport previous = null;
        Duration sum = Duration.ZERO;
        Vector<Object> row;

        List<Airport> path = pf.determineShortestPathTo(to);
        Collections.reverse(path);

        for (Airport a : path) {
            row = new Vector<Object>();

            if (previous != null) {
                row.add(a);
                row.add(previous);

                Duration d = a.getConnections().get(previous).getDuration();

                // cumulate the durations
                sum = sum.plus(d);
                row.add(DateHelper.INSTANCE.durationToString(d));
                routeHops.add(0, row);
            }

            previous = a;
        }

        if (routeHops.size() > 1) {
            row = new Vector<Object>();
            row.add("Summe");
            row.add("");
            row.add(DateHelper.INSTANCE.durationToString(sum));
            routeHops.add(row);
        }

        return routeHops;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void writeJSONString(Writer out) throws IOException {
        JSONArray baseArray = new JSONArray();

        for (Airport a : airportList.values()) {
            // The Airport is JSONStreamAware, so you can simply add it here.
            baseArray.add(a);
        }

        JSONValue.writeJSONString(baseArray, out);
    }

}
