package Singleton;
import java.util.List;

import Model.*;
import javafx.collections.ObservableList;

public class CurrentElezioneSingleton {
	private Elezione elezione;
	private final static CurrentElezioneSingleton instance = new CurrentElezioneSingleton();
	private String nomelista;
	
	private CurrentElezioneSingleton() {}
	
	public static CurrentElezioneSingleton getIstance() {
		return instance;
	}
	
	public void setElezione(Elezione elezione) throws Exception {
		this.elezione = elezione;
	}
	
	public Elezione getElezione() {
		return this.elezione;
	}
	
	public void destroyElezione() {
		this.elezione = null;
	}

	public void voteReferendum(String value) throws Exception {
		DaoFactorySingleton.getDaoFactory().getElezioneDao().voteReferendum(value, elezione.getTitolo());
	}
	
	public void voteOrdinale(List<String> opzioni, boolean liste) throws Exception {
		if(liste) {
			DaoFactorySingleton.getDaoFactory().getElezioneDao().voteOrdinaleListe(opzioni, elezione.getTitolo());
		}else {
			DaoFactorySingleton.getDaoFactory().getElezioneDao().voteOrdinaleCandidati(opzioni, elezione.getTitolo());
		}
		
	}
	
	public void voteCategorico(String opzione, boolean liste) throws Exception {
		if(liste) {
			DaoFactorySingleton.getDaoFactory().getElezioneDao().voteCategoricoListe(opzione, elezione.getTitolo());
		}else {
			DaoFactorySingleton.getDaoFactory().getElezioneDao().voteCategoricoCandidati(opzione, elezione.getTitolo());
		}
		
	}
	
	public void setNomeLista(String nome) {
		this.nomelista = nome;
	}
	
	public String getNomeLista() {
		return nomelista;
	}

	public void voteCategoricoPref(String lista, List<Candidato> items) throws Exception {
		DaoFactorySingleton.getDaoFactory().getElezioneDao().voteCategoricoListe(lista, elezione.getTitolo());
		DaoFactorySingleton.getDaoFactory().getElezioneDao().voteCategoricoPref(lista, elezione.getTitolo(), items);
	}
}
