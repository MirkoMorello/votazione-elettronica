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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ManageCandidatesController {
	private Stage stage;
	private Scene scene;
	private Parent root;

    @FXML
    private Button add;

    @FXML
    private Button back;

    @FXML
    private ListView<Candidato> currentcandidates;

    @FXML
    private DatePicker date;

    @FXML
    private Label listdesc;

    @FXML
    private Label listname;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;
    
    @FXML
    private Button remove;
    
    @FXML
    private ComboBox<String> sesso;


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
    	Lista lista = CurrentListSingleton.getIstance().getList();
    	DaoFactorySingleton.getDaoFactory().getCandidatoDao().addCandidate(new Candidato(nome, cognome, nascita, sex, lista));
    	this.initialize();
    }
    
    @FXML
    void removeCandidate(ActionEvent event) throws Exception {
    	Candidato toremove = currentcandidates.getSelectionModel().getSelectedItem();
    	if(toremove == null) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Seleziona prima un candidato da rimuovere dalla lista");
    		alert.showAndWait();
    		this.initialize();
    		return;
    	}
    	DaoFactorySingleton.getDaoFactory().getCandidatoDao().deleteCandidate(toremove);
    	currentcandidates.getItems().remove(toremove);
    	this.initialize();
    }
    
    @FXML
    void initialize() throws Exception {
    	listname.setText(String.valueOf(CurrentListSingleton.getIstance().getList().getName()));
    	listdesc.setText(String.valueOf(CurrentListSingleton.getIstance().getList().getDesc()));
    	
    	List<Candidato> candidates = DaoFactorySingleton.getDaoFactory().getCandidatoDao().getCandidatesOfList(CurrentListSingleton.getIstance().getList());
    	currentcandidates.getItems().clear();
    	for(int i = 0; i < candidates.size(); i++) {
    		currentcandidates.getItems().add(candidates.get(i));
    	}
    	sesso.getSelectionModel().select("M");
    	if(!sesso.getItems().contains("M"))
    		sesso.getItems().add("M");
    	
    	if(!sesso.getItems().contains("F"))
    		sesso.getItems().add("F");
    }
    
   
}
