
public class DaoFactorySingleton {
	private final static DaoFactorySingleton instance = new DaoFactorySingleton();
	private ListaDao listadao = new ListaDaoImpl();
	private CandidatoDao candidatodao = new CandidatoDaoImpl();
	private ElezioneDao elezionedao = new ElezioneDaoImpl();
	
	private DaoFactorySingleton() {
	}
	
	public static DaoFactorySingleton getDaoFactory() {
		return instance;
	}
	
	public ListaDao getListaDao() {
		return this.listadao;
	}
	
	public CandidatoDao getCandidatoDao() {
		return this.candidatodao;
	}
	
	public ElezioneDao getElezioneDao() {
		return this.elezionedao;
	}
}
