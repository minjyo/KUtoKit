package kutokit.view.popup;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import kutokit.MainApp;
import kutokit.model.LHC;
import kutokit.model.UCA;

public class UCAHazardPopUPController {
	@FXML private TableView<LHC> hazardousTable;
	@FXML private TableColumn<LHC, String> indexColumn, textColumn, linkColumn;

	public UCAHazardPopUPController(){
		ObservableList<LHC> hazardData = MainApp.lhcDataStore.getHazardTableList();

		ObservableList<LHC> temp = FXCollections.observableArrayList();

//		 if(!hazardData.isEmpty())
//		{
//			for(LHC l : hazardData){
//				LHC lhc = new LHC(l.getIndex(),l.getText(),l.getLink());
//				temp.add(lhc);
//			}
//			hazardData = temp;
//		}

		hazardousTable.setItems(hazardData);

//		indexColumn.setCellValueFactory(cellData -> cellData.newgetValue().getIndex());
//		textColumn.setCellValueFactory(cellData -> cellData.getValue().getText());
//		linkColumn.setCellValueFactory(cellData -> cellData.getValue().getLink());

		indexColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIndex()));
		textColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getText()));
		linkColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLink()));
	}

}
