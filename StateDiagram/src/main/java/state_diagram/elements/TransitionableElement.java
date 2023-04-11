package state_diagram.elements;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public abstract class TransitionableElement extends Element{
	protected Point over;
	protected Point base,pos;
	protected List<Transition>ts;
	public TransitionableElement(Point base, Point pos) {
		this.base = base;
		this.pos = pos;
		this.over = null;
		this.ts = new ArrayList<>();
	}
	protected abstract void paintShadow(Graphics2D g2);
	public abstract boolean containsShadow(Point p);
	public Point getOver() {
		return over;
	}
	public void setOver(Point over) {
		this.over = over;
	}
	public void move(int x, int y) {
		pos.x += x;
		pos.y += y;
	}
	public void addTransition(Transition t) {
		ts.add(t);

	}
	public Point getRelativePosition(Point p) {
		return new Point(p.x-(this.base.x + this.pos.x), p.y-(this.base.y + this.pos.y));
	}
}
