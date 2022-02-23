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
	
	@SuppressWarnings("deprecation")
	public List<Elettore> getAllElettori() throws Exception {
		Statement stmt = null;
		
		if( c == null) {
			this.connect();
		}
		List <Elettore> elettori = new ArrayList<Elettore>();
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM elettore;");
			
			while ( rs.next() ) {
				int id = rs.getInt("id");
	            String CF = rs.getString("CF");
	            String nome = rs.getString("nome");
	            String cognome = rs.getString("cognome");
	            Date nascita = rs.getDate("nascita");
	            LocalDate nascitald = LocalDate.of(nascita.getYear(), nascita.getMonth(), nascita.getDay());
	            String comune = rs.getString("comune");
	            String sesso = rs.getString("sesso");
	            char sessoChar = sesso.charAt(0);
	            String nazione = rs.getString("nazione");
	            Elettore e = new Elettore(id, CF, nome, cognome, nascitald, comune, nazione, sessoChar);
	            
	            elettori.add(e);
	        }
			return elettori;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return elettori;
	}

	public Elettore getElettore(Integer id) throws Exception {
		Elettore e = null;
		
		if( c == null) {
			this.connect();
		}
		
		try {
			String command = "SELECT * FROM elettore WHERE elettore.\"id\" = ?;";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, String.valueOf(id));
			ResultSet rs = updatedCmd.executeQuery();
			if(rs.next() == false) {
				return null;
			}
			String Code = rs.getString("id");
            String nome = rs.getString("nome");
            String cognome = rs.getString("cognome");
            Date nascita = rs.getDate("nascita");
            LocalDate nascitald = LocalDate.of(nascita.getYear(), nascita.getMonth(), nascita.getDay());
            String comune = rs.getString("comune");
            String sesso = rs.getString("sesso");
            char sessoChar = sesso.charAt(0);
            String nazione = rs.getString("nazione");
            
  
            e = new Elettore(id, Code, nome, cognome, nascitald, comune, nazione, sessoChar);
            return e;
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return e;
	}
	

	public boolean deleteElettore(Integer id) throws Exception {
		if( c == null) {
			this.connect();
		}
		
		Elettore check = getElettore(id);
		if(check == null) {
			return false;
		}
		
		try {
			String command = "DELETE FROM elettore WHERE elettore.\"CF\" = ?;";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, String.valueOf(id));
			updatedCmd.executeUpdate();
			
			return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean addElettore(Elettore e, String password) throws Exception {
		
		if( c == null) {
			this.connect();
		}
		
		Elettore check = getElettore(e.getID());
		if(check != null) {
			return false;
		}
		
		try {
			String command = "INSERT INTO elettore VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setInt(1, e.getID());
			
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(password.getBytes(StandardCharsets.UTF_8));
			byte[] hash = digest.digest();
			String hashedpassword = String.format("%064x", new BigInteger(1, hash));
			updatedCmd.setString(2, hashedpassword);
			
			updatedCmd.setString(3, e.getName());
			
			updatedCmd.setString(4, e.getSurname());
			
			String nascita = e.getNascita().toString();
			updatedCmd.setDate(5, java.sql.Date.valueOf(nascita));
			
			updatedCmd.setString(6, e.getComune());
			
			updatedCmd.setString(7, e.getSesso());
			
			updatedCmd.setString(8, e.getNazione());
			
			updatedCmd.setString(9, e.getCF());
			

			updatedCmd.executeUpdate();
			
			return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
		
	}

	@Override
	public Elettore loginElettore(Integer id, String password) throws Exception{
		Elettore e = null;
		
		if( c == null) {
			this.connect();
		}
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(password.getBytes(StandardCharsets.UTF_8));
			byte[] hash = digest.digest();
			String hashedpassword = String.format("%064x", new BigInteger(1, hash));
			String command = "SELECT * FROM elettore WHERE elettore.\"id\" = ? AND elettore.\"password\" = ?;";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, String.valueOf(id));
			updatedCmd.setString(2, String.valueOf(hashedpassword));
			ResultSet rs = updatedCmd.executeQuery();
			if(rs.next() == false) {
				return null;
			}
			String Code = rs.getString("CF");
            String nome = rs.getString("nome");
            String cognome = rs.getString("cognome");
            Date nascita = rs.getDate("nascita");
            LocalDate nascitald = LocalDate.of(nascita.getYear(), nascita.getMonth(), nascita.getDay());
            String comune = rs.getString("comune");
            String sesso = rs.getString("sesso");
            char sessoChar = sesso.charAt(0);
            String nazione = rs.getString("nazione");
            
            e = new Elettore(id, Code, nome, cognome, nascitald, comune, nazione, sessoChar);
            
            return e;
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return e;
	}
	
}
