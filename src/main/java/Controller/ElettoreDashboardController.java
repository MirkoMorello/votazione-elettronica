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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ElettoreDashboardController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;

    @FXML
    private Button terminatedElection;

    @FXML
    private Button getElezioni;

    @FXML
    private Label idcode;

    @FXML
    private Button informazioniElettore;

    @FXML
    private Button logout;


    @FXML
    void Logout(ActionEvent event) throws IOException {
    	CurrentElettoreSingleton.getIstance().destroyElettore();
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ex.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    @FXML
    void initialize() throws IOException {
    	idcode.setText(CurrentElettoreSingleton.getIstance().getElettore().getSurname() +" "+ CurrentElettoreSingleton.getIstance().getElettore().getName());
    	if(CurrentElettoreSingleton.getIstance().getElettore() == null) {
    		logout.fire();
    	}
    }
    
    @FXML
    void getElezioni(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/VotazioniElettore.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
    }

    @FXML
    void terminatedElection(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/VotazioniTerminateUtente.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void informazioniElettore(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("fxml/InformazioniElettore.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

}
