package kutokit.view.popup.cse;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kutokit.MainApp;
 
public class AddFBPopUpController implements Initializable {

	  @FXML 
	  private TextField text1;
	  @FXML
	  private TextField text2;
	  public String controller;
	  public String controlled;
	  public ArrayList<String> FB;
	  
	  @FXML 
	  private Button add;
	  @FXML 
	  private Button remove;
	  @FXML 
	  private ListView<String> listView;
	  @FXML 
	  private TextField listInput;  
	  private ObservableList<String> listItems;    
	  
	  public MainApp mainApp;
	  public boolean OKclose;
	  
	  public AddFBPopUpController() {
		  OKclose = false;
		  controller = "Controller Name";
		  controlled = "Controller Name";
		  FB = new ArrayList<String>();
	  }
	  
	  public void setData() {
		  if(mainApp.components.findController(text1.getText())!=null && mainApp.components.findController(text2.getText())!=null) {
			  controller = text1.getText();
			  controlled = text2.getText();
			  
			  if(!listItems.isEmpty()) {
				  OKclose = true;
				  close();
			  }else {
				  FXMLLoader loader = new FXMLLoader();
				  loader.setLocation(getClass().getResource("ErrorNoFB.fxml"));
				  Parent popUproot;
				  try {
					  	popUproot = (Parent) loader.load();
						Scene scene = new Scene(popUproot);
						Stage stage = new Stage();
						stage.setScene(scene);
						stage.show();
				  }catch(IOException e) {
					  e.printStackTrace();
				  }  
			  }
			  
		  }
		  else {
			  FXMLLoader loader = new FXMLLoader();
			  loader.setLocation(getClass().getResource("ErrorNotFoundController.fxml"));
			  Parent popUproot;
			  try {
				  	popUproot = (Parent) loader.load();
					Scene scene = new Scene(popUproot);
					Stage stage = new Stage();
					stage.setScene(scene);
					stage.show();
			  }catch(IOException e) {
				  e.printStackTrace();
			  }  
		  }
	  }
	  
	  public void close() { 
	       Stage pop = (Stage)text1.getScene().getWindow(); 
	       pop.close();
	  }
	  
	  @FXML
	  private void addFB(ActionEvent action){
	    listItems.add(listInput.getText());
	    FB.add(listInput.getText());
	    listInput.clear();
	  }
	  
	  @FXML
	  private void removeFB(ActionEvent action){
	    int selectedItem = listView.getSelectionModel().getSelectedIndex();
	    listItems.remove(selectedItem);
	    FB.remove(selectedItem);
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
