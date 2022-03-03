package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import Singleton.*;
import Model.*;

import Model.Elezione;
import Singleton.DaoFactorySingleton;
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

public class VotazioniElettoreController extends Controller{

    @FXML
    private Button back;

    @FXML
    private ListView<String> currentelections;

    @FXML
    private Label listname;

    @FXML
    private Button refresh;

    @FXML
    private Button vota;

    @FXML
    void back(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ElettoreDashboard.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
    }

    @FXML
    void refresh(ActionEvent event) {
    	this.initialize();
    }

    @FXML
    void vota(ActionEvent event) throws Exception {
    	String titolo = currentelections.getSelectionModel().getSelectedItem();
    	if(titolo == null) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Seleziona prima una votazione");
    		alert.showAndWait();
    		this.initialize();
    		return;
    	}
    	System.out.println("ehi");
    	Elezione e = DaoFactorySingleton.getDaoFactory().getElezioneDao().getElezione(titolo);
    	CurrentElezioneSingleton.getIstance().setElezione(e);
    	if(e instanceof Referendum) {
    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SchedaReferendum.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    	}
    	if(e instanceof VotoOrdinale) {
    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SchedaOrdinale.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    	}
    	if(e instanceof VotoCategorico) {
    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SchedaCategorico.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    	}
    }

	@Override
	void initialize() {
		try {
			List<Elezione> elezioni = DaoFactorySingleton.getDaoFactory().getElezioneDao().getElezioniAttiveUtente(CurrentElettoreSingleton.getIstance().getElettore().getCF(), CurrentElettoreSingleton.getIstance().getElettore().getComune());
	    	for(int i = 0; i < elezioni.size(); i++) {
	    		if(!currentelections.getItems().contains(elezioni.get(i).getTitolo()))
	    		currentelections.getItems().add(elezioni.get(i).getTitolo());
	    	}
	    	List<Elezione> votate = DaoFactorySingleton.getDaoFactory().getElezioneDao().getElezioniVotate(CurrentElettoreSingleton.getIstance().getElettore().getCF());
	    	for(int i = 0; i < votate.size(); i++) {
	    		currentelections.getItems().remove(votate.get(i).getTitolo());
	    	}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
