package kutokit.view;

import java.io.IOException;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.Stage;
import kutokit.MainApp;
import kutokit.model.ctm.CTM;
import kutokit.model.lhc.LHC;
import kutokit.model.utm.UCA;
import kutokit.model.utm.UCADataStore;
import kutokit.view.popup.UCAHazardPopUpController;

public class UtmController {

	private MainApp mainApp;
	private UCADataStore dataStore;

	@FXML private TableView<UCA> ucaTable;
	@FXML private TableColumn<UCA, String> CAColumn, providingColumn, notProvidingColumn, incorrectColumn, stoppedColumn;//,linkColumn;
//	@FXML private TableColumn<UCA,String> linkColumn;
	@FXML private TableColumn linkColumn;

	private ContextMenu menu;
	private MenuItem delete_menu;
	private ObservableList<LHC> hazardData = FXCollections.observableArrayList();
	private static ObservableList<UCA> ucaData =FXCollections.observableArrayList();
	private ObservableList<CTM> ctmData = FXCollections.observableArrayList();
	private ObservableList<String> hazardousList = FXCollections.observableArrayList();

	UCAHazardPopUpController ucaPopUp;

	//constructor
	public UtmController() {
	}

	private void initialize()
	{
        if(ucaTable==null)
        {
        	System.out.println(5);
        }

		dataStore = MainApp.ucadatastore;
		hazardData = MainApp.lhcDataStore.getHazardTableList();
		ucaData = dataStore.getUCATableList();
		ctmData = MainApp.ctmDataStore.getCTMTableList();

		menu = new ContextMenu();

		delete_menu = new MenuItem("Delete");

        delete_menu.setOnAction(new EventHandler<ActionEvent>() {
	     @Override
         public void handle(ActionEvent event) {
	    	 int selectedUCA = ucaTable.getSelectionModel().getSelectedIndex();
	    	 try{
	    			 ucaTable.getItems().remove(selectedUCA);
	    		 }
	    	 catch(Exception e){
	    		 System.out.println("Select empty data");
	    	 }
         }
        });

        menu.getItems().addAll(delete_menu);

        ucaTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {
                menu.show(ucaTable, event.getScreenX(), event.getScreenY());
            }
        });

        setUcaTable();
        ucaHazardPopup();



        return ;
	}


	//set MainApp
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		initialize();
	}

	public void setUcaTable() {

//		ObservableList<String> hazardousList = FXCollections.observableArrayList();

		for(LHC l : hazardData){
			hazardousList.add(l.getIndex());
		}

		 if(ucaData.isEmpty())
		{
			//Get hazardous
			//Example
			ComboBox<String> combobox = new ComboBox<String>(hazardousList);
			UCA uca = new UCA("example","new","table","control","action",combobox);
			ucaData.add(uca);
			uca = new UCA("example","new","table","control","action1",combobox);
			ucaData.add(uca);

		}

		ucaTable.setItems(ucaData);
		ucaTable.setVisible(true);

		CAColumn.setCellValueFactory(cellData -> cellData.getValue().getControlAction());
		providingColumn.setCellValueFactory(cellData -> cellData.getValue().getProvidingCausesHazard());
		notProvidingColumn.setCellValueFactory(cellData -> cellData.getValue().getNotProvidingCausesHazard());
		incorrectColumn.setCellValueFactory(cellData -> cellData.getValue().getIncorrectTimingOrOrder());
		stoppedColumn.setCellValueFactory(cellData -> cellData.getValue().getStoppedTooSoonOrAppliedTooLong());
//		linkColumn.setCellValueFactory(cellData -> cellData.getValue().getLink());

//	    linkColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		CAColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	    providingColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	    notProvidingColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	    incorrectColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	    stoppedColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		return ;

	}

	@FXML
	public void onEditChange(TableColumn.CellEditEvent<UCA, String> event) {
		//dataStore Save
		UCA uca = ucaData.get(event.getTablePosition().getRow());
		String newValue = event.getNewValue();
		String linkIndex = null;
		if(event.getTableColumn() == linkColumn){
			for(LHC l : hazardData){
				if(l.getIndex() == newValue){
					linkIndex = newValue;
				}
			}
			if(linkIndex == null){
				System.out.println("Wrong Link");
				newValue = "";
			}
		}
		uca.setUCA(event.getTableColumn().getId(), newValue,new ComboBox<String>(hazardousList));
		ucaData.set(event.getTablePosition().getRow(), uca);
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

	public void getContext(){
		ctmData = mainApp.ctmDataStore.getCTMTableList();
		if(!ctmData.isEmpty()){
			for(CTM c : ctmData){
				if(c.getHazardous().getValue()=="O"){
					ucaData.add(new UCA(c.getControlAction(),"","","","",new ComboBox<String>(hazardousList)));
				}
			}
		}
	}

}

