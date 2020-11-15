package kutokit.view.popup;

import java.util.ArrayList;

import org.controlsfx.control.CheckListView;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SelectGroupFODController{
	@FXML
	AnchorPane selectGroupPane = new AnchorPane();
	@FXML
	Button confirmButton = new Button();
	Button cancelButton = new Button();
	@FXML
	CheckListView<String> groupCheckList = new CheckListView<String>();
	@FXML
	RadioButton rootFOD = new RadioButton();
	
	public boolean confirmed = false, canceled = false;
	
	public SelectGroupFODController() {
		//first, only set rootFODPane visible
		selectGroupPane.setVisible(true);

		if(!groupCheckList.getItems().isEmpty()) {
			groupCheckList.setVisible(true);
		}
	}
	
	public void confirmClick() {
		confirmed = true;
		Stage pop = (Stage)selectGroupPane.getScene().getWindow();
		pop.close();
	}
	
	public void cancelClick(){
		canceled = true;
		Stage pop = (Stage)selectGroupPane.getScene().getWindow();
		pop.close();
	}
	

	//setRootFOD text from selected NuSRS file
	public void setRootFOD(String rootFOD) {
		this.rootFOD.setText(rootFOD);
	}
	
	//set group list in checkListView
	public void setGroupItems(ArrayList<String> groupList) {
		this.groupCheckList.getItems().addAll(groupList);
	}
	
	public ObservableList<String> selectedItems(){
		return groupCheckList.getCheckModel().getCheckedItems();
	}
}