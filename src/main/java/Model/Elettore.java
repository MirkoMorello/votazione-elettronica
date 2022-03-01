package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
	 * Questa classe è parte della componente Model del design pattern MVC
	 * Questa è l'implementazione del data transfer object del design pattern DAO di tipo Elettore
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
		if(!checkCF(CF)) {
			//throw new Exception("La lunghezza del codice fiscale deve essere di 16 caratteri");
			System.out.println("error");
		}
		this.CF = new char[16];
		for(int i = 0; i < CF.length(); i++) {
			this.CF[i] = CF.charAt(i);
		}
	}
	
	private boolean checkCF(String codice) {
		//0-3 cifre
				String calculated_CF = this.surname.replaceAll("[AEIOUaeiou]", "");
				
				if (calculated_CF.length() < 3){
					calculated_CF = calculated_CF.concat(this.name.replaceAll("[AEIOUaeiou]", ""));
					if (calculated_CF.length() < 3){
						calculated_CF += "XXX";
					}
				}
				calculated_CF = calculated_CF.substring(0,3);
				
				//3-6 cifre
				String consonanti_nome = this.name.replaceAll("[AEIOUaeiou]", "");
				if (consonanti_nome.length() <= 3){
					consonanti_nome += "XXX";
					calculated_CF += consonanti_nome.substring(0,3);
				}else{
					calculated_CF += consonanti_nome.substring(0,1);
					calculated_CF += consonanti_nome.substring(2,3);
					calculated_CF += consonanti_nome.substring(3,4);
				}
				
				// 6-8 cifre
				DateFormat dateFormat = new SimpleDateFormat("yy");  
				calculated_CF += this.nascita.format(DateTimeFormatter.ofPattern("yy"));
				
				// 9 cifra
				dateFormat = new SimpleDateFormat("MM");
				String mesi = "ABCDEHLMPRST";
				String mese = mesi.substring(Integer.parseInt(this.nascita.format(DateTimeFormatter.ofPattern("MM")))-1, Integer.parseInt(this.nascita.format(DateTimeFormatter.ofPattern("MM"))));
				calculated_CF += mese;
				
				//10-11 cifre
				calculated_CF += this.nascita.format(DateTimeFormatter.ofPattern("dd"));
				
				// -----------------test e debug
				//dateFormat = new SimpleDateFormat("yyy/MMMM/dd");
				//String test = dateFormat.format(this.nascita);
				
				// -----------------
				
				//11 cifra se estero e return
				if (codice.length() < 15){
					return false;
				}
				if (!this.nazione.toUpperCase().equals("ITALIA")){
					calculated_CF += "Z";
					if (codice.startsWith(calculated_CF.toUpperCase())){
						return true;
					}
					System.out.print("nope");
					return false;
				}
				if (codice.startsWith(calculated_CF.toUpperCase())){
					return true;
				}
				return false;
		
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
