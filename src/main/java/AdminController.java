import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;

    @FXML
    private Button button;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;
    
    @FXML
    private Label logresult;

    @FXML
    void AdminLogin(ActionEvent event) {
    	String user = username.getText();
    	String pwd = password.getText();
    	AdminDAO admindao = new AdminDaoImpl();
        
        try {
        	
        	Admin logged = admindao.loginAdmin(user, pwd); 
			if(logged != null) {
				CurrentAdminSingleton.getIstance().setAdmin(logged);
				logresult.setText("login effettuata");				
				Parent root = FXMLLoader.load(getClass().getResource("fxml/AdminDashBoard.fxml"));
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}else {
				logresult.setText("errore login");
			}
		} catch (Exception e) {
			logresult.setText("unable to query the db");
			e.printStackTrace();
		}
    }

}
