package test;

import org.junit.Test;

import helpers.MinimumAwareHashMap;
import static org.junit.Assert.*;

public class HelperTest {
	@Test
	public void testMinimumAwareHashMap() {
		MinimumAwareHashMap<String,Integer> map = new MinimumAwareHashMap<String,Integer>();
		
		map.put("Drei", 3);
		assertEquals("Drei",map.getKeyOfMinValue());
		assertEquals(Integer.valueOf(3),map.getMinValue());
		
		map.put("Vier", 4);
		assertEquals("Drei",map.getKeyOfMinValue());
		assertEquals(Integer.valueOf(3),map.getMinValue());
		
		map.put("Eins", 1);
		assertEquals("Eins",map.getKeyOfMinValue());
		assertEquals(Integer.valueOf(1),map.getMinValue());
		
		map.put("Zwei", 2);
		assertEquals("Eins",map.getKeyOfMinValue());
		assertEquals(Integer.valueOf(1),map.getMinValue());
		
		map.put("Eins", 5);
		assertEquals("Zwei",map.getKeyOfMinValue());
		assertEquals(Integer.valueOf(2),map.getMinValue());
		
		
	}
	

}
