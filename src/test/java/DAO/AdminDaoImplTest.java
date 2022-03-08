package DAO;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import Model.*;

public class AdminDaoImplTest {

	@Test
	public void testGetAdmin() throws NoSuchAlgorithmException, Exception {
		Admin a = new Admin("gabe", true);
		AdminDaoImpl dao = new AdminDaoImpl();
		dao.addAdmin(a, "password");
		System.out.print(a.getUsername());
		assertTrue(a.equals(dao.getAdmin("gabe")));
		dao.deleteAdmin("gabe");
	}

	@Test
	public void testDeleteAdmin() throws NoSuchAlgorithmException, Exception {
		Admin a = new Admin("gabe", true);
		AdminDaoImpl dao = new AdminDaoImpl();
		dao.addAdmin(a, "password");
		assertTrue(dao.deleteAdmin("gabe"));
	}

	@Test
	public void testAddAdmin() throws NoSuchAlgorithmException, Exception {
		Admin a = new Admin("gabe", true);
		AdminDaoImpl dao = new AdminDaoImpl();
		assertTrue(dao.addAdmin(a, "password"));
		dao.deleteAdmin("gabe");
	}

	@Test
	public void testLoginAdmin() throws NoSuchAlgorithmException, Exception {
		Admin a = new Admin("gabe", true);
		AdminDaoImpl dao = new AdminDaoImpl();
		dao.addAdmin(a, "password");
		assertTrue(a.equals(dao.loginAdmin("gabe", "password")));
		dao.deleteAdmin("gabe");
	}

	@Test
	public void testGetAllAdmins() throws NoSuchAlgorithmException, Exception {
		AdminDaoImpl dao = new AdminDaoImpl();
		assertNotNull(dao.getAllAdmins());
		assertTrue(dao.getAllAdmins().get(0) instanceof Admin);
	}

	@Test
	public void testIsAdmin1() throws NoSuchAlgorithmException, Exception {
		Admin a = new Admin("gabe", true);
		AdminDaoImpl dao = new AdminDaoImpl();
		dao.addAdmin(a, "password");
		assertTrue(dao.isAdmin("gabe"));
		dao.deleteAdmin("gabe");
	}
	
	@Test
	public void testIsAdmin2() throws NoSuchAlgorithmException, Exception {
		Admin a = new Admin("gabe", true);
		AdminDaoImpl dao = new AdminDaoImpl();
		dao.addAdmin(a, "password");
		assertFalse(dao.isAdmin("NotAnAdmin"));
		dao.deleteAdmin("gabe");
	}
	
	@Test
	public void testIsAdmin3() throws NoSuchAlgorithmException, Exception {
		Admin a = new Admin("gabe", false);
		AdminDaoImpl dao = new AdminDaoImpl();
		dao.addAdmin(a, "password");
		assertFalse(dao.isAdmin("gabe"));
		dao.deleteAdmin("gabe");
	}

}
