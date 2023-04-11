package state_diagram.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Transition extends Element{
	TransitionableElement from,to;
	Point fromShift,toShift;
	public Transition(TransitionableElement from, Point fromShift, TransitionableElement to) {
		super();
		this.from = from;
		this.fromShift = fromShift;
		this.to = to;
	}
	public Transition(TransitionableElement father, Point shift) {
		this(father, shift, null);
	}

	@Override
	public void paint(Graphics2D g2) {
		g2.setColor(Color.black);
		Point fromp = new Point(fromShift.x + from.base.x + from.pos.x,
								fromShift.y + from.base.y + from.pos.y);
		Point top = new Point(toShift.x + to.base.x + to.pos.x,
							  toShift.y + to.base.y + to.pos.y);

		g2.drawLine(fromp.x, fromp.y, top.x, top.y);
		
	}
	public void validate() {
		from.addTransition(this);
	}
	@Override
	public boolean contains(Point p) {
		// TODO Auto-generated method stub
		return false;
	}
	public void setDest(TransitionableElement to) {
		this.to = to;
	}
	public void setToShift(Point toShift) {
		this.toShift = toShift;
	}

}
