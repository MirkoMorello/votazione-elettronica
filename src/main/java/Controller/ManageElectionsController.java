package Controller;

import DAO.*;
import Model.*;
import Singleton.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ManageElectionsController {
	private Stage stage;
	private Scene scene;
	private Parent root;

    @FXML
    private Button back;

    @FXML
    private Button creavotazione;

    @FXML
    private ListView<String> currentelections;

    @FXML
    private Label listname;

    @FXML
    private Button remove;

    @FXML
    private Button termina;

    @FXML
    private Button votazioniconcluse;
    
    @FXML
    private Button refresh;

    @FXML
    void back(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AdminDashBoard.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void createElection(ActionEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SessioneVoto1.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Configurazione sessione di voto");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void removeElection(ActionEvent event) throws SQLException {
    	String selected = currentelections.getSelectionModel().getSelectedItem();
    	if(selected == null) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Seleziona prima una votazione dalla lista");
    		alert.showAndWait();
    		this.initialize();
    		return;
    	}
    	DaoFactorySingleton.getDaoFactory().getElezioneDao().deleteElezione(selected);
    	currentelections.getItems().remove(selected);
    	this.initialize();
    }

    @FXML
    void terminaElezione(ActionEvent event) throws Exception {
    	String titolo = currentelections.getSelectionModel().getSelectedItem();
    	if (titolo == null || titolo.equals("")) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Seleziona prima una votazione dalla lista");
    		alert.showAndWait();
    		this.initialize();
    		return;
    	}
    	DaoFactorySingleton.getDaoFactory().getElezioneDao().closeElezione(titolo);
    	this.initialize();
    }

    @FXML
    void votazioniConcluse(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/VotazioniConcluse.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    @FXML
    void refresh(ActionEvent event) throws SQLException {
    	this.initialize();
    }
    
    @FXML
    void initialize() throws SQLException {
    	List<Elezione> elezioni = DaoFactorySingleton.getDaoFactory().getElezioneDao().getElezioniAttive();
    	for(int i = 0; i < elezioni.size(); i++) {
    		if(!currentelections.getItems().contains(elezioni.get(i).getTitolo()))
    		currentelections.getItems().add(elezioni.get(i).getTitolo());
    	}
    }

}
