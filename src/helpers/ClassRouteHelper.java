package helpers;

import model.DummyImplListDataModel;
import model.ImplListDataModel;
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
	 */
	public static ListDataModel getListModel() {
		return new DummyImplListDataModel();
		//return new ImplListDataModel();
	}
	
}
