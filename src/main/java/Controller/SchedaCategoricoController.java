package Controller;

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

public class SchedaCategoricoController extends Controller{

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
        	CurrentElezioneSingleton.getIstance().voteCategorico(null,liste);
    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/VotazioniElettore.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    	}
    }

    @FXML
    void confermaVoto(ActionEvent event) throws Exception {
    	Alert alert = new Alert(AlertType.CONFIRMATION, "Confermare voto " + tochosefrom.getSelectionModel().getSelectedItem() + "?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
    	alert.showAndWait();
    	
    	if (alert.getResult() == ButtonType.YES) {
        	DaoFactorySingleton.getDaoFactory().getElezioneDao().setUserVoted(CurrentElezioneSingleton.getIstance().getElezione().getTitolo(), CurrentElettoreSingleton.getIstance().getElettore().getCF());
        	CurrentElezioneSingleton.getIstance().voteCategorico(tochosefrom.getSelectionModel().getSelectedItem(),liste);
    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/VotazioniElettore.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    	}
    }
    
    boolean liste;
    
	@Override
	void initialize() {
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
					tochosefrom.getItems().add(listeattive.get(i).getName());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			List<Candidato> candidatiattivi;
			try {
				candidatiattivi = DaoFactorySingleton.getDaoFactory().getCandidatoDao().getPartecipatingCandidates(titolo);
				for(int i = 0; i < candidatiattivi.size(); i++) {
					tochosefrom.getItems().add(candidatiattivi.get(i).getNome() + " " + candidatiattivi.get(i).getCognome());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
