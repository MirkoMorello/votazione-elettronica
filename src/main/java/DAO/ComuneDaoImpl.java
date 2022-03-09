package DAO;

import Model.*;
import Singleton.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComuneDaoImpl implements ComuneDao{
	
	private Connection c = ConnectionSingleton.getIstance().getConnection();

	@Override
	public Comune getComune(int id) throws SQLException {
		String command = "SELECT * FROM \"comune\" where id = ?";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setInt(1, id);
		ResultSet rs = updatedCmd.executeQuery();
		rs.next();
		return new Comune(rs.getString("nome"), rs.getInt("popolazione"));
	}

	@Override
	public boolean createComune(Comune comune) {
		
		String nome = comune.getNome();
		int popolazione = comune.getPopolazione(); 
		
		try {
			String command = "insert into comune (nome, popolazione) values (?, ?)";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, nome);
			updatedCmd.setInt(2, popolazione);
			updatedCmd.executeUpdate();
			try {
				LoggerSingleton.getIstance().log("admin " + CurrentAdminSingleton.getIstance().getAdmin().getUsername() + " added to database comune " + nome);
			}catch(Exception e) {
				LoggerSingleton.getIstance().log("added to database comune " + nome);
			}
			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public int getComuneId(String nome) throws SQLException {
		String command = "SELECT * FROM \"comune\" where nome = ?";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, nome);
		ResultSet rs = updatedCmd.executeQuery();
		rs.next();
		return rs.getInt("id");
	}
	
	public boolean deleteComune(Comune comune) throws SQLException{
		String command = "DELETE FROM \"comune\" where nome = ?";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, comune.getNome());
		int rs = updatedCmd.executeUpdate();;
		if (rs == 0) {
			return false;
		}
		return true;
	}

}
