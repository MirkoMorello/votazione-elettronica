
public abstract class Utente {
	
	private String CF;
	private String name;
	private String surname;
	
	public void setCF(String CF) throws Exception {
		if(!CF.matches(("/^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$/i")))
			throw new Exception("Formato codice fiscale non valido");
	}
	
	public String getCF() {
		return this.CF;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
}