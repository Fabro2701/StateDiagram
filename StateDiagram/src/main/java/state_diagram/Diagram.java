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
import javax.swing.SwingUtilities;

import state_diagram.elements.CompoundState;
import state_diagram.elements.CompoundState.RESIZE_DIR;
import state_diagram.elements.Corner;
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
		
		this.elems.add(new SimpleState(this, base, new Point(200,300)));
		this.elems.add(new Splitter(this, base, new Point(400,300)));
		this.elems.add(new InitState(this, base, new Point(400,500)));
		this.elems.add(new Corner(this, base, new Point(100,100)));
		this.elems.add(new CompoundState(this, base, new Point(300,100)));
	}

	private class CustomMouse extends MouseAdapter{
		TransitionableElement currentElement = null;
		TransitionableElement shadowElement = null;
		CompoundState currentCompound = null;
		Transition currentTransition = null;
		Point currentPoint = null;
		boolean pressed = false;
		
		public void mouseClicked(MouseEvent ev) {
	    	Point p = ev.getPoint();
			if (SwingUtilities.isRightMouseButton(ev)) {
				for(TransitionableElement e:elems) {
		    		if(e.contains(p)) {
		    			e.openMenu(p);
		    			break;
		    		}
		    	}
			}
		}
	    public void mousePressed(MouseEvent ev) {
	    	Point p = ev.getPoint();
	    	pressed = true;
	    	currentPoint = p;
	    	if(currentElement==null) {
	    		for(TransitionableElement e:elems) {
		    		if(e.contains(p)) {
		    			currentElement = e;
		    			Diagram.this.setCursor(Constants.HAND_CURSOR);
		    			break;
		    		}
		    	}
	    	}
	    	
	    }
	    public void mouseReleased(MouseEvent ev) {
	    	Point p = ev.getPoint();
	    	if(currentTransition!=null) {
	    		for(TransitionableElement e:elems) {
	    			if(e==currentTransition.getFrom())continue;
		    		if(e.containsShadow(p)) {
		    			currentTransition.setDest(e);
		    			currentTransition.setToShift(e.getRelativePosition(p));
		    			currentTransition.validate();
		    			ts.add(currentTransition);
		    			break;
		    		}
		    	}
	    	}
	    	if(currentCompound!=null) {
	    		CompoundState father=null;
				if((father=currentElement.getFather())!=null) {
					if(!father.containsInside(p)) {
						father.removeChild(currentElement);
					}
				}
	    		for(var e:elems) {//put elem inside a cmp
    				if(e instanceof CompoundState) {
    					CompoundState cse = (CompoundState)e;
    					if(cse.containsInside(p)) {
    						currentCompound = cse;
    			    		currentCompound.insertChild(currentElement);
    						Diagram.this.setCursor(Constants.DEFAULT_CURSOR);
    					}
    				}
    			}
	    	}
	    	currentElement = null;
	    	if(shadowElement!=null)shadowElement.setOver(null);
	    	shadowElement = null;
	    	currentTransition = null;
			currentPoint = null;
			currentCompound = null;
	    	pressed = false;
	    	gaux = null;
	    	Diagram.this.setCursor(Constants.DEFAULT_CURSOR);
	    	repaint();
	    }
	    public void mouseEntered(MouseEvent ev) {}
	    public void mouseExited(MouseEvent ev) {}
	    public void mouseWheelMoved(MouseWheelEvent ev){}
	    public void mouseDragged(MouseEvent ev){
	    	Point p = ev.getPoint();
	    	if(pressed) {
	    		//temporal transition
	    		if(currentTransition!=null) {
	    			if (SwingUtilities.isLeftMouseButton(ev)) {
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
	    			
	    		}
	    		//navigate
	    		else if(currentElement==null && shadowElement==null) {
		    		Diagram.this.base.move(base.x+p.x-currentPoint.x, 
		    				  			   base.y+p.y-currentPoint.y);
		    		currentPoint = p;
		    		repaint();
	    		}
	    		else if(currentElement!=null){
	    			Diagram.this.setCursor(Constants.HAND_CURSOR);
	    			for(var e:elems) {//put elem inside a cmp
	    				if(e instanceof CompoundState) {
	    					CompoundState cse = (CompoundState)e;
	    					if(cse.containsInside(p)) {
	    						currentCompound = cse;
	    						Diagram.this.setCursor(Constants.WAIT_CURSOR);
	    					}
	    				}
	    			}
	    			//move elem
	    			currentElement.move(p.x-currentPoint.x, p.y-currentPoint.y);
	    			currentPoint = p;
	    			
	    			
		    		repaint();
	    		}
	    		//create trans
	    		else if(shadowElement!=null){
	    			if (SwingUtilities.isLeftMouseButton(ev)) {
	    				Transition t = new Transition(Diagram.this, shadowElement, shadowElement.getRelativePosition(p));
		    			currentTransition = t;
		    			//currentPoint = p;
			    		repaint();
	    			}
	    			else if (SwingUtilities.isRightMouseButton(ev)) {
	    				
	    				if(shadowElement instanceof CompoundState) {
	    					CompoundState cse = (CompoundState)shadowElement;
	    					RESIZE_DIR dir = cse.getResizeDir(p);
	    					if(dir==RESIZE_DIR.HORIZONTAL) {
	    						cse.increaseWidth(p.x>currentPoint.x?1:-1);
	    						currentPoint = p;
	    						Diagram.this.setCursor(Constants.E_RESIZE_CURSOR);
	    						repaint();
	    					}
	    					else {
	    						cse.increaseHeight(p.y>currentPoint.y?1:-1);
	    						currentPoint = p;
	    						Diagram.this.setCursor(Constants.S_RESIZE_CURSOR);
	    						repaint();
	    					}
	    				}
	    				
	    			}
	    		}
	    	}
	    }
	    public void mouseMoved(MouseEvent ev){
	    	Point p = ev.getPoint();
	    	if(pressed) {
	    		return;
	    	}
			Diagram.this.setCursor(Constants.DEFAULT_CURSOR);
	    	boolean r = false;
	    	if(shadowElement!=null) {
	    		shadowElement.setOver(null);
	    		shadowElement = null;
	    		r = true;
	    	}
	    	for(TransitionableElement e:elems) {
	    		if(e.contains(p)) {
	    			Diagram.this.setCursor(Constants.HAND_CURSOR);
	    			shadowElement = e;
	    			r = true;
	    			break;
	    		}
	    		else if(e.containsShadow(p)) {
	    			Diagram.this.setCursor(Constants.CROSSHAIR_CURSOR);
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

		g2.setFont(Constants.TEXT_FONT);
		for(Element e:elems)e.paint(g2);
		g2.setStroke(stroke);
		for(var t:ts) {
			t.paint(g2);
		}
		
		if(gaux!=null)gaux.accept(g2);
	}
	public void insertElement(TransitionableElement e, int x) {
		e.setBase(base);
		e.setPos(new Point(x-base.x,-base.y));
		this.elems.add(e);
		((CustomMouse)mouse).currentElement = e;
	}
}
