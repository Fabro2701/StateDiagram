package state_diagram.elements;

import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPopupMenu;

import state_diagram.Diagram;



public abstract class Element {
	protected JPopupMenu pm;
	protected Diagram diagram;
	public Element(Diagram diagram) {
		this.diagram = diagram;
		this.pm = new JPopupMenu();
	}
	public abstract void paint(Graphics2D g2);

	public void openMenu(Point p) {
		pm.show(diagram,p.x,p.y);
	}
	public abstract boolean contains(Point p);
	

}
