/**
 * Airport
 * <p>
 * This structure describes an airport and its connections to other airports.
 * 
 * @author Marius Spix
 */
package de.bwv_aachen.dijkstra.model;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

import de.bwv_aachen.dijkstra.helpers.DateHelper;

public class Airport implements JSONStreamAware, Comparable<Airport> {
    private Long                         id;
    private String                       name;
    private JSONAwarePoint               position;

    // These are incoming connections, not outgoing connections!!!
    // In the file, however, the outgoing connections are saved.
    private HashMap<Airport, Connection> connections = new HashMap<Airport, Connection>();

    protected Airport(Long id) {
    }

    public Airport(Long id, String name) {
        setId(id);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Airport, Connection> getConnections() {
        return connections;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return getName();
    }
    
    public Point2D.Double getPosition() {
        return position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void writeJSONString(Writer out) throws IOException {
        JSONObject airportObject = new JSONObject();
        airportObject.put("id", id);
        // The name may contain special characters, e. g. the backspace (\). We
        // have to escape it!
        airportObject.put("name", JSONObject.escape(name));

        JSONArray destinationsArray = new JSONArray();

        for (Entry<Airport, Connection> c : connections.entrySet()) {
            // The Connection class does not know about its destination,
            // therefore we don't make it JSONStreamAware
            // but rather let this class do the JSON serialization for it.
            JSONObject connectionObject = new JSONObject();
            connectionObject.put("id", c.getKey().getId());
            connectionObject.put("duration", DateHelper.INSTANCE
                    .durationToString(c.getValue().getDuration()));
            destinationsArray.add(connectionObject);
        }

        airportObject.put("destinations", destinationsArray);
        airportObject.put("position", position);

        JSONValue.writeJSONString(airportObject, out);
    }

    @Override
    public int compareTo(Airport a) {
        // TODO Auto-generated method stub
        return this.getId().compareTo(a.getId());
    }
}
