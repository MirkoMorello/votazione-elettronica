package DAO;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import Model.*;

public interface CandidatoDao {
	public List<Candidato> getCandidatesOfList(Lista l) throws Exception;
	public List<Candidato> getAllCandidates() throws Exception;
	public boolean deleteCandidate(Candidato candidate) throws Exception;
	public boolean addCandidate(Candidato candidate) throws NoSuchAlgorithmException, Exception;
	public Integer getCandidatoID(String nome, String cognome);
	public List<Candidato> getPartecipatingCandidates(String titolo)throws Exception;
	public String getCandidateList(String nome, String cognome) throws Exception;
}
