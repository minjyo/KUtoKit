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

	private static CTMDataStore ctmDataStore;
	
	@FXML private AnchorPane CTMPane;
	@FXML private Label filename;
	@FXML private Pane AddFile;
	private TabPane tabPane = new TabPane();
	
	ArrayList<ObservableList<CTM>> totalData = new ArrayList<>();
	ObservableList<CTM> mcsData;
	private int controllerCount = 0;
	private int curControllerNum, curCANum;
	private ObservableList<String> hazardousOX;
	private ObservableList<String> casesCombo;

	private ArrayList<String> controllerName; // Selected controller from CSE
	private ArrayList<String> controlActionNames;
	private ArrayList<String> outputNames;
	private ObservableList<String> valuelist;
	private ArrayList<String> selectedCA = new ArrayList<String>();// 선택된 control action 저장 
	private ArrayList<String> selectedOutput = new ArrayList<String>(); // 선택된 output Variables 저장 
	
	private ArrayList<String> contextheader;
	
	public CtmController() { }
	private void initialize(){
        tabPane.setPrefWidth(1000.0);
	}

	//set MainApp
	public void setMainApp(MainApp mainApp)  {
		System.out.println("111@");

		AddFile.setVisible(false);
		
		this.mainApp = mainApp;
		System.out.println("222@");
		ctmDataStore = mainApp.ctmDataStore;
		System.out.println("333@");
		totalData = ctmDataStore.getCTMTableList();
		System.out.println("444@");
		
		controllerName = mainApp.models.getControllerName();
		//controlActionNames = mainApp.models.getControlActionName();
		//outputNames = mainApp.models.getOutputNames();
		valuelist = mainApp.models.getValuelist();
		contextheader = new ArrayList<>();

		System.out.println("controllerName:"+controllerName.toString());
		System.out.println("controlActionNames:"+controlActionNames.toString());
		System.out.println("outputNames:"+outputNames.toString());
		System.out.println("valuelist:"+valuelist.toString());
		
		hazardousOX = FXCollections.observableArrayList();
		hazardousOX.add("O");
		hazardousOX.add("X");
		
		casesCombo = FXCollections.observableArrayList();
		casesCombo.add("not providing\ncauses hazard");
		casesCombo.add("too early, too late,\nout of order");
		casesCombo.add("providing causes hazard");
		
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

		for(int i=0;i<controllerName.size();i++) {
	 		RadioButton rb = new RadioButton(controllerName.get(i));
	 		rb.setToggleGroup(group);
	 		if(i==0) {
	 			rb.setSelected(true);
	 		}
	 		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
	 		    public void changed(ObservableValue<? extends Toggle> ov,
	 		        Toggle old_toggle, Toggle new_toggle) {
	 		            if (group.getSelectedToggle() != null) {
	 		            	//TODO link radio
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
        tabPane.setPrefWidth(1000.0);
        tabPane.setPrefHeight(800.0);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        CTMPane.getChildren().addAll(radioGroup,tabPane);
		System.out.println("@@@@:9");
	}
	
	
	
	public Tab MakeTab(int tabNum, String caName, ArrayList<String> contextheader) {
		//final int row=0; //row= 테이블 길이..파일 파싱이후 데이터 추가했을때를 생각해야
        final TableView<CTM> contextTable = this.MakeTable(contextheader);
		mcsData = FXCollections.observableArrayList();
        if(totalData.size() >= tabNum+1) { 
        	mcsData = totalData.get(tabNum);
			contextTable.setItems(mcsData);
        }
        contextTable.setPrefHeight(800.0);

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
        		curCANum = tabNum;
        		curControllerNum = 0; //TODO!!!!!!!! link radio
        		AddFile.setVisible(true);
        		AddFile.toFront();
            }
        });
        hb.getChildren().addAll(fileButton);

        String[] contexts = new String[contextheader.size()];
        final TextField[] addContexts = new TextField[contextheader.size()];
		for(int t=0;t<contextheader.size();t++) {
	        final TextField addContext = new TextField();
	        addContext.setPromptText(contextheader.get(t));
			addContexts[t] = addContext;
			hb.getChildren().addAll(addContexts[t]);
		}

        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
        		int temp = contextTable.getItems().size();
        		for(int t=0;t<contextheader.size();t++) {
        			contexts[t] = addContexts[t].getText();
        			addContexts[t].clear();
        		}
        		ComboBox<String> comboBox1 = new ComboBox(casesCombo);
        		ComboBox<String> comboBox2 = new ComboBox(hazardousOX);
        		mcsData.add(new CTM(controllerName.get(tabNum), caName,comboBox1,1+temp,contexts,comboBox2));
        		comboBox1.valueProperty().addListener(new ChangeListener<String>() {
    			      @Override
    			      public void changed(ObservableValue observable, String oldValue, String newValue) {
    			    	mcsData.get(temp).setCasesValue(newValue);
    			      }
    			    });
        		comboBox2.valueProperty().addListener(new ChangeListener<String>() {
  			      @Override
  			      public void changed(ObservableValue observable, String oldValue, String newValue) {
  			    	mcsData.get(temp).setHazardousValue(newValue);
  			      }
  			    });
    			contextTable.setItems(mcsData);
            }
        });
        hb.getChildren().addAll(addButton);
        hb.setSpacing(3);
        totalhb.getChildren().addAll(hb,contextTable);
        tab.setContent(totalhb);

        if(totalData.size()<= tabNum) { 
        	totalData.add(mcsData);
        } else {
        	totalData.set(tabNum, mcsData);
        }

        return tab;
	}
	
	public TableView<CTM> MakeTable(ArrayList<String> contextheader) {
		TableView<CTM> contextTable = new TableView<CTM>();
		
		contextTable.prefWidthProperty().bind(tabPane.widthProperty());
		contextTable.prefWidth(1000.0);
		contextTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<CTM, String> CAColumn = new TableColumn<CTM,String>("Control Action");
		TableColumn casesColumn = new TableColumn("cases");
		TableColumn<CTM, Integer> noColumn = new TableColumn<CTM,Integer>("No.");
		TableColumn hazardousColumn = new TableColumn("Hazardous?");

		CAColumn.setPrefWidth(100.0);
		casesColumn.setPrefWidth(100.0);
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
		noColumn.setCellValueFactory(cellData -> cellData.getValue().getNoProperty().asObject());

		contextTable.setEditable(true);
	    
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
 	                   System.out.println((t.getTableView().getItems().get(t.getTablePosition().getRow()).getContext(temp)));
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
	public int ApplyFile() throws IOException {
		
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
	           
	            String[] temps = new String[1000];
	            temps = temp.split("\n");
	            this.ParseMSC(temps);
	            
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		}
		return 0;
	}

	private void ParseMSC(String[] temps) {
		String[][] context = new String[contextheader.size()][temps.length];

		int i=0;
		while(i < temps.length) {
			String[] splits = temps[i].split("&");
			int j=0;
			int temp=-1;

			while(j < splits.length) {
				int index= splits[j].indexOf("=");
				if(index>=0) {
					for(int t=0;t<contextheader.size();t++) { //header loof
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
			for(int y=0;y<temps.length;y++) {
				if(context[x][y]==null) {
					context[x][y] = "N/A";
				}
			}
		}
		
		for(int y=0;y<temps.length;y++) {
	        String[] contexts = new String[contextheader.size()];
			for(int x=0;x<contextheader.size();x++) {
				contexts[x] = context[x][y];
			}
			
	   		ComboBox<String> comboBox1 = new ComboBox(casesCombo);
	   		comboBox1.valueProperty().addListener(new ChangeListener<String>() {
			      @Override
			      public void changed(ObservableValue observable, String oldValue, String newValue) {
			    	 totalData.get(curControllerNum).get(curCANum).setCasesValue(newValue);
			      }
			    });
			
	   		ComboBox<String> comboBox2 = new ComboBox(hazardousOX);
	   		comboBox2.valueProperty().addListener(new ChangeListener<String>() {
			      @Override
			      public void changed(ObservableValue observable, String oldValue, String newValue) {
			    	 totalData.get(curControllerNum).get(curCANum).setHazardousValue(newValue);
			      }
			    });
			
			totalData.get(curControllerNum).add(
					new CTM(controllerName.get(curControllerNum), controlActionNames.get(curCANum),comboBox1,totalData.get(curControllerNum).size()+1,contexts,comboBox2)
			);
		}
	}
	
	@FXML
	public void closeAddFile(ActionEvent actionEvent) {
		AddFile.setVisible(false);
	}
	
}


