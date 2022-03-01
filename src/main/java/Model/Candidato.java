package Model;

import java.time.LocalDate;

public class Candidato {
	private String name; 
	private String surname;
	private LocalDate born;
	private String sesso;
	
	public Candidato(String name, String surname, LocalDate born, String sesso) {
		this.name = name;
		this.surname = surname;
		this.born = born;
		this.sesso = sesso;
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
	
	public String getSesso() {
		return sesso;
	}
}
