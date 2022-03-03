package Model;

import java.sql.SQLException;

import Singleton.DaoFactorySingleton;

public class Referendum extends Elezione{

	
	public Referendum(String titolo, String descrizione) {
		super(titolo, descrizione);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String setVincitore() {
		try {
			vincitore = DaoFactorySingleton.getDaoFactory().getElezioneDao().getVincitoreReferendum(titolo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vincitore;
	}

	@Override
	public boolean getListe() {
		return false;
	}
	
}
