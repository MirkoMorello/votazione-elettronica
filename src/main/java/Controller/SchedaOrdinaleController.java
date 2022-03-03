package Controller;

import java.util.ArrayList;
import java.util.List;

import Model.Candidato;
import Model.Lista;
import Singleton.*;

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

public class SchedaOrdinaleController extends Controller{

    @FXML
    private Button addSelected;

    @FXML
    private ListView<String> added;

    @FXML
    private Button deleteAll;

    @FXML
    private TextArea description;

    @FXML
    private ListView<String> notadded;

    @FXML
    private Button si;

    @FXML
    private Label title;
    
    private List<String> selectedinorder = new ArrayList<String>();
    
    int numofoptions;
    boolean liste;

    @FXML
    void addSelected(ActionEvent event) {
    	String toadd = notadded.getSelectionModel().getSelectedItem();
    	if(toadd == null) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Seleziona prima un elemento dalla lista di sinistra");
    		alert.showAndWait();
    		this.initialize();
    		return;
    	}
    	notadded.getItems().remove(toadd);
    	added.getItems().add(toadd);
    	selectedinorder.add(toadd);
    }

    @FXML
    void deleteAll(ActionEvent event) throws Exception {
    	this.initialize();
    	selectedinorder = new ArrayList<String>();
    }

    @FXML
    void si(ActionEvent event) throws Exception { //cambiare nome
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION, "Confermare invio votazione?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
    	alert.showAndWait();
    	
    	if (alert.getResult() == ButtonType.YES) {
    		if(selectedinorder.size() != numofoptions) {
        		DaoFactorySingleton.getDaoFactory().getElezioneDao().setUserVoted(CurrentElezioneSingleton.getIstance().getElezione().getTitolo(), CurrentElettoreSingleton.getIstance().getElettore().getCF());
        	}else {
        		CurrentElezioneSingleton.getIstance().voteOrdinale(selectedinorder,liste);
        		DaoFactorySingleton.getDaoFactory().getElezioneDao().setUserVoted(CurrentElezioneSingleton.getIstance().getElezione().getTitolo(), CurrentElettoreSingleton.getIstance().getElettore().getCF());
        	}
    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/VotazioniElettore.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    	}
    }

	@Override
	@FXML
	void initialize(){
		String titolo = CurrentElezioneSingleton.getIstance().getElezione().getTitolo();
		String desc = CurrentElezioneSingleton.getIstance().getElezione().getDescrizione();
		this.title.setText(titolo);
		this.description.setText(desc);
		liste = CurrentElezioneSingleton.getIstance().getElezione().getListe();
		if(liste) {
			List<Lista> listeattive;
			try {
				listeattive = DaoFactorySingleton.getDaoFactory().getListaDao().getParticipatingLists(titolo);
				for(int i = 0; i < listeattive.size(); i++) {
					notadded.getItems().add(listeattive.get(i).getName());
				}
				numofoptions = listeattive.size();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			List<Candidato> candidatiattivi;
			try {
				candidatiattivi = DaoFactorySingleton.getDaoFactory().getCandidatoDao().getPartecipatingCandidates(titolo);
				for(int i = 0; i < candidatiattivi.size(); i++) {
					notadded.getItems().add(candidatiattivi.get(i).getNome() + " " + candidatiattivi.get(i).getCognome());
				}
				numofoptions = candidatiattivi.size();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
