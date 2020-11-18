package kutokit.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ContextMenuEvent;
import kutokit.MainApp;
import kutokit.model.ctm.CTM;
import kutokit.model.ctm.CTMDataStore;
import kutokit.model.lhc.LHC;
import kutokit.model.pmm.ProcessModel;
import kutokit.model.utm.UCA;
import kutokit.model.utm.UCADataStore;

public class UtmController {

	private MainApp mainApp;
	private static ObservableList<UCADataStore> ucaDataStoreList = FXCollections.observableArrayList();

	@FXML private ObservableList<TableView<UCA>> ucaTableList= FXCollections.observableArrayList();
	@FXML private ObservableList<TableColumn<UCA, String>> CAColumn= FXCollections.observableArrayList();
	@FXML private ObservableList<TableColumn<UCA, String>> providingColumn= FXCollections.observableArrayList();
	@FXML private ObservableList<TableColumn<UCA, String>> notProvidingColumn= FXCollections.observableArrayList();
	@FXML private ObservableList<TableColumn<UCA, String>> incorrectColumn= FXCollections.observableArrayList();
	@FXML private ObservableList<TableColumn<UCA, String>> stoppedColumn= FXCollections.observableArrayList();
	@FXML private ObservableList<TableColumn> linkColumn= FXCollections.observableArrayList();
	@FXML private ComboBox<String> controllerComboBox,controlActionComboBox;
	@FXML private Button addCtmButton;
	@FXML private TabPane tabPane;

	private ObservableList<LHC> hazardData = FXCollections.observableArrayList();
//	private static ObservableList<ObservableList<UCA>> ucaDataList =FXCollections.observableArrayList();
	private ObservableList<CTMDataStore> ctmDataStoreList = FXCollections.observableArrayList();
	private ObservableList<String> hazardList = FXCollections.observableArrayList();

	//constructor
	public UtmController() {
	}

	//set MainApp
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		initialize();
	}

	private void initialize()
	{


		ucaDataStoreList = MainApp.ucaDataStoreList;
		hazardData = MainApp.lhcDataStore.getHazardTableList();
		ctmDataStoreList = MainApp.ctmDataStoreList;

		tabPane.getTabs().remove(0);

//		controllerComboBox
		ObservableList<String> controllerName = FXCollections.observableArrayList();
		for(CTMDataStore ctmData : ctmDataStoreList){
			boolean duplicate = false;
			for(String controllername : controllerName){
				if(controllername.equals(ctmData.getController())){
					duplicate = true;
					break;
				}
			}
			if(!duplicate){
				controllerComboBox.getItems().add(ctmData.ctmController);
				controllerName.add(ctmData.ctmController);
			}
		}

		controllerComboBox.setOnAction(event->{
			//Set ControlAction ComboBox about Clicked Controller
			controlActionComboBox.getItems().clear();
			for(CTMDataStore ctmData : ctmDataStoreList){
				if(ctmData.getController().equals(controllerComboBox.getValue())){
					controlActionComboBox.getItems().add(ctmData.getControlAction());
				}
			}

			//Show Controller Table
			tabPane.getTabs().clear();
			ucaTableList.clear();
			int id = controllerComboBox.getItems().indexOf(controlActionComboBox.getValue());
			showControllerTable(controllerComboBox.getValue(),id);
		});

		for(TableView<UCA> t : ucaTableList){
			contextMenu(t);
		}

        return ;
	}

	private void contextMenu(TableView<UCA> t) {
		//RigthtClick "delete Menu"
		//Mouse Right Click for delete
		ContextMenu menu = new ContextMenu();

		MenuItem delete_menu = new MenuItem("Delete");

        delete_menu.setOnAction(new EventHandler<ActionEvent>() {
	     @Override
         public void handle(ActionEvent event) {
    		 int selectedUCA = t.getSelectionModel().getSelectedIndex();
	    	 try{
	    			 t.getItems().remove(selectedUCA);
	    		 }
	    	 catch(Exception e){
	    		 System.out.println("Select empty data");
	    	 }
         }
        });

        menu.getItems().addAll(delete_menu);
   		t.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                menu.show(t, event.getScreenX(), event.getScreenY());
            }
        });
	}

	private void showControllerTable(String controller,int controllerId) {
		// Make Tab about Controller from UCADataStoreList
		tabPane.getTabs().clear();


		for(UCADataStore u : ucaDataStoreList){
			if(u.getController().equals(controller)){
				makeTable(u);
				Tab tab = new Tab(u.getControllAction(),ucaTableList.get(ucaTableList.size()-1));
				setUcaTable(u);
				tabPane.getTabs().add(tab);

			}
		}
	}

	@FXML
	private void addFromCTM(){

		String controller = controllerComboBox.getValue();
		String controlAction = controlActionComboBox.getValue();

		if(controlAction == null){
			System.out.println("Please Select ControlAction");
			return;
		}else if(controller ==null){
			System.out.println("Please Select Controller");
			return;
		}


		//If there is Controller-ControlAction Tab add Row form CTM
		for(UCADataStore u : ucaDataStoreList){
			if(u.getController().equals(controller)&& u.getControllAction().equals(controlAction)){
					addUCA(u);
					return;
				}
			}

		//If there isn't Controller-ControlAction Tab
		//Add CTM Data to  UCA Data
		UCADataStore newUCADataStore = addUcaTable();
		Tab tab = new Tab(controlAction,ucaTableList.get(ucaTableList.size()-1));
		setUcaTable(newUCADataStore);
		tabPane.getTabs().add(tab);
		return;
	}

	public UCADataStore addUcaTable() {
		// and new UCA Table from CTM data
		String controller = controllerComboBox.getValue();
		String controlAction = controlActionComboBox.getValue();
		UCADataStore ucadatastore = new UCADataStore();
		ObservableList<UCA> ucaData = FXCollections.observableArrayList();
		ucaData = ucadatastore.getUCATableList();

		ObservableList<CTM> curCtmData = FXCollections.observableArrayList() ;
		for(CTMDataStore ctmdata : ctmDataStoreList){
			if(controller.equals(ctmdata.getController()) && controlAction.equals(ctmdata.getControlAction())){
				curCtmData = ctmdata.getCTMTableList();
				break;

			}
		}


		for(CTM c : curCtmData){

			if(c.getHazardousValue()==""){
				break;
			}
			String ucaColumn = "";
			if(c.getHazardous().getValue().equals("O")){
				switch(c.getCases().getItems().indexOf(c.getCasesValue()))
				{
				case 0 :
					ucaColumn = "Providing Causes Hazard";
					break;
				case 1 :
					ucaColumn = "Not Providing Causes Hazard";
					break;
				case 2:
					ucaColumn = "Incorrect Timing/Order";
					break;
				case 3 :
					ucaColumn = "Stopped Too Soon/Applied Too Long";
					break;
				}
				String Context = "";
				for(ProcessModel pm : mainApp.pmmDB.getProcessModel()){
					if(pm.getControllerName().equals(c.getControllerName()) && pm.getControlActionName().equals(c.getControlAction())){
						for(int j=0;j<pm.getValuelist().size();j++){
							if(c.getContext(j)!="N/A" || !c.getContext(j).isEmpty()){
								Context +=pm.getValuelist().get(j) +" =" +c.getContext(j)+", ";
							}
						}
					}
				}

				UCA uca = new UCA(c.getControlAction(),"","","","",new ComboBox(hazardList));
				uca.setUCA(ucaColumn, Context,null);
				ucaData.add(uca);
			}
		}

		if(!ucaData.isEmpty()){
			ucadatastore.setControllAction(curCtmData.get(0).getControlAction());
			ucadatastore.setController(curCtmData.get(0).getControllerName());
			ucaDataStoreList.add(ucadatastore);
		}


		makeTable(ucadatastore);
		return ucadatastore;
	}

	private void makeTable(UCADataStore ucadatastore) {
		// Initialize from data store ,Tab -table View
		TableView newTableView = new TableView();
		newTableView.setItems(ucadatastore.getUCATableList());

		ucaTableList.add(newTableView);
		contextMenu(newTableView);

		//Column init
		TableColumn<UCA,String> ca = new TableColumn<>("CA");
		ca.setPrefWidth(180);
		ca.setResizable(false);
		ca.setOnEditCommit(event ->{
			onEditChange(event);
		});
		CAColumn.add(ca);
		TableColumn<UCA,String> pc = new TableColumn<>("Providing Causes Hazard");
		pc.setPrefWidth(180);
		pc.setResizable(false);
		pc.setOnEditCommit(event ->{
			onEditChange(event);
		});
		providingColumn.add(pc);
		TableColumn<UCA,String> np = new TableColumn<>("Not Providing Causes Hazard");
		np.setPrefWidth(180);
		np.setResizable(false);
		np.setOnEditCommit(event ->{
			onEditChange(event);
		});
		notProvidingColumn.add(np);
		TableColumn<UCA,String> it = new TableColumn<>("Incorrect Timing/Order");
		it.setPrefWidth(180);
		it.setResizable(false);
		it.setOnEditCommit(event ->{
			onEditChange(event);
		});
		incorrectColumn.add(it);
		TableColumn<UCA,String> st = new TableColumn<>("Stopped Too Soon/Applied Too Long");
		st.setPrefWidth(180);
		st.setResizable(false);
		st.setOnEditCommit(event ->{
			onEditChange(event);
		});
		stoppedColumn.add(st);
		TableColumn<UCA,String> li = new TableColumn<>("Link");
		li.setPrefWidth(100);
		li.setResizable(false);
		li.setOnEditCommit(event ->{
			onEditChange(event);
		});
		linkColumn.add(li);

	}

	private void addUCA(UCADataStore u) {

		// add UCA only
		String controller = controllerComboBox.getValue();
		String controlAction = controlActionComboBox.getValue();

		ObservableList<CTM> curCtmData = FXCollections.observableArrayList() ;
		for(CTMDataStore ctmdata : ctmDataStoreList){
			if(controller.equals(ctmdata.getController()) && controlAction.equals(ctmdata.getControlAction())){
				curCtmData = ctmdata.getCTMTableList();
				break;
			}
		}

		for(CTM c : curCtmData){
			String ucaColumn = "";

			if(c.getHazardousValue()==""){
				break;
			}

			if(c.getHazardous().getValue().equals("O")){
				System.out.println(c.getCases().getItems().indexOf(c.getCasesValue()));
				switch(c.getCases().getItems().indexOf(c.getCasesValue()))
				{
				case 0 :
					ucaColumn = "Providing Causes Hazard";
					break;
				case 1 :
					ucaColumn = "Not Providing Causes Hazard";
					break;
				case 2:
					ucaColumn = "Incorrect Timing/Order";
					break;
				case 3 :
					ucaColumn = "Stopped Too Soon/Applied Too Long";
					break;
				default :
					break;
				}
				String Context = "";
				for(ProcessModel pm : mainApp.pmmDB.getProcessModel()){
					if(pm.getControllerName().equals(c.getControllerName()) && pm.getControlActionName().equals(c.getControlAction())){
						for(int j=0;j<pm.getValuelist().size();j++){
							if(c.getContext(j)!="N/A" || !c.getContext(j).isEmpty()){
								Context +=pm.getValuelist().get(j) +" =" +c.getContext(j)+", ";
							}
						}
					}
				}

				UCA uca = new UCA(c.getControlAction(),"","","","",new ComboBox(hazardList));
				uca.setUCA(ucaColumn, Context,null);
				u.getUCATableList().add(uca);
			}
		}
		return;
	}

	public void setUcaTable(UCADataStore newUTMDataStore) {
		// Setting Link ComboBox considering hazardList(from LHC table) update
		hazardList.removeAll(hazardList);
		for(LHC l : hazardData){
			hazardList.add(l.getIndex());
		}

		for(UCA u : newUTMDataStore.getUCATableList()){
			u.setUCAInit();
			u.setLinkList(new ComboBox<String>(hazardList));
		}

		//index
		int i = ucaTableList.size()-1;

		ucaTableList.get(i).setItems(newUTMDataStore.getUCATableList());
		ucaTableList.get(i).setVisible(true);
		ucaTableList.get(i).setEditable(true);

		CAColumn.get(i).setCellValueFactory(cellData -> cellData.getValue().getControlAction());
		providingColumn.get(i).setCellValueFactory(cellData -> cellData.getValue().getProvidingCausesHazard());
		notProvidingColumn.get(i).setCellValueFactory(cellData -> cellData.getValue().getNotProvidingCausesHazard());
		incorrectColumn.get(i).setCellValueFactory(cellData -> cellData.getValue().getIncorrectTimingOrOrder());
		stoppedColumn.get(i).setCellValueFactory(cellData -> cellData.getValue().getStoppedTooSoonOrAppliedTooLong());
		linkColumn.get(i).setCellValueFactory(new PropertyValueFactory<UCA,String>("linkLists"));

		CAColumn.get(i).setCellFactory(TextFieldTableCell.forTableColumn());
	    providingColumn.get(i).setCellFactory(TextFieldTableCell.forTableColumn());
	    notProvidingColumn.get(i).setCellFactory(TextFieldTableCell.forTableColumn());
	    incorrectColumn.get(i).setCellFactory(TextFieldTableCell.forTableColumn());
	    stoppedColumn.get(i).setCellFactory(TextFieldTableCell.forTableColumn());

	    ucaTableList.get(i).getColumns().add(CAColumn.get(i));
	    ucaTableList.get(i).getColumns().add(providingColumn.get(i));
	    ucaTableList.get(i).getColumns().add(notProvidingColumn.get(i));
	    ucaTableList.get(i).getColumns().add(incorrectColumn.get(i));
	    ucaTableList.get(i).getColumns().add(stoppedColumn.get(i));
	    ucaTableList.get(i).getColumns().add(linkColumn.get(i));

	    return;

	}

	public void onEditChange(TableColumn.CellEditEvent<UCA, String> event) {
		//dataStore Save
		ObservableList<UCA> ucadata = event.getTableView().getItems();
		UCA uca = ucadata.get(event.getTablePosition().getRow());
		uca.setUCA(event.getTableColumn().getText(), event.getNewValue(), null);
	}
}

