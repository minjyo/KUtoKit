package kutokit.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kutokit.MainApp;
import kutokit.model.CTM;
import kutokit.model.UCA;

public class UtmController {

	private MainApp mainApp;

	@FXML private TableView<UCA> ucaTable;
	@FXML private TableColumn<UCA, String> CAColumn, providingColumn, notProvidingColumn, incorrectColumn, stoppedColumn;

	//constructor
	public UtmController() {
	}

	//set MainApp
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setUcaTable(ObservableList<CTM> contextTable) {
		// TODO Auto-generated method stub
		ObservableList<UCA> ucaData = FXCollections.observableArrayList();
		for(int i=0;i<contextTable.size();i++)
		{
			//hazardous가 true일 경우,
			if(contextTable.get(i).getHazardous())
			{
				//UCA 만들기 일단 Control Action만 가져온다고 했을 때
				ucaData.add(new UCA(contextTable.get(i).getControlAction()));
			}
		}
		CAColumn.setCellValueFactory(cellData -> cellData.getValue().getControlAction());
		ucaTable.setItems(ucaData);
	}
}
