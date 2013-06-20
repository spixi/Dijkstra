package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ImplListDataModelFactory {
	
	public static final ImplListDataModelFactory INSTANCE = new ImplListDataModelFactory();
	private ImplListDataModelFactory() { }
	
	private JSONParser parser = new JSONParser();
	
	public ImplListDataModel factory(Reader r) throws IOException, ParseException {
		ImplListDataModel product = new ImplListDataModel();
		
		JSONParser parser = new JSONParser();
	    ContainerFactory containerFactory = new ContainerFactory() {
		    public List creatArrayContainer() {
		      return new LinkedList();
		    }

		    public Map createObjectContainer() {
		      return new LinkedHashMap();
		    }                   
		};
		
		//TODO

		
		Object data = parser.parse(r);

		return product;
	}
	
	public ImplListDataModel factory(File r) throws IOException, ParseException, FileNotFoundException {
		return factory(new FileReader(r));
	}

}
