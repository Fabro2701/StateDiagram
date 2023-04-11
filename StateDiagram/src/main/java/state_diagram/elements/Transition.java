package state_diagram.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Transition extends Element{
	Element from,to;
	Point fromShift,toShift;
	public Transition(Element from, Point fromShift, Element to) {
		super();
		this.from = from;
		this.fromShift = fromShift;
		this.to = to;
	}
	public Transition(Element father, Point shift) {
		this(father, shift, null);
	}

	@Override
	public void paint(Graphics2D g2) {
		g2.setColor(Color.black);
		Point fromp = new Point(fromShift.x + ((SimpleState)from).base.x + ((SimpleState)from).pos.x,
								fromShift.y + ((SimpleState)from).base.y + ((SimpleState)from).pos.y);
		Point top = new Point(toShift.x + ((SimpleState)to).base.x + ((SimpleState)to).pos.x,
							  toShift.y + ((SimpleState)to).base.y + ((SimpleState)to).pos.y);

		g2.drawLine(fromp.x, fromp.y, top.x, top.y);
		
	}
	public void validate() {
		from.addTransition(this);
	}

	@Override
	protected void paintShadow(Graphics2D g2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTransition(Transition t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Point p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsShadow(Point p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void move(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Point getRelativePosition(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDest(Element to) {
		this.to = to;
	}
	public void setToShift(Point toShift) {
		this.toShift = toShift;
	}

}
