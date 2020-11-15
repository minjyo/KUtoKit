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

public class LsController implements Initializable {
	
	private MainApp mainApp;
	private LSDataStore lsDB;
	private UCADataStore ucaDB;

	@FXML private TableView<LS> lossScenarioTableView;
	@FXML private TableColumn<LS, String> linkedUCAColumn, lossFactorColumn, lossScenarioTextColumn;
	@FXML private TextField lossScenarioTextField;
	@FXML private Button addLossScenario, addNewTab;
	@FXML private ComboBox<UCA> UcaComboBox;
	@FXML private ComboBox<String> lossFactorComboBox;
	@FXML private TableRow<LS> lsRow;
	@FXML private TabPane tabPane;
	
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
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		lsDB = mainApp.lsDataStore;
		ucaDataStoreList = mainApp.ucaDataStoreList;
		for(UCADataStore u : ucaDataStoreList) {
			UcaComboBox.getItems().addAll(u.getUCATableList());
		}
		
		lossScenarioTableList = lsDB.getLossScenarioList();
		
		linkedUCAColumn.setCellValueFactory(cellData -> cellData.getValue().getUCAProperty());
		lossFactorColumn.setCellValueFactory(cellData -> cellData.getValue().getLossFactorProperty());
		lossScenarioTextColumn.setCellValueFactory(cellData -> cellData.getValue().getLossScenarioProperty());
		
		lossScenarioTableView.setItems(lossScenarioTableList);
		

		lossFactorComboBox.setItems(lossFactorCBList);
//		UcaComboBox.setItems(ucaCBList);
		
		/*
		 * add items to loss scenario table
		 */
		addLossScenario.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				//여기 코드 깨짐.
				LS ls = new LS(UcaComboBox.getSelectionModel().selectedItemProperty().toString(), lossFactorComboBox.getValue().toString(), lossScenarioTextField.getText());
				//if text field is empty, warning pop up opens
				if(lossScenarioTextField.getText().isEmpty()) {
					try {
						OpenTextFieldPopUp();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("no text field input");
						e1.printStackTrace();
					}
				}else {
					lossScenarioTableView.getItems().add(ls);
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
		allLossScenarioItems = lossScenarioTableView.getItems();
		selectedLossScenarioItem = lossScenarioTableView.getSelectionModel().getSelectedItems();
		
		/*
		 * when right-clicked, show pop up
		 */
		lossScenarioTableView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent e) {
				lossScenarioRightClickMenu.show(lossScenarioTableView, e.getScreenX(), e.getScreenY());
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
		lossScenarioTextColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		lossScenarioTextColumn.setOnEditCommit(
			(TableColumn.CellEditEvent<LS, String> t) ->
                (t.getTableView().getItems().get(
               	t.getTablePosition().getRow())
                ).setLossScenario(t.getNewValue())
		);
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
	
	/*
	 * if new tab button clicked, make new tab
	 */
	@FXML
	private void newTabButtonClicked() {
		addNewTab.setOnMouseClicked(event -> {
			Tab newTab = new Tab();
		});
	}
}