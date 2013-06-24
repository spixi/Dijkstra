package model;

import helpers.DateHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ImplListDataModelFactory {
	
	public static final ImplListDataModelFactory INSTANCE = new ImplListDataModelFactory();
	private ImplListDataModelFactory() { }
	
	public ImplListDataModel factory(Reader r) throws IOException, ParseException, BadFileFormatException {

		ImplListDataModel product = new ImplListDataModel();
		
		JSONParser parser = new JSONParser();
		
		JSONArray baseArray = cast(parser.parse(r), JSONArray.class);
		
		HashMap<Long,Airport> airportList = product.getAirportList();
		
		
		//Parse the airports first
		for(Object o : baseArray) {
			JSONObject airportObject = cast(o, JSONObject.class);
			
			Long id     =  cast(airportObject.get("id"),Long.class);
			String name =  cast(airportObject.get("name"),String.class);
			
			airportList.put(id, new Airport(id,name));
		}
		
		//Then parse the connections
		for(Object o : baseArray) {
			JSONObject airportObject = cast(o, JSONObject.class);
			
			Long      id      = cast(airportObject.get("id"),Long.class);
			Airport   airport = airportList.get(id);
			
			//airport cannot be found, so skip it
			if (airport == null)
				continue;
			
			JSONArray destinations = cast(airportObject.get("destinations"),JSONArray.class);
			
			for(Object p : destinations) {
				JSONObject connections = cast(p, JSONObject.class);
				
				Long    destination_id       = cast(connections.get("id"),Long.class);
				Airport destination_airport  = airportList.get(destination_id);
				String duration              = cast(connections.get("duration"),String.class);
				
				HashMap<Airport,Connection> c = destination_airport.getConnections();
				
				Connection connection =
						new Connection(DateHelper.INSTANCE.stringToDuration(duration));
				
				c.put(airport, connection);
			}
		}

		return product;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T cast(Object o, Class<T> expectedClass) throws BadFileFormatException {
		Class<?> catchedClass = o.getClass();
		if (catchedClass != expectedClass) {
			throw new BadFileFormatException("Fehlerhaftes Format!"
					                         + " Feld: "     + o.toString()
					                         + " Erwartet: " + expectedClass.toString()
					                         + " Gefunden: " + catchedClass.toString() );
		}
		return (T)o;
	}
	
	public ImplListDataModel factory(File r) throws IOException, ParseException, FileNotFoundException, BadFileFormatException {
		return factory(new FileReader(r));
	}

}
