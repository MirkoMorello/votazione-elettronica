package Singleton;
import java.io.File;
import java.io.IOException;
import java.util.logging.*;

public class LoggerSingleton {
	private static LoggerSingleton istance;
	Logger logger = Logger.getLogger("MyLog");  
    FileHandler fh;  
	
	private LoggerSingleton() {
		try {
			fh = new FileHandler("log.txt");
			logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	} 
	
	public static LoggerSingleton getIstance() {
		if(istance != null) {
			return istance;
		}else {
			istance = new LoggerSingleton();
			return istance;
		}
	}
	
	public void log(String message) throws IOException {

	    try {  
	        logger.info(message);  

	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    }

	}
}

