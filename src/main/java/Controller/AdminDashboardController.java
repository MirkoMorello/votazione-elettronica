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

public class AdminDashboardController extends Controller{

	private Stage stage;
	private Scene scene;
	private Parent root;

	
	@FXML
    private Button gestamministratori;

    @FXML
    private Button gesteklettori;

    @FXML
    private Button gestliste;

    @FXML
    private Button gestvotazioni;

    @FXML
    private Label idcode;

    @FXML
    private Button logout;

    @FXML
    void Logout(ActionEvent event) throws IOException {
    	CurrentAdminSingleton.getIstance().destroyAdmin();
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AdminLogin.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Admin Login");
		stage.show();
    }

    @FXML
    void initialize() {
    	if(!CurrentAdminSingleton.getIstance().getSuperuser()){
    		gestamministratori.setDisable(true);
    	}
    	idcode.setText(String.valueOf(CurrentAdminSingleton.getIstance().getAdmin().getId()));
    	if(CurrentAdminSingleton.getIstance().getAdmin() == null) {
    		logout.fire();
    	}
    }
    

    @FXML
    void gestElettori(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ElectorsManagement.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void gestListe(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ListManagement.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Gestione liste (gruppi/partiti)");
		stage.show();
    }

    @FXML
    void gestAmministratori(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AdminManagement.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Gestione liste (gruppi/partiti)");
		stage.show();
    }

    @FXML
    void gestVotazioni(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/manageElections.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Gestione votazioni");
		stage.show();
    }
}
