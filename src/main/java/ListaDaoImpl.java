import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListaDaoImpl implements ListaDao{
private Connection c;
	
	public void connect() {
	      try {
	         this.c = DriverManager
	            .getConnection("jdbc:sqlite:votazioneelettronica.db");
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	      System.out.println("Opened database successfully");
	}
	
	@Override
	public List<Lista> getAllLists() throws Exception {
		
		List<Lista> lists = new ArrayList<Lista>();
		
		if( c == null) {
			this.connect();
		}
		String command = "SELECT * FROM \"lista\";";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		ResultSet rs = updatedCmd.executeQuery();
		while(rs.next()) {
			lists.add(new Lista(rs.getString("nome"), rs.getString("descrizione")));
		}
		
		return lists;
	}

	@Override
	public Lista getList(String name) throws Exception {
		Lista list = null;
		
		if( c == null) {
			this.connect();
		}
		
		try {
			String command = "SELECT * FROM \"lista\" where lista.nome = ?;";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, String.valueOf(name));
			ResultSet rs = updatedCmd.executeQuery();
			if(rs.next() == false) {
				return null;
			}
			int id = rs.getInt("id");
			String nome = rs.getString("nome");
            String desc = rs.getString("descrizione");
            
            list = new Lista(nome, desc);
            
            return list;
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean deleteList(String name) throws Exception {
		if( c == null) {
			this.connect();
		}
				
		try {
			String command = "DELETE from \"lista\" where nome = ?;";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, name);
			updatedCmd.executeUpdate();
			
			return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addList(String name, String desc) throws NoSuchAlgorithmException, Exception {
		if( c == null) {
			this.connect();
		}
		
		Lista check = getList(name);
		if(check != null) {
			return false;
		}
		
		try {
			String command = "INSERT INTO \"lista\" (nome, descrizione) VALUES ( ?, ?);";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, name);
			updatedCmd.setString(2, desc);
			updatedCmd.executeUpdate();
			
			return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

}
