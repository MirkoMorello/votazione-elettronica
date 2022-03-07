package test;
import static org.junit.Assert.*;
import org.junit.Test;
import Model.*;


public class ElettoreTest {
	
	@Test
	public void givenCFtest() {
		Elettore elettore = new Elettore(null, null, null, null, null, null, 0);   //Given
		
		int result = Elettore.sarcazzo();										// when
		
		
		/*
		assertEquals(expected, actual); 												//then
		assertTrue(condition);
		assertFalse(condition);
		assertNotEquals(expected, actual);
		assertNull(value);
		assertNotNull(value);
		*/
	}
	
}
