package kutokit.view.components;

public class Controller {

	double x, y;
	String name;
	int id;
	
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
}
