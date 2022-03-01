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
	public boolean createComune(String nome, int popolazione) {
		try {
			String command = "insert into comune (nome, popolazione) values (?, ?)";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, nome);
			updatedCmd.setInt(2, popolazione);
			updatedCmd.executeUpdate();
			return true;
		} catch (SQLException e) {
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

}
