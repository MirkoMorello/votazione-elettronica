package DAO;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import Model.*;

public interface CandidatoDao {
	public List<Candidato> getCandidatesOfList(String listname) throws Exception;
	public List<Candidato> getAllCandidates() throws Exception;
	public boolean deleteCandidate(String name, String surname, String listname) throws Exception;
	public boolean addCandidate(String name, String surname, LocalDate nascita, String listname, String sesso) throws NoSuchAlgorithmException, Exception;
	String getCandidateList(String nome, String cognome) throws Exception;
	public Integer getCandidatoID(String nome, String cognome);
}
