package Singleton;

import Model.*;
import DAO.*;
import java.sql.SQLException;
import java.util.List;

public class CreatingElezioneSingleton {
	
	private final static CreatingElezioneSingleton currentElection = new CreatingElezioneSingleton();
	static String titolo;
	String descrizione;
	static String tipologia;
	static boolean liste = true;
	boolean comunale = false;
	String comune = null;
	Integer comuneid = null;
	Integer popolazione = null;
	String selezioneVincitore;
	Elezione e;
	
	private CreatingElezioneSingleton() {
		
	}
	
	public static CreatingElezioneSingleton getIstance() {
		return currentElection;
	}
	
	public static String getTipo() {
		return tipologia;
	}
	
	public static boolean getListe() {
		return liste;
	}
	
	public void setElezione(Elezione e) {
		this.e = e;
	}
	
	public void setListe(boolean liste) {
		if(liste) {
			CreatingElezioneSingleton.liste = true;
		}else {
			CreatingElezioneSingleton.liste = false;
		}
	}
	
	public boolean setTitolo(String titolo) {
		if(titolo == null || titolo.equals("")) {
			return false;
		}
		this.titolo = titolo;
		return true;
	}
	
	public boolean setDesc(String descrizione) {
		if(descrizione == null || descrizione.equals("")) {
			return false;
		}
		this.descrizione = descrizione;
		return true;
	}
	
	public void setTipo(String tipologia) {
		this.tipologia = tipologia;
	}
	
	public void setComunale(String comune, int popolazione) throws SQLException {
		this.comunale = true;
		DaoFactorySingleton.getDaoFactory().getComuneDao().createComune(comune, popolazione);
		comuneid = DaoFactorySingleton.getDaoFactory().getComuneDao().getComuneId(comune);
	}
	
	public void setSelezione(String selezione) {
		this.selezioneVincitore = selezione;
	}
	
	public void destroy() {
		titolo = null;
		descrizione = null;
		tipologia = null;
		comunale = false;
		comune = null;
		popolazione = null;
		selezioneVincitore = null;
	}
	
	public boolean pushReferendum() {
		boolean quorum = selezioneVincitore.equals("con quorum");
		try {
			Integer i = null;
			DaoFactorySingleton.getDaoFactory().getElezioneDao().createElezione(titolo, descrizione, tipologia, false, false, quorum, i);
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}

	public boolean pushVotazione(List<String> selected) {
		boolean maggioranza = selezioneVincitore.equals("maggioranza assoluta");
		try {
			DaoFactorySingleton.getDaoFactory().getElezioneDao().createElezione(titolo, descrizione, tipologia, maggioranza, liste, false,  comuneid);
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String getTitolo() {
		return titolo;
	}
}
