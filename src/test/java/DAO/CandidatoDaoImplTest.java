package DAO;
import Model.*;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

public class CandidatoDaoImplTest {

	@Test
	public void testGetCandidatesOfList() throws NoSuchAlgorithmException, Exception {
		CandidatoDaoImpl dao = new CandidatoDaoImpl();
		ListaDaoImpl dao2 = new ListaDaoImpl();
		Lista l = new Lista("titolo della lista", "questa è la descrizione della lista");
		dao2.addList(l);
		Candidato c = new Candidato("Mario", "Draghi", LocalDate.parse("1956-08-12"), "m", l);
		dao.addCandidate(c);
		List<Candidato> candidates = dao.getCandidatesOfList(l);
		assertTrue(candidates.get(0).equals(c));
		dao2.deleteList(l);
		dao.deleteCandidate(c);
	}

	@Test
	public void testDeleteCandidate() throws NoSuchAlgorithmException, Exception {
		CandidatoDaoImpl dao = new CandidatoDaoImpl();
		ListaDaoImpl dao2 = new ListaDaoImpl();
		Lista l = new Lista("titolo della lista", "questa è la descrizione della lista");
		dao2.addList(l);
		Candidato c = new Candidato("Mario", "Draghi", LocalDate.parse("1956-08-12"), "m", l);
		dao.addCandidate(c);
		assertTrue(dao.deleteCandidate(c));
		dao2.deleteList(l);
		dao.deleteCandidate(c);
	}

	@Test
	public void testAddCandidate() throws NoSuchAlgorithmException, Exception {
		CandidatoDaoImpl dao = new CandidatoDaoImpl();
		ListaDaoImpl dao2 = new ListaDaoImpl();
		Lista l = new Lista("titolo della lista", "questa è la descrizione della lista");
		dao2.addList(l);
		Candidato c = new Candidato("Mario", "Draghi", LocalDate.parse("1956-08-12"), "m", l);
		assertTrue(dao.addCandidate(c));
		dao2.deleteList(l);
		dao.deleteCandidate(c);
	}

	@Test
	public void testGetAllCandidates() throws Exception {
		CandidatoDaoImpl dao = new CandidatoDaoImpl();
		List<Candidato> candidates = dao.getAllCandidates();
		assertNotNull(candidates);
	}

	@Test
	public void testGetCandidatoID() throws NoSuchAlgorithmException, Exception {
		CandidatoDaoImpl dao = new CandidatoDaoImpl();
		ListaDaoImpl dao2 = new ListaDaoImpl();
		Lista l = new Lista("titolo della lista", "questa è la descrizione della lista");
		dao2.addList(l);
		Candidato c = new Candidato("Mario", "Draghi", LocalDate.parse("1956-08-12"), "m", l);
		dao.addCandidate(c);
		assertNotNull(dao.getCandidatoID("Mario", "Draghi"));
		dao2.deleteList(l);
		dao.deleteCandidate(c);
	}

	@Test
	public void testGetPartecipatingCandidates() throws Exception {
		CandidatoDaoImpl dao = new CandidatoDaoImpl();
		ListaDaoImpl dao2 = new ListaDaoImpl();
		Lista l = new Lista("titolo della lista", "questa è la descrizione della lista");
		dao2.addList(l);
		Candidato c1 = new Candidato("Mario", "Draghi", LocalDate.parse("1956-08-12"), "m", l);
		dao.addCandidate(c1);
		assertNotNull(dao.getPartecipatingCandidates(l.getName()));
		System.out.print(l.getName());
		assertTrue(dao.getPartecipatingCandidates(l.getName()).get(0) instanceof Candidato);
		assertTrue(dao.getPartecipatingCandidates(l.getName()).get(0).equals(c1));
		dao.deleteCandidate(c1);
		dao2.deleteList(l);
	}
	
/*
	@Test
	public void testGetCandidateList() throws Exception {
		CandidatoDaoImpl dao = new CandidatoDaoImpl();
		ListaDaoImpl dao2 = new ListaDaoImpl();
		Lista l = new Lista("titolo della lista", "questa è la descrizione della lista");
		dao2.addList(l);
		Candidato c = new Candidato("Mario", "Draghi", LocalDate.parse("1956-08-12"), "m", l);
		dao.addCandidate(c);
		String listName = dao.getCandidateList("Mario", "Draghi");
		System.out.print(listName);
		assertTrue(dao2.getList(listName).equals(l));
		dao2.deleteList(l);
		dao.deleteCandidate(c);
	}
*/

}
