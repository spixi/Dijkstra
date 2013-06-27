/**
* ImplListDataModel
* <p>
* This factory creates the data model.
* @author Marius Spix
*/

package model;

import helpers.DateHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ImplListDataModelFactory {
	
	public static final ImplListDataModelFactory INSTANCE = new ImplListDataModelFactory();
	private ImplListDataModelFactory() { }
	
	/**
	* factory(Reader r)
	* @throws IOException
	* @throws ParseException
	* @throws BadFileFormatException
	* <p>
	* Creates the airport data model from a JSON stream
	*/
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
			
			JSONArray destinationsArray = cast(airportObject.get("destinations"),JSONArray.class);
			
			for(Object p : destinationsArray) {
				JSONObject connectionObject = cast(p, JSONObject.class);
				
				Long    destination_id       = cast(connectionObject.get("id"),Long.class);
				Airport destination_airport  = airportList.get(destination_id);
				String duration              = cast(connectionObject.get("duration"),String.class);
				
				HashMap<Airport,Connection> c = airport.getConnections();
				
				Connection connection = new Connection(DateHelper.INSTANCE.stringToDuration(duration));
				
				c.put(destination_airport, connection);
			}
		}

		return product;
	}
	
	/**
	* cast
	* @param o: the object which should be casted
	* @param expectedClass: the expected class
	* @return the casted object
	* @throws BadFileFormatException 
	* <p>
	* Casts an object to another object and throws an exception if the object has not the expected class
	*/ 
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
	
	/**
	* factory(Reader r)
	* @throws IOException
	* @throws ParseException
	* @throws BadFileFormatException
	* @throws FileNotFoundException
	* <p>
	* Creates the airport data model from a JSON file
	*/
	public ImplListDataModel factory(File r) throws IOException, ParseException, FileNotFoundException, BadFileFormatException {
		return factory(new FileReader(r));
	}

}
