package DAO;
import Model.*;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.Test;

public class ListaDaoImplTest {

	@Test
	public void testGetAllLists() throws Exception {
		ListaDaoImpl dao = new ListaDaoImpl();
		List<Lista> lists;
		lists = dao.getAllLists();
		assertNotNull(lists);
	}

	@Test
	public void testGetList() throws Exception {
		ListaDaoImpl dao = new ListaDaoImpl();
		Lista l = new Lista("Titolo della lista", "Questa è la descrizione della lista");
		dao.addList(l);
		assertTrue(l.equals(dao.getList("Titolo della lista")));
		dao.destroyList(l);
	}

	@Test
	public void testDeleteList() throws Exception {
		ListaDaoImpl dao = new ListaDaoImpl();
		Lista l = new Lista("Titolo della lista", "Questa è la descrizione della lista");
		dao.addList(l);
		assertTrue(dao.deleteList(l));
		dao.destroyList(l);
	}

	@Test
	public void testAddList() throws NoSuchAlgorithmException, Exception {
		ListaDaoImpl dao = new ListaDaoImpl();
		Lista l = new Lista("Titolo della lista", "Questa è la descrizione della lista");
		assertTrue(dao.addList(l));
		dao.destroyList(l);
	}

	@Test
	public void testGetListID() throws NoSuchAlgorithmException, Exception {
		ListaDaoImpl dao = new ListaDaoImpl();
		Lista l = new Lista("Titolo della lista", "Questa è la descrizione della lista");
		dao.addList(l);
		assertNotNull(dao.getListID("Titolo della lista"));
		dao.destroyList(l);
	}

}
