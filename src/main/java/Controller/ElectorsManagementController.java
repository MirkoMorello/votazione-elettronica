package Controller;
import java.util.List;

import DAO.*;
import Model.*;
import Singleton.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class ElectorsManagementController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	ElettoreDao electorDAO = new ElettoreDaoImpl();

    @FXML
    private Button add;

    @FXML
    private Button back;
    
    @FXML
    private DatePicker born;

    @FXML
    private TextField cf;

    @FXML
    private TextField comune;

    @FXML
    private Label esito;

    @FXML
    private ChoiceBox<String> gender;

    @FXML
    private Label info;

    @FXML
    private ListView<String> elettori;

    @FXML
    private Button manageElector;

    @FXML
    private TextField name;

    @FXML
    private TextField nation;

    @FXML
    private Button removeElector;

    @FXML
    private TextField surname;

    @FXML
    void addList(ActionEvent event) throws Exception {
    	
    	
    	if(!name.getText().equals("") && !surname.getText().equals("") && !nation.getText().equals("") && !comune.getText().equals("") && !cf.getText().equals("") && !gender.getValue().equals("") && !born.getValue().equals(""))  {
    		String nome = name.getText();
    		String cognome = surname.getText();
    		String nazione  = nation.getText();
    		String comun = comune.getText();
    		String genere = gender.getValue();
    		LocalDate nascita = born.getValue();
    		String CF = cf.getText();
    		char gen ='f';
    		if (genere == "Maschio") {gen = 'm';}
    		
    		try {
				Elettore toadd = new Elettore(CF, nome, cognome, nascita, comun, nazione, 'm');
				if(!electorDAO.addElettore(toadd, "")){
					esito.setText("l'elettore " +CF+" è già presente nel sistema");
					return;
				};
	    		this.initialize();
	        	esito.setText("aggiunta elettore " + CF);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	} else
			try {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Devi inserire tutti i campi per poter aggiungere l'elettore");
					alert.showAndWait();
					this.initialize();
			} catch (Exception e) {
				e.printStackTrace();
			}
    }

    @FXML
    void back(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AdminDashboard.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void manageElector(ActionEvent event) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getClassLoader().getResource("fxml/ElectorsModify.fxml"));
    	Parent tableViewParent = loader.load();
    	
    	Scene tableViewScene = new Scene(tableViewParent);
    	
    	ElectorsModifyController controller = loader.getController();
    	controller.initialize(electorDAO.getElettore(elettori.getSelectionModel().getSelectedItem()));
    	

		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(tableViewScene);
		window.show();
    }

    @FXML
    void removeElector(ActionEvent event) throws Exception {
    	String name = elettori.getSelectionModel().getSelectedItem();
    	if (!electorDAO.deleteElettore(name)) {
    		info.setText("impossibile rimuovere " + name);
        	return;
    	}
    	elettori.getItems().remove(name);
    	info.setText("rimosso elettore " + name);
    	return;
    }
    
    @FXML
    void initialize() throws Exception {
    	info.setText("");
    	esito.setText("");
    	List<Elettore> electors = electorDAO.getAllElettori();
    	for(int i = 0; i < electors.size(); i++) {
    		if(!elettori.getItems().contains(electors.get(i).getCF())) {
    			elettori.getItems().add(electors.get(i).getCF());
    		}
    	}
    	if (!gender.getItems().contains("Maschio")) {
        	gender.getItems().add("Maschio");
        	gender.getItems().add("Femmina");
    	}

    }

}
