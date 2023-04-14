package state_diagram.elements.properties;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public abstract class ElementProperties extends JPanel{
	JScrollPane father;
	public ElementProperties(JScrollPane father) {
		this.father = father;
	}
	public void load() {
		_load();
		father.setViewportView(this);
	}
	protected abstract void _load();
}
