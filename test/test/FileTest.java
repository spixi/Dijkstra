/**
* FileTest
* <p>
* Test file reading classes
* @author Marius Spix
*/
package test;

import helpers.DateHelper;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Airport;
import model.Connection;
import model.ImplListDataModel;
import model.ImplListDataModelFactory;

import static org.junit.Assert.*;

public class FileTest {
	
	private static ImplListDataModel model;
	private static HashMap<Long, Airport> airportList;
	private static Airport airport;
	private static HashMap<Airport,Connection> connections;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {
		model       = ImplListDataModelFactory.INSTANCE.factory(new File("test/testconnection.json"));
		airportList = model.getAirportList();
		airport     = airportList.get(1L);
		connections = airport.getConnections();
	}

	
	@Test
	/**
	 * fileTest1
	 * <p>
	 * Test whether the name is correctly read
	 */
	public void fileTest1() throws Throwable {
		assertEquals("Frankfurt",airport.getName());
	}
	
	@Test
	/**
	 * fileTest2
	 * <p>
	 * Assure a destination is added
	 */
	public void fileTest2() throws Throwable {
		Airport dest_airport = airportList.get(4L);
		assertTrue(connections.containsKey(dest_airport));
	}
	
	@Test
	/**
	 * fileTest3
	 * <p>
	 * Assure that no wrong destination is added
	 */
	public void fileTest3() throws Throwable {
		Airport no_dest_airport  = airportList.get(2L);
		assertFalse(connections.containsKey(no_dest_airport));
	}
	
	@Test
	/**
	 * fileTest4
	 * <p>
	 * Test whether the duration is correctly read
	 */
	public void fileTest4() throws Throwable {
		Airport    dest_airport        = airportList.get(4L);
		assertEquals(DateHelper.INSTANCE.stringToDuration("01:24"),connections.get(dest_airport).getDuration());
	}
	
}
