package DAO;

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
import Singleton.*;
import Model.*;

public class CandidatoDaoImpl implements CandidatoDao{
	
	private Connection c = ConnectionSingleton.getIstance().getConnection();

	@Override
	public List<Candidato> getCandidatesOfList(String listname) throws Exception {
		
		if(listname != null) {
			int listId = DaoFactorySingleton.getDaoFactory().getListaDao().getListID(listname);
			List<Candidato> candList = new ArrayList<Candidato>();
			String command = "SELECT * FROM candidato where candidato.listaId = ? and (eliminato = 0 or eliminato is null);";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setInt(1, listId);
			ResultSet rs = updatedCmd.executeQuery();
			while(rs.next()) {
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String nascita = rs.getString("nascita");
	            LocalDate nascitald = LocalDate.parse(nascita);
	            String sesso = rs.getString("sesso");
	            candList.add(new Candidato(nome, cognome, nascitald, sesso));
			}
			return candList;
		} else {
			List<Candidato> candList = new ArrayList<Candidato>();
			String command = "SELECT * FROM candidato where candidato.listaId is NULL and (eliminato = 0 or eliminato is null);";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			ResultSet rs = updatedCmd.executeQuery();
			while(rs.next()) {
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String nascita = rs.getString("nascita");
	            LocalDate nascitald = LocalDate.parse(nascita);
	            String sesso = rs.getString("sesso");
	            candList.add(new Candidato(nome, cognome, nascitald, sesso));
			}
			return candList;
		}
		
		
	}

	@Override
	public boolean deleteCandidate(String name, String surname, String listname) throws Exception {
		if(listname != null) {
			int listId = DaoFactorySingleton.getDaoFactory().getListaDao().getListID(listname);
			
			try {
				String command = "DELETE FROM candidato where nome = ? AND cognome =  ? AND listaId = ?";
				PreparedStatement updatedCmd= c.prepareStatement(command);
				updatedCmd.setString(1, name);
				updatedCmd.setString(2, surname);
				updatedCmd.setInt(3, listId);
				updatedCmd.executeUpdate();
			}catch (SQLException ex) {
				return false;
			}
			
			return true;
		} else {
			
			try {
				String command = "DELETE FROM candidato where nome = ? AND cognome =  ? AND listaId = is NULL";
				PreparedStatement updatedCmd= c.prepareStatement(command);
				updatedCmd.setString(1, name);
				updatedCmd.setString(2, surname);
				updatedCmd.executeUpdate();
			}catch (SQLException ex) {
				return false;
			}
			
			return true;
		}
		
	}

	@Override
	public boolean addCandidate(String name, String surname, LocalDate nascita, String listname, String sesso) throws SQLException {
		if(listname != null) {
			int listId = DaoFactorySingleton.getDaoFactory().getListaDao().getListID(listname);
			
			try {
				String command = "INSERT INTO candidato (nome, cognome, nascita, listaId, sesso) values (?, ?, ?, ?, ?)";
				PreparedStatement updatedCmd= c.prepareStatement(command);
				updatedCmd.setString(1, name);
				updatedCmd.setString(2, surname);
				updatedCmd.setString(3, nascita.toString());
				updatedCmd.setInt(4, listId);
				updatedCmd.setString(5, sesso);
				System.out.println(updatedCmd.toString());
				updatedCmd.executeUpdate();
				
			}catch (SQLException ex) {
				ex.printStackTrace();
				return false;
			}
			
			return true;
		} else {
			
			try {
				String command = "INSERT INTO candidato (nome, cognome, nascita, listaId, sesso) values (?, ?, ?, null, ?)";
				PreparedStatement updatedCmd= c.prepareStatement(command);
				updatedCmd.setString(1, name);
				updatedCmd.setString(2, surname);
				updatedCmd.setString(3, nascita.toString());
				updatedCmd.setString(4, sesso);
				System.out.println(updatedCmd.toString());
				updatedCmd.executeUpdate();
				
			}catch (SQLException ex) {
				ex.printStackTrace();
				return false;
			}
			
			return true;
		}
		
	}

	@Override
	public List<Candidato> getAllCandidates() throws Exception {
		List<Candidato> candList = new ArrayList<Candidato>();
		String command = "SELECT * FROM candidato where eliminato is NULL or eliminato = 0;";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		ResultSet rs = updatedCmd.executeQuery();
		while(rs.next()) {
			String nome = rs.getString("nome");
			String cognome = rs.getString("cognome");
			String nascita = rs.getString("nascita");
            LocalDate nascitald = LocalDate.parse(nascita);
            String sesso = rs.getString("sesso");
            candList.add(new Candidato(nome, cognome, nascitald, sesso));
		}
		return candList;
	}

	@Override
	public String getCandidateList(String nome, String cognome) throws Exception {
		String command = "SELECT lista.nome FROM candidato, lista where lista.eliminata != 1 and candidato.listaId = lista.id and candidato.nome = ? and candidato.cognome = ?";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, nome);
		updatedCmd.setString(2, cognome);
		ResultSet rs = updatedCmd.executeQuery();
		if(rs.next()) {
			return rs.getString("nome");
		}else {
			return "lista indipendente";
		}
	}
	
	
	public Integer getCandidatoID(String nome, String cognome) {
		try {
			String command = "SELECT * FROM \"candidato\" where candidato.nome = ? and candidato.cognome = ?;";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, nome);
			updatedCmd.setString(2, cognome);
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
	

}
