package Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReferendumTest {

	@Test
	public void testReferendum() {
		Referendum r = new Referendum("Titolo", "Questa è la descrizione", true);
		assertNotNull(r);
	}

	@Test
	public void testGetQuorum1() {
		Referendum r = new Referendum("Titolo", "Questa è la descrizione", true);
		assertTrue(r.getQuorum());
	}

	@Test
	public void testGetQuorum2() {
		Referendum r = new Referendum("Titolo", "Questa è la descrizione", false);
		assertFalse(r.getQuorum());
	}
	
	@Test
	public void testGetTipo() {
		Referendum r = new Referendum("Titolo", "Questa è la descrizione", false);
		assertEquals("referendum", r.getTipo());
	}

	@Test
	public void testGetListe() {
		Referendum r = new Referendum("Titolo", "Questa è la descrizione", true);
		assertFalse(r.getListe());
	}

}
