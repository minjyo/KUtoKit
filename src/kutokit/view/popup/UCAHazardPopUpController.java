
package kutokit.view.popup;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kutokit.MainApp;
import kutokit.model.lhc.LHC;

public class UCAHazardPopUpController {

	@FXML private TableView<LHC> hazardTableView = new TableView<LHC>();
	@FXML private TableColumn<LHC,String> indexColumn = new TableColumn<LHC,String>();
	@FXML private TableColumn<LHC,String> textColumn = new TableColumn<LHC,String>();
	@FXML private TableColumn<LHC,String> linkColumn = new TableColumn<LHC,String>();

	public UCAHazardPopUpController(){
		ObservableList<LHC> hazardList = FXCollections.observableArrayList();
		hazardList = MainApp.lhcDataStore.getHazardTableList();

		hazardTableView.setItems(hazardList);
		indexColumn.setCellValueFactory(cellData ->cellData.getValue().indexProperty());
		textColumn.setCellValueFactory(cellData ->cellData.getValue().textProperty());
		linkColumn.setCellValueFactory(cellData ->cellData.getValue().linkProperty());


	}

}
