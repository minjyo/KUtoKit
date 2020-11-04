
package kutokit.view.popup;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kutokit.MainApp;
import kutokit.model.lhc.LHC;
import kutokit.view.popup.*;

public class UCAHazardPopUpController {

	@FXML private TableView<LHC> hazardousTableView;
	@FXML private TableColumn<LHC, String> indexColumn, textColumn, linkColumn;

	ObservableList<LHC> hazardData=FXCollections.observableArrayList();

	public UCAHazardPopUpController(){

		hazardData = MainApp.lhcDataStore.getHazardTableList();

		if(hazardousTableView==null){
			System.out.println(2);
		}

		hazardData.add(new LHC("L","H","C"));
		//error
//				hazardousTableView.setItems(hazardData);
//
//		indexColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIndex()));
//		textColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getText()));
//		linkColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLink()));
//
	}

}
