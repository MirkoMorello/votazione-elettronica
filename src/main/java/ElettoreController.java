import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/*
 * Questa è la componente Controller del design pattern MVC
 * Questa classe prende l'input dell'utente e aggiorna la vista a seconda di ciò che l'utente ha inserito
 * Questa è una classe client del design pattern DAO
 */

public class ElettoreController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Button loginBtn;

    @FXML
    private Label logresult;

    @FXML
    void login(ActionEvent event) throws NumberFormatException, Exception {
    	String user = username.getText();
    	String pwd = password.getText();
    	ElettoreDao elettoreDao = new ElettoreDaoImpl();
    	Elettore logged = elettoreDao.loginElettore(Integer.parseInt(user), pwd);
    	
        try {
			if(logged != null) {
				CurrentElettoreSingleton.getIstance().setElettore(logged);
				System.out.println("login effettuata");
				logresult.setText("login effettuata");	
				
			}else {
				logresult.setText("errore login");
			}
		} catch (Exception e) {
			System.out.println("unable to query the db");
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        assert password != null : "fx:id=\"Password\" was not injected: check your FXML file 'ex.fxml'.";
        assert username != null : "fx:id=\"Username\" was not injected: check your FXML file 'ex.fxml'.";
        assert loginBtn != null : "fx:id=\"loginBtn\" was not injected: check your FXML file 'ex.fxml'.";

    }

}
