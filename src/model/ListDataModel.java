/**
* ImplListDataModel
* <p>
* This is an interface for the data model.
* @author Serjoscha Bassauer
*/

package model;

import java.util.Vector;

public interface ListDataModel {

	/**
	* getLocations()
	* <p>
	* @return see below
	* Returns a list of the Airports in the data model
	*/
	public Vector<Airport> getLocations();
	
	/**
	* getLocations()
	* <p>
	* @return see below
	* Returns a table of the route from one airport to another.
	*/
	public Vector<Vector<Object>> getRoute(Airport from, Airport to);
	
}
