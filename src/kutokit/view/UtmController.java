package kutokit.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.event.ChangeListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kutokit.MainApp;
import kutokit.model.CTM;
import kutokit.model.ContextTableWrapper;
import kutokit.model.Contexts;
import kutokit.model.UCA;
import kutokit.view.components.Context;
import kutokit.view.components.Controller;
import kutokit.view.popup.ControllerPopUpController;
import kutokit.view.popup.UcaPopUpController;

public class UtmController {

	private MainApp mainApp;
	private Contexts dataStore;

	@FXML private TableView<UCA> ucaTable;
	@FXML private TableColumn<UCA, String> CAColumn, providingColumn, notProvidingColumn, incorrectColumn, stoppedColumn;

	private ContextMenu menu;
	private MenuItem modify_menu,add_menu,delete_menu;

	private static ObservableList<UCA> ucaData;
	//constructor
	public UtmController() {
	}

	private void initialize()
	{
		dataStore = mainApp.contexts;

		menu = new ContextMenu();

		add_menu = new MenuItem("Add");
		modify_menu = new MenuItem("Modify");
		delete_menu = new MenuItem("Delete");

		add_menu.setOnAction(new EventHandler<ActionEvent>() {
		     @Override
	         public void handle(ActionEvent event) {
		    	 ucaData.add(new UCA("Control Action","","","",""));
		    	 ucaTable.setItems(ucaData);
	         }
	     });

		modify_menu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
            	 ObservableList selectedCells = ucaTable.getSelectionModel().getSelectedCells();
            	 TablePosition tablePosition = (TablePosition) selectedCells.get(0);
            	 int selectedRow = tablePosition.getRow();
            	 int selectedColumn = tablePosition.getColumn();
            	 PopUp(selectedRow,selectedColumn);
            }
        });

        delete_menu.setOnAction(new EventHandler<ActionEvent>() {
	     @Override
         public void handle(ActionEvent event) {
	    	 int selectedUCA = ucaTable.getSelectionModel().getSelectedIndex();
	    	 ucaTable.getItems().remove(selectedUCA);
         }
        });
        menu.getItems().addAll(add_menu, modify_menu, delete_menu);

        ucaTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {
                menu.show(ucaTable, event.getScreenX(), event.getScreenY());
            }
        });

        return ;
	}

	//set MainApp
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		initialize();
	}

	public void setUcaTable(){
		ucaData = FXCollections.observableArrayList();
		//Bring Contexts
		if(dataStore.getContextId()>0){
			for(Context c : dataStore.getContext_Arr()){
				if(c.getHazardous()==true){
					ucaData.add(new UCA(c.getControlAction()));
				}
			}
		}
		return;
	}

	public void setUcaTable(ObservableList<CTM> contextTable) {
		// TODO Auto-generated method stub
		ucaData = FXCollections.observableArrayList();

		if(contextTable == null)
		{
			ucaData.add(new UCA("ex 1","","","",""));
			UCA uca = new UCA("ex_2","","","","");
			uca.setProvidingCausesHazard("ex_2_pch");
			ucaData.add(uca);
			CAColumn.setCellValueFactory(cellData -> cellData.getValue().getControlAction());
			providingColumn.setCellValueFactory(cellData -> cellData.getValue().getProvidingCausesHazard());
			notProvidingColumn.setCellValueFactory(cellData -> cellData.getValue().getNotProvidingCausesHazard());
			incorrectColumn.setCellValueFactory(cellData -> cellData.getValue().getIncorrectTimingOrOrder());
			stoppedColumn.setCellValueFactory(cellData -> cellData.getValue().getStoppedTooSoonOrAppliedTooLong());
			ucaTable.setItems(ucaData);
//			saveFile();
			return ;

		}
		for(int i=0;i<contextTable.size();i++)
		{
			//get hazardous error
//			if(contextTable.get(i).getHazardous())
//			{
//				ucaData.add(new UCA(contextTable.get(i).getControlAction(),"","","",""));
//			}
		}

		CAColumn.setCellValueFactory(cellData -> cellData.getValue().getControlAction());
		providingColumn.setCellValueFactory(cellData -> cellData.getValue().getProvidingCausesHazard());
		notProvidingColumn.setCellValueFactory(cellData -> cellData.getValue().getNotProvidingCausesHazard());
		incorrectColumn.setCellValueFactory(cellData -> cellData.getValue().getIncorrectTimingOrOrder());
		stoppedColumn.setCellValueFactory(cellData -> cellData.getValue().getStoppedTooSoonOrAppliedTooLong());
		ucaTable.setItems(ucaData);
	}

	private void PopUp(int row, int column) {
		  FXMLLoader loader = new FXMLLoader();
		  loader.setLocation(getClass().getResource("popup/UcaPopUpView.fxml"));
		  Parent popUp;

		  try {
			  	popUp = (Parent) loader.load();

				Scene scene = new Scene(popUp);
				UcaPopUpController pop = loader.getController();

				  Stage stage = new Stage();
				  stage.setScene(scene);
				  stage.show();

//				  Modify UcaTable with name when popup closed
				  stage.setOnHidden(new EventHandler<WindowEvent>() {
					    @Override
					    public void handle(WindowEvent e) {
					    	String text = pop.getModifyText();
					    	UCA uca = ucaData.get(row);
					    	switch (column)
			            	 {
			            	 case 0: //CAColumn
			            		 uca.setControlAction(text);
			            		 break;
			            	 case 1: //providingColumn
			            		 uca.setProvidingCausesHazard(text);
			            		 break;
			            	 case 2 : //notProvidingColumn
			            		 uca.setNotProvidingCausesHazard(text);
			            		 break;
			            	 case 3: //incorrectColumn
			            		 uca.setIncorrectTimingOrOrder(text);
			            		 break;
			            	 case 4://stoppedColumn;
			            		 uca.setStoppedTooSoonOrAppliedTooLong(text);
			            		 break;
			            	default :
			            		System.out.println("Modify error"+ column);
			            	 }
					    	ucaData.set(row, uca);
							ucaTable.setItems(ucaData);
					    }
					  });
		  } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }
		}

	public void saveFile()
	{

		//Create .xml file
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			doc.setXmlStandalone(true);

			//UCA Table
			Element ucaTable_xml = doc.createElement("UCA_Table");
			doc.appendChild(ucaTable_xml);

			for(int i=0;i<ucaData.size();i++)
			{
				UCA uca = ucaData.get(i);
				ucaTable_xml.setAttribute("UCA", Integer.toString(i));
				Element ca_xml = doc.createElement("CA");
				Element providing_xml = doc.createElement("Providing_Causes_Hazard");
				Element notProviding_xml = doc.createElement("Not_Providing_Causes_Hazard");
				Element incorrect_xml = doc.createElement("Incorrect_TimingOrder");
				Element stopped_xml = doc.createElement("Stopped_Too_SoonApplied_Too_Long");

				ca_xml.appendChild(doc.createTextNode(uca.getControlAction().getValue()));
				providing_xml.appendChild(doc.createTextNode(uca.getProvidingCausesHazard().getValue()));
				notProviding_xml.appendChild(doc.createTextNode(uca.getNotProvidingCausesHazard().getValue()));
				incorrect_xml.appendChild(doc.createTextNode(uca.getIncorrectTimingOrOrder().getValue()));
				stopped_xml.appendChild(doc.createTextNode(uca.getStoppedTooSoonOrAppliedTooLong().getValue()));

				ucaTable_xml.appendChild(ca_xml);
				ucaTable_xml.appendChild(providing_xml);
				ucaTable_xml.appendChild(notProviding_xml);
				ucaTable_xml.appendChild(incorrect_xml);
				ucaTable_xml.appendChild(stopped_xml);
			}


			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");
			transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //�뱾�뿬�벐湲�
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new FileOutputStream(new File("ucaTable_3.xml")));

			transformer.transform(source,result);

			//alert
			System.out.println("end");
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("UtmController - Save File");
		return ;
	}

}

