import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/*
 * Questa è la componente Controller del design pattern MVC
 * Questa classe prende l'input dell'utente e aggiorna la vista a seconda di ciò che l'utente ha inserito
 * Questa è una classe client del design pattern DAO
 */

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Password;

    @FXML
    private TextField Username;

    @FXML
    private Button loginBtn;

    @FXML
    private Label message;

    @FXML
    void login(ActionEvent event) {
    	String username = Username.getText();
    	String password = Password.getText();
    	ElettoreDao elettoreDao = new ElettoreDaoImpl();
        
        try {
			if(elettoreDao.loginElettore(username.toCharArray(), password) != null) {
				message.setText("login effettuata");
			}else {
				message.setText("errore login");
			}
		} catch (Exception e) {
			message.setText("unable to query the db");
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        assert Password != null : "fx:id=\"Password\" was not injected: check your FXML file 'ex.fxml'.";
        assert Username != null : "fx:id=\"Username\" was not injected: check your FXML file 'ex.fxml'.";
        assert loginBtn != null : "fx:id=\"loginBtn\" was not injected: check your FXML file 'ex.fxml'.";
        assert message != null : "fx:id=\"message\" was not injected: check your FXML file 'ex.fxml'.";

    }

}
