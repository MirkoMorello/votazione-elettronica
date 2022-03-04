package Controller;

import java.util.List;

import Model.Candidato;
import Model.Elettore;
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

public class SceltaPreferenzeController extends Controller{

    @FXML
    private Button addSelected;

    @FXML
    private ListView<Candidato> added;

    @FXML
    private Button confirm;

    @FXML
    private TextArea description;

    @FXML
    private ListView<Candidato> notadded;

    @FXML
    private Button removeselected;

    @FXML
    private Label title;

    @FXML
    void addSelected(ActionEvent event) {
    	Candidato toadd = notadded.getSelectionModel().getSelectedItem();
    	if(toadd == null) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Seleziona prima un elemento dalla lista di sinistra");
    		alert.showAndWait();
    		this.initialize();
    		return;
    	}
    	notadded.getItems().remove(toadd);
    	added.getItems().add(toadd);
    }

    @FXML
    void confirm(ActionEvent event) throws Exception {
    	
    	if(CurrentElezioneSingleton.getIstance().getElezione().isComunale()) {
    		List<Candidato> selected = added.getItems();
    		if(selected.size() > 2) {
    			Alert alert = new Alert(AlertType.ERROR);
        		alert.setContentText("per elezioni comunali scegliere al massimo due candidati di genere differente");
        		alert.showAndWait();
        		this.initialize();
        		return;
    		}else if (selected.size() == 2) {
    			String sessoFirst = selected.get(0).getSesso();
    			String sessoSecond = selected.get(1).getSesso();
    			if(sessoFirst.equals(sessoSecond)) {
    				Alert alert = new Alert(AlertType.ERROR);
            		alert.setContentText("per elezioni comunali scegliere al massimo due candidati di genere differente");
            		alert.showAndWait();
            		this.initialize();
            		return;
    			}
    		}
    	}
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION, "Confermare voto ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
    	alert.showAndWait();
    	
    	if (alert.getResult() == ButtonType.YES) {
        	DaoFactorySingleton.getDaoFactory().getElezioneDao().setUserVoted(CurrentElezioneSingleton.getIstance().getElezione().getTitolo(), CurrentElettoreSingleton.getIstance().getElettore().getCF());
        	CurrentElezioneSingleton.getIstance().voteCategoricoPref(CurrentElezioneSingleton.getIstance().getNomeLista(), added.getItems());
    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/VotazioniElettore.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    	}
    }

    @FXML
    void removeSelected(ActionEvent event) {
    	Candidato toremove = added.getSelectionModel().getSelectedItem();
    	if(toremove == null) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Seleziona prima un elemento dalla lista di destra");
    		alert.showAndWait();
    		this.initialize();
    		return;
    	}
    	added.getItems().remove(toremove);
    	notadded.getItems().add(toremove);
    }

	@Override
	void initialize() {
		String titolo = CurrentElezioneSingleton.getIstance().getElezione().getTitolo();
		String desc = CurrentElezioneSingleton.getIstance().getElezione().getDescrizione();
		this.title.setText(titolo);
		this.description.setText(desc);
		List<Candidato> candidatiattivi;
		try {
			candidatiattivi = DaoFactorySingleton.getDaoFactory().getCandidatoDao().getCandidatesOfList(new Lista(CurrentElezioneSingleton.getIstance().getNomeLista(), ""));
			for(int i = 0; i < candidatiattivi.size(); i++) {
				notadded.getItems().add(candidatiattivi.get(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}