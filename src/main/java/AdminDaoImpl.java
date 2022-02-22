import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class AdminDaoImpl implements AdminDAO{
	
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
	public List<Admin> getAllAdmins() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Admin getAdmin(String username) throws Exception {
		Admin admin = null;
		
		if( c == null) {
			this.connect();
		}
		
		try {
			String command = "SELECT * FROM admin where admin.username = ?;";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, String.valueOf(username));
			ResultSet rs = updatedCmd.executeQuery();
			if(rs.next() == false) {
				return null;
			}
			int id = rs.getInt("id");
            String user = rs.getString("username");
            
            admin = new Admin(id, user);
            
            return admin;
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return admin;
	}

	@Override
	public boolean deleteAdmin(String username) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAdmin(String username, String password) throws NoSuchAlgorithmException, Exception {
		if( c == null) {
			this.connect();
		}
		
		Admin check = getAdmin(username);
		if(check != null) {
			return false;
		}
		
		try {
			String command = "INSERT INTO \"admin\" (username, password) VALUES ( ?, ?);";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, username);
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(password.getBytes(StandardCharsets.UTF_8));
			byte[] hash = digest.digest();
			String hashedpassword = String.format("%064x", new BigInteger(1, hash));
			updatedCmd.setString(2, hashedpassword);
			System.out.print(hashedpassword);
			updatedCmd.executeUpdate();
			
			return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public Elettore loginAdmin(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
