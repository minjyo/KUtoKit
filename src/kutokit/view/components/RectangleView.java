package kutokit.view.components;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import kutokit.model.cse.Components;

public class RectangleView extends StackPane {

	Rectangle r;
	public int id;
	public int name;
	public DoubleProperty x, y;
	Components dataStore;
	private DoubleProperty num;
	public DoubleProperty width;
	
	
	public RectangleView(DoubleProperty x, DoubleProperty y, String name, int id, Components dataStore) {
	
		this.dataStore = dataStore;
		this.id = id;
		
		width = new SimpleDoubleProperty(150);
		this.r = new Rectangle(200, 100, Color.web("#8fbc8f"));
		
		this.x = x;
		this.y = y;
		x.bind(r.layoutXProperty());
		y.bind(r.layoutYProperty());
//		
//		this.num = num;
//		
//		width.bind(Bindings.createDoubleBinding(
//        	    () ->  150 + 50*num.get(),
//        	   num));
//	
//		r.widthProperty().bind(width);
		
		Label label = new Label(name);
		label.setFont(new Font(18));
		getChildren().addAll(r, label);
		
		enableDrag();
	}
	
	public void resizeRectangle(int[] num) {
		if(num[0]>=1 || num[1]>=1) {	
			if(num[0]<1 && num[1]>=1) {
				this.r.setWidth(200 * num[1]);
			}else if(num[0]>=1 && num[1]<1){
				this.r.setWidth(200 * num[0]);
			}
			else if(num[0]>=1 && num[1]>=1){
				this.r.setWidth(100 * num[0] + 200 * num[1]);
			}
		}	
	}

	// make a node movable by dragging it around with the mouse.
			private void enableDrag() {
				final Delta dragDelta = new Delta();

				setOnMousePressed(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						// record a delta distance for the drag and drop operation.
						dragDelta.x = getLayoutX() - mouseEvent.getX();
						dragDelta.y = getLayoutY() - mouseEvent.getY();
						getScene().setCursor(Cursor.MOVE);
					}
				});
				setOnMouseReleased(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						getScene().setCursor(Cursor.HAND);
						
						dataStore.moveController(id, getLayoutX(), getLayoutY());
					}
				});
				setOnMouseDragged(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						setLayoutX(mouseEvent.getX() + dragDelta.x);
						setLayoutY(mouseEvent.getY() + dragDelta.y);	
					}
				});
				setOnMouseEntered(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						if (!mouseEvent.isPrimaryButtonDown()) {
							getScene().setCursor(Cursor.HAND);
						}
					}
				});
				setOnMouseExited(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						if (!mouseEvent.isPrimaryButtonDown()) {
							getScene().setCursor(Cursor.DEFAULT);		
						}
					}
				});
			}
			
			static class Delta {
				double x, y;
			}
}
