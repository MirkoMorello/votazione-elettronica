import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class DaoPatternDemo {
	
   public static void main(String[] args) throws Exception {
      ElettoreDao elettoreDao = new ElettoreDaoImpl();
      
      Elettore e = new Elettore("MRCMRL67G12I4IPP", "Marco", "Marollo", LocalDate.of(1967, 5, 12), "Sbobbio", "Italia", 'M');
      
      if(elettoreDao.addElettore(e, "password")) {
    	  System.out.println("elettore inserito correttamente");
      }else {
    	  System.out.println("elettore già presente nel DB");
      }
      //elettoreDao.deleteElettore("RSSMRA74D22A001Q".toCharArray());
      
   }
}