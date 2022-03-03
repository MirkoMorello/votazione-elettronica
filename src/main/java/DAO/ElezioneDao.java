package DAO;
import Model.*;
import Singleton.*;
import java.sql.SQLException;
import java.util.List;

public interface ElezioneDao {
	public List<Elezione> getElezioniAttive() throws SQLException;
	public boolean createElezione(String titolo, String descrizione, String tipologia, boolean maggioranza_assoluta, boolean liste, boolean quorum, Integer comunale) throws SQLException;
	public Elezione getElezione(String titolo) throws SQLException;
	public boolean closeElezione(String titolo) throws Exception;
	public boolean deleteElezione(String titolo) throws SQLException;
	public void pushCandidati(List<String> selected) throws SQLException;
	public void pushListe(List<String> selected) throws SQLException;
	public void pushListeCandidati(List<String> selected) throws Exception;
	public void pushReferendum() throws SQLException ;
	public String getVincitoreReferendum(String titolo) throws SQLException;
	public String getVincitoreCategorico(String titolo) throws SQLException;
	public String getVincitoreOrdinale(String titolo) throws SQLException;
	public String getVincitoreCatConPref(String titolo) throws SQLException;
	public List<Elezione> getElezioniAttiveUtente(String CF, String comune) throws Exception;
	public void setUserVoted(String titolo, String CF) throws Exception;
	public void incrementVoterCount(String titolo) throws Exception;
	public void voteReferendum(String value, String titolo) throws Exception;
	public void voteOrdinaleListe(List<String> opzioni, String titolo) throws Exception;
	public void voteOrdinaleCandidati(List<String> opzioni, String titolo) throws Exception;
	public void voteCategoricoListe(String opzione, String titolo) throws Exception;
	public void voteCategoricoCandidati(String opzione, String titolo) throws Exception;
	public List<Elezione> getElezioniVotate(String CF) throws Exception;
}
