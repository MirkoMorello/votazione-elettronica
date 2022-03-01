package View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Questa classe è parte della componente View del design pattern MVC
 * In questa classe è possibile gestire l'interfaccia grafica che l'utente visualizzerà durante una sessione di login
 * 
 */


public class LoginFX extends Application  {
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ex.fxml"));
		
		primaryStage.setTitle("Login Votazione Elettronica");
		primaryStage.setScene(new Scene(root, 400, 300));
		primaryStage.show();
	}


    public static void main(String[] args) {
        launch(args);
    }
}