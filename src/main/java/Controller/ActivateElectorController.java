package Controller;

import DAO.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ActivateElectorController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;

    @FXML
    private Button activate;

    @FXML
    private Button back;

    @FXML
    private AnchorPane loginresult;

    @FXML
    private Label logresult;

    @FXML
    private PasswordField password1;

    @FXML
    private PasswordField password2;

    @FXML
    private TextField username;

    @FXML
    void activate(ActionEvent event) throws Exception {
    	
    	String user = username.getText();
    	String pwd1 = password1.getText();
    	String pwd2 = password2.getText();
    	ElettoreDao elettoreDao = new ElettoreDaoImpl();
    	
    	if (!pwd1.equals(pwd2)){
    		logresult.setText("Le password inserite non coincidono");
    		return;
    	}
    	
    	if (elettoreDao.getElettore(user) == null) {
    		logresult.setText("L'utente "+user+" non è registrato nel sistema");
    		return;
    	}
    	
    	if (elettoreDao.checkRegistered(user)) {
    		logresult.setText("L'utente "+user+" ha già effettuato l'attivazione");
    		return;
    	}
    	if (elettoreDao.register(user, pwd1)) {
    		logresult.setText("Utente "+user+" attivato con successo");
    		return;
    	}
    	logresult.setText("errore attivazione");
    }

    @FXML
    void back(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ex.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

}
