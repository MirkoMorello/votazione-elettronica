import java.time.LocalDate;
import java.time.Period;

public class Elettore extends Utente{
	/*@ public invariant 
	 @ (sesso == 'm' || sesso == 'f');
	 @ (nazione.equals("italia")) ==> comune != null;
	 @ nascita.before(LocalDate.now());
	 @ checkCF() == true
	 @*/
	
	/*
	 * Gli attributi nome e cognome sono contenuti nella superclasse "Utente"
	 */
	
	private /*@ spec_public @*/ char[] CF;
	private /*@ spec_public @*/ LocalDate nascita;
	private /*@ spec_public @*/ String comune;
	private /*@ spec_public @*/ String nazione;
	private /*@ spec_public @*/ char sesso;
	private /*@ spec_public @*/ boolean voto;
	
	
	public Elettore(String CF, String name, String surname, LocalDate nascita, String comune, String nazione, char sesso) throws Exception {
		super(name, surname);
		this.voto = false;
		this.nascita = LocalDate.of(nascita.getYear(), nascita.getMonth(), nascita.getDayOfMonth());
		this.comune = comune;
		this.nazione = nazione.toLowerCase().trim();
		this.sesso = Character.toLowerCase(sesso);
		setCF(CF);
	}
	
	public /*@ pure @*/ boolean canVote() {
		int years = Period.between(nascita, LocalDate.now()).getYears();
		return (years >= 18);
	}
	
	public void setCF(String CF) throws Exception {
		if(CF.length() != 16) {
			throw new Exception("La lunghezza del codice fiscale deve essere di 16 caratteri");
		}
		this.CF = new char[16];
		for(int i = 0; i < CF.length(); i++) {
			this.CF[i] = CF.charAt(i);
		}
	}
	
	private /*@ pure @*/ boolean checkCF() {
		if(!nazione.equals("italia")) {
			if(this.CF[11] != 'Z') {
				return false;
			}
		}
		if(!Character.isAlphabetic(this.CF[11])) {
			return false;
		}
		if(!(Character.isDigit(this.CF[12]) && Character.isDigit(this.CF[13]) && Character.isDigit(this.CF[14]))){
			return false;
		}
		if(!Character.isAlphabetic(this.CF[15])) {
			return false;
		}
		return true;
		
	}
	
	//@ requires this.canVote() && this.voto == false;
	private void esprimiVoto() {
		this.voto = true;
	}

}