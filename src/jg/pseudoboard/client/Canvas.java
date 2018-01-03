package jg.pseudoboard.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Canvas extends JPanel {
	
	private int w, h, maxW, maxH; // w, h relate to window; maxW, maxH relate to canvas
	private int offx, offy;
	private int size; // maxW*maxH

	private static final long serialVersionUID = 1L;
	
	private boolean noCanvas = true;
	
	private int[] pixelArray;
	private BufferedImage window;
	
	private int bg;
	
	@Override
	public void paintComponent(Graphics g) {
		w = getWidth();
		h = getHeight();

		if (noCanvas) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, w, h);
			return;
		}
		
		window = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		for (int y = offy; y < offy+Math.min(h, maxH); y++) {
			for (int x = offx; x < offx+Math.min(w, maxW); x++) {
				int rgb = pixelArray[y*Math.min(w, maxW) + x];
				if (rgb == -1) rgb = bg;//any instance of -1 represents background
				if (rgb == -2) rgb = Math.abs(bg - 0xFFFFFF);
				window.setRGB(x-offx, y-offy, rgb);
			}
		}
		
		g.drawImage(window, 0, 0, w, h, null);
		
	}
	
	public void newCanvas(int maxW, int maxH, int bg) {
		//can draw canvas now
		noCanvas = false;
		
		//set background color
		this.bg = bg;
		
		//set width, height, size of canvas (not window)
		w = getWidth();
		h = getHeight();
		this.maxW = maxW;
		this.maxH = maxH;
		size = maxW * maxH;
		
		//set offset so that window initially sees center of canvas
		offx = 0;//maxW/2 - w/2;
		offy = 0;//maxH/2 - h/2;
		
		//initiallize canvas pixel array
		pixelArray = new int[size];
		for (int i = 0; i < size; i++) {
			pixelArray[i] = -1;
		}
		
		//messing around with checkerboard idea
		/*for (int y = 0; y < maxH; y++) {
			for (int x = 0; x < maxW; x++) {
				if (y % 20 < 10)  pixelArray[y*maxW + x] = -1;
				else pixelArray[y*maxW + x] = -2;
			}
		}*/
		
		//initialize buffered image
		window = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		//update display
		repaint();
	}
	
	public void updateCanvas(int[] canvas) {
		for (int i = 0; i < size; i++) {
			pixelArray[i] = canvas[i];
		}
		repaint();
	}
	
	public void clearCanvas() {
		noCanvas = true;
		repaint();
	}
	
	

}
