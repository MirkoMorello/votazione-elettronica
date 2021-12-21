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


public class ElettoreDaoImpl implements ElettoreDao{
	
	private Connection c;
	
	private void connect() {
	      try {
	         Class.forName("org.postgresql.Driver");
	         this.c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/postgres",
	            "postgres", "password");
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM \"votazioneElettronica\".elettore;");
			
			while ( rs.next() ) {
	            String CF = rs.getString("CF");
	            String nome = rs.getString("nome");
	            String cognome = rs.getString("cognome");
	            Date nascita = rs.getDate("nascita");
	            LocalDate nascitald = LocalDate.of(nascita.getYear(), nascita.getMonth(), nascita.getDay());
	            String comune = rs.getString("comune");
	            String sesso = rs.getString("sesso");
	            boolean isAdmin = rs.getBoolean("isAdmin");
	            char sessoChar = sesso.charAt(0);
	            String nazione = rs.getString("nazione");
	            Elettore e;
	            
	            if(isAdmin) {
	            	e = new Gestore(CF, nome, cognome, nascitald, comune, nazione, sessoChar);
	            } else {
	            	e = new Elettore(CF, nome, cognome, nascitald, comune, nazione, sessoChar);
	            }
	            
	            elettori.add(e);
	        }
			return elettori;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return elettori;
	}

	public Elettore getElettore(char[] CF) throws Exception {
		Elettore e = null;
		
		if( c == null) {
			this.connect();
		}
		
		try {
			//stmt = c.createStatement();
			//ResultSet rs = stmt.executeQuery("SELECT * FROM votazioneELettronica.elettore WHERE CF ");
			String command = "SELECT * FROM \"votazioneElettronica\".elettore WHERE elettore.\"CF\" = ?;";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, String.valueOf(CF));
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
            boolean isAdmin = rs.getBoolean("isAdmin");
            char sessoChar = sesso.charAt(0);
            String nazione = rs.getString("nazione");
            
            
            if(isAdmin) {
            	e = new Gestore(Code, nome, cognome, nascitald, comune, nazione, sessoChar);
            } else {
            	e = new Elettore(Code, nome, cognome, nascitald, comune, nazione, sessoChar);
            }
            
            return e;
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return e;
	}

	public boolean deleteElettore(char[] CF) throws Exception {
		if( c == null) {
			this.connect();
		}
		
		Elettore check = getElettore(CF);
		if(check == null) {
			return false;
		}
		
		try {
			String command = "DELETE FROM \"votazioneElettronica\".elettore WHERE elettore.\"CF\" = ?;";
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
		
		if( c == null) {
			this.connect();
		}
		
		Elettore check = getElettore(e.getCF().toCharArray());
		if(check != null) {
			return false;
		}
		
		try {
			String command = "INSERT INTO \"votazioneElettronica\".elettore VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, e.getCF());
			updatedCmd.setString(2, e.getName());
			updatedCmd.setString(3, e.getSurname());
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(password.getBytes(StandardCharsets.UTF_8));
			byte[] hash = digest.digest();
			String hashedpassword = String.format("%064x", new BigInteger(1, hash));
			updatedCmd.setString(4, hashedpassword);
			System.out.print(hashedpassword);
			String nascita = e.getNascita().toString();
			updatedCmd.setDate(5, java.sql.Date.valueOf(nascita));
			updatedCmd.setString(6, e.getComune());
			updatedCmd.setString(7, e.getSesso());
			boolean isAdmin = (e instanceof Gestore);
			updatedCmd.setBoolean(8, isAdmin);
			updatedCmd.setString(9, e.getNazione());
			updatedCmd.executeUpdate();
			
			return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
		
	}
	
}
