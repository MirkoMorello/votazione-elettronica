package Controller;

import DAO.*;
import Model.*;
import Singleton.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ListManagementController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;

    @FXML
    private Button add;

    @FXML
    private Button back;

    @FXML
    private TextArea desc;

    @FXML
    private ListView<Lista> liste;

    @FXML
    private TextField name;

    @FXML
    private Button remove;
    
    @FXML
    private Label esito;
    
    @FXML
    private Label removeresult;
    
    @FXML
    private Button managecand;
    
    @FXML
    private Button independentcand;

    @FXML
    void addList(ActionEvent event) throws NoSuchAlgorithmException, Exception {
    	if(!name.getText().trim().equals("")) {
    		if(desc.getText().trim().equals("")) {
    			Alert alert = new Alert(AlertType.ERROR);
        		alert.setContentText("Il campo \"descrizione\" non può essere vuoto");
        		alert.showAndWait();
        		this.initialize();
        		return;
    		}
    		String nome = name.getText();
    		DaoFactorySingleton.getDaoFactory().getListaDao().addList(new Lista(name.getText(), desc.getText()));
    		this.initialize();
        	esito.setText("aggiunta lista " + nome);
    	}else {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Il campo \"nome\" non può essere vuoto");
    		alert.showAndWait();
    		this.initialize();
    	}
    	
    }

    @FXML
    void back(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AdminDashBoard.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Admin Dashboard");
		stage.show();
    }

    @FXML
    void removeList(ActionEvent event) throws Exception {
    	Lista list = liste.getSelectionModel().getSelectedItem();
    	if(list == null) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Seleziona prima una lista (gruppo/partito) da rimuovere dalla lista");
    		alert.showAndWait();
    		this.initialize();
    		return;
    	}
    	DaoFactorySingleton.getDaoFactory().getListaDao().deleteList(list);
    	liste.getItems().remove(list);
    	this.initialize();
    	removeresult.setText("rimossa lista " + name);
    }
    
    @FXML
    void manageCandidates(ActionEvent event) throws Exception {
    	Lista list = liste.getSelectionModel().getSelectedItem();
    	if(list == null) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Seleziona prima una lista (gruppo/partito) dalla lista");
    		alert.showAndWait();
    		this.initialize();
    		return;
    	}
    	CurrentListSingleton.getIstance().setList(list);
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ManageCandidates.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Gestione candidati lista " + list.getName());
		stage.show();
    }
    
    @FXML
    void manageIndependentCandidates(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ManageIndependentCandidates.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Gestione candidati indipendenti");
		stage.show();
    }
    
    @FXML
    void initialize() throws Exception {
    	removeresult.setText("");
    	esito.setText("");
    	liste.getItems().clear();
    	List<Lista> lists = DaoFactorySingleton.getDaoFactory().getListaDao().getAllLists();
    	for(int i = 0; i < lists.size(); i++) {
    		liste.getItems().add(lists.get(i));
    	}
    }

}
