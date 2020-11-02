
package kutokit.view.popup;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kutokit.MainApp;
import kutokit.model.lhc.LHC;
import kutokit.model.lhc.LHCDataStore;

public class UCAHazardPopUpController{
	@FXML private TableView<LHC> hazardousTable;
	@FXML private TableColumn<LHC, String> indexColumn, textColumn, linkColumn;

	private ObservableList<LHC> hazardData=FXCollections.observableArrayList();


	public UCAHazardPopUpController(){
		hazardData = MainApp.lhcDataStore.getHazardTableList();
		ObservableList<LHC> temp =FXCollections.observableArrayList();
		for(LHC l : MainApp.lhcDataStore.getHazardTableList()){
			temp.add(new LHC(l.getIndex(),l.getText(),l.getLink()));
			System.out.println(l.getIndex());
		}
		hazardData = temp;

		hazardousTable.setItems(temp);


		indexColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIndex()));
		textColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getText()));
		linkColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLink()));
	}

}