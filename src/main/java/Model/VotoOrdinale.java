package Model;

import java.sql.SQLException;

import Singleton.DaoFactorySingleton;

public class VotoOrdinale extends Elezione{
	
	boolean liste;

	public VotoOrdinale(String titolo, String descrizione, boolean liste) {
		super(titolo, descrizione);
		this.liste = liste;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String setVincitore() {
		try {
			vincitore = DaoFactorySingleton.getDaoFactory().getElezioneDao().getVincitoreOrdinale(titolo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vincitore;
	}

	@Override
	public boolean getListe() {
		// TODO Auto-generated method stub
		return liste;
	}

}
