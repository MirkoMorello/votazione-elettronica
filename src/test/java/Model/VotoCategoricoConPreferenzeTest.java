package Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class VotoCategoricoConPreferenzeTest {

	@Test
	public void testIsComunale() {
		VotoCategoricoConPreferenze v = new VotoCategoricoConPreferenze("Titolo dell'elezione Comunale", "Questa è la descrizione dell'elezione comunale", true, true);
		assertTrue(v.isComunale());
	}

	@Test
	public void testGetTipo() {
		VotoCategoricoConPreferenze v = new VotoCategoricoConPreferenze("Titolo dell'elezione", "Questa è la descrizione", true, true);
		assertEquals("categorico con preferenze", v.getTipo());
	}

	@Test
	public void testGetListe() {
		VotoCategoricoConPreferenze v = new VotoCategoricoConPreferenze("Titolo dell'elezione Comunale", "Questa è la descrizione dell'elezione comunale", true, true);
		assertTrue(v.getListe());
	}

	@Test
	public void testVotoCategoricoConPreferenze1() {
		VotoCategoricoConPreferenze v = new VotoCategoricoConPreferenze("Titolo dell'elezione", "Questa è la descrizione", true, true);
		assertNotNull(v);
	}
	
	@Test
	public void testVotoCategoricoConPreferenze2() {
		VotoCategoricoConPreferenze v = new VotoCategoricoConPreferenze("Titolo dell'elezione", "Questa è la descrizione", false, true);
		assertNotNull(v);
	}
	
	@Test
	public void testVotoCategoricoConPreferenze3() {
		VotoCategoricoConPreferenze v = new VotoCategoricoConPreferenze("Titolo dell'elezione", "Questa è la descrizione", true, false);
		assertNotNull(v);
	}
	
	@Test
	public void testVotoCategoricoConPreferenze4() {
		VotoCategoricoConPreferenze v = new VotoCategoricoConPreferenze("Titolo dell'elezione", "Questa è la descrizione", false, false);
		assertNotNull(v);
	}

}
