package state_diagram.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JMenuItem;

import state_diagram.Constants;
import state_diagram.Diagram;

public class InitState extends TransitionableElement {
	static int shadowMargin = Constants.SHADOW_MARGIN;
	static int w=Constants.INIT_STATE_W,h=Constants.INIT_STATE_H;
	public InitState(Diagram diagram, Point base, Point pos) {
		super(diagram, base, pos);
	}
	
	@Override
	public void paint(Graphics2D g2) {
		super.paint(g2);
		g2.setColor(Constants.INIT_COLOR);
		g2.fillOval(base.x+pos.x-w, base.y+pos.y-h, w, h);
		/*g2.setColor(Constants.SIMPLE_STATE_COLOR);
		g2.drawOval(base.x+pos.x-w, base.y+pos.y-h, w, h);*/
		
	}

	@Override
	protected void paintShadow(Graphics2D g2) {
		g2.setColor(Constants.SHADOW_COLOR);
		g2.fillOval(base.x+pos.x-w-shadowMargin, base.y+pos.y-h-shadowMargin, w+shadowMargin*2, h+shadowMargin*2);
	}

	@Override
	public boolean contains(Point p) {
		double centerx = base.x+pos.x-w/2;
		double centery = base.y+pos.y-h/2;
		double dx = Math.abs(p.x - centerx);
        double dy = Math.abs(p.y - centery);
        
        return (Math.pow(dx, 2)/Math.pow(w/2, 2))+(Math.pow(dy, 2)/Math.pow(h/2, 2))<=1d;
	}

	@Override
	public boolean containsShadow(Point p) {
		if(contains(p))return false;
		double centerx = base.x+pos.x-w/2;
		double centery = base.y+pos.y-h/2;
		double dx = Math.abs(p.x - centerx);
        double dy = Math.abs(p.y - centery);
        
        return (Math.pow(dx, 2)/Math.pow(w/2+shadowMargin, 2))+(Math.pow(dy, 2)/Math.pow(h/2+shadowMargin, 2))<=1d;
	}


}
