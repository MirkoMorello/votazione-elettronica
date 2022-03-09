package DAO;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import Model.Elettore;

public class ElettoreDaoImplTest {

	@Test
	public void testGetAllElettori() throws Exception {
		ElettoreDaoImpl dao = new ElettoreDaoImpl();
		assertNotNull(dao.getAllElettori());
		assertTrue(dao.getAllElettori().get(0) instanceof Elettore);
	}

	@Test
	public void testGetElettore() throws Exception {
		ElettoreDaoImpl dao = new ElettoreDaoImpl();
		Elettore e = new Elettore("MRTNNA97C07F205Z", "Anna", "Mariotti", LocalDate.parse("1997-03-07"), "Milano", "Italia", 'f');
		dao.addElettore(e, "password");
		assertTrue(dao.getElettore(e.getCF()).getCF().equals(e.getCF()));
		dao.deleteElettore("MRTNNA97C07F205Z");
	}

	@Test
	public void testDeleteElettore() throws Exception {
		ElettoreDaoImpl dao = new ElettoreDaoImpl();
		Elettore e = new Elettore("MRTNNA97C07F205Z", "Anna", "Mariotti", LocalDate.parse("1997-03-07"), "Milano", "Italia", 'f');
		dao.addElettore(e, "password");
		assertTrue(dao.deleteElettore("MRTNNA97C07F205F"));
	}

	@Test
	public void testAddElettore() throws Exception {
		ElettoreDaoImpl dao = new ElettoreDaoImpl();
		Elettore e = new Elettore("MRTNNA97C07F205F", "Anna", "Mariotti", LocalDate.parse("1997-03-07"), "Milano", "Italia", 'f');
		assertTrue(dao.addElettore(e, "password"));
		dao.deleteElettore("MRTNNA97C07F205Z");
	}

	@Test
	public void testLoginElettore() throws Exception {
		ElettoreDaoImpl dao = new ElettoreDaoImpl();
		Elettore e = new Elettore("MRTNNA97C07F205Z", "Anna", "Mariotti", LocalDate.parse("1997-03-07"), "Milano", "Italia", 'f');
		dao.addElettore(e, "password");
		dao.register("MRTNNA97C07F205Z", "password");
		Elettore logged = dao.loginElettore("MRTNNA97C07F205Z", "password");
		System.out.print(e.getCF()+" "+logged.getCF());
		assertTrue(logged.getCF().equals(e.getCF()));
		dao.deleteElettore("MRTNNA97C07F205Z");
	}

	@Test
	public void testCheckRegistered1() throws Exception {
		ElettoreDaoImpl dao = new ElettoreDaoImpl();
		Elettore e = new Elettore("MRTNNA97C07F205Z", "Anna", "Mariotti", LocalDate.parse("1997-03-07"), "Milano", "Italia", 'f');
		dao.addElettore(e, "password");
		dao.register("MRTNNA97C07F205Z", "password");
		assertTrue(dao.checkRegistered("MRTNNA97C07F205Z"));
		dao.deleteElettore("MRTNNA97C07F205Z");
	}
	
	@Test
	public void testCheckRegistered2() throws Exception {
		ElettoreDaoImpl dao = new ElettoreDaoImpl();
		Elettore e = new Elettore("MRTNNA97C07F205Z", "Anna", "Mariotti", LocalDate.parse("1997-03-07"), "Milano", "Italia", 'f');
		dao.addElettore(e, "password");
		assertFalse(dao.checkRegistered("MRTNNA97C07F205Z"));
		dao.deleteElettore("MRTNNA97C07F205Z");
	}

	@Test
	public void testRegister() throws Exception {
		ElettoreDaoImpl dao = new ElettoreDaoImpl();
		Elettore e = new Elettore("MRTNNA97C07F205Z", "Anna", "Mariotti", LocalDate.parse("1997-03-07"), "Milano", "Italia", 'f');
		dao.addElettore(e, "password");
		assertTrue(dao.register("MRTNNA97C07F205Z", "password"));
		dao.deleteElettore("MRTNNA97C07F205Z");
	}

	@Test
	public void testUpdateElettore1() throws Exception {
		ElettoreDaoImpl dao = new ElettoreDaoImpl();
		Elettore e1 = new Elettore("MRTNNA97C07F205Z", "Anna", "Mariotti", LocalDate.parse("1997-03-07"), "Milano", "Italia", 'f');
		Elettore e2 = new Elettore("MRTNNA97C07F205Z", "Anna", "Mariotti", LocalDate.parse("1997-03-07"), "Milano", "Italia", 'm');
		dao.addElettore(e1, "password");
		assertTrue(dao.updateElettore(e2));
		assertTrue(dao.getElettore("MRTNNA97C07F205Z").getSesso().equals("m"));
		dao.deleteElettore("MRTNNA97C07F205Z");
	}
	
	@Test
	public void testUpdateElettore2() throws Exception {
		ElettoreDaoImpl dao = new ElettoreDaoImpl();
		Elettore e1 = new Elettore("MRTNNA97C07F205Z", "Anna", "Mariotti", LocalDate.parse("1997-03-07"), "Milano", "Italia", 'f');
		Elettore e2 = new Elettore("MRTNNA97C07F205Z", "Anna", "Mariotti", LocalDate.parse("1997-03-07"), "Milano", "Italia", 'f');
		dao.addElettore(e1, "password");
		assertTrue(dao.updateElettore(e2));
		assertFalse(dao.getElettore("MRTNNA97C07F205Z").getSesso().equals("m"));
		dao.deleteElettore("MRTNNA97C07F205Z");
	}

}
