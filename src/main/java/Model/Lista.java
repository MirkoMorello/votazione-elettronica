package Model;

import Singleton.DaoFactorySingleton;

public class Lista {
	
	private String nome;
	private String descrizione;
	
	
	public Lista(String nome, String descrizione) {
		this.nome = nome;
		this.descrizione = descrizione;
	}
	
	public String getName() {
		return this.nome;
	}
	
	public String getDesc() {
		return this.descrizione;
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
	public int getId() {
		return DaoFactorySingleton.getDaoFactory().getListaDao().getListID(nome);
	}
	
	public boolean equals(Lista tocompare) {
		if (this.nome.equals(tocompare.getName()) && this.descrizione.equals(tocompare.getDesc())) {
			return true;
		}
		return false;
	}
}
