package helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import model.BadFileFormatException;
import model.ImplListDataModel;
import model.ImplListDataModelFactory;
import model.ListDataModel;

/**
 * This is a helper class designed to have one central point to configure Classes used within the context of the program.
 * Changes may easily be done by changing the returned object to an instance of another class
 * @author serjoscha-87
 */

public class ClassRouteHelper {

	/**
	 * This method shall ALWAYS return a instance of the correct class to use (the correct implementation)
	 * @return {@link ListDataModel} a instance of an ListDataModel implementation
	 * @throws BadFileFormatException 
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static ListDataModel getListModel() throws FileNotFoundException, IOException, ParseException, BadFileFormatException {
		File f = new File("test/testconnection.json");
		return ImplListDataModelFactory.INSTANCE.factory(f);
	}
	
}
