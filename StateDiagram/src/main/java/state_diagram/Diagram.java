package state_diagram;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JPanel;

import state_diagram.elements.Element;
import state_diagram.elements.InitState;
import state_diagram.elements.SimpleState;
import state_diagram.elements.Splitter;
import state_diagram.elements.Transition;
import state_diagram.elements.TransitionableElement;

public class Diagram extends JPanel{
	Point base;
	List<TransitionableElement>elems;
	List<Transition>ts;
	MouseAdapter mouse;
	Consumer<Graphics2D>gaux;
	public Diagram() {
		this.base = new Point(0,0);
		this.elems = new ArrayList<>();
		this.ts = new ArrayList<>();
		
		this.mouse = new CustomMouse();
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		this.addMouseWheelListener(mouse);
		
		this.elems.add(new SimpleState(base, new Point(200,300)));
		this.elems.add(new Splitter(base, new Point(400,300)));
		this.elems.add(new InitState(base, new Point(400,500)));
	}

	private class CustomMouse extends MouseAdapter{
		TransitionableElement currentElement = null;
		TransitionableElement shadowElement = null;
		Transition currentTransition = null;
		Point currentPoint = null;
		boolean pressed = false;
		
		public void mouseClicked(MouseEvent ev) {}
	    public void mousePressed(MouseEvent ev) {
	    	Point p = ev.getPoint();
	    	pressed = true;
	    	currentPoint = p;
	    	if(currentElement==null) {
	    		for(TransitionableElement e:elems) {
		    		if(e.contains(p)) {
		    			currentElement = e;
		    		}
		    	}
	    	}
	    	
	    }
	    public void mouseReleased(MouseEvent ev) {
	    	Point p = ev.getPoint();
	    	if(currentTransition!=null) {
	    		for(TransitionableElement e:elems) {
		    		if(e.containsShadow(p)) {
		    			currentTransition.setDest(e);
		    			currentTransition.setToShift(e.getRelativePosition(p));
		    			currentTransition.validate();
		    			ts.add(currentTransition);
		    			break;
		    		}
		    	}
	    	}
	    	
	    	currentElement = null;
	    	if(shadowElement!=null)shadowElement.setOver(null);
	    	shadowElement = null;
	    	currentTransition = null;
			currentPoint = null;
	    	pressed = false;
	    	gaux = null;
	    	repaint();
	    }
	    public void mouseEntered(MouseEvent ev) {}
	    public void mouseExited(MouseEvent ev) {}
	    public void mouseWheelMoved(MouseWheelEvent ev){}
	    public void mouseDragged(MouseEvent ev){
	    	Point p = ev.getPoint();
	    	if(pressed) {
	    		if(currentTransition!=null) {
	    			Point from = shadowElement.getOver();
	    			gaux = g2->g2.drawLine(from.x, from.y, p.x, p.y);
	    			//shadowElement=null;
	    			for(TransitionableElement e:elems) {
	    	    		if(e.containsShadow(p)) {
	    	    			e.setOver(p);
	    	    			repaint();
	    	    			break;
	    	    		}
	    	    	}
		    		repaint();
	    		}
	    		else if(currentElement==null && shadowElement==null) {
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
	    			Transition t = new Transition(shadowElement, shadowElement.getRelativePosition(p));
	    			currentTransition = t;
	    			//currentPoint = p;
		    		repaint();
	    		}
	    	}
	    }
	    public void mouseMoved(MouseEvent ev){
	    	Point p = ev.getPoint();
	    	if(pressed) {
	    		return;
	    	}
	    	boolean r = false;
	    	if(shadowElement!=null) {
	    		shadowElement.setOver(null);
	    		shadowElement = null;
	    		r = true;
	    	}
	    	for(TransitionableElement e:elems) {
	    		if(e.containsShadow(p)) {
	    			e.setOver(p);
	    			shadowElement = e;
	    			r = true;
	    			break;
	    		}
	    	}
	    	if(r)repaint();
	    }
	}
	static Stroke stroke = new BasicStroke(2);
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.white);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());

		for(Element e:elems)e.paint(g2);
		g2.setStroke(stroke);
		for(var t:ts) {
			t.paint(g2);
		}
		
		if(gaux!=null)gaux.accept(g2);
	}
}
