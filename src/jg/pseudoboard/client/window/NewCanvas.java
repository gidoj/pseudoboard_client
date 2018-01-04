package jg.pseudoboard.client.window;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jg.pseudoboard.client.MessageHandler;
import jg.pseudoboard.common.MessageElement;
import jg.pseudoboard.common.MessageTypeConverter.MessageType;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewCanvas extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;

	private JTextField txtName;
	private JTextField txtWidth;
	private JTextField txtHeight;
	private JSlider sliderRed;
	private JSlider sliderBlue;
	private JSlider sliderGreen;
	private JPanel colorView;
	
	private int w = 200, h = 550;
	
	private int red = 255, green = 255, blue = 255;

	public NewCanvas(MessageHandler mh) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(w, h);
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("New Canvas");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCanvasName = new JLabel("Canvas Name (aA-zZ, 0-9):");
		lblCanvasName.setBounds(6, 10, 288, 16);
		contentPane.add(lblCanvasName);
		
		txtName = new JTextField();
		txtName.setBounds(6, 38, 188, 26);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblWidth = new JLabel("Width (px):");
		lblWidth.setBounds(6, 76, 82, 16);
		contentPane.add(lblWidth);
		
		txtWidth = new JTextField();
		txtWidth.setText("1500");
		txtWidth.setBounds(6, 104, 82, 26);
		contentPane.add(txtWidth);
		txtWidth.setColumns(10);
		
		JLabel lblHeightpx = new JLabel("Height (px):");
		lblHeightpx.setBounds(100, 76, 94, 16);
		contentPane.add(lblHeightpx);
		
		txtHeight = new JTextField();
		txtHeight.setText("1500");
		txtHeight.setBounds(100, 104, 82, 26);
		contentPane.add(txtHeight);
		txtHeight.setColumns(10);
		
		JLabel lblColor = new JLabel("Background Color (RGB):");
		lblColor.setBounds(6, 142, 176, 16);
		contentPane.add(lblColor);
		
		colorView = new JPanel();
		colorView.setBackground(Color.BLACK);
		colorView.setBounds(10, 293, 179, 29);
		contentPane.add(colorView);
		
		sliderRed = new JSlider();
		sliderRed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				red = sliderRed.getValue();
				colorView.setBackground(new Color(red, green, blue));
			}
		});
		sliderRed.setValue(255);
		sliderRed.setMaximum(255);
		sliderRed.setBounds(6, 170, 190, 29);
		contentPane.add(sliderRed);
		
		sliderGreen = new JSlider();
		sliderGreen.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				green = sliderGreen.getValue();
				colorView.setBackground(new Color(red, green, blue));
			}
		});
		sliderGreen.setValue(255);
		sliderGreen.setMaximum(255);
		sliderGreen.setBounds(5, 211, 190, 29);
		contentPane.add(sliderGreen);
		
		sliderBlue = new JSlider();
		sliderBlue.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				blue = sliderBlue.getValue();
				colorView.setBackground(new Color(red, green, blue));
			}
		});
		sliderBlue.setValue(255);
		sliderBlue.setMaximum(255);
		sliderBlue.setBounds(5, 252, 190, 29);
		contentPane.add(sliderBlue);
		
		JLabel lblQuickColor = new JLabel("Quick Color:");
		lblQuickColor.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuickColor.setBounds(38, 334, 124, 16);
		contentPane.add(lblQuickColor);
		
		JPanel whiteCell = new JPanel();
		whiteCell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				red = 255;
				green = 255;
				blue = 255;
				sliderRed.setValue(red);
				sliderGreen.setValue(green);
				sliderBlue.setValue(blue);
				colorView.setBackground(new Color(red, green, blue));
			}
		});
		whiteCell.setForeground(Color.BLACK);
		whiteCell.setBackground(Color.WHITE);
		whiteCell.setBounds(27, 367, 26, 26);
		contentPane.add(whiteCell);
		
		JPanel blackCell = new JPanel();
		blackCell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				red = 0;
				green = 0;
				blue = 0;
				sliderRed.setValue(red);
				sliderGreen.setValue(green);
				sliderBlue.setValue(blue);
				colorView.setBackground(new Color(red, green, blue));
			}
		});
		blackCell.setForeground(Color.BLACK);
		blackCell.setBackground(Color.BLACK);
		blackCell.setBounds(67, 367, 26, 26);
		contentPane.add(blackCell);
		
		JPanel yellowCell = new JPanel();
		yellowCell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				red = 255;
				green = 255;
				blue = 0;
				sliderRed.setValue(red);
				sliderGreen.setValue(green);
				sliderBlue.setValue(blue);
				colorView.setBackground(new Color(red, green, blue));
			}
		});
		yellowCell.setForeground(Color.BLACK);
		yellowCell.setBackground(Color.YELLOW);
		yellowCell.setBounds(107, 367, 26, 26);
		contentPane.add(yellowCell);
		
		JPanel blueCell = new JPanel();
		blueCell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				red = 0;
				green = 0;
				blue = 255;
				sliderRed.setValue(red);
				sliderGreen.setValue(green);
				sliderBlue.setValue(blue);
				colorView.setBackground(new Color(red, green, blue));
			}
		});
		blueCell.setForeground(Color.BLACK);
		blueCell.setBackground(new Color(0, 0, 255));
		blueCell.setBounds(147, 367, 26, 26);
		contentPane.add(blueCell);
		
		JPanel redCell = new JPanel();
		redCell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				red = 255;
				green = 0;
				blue = 0;
				sliderRed.setValue(red);
				sliderGreen.setValue(green);
				sliderBlue.setValue(blue);
				colorView.setBackground(new Color(red, green, blue));
			}
		});
		redCell.setForeground(Color.BLACK);
		redCell.setBackground(Color.RED);
		redCell.setBounds(27, 408, 26, 26);
		contentPane.add(redCell);
		
		JPanel greenCell = new JPanel();
		greenCell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				red = 0;
				green = 255;
				blue = 0;
				sliderRed.setValue(red);
				sliderGreen.setValue(green);
				sliderBlue.setValue(blue);
				colorView.setBackground(new Color(red, green, blue));
			}
		});
		greenCell.setForeground(Color.BLACK);
		greenCell.setBackground(Color.GREEN);
		greenCell.setBounds(67, 408, 26, 26);
		contentPane.add(greenCell);
		
		JPanel orangeCell = new JPanel();
		orangeCell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				red = 255;
				green = 200;
				blue = 0;
				sliderRed.setValue(red);
				sliderGreen.setValue(green);
				sliderBlue.setValue(blue);
				colorView.setBackground(new Color(red, green, blue));
			}
		});
		orangeCell.setForeground(Color.BLACK);
		orangeCell.setBackground(Color.ORANGE);
		orangeCell.setBounds(107, 408, 26, 26);
		contentPane.add(orangeCell);
		
		JPanel cyanCell = new JPanel();
		cyanCell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				red = 0;
				green = 255;
				blue = 255;
				sliderRed.setValue(red);
				sliderGreen.setValue(green);
				sliderBlue.setValue(blue);
				colorView.setBackground(new Color(red, green, blue));
			}
		});
		cyanCell.setForeground(Color.BLACK);
		cyanCell.setBackground(Color.CYAN);
		cyanCell.setBounds(147, 408, 26, 26);
		contentPane.add(cyanCell);
		
		JButton btnCreate = new JButton("CREATE!");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String canvasName = txtName.getText();
				String widthString = txtWidth.getText();
				String heightString = txtWidth.getText();
				if (canvasName.length() == 0 || widthString.length() == 0 || heightString.length() == 0) {
					JOptionPane.showMessageDialog(contentPane, "Canvas name, height, or width empty. Please enter required information.", 
							"Empty Fields!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!canvasName.matches("[a-zA-Z0-9]*")) {
					JOptionPane.showMessageDialog(contentPane, "Canvas name must only include letters and numbers.", 
												"Invalid canvas name!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!widthString.matches("[0-9]*") || !heightString.matches("[0-9]*")) {
					JOptionPane.showMessageDialog(contentPane, "Height and width must be numbers.", 
							"Invalid heigth/width!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (Integer.parseInt(widthString) > 3000 || Integer.parseInt(heightString) > 3000) {
					JOptionPane.showMessageDialog(contentPane, "Height and width cannot exceed 3000.", 
							"Height/Width too big!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				int color = (red << 16) | (green << 8) | blue;
				String canvasString = canvasName + ";" + widthString + ";" + heightString + ";" + color;
				int response = mh.checkNewCanvas(new MessageElement(canvasString, MessageType.NEW_CANVAS));
				if (response == 1) setVisible(false);
				else {
					JOptionPane.showMessageDialog(contentPane, "You already have a canvas with this name.\nTry another.", 
							"Canvas name taken!", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		btnCreate.setBounds(38, 479, 117, 29);
		contentPane.add(btnCreate);
		
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	
	public void resetWindow() {
		txtName.setText("");
		txtWidth.setText("1500");
		txtHeight.setText("1500");
		red = 255;
		green = 255;
		blue = 255;
		sliderRed.setValue(red);
		sliderGreen.setValue(green);
		sliderBlue.setValue(blue);
	}
}
