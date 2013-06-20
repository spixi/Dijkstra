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
	

}
