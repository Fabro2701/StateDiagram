package state_diagram.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import state_diagram.Constants;
import state_diagram.Diagram;

public class Splitter extends TransitionableElement {
	static int shadowMargin = Constants.SHADOW_MARGIN;
	int w=Constants.SPLITTER_W,h=Constants.SPLITTER_H;
	static Stroke stroke = new BasicStroke(3);
	public Splitter(Diagram diagram, Point base, Point pos) {
		super(diagram, base, pos);
	}
	
	@Override
	public void paint(Graphics2D g2) {
		super.paint(g2);
		g2.setColor(Color.white);
		g2.fillPolygon(new int[] {base.x+pos.x-w/2, base.x+pos.x, base.x+pos.x-w/2, base.x+pos.x-w}, 
				       new int[] {base.y+pos.y-h, base.y+pos.y-h/2, base.y+pos.y, base.y+pos.y-h/2}, 4);
		g2.setColor(Constants.SPLITTER_COLOR);
		g2.setStroke(stroke);
		g2.drawPolygon(new int[] {base.x+pos.x-w/2, base.x+pos.x, base.x+pos.x-w/2, base.x+pos.x-w}, 
					   new int[] {base.y+pos.y-h, base.y+pos.y-h/2, base.y+pos.y, base.y+pos.y-h/2}, 4);
		
		if(father!=null) {
			g2.setColor(Color.gray);
			g2.drawString(String.valueOf(father.ID), base.x+pos.x-w, base.y+pos.y-h);
		}
		
	}

	@Override
	protected void paintShadow(Graphics2D g2) {
		g2.setColor(Constants.SHADOW_COLOR);
		g2.fillPolygon(new int[] {base.x+pos.x-w/2, base.x+pos.x+shadowMargin, base.x+pos.x-w/2, base.x+pos.x-w-shadowMargin}, 
			           new int[] {base.y+pos.y-h-shadowMargin, base.y+pos.y-h/2, base.y+pos.y+shadowMargin, base.y+pos.y-w/2}, 4);
		
	}

	@Override
	public TransitionableElement contains(Point p) {
		int centerx = base.x+pos.x-w/2;
		int centery = base.y+pos.y-h/2;
		double dx = Math.abs(p.x - centerx);
        double dy = Math.abs(p.y - centery);
        double d = dx / w + dy / h;
        if( d <= 0.5)return this;
        return null;
	}

	@Override
	public TransitionableElement containsShadow(Point p) {
		TransitionableElement aux = null;
		if((aux=contains(p))!=null)return aux;
		int centerx = base.x+pos.x-w/2;
		int centery = base.y+pos.y-h/2;
		double dx = Math.abs(p.x - centerx);
        double dy = Math.abs(p.y - centery);
        double d = dx / (w+shadowMargin*2) + dy / (h+shadowMargin*2);
        if( d <= 0.5)return this;
        return null;
	}

	@Override
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	}


}
