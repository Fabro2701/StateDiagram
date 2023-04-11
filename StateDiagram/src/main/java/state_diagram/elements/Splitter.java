package state_diagram.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import state_diagram.Constants;

public class Splitter extends TransitionableElement {
	static int shadowMargin = 30;
	int w=100,h=100;
	public Splitter(Point base, Point pos) {
		super(base, pos);
	}
	
	@Override
	public void paint(Graphics2D g2) {
		if(this.over!=null) {
			this.paintShadow(g2);
		}
		g2.setColor(Color.white);
		g2.fillPolygon(new int[] {base.x+pos.x-w/2, base.x+pos.x, base.x+pos.x-w/2, base.x+pos.x-w}, 
				       new int[] {base.y+pos.y-h, base.y+pos.y-h/2, base.y+pos.y, base.y+pos.y-h/2}, 4);
		g2.setColor(Constants.SIMPLE_STATE_COLOR);
		g2.drawPolygon(new int[] {base.x+pos.x-w/2, base.x+pos.x, base.x+pos.x-w/2, base.x+pos.x-w}, 
					   new int[] {base.y+pos.y-h, base.y+pos.y-h/2, base.y+pos.y, base.y+pos.y-h/2}, 4);
		for(var t:ts) {
			t.paint(g2);
		}
	}

	@Override
	protected void paintShadow(Graphics2D g2) {
		g2.setColor(Constants.SHADOW_COLOR);
		g2.fillPolygon(new int[] {base.x+pos.x-w/2, base.x+pos.x+shadowMargin, base.x+pos.x-w/2, base.x+pos.x-w-shadowMargin}, 
			           new int[] {base.y+pos.y-h-shadowMargin, base.y+pos.y-h/2, base.y+pos.y+shadowMargin, base.y+pos.y-w/2}, 4);
		
	}

	@Override
	public boolean contains(Point p) {
		int centerx = base.x+pos.x-w/2;
		int centery = base.y+pos.y-h/2;
		double dx = Math.abs(p.x - centerx);
        double dy = Math.abs(p.y - centery);
        double d = dx / w + dy / h;
        return d <= 0.5;
	}

	@Override
	public boolean containsShadow(Point p) {
		if(contains(p))return false;
		int centerx = base.x+pos.x-w/2;
		int centery = base.y+pos.y-h/2;
		double dx = Math.abs(p.x - centerx);
        double dy = Math.abs(p.y - centery);
        double d = dx / (w+shadowMargin*2) + dy / (h+shadowMargin*2);
        return d <= 0.5;
	}


}
