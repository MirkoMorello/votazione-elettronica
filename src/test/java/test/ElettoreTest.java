package test;
import static org.junit.Assert.*;
import org.junit.Test;
import Model.*;


public class ElettoreTest {
	
	@Test
	public void givenCFtest() {
		Elettore elettore = new Elettore('mrlmrk', null, null, null, null, null, 0);   //Given
		
		String result = elettore.getCF();			// when
		
		
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
