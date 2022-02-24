import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CandidatoDaoImpl implements CandidatoDao{
	
	private Connection c = ConnectionSingleton.getIstance().getConnection();

	@Override
	public List<Candidato> getCandidatesOfList(String listname) throws Exception {
		
		int listId = DaoFactorySingleton.getDaoFactory().getListaDao().getListID(listname);
		List<Candidato> candList = new ArrayList<Candidato>();
		String command = "SELECT * FROM candidato where candidato.listaId = ?;";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setInt(1, listId);
		ResultSet rs = updatedCmd.executeQuery();
		while(rs.next()) {
			String nome = rs.getString("nome");
			String cognome = rs.getString("cognome");
			String nascita = rs.getString("nascita");
            LocalDate nascitald = LocalDate.parse(nascita);
            candList.add(new Candidato(nome, cognome, nascitald));
		}
		return candList;
		
	}

	@Override
	public boolean deleteCandidate(String name, String surname, String listname) throws Exception {
		
		int listId = DaoFactorySingleton.getDaoFactory().getListaDao().getListID(listname);
		
		try {
			String command = "DELETE FROM candidato where nome = ? AND cognome =  ? AND listaId = ?";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, name);
			updatedCmd.setString(2, surname);
			updatedCmd.setInt(3, listId);
			updatedCmd.executeUpdate();
		}catch (SQLException ex) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean addCandidate(String name, String surname, LocalDate nascita, String listname) throws SQLException {
		int listId = DaoFactorySingleton.getDaoFactory().getListaDao().getListID(listname);
		
		try {
			String command = "INSERT INTO candidato (nome, cognome, nascita, listaId) values (?, ?, ?, ?)";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, name);
			updatedCmd.setString(2, surname);
			updatedCmd.setString(3, nascita.toString());
			updatedCmd.setInt(4, listId);
			System.out.println(updatedCmd.toString());
			updatedCmd.executeUpdate();
			
		}catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
		
		return true;
	}

}
