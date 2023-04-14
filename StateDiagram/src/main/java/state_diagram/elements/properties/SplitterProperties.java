package state_diagram.elements.properties;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import state_diagram.elements.SimpleState;
import state_diagram.elements.Splitter;

public class SplitterProperties extends ElementProperties {
	Splitter e;
	public SplitterProperties(JScrollPane father, Splitter e) {
		super(father);
		this.e = e;

		
	}

	@Override
	protected void _load() {
		
	}

}
