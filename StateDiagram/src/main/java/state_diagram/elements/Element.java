package state_diagram.elements;

import java.awt.Graphics2D;
import java.awt.Point;



public abstract class Element {

	
	public Element() {
	}
	public abstract void paint(Graphics2D g2);

	public abstract boolean contains(Point p);
	

}
