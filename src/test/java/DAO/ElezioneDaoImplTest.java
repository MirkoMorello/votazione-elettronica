package DAO;
import Model.*;
import Singleton.CreatingElezioneSingleton;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ElezioneDaoImplTest {

	@Test
	public void testGetElezioniAttive() throws SQLException {
		ElezioneDaoImpl dao = new ElezioneDaoImpl();
		assertNotNull(dao.getElezioniAttive());
		assertTrue(dao.getElezioniAttive() instanceof List<?>);
	}
	
	@Test
	public void testCreateElezione() throws SQLException {
		ElezioneDaoImpl dao = new ElezioneDaoImpl();
		VotoCategoricoConPreferenze elezione = new VotoCategoricoConPreferenze("Questo è il titolo", "Questa è la descrizione dell'elezione", false, false);
		assertTrue(dao.createElezione(elezione));
		dao.deleteElezione(elezione.getTitolo());
	}

	@Test
	public void testCloseElezione() throws Exception {
		ElezioneDaoImpl dao = new ElezioneDaoImpl();
		VotoCategoricoConPreferenze elezione = new VotoCategoricoConPreferenze("Questo è il titolo", "Questa è la descrizione dell'elezione", false, false);
		dao.createElezione(elezione);
		assertTrue(dao.closeElezione(elezione.getTitolo()));
		dao.deleteElezione(elezione.getTitolo());
	}

	@Test
	public void testDeleteElezione() throws SQLException {
		ElezioneDaoImpl dao = new ElezioneDaoImpl();
		VotoCategoricoConPreferenze elezione = new VotoCategoricoConPreferenze("Questo è il titolo", "Questa è la descrizione dell'elezione", false, false);
		dao.createElezione(elezione);
		assertTrue(dao.deleteElezione(elezione.getTitolo()));
	}

	@Test
	public void testPushListe() throws Exception {
		ElezioneDaoImpl dao1 = new ElezioneDaoImpl();
		ListaDaoImpl dao2 = new ListaDaoImpl();
		Lista l = new Lista("Titolo della lista", "Questa è la descrizione della lista");
		VotoCategoricoConPreferenze elezione = new VotoCategoricoConPreferenze("Questo è il titolo", "Questa è la descrizione dell'elezione", false, false);
		CreatingElezioneSingleton.getIstance().setTitolo(elezione.getTitolo());
		dao1.createElezione(elezione);
		dao2.addList(l);
		List<String> lists = new ArrayList<String>();
		lists.add(l.getName());
		assertTrue(dao1.pushListe(lists));
		dao1.deleteElezione(elezione.getTitolo());
		dao1.deleteElezioneLista(l);
		dao2.destroyList(l);
	}

	@Test
	public void testPushCandidati() throws NoSuchAlgorithmException, Exception {
		ElezioneDaoImpl dao1 = new ElezioneDaoImpl();
		CandidatoDaoImpl dao2 = new CandidatoDaoImpl();
		ListaDaoImpl dao3 = new ListaDaoImpl();
		Lista l = new Lista("Titolo della lista", "Questa è la descrizione della lista");
		Candidato c = new Candidato("Mario", "Draghi", LocalDate.parse("1956-08-12"), "m", l);
		VotoCategoricoConPreferenze elezione = new VotoCategoricoConPreferenze("Questo è il titolo", "Questa è la descrizione dell'elezione", false, false);
		CreatingElezioneSingleton.getIstance().setTitolo(elezione.getTitolo());
		dao1.createElezione(elezione);
		dao3.addList(l);
		dao2.addCandidate(c);
		List<String> lists = new ArrayList<String>();
		lists.add(c.toString());
		assertTrue(dao1.pushCandidati(lists));
		dao1.deleteElezione(elezione.getTitolo());
		dao1.deleteElezioneLista(l);
		dao2.deleteCandidate(c);
		dao3.destroyList(l);
	}

	@Test
	public void testGetElezioneId() throws SQLException {
		ElezioneDaoImpl dao = new ElezioneDaoImpl();
		VotoCategoricoConPreferenze elezione = new VotoCategoricoConPreferenze("Questo è il titolo", "Questa è la descrizione dell'elezione", false, false);
		dao.createElezione(elezione);
		assertNotNull(dao.getElezioneId(elezione.getTitolo()));
		dao.deleteElezione(elezione.getTitolo());
	}

	@Test
	public void testPushReferendum() throws SQLException {
		ElezioneDaoImpl dao = new ElezioneDaoImpl();
		Referendum elezione = new Referendum("Questo è il titolo", "Questa è la descrizione dell'elezione", false);
		CreatingElezioneSingleton.getIstance().setTitolo(elezione.getTitolo());
		CreatingElezioneSingleton.getIstance().setDesc(elezione.getDescrizione());
		CreatingElezioneSingleton.getIstance().setSelezione("con quorum");
		CreatingElezioneSingleton.getIstance().setTipo("referendum");
		CreatingElezioneSingleton.getIstance().pushReferendum();
		assertNotNull(dao.getElezioneId(elezione.getTitolo()));
		dao.deleteElezione(elezione.getTitolo());
	}

	@Test
	public void testGetVincitoreReferendum() throws SQLException {
		ElezioneDaoImpl dao = new ElezioneDaoImpl();
		Referendum elezione = new Referendum("Questo è il titolo", "Questa è la descrizione dell'elezione", false);
		CreatingElezioneSingleton.getIstance().setTitolo(elezione.getTitolo());
		CreatingElezioneSingleton.getIstance().setDesc(elezione.getDescrizione());
		CreatingElezioneSingleton.getIstance().setSelezione("senza quorum");
		CreatingElezioneSingleton.getIstance().setTipo("referendum");
		CreatingElezioneSingleton.getIstance().pushReferendum();
		dao.pushReferendum();
		assertNotNull(dao.getVincitoreReferendum(elezione.getTitolo()));
		dao.deleteElezione(elezione.getTitolo());
	}
	
	@Test
	public void testVoteReferendum() throws Exception {
		ElezioneDaoImpl dao = new ElezioneDaoImpl();
		Referendum elezione = new Referendum("Questo è il titolo del referendum", "Questa è la descrizione dell'elezione", false);
		CreatingElezioneSingleton.getIstance().setTitolo(elezione.getTitolo());
		CreatingElezioneSingleton.getIstance().setDesc(elezione.getDescrizione());
		CreatingElezioneSingleton.getIstance().setSelezione("senza quorum");
		CreatingElezioneSingleton.getIstance().setTipo("referendum");
		CreatingElezioneSingleton.getIstance().pushReferendum();
		dao.createElezione(elezione);
		dao.pushReferendum();
		assertTrue(dao.voteReferendum("Questo è il titolo del referendum", "si"));
		//dao.deleteElezione(elezione.getTitolo());
	}

	@Test
	public void testGetVincitoreCategorico() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVincitoreOrdinale() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVincitoreCatConPref() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetElezioniAttiveUtente() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetElezione() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetUserVoted() {
		fail("Not yet implemented");
	}

	@Test
	public void testIncrementVoterCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testVoteOrdinaleListe() {
		fail("Not yet implemented");
	}

	@Test
	public void testVoteOrdinaleCandidati() {
		fail("Not yet implemented");
	}

	@Test
	public void testVoteCategoricoListe() {
		fail("Not yet implemented");
	}

	@Test
	public void testVoteCategoricoCandidati() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetElezioniVotate() {
		fail("Not yet implemented");
	}

	@Test
	public void testVoteCategoricoPref() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetElezioniTerminate() {
		fail("Not yet implemented");
	}

}
