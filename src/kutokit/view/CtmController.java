package kutokit.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
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
	private int test;

	private MainApp mainApp;
	private File selectedFile;
	private ObservableList<CTM> myTable;
	@FXML private AnchorPane CTMPane;
	@FXML private Label filename;
	@FXML private Pane AddFile;

	private TableView<CTM> contextTable = new TableView<CTM>();
	private TableColumn<CTM, String> CAColumn = new TableColumn<CTM,String>("Control Action");
	private TableColumn<CTM, String> casesColumn = new TableColumn<CTM,String>("cases");
	private TableColumn<CTM, Integer> noColumn = new TableColumn<CTM,Integer>("No.");
	private TableColumn hazardousColumn = new TableColumn("Hazardous?");
	private TabPane tabPane = new TabPane();
	
	private String[] no = new String[100];
	private String context[][] = new String[15][1024];
	private String[] contextheader = new String[15];
	
	ObservableList<CTM> mcsData;
	ObservableList<CTM>[] totalData;
	private int testControllerCount = 0;
	
	
	
	private ObservableList<String> hazardousOX;

	private static CTMDataStore ctmDataStore;
	
	
	private ObservableList<String> effectiveVariable;
	private String CA;
	private String output;
	private String ControllerName;
	

	private String[] testCA;
	private String[] testControllerName;
	
	private TableView<CTM> testTable[];
	
	int i=0, k=0; //k=headers length
	
	// constructor
	public CtmController() {
		
	}

	//set MainApp
	public void setMainApp(MainApp mainApp)  {
		CAColumn.setPrefWidth(100.0);
		casesColumn.setPrefWidth(90.0);
		noColumn.setPrefWidth(30.0);
		hazardousColumn.setPrefWidth(100.0);

		AddFile.setVisible(false);
		
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
		
		//TODO - data binding
		testCA = new String[3];
		testControllerName = new String[3];
		testCA[0] = "CA1";
		testCA[1] = "CA2";
		testCA[2] = "CA3";
		testControllerName[0] = "Controller1";
		testControllerName[1] = "Controller2";
		testControllerName[2] = "Controller3";
		
		final ToggleGroup group = new ToggleGroup();
 		HBox radioGroup = new HBox();

		for(int i=0;i<testControllerName.length;i++) {
	 		RadioButton rb = new RadioButton(testControllerName[i]);
	 		rb.setToggleGroup(group);
	 		if(i==0) {
	 			rb.setSelected(true);
	 		}
	 		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
	 		    public void changed(ObservableValue<? extends Toggle> ov,
	 		        Toggle old_toggle, Toggle new_toggle) {
	 		            if (group.getSelectedToggle() != null) {
	 		            	//contextTable.setVisible(!contextTable.isVisible());
	 		            	System.out.println(new_toggle.toString());
	 		            }                
	 		        }
	 		});
	 		
	 		radioGroup.getChildren().add(rb);
	 		testControllerCount++;
		}
	 		
	 		
        //TableView<CTM> testcontextTable = new TableView<CTM>();
        //testcontextTable = this.testMakeTable();
        //testcontextTable.setLayoutY(70.0);
		for(int i=0;i<testCA.length;i++) {
			tabPane.getTabs().add(MakeTab(testCA[i]));
		}
        tabPane.setLayoutY(30.0);
        tabPane.prefWidthProperty().bind(CTMPane.widthProperty());
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        CTMPane.getChildren().addAll(radioGroup,tabPane);

        //this.MakeTable();
	}
	
	public Tab MakeTab(String caName, String[] contextheader) {
		i=0; //row count
		//final int row=0; //row= 테이블 길이..파일 파싱이후 데이터 추가했을때를 생각해야

		ObservableList<CTM> testmcsData = FXCollections.observableArrayList();
        final TableView<CTM> testcontextTable = this.MakeTable(contextheader); //table만들때 필요한것 => context header this.MakeTable(contextheader);
        
		Tab tab = new Tab();
		tab.setText(caName);

		HBox hb = new HBox();
		VBox totalhb = new VBox();
		
		//TODO ==> AddFile modify
		final Button fileButton = new Button("File PopUp");
        fileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
        		AddFile.setVisible(true);
        		AddFile.toFront();
            }
        });
        hb.getChildren().addAll(fileButton);
		
        final TextField addCases = new TextField();
        addCases.setPrefWidth(80.0);
        addCases.setPromptText("cases");
        hb.getChildren().addAll(addCases);

		//TODO ==> k??????? k = headerlength
        String[] contexts = new String[k];
        final TextField[] addContexts = new TextField[k];
		for(int t=0;t<k;t++) {
	        final TextField addContext = new TextField();
	        addCases.setPrefWidth(80.0);
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
        		testmcsData.add(new CTM(ControllerName, caName,addCases.getText(),1+i++,contexts,comboBox));
    			comboBox.valueProperty().addListener(new ChangeListener<String>() {
  			      @Override
  			      public void changed(ObservableValue observable, String oldValue, String newValue) {
  			    	testmcsData.get(temp).setHazardousValue(newValue);
  			      }
  			    });
    			testcontextTable.setItems(testmcsData);
                totalData[0].addAll(testmcsData);
                addCases.clear();
            }
        });
        hb.getChildren().addAll(addButton);
        hb.setSpacing(3);
        totalhb.getChildren().addAll(hb,testcontextTable);
        tab.setContent(totalhb);
        
        return tab;
	}
	
	public TableView<CTM> MakeTable(String[] contextheader) {
		TableView<CTM> testcontextTable = new TableView<CTM>();
		testcontextTable.prefWidthProperty().bind(CTMPane.widthProperty());
		testcontextTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<CTM, String> testCAColumn = new TableColumn<CTM,String>("Control Action");
		TableColumn<CTM, String> testcasesColumn = new TableColumn<CTM,String>("cases");
		TableColumn<CTM, Integer> testnoColumn = new TableColumn<CTM,Integer>("No.");
		TableColumn testhazardousColumn = new TableColumn("Hazardous?");
		

		testCAColumn.setPrefWidth(100.0);
		testcasesColumn.setPrefWidth(90.0);
		testnoColumn.setPrefWidth(30.0);
		testhazardousColumn.setPrefWidth(100.0);
		
		testcontextTable.prefWidthProperty().bind(CTMPane.widthProperty());
		testcontextTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // 4. Set table row 
		testCAColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("controlAction"));
		testcasesColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("cases"));
		testnoColumn.setCellValueFactory(new PropertyValueFactory<CTM, Integer>("no"));
		testhazardousColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("hazardous"));
 	    
		testCAColumn.setCellValueFactory(cellData -> cellData.getValue().getControlActionProperty());
		testcasesColumn.setCellValueFactory(cellData -> cellData.getValue().getCasesProperty());
		testnoColumn.setCellValueFactory(cellData -> cellData.getValue().getNoProperty().asObject());

	   	testcontextTable.setEditable(true);
	    
	   	testcasesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	   	testcasesColumn.setOnEditCommit(
	            new EventHandler<CellEditEvent<CTM, String>>() {
	                @Override
	                public void handle(CellEditEvent<CTM, String> t) {
	                    ((CTM) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setCases(t.getNewValue());
	                }
	            }
	 	    );
	   	testcontextTable.getColumns().addAll(testCAColumn, testcasesColumn, testnoColumn);
	    
 		for(final int[] x= {0,};x[0]<contextheader.length;x[0]++) {
 			TableColumn<CTM, String> contextColumn = new TableColumn<>(contextheader[x[0]]);
 			testcontextTable.getColumns().add(contextColumn);
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

 		testcontextTable.getColumns().add(testhazardousColumn);
 		
 		return testcontextTable;
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
			AddFile.setVisible(false);
	
	        try {
	            FileInputStream fis = new FileInputStream(selectedFile);
	            
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
	            this.fillContextTable();
	            
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
	
	public void testfillContextTable(TableView<CTM> table) {
		int i = table.getItems().size()-1;
		if(i<0) return;
		hazardousOX = FXCollections.observableArrayList();
		hazardousOX.add("O");
		hazardousOX.add("X");
		
		ObservableList<CTM> testmcsData = FXCollections.observableArrayList();
		testmcsData = totalData.get(0);
		///여기서부터!!!!!!!!!!!!!!1 totalData에 mcsData 넣을 때 index 넣는법 알기. 그래야 현재 mcsData파싱가
		
		
	    // 3. Create Data list ex
		while(i < no.length) {
	    	int temp = i;
			String[] contexts = new String[k];
			for(int t=0;t<k;t++) {
				contexts[t] = context[t][i];
			}
			ComboBox<String> comboBox = new ComboBox(hazardousOX);
			mcsData.add(new CTM(ControllerName, CA, "Not provided\ncauses hazard", i+1, contexts, comboBox));
			comboBox.valueProperty().addListener(new ChangeListener<String>() {
			      @Override
			      public void changed(ObservableValue observable, String oldValue, String newValue) {
			        mcsData.get(temp).setHazardousValue(newValue);
			      }
			    });
			i++;
		};
        totalData.add(mcsData);
        table.setItems(mcsData);
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
			mcsData.add(new CTM(ControllerName, CA, "Not provided\ncauses hazard", i+1, contexts, comboBox));
			comboBox.valueProperty().addListener(new ChangeListener<String>() {
			      @Override
			      public void changed(ObservableValue observable, String oldValue, String newValue) {
			        mcsData.get(temp).setHazardousValue(newValue);
			      }
			    });
			i++;
		};
        totalData.add(mcsData);
	    contextTable.setItems(mcsData);
	}
	
	/*private void MakeTable() {
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
	    
	    casesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
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
	   	contextTable.getColumns().addAll(CAColumn, casesColumn, noColumn);
	    
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

	   	contextTable.getColumns().add(hazardousColumn);
	}*/
	
	public ObservableList<CTM> getContextTableData() {
	       System.out.println(myTable.get(0));
	      return myTable;
	}
	
	@FXML
	public void closeAddFile(ActionEvent actionEvent) {
		AddFile.setVisible(false);
	}
	
}