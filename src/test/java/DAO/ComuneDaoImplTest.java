package DAO;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import Model.Comune;

public class ComuneDaoImplTest {

	@Test
	public void testGetComune() throws SQLException {
		ComuneDaoImpl dao = new ComuneDaoImpl();
		Comune c = new Comune("Inviskia", 54554);
		dao.createComune(c);
		assertNotNull(dao.getComune(dao.getComuneId("Inviskia")));
		dao.deleteComune(c);
	}

	@Test
	public void testCreateComune() throws SQLException {
		ComuneDaoImpl dao = new ComuneDaoImpl();
		Comune c = new Comune("Inviskia", 54554);
		assertTrue(dao.createComune(c));
		dao.deleteComune(c);
	}

	@Test
	public void testGetComuneId() throws SQLException {
		ComuneDaoImpl dao = new ComuneDaoImpl();
		Comune c = new Comune("Inviskia", 54554);
		dao.createComune(c);
		assertNotNull(dao.getComuneId("Inviskia"));
		dao.deleteComune(c);
	}

}
