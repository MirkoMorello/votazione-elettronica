package DAO;
import Model.*;
import Singleton.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/*
 * Questa classe è parte della componente Model del design pattern MVC
 * Questa è l'implementazione dell'interfaccia ElettoreDao
 * Questa è una classe DAO
 */


public class ElettoreDaoImpl implements ElettoreDao{
	
	private Connection c = ConnectionSingleton.getIstance().getConnection();
	
	@SuppressWarnings("deprecation")
	public List<Elettore> getAllElettori() throws Exception {
		Statement stmt = null;
		List <Elettore> elettori = new ArrayList<Elettore>();
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM elettore;");
			
			while ( rs.next() ) {
	            String CF = rs.getString("CF");
	            String nome = rs.getString("nome");
	            String cognome = rs.getString("cognome");
	            String nascita = rs.getString("nascita");
	            LocalDate nascitald = LocalDate.parse(nascita);
	            String comune = rs.getString("comune");
	            String sesso = rs.getString("sesso");
	            char sessoChar = sesso.charAt(0);
	            String nazione = rs.getString("nazione").toUpperCase();
	            Elettore e = new Elettore( CF, nome, cognome, nascitald, comune, nazione, sessoChar);
	            
	            elettori.add(e);
	        }
			return elettori;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return elettori;
	}

	public Elettore getElettore(String CF) throws Exception {
		
		Elettore e = null;
		try {
			String command = "SELECT * FROM elettore WHERE elettore.\"CF\" = ?;";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, String.valueOf(CF));
			ResultSet rs = updatedCmd.executeQuery();
			if(rs.next() == false) {
				return null;
			}
			String Code = rs.getString("CF");
            String nome = rs.getString("nome");
            String cognome = rs.getString("cognome");
            String nascita = rs.getString("nascita");
            LocalDate nascitald = LocalDate.parse(nascita);
            String comune = rs.getString("comune");
            String sesso = rs.getString("sesso");
            char sessoChar = sesso.charAt(0);
            String nazione = rs.getString("nazione").toUpperCase();
            
  
            e = new Elettore(Code, nome, cognome, nascitald, comune, nazione, sessoChar);
            return e;
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return e;
	}
	

	public boolean deleteElettore(String CF) throws Exception {
		
		Elettore check = getElettore(CF);
		if(check == null) {
			return false;
		}
		
		try {
			String command = "DELETE FROM elettore WHERE elettore.\"CF\" = ?;";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, String.valueOf(CF));
			updatedCmd.executeUpdate();
			
			return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean addElettore(Elettore e, String password) throws Exception {

		Elettore check = getElettore(e.getCF());
		if(check != null) {
			return false;
		}
		
		try {
			String command = "INSERT INTO elettore (password, nome, cognome, nascita, comune, sesso, nazione, CF, registered) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(password.getBytes(StandardCharsets.UTF_8));
			byte[] hash = digest.digest();
			String hashedpassword = String.format("%064x", new BigInteger(1, hash));
			updatedCmd.setString(1, hashedpassword);
			
			updatedCmd.setString(2, e.getName());
			
			updatedCmd.setString(3, e.getSurname());
			
			updatedCmd.setString(4, e.getNascita().toString());
			
			updatedCmd.setString(5, e.getComune());
			
			updatedCmd.setString(6, e.getSesso());
			
			updatedCmd.setString(7, e.getNazione().toUpperCase());
			
			updatedCmd.setString(8, e.getCF());
			
			updatedCmd.setLong(9, 0);
			

			updatedCmd.executeUpdate();
			
			return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
		
	}

	public Elettore loginElettore(String CF, String password) throws Exception{
		
		Elettore e = null;
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(password.getBytes(StandardCharsets.UTF_8));
			byte[] hash = digest.digest();
			String hashedpassword = String.format("%064x", new BigInteger(1, hash));
			String command = "SELECT * FROM elettore WHERE elettore.\"CF\" = ? AND elettore.\"password\" = ?;";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, String.valueOf(CF));
			updatedCmd.setString(2, String.valueOf(hashedpassword));
			ResultSet rs = updatedCmd.executeQuery();
			if(rs.next() == false) {
				return null;
			}
			if (rs.getInt("registered") == 0) {
				return null;
			}
			
			String Code = rs.getString("CF");
            String nome = rs.getString("nome");
            String cognome = rs.getString("cognome");
            String nascita = rs.getString("nascita");
            LocalDate nascitald = LocalDate.parse(nascita);
            String comune = rs.getString("comune");
            String sesso = rs.getString("sesso");
            char sessoChar = sesso.charAt(0);
            String nazione = rs.getString("nazione");
            
            e = new Elettore( Code, nome, cognome, nascitald, comune, nazione, sessoChar);
            
            return e;
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return e;
	}

	@Override
	public boolean checkRegistered(String CF) {
		
		try {
			String command = "SELECT * FROM elettore WHERE elettore.\"CF\" = ?;";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, String.valueOf(CF));
			ResultSet rs = updatedCmd.executeQuery();
			if(rs.next() == false) {
				return false;
			}
			if (rs.getInt("registered") == 0) {
				return false;
			}
            
            return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean register(String CF, String password) throws NoSuchAlgorithmException, SQLException {
		
		if (checkRegistered(CF) == true) {return false;}
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(password.getBytes(StandardCharsets.UTF_8));
		byte[] hash = digest.digest();
		String hashedpassword = String.format("%064x", new BigInteger(1, hash));
		
		String command = "UPDATE elettore SET registered = 1, password = ? WHERE elettore.\"CF\" = ?;";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, String.valueOf(hashedpassword));
		updatedCmd.setString(2, String.valueOf(CF));
		int rs = updatedCmd.executeUpdate();
		if(rs <= 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean updateElettore(Elettore e) throws Exception {
		
		String command = "UPDATE elettore SET nome = ?, cognome = ?, nascita = ?, comune = ?, sesso = ?, nazione = ? WHERE elettore.\"CF\" = ?;";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, String.valueOf(e.getName()));
		updatedCmd.setString(2, String.valueOf(e.getSurname()));
		updatedCmd.setString(3, String.valueOf(e.getNascita().toString()));
		updatedCmd.setString(4, String.valueOf(e.getComune()));
		updatedCmd.setString(5, String.valueOf(e.getSesso()));
		updatedCmd.setString(6, String.valueOf(e.getNazione().toUpperCase()));
		updatedCmd.setString(7, String.valueOf(e.getCF()));
		int rs = updatedCmd.executeUpdate();
		if(rs <= 0) {
			return false;
		}
		return true;
	}

	
}
