package model;

import java.util.Arrays;
import java.util.Vector;

import model.ListDataModel;

/**
 * This is a dummy implementation for the GUI beeing able to get some data. Later when there is a correct and functionating
 * implemention this one may be deleted and the object of the working implementation referenced
 * @author serjoscha-87
 */

public final class DummyImplListDataModel extends ImplListDataModel {
	
	public DummyImplListDataModel() {
		super();
		getAirportList().addAll( Arrays.asList(
				new Airport (1, "Berlin"),
				new Airport (2, "London"), 
				new Airport (3, "Aachen"),
				new Airport (4, "Köln"),
				new Airport (5, "Kairo"),
				new Airport (6, "Frankfurt"),
				new Airport (7, "Dubai") 
		) );
	}

}
