package kutokit.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class UCA {

	private SimpleStringProperty controlAction;
	private SimpleStringProperty providingCausesHazard;
	private SimpleStringProperty notProvidingCausesHazard;
	private SimpleStringProperty incorrectTimingOrOrder;
	private SimpleStringProperty stoppedTooSoonOrAppliedTooLong;
	private SimpleStringProperty link;
//	private ComboBox link ;
//	private ObservableList<String> linkList;



	public String ControlAction;
	public String ProvidingCausesHazard;
	public String NotProvidingCausesHazard;
	public String IncorrectTimingOrOrder;
	public String StoppedTooSoonOrAppliedTooLong;
	public String Link;
//	public int linkIndex;

	public UCA()
	{

	}

	public UCA(String controlAction,String providing , String notProviding, String incorrect , String stopped,String link)//ObservableList<String> link,int linkIndex)
	{
		this.controlAction = new SimpleStringProperty(controlAction);
		this.providingCausesHazard = new SimpleStringProperty(providing);
		this.notProvidingCausesHazard = new SimpleStringProperty(notProviding);
		this.incorrectTimingOrOrder = new SimpleStringProperty(incorrect);
		this.stoppedTooSoonOrAppliedTooLong = new SimpleStringProperty(stopped);
		this.link = new SimpleStringProperty(link);
//		this.link = new ComboBox(link);
//		ObservableList<String> str  = FXCollections.observableArrayList("h","e","l");
//		this.link = new COmboBox(str);

		this.ControlAction = controlAction;
		this.ProvidingCausesHazard = providing;
		this.NotProvidingCausesHazard = notProviding;
		this.IncorrectTimingOrOrder = incorrect;
		this.StoppedTooSoonOrAppliedTooLong = stopped;
		this.Link = link;

//		if(!link.isEmpty()){
//			this.Link = link.get(linkIndex);
//
//		}


	}

	public UCA(String controlAction) {
		// TODO Auto-generated constructor stub
		this.controlAction = new SimpleStringProperty(controlAction);
	}

	public void setUCA(String columnID,String setData,int linkIndex)
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
			setLink(setData);
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
//		this.link = new SimpleStringProperty(link);
//		this.Link = linkList.get(linkIndex);
//		this.linkIndex = linkIndex;
		this.link = new SimpleStringProperty(link);
;	}

	public SimpleStringProperty getlink() {
		return link;
	}

}
