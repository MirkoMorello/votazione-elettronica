package DAO;

import java.security.NoSuchAlgorithmException;

public class AdminDaoDemo {
	public static void main(String[] args) {
		AdminDAO admindao = new AdminDaoImpl();
		try {
			admindao.addAdmin("admin", "admin", true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
