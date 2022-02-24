import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface ListaDao {
	public void connect();
	public List<Lista> getAllLists() throws Exception;
	public Lista getList(String name) throws Exception;
	public boolean deleteList(String name) throws Exception;
	public boolean addList(String name, String decsription) throws NoSuchAlgorithmException, Exception;
}
