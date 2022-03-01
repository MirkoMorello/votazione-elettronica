package Controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import Singleton.*;
import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class AdminManagementController extends Controller{
	@FXML
    private Button add;

    @FXML
    private Button back;

    @FXML
    private TextField password;

    @FXML
    private TextField username;
    
    @FXML
    private CheckBox superuser;
    
    @FXML
    private ListView<String> admins;

    @FXML
    void addAdmin(ActionEvent event) {
    	String user = username.getText();
    	String pass = password.getText();
    	if(user.equals("")) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Inserire valore per username");
    		alert.showAndWait();
    		this.initialize();
    		return;
    	}
    	if(pass.equals("")) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Inserire valore per password");
    		alert.showAndWait();
    		this.initialize();
    		return;
    	}
    	
    	try {
			DaoFactorySingleton.getDaoFactory().getAdminDao().addAdmin(username.getText(), password.getText(), superuser.isSelected());
			this.initialize();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Errore inserimento admin");
    		alert.showAndWait();
    		this.initialize();
			e.printStackTrace();
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
    @Override
    void initialize() {
    	List<Admin> adminlist;
		try {
			adminlist = DaoFactorySingleton.getDaoFactory().getAdminDao().getAllAdmins();
			if(adminlist != null) {
				for(int i = 0; i < adminlist.size(); i++) {
					if(!admins.getItems().contains(adminlist.get(i).getUsername()))
						admins.getItems().add(adminlist.get(i).getUsername());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
