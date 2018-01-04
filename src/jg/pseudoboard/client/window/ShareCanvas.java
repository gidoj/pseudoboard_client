package jg.pseudoboard.client.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class ShareCanvas extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JButton btnShare;
	private JList<String> userList;
	private DefaultListModel<String> listModel;
	
	private int w = 200, h = 300;
	
	private MessageHandler mh;

	public ShareCanvas(MessageHandler mh) {
		this.mh = mh;
		createWindow();
	}
	
	public void setUserList() {
		listModel.clear();
		
		String[] users = mh.getUserList();
		if (users.length == 1 && users[0].equals("")) {
			btnShare.setEnabled(false);
			return;
		}
		btnShare.setEnabled(true);
		
		for (int i = 0; i < users.length; i++) {
			listModel.addElement(users[i]);
		}
		userList.setSelectedIndex(0);
	}
	
	private void createWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(w, h);
		setLocationRelativeTo(null);
		setTitle("Share Canvas");
		setResizable(false);
		setAlwaysOnTop(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblShare = new JLabel("Share this canvas with:");
		lblShare.setHorizontalAlignment(SwingConstants.CENTER);
		lblShare.setBounds(6, 10, 188, 16);
		contentPane.add(lblShare);
		
		listModel = new DefaultListModel<String>();
		userList = new JList<String>(listModel);
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane userListScroll = new JScrollPane(userList);
		userListScroll.setBounds(25, 40, 150, 175);
		contentPane.add(userListScroll);
		
		btnShare = new JButton("SHARE");
		btnShare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mh.sendData(new MessageElement(userList.getSelectedValue(), MessageType.SHARE_CANVAS));
				setVisible(false);
			}
		});
		btnShare.setBounds(41, 231, 117, 29);
		contentPane.add(btnShare);
		
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}

}
