package Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ListaTest {

	@Test
	public void testLista() {
		Lista l = new Lista("Nome lista", "Questa è la descrizione della lista");
		assertNotNull(l);
	}

	@Test
	public void testGetName() {
		Lista l = new Lista("Nome lista", "Questa è la descrizione della lista");
		assertEquals("Nome lista", l.getName());
	}

	@Test
	public void testGetDesc() {
		Lista l = new Lista("Nome lista", "Questa è la descrizione della lista");
		assertEquals("Questa è la descrizione della lista", l.getDesc());
	}

}
