package de.bwv_aachen.dijkstra.model;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Writer;

import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

public class JSONAwarePoint extends Point2D.Double implements JSONStreamAware {

    @Override
    public void writeJSONString(Writer out) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("x", this.x);
        jsonObject.put("y", this.y);
        JSONValue.writeJSONString(jsonObject, out);
    }

}
