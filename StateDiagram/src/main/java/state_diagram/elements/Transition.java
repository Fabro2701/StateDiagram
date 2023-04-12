package state_diagram.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import state_diagram.Constants;
import state_diagram.Diagram;

public class Transition extends Element{
	TransitionableElement from,to;
	Point fromShift,toShift;
	public Transition(Diagram diagram, TransitionableElement from, Point fromShift, TransitionableElement to) {
		super(diagram);
		this.from = from;
		this.fromShift = fromShift;
		this.to = to;
	}
	public Transition(Diagram diagram, TransitionableElement father, Point shift) {
		this(diagram, father, shift, null);
	}

	@Override
	public void paint(Graphics2D g2) {
		g2.setColor(Color.black);
		Point fromp = new Point(fromShift.x + from.base.x + from.pos.x,
								fromShift.y + from.base.y + from.pos.y);
		Point top = new Point(toShift.x + to.base.x + to.pos.x,
							  toShift.y + to.base.y + to.pos.y);
		
		

		g2.drawLine(fromp.x, fromp.y, top.x, top.y);
		
		if(to instanceof Corner)return;
		drawArrow(g2, fromp.x, fromp.y, top.x, top.y,Constants.ARROW_W,Constants.ARROW_H);
	}
	public void validate() {
		from.addFromTransition(this);
		to.addToTransition(this);
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
	private void drawArrow(//
									Graphics2D g2, //
									int x1, int y1, //
									int x2, int y2, //
									int w, int h) {

		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double xm = D - w, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;
		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;
		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;
		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };
		//g2.drawLine(x1, y1, x2, y2);
		g2.fillPolygon(xpoints, ypoints, 3);
	}
	public TransitionableElement getFrom() {
		return from;
	}
	public TransitionableElement getTo() {
		return to;
	}

}
