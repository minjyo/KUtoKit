package kutokit.model;

import java.util.ArrayList;

import kutokit.view.components.*;

public class Components {

	private ArrayList<Controller> controllers = new ArrayList<Controller>();
	public int curId;
	
	public Components() {
		curId = 3;
		
		//curID 나중에 수정 필
		//===================temp========================
		controllers.add(new Controller(200,50, "c1", 1));
		controllers.add(new Controller(200,300, "c2", 2));
		//===================temp========================
	}
	
	public ArrayList<Controller> getControllers() {
		return this.controllers;
	}
	
	public void addComponent(Controller controller) {
		controllers.add(controller);
		curId++;
	}
	
	public void removeComponent(int id) {
		
	}
	
	public void modifyComponent(int id, String name) {
		for (Controller c : controllers) {
            if (c.getId()==id) {
                c.setName(name);
                return;
            }
        }
	}
	
	public void moveComponent(int id, double x, double y) {
		for (Controller c : controllers) {
            if (c.getId()==id) {
                c.setX(x);
                c.setY(y);
                return;
            }
        }
	}
	
	
//	public void addComponent(ControlAction controlaction) {
//		controlActions.add(controlaction);
//	}
	
}
