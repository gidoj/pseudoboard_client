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
	
	public boolean noCanvas = true;
	
	private int[] pixelArray;
	private BufferedImage window;
	
	private int bg;
	
	public ToolType tool = ToolType.BRUSH;
	public boolean fillShape = false;
	public int brushSize = 5;
	public int brushColor = 0x000000;
	
	private int x0 = -1, y0 = -1, xcurr = -1, ycurr = -1;
	
	public int minX, maxX, minY, maxY;
	public int[] graphicArray;
	private BufferedImage graphic;
	
	@Override
	public void paintComponent(Graphics g) {
		w = getWidth();
		h = getHeight();

		if (noCanvas) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, w, h);
			return;
		}
		
		g.setColor(new Color(bg));
		g.fillRect(0, 0, w, h);
		
		window = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		
		for (int y = offy; y < offy+Math.min(h, maxH); y++) {
			for (int x = offx; x < offx+Math.min(w, maxW); x++) {
				int rgb = pixelArray[y*maxW + x];
				window.setRGB(x-offx, y-offy, rgb);
			}
		}
		
		g.drawImage(window, 0, 0, w, h, null);
		
		if (xcurr + ycurr >= 0) {
			graphic = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics gg = graphic.getGraphics();
			gg.setColor(new Color(brushColor | 0xFF000000));
			
			switch (tool) {
			case ARROW:
				break;
			case BRUSH:
				break;
			case CIRCLE:
				break;
			case DRAG:
				break;
			case ERASER:
				break;
			case LINE:
				break;
			case RECTANGLE:
				int x1 = x0, y1 = y0;
				if (xcurr < x0) x1 = xcurr;
				if (ycurr < y0) y1 = ycurr;
				if (fillShape) {
					gg.fillRect(x1, y1, Math.abs(x0-xcurr), Math.abs(y0-ycurr));
				} else {
					for (int i = 0; i < brushSize; i++) {
						gg.drawRect(x1+i, y1+i, Math.abs(x0-xcurr)-2*i, Math.abs(y0-ycurr)-2*i);
					}
				}
				break;
			case SELECT:
				break;
			case TRIANGLE:
				break;
			default:
				break;
			}
			
			if (xcurr < minX) minX = xcurr;
			else if (xcurr > maxX) maxX = xcurr;
			if (ycurr < minY) minY = ycurr;
			else if (ycurr > maxY) maxY = ycurr;
			g.drawImage(graphic, 0, 0, w, h, null);
		}
		
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
		
		//initialize buffered image
		window = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		//update display
		repaint();
	}
	
	public void updateCanvas(int[] canvas) {
		resetGraphic();
		for (int i = 0; i < size; i++) {
			pixelArray[i] = canvas[i];
		}
		repaint();
	}
	
	public void updateCanvasSection(int[] canvas) {
		//TODO: what if get canvas update from another user
		//      but this client currently drawing something?
		//		will mess current drawing up - find solution
		resetGraphic();//temp fix - will clear whatever user currently drawing
		minX = canvas[0];
		minY = canvas[1];
		maxX = canvas[2];
		maxY = canvas[3];
		int graphicWidth = maxX - minX + 1;
		int graphicHeight = maxY - minY + 1;
		int graphicSize = graphicWidth * graphicHeight;
		graphicArray = new int[graphicSize];
		for (int y = 0; y < graphicHeight; y++) {
			for (int x = 0; x < graphicWidth; x++) {
				int updateVal = canvas[y*graphicWidth + x + 4];
				if (updateVal == 0) continue;
				graphicArray[y*graphicWidth + x] = updateVal;
				pixelArray[(y+minY)*maxW + (x+minX)] = updateVal;
			}
		}
		repaint();
	}
	
	public void clearCanvas() {
		noCanvas = true;
		repaint();
	}
	
	public void setInitialPoint(int x0, int y0) {
		this.x0 = x0;
		this.y0 = y0;
		minX = x0;
		minY =y0;
		maxX = x0;
		maxY = y0;
		
	}
	
	public void drawNewPoint(int xcurr, int ycurr) {
		if (xcurr >= w) xcurr = w-1;
		else if (xcurr < 0) xcurr = 0;
		if (ycurr >= h) ycurr = h-1;
		else if (ycurr < 0) ycurr = 0;
		this.xcurr = xcurr;
		this.ycurr = ycurr;
		repaint();
	}
	
	public void finishDraw() {	
		int graphicWidth = maxX - minX + 1;
		int graphicHeight = maxY - minY + 1;
		int graphicSize = graphicWidth * graphicHeight;
		graphicArray = new int[graphicSize];
		for (int y = 0; y < graphicHeight; y++) {
			for (int x = 0; x < graphicWidth; x++) {
				int argb = graphic.getRGB(x+minX, y+minY);
				graphicArray[y * graphicWidth + x] = argb;
			}
		}
		resetGraphic();
	}
	
	public void resetGraphic() {
		x0 = -1;
		y0 = -1;
		xcurr = -1;
		ycurr = -1;
		graphic = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);//clear graphic image
	}
	
	

}
