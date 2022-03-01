package Controller;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Controller {
	protected Stage stage;
	protected Scene scene;
	protected Parent root;
	
	@FXML
	abstract void initialize();
}
