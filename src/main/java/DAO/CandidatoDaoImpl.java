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
	public List<Candidato> getCandidatesOfList(Lista l) throws Exception {
		
		String listname = null;
		try {
			listname = l.getName();
		}catch(NullPointerException e) {
		}
		if(listname != null) {
			List<Candidato> candList = new ArrayList<Candidato>();
			String command = "SELECT candidato.nome, candidato.cognome, candidato.nascita, candidato.sesso, lista.descrizione FROM candidato, lista where candidato.listaId = lista.id and (eliminato = 0 or eliminato is null) and lista.nome = ?;";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, listname);
			ResultSet rs = updatedCmd.executeQuery();
			while(rs.next()) {
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String nascita = rs.getString("nascita");
	            LocalDate nascitald = LocalDate.parse(nascita);
	            String sesso = rs.getString("sesso");
	            String listdesc = rs.getString("descrizione");
	            
	            candList.add(new Candidato(nome, cognome, nascitald, sesso, new Lista(listname, listdesc)));
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
	            candList.add(new Candidato(nome, cognome, nascitald, sesso, null));
			}
			return candList;
		}
		
		
	}

	@Override
	public boolean deleteCandidate(Candidato cand) throws Exception {
		String listname = cand.getList().getName();
		if(listname != null) {
			int listId = cand.getList().getId();
			
			try {
				String command = "DELETE FROM candidato where nome = ? AND cognome =  ? AND listaId = ?";
				PreparedStatement updatedCmd= c.prepareStatement(command);
				updatedCmd.setString(1, cand.getNome());
				updatedCmd.setString(2, cand.getCognome());
				updatedCmd.setInt(3, listId);
				updatedCmd.executeUpdate();
				LoggerSingleton.getIstance().log("admin " + CurrentAdminSingleton.getIstance().getAdmin().getUsername() + " removed from database candidate " + cand);
			}catch (SQLException ex) {
				return false;
			}
			
			return true;
		} else {
			
			try {
				String command = "DELETE FROM candidato where nome = ? AND cognome =  ? AND listaId = is NULL";
				PreparedStatement updatedCmd= c.prepareStatement(command);
				updatedCmd.setString(1, cand.getNome());
				updatedCmd.setString(2, cand.getCognome());
				updatedCmd.executeUpdate();
				LoggerSingleton.getIstance().log("admin " + CurrentAdminSingleton.getIstance().getAdmin().getUsername() + " removed from database candidate " + cand);
			}catch (SQLException ex) {
				return false;
			}
			
			return true;
		}
		
	}

	@Override
	public boolean addCandidate(Candidato cand) throws SQLException {
		if(cand.getList() != null) {
			int listId = cand.getList().getId();
			
			try {
				String command = "INSERT INTO candidato (nome, cognome, nascita, listaId, sesso) values (?, ?, ?, ?, ?)";
				PreparedStatement updatedCmd= c.prepareStatement(command);
				updatedCmd.setString(1, cand.getNome());
				updatedCmd.setString(2, cand.getCognome());
				updatedCmd.setString(3, cand.getNascita().toString());
				updatedCmd.setInt(4, listId);
				updatedCmd.setString(5, cand.getSesso());
				System.out.println(updatedCmd.toString());
				updatedCmd.executeUpdate();
				LoggerSingleton.getIstance().log("admin " + CurrentAdminSingleton.getIstance().getAdmin().getUsername() + " added to database candidate " + cand);
				
			}catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
			
			return true;
		} else {
			
			try {
				String command = "INSERT INTO candidato (nome, cognome, nascita, listaId, sesso) values (?, ?, ?, null, ?)";
				PreparedStatement updatedCmd= c.prepareStatement(command);
				updatedCmd.setString(1, cand.getNome());
				updatedCmd.setString(2, cand.getCognome());
				updatedCmd.setString(3, cand.getNascita().toString());
				updatedCmd.setString(4, cand.getSesso());
				System.out.println(updatedCmd.toString());
				updatedCmd.executeUpdate();
				LoggerSingleton.getIstance().log("admin " + CurrentAdminSingleton.getIstance().getAdmin().getUsername() + " added to database candidate " + cand);
				
			}catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
			
			return true;
		}
		
	}

	@Override
	public List<Candidato> getAllCandidates() throws Exception {
		List<Candidato> candList = new ArrayList<Candidato>();
		String command = "SELECT candidato.nome AS nomecand, candidato.cognome, candidato.nascita, candidato.sesso, lista.nome AS nomelista, lista.descrizione FROM candidato, lista where (eliminato is NULL or eliminato = 0) and candidato.listaId = lista.id UNION SELECT candidato.nome AS nomecand, candidato.cognome, candidato.nascita, candidato.sesso, null, null FROM candidato, lista where (eliminato is NULL or eliminato = 0) and candidato.listaId is null";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		ResultSet rs = updatedCmd.executeQuery();
		while(rs.next()) {
			String nome = rs.getString("nomecand");
			String cognome = rs.getString("cognome");
			String nascita = rs.getString("nascita");
            LocalDate nascitald = LocalDate.parse(nascita);
            String sesso = rs.getString("sesso");
            Lista lista = new Lista(rs.getString("nomelista"), rs.getString("descrizione"));
            candList.add(new Candidato(nome, cognome, nascitald, sesso, lista));
		}
		return candList;
	}

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

	@Override
	public List<Candidato> getPartecipatingCandidates(String titolo) throws Exception {
		List<Candidato> candidates = new ArrayList<Candidato>();
		String command = "SELECT candidato.nome, cognome, nascita, sesso, lista.nome as listanome, lista.descrizione as listadesc  FROM lista, candidato, elezione_candidato, elezione where elezione.titolo = ? and elezione.id = elezione_candidato.elezione and candidato.id = elezione_candidato.candidato and candidato.listaId = lista.id \r\n"
				+ "UNION \r\n"
				+ "SELECT candidato.nome, cognome, nascita, sesso, null, null FROM lista, candidato, elezione_candidato, elezione where elezione.titolo = \"categorico con cand\" and elezione.id = elezione_candidato.elezione and candidato.id = elezione_candidato.candidato and candidato.listaId is null";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, titolo);
		ResultSet rs = updatedCmd.executeQuery();
		while(rs.next()) {
			candidates.add(new Candidato(rs.getString("nome"), rs.getString("cognome"), LocalDate.parse(rs.getString("nascita")), rs.getString("sesso"), new Lista(rs.getString("listanome"), rs.getString("listadesc"))));
		}
		
		return candidates;
	}
	

}
