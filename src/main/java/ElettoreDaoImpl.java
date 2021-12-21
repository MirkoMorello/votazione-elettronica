import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
			String command = "SELECT * FROM \"votazioneElettronica\".elettore WHERE elettore.CF = ?;";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(0, String.valueOf(CF));
			ResultSet rs = updatedCmd.executeQuery();
			
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

	public boolean DeleteElettore(char[] CF) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean AddElettore(Elettore e, String password) {
		
		if( c == null) {
			this.connect();
		}
		
		try {
			//stmt = c.createStatement();
			//ResultSet rs = stmt.executeQuery("SELECT * FROM votazioneELettronica.elettore WHERE CF ");
			String command = "INSERT INTO \"votazioneElettronica\".elettore VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(0, e.getCF());
			updatedCmd.setString(1, e.getName());
			updatedCmd.setString(2, e.getSurname());
			updatedCmd.setString(3, );
			updatedCmd.setString(4, e.getName());
			updatedCmd.setString(5, e.getSurname());
			
			
            
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
	
}
