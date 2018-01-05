package jg.pseudoboard.client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import jg.pseudoboard.client.window.Board;

public class Canvas extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Board board;

	private int w, h, maxW, maxH; // w, h relate to window; maxW, maxH relate to canvas
	private int size; // maxW*maxH
	public int offx, offy;
	
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
	
	public Canvas(Board board) {
		this.board = board;
	}
	
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
		
		if (tool.equals(ToolType.DRAG) && xcurr+ycurr >= 0) {
			offx -= xcurr - x0;
			offy -= ycurr - y0;
			offx = Math.min(Math.max(offx, 0), Math.abs(maxW-w-1));
			offy = Math.min(Math.max(offy, 0), Math.abs(maxH-h-1));
			x0 = xcurr;
			y0 = ycurr;
		}
		
		window = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		
		for (int y = offy; y < offy+Math.min(h, maxH); y++) {
			for (int x = offx; x < offx+Math.min(w, maxW); x++) {
				int rgb = pixelArray[y*maxW + x];
				window.setRGB(x-offx, y-offy, rgb);
			}
		}
		
		g.drawImage(window, 0, 0, w, h, null);
		
		if (xcurr + ycurr >= 0 && !tool.equals(ToolType.DRAG)) {
			//if want coninuous brush stroke get rid of if statement - always new graphic
			if (tool != ToolType.BRUSH) graphic = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics gg = graphic.getGraphics();
			gg.setColor(new Color(brushColor | 0xFF000000));
			
			Graphics2D g2 = (Graphics2D) gg;
			g2.setStroke(new BasicStroke(brushSize));
			
			switch (tool) {
			case ARROW:
				break;
			case BRUSH:
				int stepSize = 1;
				int dx = xcurr - x0;
				int dy = ycurr - y0;
				int xs = dx == 0 ? 0 : stepSize * dx/Math.abs(dx);
				int ys = dy == 0 ? 0 : stepSize * dy/Math.abs(dy);
				int mSize = brushSize+1;
				while (dx!=0 || dy!=0) {
					int cx = x0 - mSize/2;
					int cy = y0 - mSize/2;
					gg.fillOval(cx, cy, mSize, mSize);
					dx = xcurr-x0;
					dy = ycurr-y0;					
					x0 = dx == 0 ? x0 : x0+xs;
					y0 = dy == 0 ? y0 : y0+ys;
				}
				break;
			case CIRCLE:
			case OVAL:
				int ox1 = x0, oy1 = y0;
				if (xcurr < x0) ox1 = xcurr;
				if (ycurr < y0) oy1 = ycurr;
				int ow = Math.abs(x0-xcurr);
				int oh = Math.abs(y0-ycurr);
				if (fillShape) g2.fillOval(ox1, oy1, ow, oh);
				else g2.drawOval(ox1, oy1, ow, oh);
				break;
			case ERASER:
				break;
			case LINE:
				g2.drawLine(x0, y0, xcurr, ycurr);
				break;
			case SQUARE:
			case RECTANGLE:
				int x1 = x0, y1 = y0;
				int rw = Math.abs(x0-xcurr);
				int rh = tool == ToolType.SQUARE ? rw : Math.abs(y0-ycurr);			
				if (xcurr < x0) x1 = tool == ToolType.SQUARE ? x0 - rw : xcurr;
				if (ycurr < y0) y1 = tool == ToolType.SQUARE ? y0 - rh : ycurr;
				if (fillShape) g2.fillRect(x1, y1, rw, rh);
				else g2.drawRect(x1, y1, rw, rh);
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
			
			if (tool == ToolType.BRUSH) {
				//uncomment for continuous brush stroke
				//board.sendCanvasUpdate(false);
				//setInitialPoint(xcurr, ycurr);
			}
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
		window = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		graphic = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
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
		int gminX = canvas[0];
		int gminY = canvas[1];
		int gmaxX = canvas[2];
		int gmaxY = canvas[3];
		int graphicWidth = gmaxX - gminX + 1;
		int graphicHeight = gmaxY - gminY + 1;
		int graphicSize = graphicWidth * graphicHeight;
		graphicArray = new int[graphicSize];
		for (int y = 0; y < graphicHeight; y++) {
			for (int x = 0; x < graphicWidth; x++) {
				int updateVal = canvas[y*graphicWidth + x + 4];
				if (updateVal == 0) continue;
				graphicArray[y*graphicWidth + x] = updateVal;
				pixelArray[(y+gminY)*maxW + (x+gminX)] = updateVal;
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
		minX = (minX - brushSize) < 0 ? 0 : minX - brushSize;
		minY = (minY - brushSize) < 0 ? 0 : minY - brushSize;
		maxX = (maxX + brushSize) >= maxW ? maxW-1 : maxX + brushSize;
		maxY = (maxY + brushSize) >= maxH ? maxH-1 : maxY + brushSize;
		int graphicWidth = maxX - minX + 1;
		int graphicHeight = maxY - minY + 1;
		int graphicSize = graphicWidth * graphicHeight;
		graphicArray = new int[graphicSize];
		for (int y = 0; y < graphicHeight; y++) {
			for (int x = 0; x < graphicWidth; x++) {
				if (x+minX < 0 || x+minX >= w || y+minY < 0 || y+minY >= h) continue;
				int argb = graphic.getRGB(x+minX, y+minY);
				graphicArray[y * graphicWidth + x] = argb;
			}
		}
	}
	
	public void resetGraphic() {
		x0 = -1;
		y0 = -1;
		xcurr = -1;
		ycurr = -1;
		graphic = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);//clear graphic image
	}
	
	

}
