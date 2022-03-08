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
	
	public boolean equals(Candidato tocompare) {
		if(!tocompare.getNome().equals(this.name)){
			System.out.println("name "+tocompare.getNome()+" "+this.name);
			return false;
		}
		if(!tocompare.getCognome().equals(this.surname)){
			System.out.println("surname");
			return false;
		}
		if(!tocompare.getNascita().isEqual(this.born)){
			System.out.println("born "+tocompare.getNascita().toString()+" "+this.born.toString());
			return false;
		}
		if(!tocompare.getSesso().equals(this.sesso)){
			System.out.println("sesso");
			return false;
		}
		if(!tocompare.getList().equals(this.list)){
			System.out.println("list");
			return false;
		}
		System.out.println("got false");
		return true;
	}
}
