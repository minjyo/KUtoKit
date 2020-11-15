package kutokit.view.popup;

import java.util.ArrayList;

import org.controlsfx.control.CheckListView;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;

public class SelectGroupFODController{
	@FXML
	AnchorPane rootFODPane = new AnchorPane(), selectGroupPane = new AnchorPane();
	@FXML
	Button nextButton = new Button(), confirmButton = new Button(), cancelButton = new Button();
	@FXML
	CheckListView<String> groupCheckList = new CheckListView<String>();
	@FXML
	RadioButton rootFOD = new RadioButton();
	
	public boolean confirmed = false, canceled = false;
	
	public SelectGroupFODController() {
		//first, only set rootFODPane visible
		rootFODPane.setVisible(true);
		selectGroupPane.setVisible(false);
		//if you don't click rootFOD, can't move to next scene
		nextButton.setDisable(true);
		
		if(rootFOD.isSelected()) {
			nextButton.setDisable(false);
		}
		
		//if next button is clicked, show group items view
		nextButton.setOnAction(Event ->{
			rootFODPane.setVisible(false);
			selectGroupPane.setVisible(true);
		});
		
		if(!groupCheckList.getItems().isEmpty()) {
			groupCheckList.setVisible(true);
		}
		
		confirmButton.setOnMouseClicked(MouseEvent -> {
			confirmed = true;
		});
		
		cancelButton.setOnMouseClicked(MouseEvent -> {
			canceled = true;
		});
		
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
		return groupCheckList.getSelectionModel().getSelectedItems();
	}
}