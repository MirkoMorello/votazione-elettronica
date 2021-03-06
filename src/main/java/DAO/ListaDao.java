package DAO;

import Model.*;
import Singleton.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public interface ListaDao {
	public List<Lista> getAllLists() throws Exception;
	public Lista getList(String name) throws Exception;
	public boolean deleteList(Lista l) throws Exception;
	public boolean addList(Lista l) throws NoSuchAlgorithmException, Exception;
	public Integer getListID(String listname);
	public List<Lista> getParticipatingLists(String titolo) throws Exception;
	List<Lista> getParticipatingListsPref(String titolo) throws Exception;
}
