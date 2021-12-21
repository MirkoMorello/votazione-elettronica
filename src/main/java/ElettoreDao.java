import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface ElettoreDao {
	public List<Elettore> getAllElettori() throws Exception;
	public Elettore getElettore(char[] CF) throws Exception;
	public boolean deleteElettore(char[] CF) throws Exception;
	public boolean addElettore(Elettore e, String password) throws NoSuchAlgorithmException, Exception;
	
	
	
}
