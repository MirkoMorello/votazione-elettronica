package Singleton;
import Model.*;

public final class CurrentAdminSingleton {
	
	private Admin admin;
	private final static CurrentAdminSingleton instance = new CurrentAdminSingleton();
	
	private CurrentAdminSingleton() {}
	
	public static CurrentAdminSingleton getIstance() {
		return instance;
	}
	
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	public Admin getAdmin() {
		return this.admin;
	}
	
	public void destroyAdmin() {
		this.admin = null;
	}
	
}
