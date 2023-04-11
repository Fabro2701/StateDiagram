package state_diagram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import state_diagram.elements.Element;
import state_diagram.elements.SimpleState;

public class Diagram extends JPanel{
	Point base;
	List<Element>elems;
	MouseAdapter mouse;
	public Diagram() {
		this.base = new Point(0,0);
		this.elems = new ArrayList<>();
		
		this.mouse = new CustomMouse();
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		this.addMouseWheelListener(mouse);
		
		this.elems.add(new SimpleState(base, new Point(200,300)));
		this.elems.add(new SimpleState(base, new Point(400,300)));
	}

	private class CustomMouse extends MouseAdapter{
		Element currentElement = null;
		Element shadowElement = null;
		Point currentPoint = null;
		boolean pressed = false;
		
		public void mouseClicked(MouseEvent ev) {}
	    public void mousePressed(MouseEvent ev) {
	    	Point p = ev.getPoint();
	    	pressed = true;
	    	currentPoint = p;
	    	for(Element e:elems) {
	    		if(e.contains(p)) {
	    			currentElement = e;
	    		}
	    	}
	    }
	    public void mouseReleased(MouseEvent ev) {
	    	currentElement = null;
	    	if(shadowElement!=null)shadowElement.setOver(null);
	    	shadowElement = null;
			currentPoint = null;
	    	pressed = false;
	    	repaint();
	    }
	    public void mouseEntered(MouseEvent ev) {}
	    public void mouseExited(MouseEvent ev) {}
	    public void mouseWheelMoved(MouseWheelEvent ev){}
	    public void mouseDragged(MouseEvent ev){
	    	Point p = ev.getPoint();
	    	if(pressed) {
	    		if(currentElement==null && shadowElement==null) {
		    		Diagram.this.base.move(base.x+p.x-currentPoint.x, 
		    				  			   base.y+p.y-currentPoint.y);
		    		currentPoint = p;
		    		repaint();
	    		}
	    		else if(currentElement!=null){
	    			currentElement.move(p.x-currentPoint.x, p.y-currentPoint.y);
	    			currentPoint = p;
		    		repaint();
	    		}
	    		else if(shadowElement!=null){
	    			//manage transitions
	    			currentPoint = p;
		    		repaint();
	    		}
	    	}
	    }
	    public void mouseMoved(MouseEvent ev){
	    	if(pressed)return;
	    	Point p = ev.getPoint();
	    	if(shadowElement!=null) {
	    		shadowElement.setOver(null);
	    		shadowElement = null;
	    	}
	    	for(Element e:elems) {
	    		if(e.containsShadow(p)) {
	    			e.setOver(p);
	    			shadowElement = e;
	    		}
	    	}
	    	repaint();
	    }
	}
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.white);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());

		for(Element e:elems)e.paint(g2);
	}
}
