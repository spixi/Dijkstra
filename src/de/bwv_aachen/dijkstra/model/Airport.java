/**
 * Airport
 * <p>
 * This structure describes an airport and its connections to other airports.
 * 
 * @author Marius Spix
 */
package de.bwv_aachen.dijkstra.model;

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

        JSONValue.writeJSONString(airportObject, out);

        // TODO the output is a long string which is difficult to read
        // We may need a helper class which writes to a file and reformats the
        // file
        //
        // Here are some example implementations (but I think this would be much
        // to much).
        // The user still has the editor, so he will seldom directly open the
        // file.
        //
        // jsonlint: Online reformatter: https://www.jsonlint.com/ || GitHub:
        // https://github.com/zaach/jsonlint
        // yajl: Console program json_reformatter || GitHub:
        // https://github.com/lloyd/yajl (see reformatter)
    }

    @Override
    public int compareTo(Airport a) {
        // TODO Auto-generated method stub
        return this.getId().compareTo(a.getId());
    }
}
