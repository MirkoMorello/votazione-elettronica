package DAO;

import Model.*;
import Singleton.*;
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

	private Connection c = ConnectionSingleton.getIstance().getConnection();
	
	
	@Override
	public List<Lista> getAllLists() throws Exception {
		
		List<Lista> lists = new ArrayList<Lista>();
		String command = "SELECT * FROM \"lista\" where eliminata != 1;";
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
		try {
			String command = "UPDATE \"lista\" set eliminata = 1 where nome = ?;";
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
		
		Lista check = getList(name);
		if(check != null) {
			return false;
		}
		
		try {
			String command = "INSERT INTO \"lista\" (nome, descrizione, eliminata) VALUES ( ?, ?, ?);";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, name);
			updatedCmd.setString(2, desc);
			updatedCmd.setInt(3, 0);
			updatedCmd.executeUpdate();
			
			return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Integer getListID(String listname) {
		
		try {
			String command = "SELECT * FROM \"lista\" where lista.nome = ?;";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, String.valueOf(listname));
			ResultSet rs = updatedCmd.executeQuery();
			if(rs.next() == false) {
				return null;
			}
			int id = rs.getInt("id");
			return id;
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Lista> getParticipatingLists(String titolo) throws Exception {
		List<Lista> lists = new ArrayList<Lista>();
		String command = "SELECT nome, lista.descrizione FROM lista, elezione_lista, elezione where elezione.titolo = ? and elezione.id = elezione_lista.elezione and lista.id = elezione_lista.lista";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, titolo);
		ResultSet rs = updatedCmd.executeQuery();
		while(rs.next()) {
			lists.add(new Lista(rs.getString("nome"), rs.getString("descrizione")));
		}
		
		return lists;
	}
	
	@Override
	public List<Lista> getParticipatingListsPref(String titolo) throws Exception {
		List<Lista> lists = new ArrayList<Lista>();
		String command = "SELECT nome, lista.descrizione FROM lista, elezione_lista_preferenza, elezione where elezione.titolo = ? and elezione.id = elezione_lista_preferenza.elezione and lista.id = elezione_lista_preferenza.lista";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, titolo);
		ResultSet rs = updatedCmd.executeQuery();
		while(rs.next()) {
			lists.add(new Lista(rs.getString("nome"), rs.getString("descrizione")));
		}
		
		return lists;
	}


}
