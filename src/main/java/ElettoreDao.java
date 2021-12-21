import java.util.List;

public interface ElettoreDao {
	public List<Elettore> getAllElettori() throws Exception;
	public Elettore getElettore(char[] CF) throws Exception;
	public boolean DeleteElettore(char[] CF);
	public boolean AddElettore(Elettore e);
}
