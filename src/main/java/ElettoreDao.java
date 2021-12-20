import java.util.List;

public interface ElettoreDao {
	public List<Elettore> getAllElettori();
	public Elettore getElettore(char[] CF);
	public boolean DeleteElettore(char[] CF);
	public boolean AddElettore(Elettore e);
}
