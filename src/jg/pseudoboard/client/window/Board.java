package jg.pseudoboard.client.window;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import jg.pseudoboard.client.Canvas;
import jg.pseudoboard.client.MessageHandler;
import jg.pseudoboard.client.ToolType;
import jg.pseudoboard.common.GraphicElement;
import jg.pseudoboard.common.MessageElement;
import jg.pseudoboard.common.MessageTypeConverter.MessageType;

public class Board extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private Canvas canvas;
	private Tools tools;
	
	private NewCanvas newCanvas;
	private OpenCanvas openCanvas;
	private ShareCanvas shareCanvas;
	
	private int w = 850, h = 650;
	
	private MessageHandler mh;
	
	public ToolType prevTool = ToolType.BRUSH;

	public Board(MessageHandler mh) {
		this.mh = mh;
		newCanvas = new NewCanvas(mh);
		openCanvas = new OpenCanvas(mh);
		shareCanvas = new ShareCanvas(mh);
		createWindow();
		mh.setCanvas(canvas);
	}
	
	public void changeTool(ToolType t, boolean fillShape) {
		prevTool = t;
		canvas.tool = t;
		canvas.fillShape = fillShape;
	}
	
	public void changeBrushSize(int brushSize) {
		canvas.brushSize = brushSize;
	}
	
	public void changeBrushColor(int brushColor) {
		canvas.brushColor = brushColor;
	}
	
	public void showBoard() {
		setVisible(true);
		tools = new Tools(this);
		tools.setVisible(true);
	}
	
	public void sendCanvasUpdate(boolean resetGraphic) {
		canvas.finishDraw();
		if (resetGraphic) canvas.resetGraphic();
		int minX = canvas.minX+canvas.offx;
		int minY = canvas.minY+canvas.offy;
		int maxX = canvas.maxX+canvas.offx;
		int maxY = canvas.maxY+canvas.offy;
		mh.sendData(new GraphicElement(minX, minY, maxX, maxY, canvas.graphicArray));
	}
	
	private void showNewCanvas() {
		newCanvas.setLocationRelativeTo(null);
		newCanvas.resetWindow();
		newCanvas.setVisible(true);
	}
	
	private void saveCanvas() {
		mh.sendData(new MessageElement("", MessageType.SAVE_CANVAS));
	}
	
	private void showOpenCanvas() {
		if (openCanvas.isVisible()) return;
		openCanvas.setCanvasList();
		openCanvas.setLocationRelativeTo(null);
		openCanvas.setVisible(true);
	}
	
	private void showShareCanvas() {
		if (shareCanvas.isVisible()) return;
		shareCanvas.setUserList();
		shareCanvas.setLocationRelativeTo(null);
		shareCanvas.setVisible(true);
	}
	
	private void createWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(w, h);
		setLocationRelativeTo(null);
		setTitle("PseudoBoard");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showNewCanvas();
			}
		});
		mntmNew.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showOpenCanvas();
			}
		});
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (canvas.noCanvas) {
					JOptionPane.showMessageDialog(contentPane, "No canvas is currently open that can be saved.", 
							"No canvas!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				saveCanvas();
			}
		});
		//mntmSave.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnFile.add(mntmSave);
		
		JMenuItem mntmShare = new JMenuItem("Share");
		mntmShare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (canvas.noCanvas) {
					JOptionPane.showMessageDialog(contentPane, "No canvas is currently open that can be shared.", 
							"No canvas!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				showShareCanvas();
			}
		});
		mntmShare.setAccelerator(KeyStroke.getKeyStroke('W', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnFile.add(mntmShare);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mh.disconnect(false);
				System.exit(0);
			}
		});
		mntmQuit.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnFile.add(mntmQuit);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenuItem mntmToolPane = new JMenuItem("Tool Pane");
		mntmToolPane.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tools.showTools();
			}
		});
		mnView.add(mntmToolPane);
		
		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);
		
		JMenuItem mntmArrow = new JMenuItem("(A) Arrow");
		mntmArrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTool(ToolType.ARROW);
			}
		});
		mnTools.add(mntmArrow);
		
		JMenuItem mntmBrush = new JMenuItem("(B) Brush");
		mntmBrush.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTool(ToolType.BRUSH);
			}
		});
		mnTools.add(mntmBrush);
		
		JMenuItem mntmCircle = new JMenuItem("(C) Circle");
		mntmCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTool(ToolType.OVAL);
			}
		});
		mnTools.add(mntmCircle);
		
		JMenuItem mntmDrag = new JMenuItem("(D) Drag");
		mntmDrag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTool(ToolType.DRAG);
			}
		});
		mnTools.add(mntmDrag);
		
		JMenuItem mntmEraser = new JMenuItem("(E) Eraser");
		mntmEraser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTool(ToolType.ERASER);
			}
		});
		mnTools.add(mntmEraser);
		
		JMenuItem mntmLine = new JMenuItem("(L) Line");
		mntmLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTool(ToolType.LINE);
			}
		});
		mnTools.add(mntmLine);
		
		JMenuItem mntmRectangle = new JMenuItem("(R) Rectangle");
		mntmRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTool(ToolType.RECTANGLE);
			}
		});
		mnTools.add(mntmRectangle);
		
		JMenuItem mntmSelect = new JMenuItem("(S) Select");
		mntmSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTool(ToolType.SELECT);
			}
		});
		mnTools.add(mntmSelect);
		
		JMenu mnColor = new JMenu("Color");
		menuBar.add(mnColor);
		
		JMenuItem mntmBlack = new JMenuItem("(1) BLACK");
		mntmBlack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tools.setColor(0, 0, 0);
			}
		});
		mnColor.add(mntmBlack);
		
		JMenuItem mntmWhite = new JMenuItem("(2) WHITE");
		mntmWhite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tools.setColor(255, 255, 255);
			}
		});
		mnColor.add(mntmWhite);
		
		JMenuItem mntmGray = new JMenuItem("(3) GRAY");
		mntmGray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tools.setColor(170, 170, 170);
			}
		});
		mnColor.add(mntmGray);
		
		JMenuItem mntmBlue = new JMenuItem("(4) BLUE");
		mntmBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tools.setColor(0, 0, 255);
			}
		});
		mnColor.add(mntmBlue);
		
		JMenuItem mntmRed = new JMenuItem("(5) RED");
		mntmRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tools.setColor(255, 0, 0);
			}
		});
		mnColor.add(mntmRed);
		
		JMenuItem mntmGreen = new JMenuItem("(6) GREEN");
		mntmGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tools.setColor(0, 255, 0);
			}
		});
		mnColor.add(mntmGreen);
		
		JMenuItem mntmYellow = new JMenuItem("(7) YELLOW");
		mntmYellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tools.setColor(255, 255, 0);
			}
		});
		mnColor.add(mntmYellow);
		
		JMenuItem mntmOrange = new JMenuItem("(8) ORANGE");
		mntmOrange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tools.setColor(255, 200, 0);
			}
		});
		mnColor.add(mntmOrange);
		
		JMenuItem mntmCyan = new JMenuItem("(9) CYAN");
		mntmCyan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tools.setColor(0, 255, 255);
			}
		});
		mnColor.add(mntmCyan);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		canvas = new Canvas(this);
		contentPane.add(canvas);
		GridBagLayout gbl_canvas = new GridBagLayout();
		gbl_canvas.columnWidths = new int[]{0};
		gbl_canvas.rowHeights = new int[]{0};
		gbl_canvas.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_canvas.rowWeights = new double[]{Double.MIN_VALUE};
		canvas.setLayout(gbl_canvas);
		
		canvas.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if (canvas.noCanvas) return;
				sendCanvasUpdate(true);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (canvas.noCanvas) return;
				canvas.setInitialPoint(e.getX(), e.getY());
			}
			
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {	
			}
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		canvas.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if (canvas.noCanvas) return;
				canvas.drawNewPoint(e.getX(), e.getY());
			}
			
			public void mouseMoved(MouseEvent e) {
			}
		});
		
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE:
				case KeyEvent.VK_SHIFT:
					canvas.tool = prevTool;
					break;
				case KeyEvent.VK_OPEN_BRACKET:
					tools.changeBrushSize(-1);
					break;
				case KeyEvent.VK_CLOSE_BRACKET:
					tools.changeBrushSize(1);
					break;
				case KeyEvent.VK_L:
					updateTool(ToolType.LINE);
					break;
				case KeyEvent.VK_A:
					updateTool(ToolType.ARROW);
					break;
				case KeyEvent.VK_B:
					updateTool(ToolType.BRUSH);
					break;
				case KeyEvent.VK_C:
					updateTool(ToolType.OVAL);
					break;
				case KeyEvent.VK_R:
					updateTool(ToolType.RECTANGLE);
					break;
				case KeyEvent.VK_T:
					updateTool(ToolType.TRIANGLE);
					break;
				case KeyEvent.VK_E:
					updateTool(ToolType.ERASER);
					break;
				case KeyEvent.VK_S:
					updateTool(ToolType.SELECT);
					break;
				case KeyEvent.VK_D:
					updateTool(ToolType.DRAG);
					break;
				case KeyEvent.VK_F:
					tools.setFillShape(!tools.fillShape());
					changeTool(prevTool, tools.fillShape());
					break;
				case KeyEvent.VK_1:
					tools.setColor(0, 0, 0);
					break;
				case KeyEvent.VK_2:
					tools.setColor(255, 255, 255);
					break;
				case KeyEvent.VK_3:
					tools.setColor(170, 170, 170);
					break;
				case KeyEvent.VK_4:
					tools.setColor(0, 0, 255);
					break;
				case KeyEvent.VK_5:
					tools.setColor(255, 0, 0);
					break;
				case KeyEvent.VK_6:
					tools.setColor(0, 255, 0);
					break;
				case KeyEvent.VK_7:
					tools.setColor(255, 255, 0);
					break;
				case KeyEvent.VK_8:
					tools.setColor(255, 200, 0);
					break;
				case KeyEvent.VK_9:
					tools.setColor(0, 255, 255);
					break;
				default:
					break;
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE:
					canvas.tool = ToolType.DRAG;
					break;
				case KeyEvent.VK_SHIFT:
					switch (prevTool) {
					case OVAL:
						canvas.tool = ToolType.CIRCLE;
						break;
					case RECTANGLE:
						canvas.tool = ToolType.SQUARE;
						break;
					case LINE:
						canvas.tool = ToolType.RIGID_LINE;
						break;
					default:
						break;
					}
					break;
				default:
					break;
				}
			}
		});
		
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				mh.disconnect(false);
				System.exit(0);
			}
		});
	}
	
	public void updateTool(ToolType t) {
		prevTool = t;
		tools.setTool(prevTool);
		changeTool(prevTool, tools.fillShape());
	}

}
