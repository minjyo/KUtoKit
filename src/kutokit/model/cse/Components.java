package kutokit.model.cse;

import java.util.ArrayList;

public class Components {

	private ArrayList<Controller> controllers = new ArrayList<Controller>();
	private ArrayList<ControlAction> controlActions = new ArrayList<ControlAction>();
	private ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
	private ArrayList<Text> texts = new ArrayList<Text>();
	
	public int curId=1;
	public ControlAction curCA;
	public Feedback curFB;
	public Controller curController;
	
	public Components() {
		curId = 6;
		
		//===================temp========================
		controllers.add(new Controller(150,30, "c1", 1));
		controllers.add(new Controller(100,300, "c2", 2));
		controllers.add(new Controller(400,300, "c3", 3));
		
		ArrayList<String> ca = new ArrayList<String>();
		ca.add("a");
		controlActions.add(new ControlAction("c1", "c2", ca, 4, this));
		feedbacks.add(new Feedback("c2", "c1", ca, 5, this));
		
		//===================temp========================
	}
	
	public ArrayList<Controller> getControllers() {
		return this.controllers;
	}
	
	public ArrayList<ControlAction> getControlActions() {
		return this.controlActions;
	}
	
	public ArrayList<Feedback> getFeedbacks() {
		return this.feedbacks;
	}
	
	public ArrayList<Text> getTexts() {
		return this.texts;
	}
	
	public int getCurId() {
		return this.curId;
	}
	
	public void setCurId(int id) {
		this.curId = id;
	}
	
	public void setTexts(ArrayList<Text> texts) {
		this.texts = texts;
	}
	
	
	public void addController(Controller controller) {
		controllers.add(controller);
		curId++;
	}
	
	public void addControlAction(ControlAction ca) {
		controlActions.add(ca);
		curId++;
	}
	
	public void addFeedback(Feedback fb) {
		feedbacks.add(fb);
		curId++;
	}
	
	public void addText(Text t) {
		texts.add(t);
		curId++;
	}
	
	public void moveController(int id, double x, double y) {
		for (Controller c : controllers) {
            if (c.getId()==id) {
                c.setX(x);
                c.setY(y);
                return;
            }
        }
	}
	
	public void moveText(int id, double x, double y) {
		for (Text t : texts) {
            if (t.getId()==id) {
            	t.setX(x);
                t.setY(y);
                return;
            }
        }
	}
	
	//delete Controller -> delete Controller's ControlAction -> delete Related Controller's ControlAction
	public void deleteController(int id) {
		for (Controller c : controllers) {
            if (c.getId()==id) {
            	for( int caId : c.getCA().keySet() ){
            		if(c.getCA().get(caId)==1) {
            			deleteControlActionFromController(caId);	
            		}else {
            			deleteControlActionFromControlled(caId);
            		}
            		
                }
            	for( int fbId : c.getFB().keySet() ){
            		if(c.getFB().get(fbId)==1) {
            			deleteFeedbackFromController(fbId);	
            		}else {
            			deleteFeedbackFromControlled(fbId);
            		}
                }
            	
                controllers.remove(c);
                return;
            }
        }
	}
	
	public void deleteControlActionFromController(int id) {
		for (ControlAction c : controlActions) {
            if (c.getId()==id) {
            	c.getControlled().removeCA(id);
            	
            	controlActions.remove(c);
                return;
            }
        }
	}
	
	public void deleteControlActionFromControlled(int id) {
		for (ControlAction c : controlActions) {
            if (c.getId()==id) {
            	c.getController().removeCA(id);
            	
            	controlActions.remove(c);
                return;
            }
        }
	}
	
	public void deleteFeedbackFromController(int id) {
		for (Feedback c : feedbacks) {
            if (c.getId()==id) {
            	c.getControlled().removeCA(id);
            	
            	feedbacks.remove(c);
                return;
            }
        }
	}
	
	public void deleteFeedbackFromControlled(int id) {
		for (Feedback c : feedbacks) {
            if (c.getId()==id) {
            	c.getController().removeCA(id);
            	
            	feedbacks.remove(c);
                return;
            }
        }
	}
		
	//delete ControlAction -> delete Controller, Controlled's ControlAction
	public void deleteControlAction(int id) {
		for (ControlAction c : controlActions) {
            if (c.getId()==id) {
            	c.getController().removeCA(id);
            	c.getControlled().removeCA(id);

            	controlActions.remove(c);
                return;
            }
        }
	}
	
	public void deleteFeedback(int id) {
		for (Feedback c : feedbacks) {
            if (c.getId()==id) {
            	c.getController().removeFB(id);
            	c.getControlled().removeFB(id);
            	
            	feedbacks.remove(c);
                return;
            }
        }
	}
	
	public void deleteText(int id) {
		for (Text t : texts) {
            if (t.getId()==id) {
            	texts.remove(t);
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
	
	public void modifyFeedback(int id, ArrayList<String> FB) {
		for (Feedback fb : feedbacks) {
            if (fb.getId()==id) {
            	fb.setFB(FB);
                return;
            }
        }
	}
	
	public void modifyText(int id, String content) {
		for (Text t : texts) {
            if (t.getId()==id) {
            	t.content = content;
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
	
	public Controller findController(int id) {
		for (Controller c : controllers) {
            if (c.getId()==id) {
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
	
	public Feedback findFeedback(int id) {
		for (Feedback fb : feedbacks) {
            if (fb.getId()==id) {
                return fb;
            }
        }
		return null;
	}
	
	public Text findText(int id) {
		for (Text t : texts) {
            if (t.getId()==id) {
            	return t;
            }
        }
		return null;
	}

}
