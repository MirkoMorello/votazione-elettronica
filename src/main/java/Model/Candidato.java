package Model;

import java.time.LocalDate;

public class Candidato {
	private String name; 
	private String surname;
	private LocalDate born;
	private String sesso;
	private Lista list;
	
	public Candidato(String name, String surname, LocalDate born, String sesso, Lista list) {
		this.name = name;
		this.surname = surname;
		this.born = born;
		this.sesso = sesso;
		this.list = list;
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
	
	public Lista getList() {
		return list;
	}
	
	@Override
	public String toString() {
		return name + " " + surname;
	}
}
