package Model;
public class Admin {
	private int id;
	private String username;
	private boolean superuser;
	
	public Admin(String username, boolean superuser) {
		this.id = id;
		this.username = username;
		this.superuser = superuser;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getId() {
		return this.username;
	}
	
	public boolean isSuperUser() {
		return superuser;
	}
	
	public boolean equals(Admin tocompare) {
		if (tocompare.getUsername().equals(this.username) && tocompare.isSuperUser() == this.superuser) {
			return true;
		}
		return false;
	}
}
