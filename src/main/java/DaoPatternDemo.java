import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class DaoPatternDemo {
	
   public static void main(String[] args) throws Exception {
      ElettoreDao elettoreDao = new ElettoreDaoImpl();

      //print all students
      //for (Elettore student : elettore.getAllStudents()) {
      //   System.out.println("Student: [RollNo : " + student.getRollNo() + ", Name : " + student.getName() + " ]");
      //}


      //update student
      //Student student =studentDao.getAllStudents().get(0);
      //student.setName("Michael");
      //studentDao.updateStudent(student);

      //get the student
      //studentDao.getStudent(0);
      //System.out.println("Student: [RollNo : " + student.getRollNo() + ", Name : " + student.getName() + " ]");		
      
      //Create a new Elettore
      
      Elettore e = new Elettore("MRCMRL67G12I4IPP", "Marco", "Marollo", LocalDate.of(1967, 5, 12), "Sbobbio", "Italia", 'M');
      
      if(elettoreDao.addElettore(e, "password")) {
    	  System.out.println("elettore inserito correttamente");
      }else {
    	  System.out.println("elettore già presente nel DB");
      }
      
      elettoreDao.deleteElettore("MRCMRL67G12I4IPP".toCharArray());
      
      //Elettore result = elettoreDao.getElettore(e.getCF().toCharArray());
      
      //System.out.println(result.getCF());
      
   }
}