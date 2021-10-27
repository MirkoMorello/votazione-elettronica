
public class Elettore extends Utente{
	
	public void Vote(Elezione election, Voto voto) throws Exception {
		if (HasVoted(election)) {
			return;
		}
		//TODO aggiungere sistema di votazione effettivo
	}
	
	private boolean HasVoted(Elezione election){
		//TODO: aggiungere sistema di identificazione per voto già effettuato
		return false;
	}
}
