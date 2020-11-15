
package kutokit.view.popup;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kutokit.MainApp;
import kutokit.model.lhc.LHC;


public class UCAHazardPopUpController implements Initializable {

	@FXML private TableView<LHC> hazardTableView = new TableView<LHC>();
	@FXML private TableColumn<LHC,String> indexColumn = new TableColumn<LHC,String>();
	@FXML private TableColumn<LHC,String> textColumn = new TableColumn<LHC,String>();
	@FXML private TableColumn<LHC,String> linkColumn = new TableColumn<LHC,String>();

	@FXML private Stage stage;

	ObservableList<LHC> hazardList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL url,ResourceBundle rb){
		hazardList = MainApp.lhcDataStore.getHazardTableList();
		hazardTableView.setItems(hazardList);
		indexColumn.setCellValueFactory(cellData -> cellData.getValue().indexProperty());
		textColumn.setCellValueFactory(cellData -> cellData.getValue().textProperty());
		linkColumn.setCellValueFactory(cellData -> cellData.getValue().linkProperty());

		hazardTableView.setItems(hazardList);
	}

	  public void close() {
		  Stage pop = (Stage)hazardTableView.getScene().getWindow();
	       pop.close();
	  }



	public UCAHazardPopUpController(){
	}

}
