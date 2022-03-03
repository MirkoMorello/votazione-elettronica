package Model;

public class Referendum extends Elezione{

	
	public Referendum(String titolo, String descrizione) {
		super(titolo, descrizione);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getVincitore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getListe() {
		return false;
	}
	
}
