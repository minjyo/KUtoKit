package kutokit.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.javafx.scene.control.SelectedCellsMap;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import kutokit.MainApp;
import kutokit.model.LHC;
import kutokit.model.LHCDataStore;

public class LhcController implements Initializable {

	private MainApp mainApp;
	private LHCDataStore lhcDB;
	
	@FXML private Tab lossTab, hazardTab, constraintTab;
	@FXML private TableView<LHC> lossTableView;
	@FXML private TableColumn<LHC, String> lossIndexColumn, lossTextColumn, lossLinkColumn;
	@FXML private TableView<LHC> hazardTableView;
	@FXML private TableColumn<LHC, String> hazardIndexColumn, hazardTextColumn, hazardLinkColumn;
	@FXML private TableView<LHC> constraintTableView;
	@FXML private TableColumn<LHC, String> constraintIndexColumn, constraintTextColumn, constraintLinkColumn;
	@FXML private TextField lossTextField, hazardTextField, hazardLinkField, constraintTextField, constraintLinkField;
	@FXML private Button addLossButton, addHazardButton, addConstraintButton;
	@FXML private TableRow<LHC> lossRow, removeHazard, removeConstraint;
	
	ObservableList<LHC> lossTableList, hazardTableList, constraintTableList;
	
	/*
	 * constructor
	 */
	public LhcController() {
//		lossTableList.add(new LHC("L1", "ex)Loss of life or Injury to people", ""));
//		hazardTableList.add(new LHC("H1", "ex)Nuclear power plant releases dangerous materials.", "[L1]"));
//		constraintTableList.add(new LHC("C1", "ex)Nuclear power plant must not release dangerous materials.", "[H1]"));
	}
	
	//set MainApp
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
		
		/*
		 * 
		 * below is code part for loss
		 * 
		*/
		
		//add items to loss table
		lossIndexColumn.setCellValueFactory(cellData -> cellData.getValue().indexProperty());
		lossTextColumn.setCellValueFactory(cellData -> cellData.getValue().textProperty());
		lossLinkColumn.setCellValueFactory(cellData -> cellData.getValue().linkProperty());
		
		lossTableView.setItems(lossTableList); 
		
		addLossButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				LHC lhc = new LHC("L" + (lhcDB.getLossTableList().size()+1), lossTextField.getText(), "");
				lossTableView.getItems().add(lhc);
				lossTextField.clear();
			}
		});
		
		//delete items from loss table
		ContextMenu lossRightClickMenu = new ContextMenu();
		MenuItem removeLossMenu = new MenuItem("Delete");
		lossRightClickMenu.getItems().add(removeLossMenu);
		
		
		ObservableList<LHC> allLossItems, selectedLossItem;
		allLossItems = lossTableView.getItems();
		selectedLossItem = lossTableView.getSelectionModel().getSelectedItems();
		
		//when right-clicked, show pop up
		lossTableView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent e) {
				lossRightClickMenu.show(lossTableView, e.getScreenX(), e.getScreenY());
				removeLossMenu.setOnAction(event -> {
					selectedLossItem.forEach(allLossItems::remove);
					//need to update loss index
				});
			}
			
		});
		
		//modify text in loss table
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
		
		//add items to hazard table
		hazardIndexColumn.setCellValueFactory(cellData -> cellData.getValue().indexProperty());
		hazardTextColumn.setCellValueFactory(cellData -> cellData.getValue().textProperty());
		hazardLinkColumn.setCellValueFactory(cellData -> cellData.getValue().linkProperty());
		
		hazardTableView.setItems(hazardTableList); 
		
		addHazardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				LHC lhc = new LHC("H" + (lhcDB.getHazardTableList().size()+1), hazardTextField.getText(), hazardLinkField.getText());
				hazardTableView.getItems().add(lhc);
				hazardTextField.clear();
				hazardLinkField.clear();
			}
		});
		
		//delete items from hazard table
		ContextMenu hazardRightClickMenu = new ContextMenu();
		MenuItem removeHazardMenu = new MenuItem("Delete");
		hazardRightClickMenu.getItems().add(removeHazardMenu);
		
		
		ObservableList<LHC> allHazardItems, selectedHazardItem;
		allHazardItems = hazardTableView.getItems();
		selectedHazardItem = hazardTableView.getSelectionModel().getSelectedItems();
		
		//when right-clicked, show pop up
		hazardTableView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent e) {
				hazardRightClickMenu.show(hazardTableView, e.getScreenX(), e.getScreenY());
				removeHazardMenu.setOnAction(event -> {
					selectedHazardItem.forEach(allHazardItems::remove);
					//need to update hazard index
				});
			}
			
		});
		
		//modify text in hazard table
		hazardTextColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		hazardTextColumn.setOnEditCommit(
			(TableColumn.CellEditEvent<LHC, String> t) ->
				(t.getTableView().getItems().get(
				t.getTablePosition().getRow())
		        ).setText(t.getNewValue())
		);
		
		//modify link in hazard table
		hazardLinkColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		hazardLinkColumn.setOnEditCommit(
			(TableColumn.CellEditEvent<LHC, String> t) ->
				(t.getTableView().getItems().get(
				t.getTablePosition().getRow())
	            ).setText(t.getNewValue())
		);
		
		/*
		 * 
		 * below is code part for constraint
		 * 
		*/
				
		//add items to constraint table
		constraintIndexColumn.setCellValueFactory(cellData -> cellData.getValue().indexProperty());
		constraintTextColumn.setCellValueFactory(cellData -> cellData.getValue().textProperty());
		constraintLinkColumn.setCellValueFactory(cellData -> cellData.getValue().linkProperty());
		
		constraintTableView.setItems(constraintTableList); 
		
		addConstraintButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				LHC lhc = new LHC("C" + (lhcDB.getConstraintTableList().size()+1), constraintTextField.getText(), constraintLinkField.getText());
				constraintTableView.getItems().add(lhc);
				constraintTextField.clear();
				constraintLinkField.clear();
			}
		});
		
		//delete items from constraint table
		ContextMenu constraintRightClickMenu = new ContextMenu();
		MenuItem removeConstraintMenu = new MenuItem("Delete");
		constraintRightClickMenu.getItems().add(removeConstraintMenu);
				
		ObservableList<LHC> allConstraintItems, selectedConstraintItem;
		allConstraintItems = constraintTableView.getItems();
		selectedConstraintItem = constraintTableView.getSelectionModel().getSelectedItems();
			
		//when right-clicked, show pop up
		constraintTableView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent e) {
				constraintRightClickMenu.show(constraintTableView, e.getScreenX(), e.getScreenY());
				removeConstraintMenu.setOnAction(event -> {
					selectedConstraintItem.forEach(allConstraintItems::remove);
					//need to update constraint index
						});
				}
					
		});
		
		//modify text in constraint table
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
			(TableColumn.CellEditEvent<LHC, String> t) ->
				(t.getTableView().getItems().get(
				t.getTablePosition().getRow())
		        ).setText(t.getNewValue())
		);
	}
}
