package Model;

import java.time.LocalTime;

public abstract class Elezione {
	String titolo;
	String descrizione;
	boolean liste;
	String vincitore;
	
	public Elezione(String titolo, String descrizione) {
		this.titolo = titolo;
		this.descrizione = descrizione;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public boolean isComunale() {
		return false;
	}
	
	public abstract String setVincitore();
	
	public String getVincitore() {
		return vincitore;
	}
	
	public boolean getQuorum() {
		return false;
	}
	
	public boolean getMaggAssoluta() {
		return false;
	}
	
	public abstract String getTipo();
	
	public abstract boolean getListe();
}
