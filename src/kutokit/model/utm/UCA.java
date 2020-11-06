package kutokit.model.utm;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class UCA {

	private SimpleStringProperty controlAction;
	private SimpleStringProperty providingCausesHazard;
	private SimpleStringProperty notProvidingCausesHazard;
	private SimpleStringProperty incorrectTimingOrOrder;
	private SimpleStringProperty stoppedTooSoonOrAppliedTooLong;
	private ComboBox<String> links ;

	public String ControlAction;
	public String ProvidingCausesHazard;
	public String NotProvidingCausesHazard;
	public String IncorrectTimingOrOrder;
	public String StoppedTooSoonOrAppliedTooLong;
	public String Link;

	public UCA()
	{

	}

	public UCA(String controlAction,String providing , String notProviding, String incorrect , String stopped,ComboBox<String> linkList)//ObservableList<String> link,int linkIndex)
	{
		this.controlAction = new SimpleStringProperty(controlAction);
		this.providingCausesHazard = new SimpleStringProperty(providing);
		this.notProvidingCausesHazard = new SimpleStringProperty(notProviding);
		this.incorrectTimingOrOrder = new SimpleStringProperty(incorrect);
		this.stoppedTooSoonOrAppliedTooLong = new SimpleStringProperty(stopped);
		this.links = linkList;
		linkList.setOnAction(event ->{
			this.Link = linkList.getSelectionModel().getSelectedItem();
		});

		this.ControlAction = controlAction;
		this.ProvidingCausesHazard = providing;
		this.NotProvidingCausesHazard = notProviding;
		this.IncorrectTimingOrOrder = incorrect;
		this.StoppedTooSoonOrAppliedTooLong = stopped;
		this.Link = linkList.getValue();
	}

	public UCA(String controlAction) {
		this.controlAction = new SimpleStringProperty(controlAction);
	}

	public void setUCAInit(){
		this.controlAction = new SimpleStringProperty(ControlAction);
		this.providingCausesHazard = new SimpleStringProperty(this.ProvidingCausesHazard);
		this.notProvidingCausesHazard = new SimpleStringProperty(this.NotProvidingCausesHazard);
		this.incorrectTimingOrOrder = new SimpleStringProperty(this.IncorrectTimingOrOrder);
		this.stoppedTooSoonOrAppliedTooLong = new SimpleStringProperty(this.StoppedTooSoonOrAppliedTooLong);
	}

	public void setUCA(String columnID,String setData,ComboBox<String> linkIndex)
	{

		switch(columnID)
		{
		case "CAColumn" :
			setControlAction(setData);
			break;
		case "providingColumn" :
			setProvidingCausesHazard(setData);
			break;
		case "notProvidingColumn":
			setNotProvidingCausesHazard(setData);
			break;
		case "incorrectColumn" :
			setIncorrectTimingOrOrder(setData);
			break;
		case "stoppedColumn" :
			setStoppedTooSoonOrAppliedTooLong(setData);
			break;
		case "linkColumn" :
			setLinkList(links);
		default :
			System.out.println("There is no "+ columnID +" Column");
		}
	}
	public SimpleStringProperty getControlAction()
	{
		return controlAction;
	}

	public void setControlAction(String controlAction)
	{
		this.controlAction = new SimpleStringProperty(controlAction);
		this.ControlAction = controlAction;
	}

	public SimpleStringProperty getProvidingCausesHazard()
	{
		return providingCausesHazard;
	}

	public void setProvidingCausesHazard(String providingCausesHazard)
	{
		this.providingCausesHazard = new SimpleStringProperty(providingCausesHazard);
		this.ProvidingCausesHazard = providingCausesHazard;

	}

	public SimpleStringProperty getNotProvidingCausesHazard()
	{
		return notProvidingCausesHazard;
	}

	public void setNotProvidingCausesHazard(String notProvidingCausesHazard)
	{
		this.notProvidingCausesHazard = new SimpleStringProperty(notProvidingCausesHazard);
		this.NotProvidingCausesHazard = notProvidingCausesHazard;
	}

	public SimpleStringProperty getIncorrectTimingOrOrder()
	{
		return incorrectTimingOrOrder;
	}

	public void setIncorrectTimingOrOrder(String incorrectTimingOrOrder) {
		this.incorrectTimingOrOrder = new SimpleStringProperty(incorrectTimingOrOrder);
		this.IncorrectTimingOrOrder = incorrectTimingOrOrder;
	}

	public SimpleStringProperty getStoppedTooSoonOrAppliedTooLong() {
		return stoppedTooSoonOrAppliedTooLong;
	}

	public void setStoppedTooSoonOrAppliedTooLong(String stoppedTooSoonOrAppliedTooLong) {
		this.stoppedTooSoonOrAppliedTooLong = new SimpleStringProperty(stoppedTooSoonOrAppliedTooLong);
		this.StoppedTooSoonOrAppliedTooLong = stoppedTooSoonOrAppliedTooLong;
	}

	public void setLink(String link) {
		System.out.println(link);
		this.Link = link;
	}

	public void setLinkList(ComboBox<String> linkList) {
		this.links = linkList;
		if(Link!=null){
			links.setValue(Link);
		}
		linkList.setOnAction(event ->{
			this.Link = linkList.getSelectionModel().getSelectedItem();
		});
	}

	public ComboBox<String> getLinkLists() {
		return links;
	}
}
