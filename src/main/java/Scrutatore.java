import java.time.LocalDate;

public class Scrutatore extends Utente {

	public Scrutatore(String CF, String name, String surname) throws Exception {
		super(CF, name, surname);
		// TODO Auto-generated constructor stub
	}
	
	public void userVoted(String CF, String name, String surname, LocalDate born, Elezione election) throws Exception {
		Elettore e = new Elettore(CF, name, surname, born);
		if (e.canVote()) {
			if(e.hasVoted(election)) {
				throw new Exception("User already voted");
			}
			/* TODO lo scrutatore, quando si presenta un elettore in presenza
			 * deve registrare che tale elettore ha votato, in modo tale che, se si dovesse
			 * collegare da remoto, il sistema non gli permetta di votare una seconda volta 
			 */
		} else {
			throw new Exception("User cannot vote");
		}
	}
}
