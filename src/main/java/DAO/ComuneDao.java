package DAO;
import Model.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public interface ComuneDao {
	public Comune getComune(int id) throws SQLException;
	public boolean createComune(Comune c);
	public int getComuneId(String nome) throws SQLException;
}
