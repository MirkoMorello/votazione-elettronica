package Controller;
import DAO.*;
import Model.*;
import Singleton.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ManageIndependentCandidatesController extends Controller{

    @FXML
    private Button add;

    @FXML
    private Button back;

    @FXML
    private ListView<String> currentcandidates;

    @FXML
    private DatePicker date;

    @FXML
    private TextField name;

    @FXML
    private Button remove;

    @FXML
    private ComboBox<String> sesso;

    @FXML
    private TextField surname;

    @FXML
    void add(ActionEvent event) throws NoSuchAlgorithmException, Exception {
    	LocalDate nascita = date.getValue();
    	String nome = name.getText().trim();
    	String cognome = surname.getText().trim();
    	String sex = sesso.getValue();
    	if(nome.equals("")||cognome.equals("")||sex.equals("")||nascita == null) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Errore nell'inserimento dei dati");
    		alert.showAndWait();
    		this.initialize();
    		return;
    	}
    	String lista = null;
    	DaoFactorySingleton.getDaoFactory().getCandidatoDao().addCandidate(nome, cognome, nascita, null, sex);
    	this.initialize();
    }

    @FXML
    void back(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ListManagement.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Gestione liste (gruppi/partiti)");
		stage.show();
    }

    @FXML
    void removeCandidate(ActionEvent event) throws Exception {
    	String namesurname = currentcandidates.getSelectionModel().getSelectedItem();
    	if(namesurname == null) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Seleziona prima un candidato da rimuovere dalla lista");
    		alert.showAndWait();
    		this.initialize();
    		return;
    	}
    	String[] splitted = namesurname.split("\\s+");
    	String name = splitted[0];
    	String surname = splitted[1];
    	DaoFactorySingleton.getDaoFactory().getCandidatoDao().deleteCandidate(name, surname, null);
    	currentcandidates.getItems().remove(name + " " + surname);
    	this.initialize();
    }
    
    @Override
    @FXML
    void initialize() {
    	
    	List<Candidato> candidates;
		try {
			candidates = DaoFactorySingleton.getDaoFactory().getCandidatoDao().getCandidatesOfList(null);
			for(int i = 0; i < candidates.size(); i++) {
	    		if(!currentcandidates.getItems().contains(candidates.get(i).getNome() +" " + candidates.get(i).getCognome()))
	    		currentcandidates.getItems().add(candidates.get(i).getNome() + " " + candidates.get(i).getCognome());
	    	}
	    	sesso.getSelectionModel().select("M");
	    	if(!sesso.getItems().contains("M"))
	    		sesso.getItems().add("M");
	    	
	    	if(!sesso.getItems().contains("F"))
	    		sesso.getItems().add("F");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
