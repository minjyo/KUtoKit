package kutokit.model;

import javafx.beans.property.SimpleStringProperty;

public class UCA {

	private SimpleStringProperty controlAction;


	private SimpleStringProperty providingCausesHazard;
	private SimpleStringProperty notProvidingCausesHazard;
	private SimpleStringProperty incorrectTimingOrOrder;
	private SimpleStringProperty stoppedTooSoonOrAppliedTooLong;

	public UCA(String controlAction,String providing , String notProviding, String incorrect , String stopped)
	{
		this.controlAction = new SimpleStringProperty(controlAction);
		this.providingCausesHazard = new SimpleStringProperty(providing);
		this.notProvidingCausesHazard = new SimpleStringProperty(notProviding);
		this.incorrectTimingOrOrder = new SimpleStringProperty(incorrect);
		this.stoppedTooSoonOrAppliedTooLong = new SimpleStringProperty(stopped);

	}

	public UCA(String controlAction) {
		// TODO Auto-generated constructor stub
		this.controlAction = new SimpleStringProperty(controlAction);
	}

	public SimpleStringProperty getControlAction()
	{
		return controlAction;
	}

	public void setControlAction(String controlAction)
	{
		this.controlAction = new SimpleStringProperty(controlAction);
	}

	public SimpleStringProperty getProvidingCausesHazard()
	{
		return providingCausesHazard;
	}

	public void setProvidingCausesHazard(String providingCausesHazard)
	{
		this.providingCausesHazard = new SimpleStringProperty(providingCausesHazard);
	}

	public SimpleStringProperty getNotProvidingCausesHazard()
	{
		return notProvidingCausesHazard;
	}

	public void setNotProvidingCausesHazard(String notProvidingCausesHazard)
	{
		this.notProvidingCausesHazard = new SimpleStringProperty(notProvidingCausesHazard);
	}

	public SimpleStringProperty getIncorrectTimingOrOrder()
	{
		return incorrectTimingOrOrder;
	}

	public void setIncorrectTimingOrOrder(String incorrectTimingOrOrder) {
		this.incorrectTimingOrOrder = new SimpleStringProperty(incorrectTimingOrOrder);
	}

	public SimpleStringProperty getStoppedTooSoonOrAppliedTooLong() {
		return stoppedTooSoonOrAppliedTooLong;
	}

	public void setStoppedTooSoonOrAppliedTooLong(String stoppedTooSoonOrAppliedTooLong) {
		this.stoppedTooSoonOrAppliedTooLong = new SimpleStringProperty(stoppedTooSoonOrAppliedTooLong);
	}


}
