import java.time.LocalDate;
import java.time.Period;

public class Elettore extends Utente{
	
	private char[] CF;
	
	private LocalDate nascita;
	private String comune;
	private char[] nazione;
	public enum sesso {M, F};
	private sesso sesso;
	private boolean voto;
	
	
	public Elettore(String CF, String name, String surname, LocalDate nascita, String comune, String nazione, sesso sesso) throws Exception {
		super(name, surname);
		this.nazione = new char[2];
		this.voto = false;
		this.nascita = nascita;
		this.comune = comune;
		if(nazione.length() != 2) {
			throw new Exception("Indicare La nazione con il corrispettivo codice di due caratteri (esempio: Italia - IT)");
		}
		this.nazione[0] = nazione.charAt(0);
		this.nazione[1] = nazione.charAt(1);
		this.sesso = sesso;
		setCF(CF);
		// TODO Auto-generated constructor stub
	}

	public void vote(Elezione election, Voto voto) throws Exception {
		if (this.hasVoted(election)) {
			return;
		}
		//TODO aggiungere sistema di votazione effettivo
	}
	
	public boolean hasVoted(Elezione election){
		//TODO: aggiungere sistema di identificazione per voto gi� effettuato
		return false;
	}
	
	public boolean canVote() {
		int years = Period.between(nascita, LocalDate.now()).getYears();
		return (years >= 18);
	}
	
	
	
	public void setCF(String CF) throws Exception {
		this.CF = new char[16];
		for(int i = 0; i < CF.length(); i++) {
			this.CF[i] = CF.charAt(i);
		}
	}
	
	private boolean checkCF(String CF) {
		
		return true;
	}
	
	private void esprimiVoto() {
		this.voto = true;
	}
}
