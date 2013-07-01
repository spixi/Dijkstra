/**
 * FileTest
 * <p>
 * Test file reading classes
 * 
 * @author Marius Spix
 */
package de.bwv_aachen.dijkstra.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import de.bwv_aachen.dijkstra.helpers.DateHelper;
import de.bwv_aachen.dijkstra.model.Airport;
import de.bwv_aachen.dijkstra.model.ImplListDataModel;
import de.bwv_aachen.dijkstra.model.ImplListDataModelFactory;

public class FileTest {

    private static ImplListDataModel      model;
    private static HashMap<Long, Airport> airportList;
    private static Airport                airport;

    @BeforeClass
    public static void setUpBeforeClass() throws Throwable {
        model = ImplListDataModelFactory.INSTANCE.factory(new File(
                "test/testconnection.json"));
        airportList = model.getAirportList();
        airport = airportList.get(1L);
    }

    @Test
    /**
     * fileTest1
     * <p>
     * Test whether the name is correctly read
     */
    public void fileTest1() throws Throwable {
        assertEquals("Frankfurt", airport.getName());
    }

    @Test
    /**
     * fileTest2
     * <p>
     * Assure a destination is added
     */
    public void fileTest2() throws Throwable {
        Airport dest_airport = airportList.get(4L);
        assertTrue(dest_airport.getConnections().containsKey(airport));
    }

    @Test
    /**
     * fileTest3
     * <p>
     * Assure that no wrong destination is added
     */
    public void fileTest3() throws Throwable {
        Airport no_dest_airport = airportList.get(2L);
        assertFalse(no_dest_airport.getConnections().containsKey(airport));
    }

    @Test
    /**
     * fileTest4
     * <p>
     * Test whether the duration is correctly read
     */
    public void fileTest4() throws Throwable {

        Airport dest_airport = airportList.get(4L);
        assertEquals(DateHelper.INSTANCE.stringToDuration("01:24"), airport
                .getConnections().get(dest_airport).getDuration());
    }

    @Test
    /**
     * JSONWriteTest1
     * <p>
     * Test the JSONWriter for the model class
     */
    public void JSONWriteTest1() throws IOException {
        StringWriter sw = new StringWriter();
        model.writeJSONString(sw);
        System.out.println(sw);

        // TODO: add an actual test here
        // TODO 2: check what happens when you put an " (double-quote) or an \
        // (backslash) in a Airport's name)
    }

}
