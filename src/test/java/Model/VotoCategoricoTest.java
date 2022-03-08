package Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class VotoCategoricoTest {

	@Test
	public void testGetMaggAssoluta() {
		VotoCategorico v = new VotoCategorico("Titolo dell'elezione", "Questa è la descrizione", true, true);
		assertTrue(v.getMaggAssoluta());
	}

	@Test
	public void testGetTipo() {
		VotoCategorico v = new VotoCategorico("Titolo dell'elezione", "Questa è la descrizione", true, true);
		assertEquals("categorico", v.getTipo());
	}

	@Test
	public void testGetListe() {
		VotoCategorico v = new VotoCategorico("Titolo dell'elezione", "Questa è la descrizione", true, true);
		assertTrue(v.getListe());
	}

	@Test
	public void testVotoCategorico1() {
		VotoCategorico v = new VotoCategorico("Titolo dell'elezione", "Questa è la descrizione, test numero 1", true, true);
		assertNotNull(v);
	}
	
	@Test
	public void testVotoCategorico2() {
		VotoCategorico v = new VotoCategorico("Titolo", "Questa è la descrizione", true, false);
		assertNotNull(v);
	}
	
	@Test
	public void testVotoCategorico3() {
		VotoCategorico v = new VotoCategorico("Titolo", "Questa è la descrizione, test numero 3", false, true);
		assertNotNull(v);
	}
	
	@Test
	public void testVotoCategorico4() {
		VotoCategorico v = new VotoCategorico("Titolo", "Questa è la descrizione", false, false);
		assertNotNull(v);
	}

}
