package DAO;
import Model.*;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface AdminDAO {
	public List<Admin> getAllAdmins() throws Exception;
	public Admin getAdmin(String username) throws Exception;
	public boolean deleteAdmin(String username) throws Exception;
	public boolean addAdmin(String username, String password, boolean superuser) throws NoSuchAlgorithmException, Exception;
	public Admin loginAdmin(String username, String password) throws Exception;
	public boolean isAdmin(String username);
}
