package Controller;
import DAO.*;
import Model.*;
import Singleton.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    private Button activate;
	
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
    void loginBtn(ActionEvent event) throws NumberFormatException, Exception {
    	String user = username.getText();
    	String pwd = password.getText();
    	ElettoreDao elettoreDao = new ElettoreDaoImpl();
    	Elettore logged = elettoreDao.loginElettore(user, pwd);
    	
        try {
			if(logged != null) {
				CurrentElettoreSingleton.getIstance().setElettore(logged);
				System.out.println("login effettuata");
				logresult.setText("login effettuata");
				
				Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ElettoreDashboard.fxml"));
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}else {
				logresult.setText("errore login, utenza non trovata oppure non attiva");
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
    
    @FXML
    void activate(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/activateElector.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

}
