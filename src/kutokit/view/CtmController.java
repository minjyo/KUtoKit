package kutokit.view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import kutokit.Info;
import kutokit.MainApp;
import kutokit.model.ctm.CTM;
import kutokit.model.ctm.CTMDataStore;
import kutokit.model.utm.UCADataStore;

public class CtmController {

	private MainApp mainApp;
	private File selectedFile;
	private ObservableList<CTM> myTable;
	@FXML private Label filename;
	@FXML private Pane AddFile;
	
	@FXML private TextField TextFieldCA;
	@FXML private TextField TextFieldCases;
	@FXML private TextField TextFieldContext1,TextFieldContext2,TextFieldContext3,TextFieldContext4,TextFieldContext5,TextFieldContext6,TextFieldContext7,TextFieldContext8;
	
	@FXML private TableView<CTM> contextTable;
	@FXML private TableColumn<CTM, String> CAColumn, casesColumn;
	@FXML private TableColumn<CTM, Integer> noColumn;
	@FXML private TableColumn hazardousColumn;
	final HBox hb = new HBox();
	
	private String[] no = new String[100];
	private String context[][] = new String[15][100];
	private String[] contextheader = new String[15];
	
	ObservableList<CTM> mcsData;

	private ObservableList<String> hazardousOX;

	private static CTMDataStore ctmDataStore;
	
	
	ObservableList<String> effectiveVariable;
	String CA;
	String output;
	
	int i=0, k=0; //k=headers length
	
	// constructor
	public CtmController() {
		
	}

	//set MainApp
	public void setMainApp(MainApp mainApp)  {
		this.mainApp = mainApp;
		ctmDataStore = mainApp.ctmDataStore;
		mcsData = ctmDataStore.getCTMTableList();
	}

	private void initialize(){

	}
	
	@FXML
	public void AddFile() {
		effectiveVariable = mainApp.models.getValuelist();
		CA = mainApp.models.getControlActionName();
		output = mainApp.models.getOutputName();
		System.out.println("eV:"+effectiveVariable);
		System.out.println("CA:"+CA);
		System.out.println("output:"+output);
        FileChooser fc = new FileChooser();
        fc.setTitle("Add File");
        // fc.setInitialDirectory(new File("C:/")); // default 디렉토리 설정
        // minjyo - mac
        fc.setInitialDirectory(new File(Info.directory));
        // 확장자 제한
        ExtensionFilter txtType = new ExtensionFilter("text file", "*.txt", "*.doc");
        fc.getExtensionFilters().addAll(txtType);
         
	    selectedFile =  fc.showOpenDialog(null);
        if(selectedFile != null) {
	        //System.out.println(selectedFile); 
	        //System.out.println(selectedFile.getName());
	        filename.setText(selectedFile.getName());
        }
    }
	
	@FXML
	public void ApplyFile() throws IOException {
		
		if(selectedFile != null) {
			AddFile.getChildren().clear();
	
			// 1. Read MCS File
	        try {
	            FileInputStream fis = new FileInputStream(selectedFile);
	            //BufferedInputStream bis = new BufferedInputStream(fis);
	            
	            byte [] buffer = new byte[fis.available()];
	            String temp="";
	            while((fis.read(buffer)) != -1) {
	            	temp = new String(buffer);
	            }    
	            fis.close();
	           
	            //2. Add Parsing File
	            String[] temps = new String[1000];
	            temps = temp.split("\n");
	            
	            this.ParseMSC(temps);
	            
	            
	            this.MakeTable();
	            this.fillContextTable();

	            //bis.close();    
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		}
	}

	private void ParseMSC(String[] temps) {

		if(k==0) {
			contextheader[k++] = output;
		}
		for(int x=0;x<effectiveVariable.size();x++) {
			contextheader[k++] = effectiveVariable.get(x);
		}
		System.out.println(Arrays.toString(contextheader));
		
		int i=0;
		while(i < temps.length) {
			no[i] = temps[i].substring(0, 1);
			String[] splits = temps[i].split("&");
			int j=0;
			int temp=0;

			while(j < splits.length) {
				int index= splits[j].indexOf("=");
				if(index>=0 && splits[j].substring(0,index).contains(output.substring(2,9))) { //variable name
					for(int t=0;t<=k;t++) { //header loof
						if(contextheader[t]!=null && splits[j].substring(0,index).contains(contextheader[t])) {
							if(context[t][i]==null) {
								context[t][i] = splits[j].substring(index+1);
								if(context[t][i].substring(0,1).contains("=")) { //th_HI_LOG_POWER_Trip_Logic_state
									context[t][i] = context[t][i].replace("= ", "");
								}
								if(splits[j].contains("!")) {
									if(splits[j].contains("false")) context[t][i] = "true";
									else if(splits[j].contains("true")) context[t][i] = "false";
								}
							} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
								context[t][i] += (" & \n" + splits[j].substring(index+1));
							}
							temp = t;
							break;
						}
						
						/*if(t==k && k>0) { //not contains header but variable
							contextheader[k] = splits[j].substring(0,index);
							if(contextheader[k].contains("(A)")) {
								int a= splits[j].indexOf("(A)");
								contextheader[k] = contextheader[k].substring(a+3);
							}
							if(contextheader[k].contains("!")) {
								contextheader[k] = contextheader[k].substring(0,contextheader[k].length()-1);
								if(splits[j].contains("false")) context[k][i] = "true";
								else context[k][i] = "false";
							} else {
								context[k][i] = splits[j].substring(index+1);
							}
							if(k<15-1) {k++;break;}
							temp = t;
						}*/
					}
				} else if(index < 0) {
					if(context[temp][i]==null || context[temp][i].contains("true") || context[temp][i].contains("false")) {
						context[temp][i]=splits[j];
					}else {
						//context[temp][i] += (" & \n" +splits[j]);
					}
				}		
				j++;
			}
			i++;
		}
		
		for(int x=0;x<k;x++) {
			for(int y=0;y<100;y++) {
				if(context[x][y]==null) {
					context[x][y] = "N/A";
				}
			}
		}
		//System.out.println(Arrays.toString(f_HI_LOG_POWER_Trip_Out));
	}
	
	private void MakeTable() {
		contextTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        // 4. Set table row 
        CAColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("controlAction"));
   	 	casesColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("cases"));
 	 	noColumn.setCellValueFactory(new PropertyValueFactory<CTM, Integer>("no"));
 	    
	   	CAColumn.setCellValueFactory(cellData -> cellData.getValue().getControlActionProperty());
	   	casesColumn.setCellValueFactory(cellData -> cellData.getValue().getCasesProperty());
	   	noColumn.setCellValueFactory(cellData -> cellData.getValue().getNoProperty().asObject());

	    contextTable.setEditable(true);
	    
 		for(final int[] x= {0,};x[0]<k;x[0]++) {
 			TableColumn<CTM, String> contextColumn = new TableColumn<>(contextheader[x[0]]);
 			contextTable.getColumns().add(contextColumn);
 			contextColumn.setPrefWidth(80.0);
 			contextColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>(contextheader[x[0]]));
 			int temp = x[0];
 			contextColumn.setCellValueFactory(cellData -> cellData.getValue().getContextProperty(temp));
 			contextColumn.setCellFactory(TextFieldTableCell.forTableColumn());
 			contextColumn.setOnEditCommit(
 	            new EventHandler<CellEditEvent<CTM, String>>() {
 	                @Override
 	                public void handle(CellEditEvent<CTM, String> t) {
 	                    ((CTM) t.getTableView().getItems().get(
 	                        t.getTablePosition().getRow())
 	                        ).setContext(temp, t.getNewValue());
 	                }
 	            }
 	        );
 		}

 	    hazardousColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("hazardous"));
 	    //test1.setCellValueFactory(new PropertyValueFactory<CTM, String>("test1"));
 	    //test2.setCellValueFactory(new PropertyValueFactory<CTM, String>("test2"));
 	    //contextsColumn.getColumns().addAll(test1,test2);
 	    
	   	// 5. Put data in table
	   	//contextsColumn.setCellValueFactory(cellData -> cellData.getValue().getContextsProperty());
	   	//hazardousColumn.setCellValueFactory(cellData -> cellData.getValue().getHazardousProperty());
	   	//hazardousColumn.setCellFactory(ComboBoxTableCell.forTableColumn("Friends", "Family", "Work Contacts"));
	    
	    //contextTable.setEditable(true);
	}
	
	public void fillContextTable() {
		mcsData = FXCollections.observableArrayList();

		hazardousOX = FXCollections.observableArrayList();
		hazardousOX.add("O");
		hazardousOX.add("X");
		
	    // 3. Create Data list ex
		while(i < no.length) {
			String[] contexts = new String[k];
			for(int t=0;t<k;t++) {
				contexts[t] = context[t][i];
			}
			ComboBox<String> comboBox = new ComboBox(hazardousOX);
			//System.out.println(Arrays.toString(contexts));
			mcsData.add(new CTM(CA, "Not provided\ncauses hazard", i+1, contexts, comboBox));
			i++;
		};
	    contextTable.setItems(mcsData);
	}
	
	@FXML
	public void onEditChange(TableColumn.CellEditEvent<CTM, String> productStringCellEditEvent) {
		CTM temp = contextTable.getSelectionModel().getSelectedItem();
		System.out.println(temp);
		System.out.println(productStringCellEditEvent.getRowValue());
		

		//System.out.println(temp.getContext(0));
		//System.out.println(productStringCellEditEvent.getRowValue().getContext(0));
		
		//System.out.println(productStringCellEditEvent.getRowValue().getContext(0));
		//context[productStringCellEditEvent.getTablePosition().getColumn()-3][productStringCellEditEvent.getTablePosition().getRow()]=productStringCellEditEvent.getNewValue();
		//System.out.println(context[productStringCellEditEvent.getTablePosition().getColumn()-3][productStringCellEditEvent.getTablePosition().getRow()]);
		//mcsData.set(temp.getNo()-1, productStringCellEditEvent.getRowValue());
		
		//System.out.println(mcsData.get(temp.getNo()-1).getContext(0));
		//Todo :: @@@@@@@@Edit Value@@@@@@@@@@
		
	}
	
	public ObservableList<CTM> getContextTableData() {
	       System.out.println(myTable.get(0));
	      return myTable;
	}
	
	@FXML
	public void addContext(ActionEvent actionEvent) {
		//CTM newContext = new CTM(TextFieldCA.getText(), TextFieldCases.getText(), ++i, TextFieldContext1.getText(), TextFieldContext2.getText(), TextFieldContext3.getText(), TextFieldContext4.getText(), TextFieldContext5.getText(), TextFieldContext6.getText(), TextFieldContext7.getText(), TextFieldContext8.getText(), FXCollections.observableArrayList("O","X"));
		//contextTable.getItems().add(newContext);
	}
	
	@FXML
	public void closeAddFile(ActionEvent actionEvent) {

		effectiveVariable = mainApp.models.getValuelist();
		CA = mainApp.models.getControlActionName();
		output = mainApp.models.getOutputName();
		System.out.println("eV:"+effectiveVariable);
		System.out.println("CA:"+CA);
		System.out.println("output:"+output);
		
		if(k==0) {
			contextheader[k++] = output;
		}
		for(int x=0;x<effectiveVariable.size();x++) {
			contextheader[k++] = effectiveVariable.get(x);
		}
		
		
		final TextField addCA = new TextField();
		addCA.setPromptText("Control Action");
		addCA.setMaxWidth(CAColumn.getPrefWidth());
        final TextField addCases = new TextField();
        addCases.setMaxWidth(casesColumn.getPrefWidth());
        addCases.setPromptText("cases");
        /*final TextField addHazardous = new TextField();
        addHazardous.setMaxWidth(hazardousColumn.getPrefWidth());
        addHazardous.setPromptText("hazardous");*/
        final TextField addContext = new TextField();
        addContext.setMaxWidth(hazardousColumn.getPrefWidth());
        addContext.setPromptText("contexts");
        String[] contexts = new String[1];
		for(int t=0;t<1;t++) {
			contexts[t] = addContext.getText();
		}
 
        final Button addButton = new Button("Add");
		hazardousOX = FXCollections.observableArrayList();
		hazardousOX.add("O");
		hazardousOX.add("X");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
        		ComboBox<String> comboBox = new ComboBox(hazardousOX);
                mcsData.add(new CTM(addCA.getText(),addCases.getText(),i++,contexts,comboBox));
                addCA.clear();
                addCases.clear();
                //addHazardous.clear();
            }
        });
        hb.getChildren().addAll(addCA, addCases, addContext, addButton);
        hb.setSpacing(3);
 
        /*final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(contextTable, hb);
        vbox.show();*/
		
		AddFile.getChildren().clear();
		MakeTable();
	}
	
}