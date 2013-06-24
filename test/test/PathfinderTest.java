/**
* PathfinderTest
* <p>
* Test class for the Pathfinder class
* @author Marius Spix
*/

package test;

import static org.junit.Assert.*;

import helpers.Pathfinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import model.Airport;
import model.BadFileFormatException;
import model.ImplListDataModel;
import model.ImplListDataModelFactory;

import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;

public class PathfinderTest {
	
	private static ImplListDataModel m;
	private static HashMap<Long,Airport> l;
	private static Pathfinder pf;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws FileNotFoundException, IOException, ParseException, BadFileFormatException {
		File f = new File("test/testconnection.json");
		
		m = ImplListDataModelFactory.INSTANCE.factory(f);
		l = m.getAirportList();
		pf = new Pathfinder(l.get(1L), l.values());
	}
	
	/***
	 * pathfinderTest
	 * <p>
	 * Template for tests of the Pathfinder class
	 * @param destination: destination from the start airport
	 * @param should: expected hops
	 */
	private void pathfinderTest(Airport destination, Airport... should) {
		Airport[] is = new Airport[should.length];
		
		List<Airport> q =
		pf.determineShortestPathTo(destination);
		
		q.toArray(is);
		
		assertArrayEquals(should, is);
	}
	
	@Test
	/**
	 * testPathfinder2
	 * <p>
	 * Test the pathfinder class.
	 */
	public void testPathfinder1() {		
		pathfinderTest(l.get(6L), l.get(1L), l.get(7L), l.get(6L));
	}
	
	@Test
	/**
	 * testPathfinder2
	 * <p>
	 * Test the route to the start airport itself
	 */
	public void testPathfinder2() {		
		pathfinderTest(l.get(1L), l.get(1L));
	}
	
	@Test
	/**
	 * testPathfinder2
	 * <p>
	 * Test an unreachable airport
	 */
	public void testPathfinder3() {		
		pathfinderTest(l.get(8L), l.get(8L));
	}

}
