
public abstract class Utente {
	
	private /*@ non_null @*/ String name;
	private /*@ non_null @*/ String surname;
	
	public Utente(String name, String surname) throws Exception {
		this.name = name;
		this.surname = surname;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	
	
}