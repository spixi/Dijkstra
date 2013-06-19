package model;

import java.util.Arrays;
import java.util.Vector;

import model.ListDataModel;

public class DummyImplListDataModel implements ListDataModel {

	public Vector<String> getPossibleLocations() {
		return new Vector<String>(Arrays.asList(
									"Berlin",
									"London", 
									"Aachen"
								));
	}

}
