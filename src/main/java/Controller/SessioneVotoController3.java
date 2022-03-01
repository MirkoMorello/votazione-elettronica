package Controller;
import DAO.*;
import Model.*;
import Singleton.*;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class SessioneVotoController3 extends Controller{

    @FXML
    private Button continua;
    
    @FXML
    private ComboBox<String> electiondomain;

    @FXML
    void continua(ActionEvent event) throws IOException {
    	boolean liste = electiondomain.getValue().equals("liste");
    	CreatingElezioneSingleton.getIstance().setListe(liste);
    	Parent root = FXMLLoader.load(getClass().getResource("fxml/SessioneVoto4.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

	@Override
	@FXML
	void initialize() {
		electiondomain.getSelectionModel().select("liste");
		electiondomain.getItems().addAll("liste", "candidati");
	}

}
