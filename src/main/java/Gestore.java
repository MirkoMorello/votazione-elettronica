import java.time.LocalDate;

public class Gestore extends Elettore {

	public Gestore( String CF, String name, String surname, LocalDate nascita, String comune, String nazione, char sesso ) throws Exception {
		super(CF, name, surname, nascita, comune, nazione, sesso);
	}
	
}
