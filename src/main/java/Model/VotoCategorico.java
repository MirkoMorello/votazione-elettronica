package Model;

import java.sql.SQLException;

import Singleton.DaoFactorySingleton;

public class VotoCategorico extends Elezione{

	boolean liste;
	boolean maggassoluta;
	
	public VotoCategorico(String titolo, String descrizione, boolean liste, boolean maggassoluta) {
		super(titolo, descrizione);
		this.liste = liste;
		this.maggassoluta = maggassoluta;
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
		return liste;
	}
	
	@Override
	public boolean getMaggAssoluta() {
		return maggassoluta;
	}

	@Override
	public String getTipo() {
		return "categorico";
	}

}
