package Controller;
import DAO.*;
import Model.*;
import Singleton.*;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SessioneVotoController1 extends Controller{

    @FXML
    private Button continua;

    @FXML
    private TextArea descrizione;

    @FXML
    private ComboBox<String> tipologia;

    @FXML
    private TextField titolo;

    @FXML
    void continua(ActionEvent event) throws IOException {
    	
    	if(titolo.getText().trim().equals("")) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Il titolo non può essere vuoto");
    		alert.showAndWait();
    		this.initialize();
    		return;
    	}
    	if(descrizione.getText().trim().equals("")) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("La descrizione non può essere vuota");
    		alert.showAndWait();
    		this.initialize();
    		return;
    	}
    	
    	boolean result = CreatingElezioneSingleton.getIstance().setTitolo(titolo.getText());
    	if(!result) {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setContentText("Errore nell'inserimento dei dati");
        	alert.showAndWait();
        	this.initialize();
        	return;
    	}
    	CreatingElezioneSingleton.getIstance().setDesc(descrizione.getText());
    	CreatingElezioneSingleton.getIstance().setTipo(tipologia.getValue());
    	
    	if(tipologia.getValue().equals("categorico con preferenze")) {
    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SessioneVoto1.5.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}else {
    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SessioneVoto2.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    	
    	
    }
    
    @FXML
    void initialize() {
    	tipologia.getItems().addAll("ordinale", "categorico", "categorico con preferenze", "referendum");
    	tipologia.getSelectionModel().select("referendum");
    }

}
