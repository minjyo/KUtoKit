package kutokit.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import kutokit.Info;
import kutokit.MainApp;
import kutokit.model.ctm.CTM;
import kutokit.model.ctm.CTMDataStore;
import kutokit.model.pmm.ProcessModel;

public class CtmController2 implements Initializable{
	private MainApp mainApp;
	private File selectedFile;
	private ProcessModel pm = new ProcessModel();

	@FXML ComboBox<String> controllerCB,controlActionCB;
	@FXML Button addFileButton, addTabButton;
	@FXML Tab firstTab;
	@FXML TabPane tabPane;
	@FXML TableView<CTM> ctmTableView;
	@FXML TableColumn<CTM,String> casesColumn, hazardousColumn;
	@FXML TableColumn<CTM,Integer> noColumn;
//	@FXML AnchorPane addCTM;

	private ObservableList<String> cases = FXCollections.observableArrayList(
			"Not providing\ncauses hazard",
			"Providing causes hazard",
			"Too early, too late,\nout of order",
			"Stopped too soon,\napplied too long");
	private ObservableList<String> hazardous = FXCollections.observableArrayList("O", "X");

	ComboBox<String> casesCB = new ComboBox<String>(cases);
	ComboBox<String> hazardousCB = new ComboBox<String>(hazardous);
	private ObservableList<String> contextList = FXCollections.observableArrayList();

	ObservableList<TableView<CTM>> totalTableView = FXCollections.observableArrayList();
	ObservableList<ComboBox> casesList = FXCollections.observableArrayList();
	ObservableList<ComboBox> hazardousList = FXCollections.observableArrayList();

	//CTMDataStoreList
	ObservableList<CTMDataStore> ctmDataStoreList = FXCollections.observableArrayList();

	/*
	 * default constructor
	 */
	public CtmController2() {
	}

	//set MainApp
	public void setMainApp(MainApp mainApp)  {
		this.mainApp = mainApp;
		pm = mainApp.models;
		//Test Data
		test();

		initialize(null,null);
	}

	private void test() {
		// Make ProcessModel Data for CTM test
		pm = mainApp.models;

		//Controller
		ArrayList<String> controller = new ArrayList();
		controller.add("c1");
		controller.add("c2");
		pm.setControllerName(controller);

		//ControlAction
		ArrayList<ArrayList<String>> controlActions = new ArrayList();
		ArrayList<String> ca1 = new ArrayList();
		ArrayList<String> ca2 = new ArrayList();
		ca1.add("ca11");
		ca2.add("ca21");
		ca2.add("ca22");
		controlActions.add(ca1);
		controlActions.add(ca2);
		pm.setControlActionNames(controlActions);

		//Output
		ArrayList<ArrayList<String>> outputs = new ArrayList();
		ArrayList<String> op1 = new ArrayList();
		ArrayList<String> op2 = new ArrayList();
		op1.add("op11");
		op2.add("op21");
		op2.add("op22");
		outputs.add(op1);
		outputs.add(op2);
		pm.setOutputNames(outputs);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Get from DataStore
//		pm = mainApp.models;
		contextList = pm.getValuelist();
		ctmDataStoreList = mainApp.ctmDataStoreList;


		//Setting Controller, ControlAction ComboBox
		controllerCB.getItems().addAll(pm.getControllerName());
		controllerCB.setOnAction(event ->{
			//Remove Before Table, ControlAction ComboBox
			controlActionCB.setValue(null);
			controlActionCB.getItems().remove(0,controlActionCB.getItems().size());
			totalTableView.remove(0,totalTableView.size());

			//Get Controller id
			for(int i=0;i<pm.getControllerName().size();i++){
				if(pm.getControllerName().get(i)==controllerCB.getValue()){
					//Setting ControlAction ComboBox
					controlActionCB.getItems().addAll(pm.getControlActionNames().get(i));

					//Open Tab about Controller
					showControllerTabs(controllerCB.getValue(),i);
				}
			}

		});

		//set first first Tab name
		firstTab.setText(pm.getControllerName() + "-" + pm.getControlActionNames());

		//if tab button is clicked, add new tab
		addTabButton.setOnAction(event -> {
			if(!controllerCB.getValue().isEmpty() && !controlActionCB.getValue().isEmpty()) {
				//if user selected controller & control action, add new tab
				addNewTab();
			}else {
				System.out.println("no selected controller & control action");
			}
		});

		//if File Button is clicked, add new File with file chooser
		addFileButton.setOnAction(event -> AddFile());

		//Add new CTM TextField
		tabPane.getSelectionModel().getSelectedItem().setOnSelectionChanged(event->{
			String controller = controllerCB.getValue();
			String controlAction = tabPane.getSelectionModel().getSelectedItem().getId();
			Tab tab = tabPane.getSelectionModel().getSelectedItem();
			int id = tabPane.getSelectionModel().getSelectedIndex();

			//Controller - add

			TextFieldChange(controller,controlAction);
		});

	}

	private void TextFieldChange(String controller, String controlAction) {
		// TODO Auto-generated method stub

	}

	private void showControllerTabs(String Controller, int id) {
		//Remove All tab
		tabPane.getTabs().remove(0, tabPane.getTabs().size());

		// Add Controller - ControlAction Tab
		for(CTMDataStore c : ctmDataStoreList){
			if(c.getController()==Controller){
				//Add Data to new Tab
				addData(c,id);

			}
		}
	}

	private void addData(CTMDataStore c,int id) {
		//Add Data from DataStroe to Table
		//Get ContextHeader from PMM Table -> Modify? if PMM output Change
//		MakeTable(pm.getOutputNames().get(id));

		//create new tab
		Tab newTab = new Tab();

		//create new scroll pane for new tab
		ScrollPane scrollPane = new ScrollPane();

		//create new tableView for new scroll pane
		TableView<CTM> newTableView = new TableView<CTM>();

		//create new table column for new table view
		TableColumn<CTM,String> newCasesColumn = null,newHazardousColumn = null;
		TableColumn<CTM,Integer> newNoColumn = null;
		newCasesColumn.setPrefWidth(154);
		newCasesColumn.setResizable(false);
		newCasesColumn.setEditable(true);
		newTableView.getColumns().add(newCasesColumn);
		newNoColumn.setPrefWidth(42);
		newNoColumn.setResizable(false);
		newNoColumn.setEditable(true);
		newTableView.getColumns().add(newNoColumn);
		newHazardousColumn.setPrefWidth(107);
		newHazardousColumn.setResizable(false);
		newHazardousColumn.setEditable(true);
		int i=0;
		for(String s : pm.getOutputNames().get(id)){
			TableColumn<CTM,String> newContextColumn = new TableColumn<CTM,String>();
			newContextColumn.setText(s);
			newContextColumn.setPrefWidth(154);
			newContextColumn.setResizable(false);
			newContextColumn.setEditable(true);
			final int j = i;
			newContextColumn.setCellValueFactory(cellData -> cellData.getValue().getContextProperty(j));
			newContextColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			newTableView.getColumns().add(newContextColumn);
			i++;
		}
		newTableView.getColumns().add(newHazardousColumn);

		//Add Data in Table
		newCasesColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("casesList"));
		newNoColumn.setCellValueFactory(new PropertyValueFactory<CTM, Integer>("NoProperty"));
		newHazardousColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("hazardousList"));

		newCasesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		newHazardousColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		newTableView.setItems(c.getCTMTableList());
		newTab.setContent(scrollPane);
		scrollPane.setContent(newTableView);

		totalTableView.add(newTableView);
	}

	/*
	 * add new tab
	 */

//	@FXML
	public void addNewTab() {
		String controller = controllerCB.getValue();
		String controlAction = controlActionCB.getValue();

		//Controller- ControlAction PM exist confirm()
		int i=0;
		int j=0;
		ArrayList<String> contextheader = new ArrayList<String>();
		for(String c : pm.getControllerName()){
			if(c==controller){
				for(String ca : pm.getControlActionNames().get(i)){
					if(ca == controlAction){
						//? pmm -> Doesn't each control action have another Output?
						//contextheader = pm.getOutputNames().get(i).get(j);
						contextheader = pm.getOutputNames().get(i);
						i = pm.getControlActionNames().size();
						break;
					}
					j++;
				}
			}
			i++;
		}

		if(contextheader.isEmpty()){
			System.out.println("There is no"+controller+ ","+"controlAction"+"Process Model");
			return;
		}

		//create new tab
		Tab newTab = new Tab();

		//create new scroll pane for new tab
		ScrollPane scrollPane = new ScrollPane();

		//create new tableView for new scroll pane
		TableView<CTM> newTableView = new TableView<CTM>();

		//create new table column for new table view
		TableColumn<CTM,String> newCasesColumn = new TableColumn<>();
		TableColumn<CTM,String> newHazardousColumn = new TableColumn<>();
		TableColumn<CTM,Integer> newNoColumn = new TableColumn<>();
		newCasesColumn.setPrefWidth(154);
		newCasesColumn.setResizable(false);
		newCasesColumn.setEditable(true);
		newCasesColumn.setText("Cases");
		newTableView.getColumns().add(newCasesColumn);
		newNoColumn.setPrefWidth(42);
		newNoColumn.setResizable(false);
		newNoColumn.setEditable(true);
		newNoColumn.setText("No");
		newTableView.getColumns().add(newNoColumn);
		newHazardousColumn.setPrefWidth(107);
		newHazardousColumn.setResizable(false);
		newHazardousColumn.setEditable(true);
		newHazardousColumn.setText("Hazardous");

		//Make Context Column
		i=0;
		for(String s : contextheader){
			TableColumn<CTM,String> newContextColumn = new TableColumn<CTM,String>();
			newContextColumn.setText(s);
			newContextColumn.setPrefWidth(154);
			newContextColumn.setResizable(false);
			newContextColumn.setEditable(true);
			final int f = i;
			newContextColumn.setCellValueFactory(cellData -> cellData.getValue().getContextProperty(f));
			newContextColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			newTableView.getColumns().add(newContextColumn);
			i++;
		}
		newTableView.getColumns().add(newHazardousColumn);

		//Set DataStore
		CTMDataStore newCtmDataStore = new CTMDataStore();
		newCtmDataStore.setControlAction(controlAction);
		newCtmDataStore.setController(controller);
		ctmDataStoreList.add(newCtmDataStore);

		//Set Table Context
		newCasesColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("casesList"));
		newNoColumn.setCellValueFactory(new PropertyValueFactory<CTM, Integer>("NoProperty"));
		newHazardousColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("hazardousList"));
		newCasesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		newHazardousColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		//set new tab's name
		newTab.setText(controllerCB.getValue() + "-" + controlActionCB.getValue());
		//add new tab into tabPane
		tabPane.getTabs().add(newTab);
		//set scrollPane in new tab
		newTab.setContent(scrollPane);
		//set new tableView in new scroll pane
		scrollPane.setContent(newTableView);
		//Set Table - DataStore
		newTableView.setItems(newCtmDataStore.getCTMTableList());


		//Add totalTableView
		totalTableView.add(newTableView);

	}
	//open file chooser to get MCS File
	@FXML
	public void AddFile() {
        FileChooser fc = new FileChooser();

        //file chooser setting
        fc.setTitle("Choose file to Apply");
        fc.setInitialDirectory(new File(Info.directory));

        //only get .txt file format(MCS File)
        ExtensionFilter txtType = new ExtensionFilter("MCS file (*.txt)", "*.txt");
        fc.getExtensionFilters().addAll(txtType);

	    selectedFile =  fc.showOpenDialog(null);
        if(selectedFile != null) {
	        try {
				ApplyFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

	//Apply chosen MCS File
	public int ApplyFile() throws IOException {

		if(selectedFile != null) {
	        try {
	            FileInputStream fis = new FileInputStream(selectedFile);

	            byte [] buffer = new byte[fis.available()];
	            String temp="";
	            while((fis.read(buffer)) != -1) {
	            	temp = new String(buffer);
	            }
	            fis.close();

	            String[] temps = new String[1000];
	            temps = temp.split("\n");
	            this.ParseMCS(temps);

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		}
		return 0;
	}

	private void ParseMCS(String[] temps) {
		//Get Data form mcs.txt file
		String[][] context = new String[contextList.size()][temps.length];

		int i=0;
		while(i < temps.length) {
			String[] splits = temps[i].split("&");
			int j=0;
			int temp=-1;

			while(j < splits.length) {
				int index= splits[j].indexOf("=");
				if(index>=0) {
					for(int t=0;t<contextList.size();t++) { //header loop
						if(splits[j].contains(contextList.get(t))) {
							if(context[t][i]==null) {
								context[t][i] = splits[j].substring(index+1);
								if(context[t][i].substring(0,1).contains("=")) {
									context[t][i] = context[t][i].replace("= ", "");
								}
								if(splits[j].substring(0,index).contains("!")) {
									if(splits[j].contains("false")) context[t][i] = "true";
									else if(splits[j].contains("true")) context[t][i] = "false";
								}
								if(splits[j].contains("<=")){
									context[t][i] = splits[j].replace(contextList.get(t), "x");
									context[t][i] = context[t][i].replace("(A)", "");
								}
							} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
								context[t][i] += (" & \n" + splits[j].substring(index+1));
							}
							temp = t;
							break;
						}
					}
				} else if(index < 0 && temp >= 0) {
					if(context[temp][i]==null || context[temp][i].contains("true") || context[temp][i].contains("false")) {
						if(splits[j].length()!=1) {
							context[temp][i]=splits[j];
						}
					}else if(!context[temp][i].isEmpty() && !context[temp][i].contains("<=")){
						context[temp][i] += (" & \n" +splits[j]);
					}
					temp = -1;
				}
				j++;
			}
			i++;
		}

		for(int x=0;x<contextList.size();x++) {
			for(int y=0;y<temps.length;y++) {
				if(context[x][y]==null) {
					context[x][y] = "N/A";
				}
			}
		}

		for(int y=0;y<temps.length;y++) {
	        String[] contexts = new String[contextList.size()];
			for(int x=0;x<contextList.size();x++) {
				contexts[x] = context[x][y];
			}

	   		ComboBox<String> casesComboBox = new ComboBox(cases);
	   		ComboBox<String> hazardComboBox = new ComboBox(hazardous);

//			totalData.get(curControllerNum).getCTMTableList().add(
//					new CTM(controllerName.get(curControllerNum), controlActionNames.get(curCANum),comboBox1,totalData.get(curControllerNum).tableSize+1,contexts,comboBox2)
//			);
		}
	}
}