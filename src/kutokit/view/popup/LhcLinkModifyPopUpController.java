package kutokit.view.popup;

import org.controlsfx.control.CheckListView;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LhcLinkModifyPopUpController {
	@FXML 
	CheckListView<String> linkItems = new CheckListView<String>();
	@FXML
	Button confirmButton = new Button(), cancelButton = new Button();
	
	public LhcLinkModifyPopUpController() {
		if(linkItems.getSelectionModel().isEmpty()) {
			return;
		}else {
			
		}
	}
	
	public ObservableList<String> getLinkItems(){
		return linkItems.getItems();
	}
	
	public void setLinkItems(ObservableList<String> linkItemDatas) {
		this.linkItems.getItems().addAll(linkItemDatas);
	}
}