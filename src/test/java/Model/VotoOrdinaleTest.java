package Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class VotoOrdinaleTest {

	@Test
	public void testGetTipo() {
		VotoOrdinale v = new VotoOrdinale("Questo è il titolo","Questa è la descrizione dell'elezione ordinale", false);
		assertEquals("ordinale",v.getTipo());
	}

	@Test
	public void testGetListe1() {
		VotoOrdinale v = new VotoOrdinale("Titolo","Questa è la descrizione", true);
		assertTrue(v.getListe());
	}
	
	@Test
	public void testGetListe2() {
		VotoOrdinale v = new VotoOrdinale("Titolo","Questa è la descrizione", false);
		assertFalse(v.getListe());
	}

	@Test
	public void testVotoOrdinale() {
		VotoOrdinale v = new VotoOrdinale("Questo è il titolo","Questa è la descrizione dell'elezione ordinale", false);
		assertNotNull(v);
	}

}
