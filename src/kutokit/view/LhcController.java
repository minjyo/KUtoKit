package kutokit.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;

import com.sun.javafx.scene.control.SelectedCellsMap;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kutokit.MainApp;
import kutokit.model.lhc.LHC;
import kutokit.model.lhc.LhcDataStore;
import javafx.fxml.*;

public class LhcController implements Initializable {

	private MainApp mainApp;
	private LhcDataStore lhcDB;
	
	@FXML private TableView<LHC> lossTableView;
	@FXML private TableColumn<LHC, String> lossIndexColumn, lossTextColumn, lossLinkColumn;
	@FXML private TableView<LHC> hazardTableView;
	@FXML private TableColumn<LHC, String> hazardIndexColumn, hazardTextColumn, hazardLinkColumn;
	@FXML private TableView<LHC> constraintTableView;
	@FXML private TableColumn<LHC, String> constraintIndexColumn, constraintTextColumn, constraintLinkColumn;
	@FXML private TextField lossTextField, hazardTextField, hazardLinkTextField, constraintTextField, constraintLinkTextField;
	@FXML private Button addLossButton, addHazardButton, addConstraintButton;
	@FXML private TableRow<LHC> lossRow, removeHazard, removeConstraint;
	@FXML private CheckComboBox<String> hazardLinkCB, constraintLinkCB;
	
	ObservableList<LHC> lossTableList, hazardTableList, constraintTableList;
	
	/*
	 * constructor
	 */
	public LhcController() {
//		lossTableList.add(new LHC("L1", "ex)Loss of life or Injury to people", ""));
//		hazardTableList.add(new LHC("H1", "ex)Nuclear power plant releases dangerous materials.", "[L1]"));
//		constraintTableList.add(new LHC("C1", "ex)Nuclear power plant must not release dangerous materials.", "[H1]"));
	}
	
	/*
	 * set MainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	/*
	 * add/modify/delete each items in each table
	 */
	@Override
	public void initialize(URL url, ResourceBundle rsrcs) {
		lhcDB = mainApp.lhcDataStore;
		
		lossTableList = lhcDB.getLossTableList();
		hazardTableList = lhcDB.getHazardTableList();
		constraintTableList = lhcDB.getConstraintTableList();
		
		ObservableList<String> lossIndexList = FXCollections.observableArrayList();
		ObservableList<String> hazardIndexList = FXCollections.observableArrayList();

		/*
		 * 
		 * below is code part for loss
		 * 
		*/
		
		
		lossIndexColumn.setCellValueFactory(cellData -> cellData.getValue().indexProperty());
		lossTextColumn.setCellValueFactory(cellData -> cellData.getValue().textProperty());
		lossLinkColumn.setCellValueFactory(cellData -> cellData.getValue().linkProperty());
		
		lossTableView.setItems(lossTableList); 
		
		/*
		 * add items to loss table
		 */
		addLossButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				LHC lhc = new LHC("L" + (lhcDB.getLossTableList().size()+1), lossTextField.getText(), "");
				//if text field is empty, warning pop up opens
				if(lossTextField.getText().isEmpty()) {
					try {
						OpenTextFieldPopUp();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("no text field input");
						e1.printStackTrace();
					}
				}else {
					lossTableView.getItems().add(lhc);
//					hazardLinkCB.getItems().addAll(lhc.getIndex());
					lossTextField.clear();
				}
			}
		});
		
		/*
		 * delete items from loss table
		 */
		ContextMenu lossRightClickMenu = new ContextMenu();
		MenuItem removeLossMenu = new MenuItem("Delete");
		lossRightClickMenu.getItems().add(removeLossMenu);
		
		ObservableList<LHC> allLossItems, selectedLossItem;
		allLossItems = lossTableView.getItems();
		selectedLossItem = lossTableView.getSelectionModel().getSelectedItems();
		
		/*
		 * when right-clicked, show pop up
		 */
		lossTableView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent e) {
				lossRightClickMenu.show(lossTableView, e.getScreenX(), e.getScreenY());
				removeLossMenu.setOnAction(event -> {
					selectedLossItem.forEach(allLossItems::remove);
					//need to update loss index
					updateLossIndex();
//					hazardLinkColumn.get
				});
			}
		});
		lossRightClickMenu.hide();
		
		/*
		 * modify text in loss table
		 */
		lossTextColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		lossTextColumn.setOnEditCommit(
			(TableColumn.CellEditEvent<LHC, String> t) ->
                (t.getTableView().getItems().get(
               	t.getTablePosition().getRow())
                ).setText(t.getNewValue())
		);
		
		/*
		 * 
		 * below is code part for hazard
		 * 
		*/
		
		
		hazardIndexColumn.setCellValueFactory(cellData -> cellData.getValue().indexProperty());
		hazardTextColumn.setCellValueFactory(cellData -> cellData.getValue().textProperty());
		hazardLinkColumn.setCellValueFactory(cellData -> cellData.getValue().linkProperty());
		
		hazardTableView.setItems(hazardTableList); 
		
		/*
		 * add items to hazard table
		 */
		addHazardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				LHC lhc = new LHC("H" + (lhcDB.getHazardTableList().size()+1), hazardTextField.getText(), "[L" + hazardLinkTextField.getText() + "]" );
//				hazardLinkCB.getCheckModel().getCheckedItems().toString()
				if(!hazardLinkTextField.getText().contains(",") && Integer.parseInt(hazardLinkTextField.getText()) > lhcDB.getLossTableList().size()) {
					try {
						openNoLinkPopUp();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(hazardLinkTextField.getText().contains(",")){
					String[] hazardLink;
					StringBuilder sb = new StringBuilder();
					sb.append("[");
					for(int i = 0; i < lhcDB.getLossTableList().size(); i++) {
						hazardLink = hazardLinkTextField.getText().split(",");
						hazardLink[i] = "L" + hazardLink[i];
						sb.append(hazardLink[i] + ", ");
					}
					sb.delete(sb.length() - 2, sb.length());
					sb.append("]");
					lhc.setLink(sb.toString());
					hazardTableView.getItems().add(lhc);
					hazardTextField.clear();
					hazardLinkTextField.clear();
				}else if(hazardTextField.getText().isEmpty()) {
				
					//if text field is empty, warning pop up opens
					try {
						OpenTextFieldPopUp();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("no text field input");
						e1.printStackTrace();
					}
				}else {
					//if entered value fits format and text field is not empty, continue on
					hazardTableView.getItems().add(lhc);
					constraintLinkCB.getItems().addAll(lhc.getIndex());
					hazardLinkTextField.clear();
					hazardTextField.clear();
				}
			}

		});
		
		/*
		 * delete items from hazard table
		 */
		ContextMenu hazardRightClickMenu = new ContextMenu();
		MenuItem removeHazardMenu = new MenuItem("Delete");
		hazardRightClickMenu.getItems().add(removeHazardMenu);
		
		
		ObservableList<LHC> allHazardItems, selectedHazardItem;
		allHazardItems = hazardTableView.getItems();
		selectedHazardItem = hazardTableView.getSelectionModel().getSelectedItems();
		
		/*
		*when mouse is right-clicked, show pop up
		*/
		hazardTableView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent e) {
				hazardRightClickMenu.show(hazardTableView, e.getScreenX(), e.getScreenY());
				removeHazardMenu.setOnAction(event -> {
					selectedHazardItem.forEach(allHazardItems::remove);
					//need to update hazard index
					updateHazardIndex();
				});
			}
		});
		hazardRightClickMenu.hide();
		
		/*
		 * modify text in hazard table
		*/
		hazardTextColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		hazardTextColumn.setOnEditCommit(
			(TableColumn.CellEditEvent<LHC, String> t) ->
				(t.getTableView().getItems().get(
				t.getTablePosition().getRow())
		        ).setText(t.getNewValue().toString())
		);
		
		/*
		 * modify link in hazard table
		 */
		hazardLinkColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		hazardLinkColumn.setOnEditCommit(
			(TableColumn.CellEditEvent<LHC, String> t) -> {
				if(t.getNewValue().contains("[") && t.getNewValue().contains("]")) {
					(t.getTableView().getItems().get(
							t.getTablePosition().getRow())
							).setLink(t.getNewValue().toString());
				} else {
					try {
						openLinkFormatPopUp();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		
		/*
		 * 
		 * below is code part for constraint
		 * 
		*/
				
		
		constraintIndexColumn.setCellValueFactory(cellData -> cellData.getValue().indexProperty());
		constraintTextColumn.setCellValueFactory(cellData -> cellData.getValue().textProperty());
		constraintLinkColumn.setCellValueFactory(cellData -> cellData.getValue().linkProperty());
		
		constraintTableView.setItems(constraintTableList); 
		
		/*
		 * add items to constraint table
		 */
		addConstraintButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				LHC lhc = new LHC("C" + (lhcDB.getConstraintTableList().size()+1), constraintTextField.getText(), constraintLinkCB.getCheckModel().getCheckedItems().toString());
				if(constraintTextField.getText().isEmpty()) {
					//if text field is empty, warning pop up opens
					try {
						OpenTextFieldPopUp();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("no text field input");
						e1.printStackTrace();
					}
				}else {
					//if entered value fits format, continue on
					constraintTableView.getItems().add(lhc);
					constraintTextField.clear();
				}
			}
		});
		
		/*
		 * delete items from constraint table
		 */
		ContextMenu constraintRightClickMenu = new ContextMenu();
		MenuItem removeConstraintMenu = new MenuItem("Delete");
		constraintRightClickMenu.getItems().add(removeConstraintMenu);
				
		ObservableList<LHC> allConstraintItems, selectedConstraintItem;
		allConstraintItems = constraintTableView.getItems();
		selectedConstraintItem = constraintTableView.getSelectionModel().getSelectedItems();
			
		/*
		 * when right-clicked, you can delete selected row
		 */
		constraintTableView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent e) {
				constraintRightClickMenu.show(constraintTableView, e.getScreenX(), e.getScreenY());
				removeConstraintMenu.setOnAction(event -> {
					selectedConstraintItem.forEach(allConstraintItems::remove);
					//need to update constraint index
					updateConstraintIndex();
				});
			}
		});
		constraintRightClickMenu.hide();
		
		/*
		 * modify text in constraint table
		 */
		constraintTextColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		constraintTextColumn.setOnEditCommit(
			(TableColumn.CellEditEvent<LHC, String> t) ->
				(t.getTableView().getItems().get(
				t.getTablePosition().getRow())
	            ).setText(t.getNewValue())
		);
		
		//modify link in constraint table
		constraintLinkColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		constraintLinkColumn.setOnEditCommit(
			(TableColumn.CellEditEvent<LHC, String> t) -> {
				if(t.getNewValue().contains("[") && t.getNewValue().contains("]")) {
					(t.getTableView().getItems().get(
						t.getTablePosition().getRow())
						).setLink(t.getNewValue().toString());
				}else {
					try {
						openLinkFormatPopUp();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		);
	}
	
	//if text field is empty, this pop up opens
	private void OpenTextFieldPopUp() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Parent parent = loader.load(getClass().getResource("popup/LhcNoTextInputPopUpView.fxml"));
		Stage dialogStage = new Stage();
		Scene scene = new Scene(parent);
		
		//set dialog setting
		dialogStage.setTitle("Empty text field");            
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(mainApp.getPrimaryStage());
		dialogStage.setScene(scene);
		dialogStage.setResizable(false);
		dialogStage.show();
	}

	//if link does not fit format of [index], this pop up opens
	private void openLinkFormatPopUp() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Parent parent = loader.load(getClass().getResource("popup/LhcLinkFormatPopUpView.fxml"));
		Scene scene = new Scene(parent);
		Stage dialogStage = new Stage();
		            
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(mainApp.getPrimaryStage());
		dialogStage.setTitle("Wrong link format");
		
		dialogStage.setScene(scene);
		dialogStage.setResizable(false);
		dialogStage.show();		
	}
	
	//if link does not fit format of [index], this pop up opens
		private void openNoLinkPopUp() throws IOException {
			FXMLLoader loader = new FXMLLoader();
			Parent parent = loader.load(getClass().getResource("popup/LhcNoLinkPopUpView.fxml"));
			Scene scene = new Scene(parent);
			Stage dialogStage = new Stage();
			            
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApp.getPrimaryStage());
			dialogStage.setTitle("No matching index");
			
			dialogStage.setScene(scene);
			dialogStage.setResizable(false);
			dialogStage.show();		
		}
	
	//if link for hazard to loss does not fit format, this pop up opens
	private void openHazardLinkPopUp() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Parent parent = loader.load(getClass().getResource("popup/LhcHazardLinkPopUpView.fxml"));
		Scene scene = new Scene(parent);
		Stage dialogStage = new Stage();
		            
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(mainApp.getPrimaryStage());
		dialogStage.setTitle("Wrong link format");
		
		dialogStage.setScene(scene);
		dialogStage.setResizable(false);
		dialogStage.show();		
	}
		
	//if link for constraint to hazard does not fit format, this pop up opens
	private void openConstraintLinkPopUp() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Parent parent = loader.load(getClass().getResource("popup/LhcConstraintLinkPopUpView.fxml"));
		Scene scene = new Scene(parent);
		Stage dialogStage = new Stage();
			            
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(mainApp.getPrimaryStage());
		dialogStage.setTitle("Wrong link format");
		
		dialogStage.setScene(scene);
		dialogStage.setResizable(false);
		dialogStage.show();		
	}	
	
	//function to update loss index when one is deleted.
	private void updateLossIndex() {
		ArrayList<String> index = new ArrayList<>();
		int total = lossTableList.size();
		for(int i = 0; i < total + 1; i++) {
			index.add(lossIndexColumn.getCellData(i));
			if(i == 0) {
				//always put first item's index as L1 
				if(index.get(i).equals("L1")) {
					continue;
				}else {
					index.get(i).replace(Integer.toString(i), "1");
					lossTableList.get(i).setIndex("L1");
				}
			}else {
				//from second item
				if(index.get(i).equals("L" + Integer.toString(i + 1))) {
					//for example, if second item(i=1)'s index is not L2
					continue;
				}else {
					lossTableList.get(i).setIndex("");
					lossTableList.get(i).setIndex("L" + Integer.toString(i + 1));
				}
			}
			total-- ;
		}
	}
	
	//function to update hazard index when one is deleted.
	private void updateHazardIndex() {
		ArrayList<String> index = new ArrayList<>();
		int total = hazardTableList.size();
		for(int i = 0; i < total + 1; i++) {
			index.add(hazardIndexColumn.getCellData(i));
			if(i == 0) {
				//always put first item's index as H1 
				if(index.get(i).equals("H1")) {
					continue;
				}else {
					index.get(i).replace(Integer.toString(i), "1");
					hazardTableList.get(i).setIndex("H1");
				}
			}else {
				//from second item
				if(index.get(i).equals("H" + Integer.toString(i + 1))) {
					//for example, if second item(i=1)'s index is not H2
					continue;
				}else {
					hazardTableList.get(i).setIndex("");
					hazardTableList.get(i).setIndex("H" + Integer.toString(i + 1));
				}
			}
			total-- ;
		}
	}
	
	//function to update constraint index when one is deleted.
	private void updateConstraintIndex() {
		ArrayList<String> index = new ArrayList<>();
		int total = constraintTableList.size();
		for(int i = 0; i < total + 2; i++) {
			index.add(constraintIndexColumn.getCellData(i));
			if(i == 0) {
				//always put first item's index as C1 
				if(index.get(i).equals("C1")) {
					continue;
				}else {
					index.get(i).replace(Integer.toString(i), "1");
					constraintTableList.get(i).setIndex("C1");
				}
			}else {
				//from second item
				if(index.get(i).equals("C" + Integer.toString(i + 1))) {
					//for example, if second item(i=1)'s index is not C2
					continue;
				}else {
					constraintTableList.get(i).setIndex("");
					constraintTableList.get(i).setIndex("C" + Integer.toString(i + 1));
				}
			}
			total-- ;
		}
	}
}
