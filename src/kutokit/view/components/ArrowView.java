package kutokit.view.components;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;


public class ArrowView extends Path{
	
	private static final double defaultArrowHeadSize = 15.0;
	
	public int id;
	public DoubleProperty startX, startY, endX, endY;
	public LabelView label;
	public String type="";
	
	public ArrowView(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY, double arrowHeadSize, String type) {
		super();
		
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.type = type;
		
		strokeProperty().bind(fillProperty());
        setFill(Color.BLACK);
        setStrokeWidth(2);
        
        MoveTo move;
        LineTo line1;
        if(type.equals("CA")) {
        	//Line
            move = new MoveTo(startX.get()+120, startY.get()+100);
            move.xProperty().bind(startX.add(120));
            move.yProperty().bind(startY.add(100));
        	
            line1 = new LineTo(endX.get()+120, endY.get());
            line1.xProperty().bind(endX.add(120));
            line1.yProperty().bind(endY);
        }else {
        	//Line
            move = new MoveTo(startX.get()+30, startY.get());
            move.xProperty().bind(startX.add(30));
            move.yProperty().bind(startY);
        	
            line1 = new LineTo(endX.get()+30, endY.get()+100);
            line1.xProperty().bind(endX.add(30));
            line1.yProperty().bind(endY.add(100));
        }
        
        getElements().add(move);
        getElements().add(line1);
        
        //ArrowHead
        DoubleProperty angle = new SimpleDoubleProperty(0);
        angle.bind(Bindings.createDoubleBinding(
        	    () ->  Math.atan2((endY.get() - startY.get()), (endX.get() - startX.get())) - Math.PI / 2.0,
        	    startX, startY, endX, endY));
        
        DoubleProperty sin = new SimpleDoubleProperty(0);
        sin.bind(Bindings.createDoubleBinding(
        	    () ->  Math.sin(angle.get()),
        	    angle));
        
        DoubleProperty cos = new SimpleDoubleProperty(0);
        cos.bind(Bindings.createDoubleBinding(
        	    () ->  Math.cos(angle.get()),
        	    angle));
        
        //point1
        DoubleProperty x1 = new SimpleDoubleProperty(0);
        x1.bind(Bindings.createDoubleBinding(
        	    () ->  (- 1.0 / 2.0 * cos.get() + Math.sqrt(3) / 2 * sin.get()) * arrowHeadSize + endX.get(),
        	    sin, cos, endX));
        
        DoubleProperty y1 = new SimpleDoubleProperty();
        y1.bind(Bindings.createDoubleBinding(
        	    () ->  (- 1.0 / 2.0 * sin.get() - Math.sqrt(3) / 2 * cos.get()) * arrowHeadSize + endY.get(),
        	    sin, cos, endY));
        
        //point2
        DoubleProperty x2 = new SimpleDoubleProperty();
        x2.bind(Bindings.createDoubleBinding(
        	    () -> (1.0 / 2.0 * cos.get() + Math.sqrt(3) / 2 * sin.get()) * arrowHeadSize + endX.get(),
        	    sin, cos, endX));
        
        DoubleProperty y2 = new SimpleDoubleProperty();
        y2.bind(Bindings.createDoubleBinding(
        	    () -> (1.0 / 2.0 * sin.get() - Math.sqrt(3) / 2 * cos.get()) * arrowHeadSize + endY.get(),
        	    sin, cos, endY));
        
        LineTo line2, line3, line4;
        
        if(type.equals("CA")) {
        	line2 = new LineTo(x1.get(), y1.get());
            line2.xProperty().bind(x1.add(120));
            line2.yProperty().bind(y1);
            
            line3 = new LineTo(x2.get(), y2.get());
            line3.xProperty().bind(x2.add(120));
            line3.yProperty().bind(y2);
            
            line4 = new LineTo(endX.get(), endY.get());
            line4.xProperty().bind(endX.add(120));
            line4.yProperty().bind(endY);
        }else {
        	line2 = new LineTo(x1.get(), y1.get());
            line2.xProperty().bind(x1.add(30));
            line2.yProperty().bind(y1.add(100));
            
            line3 = new LineTo(x2.get(), y2.get());
            line3.xProperty().bind(x2.add(30));
            line3.yProperty().bind(y2.add(100));
            
            line4 = new LineTo(endX.get(), endY.get());
            line4.xProperty().bind(endX.add(30));
            line4.yProperty().bind(endY.add(100));
        }
        
        getElements().add(line2);
        getElements().add(line3);
        getElements().add(line4);
	}
	
	public ArrowView(ControlAction ca, DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY, int id){
		this(startX, startY, endX, endY, 15, "CA");
		this.id = ca.getId();
	}
	
	public ArrowView(Feedback fb, DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY, int id){
	    this(endX, endY, startX, startY, 15, "FB");
		this.id = fb.getId();
	}
	
	public void setLabel(LabelView label) {
		this.label = label;
	}
	
	public int getID() {
    	return id;
    }
}
