package Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComuneTest {

	@Test
	public void testComune() {
		Comune c = new Comune("Saronno", 20000);
		assertNotNull(c);
	}

	@Test
	public void testGetNome() {
		Comune c = new Comune("Milano", 205430);
		assertEquals(c.getNome(),"Milano");
	}

	@Test
	public void testGetPopolazione() {
		Comune c = new Comune("Torino", 205430);
		assertEquals(c.getPopolazione(),205430);
	}

}
