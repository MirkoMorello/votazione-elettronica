package DAO;

import Model.*;
import Singleton.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public interface ListaDao {
	public List<Lista> getAllLists() throws Exception;
	public Lista getList(String name) throws Exception;
	public boolean deleteList(String name) throws Exception;
	public boolean addList(String name, String decsription) throws NoSuchAlgorithmException, Exception;
	public Integer getListID(String listname);
}
