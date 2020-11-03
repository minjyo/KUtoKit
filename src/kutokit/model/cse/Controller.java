package kutokit.model.cse;

import java.util.HashMap;
import java.util.Map;

public class Controller {

	double x, y;
	String name;
	int id;
	Map<Integer, Integer> CA = new HashMap<Integer, Integer>(); //key: CA id, value: 1->controller, 0->controlled
	
	public Controller() {
		
	}
	
	public Controller(double x, double y, String name, int id) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.id = id;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Map<Integer, Integer> getCA(){
		return this.CA;
	}

	public void addCA(int id, int type) {
		this.CA.put(id, type);
	}
}
