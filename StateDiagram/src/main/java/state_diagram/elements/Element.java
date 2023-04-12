package state_diagram.elements;

import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPopupMenu;

import org.json.JSONObject;

import state_diagram.Diagram;



public abstract class Element {
	public int ID;
	protected JPopupMenu pm;
	protected Diagram diagram;
	public Element(Diagram diagram) {
		this.diagram = diagram;
		this.pm = new JPopupMenu();
		this.ID = IdGenerator.nextId();
	}
	public abstract void paint(Graphics2D g2);

	public void openMenu(Point p) {
		pm.show(diagram,p.x,p.y);
	}
	public abstract TransitionableElement contains(Point p);
	
	public abstract JSONObject toJSON();
}
