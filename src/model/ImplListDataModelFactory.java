package model;

import helpers.DateHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ImplListDataModelFactory {
	
	public static final ImplListDataModelFactory INSTANCE = new ImplListDataModelFactory();
	private ImplListDataModelFactory() { }
	
	public ImplListDataModel factory(Reader r) throws IOException, ParseException, BadFileFormatException {
//TODO: non-confusing variable names
		
		ImplListDataModel product = new ImplListDataModel();
		
		JSONParser parser = new JSONParser();
		
		JSONArray data = cast(parser.parse(r), JSONArray.class);
		
		HashSet<Airport> airportList = product.getAirportList();
		
		for(Object o : data) {
			JSONObject airport = cast(o, JSONObject.class);
			
			String id   =            cast(airport.get("id"),String.class);
			String name =            cast(airport.get("name"),String.class);
			JSONArray destinations = cast(airport.get("destinations"),JSONArray.class);
			
			Airport a             = new Airport(Integer.valueOf(id),name);
			HashSet<Connection> c = a.getConnections();
			
			// TODO comments
			for(Object p : destinations) {
				JSONObject connections = cast(p, JSONObject.class);
				
				String id2      = cast(connections.get("id"),String.class);
				String duration = cast(connections.get("duration"),String.class);
				
				Connection connection =
						new Connection(Integer.valueOf(id2),
						               DateHelper.INSTANCE.stringToDuration(duration));
				
				c.add(connection);
			}
			
			airportList.add(a);
		}
		
		System.out.println(data);

		return product;
	}
	
	//TODO: more meaningful and understandable message
	@SuppressWarnings("unchecked")
	private <T> T cast(Object o, Class<T> clazz) throws BadFileFormatException {
		if (o.getClass() != clazz) {
			throw new BadFileFormatException("Fehlerhaftes Dateiformat!");
		}
		return (T)o;
	}
	
	public ImplListDataModel factory(File r) throws IOException, ParseException, FileNotFoundException, BadFileFormatException {
		return factory(new FileReader(r));
	}

}
