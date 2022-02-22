import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface AdminDAO {
	public void connect();
	public List<Admin> getAllAdmins() throws Exception;
	public Admin getAdmin(String username) throws Exception;
	public boolean deleteAdmin(String username) throws Exception;
	public boolean addAdmin(String username, String password) throws NoSuchAlgorithmException, Exception;
	public Elettore loginAdmin(String username, String password) throws Exception;
}
