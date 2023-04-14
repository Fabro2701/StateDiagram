package state_diagram.elements.properties;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import state_diagram.elements.SimpleState;
import state_diagram.elements.Transition;

public class TransitionProperties extends ElementProperties {
	Transition e;
	JTextField typeField,codeField;
	public TransitionProperties(JScrollPane father, Transition e) {
		super(father);
		this.e = e;

		this.setLayout(new BorderLayout());
		JPanel proppanel = new JPanel();
		proppanel.setLayout(new GridLayout(0,2));
		
		JLabel typeLabel = new JLabel("Type:");
		typeField = new JTextField(e.getType().toString());
		proppanel.add(typeLabel);proppanel.add(typeField);
		
		JLabel codeLabel = new JLabel("Code:");
		codeField = new JTextField(e.getCode());
		proppanel.add(codeLabel);proppanel.add(codeField);
		
		
		JButton saveb = new JButton("save");
		saveb.addActionListener(a->{
			e.setType(Transition.TRANSITION_TYPE.valueOf(typeField.getText()));
			e.setCode(codeField.getText());
			e.getDiagram().repaint();
		});

		this.add(proppanel, BorderLayout.PAGE_START);
		this.add(saveb, BorderLayout.PAGE_END);
	}

	@Override
	protected void _load() {
		typeField.setText(e.getType().toString());
		codeField.setText(e.getCode());
	}

}
