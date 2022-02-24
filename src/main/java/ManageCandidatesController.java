import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    private ListView<String> currentcandidates;

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
    void back(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("fxml/ListManagement.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    @FXML
    void initialize() {
    	System.out.println("i sos" + String.valueOf(CurrentListSingleton.getIstance().getList().getName()));
    	listname.setText(String.valueOf(CurrentListSingleton.getIstance().getList().getName()));
    }

}
