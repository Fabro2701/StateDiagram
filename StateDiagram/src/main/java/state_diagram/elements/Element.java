package state_diagram.elements;

import java.awt.Graphics2D;
import java.awt.Point;



public abstract class Element {
	protected Point over;
	
	public Element() {
		this.over = null;
	}
	public abstract void paint(Graphics2D g2);
	protected abstract void paintShadow(Graphics2D g2);
	public abstract void addTransition(Transition t);
	public abstract boolean contains(Point p);
	public abstract boolean containsShadow(Point p);
	
	public Point getOver() {
		return over;
	}
	public void setOver(Point over) {
		this.over = over;
	}
	public abstract void move(int x, int y);
	public abstract Point getRelativePosition(Point p);
}
