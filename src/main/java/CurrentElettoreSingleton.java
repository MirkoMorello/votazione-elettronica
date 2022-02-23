import java.time.LocalDate;

public final class CurrentElettoreSingleton {
	
	private Elettore elettore;
	private final static CurrentElettoreSingleton instance = new CurrentElettoreSingleton();
	
	private CurrentElettoreSingleton() {}
	
	public static CurrentElettoreSingleton getIstance() {
		return instance;
	}
	
	public void setElettore(Elettore elettore) throws Exception {
		this.elettore = elettore;
	}
	
	public Elettore getElettore() {
		return this.elettore;
	}
	
}
