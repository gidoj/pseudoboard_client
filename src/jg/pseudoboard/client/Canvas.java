package jg.pseudoboard.client;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Canvas extends JPanel {
	
	private int w, h, maxW, maxH;
	private int offx, offy;

	private static final long serialVersionUID = 1L;
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		w = getWidth();
		h = getHeight();
		g.fillRect(0, 0, w, h);
	}

}
