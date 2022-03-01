package DAO;
import Model.*;
import Singleton.*;
import java.sql.SQLException;
import java.util.List;

public interface ElezioneDao {
	public List<Elezione> getElezioniAttive() throws SQLException;
	public boolean createElezione(String titolo, String descrizione, String tipologia, boolean maggioranza_assoluta, boolean liste, boolean quorum, Integer comunale) throws SQLException;
	public boolean closeElezione(String titolo);
	public boolean deleteElezione(String titolo) throws SQLException;
	public void pushCandidati(List<String> selected) throws SQLException;
	public void pushListe(List<String> selected) throws SQLException;
	public void pushListeCandidati(List<String> selected) throws Exception;
	public void pushReferendum() throws SQLException ;
}
