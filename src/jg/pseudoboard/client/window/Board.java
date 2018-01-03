package jg.pseudoboard.client.window;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import jg.pseudoboard.client.Canvas;
import jg.pseudoboard.client.MessageHandler;
import jg.pseudoboard.common.MessageElement;
import jg.pseudoboard.common.MessageTypeConverter.MessageType;

public class Board extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private Canvas canvas;
	private Tools tools;
	
	private NewCanvas newCanvas;
	private OpenCanvas openCanvas;
	
	private int w = 850, h = 650;
	
	private MessageHandler mh;

	public Board(MessageHandler mh) {
		this.mh = mh;
		newCanvas = new NewCanvas(mh);
		openCanvas = new OpenCanvas(mh);
		createWindow();
		mh.setCanvas(canvas);
	}
	
	public void showBoard() {
		setVisible(true);
		tools = new Tools(this);
		tools.setVisible(true);
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
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showOpenCanvas();
			}
		});
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveCanvas();
			}
		});
		mnFile.add(mntmSave);
		
		JMenuItem mntmShare = new JMenuItem("Share");
		mntmShare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showShareCanvas();
			}
		});
		mnFile.add(mntmShare);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		canvas = new Canvas();
		contentPane.add(canvas);
		GridBagLayout gbl_canvas = new GridBagLayout();
		gbl_canvas.columnWidths = new int[]{0};
		gbl_canvas.rowHeights = new int[]{0};
		gbl_canvas.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_canvas.rowWeights = new double[]{Double.MIN_VALUE};
		canvas.setLayout(gbl_canvas);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				mh.disconnect(false);
				System.exit(0);
			}
		});
	}

}
