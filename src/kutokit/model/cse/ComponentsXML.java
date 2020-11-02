package kutokit.model.cse;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import kutokit.view.components.*;

@XmlRootElement(name = "components")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ComponentsXML {

	private ArrayList<Controller> controllers;
	private ArrayList<ControlAction> controlActions = new ArrayList<ControlAction>();
	private ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
	
	@XmlElement(name = "controller")
	public ArrayList<Controller> getControllers() {
		return controllers;
	}

	public void setControllers(ArrayList<Controller> controllers) {
		this.controllers = controllers;
	}
	
	@XmlElement(name = "controlActions")
	public ArrayList<ControlAction> getControlActions() {
		return controlActions;
	}

	public void setControlActions(ArrayList<ControlAction> controlActions) {
		System.out.println(this.controlActions.size());
		System.out.println(controlActions.size());
		this.controlActions = controlActions;
//		for(ControlAction c : this.controlActions) {
//			//System.out.println("id: " + c.getId() + "ca: " + c.getCA() + "c1: " + c.getControllerID() + "c2: " + c.getControlledID());
//		}
	}
	
	@XmlElement(name = "feedbacks")
	public ArrayList<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(ArrayList<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}
}
