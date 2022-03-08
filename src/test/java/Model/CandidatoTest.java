package Model;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class CandidatoTest {

	@Test
	public void testCandidato() {
		Lista l = new Lista("Nome della lista", "Questa è la descrizione della lista");
		Candidato c = new Candidato("Giovanni", "Malapegno", LocalDate.parse("1997-03-07"), "m", l);
		assertNotNull(c);
	}

	@Test
	public void testGetNome() {
		Lista l = new Lista("Nome della lista", "Questa è la descrizione della lista");
		Candidato c = new Candidato("Mario", "Draghi", LocalDate.parse("1956-08-12"), "m", l);
		assertEquals("Mario", c.getNome());
	}

	@Test
	public void testGetCognome() {
		Lista l = new Lista("Nome della lista", "Questa è la descrizione");
		Candidato c = new Candidato("Eleonora", "Mariandola", LocalDate.parse("1997-03-12"), "f", l);
		assertEquals("Mariandola", c.getCognome());
	}

	@Test
	public void testGetNascita() {
		Lista l = new Lista("Nome della lista", "Questa è la descrizione");
		Candidato c = new Candidato("Elisabetta", "Vendozzi", LocalDate.parse("1960-10-23"), "f", l);
		assertEquals(LocalDate.parse("1960-10-23"), c.getNascita());
	}

	@Test
	public void testGetSesso() {
		Lista l = new Lista("Nome della lista", "Questa è la descrizione");
		Candidato c = new Candidato("Giulia", "Rescaldina", LocalDate.parse("1980-12-25"), "f", l);
		assertEquals("f", c.getSesso());
	}

	@Test
	public void testGetList() {
		Lista l = new Lista("Nome della lista", "Questa è la descrizione");
		Candidato c = new Candidato("Cristiano", "Millani", LocalDate.parse("2000-03-23"), "m", l);
		assertEquals(l, c.getList());
	}

	@Test
	public void testToString() {
		Lista l = new Lista("Nome della lista", "Questa è la descrizione");
		Candidato c = new Candidato("Davide", "Tosi", LocalDate.parse("2000-03-23"), "m", l);
		assertEquals("Davide Tosi", c.toString());
	}

}
