package state_diagram.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import state_diagram.Constants;

public class SimpleState extends TransitionableElement {
	static int shadowMargin = Constants.SHADOW_MARGIN;
	static Stroke stroke = new BasicStroke(1);
	int w=Constants.SIMPLE_STATE_W,h=Constants.SIMPLE_STATE_H;
	public SimpleState(Point base, Point pos) {
		super(base, pos);
	}
	
	@Override
	public void paint(Graphics2D g2) {
		super.paint(g2);
		g2.setColor(Color.white);
		g2.fillRect(base.x+pos.x-w, base.y+pos.y-h, w, h);
		g2.setColor(Constants.SIMPLE_STATE_COLOR);
		g2.setStroke(stroke);
		g2.drawPolygon(new int[] {base.x+pos.x-w, base.x+pos.x, base.x+pos.x, base.x+pos.x-w}, 
					   new int[] {base.y+pos.y-h, base.y+pos.y-h, base.y+pos.y, base.y+pos.y}, 4);
	
	}

	@Override
	protected void paintShadow(Graphics2D g2) {
		g2.setColor(Constants.SHADOW_COLOR);
		g2.fillRect(base.x+pos.x-w-shadowMargin, base.y+pos.y-h-shadowMargin, 
				    w+2*shadowMargin, h+2*shadowMargin);
	}

	@Override
	public boolean contains(Point p) {
		return (p.x>=base.x+pos.x-w&&p.x<=base.x+pos.x)&&
			   (p.y>=base.y+pos.y-h&&p.y<=base.y+pos.y);
	}

	@Override
	public boolean containsShadow(Point p) {
		if(contains(p))return false;
		return (p.x>=base.x+pos.x-w-shadowMargin&&p.x<=base.x+pos.x+shadowMargin)&&
			   (p.y>=base.y+pos.y-h-shadowMargin&&p.y<=base.y+pos.y+shadowMargin);
	}


}
