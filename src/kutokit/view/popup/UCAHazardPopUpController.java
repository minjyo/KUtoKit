
package kutokit.view.popup;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kutokit.MainApp;
import kutokit.model.lhc.LHC;

public class UCAHazardPopUpController {

	@FXML private TableView<LHC> hazardTableView;
	@FXML private TableColumn<LHC,String> indexColumn,textColumn,linkColumn;

	public UCAHazardPopUpController(){
		ObservableList<LHC> hazardList = FXCollections.observableArrayList();
		hazardList = MainApp.lhcDataStore.getHazardTableList();

		System.out.println(hazardList.get(0).indexProperty().getValue());

		TableView<LHC> hazardTableView = new TableView();

		TableColumn<LHC,String> indexColumn= new TableColumn("index");
		indexColumn.setId("indexColumn");
		TableColumn<LHC,String> textColumn= new TableColumn("link");
		indexColumn.setId("textColumn");
		TableColumn<LHC,String> linkColumn= new TableColumn("index");
		indexColumn.setId("indexColumn");


		indexColumn.setCellValueFactory(cellData ->cellData.getValue().indexProperty());
		textColumn.setCellValueFactory(cellData ->cellData.getValue().textProperty());
		linkColumn.setCellValueFactory(cellData ->cellData.getValue().linkProperty());

		hazardTableView.setItems(hazardList);
	}

}
