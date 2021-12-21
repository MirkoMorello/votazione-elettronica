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
      
      Elettore e = new Elettore("MRLMRK97H14I441R", "Mirko", "Morello", LocalDate.of(1997, 6, 14), "Saronno", "Italia", 'M');
      
      //elettoreDao.addElettore(e, "password");
      
      Elettore result = elettoreDao.getElettore(e.getCF().toCharArray());
      
      System.out.println(result.getCF());
      
   }
}