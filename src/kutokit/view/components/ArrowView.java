package kutokit.view.components;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class ArrowView extends Line{
	
		int id;
		//public double startX, startY, endX, endY;
		public Path arrowHead;
		
	    private static final double defaultArrowHeadSize = 15.0;
	    
	    public ArrowView(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY, double arrowHeadSize){
	        
	    	super();
	        
	        startXProperty().bind(startX.add(120));
	        startYProperty().bind(startY.add(100));
	        endXProperty().bind(endX.add(120));
	        endYProperty().bind(endY);
	        setStrokeWidth(2);
	        
	        arrowHead = new ArrowHead(startX, startY, endX, endY, defaultArrowHeadSize);
	    }
	   
	    public ArrowView(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY, int id){
	        this(startX, startY, endX, endY, defaultArrowHeadSize);
	        this.id=id;
	    }
	    
	    public int getID() {
	    	return id;
	    }
	   
}

	class ArrowHead extends Path{
		public ArrowHead(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY, double arrowHeadSize) {
			strokeProperty().bind(fillProperty());
	        setFill(Color.BLACK);
	        
	        //ArrowHead
	        DoubleProperty angle = new SimpleDoubleProperty(0);
	        angle.bind(Bindings.createDoubleBinding(
	        	    () ->  Math.atan2((endY.get() - startY.get()), (endX.get() - startX.get())) - Math.PI / 2.0,
	        	    startX.add(120), startY.add(100), endX.add(120), endY));
	        
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
	        
	        MoveTo move = new MoveTo(endX.get(), endY.get());
	        move.xProperty().bind(endX.add(120));
	        move.yProperty().bind(endY);
	        
	        LineTo line1 = new LineTo(x1.get(), y1.get());
	        line1.xProperty().bind(x1.add(120));
	        line1.yProperty().bind(y1);
	        
	        LineTo line2 = new LineTo(x2.get(), y2.get());
	        line2.xProperty().bind(x2.add(120));
	        line2.yProperty().bind(y2);
	        
	        LineTo line3 = new LineTo(endX.get(), endY.get());
	        line3.xProperty().bind(endX.add(120));
	        line3.yProperty().bind(endY);
	       
	        getElements().add(move);
	        getElements().add(line1);
	        getElements().add(line2);
	        getElements().add(line3);
		}
	}
