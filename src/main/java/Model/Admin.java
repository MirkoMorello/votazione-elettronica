package Model;
public class Admin {
	private int id;
	private String username;
	
	public Admin(int id, String username) {
		this.id = id;
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public int getId() {
		return this.id;
	}
}