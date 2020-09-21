package kutokit.model;

import javafx.beans.property.SimpleStringProperty;

public class UCA {

	private SimpleStringProperty controlAction;


	private SimpleStringProperty providingCausesHazard;
	private SimpleStringProperty notProvidingCausesHazard;
	private SimpleStringProperty incorrectTimingOrOrder;
	private SimpleStringProperty stoppedTooSoonOrAppliedTooLong;

	public UCA(String controlAction)
	{
		this.controlAction = new SimpleStringProperty(controlAction);
	}

	public SimpleStringProperty getControlAction()
	{
		return controlAction;
	}

	public void setControlAction(SimpleStringProperty controlAction)
	{
		this.controlAction = controlAction;
	}

	public SimpleStringProperty getProvidingCausesHazard()
	{
		return providingCausesHazard;
	}

	public void setProvidingCausesHazard(SimpleStringProperty providingCausesHazard)
	{
		this.providingCausesHazard = providingCausesHazard;
	}

	public SimpleStringProperty getNotProvidingCausesHazard()
	{
		return notProvidingCausesHazard;
	}

	public void setNotProvidingCausesHazard(SimpleStringProperty notProvidingCausesHazard)
	{
		this.notProvidingCausesHazard = notProvidingCausesHazard;
	}

	public SimpleStringProperty getIncorrectTimingOrOrder()
	{
		return incorrectTimingOrOrder;
	}

	public void setIncorrectTimingOrOrder(SimpleStringProperty incorrectTimingOrOrder) {
		this.incorrectTimingOrOrder = incorrectTimingOrOrder;
	}

	public SimpleStringProperty getStoppedTooSoonOrAppliedTooLong() {
		return stoppedTooSoonOrAppliedTooLong;
	}

	public void setStoppedTooSoonOrAppliedTooLong(SimpleStringProperty stoppedTooSoonOrAppliedTooLong) {
		this.stoppedTooSoonOrAppliedTooLong = stoppedTooSoonOrAppliedTooLong;
	}


}
