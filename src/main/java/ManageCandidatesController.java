import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

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
    	System.out.println("vondietro");
    	Parent root = FXMLLoader.load(getClass().getResource("fxml/ListManagement.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    @FXML
    void add(ActionEvent event) throws NoSuchAlgorithmException, Exception {
    	System.out.println("buba");
    	LocalDate nascita = date.getValue();
    	String lista = CurrentListSingleton.getIstance().getList().getName();
    	Boolean result = DaoFactorySingleton.getDaoFactory().getCandidatoDao().addCandidate(name.getText(), surname.getText(), nascita, lista);
    	if(result) {
    		System.out.println("yee");
    	}else {
    		System.out.println("noooo");
    	}
    	this.initialize();
    }
    
    @FXML
    void initialize() throws Exception {
    	listname.setText(String.valueOf(CurrentListSingleton.getIstance().getList().getName()));
    	listdesc.setText(String.valueOf(CurrentListSingleton.getIstance().getList().getDesc()));
    	
    	List<Candidato> candidates = DaoFactorySingleton.getDaoFactory().getCandidatoDao().getCandidatesOfList(CurrentListSingleton.getIstance().getList().getName());
    	for(int i = 0; i < candidates.size(); i++) {
    		if(!currentcandidates.getItems().contains(candidates.get(i).getNome() +" " + candidates.get(i).getCognome()))
    		currentcandidates.getItems().add(candidates.get(i).getNome() + " " + candidates.get(i).getCognome());
    	}
    }
    
   
}
