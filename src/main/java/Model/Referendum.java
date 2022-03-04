package Model;

import java.sql.SQLException;

import Singleton.DaoFactorySingleton;

public class Referendum extends Elezione{

	boolean quorum;
	
	public Referendum(String titolo, String descrizione, boolean quorum) {
		super(titolo, descrizione);
		this.quorum = quorum;
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
	
	@Override
	public boolean getQuorum() {
		return quorum;
	}

	@Override
	public String getTipo() {
		return "referendum";
	}
	
}
