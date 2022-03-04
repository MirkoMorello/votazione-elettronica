package Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import Model.Elettore;
import Model.Elezione;
import Singleton.DaoFactorySingleton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class VotazioniTerminateAdminController extends Controller{

    @FXML
    private Button back;

    @FXML
    private Label listname;

    @FXML
    private TableView<Elezione> terminatedelections;
    
    @FXML
    private TableColumn<Elezione, String> elezioni;

    @FXML
    private TableColumn<Elezione, String> vincitori;

    @FXML
    void back(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/manageElections.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

	@Override
	void initialize() {
		List<Elezione> term;
		try {
			term = DaoFactorySingleton.getDaoFactory().getElezioneDao().getElezioniTerminate();
			for(int i = 0; i < term.size(); i++) {
				System.out.println(term.get(i).getVincitore());
			}
			
			elezioni.setCellValueFactory(new PropertyValueFactory<Elezione, String>("titolo"));
			vincitori.setCellValueFactory(new PropertyValueFactory<Elezione, String>("vincitore"));
			
			terminatedelections.setItems(FXCollections.observableList(DaoFactorySingleton.getDaoFactory().getElezioneDao().getElezioniTerminate()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
