package Controller;

import DAO.*;
import Model.*;
import Singleton.*;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SessioneVotoControllerComunale extends Controller{

    @FXML
    private ComboBox<String> comunale;

    @FXML
    private Button continua;
    
    @FXML
    private TextField comune;
    
    @FXML
    private TextField popolazione;

    @FXML
    void continua(ActionEvent event) throws IOException, NumberFormatException, SQLException {
    	if(comunale.getValue().equals("si")) {
    		CreatingElezioneSingleton.getIstance().setComunale(comune.getText(), Integer.parseInt(popolazione.getText()));
    	}
    	Parent root = FXMLLoader.load(getClass().getResource("fxml/SessioneVoto2.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

	@Override
	@FXML
	void initialize() {
		comunale.getItems().addAll("no", "si");
		comunale.getSelectionModel().select("no");
	}

}
