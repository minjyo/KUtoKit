package kutokit.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kutokit.MainApp;
import kutokit.model.lhc.LHC;
import kutokit.model.ls.LS;
import kutokit.model.ls.LSDataStore;
import kutokit.model.utm.UCA;
import kutokit.model.utm.UCADataStore;

public class LsController {
	
	private MainApp mainApp;
	private LSDataStore lsDB;
	private UCADataStore ucaDB;

	@FXML private Tab firstTab;
 	@FXML private TextField lossScenarioTextField;
	@FXML private Button addLossScenario, addNewTab;
	@FXML private ComboBox<String> UcaComboBox, lossFactorComboBox;
	@FXML private TabPane tabPane;
	
	ObservableList<TableView<LS>> lsTableViewList = FXCollections.observableArrayList();
	ObservableList<TableColumn<LS, String>> ucaColList = FXCollections.observableArrayList();
	ObservableList<TableColumn<LS, String>> lossFactorColList = FXCollections.observableArrayList();
	ObservableList<TableColumn<LS, String>> lossScenarioColList = FXCollections.observableArrayList();
	ObservableList<LS> lossScenarioTableList;
	ObservableList<String> lossFactorCBList = FXCollections.observableArrayList("1) Controller Problems", "2) Feedback Problems", "3) Control Path Problems", "4) Controlled Process Problems");
	ObservableList<UCADataStore> ucaDataStoreList = FXCollections.observableArrayList();
	//	ObservableList<UCA> ucaCBList = ucaDB.getUCATableList();
	/*
	 * default constructor
	 */
	public LsController() {
		
	}
	
	/*
	 * set mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		initialize();
	}
	
	public void addLossScenario() {
		int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
		
		lossScenarioTableList = lsDB.getLossScenarioList();
		
		ucaColList.get(tabIndex).setCellValueFactory(cellData -> cellData.getValue().getUCAProperty());
		lossFactorColList.get(tabIndex).setCellValueFactory(cellData -> cellData.getValue().getLossFactorProperty());
		lossScenarioColList.get(tabIndex).setCellValueFactory(cellData -> cellData.getValue().getLossScenarioProperty());
		
		lsTableViewList.get(tabIndex).setItems(lossScenarioTableList);
		
		/*
		 * add items to loss scenario table
		 */
		addLossScenario.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				//여기 코드 깨짐.
				LS ls = new LS(UcaComboBox.getValue(), lossFactorComboBox.getValue(), lossScenarioTextField.getText());
				//if text field is empty, warning pop up opens
				if(lossScenarioTextField.getText().isEmpty()) {
					try {
						OpenTextFieldPopUp();
						System.out.println("no text field input");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					lsTableViewList.get(tabIndex).getItems().add(ls);
					lossScenarioTextField.clear();
				}
			}
		});
		
		/*
		 * delete items from loss scenario table
		 */
		ContextMenu lossScenarioRightClickMenu = new ContextMenu();
		MenuItem removeLossScenarioMenu = new MenuItem("Delete");
		lossScenarioRightClickMenu.getItems().add(removeLossScenarioMenu);
		
		ObservableList<LS> allLossScenarioItems, selectedLossScenarioItem;
		allLossScenarioItems = lsTableViewList.get(tabIndex).getItems();
		selectedLossScenarioItem = lsTableViewList.get(tabIndex).getSelectionModel().getSelectedItems();
		
		/*
		 * when right-clicked, show pop up
		 */
		lsTableViewList.get(tabIndex).setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent e) {
				lossScenarioRightClickMenu.show(lsTableViewList.get(tabIndex), e.getScreenX(), e.getScreenY());
				removeLossScenarioMenu.setOnAction(event -> {
					selectedLossScenarioItem.forEach(allLossScenarioItems::remove);
					//no indexing here
				});
			}
		});
		lossScenarioRightClickMenu.hide();
		
		/*
		 * modify text in loss scenario table
		 */
		lossScenarioColList.get(tabIndex).setCellFactory(TextFieldTableCell.forTableColumn());
		lossScenarioColList.get(tabIndex).setOnEditCommit(
			(TableColumn.CellEditEvent<LS, String> t) ->
                (t.getTableView().getItems().get(
               	t.getTablePosition().getRow())
                ).setLossScenario(t.getNewValue())
		);
	}
	
	public void initialize() {
		lsDB = mainApp.lsDataStore;
		ucaDataStoreList = mainApp.ucaDataStoreList;
		
//		tabPane.getTabs().remove(0);
		
		//set uca combobox
		ObservableList<String> ucaDatas = FXCollections.observableArrayList();
		
		for(int i = 0; i < ucaDataStoreList.size(); i++) {
		    for(UCA u : ucaDataStoreList.get(i).getUCATableList()){
		    	String ucaType1 = u.getIncorrectTimingOrOrder().getValue();
		        String ucaType2 = u.getNotProvidingCausesHazard().getValue();
		        String ucaType3 = u.getIncorrectTimingOrOrder().getValue();
		        String ucaType4 = u.getStoppedTooSoonOrAppliedTooLong().getValue();
		        if(!ucaType1.isEmpty()) ucaDatas.add(ucaType1);
		        if(!ucaType2.isEmpty()) ucaDatas.add(ucaType2);
		        if(!ucaType3.isEmpty()) ucaDatas.add(ucaType3);
		        if(!ucaType4.isEmpty()) ucaDatas.add(ucaType4);
		    }
		}
	    
	    UcaComboBox.setItems(ucaDatas);
	    System.out.println(ucaDatas + " : uca datas");
	    
	    //add loss factor combobox
		lossFactorComboBox.setItems(lossFactorCBList);
		
		addNewTab.setOnAction(Event ->{
			addNewTab(lsTableViewList.size());
		});
		
		if(tabPane.getTabs().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("No tab to work");
			alert.setContentText("You have to add tab first");
		}else {
			tabPane.getTabs().remove(0);
			addNewTab(lsTableViewList.size());
			addLossScenario();
		}
	}
	
	/*
	 * if text field is empty, this pop up opens
	 */
	private void OpenTextFieldPopUp() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Parent parent = loader.load(getClass().getResource("popup/LsNoTextInputPopUpView.fxml"));
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
	
	private void addNewTab(int index) {
		Tab newTab = new Tab();
		newTab.setText("LS" + Integer.toString(index));
		tabPane.getTabs().add(newTab);

		TableView<LS> newTable = new TableView<LS>();
		lsTableViewList.add(newTable);
		
		newTab.setContent(newTable);
		
		TableColumn<LS, String> ucaCol = new TableColumn<LS, String>();
		TableColumn<LS, String> lossFactorCol = new TableColumn<LS, String>();
		TableColumn<LS, String> lossScenarioCol = new TableColumn<LS, String>();
		
		ucaColList.add(ucaCol);
		lossFactorColList.add(lossFactorCol);
		lossScenarioColList.add(lossScenarioCol);
		
		ucaCol.setPrefWidth(160.0);
		lossFactorCol.setPrefWidth(210.0);
		lossScenarioCol.setPrefWidth(630.0);
		
		ucaCol.setResizable(false);
		lossFactorCol.setResizable(false);
		lossScenarioCol.setResizable(false);
		
		ucaCol.setText("UCA");
		lossFactorCol.setText("Loss Factor");
		lossScenarioCol.setText("Loss Scenario");
		
		newTable.getColumns().add(ucaCol);
		newTable.getColumns().add(lossFactorCol);
		newTable.getColumns().add(lossScenarioCol);
	}
}