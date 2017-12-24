package jg.pseudoboard.client.window;

import java.awt.Color;

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
import javax.swing.event.ChangeEvent;

public class Tools extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private int w = 150, h = 590;
	
	private Board board;
	
	private JPanel colorView;
	private int red = 0, green = 0, blue = 0;

	public Tools(Board board) {
		this.board = board;
		createWindow();
		
	}
	
	public void showTools() {
		setLocation(board.getX() + 10, board.getY() + 50);
		setVisible(true);
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
		
		JSlider sliderSize = new JSlider();
		sliderSize.setValue(5);
		sliderSize.setMaximum(20);
		sliderSize.setMinimum(1);
		sliderSize.setBounds(6, 334, 133, 29);
		contentPane.add(sliderSize);
		
		JRadioButton rdbtnLine = new JRadioButton("Line (L)");
		rdbtnLine.setFocusPainted(false);
		rdbtnLine.setBounds(6, 6, 141, 23);
		contentPane.add(rdbtnLine);
		
		JRadioButton rdbtnArrow = new JRadioButton("Arrow (A)");
		rdbtnArrow.setFocusPainted(false);
		rdbtnArrow.setBounds(6, 36, 141, 23);
		contentPane.add(rdbtnArrow);
		
		JRadioButton rdbtnBrush = new JRadioButton("Brush (B)");
		rdbtnBrush.setSelected(true);
		rdbtnBrush.setFocusPainted(false);
		rdbtnBrush.setBounds(6, 66, 141, 23);
		contentPane.add(rdbtnBrush);
		
		JRadioButton rdbtnCircle = new JRadioButton("Circle (C)");
		rdbtnCircle.setFocusPainted(false);
		rdbtnCircle.setBounds(6, 96, 141, 23);
		contentPane.add(rdbtnCircle);
		
		JRadioButton rdbtnRectangle = new JRadioButton("Rectangle (R)");
		rdbtnRectangle.setFocusPainted(false);
		rdbtnRectangle.setBounds(6, 126, 141, 23);
		contentPane.add(rdbtnRectangle);
		
		JRadioButton rdbtnTriangle = new JRadioButton("Triangle (T)");
		rdbtnTriangle.setFocusPainted(false);
		rdbtnTriangle.setBounds(6, 156, 141, 23);
		contentPane.add(rdbtnTriangle);
		
		JRadioButton rdbtnEraser = new JRadioButton("Eraser (E)");
		rdbtnEraser.setFocusPainted(false);
		rdbtnEraser.setBounds(6, 186, 141, 23);
		contentPane.add(rdbtnEraser);
		
		JRadioButton rdbtnSelect = new JRadioButton("Select (S)");
		rdbtnSelect.setFocusPainted(false);
		rdbtnSelect.setBounds(6, 216, 141, 23);
		contentPane.add(rdbtnSelect);
		
		JRadioButton rdbtnDrag = new JRadioButton("Drag (D)");
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
		
		JCheckBox chckbxFillShape = new JCheckBox("Fill Shape");
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
			}
		});
		sliderBlue.setValue(0);
		sliderBlue.setMaximum(255);
		sliderBlue.setBounds(8, 485, 133, 29);
		contentPane.add(sliderBlue);
		
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
}
