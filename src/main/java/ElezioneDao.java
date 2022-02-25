import java.sql.SQLException;
import java.util.List;

public interface ElezioneDao {
	public List<Elezione> getElezioniAttive() throws SQLException;
	public boolean createElezione(String titolo, String descrizione, String tipologia, boolean maggioranza_assoluta, boolean liste, boolean quorum) throws SQLException;
	public boolean closeElezione(String titolo);
}
