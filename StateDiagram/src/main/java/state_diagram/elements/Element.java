package state_diagram.elements;

import java.awt.Graphics2D;
import java.awt.Point;



public abstract class Element {
	protected Point base,pos;
	protected Point over;
	
	public Element(Point base, Point pos) {
		this.base = base;
		this.pos = pos;
		this.over = null;
	}
	public abstract void paint(Graphics2D g2);
	protected abstract void paintShadow(Graphics2D g2);
	public abstract void addTransition(Transition t);
	public abstract boolean contains(Point p);
	public abstract boolean containsShadow(Point p);
	public void move(int x, int y) {
		pos.x += x;
		pos.y += y;
	}
	public Point getPos() {
		return pos;
	}
	public void setPos(Point pos) {
		this.pos = pos;
	}
	public Point getOver() {
		return over;
	}
	public void setOver(Point over) {
		this.over = over;
	}
}
