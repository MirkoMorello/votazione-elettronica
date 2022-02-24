import java.time.LocalDate;

public class Candidato {
	private String name; 
	private String surname;
	private LocalDate born;
	
	public Candidato(String name, String surname, LocalDate born) {
		this.name = name;
		this.surname = surname;
		this.born = born;
	}
	
	public String getNome() {
		return name;
	}
	
	public String getCognome() {
		return surname;
	}
	
	public LocalDate getNascita() {
		return born;
	}
}
