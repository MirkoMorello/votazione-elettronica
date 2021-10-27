import java.time.LocalTime;

public abstract class Elezione {
	private String id;
	private LocalTime expire;
	
	public LocalTime getExpirationDate(){
		return this.expire;
	}
}
