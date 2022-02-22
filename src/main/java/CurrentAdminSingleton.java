
public final class CurrentAdminSingleton {
	
	private Admin admin;
	private final static CurrentAdminSingleton instance = new CurrentAdminSingleton();
	
	private CurrentAdminSingleton() {}
	
	public static CurrentAdminSingleton getIstance() {
		return instance;
	}
	
	public void setAdmin(int id, String username) {
		this.admin = new Admin(id, username);
	}
	
	public Admin getAdmin() {
		return this.admin;
	}
	
}
