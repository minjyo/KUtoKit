package kutokit.model;

import java.util.ArrayList;

import kutokit.view.components.*;

public class Components {

	private ArrayList<Controller> controllers = new ArrayList<Controller>();
	private ArrayList<ControlAction> controlActions = new ArrayList<ControlAction>();
	public int curId;
	public ControlAction curCA;
	
	public Components() {
		curId = 3;
		
		//curID 나중에 수정 필
		//===================temp========================
		controllers.add(new Controller(100,30, "c1", 1));
		controllers.add(new Controller(100,300, "c2", 2));
		//===================temp========================
	}
	
	public ArrayList<Controller> getControllers() {
		return this.controllers;
	}
	
	public void addController(Controller controller) {
		controllers.add(controller);
		curId++;
	}
	
	public void addControlAction(ControlAction ca) {
		controlActions.add(ca);
		curId++;
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
	
	public void deleteController(int id) {
		for (Controller c : controllers) {
            if (c.getId()==id) {
                controllers.remove(c);
                return;
            }
        }
	}
	
	public void deleteControlAction(int id) {
		for (ControlAction c : controlActions) {
            if (c.getId()==id) {
            	controlActions.remove(c);
                return;
            }
        }
	}
	
	public void modifyController(int id, String name) {
		for (Controller c : controllers) {
            if (c.getId()==id) {
                c.setName(name);
                return;
            }
        }
	}
	
	public void modifyControlAction(int id, ArrayList<String> CA) {
		for (ControlAction ca : controlActions) {
            if (ca.getId()==id) {
                ca.setCA(CA);
                return;
            }
        }
	}
	
	public Controller findController(String name) {
		for (Controller c : controllers) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
		return null;
	}
	
	public ControlAction findControlAction(int id) {
		for (ControlAction ca : controlActions) {
            if (ca.getId()==id) {
                return ca;
            }
        }
		return null;
	}
	

}
