package Controller;
import DAO.*;
import Model.*;
import Singleton.*;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class SessioneVotoController2 extends Controller{

    @FXML
    private Button continua;

    @FXML
    private ComboBox<String> tipologiavincitore;

    @FXML
    void continua(ActionEvent event) throws IOException, SQLException {
    	
    	if(tipologiavincitore.getValue() != "") {
    		CreatingElezioneSingleton.getIstance().setSelezione(tipologiavincitore.getValue());
    	}else {
    		//show error
    	}
    	
    	if(CreatingElezioneSingleton.getIstance().getTipo().equals("referendum")) {
    		CreatingElezioneSingleton.getIstance().pushReferendum();
    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ConfirmCreation.fxml"));
        	DaoFactorySingleton.getDaoFactory().getElezioneDao().pushReferendum();
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    	}else if(CreatingElezioneSingleton.getIstance().getTipo().equals("categorico con preferenze")) {
    		CreatingElezioneSingleton.getIstance().pushReferendum();
    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SessioneVoto4.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    	}else {
    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SessioneVoto3.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    	}
    }

	@Override
	@FXML
	void initialize() {
		if(CreatingElezioneSingleton.getIstance().getTipo().equals("referendum")) {
			tipologiavincitore.getItems().addAll("con quorum", "senza quorum");
			tipologiavincitore.getSelectionModel().select("senza quorum");
		}else {
			tipologiavincitore.getItems().addAll("maggioranza assoluta", "maggioranza semplice");
			tipologiavincitore.getSelectionModel().select("maggioranza semplice");
		}
	}

}
