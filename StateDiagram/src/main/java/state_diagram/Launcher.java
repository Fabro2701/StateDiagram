package state_diagram;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Launcher extends JFrame{
	public static void main(String args[]) {
		SwingUtilities.invokeLater(()->{
			Launcher launcher = new Launcher();
			JPanel panel = new JPanel();
			launcher.setContentPane(panel);
			
			Diagram diagram = new Diagram();
			diagram.setPreferredSize(new Dimension(500,500));
			panel.add(diagram);
			
			launcher.setLocationRelativeTo(null);
			launcher.pack();
			launcher.setDefaultCloseOperation(EXIT_ON_CLOSE);
			launcher.setVisible(true);
		});
	}
}