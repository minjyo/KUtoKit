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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.AnchorPane;
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
	@FXML private AnchorPane CTMPane;
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
	private String context[][] = new String[15][1024];
	private String[] contextheader = new String[15];
	
	ObservableList<CTM> mcsData;

	private ObservableList<String> hazardousOX;

	private static CTMDataStore ctmDataStore;
	
	
	private ObservableList<String> effectiveVariable;
	private String CA;
	private String output;
	private String ControllerName;
	
	int i=0, k=0; //k=headers length
	
	// constructor
	public CtmController() {
		
	}

	//set MainApp
	public void setMainApp(MainApp mainApp)  {
		this.mainApp = mainApp;
		mcsData = FXCollections.observableArrayList();
		ctmDataStore = mainApp.ctmDataStore;
		mcsData = ctmDataStore.getCTMTableList();
		
		effectiveVariable = mainApp.models.getValuelist();
		CA = mainApp.models.getControlActionName();
		output = mainApp.models.getOutputName();
		ControllerName = mainApp.models.getControllerName();
		System.out.println("eV:"+effectiveVariable);
		System.out.println("CA:"+CA);
		System.out.println("output:"+output);
		System.out.println("ControllerName:"+ControllerName);
		
		if(k==0 && output!=null) {
			contextheader[k++] = output;
		}
		for(int x=0;x<effectiveVariable.size();x++) {
			contextheader[k++] = effectiveVariable.get(x);
		}
		

        final Button fileButton = new Button("File PopUp");
        fileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
        		AddFile.setVisible(!AddFile.isVisible());
            }
        });
        hb.getChildren().addAll(fileButton);
		
		final TextField addCA = new TextField();
		addCA.setPromptText("Control Action");
		addCA.setMaxWidth(CAColumn.getPrefWidth());
        final TextField addCases = new TextField();
        addCases.setMaxWidth(casesColumn.getPrefWidth());
        addCases.setPromptText("cases");
        hb.getChildren().addAll(addCA, addCases);
        
        String[] contexts = new String[k];
        final TextField[] addContexts = new TextField[k];
		for(int t=0;t<k;t++) {
	        final TextField addContext = new TextField();
	        addContext.setMaxWidth(hazardousColumn.getPrefWidth());
	        addContext.setPromptText(contextheader[t]);
			addContexts[t] = addContext;
			hb.getChildren().addAll(addContexts[t]);
		}
 
        final Button addButton = new Button("Add");
		hazardousOX = FXCollections.observableArrayList();
		hazardousOX.add("O");
		hazardousOX.add("X");
		int temp = i;
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
        		ComboBox<String> comboBox = new ComboBox(hazardousOX);
        		for(int t=0;t<k;t++) {
        			contexts[t] = addContexts[t].getText();
        			context[t][i] = addContexts[t].getText();
        			addContexts[t].clear();
        		}
                mcsData.add(new CTM(addCA.getText(),addCases.getText(),1+i++,contexts,comboBox));
    			comboBox.valueProperty().addListener(new ChangeListener<String>() {
  			      @Override
  			      public void changed(ObservableValue observable, String oldValue, String newValue) {
  			        mcsData.get(temp).setHazardousValue(newValue);
  			      }
  			    });
        	    contextTable.setItems(mcsData);
                addCA.clear();
                addCases.clear();
            }
        });
        hb.getChildren().addAll(addButton);
        hb.setSpacing(3);
        CTMPane.getChildren().addAll(hb);
        

        this.MakeTable();
	}

	private void initialize(){
		

	}
	
	@FXML
	public void AddFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Add File");
        // fc.setInitialDirectory(new File("C:/")); // default 디렉토리 설정
        fc.setInitialDirectory(new File(Info.directory));
        // 확장자 제한
        ExtensionFilter txtType = new ExtensionFilter("text file", "*.txt", "*.doc");
        fc.getExtensionFilters().addAll(txtType);
         
	    selectedFile =  fc.showOpenDialog(null);
        if(selectedFile != null) {
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
	            

	            //this.MakeTable();
	            this.fillContextTable();

	            //bis.close();    
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		}
	}

	private void ParseMSC(String[] temps) {
		int i=0;
		while(i < temps.length) {
			no[i] = temps[i].substring(0, 1);
			String[] splits = temps[i].split("&");
			int j=0;
			int temp=-1;

			while(j < splits.length) {
				int index= splits[j].indexOf("=");
				if(index>=0) {
					for(int t=0;t<=k;t++) { //header loof
						if(contextheader[t]!=null && splits[j].contains(contextheader[t])) {
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
									context[t][i] = splits[j].replace(contextheader[t], "x");
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
		
		for(int x=0;x<k;x++) {
			for(int y=0;y<no.length;y++) {
				if(context[x][y]==null) {
					context[x][y] = "N/A";
				}
			}
		}
		//System.out.println(Arrays.toString(f_HI_LOG_POWER_Trip_Out));
	}
	
	private void MakeTable() {
		//contextTable.prefHeightProperty().bind(CTMPane.heightProperty());
		contextTable.prefWidthProperty().bind(CTMPane.widthProperty());
		contextTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // 4. Set table row 
        CAColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("controlAction"));
   	 	casesColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("cases"));
 	 	noColumn.setCellValueFactory(new PropertyValueFactory<CTM, Integer>("no"));
 	    hazardousColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("hazardous"));
 	    
	   	CAColumn.setCellValueFactory(cellData -> cellData.getValue().getControlActionProperty());
	   	casesColumn.setCellValueFactory(cellData -> cellData.getValue().getCasesProperty());
	   	noColumn.setCellValueFactory(cellData -> cellData.getValue().getNoProperty().asObject());

	    contextTable.setEditable(true);
	    
	    CAColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	    casesColumn.setCellFactory(TextFieldTableCell.forTableColumn());

	   	CAColumn.setOnEditCommit(
            new EventHandler<CellEditEvent<CTM, String>>() {
                @Override
                public void handle(CellEditEvent<CTM, String> t) {
                    ((CTM) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setControlAction(t.getNewValue());
        			System.out.println(mcsData.get(4).getControlAction());
                }
            }
 	    );
	   	casesColumn.setOnEditCommit(
	            new EventHandler<CellEditEvent<CTM, String>>() {
	                @Override
	                public void handle(CellEditEvent<CTM, String> t) {
	                    ((CTM) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setCases(t.getNewValue());
	        			System.out.println(mcsData.get(4).getCases());
	                }
	            }
	 	    );

	    
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
	}
	
	public void fillContextTable() {
		hazardousOX = FXCollections.observableArrayList();
		hazardousOX.add("O");
		hazardousOX.add("X");
		
	    // 3. Create Data list ex
		while(i < no.length) {
	    	int temp = i;
			String[] contexts = new String[k];
			for(int t=0;t<k;t++) {
				contexts[t] = context[t][i];
			}
			ComboBox<String> comboBox = new ComboBox(hazardousOX);
			mcsData.add(new CTM(CA, "Not provided\ncauses hazard", i+1, contexts, comboBox));
			comboBox.valueProperty().addListener(new ChangeListener<String>() {
			      @Override
			      public void changed(ObservableValue observable, String oldValue, String newValue) {
			        mcsData.get(temp).setHazardousValue(newValue);
			      }
			    });
			i++;
		};
	    contextTable.setItems(mcsData);
	}
	
	public ObservableList<CTM> getContextTableData() {
	       System.out.println(myTable.get(0));
	      return myTable;
	}
	
	@FXML
	public void closeAddFile(ActionEvent actionEvent) {
		AddFile.setVisible(false);
	}
	
}