package Singleton;
import Model.*;

public class CurrentListSingleton {
	private Lista list;
	private final static CurrentListSingleton instance = new CurrentListSingleton();
	
	private CurrentListSingleton() {}
	
	public static CurrentListSingleton getIstance() {
		return instance;
	}
	
	public void setList(String nome, String descrizione) {
		this.list = new Lista(nome, descrizione);
	}
	
	public Lista getList() {
		return this.list;
	}
	
	public void destroyList() {
		this.list = null;
	}
}
