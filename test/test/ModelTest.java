/**
* ModelTest
* <p>
* Test class for the Model class
* @author Marius Spix
*/
package test;

import static org.junit.Assert.*;

import helpers.DateHelper;

import model.Airport;
import model.Connection;

import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;

public class ModelTest {
	
	Long id;
	String  name;
	Airport a;

	@Before
	public void setUp() throws Exception {
		id = 1L;
		name = "Frankfurt";
		a = new Airport(id,name);
	}

	@Test
	/**
	 * testAirport1
	 * <p>
	 * Test the constructor of the Airport class
	 */
	public void testAirport1() {
		assertEquals(name, a.getName());
		assertEquals(id, a.getId());
	}
	
	@Test
	/**
	 * testAirport2
	 * <p>
	 * Test the setters of the Airport class
	 */
	public void testAirport2() {
		String  new_name = "London";
		a.setName(new_name);
		assertEquals(new_name, a.getName());
	}
	
	@Test
	/**
	 * testConnection1
	 * <p>
	 * Test the constructor of the Connection class
	 */
    public void testConnection1() {
		Duration d = DateHelper.INSTANCE.stringToDuration("00:35");
		
		Connection c = new Connection(d);
		assertEquals(d, c.getDuration());	
    }
	
	@Test
	/**
	 * testConnection2
	 * <p>
	 * Test the setters and getters of the Connection class
	 */
    public void testConnection2() {
		Duration new_duration = DateHelper.INSTANCE.stringToDuration("00:40");
		
		Connection c = new Connection(null);
		c.setDuration(new_duration);
		
		assertEquals(new_duration, c.getDuration());	
    }
	
	@Test (expected = IllegalArgumentException.class)
	/**
	 * testDateHelper1
	 * <p>
	 * Test the DateHelper class
	 * <p>
	 * Invalid input (random text instead of time)
	 */
    public void testDateHelper1() {
		DateHelper.INSTANCE.stringToDuration("foo");
    }
	
	@Test (expected = IllegalArgumentException.class)
	/**
	 * testDateHelper2
	 * <p>
	 * Test the DateHelper class
	 * <p>
	 * Another invalid input (only HH:MM is currently allowed)
	 */
    public void testDateHelper2() {
		DateHelper.INSTANCE.stringToDuration("07:50:03");
    }
	
	@Test
	/**
	 * testDateHelper3
	 * <p>
	 * Test the DateHelper class
	 * <p>
	 * Negative values should be interpreted as positive values
	 */
    public void testDateHelper3() {
    	Duration d = DateHelper.INSTANCE.stringToDuration("-01:00");
    	assertEquals(1.0D, d.getStandardHours(), 0);
    }
	
	@Test
	/**
	 * testDateHelper4
	 * <p>
	 * Test the DateHelper class
	 * <p>
	 * Valid input > 24 hours
	 */
    public void testDateHelper4() {
		Duration d = DateHelper.INSTANCE.stringToDuration("25:00");
		assertEquals(25.0D, d.getStandardHours(), 0);
    }
	
	
}
