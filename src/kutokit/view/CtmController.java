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

	private MainApp mainApp;
	private File selectedFile;
	private ObservableList<CTM> myTable;

	private static CTMDataStore ctmDataStore;
	
	@FXML private AnchorPane CTMPane;
	@FXML private Label filename;
	@FXML private Pane AddFile;
	private TabPane tabPane = new TabPane();

	//private TableView<CTM> contextTable = new TableView<CTM>();
	//private TableColumn<CTM, String> CAColumn = new TableColumn<CTM,String>("Control Action");
	//private TableColumn<CTM, String> casesColumn = new TableColumn<CTM,String>("cases");
	//private TableColumn<CTM, Integer> noColumn = new TableColumn<CTM,Integer>("No.");
	//private TableColumn hazardousColumn = new TableColumn("Hazardous?");
	
	//private String[] no = new String[100];
	//private String[] contextheader = new String[15];
	
	ArrayList<ObservableList<CTM>> totalData = new ArrayList<>();
	private int controllerCount = 0;
	private ObservableList<String> hazardousOX;
	
	private String controllerName;
	private ArrayList<String> controlActionNames;
	private ArrayList<String> outputNames;
	private ObservableList<String> valuelist;
	private ArrayList<String> contextheader;

	private TableView<CTM>[] totalTable;
	
	public CtmController() { }
	private void initialize(){ }
	
	

	//set MainApp
	public void setMainApp(MainApp mainApp)  {

		AddFile.setVisible(false);
		
		this.mainApp = mainApp;
		ctmDataStore = mainApp.ctmDataStore;
		//mcsData = ctmDataStore.getCTMTableList(); //TODO
		
		controllerName = mainApp.models.getControllerName();
		controlActionNames = mainApp.models.getControlActionName();
		outputNames = mainApp.models.getOutputNames();
		valuelist = mainApp.models.getValuelist();
		contextheader = new ArrayList<>();
		
		System.out.println("controllerName:"+controllerName);
		System.out.println("controlActionNames:"+controlActionNames);
		System.out.println("outputNames:"+outputNames);
		System.out.println("valuelist:"+valuelist);
		
		int headerlength = 0;
		if(headerlength==0) {
			for(int x=0;x<outputNames.size();x++) {
				contextheader.add(outputNames.get(x));
			}
		}
		for(int x=0;x<valuelist.size();x++) {
			contextheader.add(valuelist.get(x));
		}
		
		final ToggleGroup group = new ToggleGroup();
 		HBox radioGroup = new HBox();

		for(int i=0;i<1;i++) {
	 		RadioButton rb = new RadioButton(controllerName);
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
	 		controllerCount++;
		}
		
		for(int i=0;i<controlActionNames.size();i++) {
			tabPane.getTabs().add(MakeTab(i,controlActionNames.get(i), contextheader));
		}
        tabPane.setLayoutY(30.0);
        tabPane.prefWidthProperty().bind(CTMPane.widthProperty());
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        CTMPane.getChildren().addAll(radioGroup,tabPane);
	}
	
	
	
	public Tab MakeTab(int tabNum, String caName, ArrayList<String> contextheader) {
		//final int row=0; //row= 테이블 길이..파일 파싱이후 데이터 추가했을때를 생각해야
		ObservableList<CTM> mcsData = FXCollections.observableArrayList();
        final TableView<CTM> contextTable = this.MakeTable(contextheader);
        
        String[][] context = new String[15][1024];
        int len = 0;
        
		Tab tab = new Tab();
		tab.setText(caName);
		HBox hb = new HBox();
		VBox totalhb = new VBox();
		
		//TODO ==> AddFile modify
		final Button fileButton = new Button("File PopUp");
        fileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
        		//AddFile.setVisible(true); //TODO!!!!!!!! 파일팝업이랑 연
        		//AddFile.toFront();
            	AddFile();
            }
        });
        hb.getChildren().addAll(fileButton);
		
        final TextField addCases = new TextField();
        addCases.setPrefWidth(80.0);
        addCases.setPromptText("cases");
        hb.getChildren().addAll(addCases);

        String[] contexts = new String[contextheader.size()];
        final TextField[] addContexts = new TextField[contextheader.size()];
		for(int t=0;t<contextheader.size();t++) {
	        final TextField addContext = new TextField();
	        addCases.setPrefWidth(80.0);
	        addContext.setPromptText(contextheader.get(t));
			addContexts[t] = addContext;
			hb.getChildren().addAll(addContexts[t]);
		}
 
        final Button addButton = new Button("Add");
		hazardousOX = FXCollections.observableArrayList();
		hazardousOX.add("O");
		hazardousOX.add("X");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
        		int temp = contextTable.getItems().size();
        		ComboBox<String> comboBox = new ComboBox(hazardousOX);
        		for(int t=0;t<contextheader.size();t++) {
        			contexts[t] = addContexts[t].getText();
        			context[t][temp] = addContexts[t].getText();
        			addContexts[t].clear();
        		}
        		//TODO 편집이벤트 달리는지 확인하
        		mcsData.add(new CTM(controllerName, caName,addCases.getText(),1+temp,contexts,comboBox));
    			comboBox.valueProperty().addListener(new ChangeListener<String>() {
  			      @Override
  			      public void changed(ObservableValue observable, String oldValue, String newValue) {
  			    	mcsData.get(temp).setHazardousValue(newValue);
  			      }
  			    });
    			contextTable.setItems(mcsData);
        		totalData.add(mcsData);
                addCases.clear();
            }
        });
        hb.getChildren().addAll(addButton);
        hb.setSpacing(3);
        totalhb.getChildren().addAll(hb,contextTable);
        tab.setContent(totalhb);
        
        return tab;
	}
	
	public TableView<CTM> MakeTable(ArrayList<String> contextheader) {
		TableView<CTM> contextTable = new TableView<CTM>();
		contextTable.prefWidthProperty().bind(CTMPane.widthProperty());
		contextTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<CTM, String> CAColumn = new TableColumn<CTM,String>("Control Action");
		TableColumn<CTM, String> casesColumn = new TableColumn<CTM,String>("cases");
		TableColumn<CTM, Integer> noColumn = new TableColumn<CTM,Integer>("No.");
		TableColumn hazardousColumn = new TableColumn("Hazardous?");

		CAColumn.setPrefWidth(100.0);
		casesColumn.setPrefWidth(90.0);
		noColumn.setPrefWidth(30.0);
		hazardousColumn.setPrefWidth(100.0);
		
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
	                }
	            }
	 	    );
		contextTable.getColumns().addAll(CAColumn, casesColumn, noColumn);
	    
 		for(final int[] x= {0,};x[0]<contextheader.size();x[0]++) {
 			TableColumn<CTM, String> contextColumn = new TableColumn<>(contextheader.get(x[0]));
 			contextTable.getColumns().add(contextColumn);
 			contextColumn.setPrefWidth(80.0);
 			contextColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>(contextheader.get(x[0])));
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
 		
 		return contextTable;
	}
	
	@FXML
	public void AddFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Add File");
        fc.setInitialDirectory(new File(Info.directory));
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
	            
	            String[][] data = this.ParseMSC(temps);
	            //this.fillContextTable();
	            
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		}
	}

	private String[][] ParseMSC(String[] temps) {
		String[][] context = new String[15][1024];
		
		int i=0;
		while(i < temps.length) {
			//no[i] = temps[i].substring(0, 1);
			String[] splits = temps[i].split("&");
			int j=0;
			int temp=-1;

			while(j < splits.length) {
				int index= splits[j].indexOf("=");
				if(index>=0) {
					for(int t=0;t<=contextheader.size();t++) { //header loof
						if(splits[j].contains(contextheader.get(t))) {
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
									context[t][i] = splits[j].replace(contextheader.get(t), "x");
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
		
		for(int x=0;x<contextheader.size();x++) {
			for(int y=0;y<100;y++) { //TODO table length.....
				if(context[x][y]==null) {
					context[x][y] = "N/A";
				}
			}
		}
		return context;
	}
	
	/*public void fillContextTable(TableView<CTM> table) {
		int i = table.getItems().size()-1;
		if(i<0) return;
		hazardousOX = FXCollections.observableArrayList();
		hazardousOX.add("O");
		hazardousOX.add("X");
		
		ObservableList<CTM> testmcsData = FXCollections.observableArrayList();
		testmcsData = totalData.get(0);
		///TODO 여기서부터!!!!!!!!!!!!!!1 totalData에 mcsData 넣을 때 index 넣는법 알기. 그래야 현재 mcsData파싱가
		
		
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
	}*/
	
	/*public void fillContextTable() {
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