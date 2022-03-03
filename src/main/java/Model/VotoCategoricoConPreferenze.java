package Model;

import java.sql.SQLException;

import Singleton.DaoFactorySingleton;

public class VotoCategoricoConPreferenze extends VotoCategorico{

	boolean comunale;
	
	public VotoCategoricoConPreferenze(String titolo, String descrizione, boolean comunale) {
		super(titolo, descrizione, true);
		this.comunale = comunale;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isComunale() {
		return comunale;
	}
	
	@Override 
	public String setVincitore() {
		try {
			vincitore = DaoFactorySingleton.getDaoFactory().getElezioneDao().getVincitoreCatConPref(titolo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vincitore;
	}

}
