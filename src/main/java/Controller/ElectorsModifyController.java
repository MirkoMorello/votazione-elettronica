package Controller;
import java.io.IOException;
import java.time.LocalDate;
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

public class ElectorsModifyController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private ElettoreDao electorDAO = new ElettoreDaoImpl();
	private Elettore selectedElettore;
	

    @FXML
    private Button apply;

    @FXML
    private Button back;

    @FXML
    private DatePicker born;

    @FXML
    private TextField comune;

    @FXML
    private ListView<String> elettori;

    @FXML
    private Label esito;

    @FXML
    private ChoiceBox<String> gender;

    @FXML
    private Label info;

    @FXML
    private TextField name;

    @FXML
    private TextField nation;

    @FXML
    private TextField surname;

    @FXML
    void apply(ActionEvent event) throws Exception {
    	if(!name.getText().equals("") && !surname.getText().equals("") && !nation.getText().equals("") && !comune.getText().equals("") && !gender.getValue().equals("") && !born.getValue().equals(""))  {
    		String nome = name.getText();
    		String cognome = surname.getText();
    		String nazione  = nation.getText();
    		String comun = comune.getText();
    		String genere = gender.getValue();
    		LocalDate nascita = born.getValue();
    		String CF = selectedElettore.getCF();
    		char gen ='f';
    		if (genere == "Maschio") {gen = 'm';}
    		
    		try {
				Elettore toadd = new Elettore(CF, nome, cognome, nascita, comun, nazione, gen);
				if(!electorDAO.updateElettore(toadd)){
					esito.setText("impossibile modificare " + CF);
					return;
				};
	    		this.initialize(toadd);
	        	esito.setText("modificato elettore " + CF);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	} else
			try {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Devi inserire tutti i campi per poter modificare l'elettore");
					alert.showAndWait();
					this.initialize(this.selectedElettore);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	return;
    }

    @FXML
    void back(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("fxml/ElectorsManagement.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    @FXML
    void initialize(Elettore selected) throws Exception {
    	this.selectedElettore = selected;
    	info.setText("Modifica in corso di "+selected.getCF());
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
