package kutokit.view;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.Stage;
import kutokit.MainApp;
import kutokit.model.ctm.CTM;
import kutokit.model.ctm.CTMDataStore;
import kutokit.model.lhc.LHC;
import kutokit.model.utm.UCA;
import kutokit.model.utm.UCADataStore;
import kutokit.view.popup.UCAHazardPopUpController;

public class UtmController {

	private MainApp mainApp;
	private UCADataStore dataStore;
	private static ObservableList<UCADataStore> ucaDataStoreList = FXCollections.observableArrayList();

	@FXML private ObservableList<TableView<UCA>> ucaTableList= FXCollections.observableArrayList();
	@FXML private ObservableList<TableColumn<UCA, String>> CAColumn= FXCollections.observableArrayList();
	@FXML private ObservableList<TableColumn<UCA, String>> providingColumn= FXCollections.observableArrayList();
	@FXML private ObservableList<TableColumn<UCA, String>> notProvidingColumn= FXCollections.observableArrayList();
	@FXML private ObservableList<TableColumn<UCA, String>> incorrectColumn= FXCollections.observableArrayList();
	@FXML private ObservableList<TableColumn<UCA, String>> stoppedColumn= FXCollections.observableArrayList();
	@FXML private ObservableList<TableColumn> linkColumn= FXCollections.observableArrayList();
	@FXML private ObservableList<RadioButton> controllerButton = FXCollections.observableArrayList();

	@FXML private TabPane tabPane;

	private ContextMenu menu;
	private MenuItem delete_menu;
	private ObservableList<LHC> hazardData = FXCollections.observableArrayList();
	private static ObservableList<ObservableList<UCA>> ucaDataList =FXCollections.observableArrayList();
	private ObservableList<CTMDataStore> ctmData = FXCollections.observableArrayList();
	private ObservableList<String> hazardousList = FXCollections.observableArrayList();

	UCAHazardPopUpController ucaPopUp;

	public String controller = null;
	public String controlAction = null;

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
		ctmData = MainApp.ctmDataStoreList;

		//Test example
//		Test();
		//After CTM_table array -> change
		//test after input ctm example
		//input data about ctm in datastore
//   	 	String[] context = {"one","two"};
//   	 	ObservableList<String> l = FXCollections.observableArrayList();
//   	 	l.add("O");
//   	 	l.add("X");
//   	 	ComboBox<String> comboBox = new ComboBox(l);
//   	 	ObservableList<CTM> ctm = FXCollections.observableArrayList();
//   	 	ctm.add(new CTM("controller name","control aciton","cases",0,context,comboBox));
//   	 	ctmData.add(ctm);

		//example
//	 	String ctmController = "Controller";
//	 	String ctmControlAction = "Control Action";
// 		CTM ctm = ctmData.get(0).get(0);
// 		System.out.println(ctm.getCases()+ctm.getContext(0)+ctm.getControlAction()+ctm.getControllerName()+ctm.getHazardousValue());

   	 	int i=0;
//   	 	for(int j=0;j<ctmData.size();j++){
//   	 		i=0;
//	   	 	for(UCADataStore u : ucaDataStoreList){
//		 		if(u.getControllAction() == ctmData.get(j).get(0).getControllerName()&& u.getController()==ctmData.get(j).get(0).getControlAction()){
//		 			System.out.println(i);
//		   	 			break;
//		 		}
//		 		i++;
//		 	}
//		   	//new Ctm Table
//	   	 	if(i==ucaDataStoreList.size()){
//	   	 		addUcaTable(ctmData.get(j));
//	   	 	}
//   	 	}

//   	 		for(UCADataStore u : ucaDataStoreList){
//   	 			if(u.getControllAction() == ctmData.get(0).get(0).getControllerName()&& u.getController()==ctmData.get(0).get(0).getControlAction()){
//	   	 			break;
//   	 			}
//   		 		i++;
//   	 		}

//	 	}
   	 	i++;
	   	//new Ctm Table
	 	if(i==ucaDataStoreList.size()){
	 		addUcaTable(ctmData.get(0).getCTMTableList());
	 	}
		// Initialize from data store ,Tab -table View
		for(i=0;i<ucaDataStoreList.size();i++){
			ucaDataList.add(ucaDataStoreList.get(i).getUCATableList());
			TableView tableView = new TableView();
			tableView.setItems(ucaDataList.get(i));
			ucaTableList.add(tableView);

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


		//Mouse Right Click for delete
		menu = new ContextMenu();

		delete_menu = new MenuItem("Delete");

        delete_menu.setOnAction(new EventHandler<ActionEvent>() {
	     @Override
         public void handle(ActionEvent event) {
	    	 for(TableView<UCA> t : ucaTableList){
	    		 int selectedUCA = t.getSelectionModel().getSelectedIndex();
		    	 try{
		    			 t.getItems().remove(selectedUCA);
		    		 }
		    	 catch(Exception e){
		    		 System.out.println("Select empty data");
		    	 }
	    	 }

         }
        });

        menu.getItems().addAll(delete_menu);

   	 	for(TableView<UCA> t : ucaTableList){
	   		t.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
	            @Override
	            public void handle(ContextMenuEvent event) {
	                menu.show(t, event.getScreenX(), event.getScreenY());
	            }
	        });
   	 	}



   	 	tabPane.getTabs().remove(0,tabPane.getTabs().size());
 		String title = ucaDataStoreList.get(0).getController()+"-"+ucaDataStoreList.get(0).getControllAction();
 		Tab tab =new Tab(title,ucaTableList.get(0));
 		setUcaTable(0);
 		tabPane.getTabs().add(tab);
//   	 	for(i=0;i<ucaDataStoreList.size();i++){
//   	 		String title = ucaDataStoreList.get(i).getController()+"-"+ucaDataStoreList.get(i).getControllAction();
//   	 		Tab tab =new Tab(title,ucaTableList.get(i));
//   	 		setUcaTable(i);
//   	 		tabPane.getTabs().add(tab);
//   	 	}
//        ucaHazardPopup();

        return ;
	}

	private void Test() {
		// Test input data from dataStore

		//first Tab
		UCADataStore ucadatastore = new UCADataStore();
		String controller = "controller1";
		String controllAction = "ControlAciton1";
		UCA uca = new UCA("1","1","1","1","1",null); // in real should setUCA in initial
		ObservableList ucaData = FXCollections.observableArrayList();
		ucaData = ucadatastore.getUCATableList();
		ucaData.add(uca);
		ucaDataList.add(ucaData);
		ucadatastore.setControllAction(controllAction);
		ucadatastore.setController(controller);
		ucaDataStoreList.add(ucadatastore);

		UCADataStore ucadatastore1 = new UCADataStore();
		controller = "controller2";
		controllAction = "ControlAciton2";
		uca = new UCA("2","2","2","3","2",null); // in real should setUCA in initial
		ObservableList ucaData2 = FXCollections.observableArrayList();
		ucaData2 = ucadatastore1.getUCATableList();
		ucaData2.add(uca);
		ucaDataList.add(ucaData2);
		ucadatastore1.setControllAction(controllAction);
		ucadatastore1.setController(controller);
		ucaDataStoreList.add(ucadatastore1);
	}

	public void addUcaTable(ObservableList<CTM> ctmData) {
		// and new UCA Table from CTM data
		String tabtext = ctmData.get(0).getControllerName() + "-" + ctmData.get(0).getControlAction();
		UCADataStore ucadatastore = new UCADataStore();
		ObservableList<UCA> ucaData = FXCollections.observableArrayList();
		ucaData = ucadatastore.getUCATableList();


		for(CTM c : ctmData){
			String ucaColumn = "";
			if(c.getHazardous().getValue().equals("O")){
				switch((String)c.getCases().getValue())
				{
				//case naming corretly -dayun should modify
				case "CA ":
					ucaColumn = "CA";
					break;
				case "providing causes hazard":
					ucaColumn = "Providing Causes Hazard";
					break;
				case "not providing\ncauses hazard" :
					ucaColumn = "Not Providing Causes Hazard";
					break;
				case "too early, too late,\nout of order" :
					ucaColumn = "Incorrect Timing/Order";
					break;
				case "Stopped Too Soon/Applied Too Long" :
					ucaColumn = "Stopped Too Soon/Applied Too Long";
					break;
				default :
					break;
				}
				String Context = "";
				for(int j =0;j<mainApp.models.getValuelist().size();j++){
					if(c.getContext(j)!="N/A" || !c.getContext(j).isEmpty()){
						Context +=mainApp.models.getValuelist().get(j) +" =" +c.getContext(j)+", ";
					}
				}

				UCA uca = new UCA(c.ControlAction,"","","","",null);
				uca.setUCA(ucaColumn, Context,null);
				ucaData.add(uca);
				ucaDataList.add(ucaData);
			}

			if(!ucaData.isEmpty()){
				ucadatastore.setControllAction(ctmData.get(0).getControllerName());
				ucadatastore.setController(ctmData.get(0).getControlAction());
				ucaDataStoreList.add(ucadatastore);
			}
		}


		return;
	}

	public void setUcaTable(int i) {
		// Setting Link ComboBox considering hazardList(from LHC table) update
		hazardousList.removeAll(hazardousList);
		hazardousList.add("None");
		for(LHC l : hazardData){
			hazardousList.add(l.getIndex());
		}

		for(UCA u : ucaDataList.get(i)){
			u.setUCAInit();
			u.setLinkList(new ComboBox<String>(hazardousList));
		}

		ucaTableList.get(i).setItems(ucaDataList.get(i));
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

	private void ucaHazardPopup() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("popup/UCAHazardPopUpView.fxml"));
		ucaPopUp = loader.getController();

		Parent popUproot;
		try {
		  	popUproot = (Parent) loader.load();

			Scene scene = new Scene(popUproot);

		    Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();

		  } catch (IOException e) {
				e.printStackTrace();
		  }
	}

}

