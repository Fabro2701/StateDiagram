package state_diagram.elements;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import state_diagram.Constants;
import state_diagram.Diagram;

public class SimpleState extends TransitionableElement {
	static int shadowMargin = Constants.SHADOW_MARGIN;
	static Stroke stroke = new BasicStroke(1);
	int w=Constants.SIMPLE_STATE_W,h=Constants.SIMPLE_STATE_H;
	
	String id;
	int idW,idH;
	public SimpleState(Diagram diagram, Point base, Point pos) {
		super(diagram, base, pos);
		JMenuItem mEdit = new JMenuItem("Edit");
		mEdit.addActionListener(ae->edit());
		this.pm.add(mEdit);
		
		updateId("id");
	}
	public void edit() {
		JDialog dialog = new JDialog();
		JPanel panel = new JPanel();
		dialog.setContentPane(panel);
		panel.setLayout(new BorderLayout());
		JPanel proppanel = new JPanel();
		proppanel.setLayout(new GridLayout(0,2));
		
		JLabel idLabel = new JLabel("ID:");
		JTextField idField = new JTextField(this.id);
		proppanel.add(idLabel);proppanel.add(idField);
		
		JButton saveb = new JButton("save");
		saveb.addActionListener(a->{
			updateId(idField.getText());
			dialog.dispose();
			diagram.repaint();
		});
		
		panel.add(saveb, BorderLayout.PAGE_END);
		panel.add(proppanel, BorderLayout.CENTER);
		dialog.pack();
		dialog.setLocationRelativeTo(dialog);
		dialog.setVisible(true);
	}
	private void updateId(String id) {
		int lastw = idW;
		this.id=id;
		Rectangle2D rec = getIdBounds();
		this.idW = (int) rec.getWidth();
		this.idH = (int) rec.getHeight();
		
		for(var t:this.fromTs) {
			t.fromShift.x += idW-lastw;
		}
		for(var t:this.toTs) {
			t.toShift.x += idW-lastw;
		}
	}
	private Rectangle2D getIdBounds() {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
		return Constants.TEXT_FONT.getStringBounds(id, frc);
	}
	
	@Override
	public void paint(Graphics2D g2) {
		super.paint(g2);
		g2.setColor(Color.white);
		g2.fillRoundRect(base.x+pos.x-w, base.y+pos.y-h, w +idW, h, Constants.ARC_W, Constants.ARC_H);
		g2.setColor(Constants.SIMPLE_STATE_COLOR);
		g2.setStroke(stroke);
		/*g2.drawPolygon(new int[] {base.x+pos.x-w, base.x+pos.x +idW, base.x+pos.x +idW, base.x+pos.x-w}, 
					   new int[] {base.y+pos.y-h, base.y+pos.y-h, base.y+pos.y, base.y+pos.y}, 4);//idH not applied cause id is displayed always in one line
		*/
		g2.drawRoundRect(base.x+pos.x-w, base.y+pos.y-h, w +idW, h, Constants.ARC_W, Constants.ARC_H);

		g2.setColor(Color.black);
		g2.drawString(id, base.x+pos.x-w+(w)/2, base.y+pos.y-h+idH);
	}

	@Override
	protected void paintShadow(Graphics2D g2) {
		g2.setColor(Constants.SHADOW_COLOR);
		g2.fillRoundRect(base.x+pos.x-w-shadowMargin, base.y+pos.y-h-shadowMargin, 
				    w+2*shadowMargin +idW, h+2*shadowMargin, 
				    Constants.ARC_W, Constants.ARC_H);
	}

	@Override
	public boolean contains(Point p) {
		return (p.x>=base.x+pos.x-w&&p.x<=base.x+pos.x +idW)&&
			   (p.y>=base.y+pos.y-h&&p.y<=base.y+pos.y);
	}

	@Override
	public boolean containsShadow(Point p) {
		if(contains(p))return false;
		return (p.x>=base.x+pos.x-w-shadowMargin&&p.x<=base.x+pos.x+shadowMargin +idW)&&
			   (p.y>=base.y+pos.y-h-shadowMargin&&p.y<=base.y+pos.y+shadowMargin);
	}


}
