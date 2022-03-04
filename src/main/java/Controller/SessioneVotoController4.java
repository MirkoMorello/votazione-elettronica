package Controller;
import DAO.*;
import Model.*;
import Singleton.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SessioneVotoController4 extends Controller{

    @FXML
    private Button addSelected;

    @FXML
    private ListView<String> nonselezionati;

    @FXML
    private ListView<String> selezionati;

    @FXML
    private Button termina;
    
    @FXML
    private Button removeSelected;

    @FXML
    void Termina(ActionEvent event) throws Exception {
    	List<String> selected = selezionati.getItems();
    	CreatingElezioneSingleton.getIstance().pushVotazione(selected);
    	if(CreatingElezioneSingleton.getTipo().equals("referendum")) {
    		DaoFactorySingleton.getDaoFactory().getElezioneDao().pushReferendum();
    	}
    	if(CreatingElezioneSingleton.getTipo().equals("categorico con preferenze")) {
    		DaoFactorySingleton.getDaoFactory().getElezioneDao().pushListeCandidati(selected);
    		DaoFactorySingleton.getDaoFactory().getElezioneDao().pushListe(selected);
    	}else if(!CreatingElezioneSingleton.getListe()) {
    		List<String> parsed = new ArrayList<String>();
    		for(int i = 0; i < selected.size(); i++) {
    			parsed.add(selected.get(i).split("-")[0]);
    		}
    		DaoFactorySingleton.getDaoFactory().getElezioneDao().pushCandidati(parsed);
    	} else {
    		DaoFactorySingleton.getDaoFactory().getElezioneDao().pushListe(selected);
    	}
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ConfirmCreation.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void addSelected(ActionEvent event) {
    	String namesurname = nonselezionati.getSelectionModel().getSelectedItem();
    	if(namesurname == null) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Nessuna lista selezionata da aggiungere");
    		alert.showAndWait();
    		this.initialize();
    		return;
    	}
    	selezionati.getItems().add(namesurname);
    	nonselezionati.getItems().remove(namesurname);
    }
    
    @FXML
    void removeSelected(ActionEvent event) {
    	String namesurname = selezionati.getSelectionModel().getSelectedItem();
    	if(namesurname == null) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Nessuna lista selezionata da rimuovere");
    		alert.showAndWait();
    		return;
    	}
    	nonselezionati.getItems().add(namesurname);
    	selezionati.getItems().remove(namesurname);
    }

	@Override
	@FXML
	void initialize(){
		String nome; 
		String cognome;
		String lista;
		boolean liste = CreatingElezioneSingleton.getListe();
		nonselezionati.getItems().clear();
		if(!liste) {
			try {
				List<Candidato> candidates = DaoFactorySingleton.getDaoFactory().getCandidatoDao().getAllCandidates();
				System.out.println(candidates.size());
				for(int i = 0; i < candidates.size(); i++) {
					nome = candidates.get(i).getNome();
					cognome = candidates.get(i).getCognome();
					System.out.println(nome + " " + cognome);
					lista = DaoFactorySingleton.getDaoFactory().getCandidatoDao().getCandidateList(nome, cognome);
		    		nonselezionati.getItems().add(nome + " " + cognome + "-" + lista);
		    	}
			}catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			List<Lista> lists;
			try {
				lists = DaoFactorySingleton.getDaoFactory().getListaDao().getAllLists();
				for(int i = 0; i < lists.size(); i++) {
		    		nonselezionati.getItems().add(lists.get(i).getName());
		    	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
		}
	}
}