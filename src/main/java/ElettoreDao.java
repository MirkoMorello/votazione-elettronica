import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface ElettoreDao {
	/*
	 * Questa interfaccia è parte della componente Model del design pattern MVC
	 * In questa classe abbiamo scritto l'interfaccia per poter interagire in maniera semplice con un'entità di tipo Elettore all'interno del nostro database
	 * Qui abbiamo definito tutte le varie funzioni corrispondenti alle operazioni di base effettuate in un database (CRUD)
	 * Questa classe fa parte del design pattern DAO
	 * */
	public List<Elettore> getAllElettori() throws Exception;
	public Elettore getElettore(char[] CF) throws Exception;
	public boolean deleteElettore(char[] CF) throws Exception;
	public boolean addElettore(Elettore e, String password) throws NoSuchAlgorithmException, Exception;
	public Elettore loginElettore(char[] CF, String password) throws Exception;
	
	
	
}
