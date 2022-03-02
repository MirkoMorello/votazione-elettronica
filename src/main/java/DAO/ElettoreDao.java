package DAO;

import Model.*;
import Singleton.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public interface ElettoreDao {
	/*
	 * Questa interfaccia è parte della componente Model del design pattern MVC
	 * In questa classe abbiamo scritto l'interfaccia per poter interagire in maniera semplice con un'entità di tipo Elettore all'interno del nostro database
	 * Qui abbiamo definito tutte le varie funzioni corrispondenti alle operazioni di base effettuate in un database (CRUD)
	 * Questa classe fa parte del design pattern DAO
	 * */
	public List<Elettore> getAllElettori() throws Exception;
	public Elettore getElettore(String CF) throws Exception;
	public boolean deleteElettore(String CF) throws Exception;
	public boolean addElettore(Elettore e, String password) throws NoSuchAlgorithmException, Exception;
	public Elettore loginElettore(String CF, String password) throws Exception;
	public boolean checkRegistered(String CF);
	public boolean register(String CF, String password) throws NoSuchAlgorithmException, SQLException;
	public boolean updateElettore(Elettore e) throws Exception;
	
}
