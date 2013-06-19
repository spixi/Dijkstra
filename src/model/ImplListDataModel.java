package model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ImplListDataModel implements ListDataModel {

	public Vector<String> getLocations() {
		Vector<String> tempVector = new Vector<>();
		JSONParser jp = new JSONParser();
		try {
			JSONArray allElems = (JSONArray) jp.parse(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("test/testconnection.json")));
			for(int i = 0; i < allElems.size(); i++) {
				JSONObject elem = (JSONObject)allElems.get(i);
				tempVector.add(elem.get("name").toString());
			}
		} catch (IOException e) { e.printStackTrace(); } catch (ParseException e) { e.printStackTrace(); }
		return tempVector;
	}

}
