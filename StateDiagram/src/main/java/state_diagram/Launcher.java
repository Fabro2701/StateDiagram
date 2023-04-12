package state_diagram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Launcher extends JFrame{
	public static void main(String args[]) {
		SwingUtilities.invokeLater(()->{
			Launcher launcher = new Launcher();
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			launcher.setContentPane(panel);
			
			Diagram diagram = new Diagram();
			diagram.setPreferredSize(new Dimension(800,700));
			panel.add(diagram, BorderLayout.CENTER);
			
			ToolBar toolBar = new ToolBar(diagram);
			toolBar.setPreferredSize(new Dimension(800,70));
			toolBar.setBorder(BorderFactory.createMatteBorder(
                    1, 1, 1, 1, Color.gray));
			panel.add(toolBar, BorderLayout.PAGE_START);
			
			JPanel buttonsPanel = new JPanel();
			buttonsPanel.setLayout(new FlowLayout());
			JButton saveButton = new JButton("SAVE");
			saveButton.addActionListener(ae->diagram.save());
			JButton loadButton = new JButton("LOAD");
			loadButton.addActionListener(ae->{
				try {
					diagram.load();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException
						| ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			buttonsPanel.add(saveButton);
			buttonsPanel.add(loadButton);
			panel.add(buttonsPanel, BorderLayout.PAGE_END);
			
			launcher.setLocation(500, 100);
			launcher.pack();
			launcher.setDefaultCloseOperation(EXIT_ON_CLOSE);
			launcher.setVisible(true);
		});
	}
}
