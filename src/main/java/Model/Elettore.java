package Model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class Elettore{
	
	private String name;
	private String surname;
	private char[] CF;
	private LocalDate nascita;
	private String comune;
	private String nazione;
	private char sesso;
	private boolean voto;
	
	/*
	 * Questa classe � parte della componente Model del design pattern MVC
	 * Questa � l'implementazione del data transfer object del design pattern DAO di tipo Elettore
	 */
	
	
	public Elettore(String CF, String name, String surname, LocalDate nascita, String comune, String nazione, char sesso) throws Exception {
		this.name = name;
		this.surname = surname;
		this.voto = false;
		this.nascita = nascita;
		this.comune = comune;
		this.nazione = nazione.toLowerCase().trim();
		this.sesso = Character.toLowerCase(sesso);
		setCF(CF);
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	
	public boolean canVote() {
		int years = Period.between(nascita, LocalDate.now()).getYears();
		return (years >= 18);
	}
	
	public void setCF(String CF) throws Exception {
		if(CF.length() != 16) {
			throw new Exception("La lunghezza del codice fiscale deve essere di 16 caratteri");
		}
		this.CF = new char[16];
		for(int i = 0; i < CF.length(); i++) {
			this.CF[i] = CF.charAt(i);
		}
	}
	
	private boolean checkCF() {
		if(!nazione.equals("italia")) {
			if(this.CF[11] != 'Z') {
				return false;
			}
		}
		if(!Character.isAlphabetic(this.CF[11])) {
			return false;
		}
		if(!(Character.isDigit(this.CF[12]) && Character.isDigit(this.CF[13]) && Character.isDigit(this.CF[14]))){
			return false;
		}
		if(!Character.isAlphabetic(this.CF[15])) {
			return false;
		}
		return true;
		
	}
	
	public String getCF() {
		return String.valueOf(CF);
	}
	
	
	private void esprimiVoto() {
		this.voto = true;
	}
	
	public LocalDate getNascita() {
		return this.nascita;
	}
	
	public String getComune() {
		return this.comune;
	}
	
	public String getSesso() {
		return String.valueOf(sesso);
	}
	
	public String getNazione() {
		return nazione;
	}

}
