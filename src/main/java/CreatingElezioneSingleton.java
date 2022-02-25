public class CreatingElezioneSingleton {
	
	CreatingElezioneSingleton currentElection = new CreatingElezioneSingleton();
	Elezione e;
	
	private CreatingElezioneSingleton() {
		
	}
	
	public CreatingElezioneSingleton getIstance() {
		return this.currentElection;
	}
	
	public void setElezione(Elezione e) {
		this.e = e;
	}
}
