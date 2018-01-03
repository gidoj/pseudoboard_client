package jg.pseudoboard.client.window;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jg.pseudoboard.client.MessageHandler;

public class ShareCanvas extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private int w = 200, h = 300;
	
	private MessageHandler mh;

	public ShareCanvas(MessageHandler mh) {
		this.mh = mh;
		createWindow();
	}
	
	private void createWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(w, h);
		setLocationRelativeTo(null);
		setTitle("Open Canvas");
		setResizable(false);
		setAlwaysOnTop(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}

}
