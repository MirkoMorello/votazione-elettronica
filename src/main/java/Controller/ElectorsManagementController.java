package Controller;
import java.util.List;
import java.util.Objects;

import DAO.*;
import Model.*;
import Singleton.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Button manageElector;

    @FXML
    private Button removeElector;
    
    @FXML
    private TableView<Elettore> elettori;

    @FXML
    private TableColumn<Elettore, LocalDate> bornTable;

    @FXML
    private TableColumn<Elettore, String> cfTable;

    @FXML
    private TableColumn<Elettore, String> comuneTable;
    
    @FXML
    private TableColumn<Elettore, String> genderTable;
    
    @FXML
    private TableColumn<Elettore, String> nameTable;

    @FXML
    private TableColumn<Elettore, String> nationTable;
    
    @FXML
    private TableColumn<Elettore, String> surnameTable;

    @FXML
    private Label esito;

    @FXML
    private Label info;
    
    @FXML
    private TextField cf;
    
    @FXML
    private TextField name;
    
    @FXML
    private TextField surname;
    
    @FXML
    private TextField nation;
    
    @FXML
    private TextField comune;
    
    @FXML
    private ChoiceBox<String> gender;
    
    @FXML
    private DatePicker born;

    @FXML
    void addList(ActionEvent event) throws Exception {
    	
    	
    	if(!name.getText().equals("") && !surname.getText().equals("") && !nation.getText().equals("") && !comune.getText().equals("") && !cf.getText().equals("") && Objects.nonNull(gender.getValue()) && Objects.nonNull(born.getValue()))  {
    		String nome = name.getText();
    		String cognome = surname.getText();
    		String nazione  = nation.getText();
    		String comun = comune.getText();
    		String genere = gender.getValue();
    		LocalDate nascita = born.getValue();
    		String CF = cf.getText();
    		char gen ='f';
    		if (genere.equals("Maschio")) {gen = 'm';}
    		
    		try {
				Elettore toadd = new Elettore(CF, nome, cognome, nascita, comun, nazione, gen);
				if(!electorDAO.addElettore(toadd, "")){
					esito.setText("l'elettore " +CF+" è già presente nel sistema");
					return;
				}
	    		this.initialize();
	        	esito.setText("aggiunta elettore " + CF);
			} catch (Exception e) {
				esito.setText("il codice fiscale " +CF+" non combacia con i dati inseriti");
				e.printStackTrace();
				return;
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
    	controller.initialize(electorDAO.getElettore(elettori.getSelectionModel().getSelectedItem().getCF()));
    	

		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(tableViewScene);
		window.show();
    }

    @FXML
    void removeElector(ActionEvent event) throws Exception {
    	Elettore e = elettori.getSelectionModel().getSelectedItem();
    	if (!electorDAO.deleteElettore(e.getCF())) {
    		info.setText("impossibile rimuovere " + elettori.getSelectionModel().getSelectedItem().getCF());
        	return;
    	}
    	elettori.getItems().remove(elettori.getSelectionModel().getSelectedItem());
    	info.setText("rimosso elettore " + elettori.getSelectionModel().getSelectedItem().getCF());
    	return;
    }
    
    @FXML
    void initialize() throws Exception {
    	info.setText("");
    	esito.setText("");
    	cfTable.setCellValueFactory(new PropertyValueFactory<Elettore, String>("CF"));
        nameTable.setCellValueFactory(new PropertyValueFactory<Elettore, String>("name"));
        surnameTable.setCellValueFactory(new PropertyValueFactory<Elettore, String>("surname"));
        bornTable.setCellValueFactory(new PropertyValueFactory<Elettore, LocalDate>("nascita"));
        comuneTable.setCellValueFactory(new PropertyValueFactory<Elettore, String>("comune"));
        genderTable.setCellValueFactory(new PropertyValueFactory<Elettore, String>("sesso"));
        nationTable.setCellValueFactory(new PropertyValueFactory<Elettore, String>("nazione"));
        
    	elettori.setItems(FXCollections.observableList(electorDAO.getAllElettori()));

    	if (!gender.getItems().contains("Maschio")) {
        	gender.getItems().add("Maschio");
        	gender.getItems().add("Femmina");
    	}

    }

}
