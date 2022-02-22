import java.security.NoSuchAlgorithmException;

public class AdminDaoDemo {
	public static void main(String[] args) {
		AdminDAO admindao = new AdminDaoImpl();
		admindao.connect();
		try {
			admindao.addAdmin("admin", "admin");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
