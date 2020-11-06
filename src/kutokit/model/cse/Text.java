package kutokit.model.cse;

public class Text {

	double x, y;
	String content;
	int id;
	
	public Text() {
		
	}
	
	public Text(double x, double y, String content, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.content = content;
	}
	
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public String getContent() {
		return content;
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
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
