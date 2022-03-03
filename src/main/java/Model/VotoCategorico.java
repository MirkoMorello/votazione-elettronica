package Model;

public class VotoCategorico extends Elezione{

	boolean liste;
	
	public VotoCategorico(String titolo, String descrizione, boolean liste) {
		super(titolo, descrizione);
		this.liste = liste;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getVincitore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getListe() {
		return true;
	}

}
