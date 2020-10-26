package kutokit.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import kutokit.MainApp;
import kutokit.model.LHC;

public class LhcController implements Initializable {

	private MainApp mainApp;
	
	@FXML private Tab lossTab, hazardTab, constraintTab;
	@FXML private TableView<LHC> lossTableView;
	@FXML private TableColumn<LHC, String> lossIndexColumn, lossTextColumn, lossLinkColumn;
	@FXML private TableView<LHC> hazardTableView;
	@FXML private TableColumn<LHC, String> hazardIndexColumn, hazardTextColumn, hazardLinkColumn;
	@FXML private TableView<LHC> constraintTableView;
	@FXML private TableColumn<LHC, String> constraintIndexColumn, constraintTextColumn, constraintLinkColumn;
	@FXML private TextField lossTextField, hazardTextField, hazardLinkField, constraintTextField, constraintLinkField;
	@FXML private Button addLossButton, addHazardButton, addConstraintButton;
	
	ObservableList<LHC> lossTableList = FXCollections.observableArrayList();
	ObservableList<LHC> hazardTableList = FXCollections.observableArrayList();
	ObservableList<LHC> constraintTableList = FXCollections.observableArrayList();
	
	/*
	 * constructor
	 */
	public LhcController() {
//		lossTableList.add(new LHC(new SimpleStringProperty("L1"), new SimpleStringProperty("ex)Loss of life or Injury to people"), new SimpleStringProperty(".")));
//		hazardTableList.add(new LHC(new SimpleStringProperty("H1"), new SimpleStringProperty("ex)Nuclear power plant releases dangerous materials."), new SimpleStringProperty("[L1]")));
//		constraintTableList.add(new LHC(new SimpleStringProperty("C1"), new SimpleStringProperty("ex)Nuclear power plant must not release dangerous materials."), new SimpleStringProperty("[H1]")));
	}
	
	//set MainApp
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	/*
	 * initialize each table
	 */
	@Override
	public void initialize(URL url, ResourceBundle rsrcs) {
		lossIndexColumn.setCellValueFactory(cellData -> cellData.getValue().getIndexProperty());
		lossTextColumn.setCellValueFactory(cellData -> cellData.getValue().getTextProperty());
		lossLinkColumn.setCellValueFactory(cellData -> cellData.getValue().getLinkProperty());
		
		lossTableView.setItems(lossTableList); 
		
		addLossButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				lossTableView.getItems().add(new LHC(new SimpleStringProperty("L" + (lossTableList.size()+1)), new SimpleStringProperty(lossTextField.getText()), new SimpleStringProperty(".")));
				lossTextField.clear();
			}
		});
		
		hazardIndexColumn.setCellValueFactory(cellData -> cellData.getValue().getIndexProperty());
		hazardTextColumn.setCellValueFactory(cellData -> cellData.getValue().getTextProperty());
		hazardLinkColumn.setCellValueFactory(cellData -> cellData.getValue().getLinkProperty());
		
		hazardTableView.setItems(hazardTableList); 
		
		addHazardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				hazardTableView.getItems().add(new LHC(new SimpleStringProperty("H" + (hazardTableList.size()+1)), new SimpleStringProperty(hazardTextField.getText()), new SimpleStringProperty(hazardLinkField.getText())));
				hazardTextField.clear();
				hazardLinkField.clear();
			}
		});
		
		constraintIndexColumn.setCellValueFactory(cellData -> cellData.getValue().getIndexProperty());
		constraintTextColumn.setCellValueFactory(cellData -> cellData.getValue().getTextProperty());
		constraintLinkColumn.setCellValueFactory(cellData -> cellData.getValue().getLinkProperty());
		
		constraintTableView.setItems(constraintTableList); 
		
		addConstraintButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				constraintTableView.getItems().add(new LHC(new SimpleStringProperty("C" + (constraintTableList.size()+1)), new SimpleStringProperty(constraintTextField.getText()), new SimpleStringProperty(constraintLinkField.getText())));
				constraintTextField.clear();
				constraintLinkField.clear();
			}
		});
	}
	
	public void modifyLoss() {
		
	}
	
	public void modifyHazard() {
		
	}
	
	public void modifyConstraint() {
		
	}

	/*
	 * delete chosen row from loss table
	 */
	@FXML
	public void deleteLoss() {
		int selectedIndex = lossTableView.getSelectionModel().getSelectedIndex();
		lossTableView.getItems().remove(selectedIndex);
	}
	
	/*
	 * delete chose row from hazard table
	 */
	@FXML
	public void deleteHazard() {
		
		int selectedIndex = hazardTableView.getSelectionModel().getSelectedIndex();
		hazardTableView.getItems().remove(selectedIndex);
	}
	
	/*
	 * delete chose row from constraint table
	 */
	@FXML
	public void deleteConstraint() {
		int selectedIndex = constraintTableView.getSelectionModel().getSelectedIndex();
		constraintTableView.getItems().remove(selectedIndex);
	}
}
