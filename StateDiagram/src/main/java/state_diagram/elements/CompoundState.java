package state_diagram.elements;

import static state_diagram.Constants.COMPOUND_STATE_HEADER;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import state_diagram.Constants;
import state_diagram.Diagram;

public class CompoundState extends TransitionableElement {
	static int shadowMargin = Constants.SHADOW_MARGIN;
	static Stroke stroke = new BasicStroke(1);
	int w=Constants.COMPOUND_STATE_W,h=Constants.COMPOUND_STATE_H;
	
	String id;
	int idW,idH;
	int exW,exH;
	
	List<TransitionableElement>children;
	public CompoundState(Diagram diagram, Point base, Point pos) {
		super(diagram, base, pos);
		this.children = new ArrayList<>();
		updateId("compid");
	}
	
	public void insertChild(TransitionableElement e) {
		this.children.add(e);
		e.setFather(this);
	}
	public void removeChild(TransitionableElement e) {
		this.children.remove(e);
		e.setFather(this);
	}
	
	@Override
	public void paint(Graphics2D g2) {
		super.paint(g2);
		//System.out.println(children.size());
		g2.setColor(Color.white);
		//g2.fillRoundRect(base.x+pos.x-w, base.y+pos.y-h, w +exW, h, Constants.ARC_W, Constants.ARC_H);
		g2.setColor(Constants.SIMPLE_STATE_COLOR);
		g2.setStroke(stroke);
		g2.drawRoundRect(base.x+pos.x-w, base.y+pos.y-h, w+exW, h+exH, Constants.ARC_W, Constants.ARC_H);
		g2.drawLine(base.x+pos.x-w, base.y+pos.y-h +idH+COMPOUND_STATE_HEADER, base.x+pos.x+exW, base.y+pos.y-h +idH+COMPOUND_STATE_HEADER);

		g2.setColor(Color.black);
		g2.drawString(id, base.x+pos.x-w+(w-idW)/2, base.y+pos.y-h+idH);
	}

	@Override
	protected void paintShadow(Graphics2D g2) {
		g2.setColor(Constants.SHADOW_COLOR);
		g2.fillRoundRect(base.x+pos.x-w-shadowMargin, base.y+pos.y-h-shadowMargin, 
				    	 w+2*shadowMargin +exW, 	  h+2*shadowMargin +exH, 
				    	 Constants.ARC_W, Constants.ARC_H);
	}

	@Override
	public boolean contains(Point p) {
		return (p.x>=base.x+pos.x-w&&p.x<=base.x+pos.x +exW)&&
			   (p.y>=base.y+pos.y-h&&p.y<=base.y+pos.y-(h-(COMPOUND_STATE_HEADER+idH)));
	}

	@Override
	public boolean containsShadow(Point p) {
		if((p.x>=base.x+pos.x-w&&p.x<=base.x+pos.x +exW)&&
		   (p.y>=base.y+pos.y-h&&p.y<=base.y+pos.y +exH))return false;
		return (p.x>=base.x+pos.x-w-shadowMargin&&p.x<=base.x+pos.x+shadowMargin +exW)&&
			   (p.y>=base.y+pos.y-h-shadowMargin&&p.y<=base.y+pos.y+shadowMargin +exH);
	}
	public boolean containsInside(Point p) {
		if(contains(p))return false;
		return ((p.x>=base.x+pos.x-w&&p.x<=base.x+pos.x +exW)&&
				(p.y>=base.y+pos.y-h&&p.y<=base.y+pos.y +exH));
	}
	public enum RESIZE_DIR{
		HORIZONTAL,VERTICAL
	}
	public RESIZE_DIR getResizeDir(Point point) {
		if(point.x>base.x+pos.x+exW)return RESIZE_DIR.HORIZONTAL;
		else return RESIZE_DIR.VERTICAL; 
	}

	public void increaseWidth(int i) {
		this.exW += i;
	}
	public void increaseHeight(int i) {
		this.exH += i;
	}
	private void updateId(String id) {
		int lastw = idW;
		this.id=id;
		Rectangle2D rec = getIdBounds();
		this.idW = (int) rec.getWidth();
		this.idH = (int) rec.getHeight();
		
		for(var t:this.fromTs) {
			t.fromShift.x += idW-lastw;
		}
		for(var t:this.toTs) {
			t.toShift.x += idW-lastw;
		}
	}
	private Rectangle2D getIdBounds() {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
		return Constants.TEXT_FONT.getStringBounds(id, frc);
	}

}