package state_diagram.elements.properties;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import state_diagram.elements.Splitter;
import state_diagram.elements.Transition;

public class SplitterProperties extends ElementProperties {
	Splitter e;
	List<JTextField>tFields;
	public SplitterProperties(JScrollPane father, Splitter e) {
		super(father);
		this.e = e;
		this.tFields = new ArrayList<>();

		this.setLayout(new BorderLayout());
		JPanel proppanel = new JPanel();
		proppanel.setLayout(new GridLayout(0,2));
		
		int i=0;
		for(Transition t:e.getFromTs()) {
			JLabel idLabel = new JLabel("t "+t.ID+":");
			JTextField f = new JTextField(String.valueOf(e.getProbs().get(i)));
			tFields.add(f);
			proppanel.add(idLabel);proppanel.add(f);
			i++;
		}
		
		
		JButton saveb = new JButton("save");
		saveb.addActionListener(a->{
			save();
			e.getDiagram().repaint();
		});

		this.add(proppanel, BorderLayout.PAGE_START);
		this.add(saveb, BorderLayout.PAGE_END);
	}

	private void save() {
		for(int i=0;i<e.getFromTs().size();i++) {
			e.getProbs().set(i, Double.valueOf(this.tFields.get(i).getText()));
		}
	}

	@Override
	protected void _load() {
		
	}

}
