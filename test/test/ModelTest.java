package test;

import static org.junit.Assert.*;

import helpers.DateHelper;

import model.Airport;
import model.Connection;

import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;

public class ModelTest {
	
	Integer id;
	String  name;
	Airport a;

	@Before
	public void setUp() throws Exception {
		id = 1;
		name = "Frankfurt";
		a = new Airport(id,name);
	}

	@Test
	//Test the constructor of the class Airport
	public void testAirport1() {
		assertEquals(id, a.getId());
		assertEquals(name, a.getName());
	}
	
	@Test
	//Test the setters of the class Airport
	public void testAirport2() {
		Integer new_id   = 2;
		String  new_name = "London";
		
		a.setId(new_id);
		a.setName(new_name);
		
		assertEquals(new_id, a.getId());
		assertEquals(new_name, a.getName());
	}
	
	@Test
	//Test the constructor of the class Connection
    public void testConnection1() {
		Duration d = DateHelper.INSTANCE.stringToDuration("00:35");
		
		Connection c = new Connection(a,d);
		
		assertEquals(a, c.getDestination());
		assertEquals(d, c.getDuration());	
    }
	
	@Test
	//Test the setters and getters of the class Connection
    public void testConnection2() {
		Airport  new_airport  = new Airport(2,"Berlin");
		Duration new_duration = DateHelper.INSTANCE.stringToDuration("00:40");
		
		Connection c = new Connection(null,null);
		
		c.setDestination(new_airport);
		c.setDuration(new_duration);
		
		assertEquals(new_airport, c.getDestination());
		assertEquals(new_duration, c.getDuration());	
    }
	
	@Test (expected = IllegalArgumentException.class)
	//Test the DateHelper class
	//Invalid input
    public void testDateHelper1() {
		DateHelper.INSTANCE.stringToDuration("foo");
    }
	
	@Test (expected = IllegalArgumentException.class)
	//Test the DateHelper class
	//Another valid input (only HH:MM is currently allowed)
    public void testDateHelper2() {
		DateHelper.INSTANCE.stringToDuration("07:50:03");
    }
	
	@Test
	//Test the DateHelper class
	//Negative values should be interpreted as positive values
    public void testDateHelper3() {
    	Duration d = DateHelper.INSTANCE.stringToDuration("-01:00");
    	assertEquals(1.0D, d.getStandardHours(), 0);
    }
	
	@Test
	//Test the DateHelper class
	//Valid input > 24 hours
    public void testDateHelper4() {
		Duration d = DateHelper.INSTANCE.stringToDuration("25:00");
		assertEquals(25.0D, d.getStandardHours(), 0);
    }
	
	
}
