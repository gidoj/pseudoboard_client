package jg.pseudoboard.client.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import jg.pseudoboard.client.MessageHandler;
import jg.pseudoboard.common.MessageElement;
import jg.pseudoboard.common.MessageTypeConverter.MessageType;

public class OpenCanvas extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JButton btnOpen;
	private JList<String> canvasList;
	private DefaultListModel<String> listModel;
	
	private List<String> rawNames;
	
	private int w = 200, h = 300;
	
	private MessageHandler mh;

	public OpenCanvas(MessageHandler mh) {
		this.mh = mh;
		rawNames = new ArrayList<String>();
		createWindow();
	}
	
	public void setCanvasList() {
		listModel.clear();
		rawNames.clear();
		
		String[] canvases = mh.getCanvasList();
		if (canvases.length == 1 && canvases[0].equals("")) {
			btnOpen.setEnabled(false);
			return;
		}
		btnOpen.setEnabled(true);
		
		for (int i = 0; i < canvases.length; i++) {
			rawNames.add(canvases[i]);
			String[] info = canvases[i].split("_")[1].split("-");
			String name = info[1] + " (" + info[0] + ")";
			listModel.addElement(name);
		}
		canvasList.setSelectedIndex(0);
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
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblYourCanvases = new JLabel("Your canvases:");
		lblYourCanvases.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourCanvases.setBounds(6, 10, 188, 16);
		contentPane.add(lblYourCanvases);
		
		listModel = new DefaultListModel<String>();
		canvasList = new JList<String>(listModel);
		canvasList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane canvasListScroll = new JScrollPane(canvasList);
		canvasListScroll.setBounds(25, 40, 150, 175);
		contentPane.add(canvasListScroll);
		
		btnOpen = new JButton("OPEN");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int chosen = canvasList.getSelectedIndex();
				mh.sendData(new MessageElement(rawNames.get(chosen), MessageType.OPEN_CANVAS));
				setVisible(false);
			}
		});
		btnOpen.setBounds(41, 231, 117, 29);
		contentPane.add(btnOpen);
		
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
}
