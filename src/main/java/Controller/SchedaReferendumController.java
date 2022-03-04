package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import Singleton.*;

public class SchedaReferendumController extends Controller{

    @FXML
    private TextArea description;

    @FXML
    private Button no;

    @FXML
    private Button si;

    @FXML
    private Label title;
    
    @FXML
    private Button bianca;

    @FXML
    void no(ActionEvent event) throws Exception {
    	Alert alert = new Alert(AlertType.CONFIRMATION, "Confermi voto NO?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
    	alert.showAndWait();

    	if (alert.getResult() == ButtonType.YES) {
    	    CurrentElezioneSingleton.getIstance().voteReferendum("no");
    	    DaoFactorySingleton.getDaoFactory().getElezioneDao().setUserVoted(CurrentElezioneSingleton.getIstance().getElezione().getTitolo(), CurrentElettoreSingleton.getIstance().getElettore().getCF());
    	    DaoFactorySingleton.getDaoFactory().getElezioneDao().incrementVoterCount(CurrentElezioneSingleton.getIstance().getElezione().getTitolo());
    	    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/VotazioniElettore.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    	}
    }

    @FXML
    void si(ActionEvent event) throws Exception {
    	Alert alert = new Alert(AlertType.CONFIRMATION, "Confermi voto SI?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
    	alert.showAndWait();

    	if (alert.getResult() == ButtonType.YES) {
    		CurrentElezioneSingleton.getIstance().voteReferendum("si");
    		DaoFactorySingleton.getDaoFactory().getElezioneDao().setUserVoted(CurrentElezioneSingleton.getIstance().getElezione().getTitolo(), CurrentElettoreSingleton.getIstance().getElettore().getCF());
    		DaoFactorySingleton.getDaoFactory().getElezioneDao().incrementVoterCount(CurrentElezioneSingleton.getIstance().getElezione().getTitolo());
    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/VotazioniElettore.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    	}
    }
    
    @FXML
    void schedaBianca(ActionEvent event) throws Exception {
    	Alert alert = new Alert(AlertType.CONFIRMATION, "Confermi voto SI?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
    	alert.showAndWait();

    	if (alert.getResult() == ButtonType.YES) {
    		CurrentElezioneSingleton.getIstance().voteReferendum("bianca");
    		DaoFactorySingleton.getDaoFactory().getElezioneDao().setUserVoted(CurrentElezioneSingleton.getIstance().getElezione().getTitolo(), CurrentElettoreSingleton.getIstance().getElettore().getCF());
    		DaoFactorySingleton.getDaoFactory().getElezioneDao().incrementVoterCount(CurrentElezioneSingleton.getIstance().getElezione().getTitolo());
    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/VotazioniElettore.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    	}
    }

	@Override
	void initialize() {
		this.title.setText(CurrentElezioneSingleton.getIstance().getElezione().getTitolo());
		this.description.setText(CurrentElezioneSingleton.getIstance().getElezione().getDescrizione());
	}

}
