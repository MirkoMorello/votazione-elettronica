import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AdminDashboardController {

	private Stage stage;
	private Scene scene;
	private Parent root;

	
	@FXML
    private Button AP;

    @FXML
    private Button gesteklettori;

    @FXML
    private Button gestliste;

    @FXML
    private Button gestvotazioni;

    @FXML
    private Label idcode;

    @FXML
    private Button logout;

    @FXML
    void Logout(ActionEvent event) throws IOException {
    	CurrentAdminSingleton.getIstance().destroyAdmin();
    	Parent root = FXMLLoader.load(getClass().getResource("fxml/AdminLogin.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void initialize() {
    	idcode.setText(String.valueOf(CurrentAdminSingleton.getIstance().getAdmin().getId()));
    	if(CurrentAdminSingleton.getIstance().getAdmin() == null) {
    		logout.fire();
    	}
    }
    

    @FXML
    void gestElettori(ActionEvent event) {

    }

    @FXML
    void gestListe(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("fxml/ListManagement.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void gestPA(ActionEvent event) {

    }

    @FXML
    void gestVotazioni(ActionEvent event) {

    }
}
