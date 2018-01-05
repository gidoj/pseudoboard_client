package jg.pseudoboard.client.window;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;

import jg.pseudoboard.client.ToolType;

import javax.swing.event.ChangeEvent;

public class Tools extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JCheckBox chckbxFillShape;
	private JSlider sliderSize;
	
	private JRadioButton rdbtnLine;
	private JRadioButton rdbtnArrow;
	private JRadioButton rdbtnBrush;
	private JRadioButton rdbtnCircle;
	private JRadioButton rdbtnRectangle;
	private JRadioButton rdbtnTriangle;
	private JRadioButton rdbtnEraser;
	private JRadioButton rdbtnSelect;
	private JRadioButton rdbtnDrag;
	
	private int w = 150, h = 590;
	
	private Board board;
	
	private JPanel colorView;
	private int red = 0, green = 0, blue = 0;
	
	private ToolType tool = ToolType.BRUSH;

	public Tools(Board board) {
		this.board = board;
		createWindow();
		
	}
	
	public void showTools() {
		setLocation(board.getX() + 10, board.getY() + 50);
		setVisible(true);
	}
	
	public void changeBrushSize(int i) {
		int newSize = Math.min(Math.max(sliderSize.getValue()+i, 1), sliderSize.getMaximum());
		sliderSize.setValue(newSize);
	}
	
	public void setTool(ToolType t) {
		selectTool(tool, false);
		selectTool(t, true);
		tool = t;
	}
	
	public boolean fillShape() {
		return chckbxFillShape.isSelected();
	}
	
	public void setFillShape(boolean fill) {
		chckbxFillShape.setSelected(fill);
	}
	
	private void selectTool(ToolType t, boolean select) {
		switch (t) {
		case ARROW:
			rdbtnArrow.setSelected(select);
			break;
		case BRUSH:
			rdbtnBrush.setSelected(select);
			break;
		case OVAL:
			rdbtnCircle.setSelected(select);
			break;
		case DRAG:
			rdbtnDrag.setSelected(select);
			break;
		case ERASER:
			rdbtnEraser.setSelected(select);
			break;
		case LINE:
			rdbtnLine.setSelected(select);
			break;
		case RECTANGLE:
			rdbtnRectangle.setSelected(select);
			break;
		case SELECT:
			rdbtnSelect.setSelected(select);
			break;
		case TRIANGLE:
			rdbtnTriangle.setSelected(select);
			break;
		default:
			break;
		
		}
	}
	
	public void setColor(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		colorView.setBackground(new Color(red, green, blue));
		board.changeBrushColor((red << 16) | (green << 8) | blue);
	}
	
	private void createWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(w, h);
		setLocation(board.getX() + 10, board.getY() + 50);
		setResizable(false);
		setTitle("Tools");
		setAlwaysOnTop(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		sliderSize = new JSlider();
		sliderSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				board.changeBrushSize(sliderSize.getValue());
			}
		});
		sliderSize.setValue(5);
		sliderSize.setMaximum(20);
		sliderSize.setMinimum(1);
		sliderSize.setBounds(6, 334, 133, 29);
		contentPane.add(sliderSize);
		
		rdbtnLine = new JRadioButton("Line (L)");
		rdbtnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tool = ToolType.LINE;
				board.changeTool(tool, chckbxFillShape.isSelected());
			}
		});
		rdbtnLine.setFocusPainted(false);
		rdbtnLine.setBounds(6, 6, 141, 23);
		contentPane.add(rdbtnLine);
		
		rdbtnArrow = new JRadioButton("Arrow (A)");
		rdbtnArrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tool = ToolType.ARROW;
				board.changeTool(tool, chckbxFillShape.isSelected());
			}
		});
		rdbtnArrow.setFocusPainted(false);
		rdbtnArrow.setBounds(6, 36, 141, 23);
		contentPane.add(rdbtnArrow);
		
		rdbtnBrush = new JRadioButton("Brush (B)");
		rdbtnBrush.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tool = ToolType.BRUSH;
				board.changeTool(tool, chckbxFillShape.isSelected());
			}
		});
		rdbtnBrush.setSelected(true);
		rdbtnBrush.setFocusPainted(false);
		rdbtnBrush.setBounds(6, 66, 141, 23);
		contentPane.add(rdbtnBrush);
		
		rdbtnCircle = new JRadioButton("Circle (C)");
		rdbtnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tool = ToolType.OVAL;
				board.changeTool(tool, chckbxFillShape.isSelected());
			}
		});
		rdbtnCircle.setFocusPainted(false);
		rdbtnCircle.setBounds(6, 96, 141, 23);
		contentPane.add(rdbtnCircle);
		
		rdbtnRectangle = new JRadioButton("Rectangle (R)");
		rdbtnRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tool = ToolType.RECTANGLE;
				board.changeTool(tool, chckbxFillShape.isSelected());
			}
		});
		rdbtnRectangle.setFocusPainted(false);
		rdbtnRectangle.setBounds(6, 126, 141, 23);
		contentPane.add(rdbtnRectangle);
		
		rdbtnTriangle = new JRadioButton("Triangle (T)");
		rdbtnTriangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tool = ToolType.TRIANGLE;
				board.changeTool(tool, chckbxFillShape.isSelected());
			}
		});
		rdbtnTriangle.setFocusPainted(false);
		rdbtnTriangle.setBounds(6, 156, 141, 23);
		contentPane.add(rdbtnTriangle);
		
		rdbtnEraser = new JRadioButton("Eraser (E)");
		rdbtnEraser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tool = ToolType.ERASER;
				board.changeTool(tool, chckbxFillShape.isSelected());
			}
		});
		rdbtnEraser.setFocusPainted(false);
		rdbtnEraser.setBounds(6, 186, 141, 23);
		contentPane.add(rdbtnEraser);
		
		rdbtnSelect = new JRadioButton("Select (S)");
		rdbtnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tool = ToolType.SELECT;
				board.changeTool(tool, chckbxFillShape.isSelected());
			}
		});
		rdbtnSelect.setFocusPainted(false);
		rdbtnSelect.setBounds(6, 216, 141, 23);
		contentPane.add(rdbtnSelect);
		
		rdbtnDrag = new JRadioButton("Drag (D)");
		rdbtnDrag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tool = ToolType.DRAG;
				board.changeTool(tool, chckbxFillShape.isSelected());
			}
		});
		rdbtnDrag.setFocusPainted(false);
		rdbtnDrag.setBounds(6, 246, 141, 23);
		contentPane.add(rdbtnDrag);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnLine);
		group.add(rdbtnArrow);
		group.add(rdbtnBrush);
		group.add(rdbtnCircle);
		group.add(rdbtnRectangle);
		group.add(rdbtnTriangle);
		group.add(rdbtnEraser);
		group.add(rdbtnSelect);
		group.add(rdbtnDrag);
		
		chckbxFillShape = new JCheckBox("Fill Shape (F)");
		chckbxFillShape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.changeTool(tool, chckbxFillShape.isSelected());
			}
		});
		chckbxFillShape.setFocusPainted(false);
		chckbxFillShape.setBounds(6, 276, 128, 23);
		contentPane.add(chckbxFillShape);
		
		JLabel lblBrushSize = new JLabel("Brush Size:");
		lblBrushSize.setBounds(14, 318, 118, 16);
		contentPane.add(lblBrushSize);
		
		JLabel lblColorSelect = new JLabel("Color Select (RGB):");
		lblColorSelect.setBounds(16, 375, 116, 16);
		contentPane.add(lblColorSelect);
		
		colorView = new JPanel();
		colorView.setBackground(new Color(0, 0, 0));
		colorView.setBounds(17, 525, 116, 29);
		contentPane.add(colorView);
		
		JSlider sliderRed = new JSlider();
		sliderRed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				red = sliderRed.getValue();
				colorView.setBackground(new Color(red, green, blue));
				board.changeBrushColor((red << 16) | (green << 8) | blue); 
			}
		});
		sliderRed.setValue(0);
		sliderRed.setMaximum(255);
		sliderRed.setBounds(8, 403, 133, 29);
		contentPane.add(sliderRed);
		
		JSlider sliderGreen = new JSlider();
		sliderGreen.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				green = sliderGreen.getValue();
				colorView.setBackground(new Color(red, green, blue));
				board.changeBrushColor((red << 16) | (green << 8) | blue);
			}
		});
		sliderGreen.setValue(0);
		sliderGreen.setMaximum(255);
		sliderGreen.setBounds(8, 444, 133, 29);
		contentPane.add(sliderGreen);
		
		JSlider sliderBlue = new JSlider();
		sliderBlue.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				blue = sliderBlue.getValue();
				colorView.setBackground(new Color(red, green, blue));
				board.changeBrushColor((red << 16) | (green << 8) | blue);
			}
		});
		sliderBlue.setValue(0);
		sliderBlue.setMaximum(255);
		sliderBlue.setBounds(8, 485, 133, 29);
		contentPane.add(sliderBlue);
		
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
}
