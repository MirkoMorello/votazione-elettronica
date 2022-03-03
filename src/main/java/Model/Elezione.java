package Model;

import java.time.LocalTime;

public abstract class Elezione {
	String titolo;
	String descrizione;
	boolean liste;
	
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
	
	public abstract String getVincitore();
	
	public abstract boolean getListe();
}
