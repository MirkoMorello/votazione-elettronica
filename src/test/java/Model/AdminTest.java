package Model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class AdminTest {


	@Test
	public void testAdmin() {
		Admin a = new Admin("gabe", true);
		assertNotNull(a);
	}

	@Test
	public void testGetUsername() {
		Admin a = new Admin("giyo", true);
		assertNotNull(a);
	}

	@Test
	public void testIsSuperUser1() {
		Admin a = new Admin("fret", false);
		assertFalse(a.isSuperUser());
	}
	
	@Test
	public void testIsSuperUser2() {
		Admin a = new Admin("ghemon", true);
		assertFalse(a.isSuperUser());
	}

}
