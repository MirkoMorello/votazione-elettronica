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
		String command = "SELECT * FROM \"elezione\" where (closed = 0 or closed is null);";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		ResultSet rs = updatedCmd.executeQuery();
		while(rs.next()) {
			switch(rs.getString("tipologia")){
			case "ordinale":
				elezioni.add(new VotoOrdinale(rs.getString("titolo"), rs.getString("descrizione"), (rs.getInt("liste") == 1)));
				break;
			case "categorico":
				elezioni.add(new VotoCategorico(rs.getString("titolo"), rs.getString("descrizione"), (rs.getInt("liste") == 1), (rs.getInt("maggioranza_assoluta") == 1)));
				break;
			case "categorico con preferenze":
				Integer comunale = rs.getInt("comunale");
				boolean com = false;
				if(comunale !=  null) {
					com = true;
				}
				elezioni.add(new VotoCategoricoConPreferenze(rs.getString("titolo"), rs.getString("descrizione"), com, (rs.getInt("maggioranza_assoluta") == 1)));
				break;
			case "referendum":
				elezioni.add(new Referendum(rs.getString("titolo"), rs.getString("descrizione"), (rs.getInt("quorum") == 1)));
				break;
			default:
				break;
			}
		}
		
		
		return elezioni;
	}

	@Override
	public boolean createElezione(Elezione e) throws SQLException {
		int magg = 0;
		int list = 0;
		int quo = 0;
		int com = 0;
		if(e.getMaggAssoluta())
			magg = 1;
		if(e.getListe())
			list = 1;
		if(e.getQuorum())
			quo = 1;
		String command = "INSERT INTO elezione (titolo, descrizione, tipologia, maggioranza_assoluta, liste, voters_count, closed, comunale, quorum) values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, e.getTitolo());
		updatedCmd.setString(2, e.getDescrizione());
		updatedCmd.setString(3, e.getTipo());
		updatedCmd.setInt(4, magg);
		updatedCmd.setInt(5, list);
		updatedCmd.setInt(6, 0);
		updatedCmd.setInt(7, 0);
		
		
		
		if(e.isComunale()) {
			updatedCmd.setInt(8, CreatingElezioneSingleton.getIstance().getComune());
		}
		
		updatedCmd.setInt(9, quo);
		
		try{
			updatedCmd.executeUpdate();
			LoggerSingleton.getIstance().log("admin " + CurrentAdminSingleton.getIstance().getAdmin().getUsername() + " created elezione " + e.getTitolo());
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean closeElezione(String titolo) throws Exception {
		String command = "update elezione set closed = 1 where titolo = ?";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, titolo);
		
		try{
			updatedCmd.executeUpdate();
			LoggerSingleton.getIstance().log("admin " + CurrentAdminSingleton.getIstance().getAdmin().getUsername() + " closed elezione " + titolo);
			return true;
		}catch(SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteElezione(String titolo) throws SQLException {
		String command = "delete from elezione where titolo = ?;";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, titolo);
		
		try{
			updatedCmd.executeUpdate();
			LoggerSingleton.getIstance().log("admin " + CurrentAdminSingleton.getIstance().getAdmin().getUsername() + " deleted elezione " + titolo);
			return true;
		}catch(Exception ex) {
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
			List<Candidato> candidatesoflist = DaoFactorySingleton.getDaoFactory().getCandidatoDao().getCandidatesOfList(new Lista(selected.get(i), ""));
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
		boolean quo = (rs.getInt("quorum") == 1);
		int si = rs.getInt("si");
		int no = rs.getInt("no");
		int quorum = getQuorum();
		
		if(quo) {
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
		} else {
			if(si > no)
				return "si";
			else
				return "no";
		}
		
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
		
		String lista = getVincitoreCategorico(titolo);
		boolean maggAssoluta = getMajority(titolo);
		String command;
		String returnValue = "";
		int votes;
		
		command = "select candidato.nome, candidato.cognome, MAX(elezione_lista_preferenza.punteggio) from elezione, elezione_lista_preferenza, candidato, lista where elezione.id = elezione_lista_preferenza.elezione and elezione_lista_preferenza.candidato = candidato.id and titolo = ? and elezione_lista_preferenza.lista = lista.id and lista.nome = ?";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, titolo);
		updatedCmd.setString(2, lista);
		ResultSet rs = updatedCmd.executeQuery();
		rs.next();
		returnValue = lista + " - " + rs.getString("nome") + " " + rs.getString("cognome");
		
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
		String command = "SELECT maggioranza_assoluta FROM elezione where titolo = ?";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, titolo);
		ResultSet rs = updatedCmd.executeQuery();
		rs.next();
		return (rs.getInt("maggioranza_assoluta")==1);
	}
	
	private int getQuorum() throws SQLException {
		String command = "SELECT COUNT(*) from elettore";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		ResultSet rs = updatedCmd.executeQuery();
		rs.next();
		return rs.getInt("count(*)")/2;
	}

	@Override
	public List<Elezione> getElezioniAttiveUtente(String CF, String comune) throws Exception {
		List<Elezione> elezioni = new ArrayList<Elezione>();
		String command = "SELECT * FROM elezione, comune, elettore_voted where (closed = 0 or closed is null) and (elezione.comunale is null or elezione.comunale = comune.id and comune.nome = ?);";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, comune);
		ResultSet rs = updatedCmd.executeQuery();
		while(rs.next()) {
			
			switch(rs.getString("tipologia")){
			case "ordinale":
				elezioni.add(new VotoOrdinale(rs.getString("titolo"), rs.getString("descrizione"), (rs.getInt("liste") == 1)));
				break;
			case "categorico":
				elezioni.add(new VotoCategorico(rs.getString("titolo"), rs.getString("descrizione"), (rs.getInt("liste") == 1), (rs.getInt("maggioranza_assoluta") == 1)));
				break;
			case "categorico con preferenze":
				Integer comunale = rs.getInt("comunale");
				boolean com = false;
				if(comunale !=  null) {
					com = true;
				}
				elezioni.add(new VotoCategoricoConPreferenze(rs.getString("titolo"), rs.getString("descrizione"), com, (rs.getInt("maggioranza_assoluta") == 1)));
				break;
			case "referendum":
				elezioni.add(new Referendum(rs.getString("titolo"), rs.getString("descrizione"), (rs.getInt("quorum") == 1)));
				break;
			default:
				break;
			}
		}
		
		
		return elezioni;
	}

	@Override
	public Elezione getElezione(String titolo) throws SQLException {
		String command = "SELECT * FROM elezione where titolo = ?";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, titolo);
		ResultSet rs = updatedCmd.executeQuery();
		Elezione e = null;
		switch(rs.getString("tipologia")){
		case "ordinale":
			e = new VotoOrdinale(rs.getString("titolo"), rs.getString("descrizione"), (rs.getInt("liste") == 1));
			break;
		case "categorico":
			e = new VotoCategorico(rs.getString("titolo"), rs.getString("descrizione"), (rs.getInt("liste") == 1), (rs.getInt("maggioranza_assoluta") == 1));
			break;
		case "categorico con preferenze":
			Integer comunale = rs.getInt("comunale");
			boolean com = false;
			if(comunale !=  null) {
				com = true;
			}
			e = new VotoCategoricoConPreferenze(rs.getString("titolo"), rs.getString("descrizione"), com, (rs.getInt("maggioranza_assoluta") == 1));
			break;
		case "referendum":
			e = new Referendum(rs.getString("titolo"), rs.getString("descrizione"), (rs.getInt("quorum") == 1));
			break;
		default:
			break;
		}
		
		return e;
	}

	@Override
	public void setUserVoted(String titolo, String CF) throws Exception{
		int id = getElezioneId(titolo);
		String command = "insert into elettore_voted (elettore, elezione) values (?, ?)";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, CF);
		updatedCmd.setInt(2, id);
		updatedCmd.executeUpdate();
		LoggerSingleton.getIstance().log("user  " + CurrentElettoreSingleton.getIstance().getElettore().getCF() + " voted ");
	}

	@Override
	public void incrementVoterCount(String titolo) throws Exception {
		String command = "update elezione set voters_count = voters_count + 1 where titolo = ?";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, titolo);
		updatedCmd.executeUpdate();
	}

	@Override
	public void voteReferendum(String value, String titolo) throws Exception {
		int id = getElezioneId(titolo);
		if(value.equals("si")) {
			String command = "update referendum set si = si + 1 where elezione = ?";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setInt(1, id);
			updatedCmd.executeUpdate();
		}else if(value.equals("no")){
			String command = "update referendum set no = no + 1 where elezione = ?";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setInt(1, id);
			updatedCmd.executeUpdate();
		} else {
			String command = "update referendum set bianca = bianca + 1 where elezione = ?";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setInt(1, id);
			updatedCmd.executeUpdate();
		}
		
	}

	@Override
	public void voteOrdinaleListe(List<String> opzioni, String titolo) throws Exception {
		if(opzioni == null) {
			incrementVoterCount(titolo);
			return;
		}
		int id = getElezioneId(titolo);
		int value = opzioni.size();
		for(int i = 0; i < opzioni.size(); i++) {
			int listid = DaoFactorySingleton.getDaoFactory().getListaDao().getListID(opzioni.get(i));
			String command = "update elezione_lista set punteggio = punteggio + ? where elezione = ? and lista = ?";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setInt(1, value);
			updatedCmd.setInt(2, id);
			updatedCmd.setInt(3, listid);
			updatedCmd.executeUpdate();
			value = value - 1;
		}
		incrementVoterCount(titolo);
	}

	@Override
	public void voteOrdinaleCandidati(List<String> opzioni, String titolo) throws Exception {
		if(opzioni == null) {
			incrementVoterCount(titolo);
			return;
		}
		int id = getElezioneId(titolo);
		int value = opzioni.size();
		for(int i = 0; i < opzioni.size(); i++) {
			String[] splitted = opzioni.get(i).split(" ");
			int candidatoid = DaoFactorySingleton.getDaoFactory().getCandidatoDao().getCandidatoID(splitted[0], splitted[1]);
			String command = "update elezione_candidato set punteggio = punteggio + ? where elezione = ? and candidato = ?";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setInt(1, value);
			updatedCmd.setInt(2, id);
			updatedCmd.setInt(3, candidatoid);
			updatedCmd.executeUpdate();
			value = value - 1;
		}
		incrementVoterCount(titolo);
	}

	@Override
	public void voteCategoricoListe(String opzione, String titolo) throws Exception {
		if(opzione == null) {
			incrementVoterCount(titolo);
			return;
		}
		int id = getElezioneId(titolo);
		int listid = DaoFactorySingleton.getDaoFactory().getListaDao().getListID(opzione);
		String command = "update elezione_lista set punteggio = punteggio + 1 where elezione = ? and lista = ?";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setInt(1, id);
		updatedCmd.setInt(2, listid);
		updatedCmd.executeUpdate();
		incrementVoterCount(titolo);
	}

	@Override
	public void voteCategoricoCandidati(String opzione, String titolo) throws Exception {
		if(opzione == null) {
			incrementVoterCount(titolo);
			return;
		}
		int id = getElezioneId(titolo);
		String[] splitted = opzione.split(" ");
		int candidatoid = DaoFactorySingleton.getDaoFactory().getCandidatoDao().getCandidatoID(splitted[0], splitted[1]);
		String command = "update elezione_candidato set punteggio = punteggio + 1 where elezione = ? and candidato = ?";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setInt(1, id);
		updatedCmd.setInt(2, candidatoid);
		updatedCmd.executeUpdate();
		incrementVoterCount(titolo);
	}

	@Override
	public List<Elezione> getElezioniVotate(String CF) throws Exception {
		List<Elezione> elezioni = new ArrayList<Elezione>();
		String command = "SELECT * FROM elezione, elettore_voted where elettore_voted.elettore = ? and elettore_voted.elezione = elezione.id;";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		updatedCmd.setString(1, CF);
		ResultSet rs = updatedCmd.executeQuery();
		while(rs.next()) {
			switch(rs.getString("tipologia")){
			case "ordinale":
				elezioni.add(new VotoOrdinale(rs.getString("titolo"), rs.getString("descrizione"), (rs.getInt("liste") == 1)));
				break;
			case "categorico":
				elezioni.add(new VotoCategorico(rs.getString("titolo"), rs.getString("descrizione"), (rs.getInt("liste") == 1), (rs.getInt("maggioranza_assoluta") == 1)));
				break;
			case "categorico con preferenze":
				Integer comunale = rs.getInt("comunale");
				boolean com = false;
				if(comunale !=  null) {
					com = true;
				}
				elezioni.add(new VotoCategoricoConPreferenze(rs.getString("titolo"), rs.getString("descrizione"), com, (rs.getInt("maggioranza_assoluta") == 1)));
				break;
			case "referendum":
				elezioni.add(new Referendum(rs.getString("titolo"), rs.getString("descrizione"), (rs.getInt("quorum") == 1)));
				break;
			default:
				break;
			}
		}
		return elezioni;
	}

	@Override
	public void voteCategoricoPref(String lista, String titolo , List<Candidato> items) throws Exception {
		if(lista == null) {
			incrementVoterCount(titolo);
			return;
		}
		int id = getElezioneId(titolo);
		int listid = DaoFactorySingleton.getDaoFactory().getListaDao().getListID(lista);
		
		for(int i = 0; i < items.size(); i++) {
			String nome = items.get(i).getNome();
			String cognome = items.get(i).getCognome();
			int candidatoid = DaoFactorySingleton.getDaoFactory().getCandidatoDao().getCandidatoID(nome, cognome);
			String command = "update elezione_lista_preferenza set punteggio = punteggio + 1 where candidato = ? and elezione = ? and lista = ?";
			PreparedStatement updatedCmd= c.prepareStatement(command);
			updatedCmd.setInt(1, candidatoid);
			updatedCmd.setInt(2, id);
			updatedCmd.setInt(3, listid);
			updatedCmd.executeUpdate();
			incrementVoterCount(titolo);
		}
	}

	@Override
	public List<Elezione> getElezioniTerminate() throws Exception {
		List<Elezione> elezioni = new ArrayList<Elezione>();
		String command = "SELECT * FROM \"elezione\" where closed = 1;";
		PreparedStatement updatedCmd= c.prepareStatement(command);
		ResultSet rs = updatedCmd.executeQuery();
		Elezione e;
		while(rs.next()) {
			switch(rs.getString("tipologia")){
			case "ordinale":
				e = new VotoOrdinale(rs.getString("titolo"), rs.getString("descrizione"), (rs.getInt("liste") == 1));
				e.setVincitore();
				elezioni.add(e);
				break;
			case "categorico":
				e = new VotoCategorico(rs.getString("titolo"), rs.getString("descrizione"), (rs.getInt("liste") == 1), (rs.getInt("maggioranza_assoluta") == 1));
				e.setVincitore();
				elezioni.add(e);
				break;
			case "categorico con preferenze":
				Integer comunale = rs.getInt("comunale");
				boolean com = false;
				if(comunale !=  null) {
					com = true;
				}
				e = new VotoCategoricoConPreferenze(rs.getString("titolo"), rs.getString("descrizione"), com, (rs.getInt("maggioranza_assoluta") == 1));
				e.setVincitore();
				elezioni.add(e);
				break;
			case "referendum":
				e = new Referendum(rs.getString("titolo"), rs.getString("descrizione"), (rs.getInt("quorum") == 1));
				e.setVincitore();
				elezioni.add(e);
				break;
			default:
				break;
			}
		}
		
		
		return elezioni;
	}
	

}
