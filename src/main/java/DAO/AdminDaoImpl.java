package DAO;
import Singleton.*;
import Model.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * Questa classe è parte della componente Model del design pattern MVC
 * Questa è l'implementazione dell'interfaccia AdminDao
 * Questa è una classe DAO
 */

public class AdminDaoImpl implements AdminDAO{
	
	private Connection c = ConnectionSingleton.getIstance().getConnection();

	@Override
	public Admin getAdmin(String username) throws Exception {
		Admin admin = null;
		
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
            int superuser = rs.getInt("superuser");
            
            admin = new Admin( user, superuser == 1);
            
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
	public boolean addAdmin(Admin a, String password) throws NoSuchAlgorithmException, Exception {
		
		String username = a.getUsername();
		
		int suser = 0;
		if(a.isSuperUser()) {
			suser = 1;
		}
		
		Admin check = getAdmin(username);
		if(check != null) {
			return false;
		}
		
		try {
			String command = "INSERT INTO \"admin\" (username, password, superuser) VALUES ( ?, ?, ?);";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, username);
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(password.getBytes(StandardCharsets.UTF_8));
			byte[] hash = digest.digest();
			String hashedpassword = String.format("%064x", new BigInteger(1, hash));
			updatedCmd.setString(2, hashedpassword);
			updatedCmd.setInt(3, suser);
			System.out.print(hashedpassword);
			updatedCmd.executeUpdate();
			
			return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public Admin loginAdmin(String username, String password) throws Exception {
		
		Admin admin = null;
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(password.getBytes(StandardCharsets.UTF_8));
			byte[] hash = digest.digest();
			String hashedpassword = String.format("%064x", new BigInteger(1, hash));
			String command = "SELECT * FROM admin WHERE username = ? AND password = ?;";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, username);
			updatedCmd.setString(2, String.valueOf(hashedpassword));
			ResultSet rs = updatedCmd.executeQuery();
			if(rs.next() == false) {
				return null;
			}
			int id = rs.getInt("id");
            String user = rs.getString("username");
            int superuser = rs.getInt("superuser");
            
            admin = new Admin( user, superuser == 1);
            
            return admin;
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return admin;
	}

	@Override
	public List<Admin> getAllAdmins() throws Exception {
		List<Admin> admins = new ArrayList<Admin>();
		String command = "SELECT * FROM admin;";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		ResultSet rs = updatedCmd.executeQuery();
		while(rs.next()) {
			admins.add(new Admin( rs.getString("username"), (rs.getInt("superuser") == 1)));
		}
		return admins;
	}

	@Override
	public boolean isAdmin(String username) {
		String command = "SELECT superuser FROM admin where admin.username = ?;";
		PreparedStatement updatedCmd;
		try {
			updatedCmd = c.prepareStatement(command);
			updatedCmd.setString(1, String.valueOf(username));
			ResultSet rs = updatedCmd.executeQuery();
			if(rs.next() == false) {
				return false;
			}
			return (rs.getInt("superuser") == 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

}
