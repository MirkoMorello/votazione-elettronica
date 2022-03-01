package Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ConfirmCreationController extends Controller{

    @FXML
    private Button close;

    @FXML
    void close(ActionEvent event) {
    	Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

	@Override
	void initialize() {
		// TODO Auto-generated method stub
		
	}

}
