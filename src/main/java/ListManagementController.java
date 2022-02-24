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
	
	ListaDao ld = new ListaDaoImpl();
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
    private ListView<String> liste;

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
    void addList(ActionEvent event) throws NoSuchAlgorithmException, Exception {
    	if(!name.getText().equals("")) {
    		String nome = name.getText();
    		ld.addList(name.getText(), desc.getText());
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
    	Parent root = FXMLLoader.load(getClass().getResource("fxml/AdminDashBoard.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void removeList(ActionEvent event) throws Exception {
    	String name = liste.getSelectionModel().getSelectedItem();
    	ld.deleteList(name);
    	liste.getItems().remove(name);
    	this.initialize();
    	removeresult.setText("rimoaa lista " + name);
    }
    
    @FXML
    void manageCandidates(ActionEvent event) throws Exception {
    	String name = liste.getSelectionModel().getSelectedItem();
    	Lista list = ld.getList(name);
    	CurrentListSingleton.getIstance().setList(list.getName(), list.getDesc());
    	Parent root = FXMLLoader.load(getClass().getResource("fxml/ManageCandidates.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    
    @FXML
    void initialize() throws Exception {
    	removeresult.setText("");
    	esito.setText("");
    	List<Lista> lists = ld.getAllLists();
    	for(int i = 0; i < lists.size(); i++) {
    		if(!liste.getItems().contains(lists.get(i).getName()))
    		liste.getItems().add(lists.get(i).getName());
    	}
    }

}
