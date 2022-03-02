package DAO;

import Model.*;
import Singleton.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ElezioneDaoImpl implements ElezioneDao{
	
	private Connection c = ConnectionSingleton.getIstance().getConnection();

	@Override
	public List<Elezione> getElezioniAttive() throws SQLException {
		List<Elezione> elezioni = new ArrayList<Elezione>();
		String command = "SELECT * FROM \"elezione\" where closed != 1;";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		ResultSet rs = updatedCmd.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getString("tipologia"));
			switch(rs.getString("tipologia")){
			case "ordinale":
				elezioni.add(new VotoOrdinale(rs.getString("titolo"), rs.getString("descrizione")));
				break;
			case "categorico":
				elezioni.add(new VotoCategorico(rs.getString("titolo"), rs.getString("descrizione")));
				break;
			case "categorico con preferenze":
				elezioni.add(new VotoCategoricoConPreferenze(rs.getString("titolo"), rs.getString("descrizione")));
				break;
			case "referendum":
				elezioni.add(new VotoOrdinale(rs.getString("titolo"), rs.getString("descrizione")));
				break;
			default:
				break;
			}
		}
		System.out.println(elezioni.size());
		
		
		return elezioni;
	}

	@Override
	public boolean createElezione(String titolo, String descrizione, String tipologia, boolean maggioranza_assoluta,
			boolean liste, boolean quorum, Integer comunale) throws SQLException {
		int magg = 0;
		int list = 0;
		int quo = 0;
		int com = 0;
		if(maggioranza_assoluta)
			magg = 1;
		if(liste)
			list = 1;
		if(quorum)
			quo = 1;
		String command = "INSERT INTO elezione (titolo, descrizione, tipologia, maggioranza_assoluta, liste, voters_count, closed, comunale) values (?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, titolo);
		updatedCmd.setString(2, descrizione);
		updatedCmd.setString(3, tipologia);
		updatedCmd.setInt(4, magg);
		updatedCmd.setInt(5, list);
		updatedCmd.setInt(6, 0);
		updatedCmd.setInt(7, 0);
		if(comunale != null) {
			updatedCmd.setInt(8, comunale);
		}
		
		try{
			updatedCmd.executeUpdate();
			return true;
		}catch(SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean closeElezione(String titolo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteElezione(String titolo) throws SQLException {
		String command = "delete from elezione where titolo = ?;";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, titolo);
		
		try{
			updatedCmd.executeUpdate();
			return true;
		}catch(SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public void pushListe(List<String> selected) throws SQLException {
		int elezioneid;
		int listaid;
		String titolo = CreatingElezioneSingleton.getTitolo();
		elezioneid = getElezioneId(titolo);
		for(int i = 0; i < selected.size(); i++) {
			listaid = DaoFactorySingleton.getDaoFactory().getListaDao().getListID(selected.get(i));
			String command = "insert into elezione_lista (elezione, lista, punteggio) values (?, ?, 0)";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setInt(1, elezioneid);
			updatedCmd.setInt(2, listaid);
			try {
				updatedCmd.executeUpdate();
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void pushCandidati(List<String> selected) throws SQLException {
		int elezioneid;
		int candidatoid;
		String titolo = CreatingElezioneSingleton.getTitolo();
		elezioneid = getElezioneId(titolo);
		for(int i = 0; i < selected.size(); i++) {
			String[] splitted = selected.get(i).split(" ");
			candidatoid = DaoFactorySingleton.getDaoFactory().getCandidatoDao().getCandidatoID(splitted[0], splitted[1] );
			String command = "insert into elezione_candidato (elezione, candidato, punteggio) values (?, ?, 0)";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setInt(1, elezioneid);
			updatedCmd.setInt(2, candidatoid);
			try {
				updatedCmd.executeUpdate();
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void pushListeCandidati(List<String> selected) throws Exception {
		int elezioneid;
		int listaid;
		int candidatoid;
		String titolo = CreatingElezioneSingleton.getTitolo();
		elezioneid = getElezioneId(titolo);
		for(int i = 0; i < selected.size(); i++) {
			listaid = DaoFactorySingleton.getDaoFactory().getListaDao().getListID(selected.get(i));
			List<Candidato> candidatesoflist = DaoFactorySingleton.getDaoFactory().getCandidatoDao().getCandidatesOfList(selected.get(i));
			for(int j = 0; j < candidatesoflist.size(); j++) {
				candidatoid = DaoFactorySingleton.getDaoFactory().getCandidatoDao().getCandidatoID(candidatesoflist.get(j).getNome(), candidatesoflist.get(j).getCognome());
				String command = "insert into elezione_lista_preferenza (elezione, lista, candidato, punteggio) values (?, ?, ?, 0)";
				PreparedStatement updatedCmd= c.prepareStatement(command);
				updatedCmd.setInt(1, elezioneid);
				updatedCmd.setInt(2, listaid);
				updatedCmd.setInt(3, candidatoid);
				try {
					updatedCmd.executeUpdate();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public int getElezioneId(String titolo) throws SQLException {
		String command = "select * from elezione where titolo = ?;";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, titolo);
		ResultSet rs = updatedCmd.executeQuery();
		rs.next();
		return rs.getInt("id");
	}
	
	public void pushReferendum() throws SQLException {
		int id = getElezioneId(CreatingElezioneSingleton.getIstance().getTitolo());
		String command = "insert into referendum (elezione, si, no, bianca) values (?, 0, 0, 0)";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setInt(1, id);
		try {
			updatedCmd.executeUpdate();
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public String getVincitoreReferendum(String titolo) throws SQLException {
		String command = "SELECT * FROM elezione, referendum where elezione.id = referendum.elezione and elezione.titolo = ?";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, titolo);
		ResultSet rs = updatedCmd.executeQuery();
		rs.next();
		int si = rs.getInt("si");
		int no = rs.getInt("no");
		int quorum = getQuorum();
		if(si > no)
			if(si > quorum)
				return "si";
			else
				return "quorum non raggiunto";
		else 
			if(no > quorum)
				return "no";
			else
				return "quorum non raggiunto";
	}

	@Override
	public String getVincitoreCategorico(String titolo) throws SQLException {
		boolean votingForLists = votingByList(titolo);
		boolean maggAssoluta = getMajority(titolo);
		String command;
		String returnValue;
		int votes;
		if(votingForLists) {
			command = "select nome, MAX(punteggio) from elezione, elezione_lista, lista where elezione.id = elezione_lista.elezione and elezione_lista.lista = lista.id and titolo = ?";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, titolo);
			ResultSet rs = updatedCmd.executeQuery();
			rs.next();
			votes = rs.getInt("MAX(punteggio)");
			returnValue = rs.getString("nome");
		} else {
			command = "select candidato.nome, candidato.cognome, MAX(punteggio) from elezione, elezione_candidato, candidato where elezione.id = elezione_candidato.elezione and elezione_candidato.candidato = candidato.id and titolo = ?";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, titolo);
			ResultSet rs = updatedCmd.executeQuery();
			rs.next();
			votes = rs.getInt("MAX(punteggio)");
			returnValue = rs.getString("nome") +" "+ rs.getString("cognome");
		}
		
		if(maggAssoluta) {
			command = "select SUM(punteggio) from elezione, elezione_candidato, candidato where elezione.id = elezione_candidato.elezione and elezione_candidato.candidato = candidato.id and titolo = ?";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, titolo);
			ResultSet rs = updatedCmd.executeQuery();
			rs.next();
			int totVoti = rs.getInt("SUM(punteggio)");
			if(votes < (totVoti/2) + 1) {
				returnValue = "maggioranza assoluta non raggiunta";
			}
		}
		
		return returnValue;
	}

	@Override
	public String getVincitoreOrdinale(String titolo) throws SQLException {
		boolean votingForLists = votingByList(titolo);
		boolean maggAssoluta = getMajority(titolo);
		String command;
		String returnValue;
		int votes;
		if(votingForLists) {
			command = "select nome, MAX(punteggio) from elezione, elezione_lista, lista where elezione.id = elezione_lista.elezione and elezione_lista.lista = lista.id and titolo = ?";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, titolo);
			ResultSet rs = updatedCmd.executeQuery();
			rs.next();
			votes = rs.getInt("MAX(punteggio)");
			returnValue = rs.getString("nome");
		} else {
			command = "select candidato.nome, candidato.cognome, MAX(punteggio) from elezione, elezione_candidato, candidato where elezione.id = elezione_candidato.elezione and elezione_candidato.candidato = candidato.id and titolo = ?";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, titolo);
			ResultSet rs = updatedCmd.executeQuery();
			rs.next();
			votes = rs.getInt("MAX(punteggio)");
			returnValue = rs.getString("nome") +" "+ rs.getString("cognome");
		}
		
		if(maggAssoluta) {
			command = "select SUM(punteggio) from elezione, elezione_candidato, candidato where elezione.id = elezione_candidato.elezione and elezione_candidato.candidato = candidato.id and titolo = ?";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, titolo);
			ResultSet rs = updatedCmd.executeQuery();
			rs.next();
			int totVoti = rs.getInt("SUM(punteggio)");
			if(votes < (totVoti/2) + 1) {
				returnValue = "maggioranza assoluta non raggiunta";
			}
		}
		
		return returnValue;
	}

	@Override
	public String getVincitoreCatConPref(String titolo) throws SQLException {
		
		boolean votingForLists = votingByList(titolo);
		boolean maggAssoluta = getMajority(titolo);
		String command;
		String returnValue;
		int votes;
		if(votingForLists) {
			command = "select nome, MAX(punteggio) from elezione, elezione_lista_preferenza, lista where elezione.id = elezione_lista_preferenza.elezione and elezione_lista_preferenza.lista = lista.id and titolo = ?";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, titolo);
			ResultSet rs = updatedCmd.executeQuery();
			rs.next();
			votes = rs.getInt("MAX(punteggio)");
			returnValue = rs.getString("nome");
		} else {
			command = "select candidato.nome, candidato.cognome, MAX(punteggio) from elezione, elezione_lista_preferenza, lista where elezione.id = elezione_lista_preferenza.elezione and elezione_lista_preferenza.lista = lista.id and titolo = ?";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, titolo);
			ResultSet rs = updatedCmd.executeQuery();
			rs.next();
			votes = rs.getInt("MAX(punteggio)");
			returnValue = rs.getString("nome") +" "+ rs.getString("cognome");
		}
		
		if(maggAssoluta) {
			command = "select SUM(punteggio) from elezione, elezione_lista_preferenza, lista where elezione.id = elezione_lista_preferenza.elezione and elezione_lista_preferenza.lista = lista.id and titolo = ?";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setString(1, titolo);
			ResultSet rs = updatedCmd.executeQuery();
			rs.next();
			int totVoti = rs.getInt("SUM(punteggio)");
			if(votes < (totVoti/2) + 1) {
				returnValue = "maggioranza assoluta non raggiunta";
				return returnValue;
			}
		}
		
		command = "select candidato.nome, candidato.cognome, MAX(elezione_lista_preferenza.punteggio) from elezione, elezione_lista_preferenza, candidato, lista where elezione.id = elezione_lista_preferenza.elezione and elezione_lista_preferenza.candidato = candidato.id and titolo = ? and elezione_lista_preferenza.lista = lista.id and lista.nome = ?";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, titolo);
		updatedCmd.setString(2, returnValue);
		ResultSet rs = updatedCmd.executeQuery();
		rs.next();
		returnValue = returnValue + " - " + rs.getString("candidato.nome") + " " + rs.getString("candidato.cognome");
		
		return returnValue;
	}
	
	private boolean votingByList(String titolo) throws SQLException {
		String command = "SELECT liste FROM elezione where titolo = ?";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, titolo);
		ResultSet rs = updatedCmd.executeQuery();
		rs.next();
		return (rs.getInt("liste")==1);
	}
	
	private boolean getMajority(String titolo) throws SQLException {
		String command = "SELECT liste FROM elezione where titolo = ?";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, titolo);
		ResultSet rs = updatedCmd.executeQuery();
		rs.next();
		return (rs.getInt("maggioranza_assoluta")==1);
	}
	
	private int getQuorum() throws SQLException {
		String command = "SELECT COUNT(*) from elettori";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		ResultSet rs = updatedCmd.executeQuery();
		rs.next();
		return rs.getInt("count(*)")/2;
	}

}
