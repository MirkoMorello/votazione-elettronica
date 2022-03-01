package Singleton;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionSingleton {
	private static ConnectionSingleton istance;
	private Connection c;

	private ConnectionSingleton() {
		try {
	         this.c = DriverManager
	            .getConnection("jdbc:sqlite:votazioneelettronica.db");
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	      System.out.println("Opened database successfully");
	}
	
	public static ConnectionSingleton getIstance() {
		if(istance == null) {
			istance = new ConnectionSingleton();
		} 
		return istance;
	}
	
	public Connection getConnection() {
		return this.c;
	}
}
