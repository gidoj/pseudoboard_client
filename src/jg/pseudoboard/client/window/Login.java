package jg.pseudoboard.client.window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private int w = 250, h = 315;
	private JTextField txtUsername;
	private JTextField txtID;
	
	public Login() {
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
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Helvetica", Font.PLAIN, 15));
		lblUsername.setBounds(10, 57, 171, 16);
		contentPane.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(10, 78, 230, 26);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblID = new JLabel("ID (5 digits):");
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
