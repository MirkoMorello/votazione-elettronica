import java.time.LocalDate;
import java.time.Period;

public class Elettore extends Utente{
	
	private LocalDate born_on;
	
	public Elettore(String CF, String name, String surname, LocalDate born_on) throws Exception {
		super(CF, name, surname);
		this.born_on = born_on;
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
		int years = Period.between(born_on, LocalDate.now()).getYears();
		return (years >= 18);
	}
}
