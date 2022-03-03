package Model;

import java.sql.SQLException;

import Singleton.DaoFactorySingleton;

public class VotoCategorico extends Elezione{

	boolean liste;
	
	public VotoCategorico(String titolo, String descrizione, boolean liste) {
		super(titolo, descrizione);
		this.liste = liste;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String setVincitore() {
		try {
			vincitore = DaoFactorySingleton.getDaoFactory().getElezioneDao().getVincitoreCategorico(titolo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vincitore;
	}

	@Override
	public boolean getListe() {
		return true;
	}

}
