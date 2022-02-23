import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminLoginFX extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("fxml/AdminLogin.fxml"));
		
		primaryStage.setTitle("Login Admin");
		primaryStage.setScene(new Scene(root, 400, 460));
		primaryStage.show();
	}


    public static void main(String[] args) {
        launch(args);
    }

}
