package GUI;

import bus.LoginBUS;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LoginView {

	private LoginBUS loginBUS;

	private JPanel contentPane = new JPanel();
	private static JLabel lblUsername = new JLabel("Username");
	private static JLabel lblPassword = new JLabel("Password");
	private static JTextField txtUsername = new JTextField();
	private static JPasswordField txtPassword = new JPasswordField();
	private static JLabel lblTitle = new JLabel("Number Find Game");
	private static JButton btnSubmit = new JButton("Login");
	private static JLabel lnkGotoRegister = new JLabel("Create a new account...");
	private static JLabel lblNetIp = new JLabel("Server Hostname");
	private static JTextField txtNetIp;

	public LoginView(LoginBUS loginBUS) {
		this.loginBUS = loginBUS;

		loginViewSetup();
		customizeComponents();
		bindListeners();
		initViewBinder();
	}

	public void loginViewSetup() {

		contentPane.setBackground(Color.decode("#35455d"));
		contentPane.setSize(1024, 768);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtUsername.setColumns(10);
		
				lblPassword.setForeground(Color.WHITE);
				lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtPassword.setColumns(10);
		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 16));
		lnkGotoRegister.setForeground(Color.WHITE);
		lnkGotoRegister.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNetIp.setForeground(Color.WHITE);
		
				lblNetIp.setFont(new Font("Tahoma", Font.BOLD, 15));
				
						txtNetIp = new JTextField();
						txtNetIp.setEditable(true);
						txtNetIp.setFont(new Font("Tahoma", Font.PLAIN, 17));
						txtNetIp.setColumns(10);
						GroupLayout gl_contentPane = new GroupLayout(contentPane);
						gl_contentPane.setHorizontalGroup(
							gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(357)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 310, GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
													.addGap(5)
													.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_contentPane.createSequentialGroup()
													.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
													.addGap(5)
													.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
												.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 310, GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGap(65)
													.addComponent(lnkGotoRegister, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE))))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(333)
											.addComponent(lblNetIp)
											.addGap(15)
											.addComponent(txtNetIp, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)))
									.addContainerGap(308, Short.MAX_VALUE))
						);
						gl_contentPane.setVerticalGroup(
							gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(292)
									.addComponent(lblTitle)
									.addGap(11)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(3)
											.addComponent(lblUsername))
										.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(5)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(3)
											.addComponent(lblPassword))
										.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(15)
									.addComponent(btnSubmit)
									.addGap(16)
									.addComponent(lnkGotoRegister)
									.addGap(207)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNetIp, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtNetIp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(59))
						);
						contentPane.setLayout(gl_contentPane);

	}

	// Functions

	private void customizeComponents() {
		lnkGotoRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	private void bindListeners() {
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					LoginView.this.loginBUS.action_LoginSubmit();
				} catch (Exception exception) {
					String message = exception.getMessage();
					if (message.contains("Connection refused")) {
						message = "Game server is currently unavailable.";
					}

					JOptionPane.showMessageDialog(LoginView.this.contentPane, message, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		lnkGotoRegister.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginView.this.loginBUS.action_GotoRegisterView();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lnkGotoRegister.setText("<html><a href='' style='color: white'>Create a new account...</a></html>");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lnkGotoRegister.setText("Create a new account...");
			}
		});
	}

    private void initViewBinder() {
        this.loginBUS.viewBinder.txtUsername = txtUsername;
        this.loginBUS.viewBinder.txtPassword = txtPassword;
        this.loginBUS.viewBinder.txtNetIp = txtNetIp;
    }

	// Properties

	public JPanel getContentPane() {
		return contentPane;
	}

}
