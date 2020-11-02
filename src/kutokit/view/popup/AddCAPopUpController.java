package kutokit.view.popup;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
 
public class AddCAPopUpController implements Initializable {

	  @FXML 
	  private TextField text1;
	  @FXML
	  private TextField text2;
	  public String controller;
	  public String controlled;
	  public ArrayList<String> CA;
	  
	  @FXML 
	  private Button add;
	  @FXML 
	  private Button remove;
	  @FXML 
	  private ListView<String> listView;
	  @FXML 
	  private TextField listInput;  
	  private ObservableList<String> listItems;       
	  
	  public AddCAPopUpController() {
		  controller = "Controller";
		  controlled = "Controlled";
		  CA = new ArrayList<String>();
	  }
	  
	  public void setData() {
		  controller = text1.getText();
		  controlled = text2.getText();
		  close();
	  }
	  
	  public void close() { 
	       Stage pop = (Stage)text1.getScene().getWindow(); 
	       pop.close();
	  }
	  
	  @FXML
	  private void addCA(ActionEvent action){
	    listItems.add(listInput.getText());
	    CA.add(listInput.getText());
	    listInput.clear();
	  }
	  
	  @FXML
	  private void removeCA(ActionEvent action){
	    int selectedItem = listView.getSelectionModel().getSelectedIndex();
	    listItems.remove(selectedItem);
	    CA.remove(selectedItem);
	  }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//set ListView
		listItems = FXCollections.observableArrayList(); 
		listView.setItems(listItems);
		
		//Disable buttons to start
		add.setDisable(true);
		remove.setDisable(true);

	    // Add a ChangeListener to TextField to look for change in focus
		listInput.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		        if(listInput.isFocused()){
		        	add.setDisable(false);
				}
			}
		});    

		// Add a ChangeListener to ListView to look for change in focus
		listView.focusedProperty().addListener(new ChangeListener<Boolean>() {
		     public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		        if(listView.isFocused()){
		          remove.setDisable(false);
		        }
		     }
		});
	}
}
