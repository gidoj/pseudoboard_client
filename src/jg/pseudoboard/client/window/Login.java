package jg.pseudoboard.client.window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import jg.pseudoboard.client.ConnectionStatus;
import jg.pseudoboard.client.MessageHandler;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private int w = 250, h = 315;
	private JTextField txtUsername;
	private JTextField txtID;
	
	private MessageHandler mh;
	private static int port = 21898;
	
	public Login() {
		mh = new MessageHandler(port);
		createWindow();
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
		setResizable(false);
		setTitle("Login");
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Pseudoboard Login");
		lblTitle.setForeground(new Color(0, 0, 128));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Helvetica", Font.PLAIN, 20));
		lblTitle.setBounds(10, 20, 230, 25);
		contentPane.add(lblTitle);
		
		JLabel lblUsername = new JLabel("Username (aA-zZ, 0-9):");
		lblUsername.setFont(new Font("Helvetica", Font.PLAIN, 15));
		lblUsername.setBounds(10, 57, 171, 16);
		contentPane.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(10, 78, 230, 26);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblID = new JLabel("ID (5 digits, no leading 0):");
		lblID.setFont(new Font("Helvetica", Font.PLAIN, 15));
		lblID.setBounds(10, 133, 171, 16);
		contentPane.add(lblID);
		
		txtID = new JTextField();
		txtID.setColumns(10);
		txtID.setBounds(10, 154, 230, 26);
		txtID.setDocument(new JTextFieldLimit(5));
		contentPane.add(txtID);
		
		JCheckBox chckbxNewUser = new JCheckBox("NEW USER");
		chckbxNewUser.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxNewUser.setBounds(71, 192, 107, 23);
		contentPane.add(chckbxNewUser);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtUsername.getText();
				String idString = txtID.getText();
				if (username.length() == 0 || idString.length() == 0) {
					JOptionPane.showMessageDialog(contentPane, "Username or ID empty. Please enter required information.", 
							"Empty Fields!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!username.matches("[a-zA-Z0-9]*")) {
					JOptionPane.showMessageDialog(contentPane, "Username must only include letters and numbers.", 
												"Invalid Username!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (idString.length() != 5 || !idString.matches("[0-9]*") || idString.charAt(0) == '0') {
					JOptionPane.showMessageDialog(contentPane, "ID must be 5 digits long with no leading 0s.", 
							"Invalid ID!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int id = Integer.parseInt(idString);
				boolean newUser = chckbxNewUser.isSelected();
				ConnectionStatus status = mh.connect(username, id, newUser);
				if (status.equals(ConnectionStatus.SERVER_DOWN)) {
					JOptionPane.showMessageDialog(contentPane, "If you want to use this program,\ngo bother Joe to start the server.", 
							"Server Down!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (newUser) {
					if (status.equals(ConnectionStatus.LOGIN_FAIL)) {
						JOptionPane.showMessageDialog(contentPane, "Please try another username.", 
								"Username already taken!", JOptionPane.ERROR_MESSAGE);
						return;
					}
				} else {
					if (status.equals(ConnectionStatus.LOGIN_FAIL)) {
						JOptionPane.showMessageDialog(contentPane, "The information provided doesn't match any stored users.\nPlease try again.", 
								"Incorrect Username/ID!", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				setVisible(false);
				Board board = new Board(mh);
				board.showBoard();
			}
		});
		btnLogin.setBounds(66, 234, 117, 29);
		contentPane.add(btnLogin);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
