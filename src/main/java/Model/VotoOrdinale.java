package Model;

public class VotoOrdinale extends Elezione{
	
	boolean liste;

	public VotoOrdinale(String titolo, String descrizione, boolean liste) {
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
		// TODO Auto-generated method stub
		return liste;
	}

}
