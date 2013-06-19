package model;

import java.util.Arrays;
import java.util.Vector;

import model.ListDataModel;

/**
 * This is a dummy implementation for the GUI beeing able to get some data. Later when there is a correct and functionating
 * implemention this one may be deleted and the object of the working implementation referenced
 * @author serjoscha-87
 */

public class DummyImplListDataModel implements ListDataModel {

	public Vector<String> getLocations() {
		return new Vector<String>(Arrays.asList(
									"Berlin",
									"London", 
									"Aachen",
									"Köln",
									"Kairo",
									"Frankfurt",
									"Dubai"
								));
	}

}
