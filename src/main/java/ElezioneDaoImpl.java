import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ElezioneDaoImpl implements ElezioneDao{
	
	private Connection c = ConnectionSingleton.getIstance().getConnection();

	@Override
	public List<Elezione> getElezioniAttive() throws SQLException {
		List<Elezione> elezioni = new ArrayList<Elezione>();
		String command = "SELECT * FROM \"elezione\" where closed != 1;";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		ResultSet rs = updatedCmd.executeQuery();
		switch(rs.getString("tipologia")){
		case "voto ordinale":
			break;
		case "voto categorico":
			break;
		case "voto categorico con preferenze":
			break;
		case "referendum":
			break;
		default:
			break;
		}
		
		return elezioni;
	}

	@Override
	public boolean createElezione(String titolo, String descrizione, String tipologia, boolean maggioranza_assoluta,
			boolean liste, boolean quorum) throws SQLException {
		int magg = 0;
		int list = 0;
		int quo = 0;
		if(maggioranza_assoluta)
			magg = 1;
		if(liste)
			list = 1;
		if(quorum)
			quo = 0;
		String command = "INSERT INTO elezione (titolo, descrizione, tipologia, maggioranza_assoluta, liste, voters_count, closed) values (?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, titolo);
		updatedCmd.setString(2, descrizione);
		updatedCmd.setString(3, tipologia);
		updatedCmd.setInt(4, magg);
		updatedCmd.setInt(5, list);
		updatedCmd.setInt(6, 0);
		updatedCmd.setInt(7, 0);
		
		try{
			updatedCmd.executeUpdate();
			return true;
		}catch(SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean closeElezione(String titolo) {
		// TODO Auto-generated method stub
		return false;
	}

}
