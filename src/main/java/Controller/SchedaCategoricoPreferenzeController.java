package Controller;

import java.io.IOException;
import java.util.List;

import Model.Candidato;
import Model.Lista;
import Singleton.CurrentElettoreSingleton;
import Singleton.CurrentElezioneSingleton;
import Singleton.DaoFactorySingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SchedaCategoricoPreferenzeController extends Controller{

    @FXML
    private Button bainca;

    @FXML
    private Button conferma;

    @FXML
    private TextArea description;

    @FXML
    private Label title;

    @FXML
    private ListView<String> tochosefrom;

    @FXML
    void confermaSchedaBianca(ActionEvent event) throws Exception {
    	Alert alert = new Alert(AlertType.CONFIRMATION, "Confermare scheda bianca?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
    	alert.showAndWait();
    	
    	if (alert.getResult() == ButtonType.YES) {
        	DaoFactorySingleton.getDaoFactory().getElezioneDao().setUserVoted(CurrentElezioneSingleton.getIstance().getElezione().getTitolo(), CurrentElettoreSingleton.getIstance().getElettore().getCF());
        	CurrentElezioneSingleton.getIstance().voteCategorico(null,true);
    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/VotazioniElettore.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    	}
    }

    @FXML
    void confermaVoto(ActionEvent event) throws IOException {
    	Alert alert = new Alert(AlertType.CONFIRMATION, "Confermare voto " + tochosefrom.getSelectionModel().getSelectedItem() + "?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
    	alert.showAndWait();
    	
    	if (alert.getResult() == ButtonType.YES) {
    		//set list 
    		String nomelista = tochosefrom.getSelectionModel().getSelectedItem();
    		if(nomelista == null) {
    			//error popup
    			alert = new Alert(AlertType.ERROR);
        		alert.setContentText("Seleziona prima una lista");
        		alert.showAndWait();
        		this.initialize();
        		return;
    		}
    		CurrentElezioneSingleton.getIstance().setNomeLista(nomelista);
    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SceltaPreferenze.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    	}
    }

	@Override
	void initialize() {
		String titolo = CurrentElezioneSingleton.getIstance().getElezione().getTitolo();
		String desc = CurrentElezioneSingleton.getIstance().getElezione().getDescrizione();
		this.title.setText(titolo);
		this.description.setText(desc);
		List<Lista> listeattive;
		try {
			listeattive = DaoFactorySingleton.getDaoFactory().getListaDao().getParticipatingLists(titolo);
			for(int i = 0; i < listeattive.size(); i++) {
				System.out.println("sos");
				tochosefrom.getItems().add(listeattive.get(i).getName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
